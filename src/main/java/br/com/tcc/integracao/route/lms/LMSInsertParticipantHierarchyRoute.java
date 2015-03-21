package br.com.tcc.integracao.route.lms;

import java.sql.SQLException;

import org.apache.camel.LoggingLevel;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import br.com.tcc.integracao.bean.lms.MergeStrategyHierarchy;
import br.com.tcc.integracao.util.RouteConstants;

@Component
public class LMSInsertParticipantHierarchyRoute extends SpringRouteBuilder {

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
	    .to("seda:lmsCheckForPersistRoute");
		
		onException(Throwable.class)
		.handled(true)
		.setHeader(RouteConstants.SYSTEM_FAIL, simple("LMS"))
		.setBody(simple("false"))
	    .to("checkpoint:error?executionStatus=processed_with_error")
	    .to("seda:lmsCheckForPersistRoute");
		
		from("direct:insertParticipantHierarchy").routeId("lmsInsertParticipantHierarchyRoute")
		.to("checkpoint:bean?message=Hierarquias sendo inseridas...")
		.enrich("mybatis:selectAllHierarchies?statementType=SelectList", new MergeStrategyHierarchy())
		.choice()
		  .when(simple("${body.size()} > 0"))
		  	.to("mybatis:insertLMSHierarchy?statementType=InsertList")
		  .otherwise()
		    .to("checkpoint:bean?message=Não existe nenhuma hierarquia para ser atualizada...")
		.end()
		.setBody(simple("true"))
		.to("seda:lmsCheckForPersistRoute");
	}
}
