package br.com.afferolab.hub.atento.ativos.route.skillo;

import org.apache.camel.LoggingLevel;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import br.com.afferolab.hub.atento.ativos.bean.BatchSizePredicate;
import br.com.afferolab.hub.atento.ativos.bean.ListAggregationStrategy;
import br.com.afferolab.hub.atento.ativos.bean.skillo.SkilloFilter;
import br.com.afferolab.hub.atento.ativos.bean.skillo.SkilloFormatter;
import br.com.afferolab.hub.atento.ativos.util.CopyMessage;
import br.com.afferolab.hub.atento.ativos.util.RouteConstants;

@Component
public class SkilloFormatterRoute extends SpringRouteBuilder {

	private static final Integer BATCH_SIZE = 70;
	
	@Override
	public void configure() throws Exception {
		
		errorHandler(defaultErrorHandler()
	    .maximumRedeliveries(3)
	    .redeliveryDelay(1000)
	    .retryAttemptedLogLevel(LoggingLevel.ERROR));
						
		onException(IllegalArgumentException.class)
        .handled(true)
        .setHeader(RouteConstants.SYSTEM_FAIL, simple("SKILLO"))
        .setBody(simple("false"))
        .to("seda:checkForPersistRoute");
		
		onException(Throwable.class)
		.handled(true)
		.setHeader(RouteConstants.SYSTEM_FAIL, simple("SKILLO"))
		.setBody(simple("false"))
        .to("seda:checkForPersistRoute");
		
		from("direct:skilloFormatter").routeId("SkilloFormatterRoute")
		.setHeader(RouteConstants.HEADER_STATUS, simple("true"))
		.bean(CopyMessage.class)
		.to("checkpoint:bean?message=[Skillo] Filtrando dados...")
		.bean(SkilloFilter.class)
		.to("checkpoint:bean?message=[Skillo] Dados filtrados com sucesso...")
		.bean(SkilloFormatter.class)
		.to("checkpoint:bean?message=[Skillo] Dados formatados com sucesso...")
		.to("checkpoint:bean?message=[Skillo] Employees sendo inseridos na intermediária...")
		.split(body()).stopOnException()
			.aggregate(constant(true), new ListAggregationStrategy())
			.completionPredicate(new BatchSizePredicate(BATCH_SIZE))
			.completionTimeout(RouteConstants.BATCH_TIME_OUT)
				.to("mybatis:insertSkilloEmployee?statementType=InsertList")
			.end()
		.end()
		.to("checkpoint:bean?message=[Skillo] Employees inseridos com sucesso na intermediária...")
		.setBody(simple("true"))
		.to("checkpoint:bean?message=[Skillo] Enviando para rota de inserção/atualização dos dados...")
		.to("seda:checkForPersistRoute");
	}
}