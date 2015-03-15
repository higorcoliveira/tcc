package br.com.afferolab.hub.atento.ativos.route.anet;

import org.apache.camel.LoggingLevel;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import br.com.afferolab.hub.atento.ativos.bean.AggregateStrategy;
import br.com.afferolab.hub.atento.ativos.util.RouteConstants;

@Component
public class ANETCheckForPersistRoute extends SpringRouteBuilder {

	@Override
	public void configure() throws Exception {

		errorHandler(defaultErrorHandler()
	    .maximumRedeliveries(3)
	    .redeliveryDelay(1000)
	    .retryAttemptedLogLevel(LoggingLevel.ERROR));
		
		onException(Throwable.class)
		.handled(true)
        .to("checkpoint:error?executionStatus=processed_with_error");
      		
		from("seda:anetCheckForPersistRoute").routeId("ANETCheckForPersistRoute")
		.aggregate(header(RouteConstants.HEADER_STATUS), new AggregateStrategy())
		.completionSize(2)
		.choice()
		 .when(simple("${body} != true"))
		   .to("checkpoint:bean?message=[ANET] Carga apresentou problemas na gravação de area/hierarquia.")
		 .otherwise()
		   .to("checkpoint:bean?message=[ANET] Areas e Hierarquias inseridas com sucesso...")
		.end()
		.to("seda:checkForPersistRoute");
	}
}
