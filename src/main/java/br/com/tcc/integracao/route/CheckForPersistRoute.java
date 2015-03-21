package br.com.tcc.integracao.route;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import br.com.tcc.integracao.AggregateStrategy;
import br.com.tcc.integracao.util.RouteConstants;

@Component
public class CheckForPersistRoute extends SpringRouteBuilder {
	
	@Override
	public void configure() throws Exception {
		
		onException(IllegalArgumentException.class)
        .handled(true)
        .to("checkpoint:error?executionStatus=processed_with_error");
		
		from("seda:checkForPersistRoute").routeId("CheckForPersistRoute")
		.to("checkpoint:bean?message=Verificando se todas as validações ocorreram com sucesso...")
		.aggregate(header(RouteConstants.HEADER_STATUS), new AggregateStrategy())
		.completionSize(3)
		.choice()
		 .when(simple("${body} != true"))
		   .to("checkpoint:bean?message=Rota ${header.routeLmsFail} apresentou(aram) falha(s). A carga não irá continuar seu processamento.")
		   .to("checkpoint:error?executionStatus=processed_with_error")
		 .otherwise()
		   .to("checkpoint:bean?message=Enviando para as rotas que persistem os dados na base do LMS...")
		   .multicast()
		   .to("direct:lmsPersist")
		.end();
	}
}
