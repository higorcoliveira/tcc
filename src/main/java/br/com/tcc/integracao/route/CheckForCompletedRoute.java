package br.com.tcc.integracao.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import br.com.tcc.integracao.AggregateStrategy;
import br.com.tcc.integracao.util.RouteConstants;

@Component
public class CheckForCompletedRoute extends SpringRouteBuilder {

	@Override
	public void configure() throws Exception {

		errorHandler(defaultErrorHandler()
	    .maximumRedeliveries(3)
	    .redeliveryDelay(1000)
	    .retryAttemptedLogLevel(LoggingLevel.ERROR));
		
		onException(Throwable.class)
		.handled(true)
        .to("checkpoint:error?executionStatus=processed_with_error");
      		
		from("seda:checkForCompletedRoute").routeId("CheckForCompletedRoute")
		.to("checkpoint:bean?message=[Ativo] Verificando se todas as cargas foram finalizadas...")
		.aggregate(header(RouteConstants.HEADER_FINISHED), new AggregateStrategy())
		.completionSize(3)
		.choice()
		 .when(simple("${body} != true"))
		   .to("checkpoint:bean?message=[Ativo] Rota(s) ${header.routeGRFail} ${header.routeAnetFail} ${header.routeSkilloFail} apresentou(aram) falha(s).")
		   .to("checkpoint:error?executionStatus=processed_with_error")
		 .otherwise()
		   .to("checkpoint:bean?message=[Ativo] Carga de Ativos realizada com sucesso...")
		   .to("checkpoint:success?executionStatus=processed_with_success")
		.end();
	}
}
