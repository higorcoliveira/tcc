package br.com.tcc.integracao.route.lms;

import java.sql.SQLException;

import org.apache.camel.LoggingLevel;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import br.com.tcc.integracao.bean.lms.MergeStrategyArea;
import br.com.tcc.integracao.util.RouteConstants;

@Component
public class ANETInsertParticipantAreaRoute extends SpringRouteBuilder {

	@Override
	public void configure() throws Exception {
		
		errorHandler(defaultErrorHandler()
	    .maximumRedeliveries(3)
	    .redeliveryDelay(1000)
	    .retryAttemptedLogLevel(LoggingLevel.ERROR));
								
		onException(SQLException.class)
	    .handled(true)
	    .setHeader(RouteConstants.SYSTEM_FAIL, simple("ANET"))
	    .setBody(simple("false"))
	    .to("checkpoint:error?executionStatus=processed_with_error")
	    .to("seda:anetCheckForPersistRoute");
		
		onException(Throwable.class)
		.handled(true)
		.setHeader(RouteConstants.SYSTEM_FAIL, simple("ANET"))
		.setBody(simple("false"))
	    .to("checkpoint:error?executionStatus=processed_with_error")
	    .to("seda:anetCheckForPersistRoute");
		
		from("direct:insertParticipantArea").routeId("ANETInsertParticipantAreaRoute")
		.to("checkpoint:bean?message=[ANET] Áreas sendo inseridas...")
		.enrich("mybatis:selectAllAreas?statementType=SelectList", new MergeStrategyArea())
		.choice()
		  .when(simple("${body.size()} > 0"))
		  	.to("mybatis:insertAnetArea?statementType=InsertList")
		  .otherwise()
		    .to("checkpoint:bean?message=[ANET] Não existe nenhuma area para ser atualizada...")
		.end()
		.setBody(simple("true"))
		.to("seda:anetCheckForPersistRoute");
	}
}
