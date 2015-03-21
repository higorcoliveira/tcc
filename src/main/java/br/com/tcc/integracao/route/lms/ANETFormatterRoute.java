package br.com.tcc.integracao.route.lms;

import java.sql.SQLException;

import org.apache.camel.LoggingLevel;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import br.com.tcc.integracao.BatchSizePredicate;
import br.com.tcc.integracao.ListAggregationStrategy;
import br.com.tcc.integracao.bean.lms.LMSFilter;
import br.com.tcc.integracao.bean.lms.LMSFormatter;
import br.com.tcc.integracao.util.CopyMessage;
import br.com.tcc.integracao.util.RouteConstants;

@Component
public class ANETFormatterRoute extends SpringRouteBuilder {

	@Override
	@SuppressWarnings("unchecked")
	public void configure() throws Exception {
		
		errorHandler(defaultErrorHandler()
	    .maximumRedeliveries(3)
	    .redeliveryDelay(1000)
	    .retryAttemptedLogLevel(LoggingLevel.ERROR));
						
		onException(IllegalArgumentException.class, SQLException.class)
	    .handled(true)
	    .setHeader(RouteConstants.SYSTEM_FAIL, simple("ANET"))
	    .setBody(simple("false"))
	    .to("seda:checkForPersistRoute");
		
		onException(Throwable.class)
		.handled(true)
		.setHeader(RouteConstants.SYSTEM_FAIL, simple("ANET"))
		.setBody(simple("false"))
	    .to("seda:checkForPersistRoute");
		
		from("direct:anetFormatter").routeId("ANETFormatterRoute")
		.setHeader(RouteConstants.HEADER_STATUS, simple("true"))
		.bean(CopyMessage.class)
		.to("checkpoint:bean?message=[ANET] Filtrando dados...")
		.bean(LMSFilter.class)
		.to("checkpoint:bean?message=[ANET] Dados filtrados com sucesso...")
		.bean(LMSFormatter.class)
		.to("checkpoint:bean?message=[ANET] Dados formatados com sucesso...")
		.to("checkpoint:bean?message=[ANET] Participantes sendo inseridos na intermediária...")
		.split(body()).stopOnException()
			.aggregate(constant(true), new ListAggregationStrategy())
			.completionPredicate(new BatchSizePredicate(RouteConstants.BATCH_SIZE))
			.completionTimeout(RouteConstants.BATCH_TIME_OUT)
				.to("mybatis:insertAnetParticipants?statementType=InsertList")
				.to("mybatis:insertAnetManagerParticipant?statementType=InsertList")
			.end()
		.end()
		.to("checkpoint:bean?message=[ANET] Participantes inseridos com sucesso na intermediária...")
		.multicast()
		.parallelProcessing()
		.to("direct:insertParticipantArea", "direct:insertParticipantHierarchy");
	}
}
