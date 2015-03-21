package br.com.tcc.integracao.route.lms;

import org.apache.camel.LoggingLevel;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import br.com.tcc.integracao.AggregateStrategy;
import br.com.tcc.integracao.util.RouteConstants;

@Component
public class LMSCheckForPersistRoute extends SpringRouteBuilder {

	@Override
	public void configure() throws Exception {

		errorHandler(defaultErrorHandler()
	    .maximumRedeliveries(3)
	    .redeliveryDelay(1000)
	    .retryAttemptedLogLevel(LoggingLevel.ERROR));
		
		onException(Throwable.class)
		.handled(true)
        .to("checkpoint:error?executionStatus=processed_with_error");
      		
		from("seda:lmsCheckForPersistRoute").routeId("lmsCheckForPersistRoute")
		.aggregate(header(RouteConstants.HEADER_STATUS), new AggregateStrategy())
		.completionSize(2)
		.choice()
		 .when(simple("${body} != true"))
		   .to("checkpoint:bean?message=Carga apresentou problemas na gravação de area/hierarquia.")
		 .otherwise()
		   .to("checkpoint:bean?message=Areas e Hierarquias inseridas com sucesso...")
		.end()
		.to("seda:checkForPersistRoute");
	}
}
