package br.com.afferolab.hub.atento.ativos.route;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import br.com.afferolab.hub.atento.ativos.bean.AggregateStrategy;
import br.com.afferolab.hub.atento.ativos.util.RouteConstants;

@Component
public class CheckForPersistRoute extends SpringRouteBuilder {
	
	@Override
	public void configure() throws Exception {
		
		onException(IllegalArgumentException.class)
        .handled(true)
        .to("checkpoint:error?executionStatus=processed_with_error");
		
		from("seda:checkForPersistRoute").routeId("CheckForPersistRoute")
		.to("checkpoint:bean?message=[Ativo] Verificando se todas as validações ocorreram com sucesso...")
		.aggregate(header(RouteConstants.HEADER_STATUS), new AggregateStrategy())
		.completionSize(3)
		.choice()
		 .when(simple("${body} != true"))
		   .to("checkpoint:bean?message=[Ativo] Rota(s) ${header.routeGRFail} ${header.routeAnetFail} ${header.routeSkilloFail} apresentou(aram) falha(s). A carga não irá continuar seu processamento.")
		   .to("checkpoint:error?executionStatus=processed_with_error")
		 .otherwise()
		   .to("checkpoint:bean?message=[Ativo] Enviando para as rotas que persistem os dados nas bases do GR, Aulanet e Skillo...")
		   .multicast()
		   .to("direct:gestaoRecursosPersist", "direct:anetPersist", "direct:skilloPersist")
		.end();
	}
}
