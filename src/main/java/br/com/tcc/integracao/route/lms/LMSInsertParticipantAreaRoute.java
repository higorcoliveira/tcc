package br.com.tcc.integracao.route.lms;

import java.sql.SQLException;

import org.apache.camel.LoggingLevel;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import br.com.tcc.integracao.bean.lms.MergeStrategyArea;
import br.com.tcc.integracao.util.RouteConstants;

@Component
public class LMSInsertParticipantAreaRoute extends SpringRouteBuilder {

	@Override
	public void configure() throws Exception {
		
		errorHandler(defaultErrorHandler()
	    .maximumRedeliveries(3)
	    .redeliveryDelay(1000)
	    .retryAttemptedLogLevel(LoggingLevel.ERROR));
								
		onException(SQLException.class)
	    .handled(true)
	    .setHeader(RouteConstants.SYSTEM_FAIL, simple("LMS"))
	    .setBody(simple("false"))
	    .to("checkpoint:error?executionStatus=processed_with_error")
	    .to("seda:LMSCheckForPersistRoute");
		
		onException(Throwable.class)
		.handled(true)
		.setHeader(RouteConstants.SYSTEM_FAIL, simple("LMS"))
		.setBody(simple("false"))
	    .to("checkpoint:error?executionStatus=processed_with_error")
	    .to("seda:LMSCheckForPersistRoute");
		
		from("direct:insertParticipantArea").routeId("lmsInsertParticipantAreaRoute")
		.to("checkpoint:bean?message=Áreas sendo inseridas...")
		.enrich("mybatis:selectAllAreas?statementType=SelectList", new MergeStrategyArea())
		.choice()
		  .when(simple("${body.size()} > 0"))
		  	.to("mybatis:insertLMSArea?statementType=InsertList")
		  .otherwise()
		    .to("checkpoint:bean?message=Não existe nenhuma area para ser atualizada...")
		.end()
		.setBody(simple("true"))
		.to("seda:LMSCheckForPersistRoute");
	}
}
