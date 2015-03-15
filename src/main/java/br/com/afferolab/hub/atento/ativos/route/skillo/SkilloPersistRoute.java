package br.com.afferolab.hub.atento.ativos.route.skillo;

import java.sql.SQLException;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import br.com.afferolab.hub.atento.ativos.util.RouteConstants;

@Component
public class SkilloPersistRoute extends SpringRouteBuilder {

	@Override
	@SuppressWarnings("unchecked")
	public void configure() throws Exception {

		errorHandler(defaultErrorHandler()
	    .maximumRedeliveries(3)
	    .redeliveryDelay(1000)
	    .retryAttemptedLogLevel(LoggingLevel.ERROR));
						
		onException(IllegalArgumentException.class, SQLException.class)
        .handled(true)
        .setHeader(RouteConstants.SYSTEM_FAIL, simple("SKILLO"))
        .setBody(simple("false"))
        .to("seda:checkForCompletedRoute");
		
		onException(Throwable.class)
		.handled(true)
		.setHeader(RouteConstants.SYSTEM_FAIL, simple("SKILLO"))
		.setBody(simple("false"))
        .to("seda:checkForCompletedRoute");

		from("direct:skilloPersist").routeId("SkilloPersistRoute")
		.to("checkpoint:bean?message=[Skillo] Chamando cargas do Skillo...")
		
		// SKILLO ADMINISTRATIVO
		.setHeader(RouteConstants.HEADER_FINISHED, simple("true"))
		.setHeader(Exchange.HTTP_URI, simple("{{skillo.adm.persist.endpoint}}"))
		.setHeader(Exchange.HTTP_METHOD, constant("GET"))
		.to("http://skilloadm")
		.to("checkpoint:bean?message=[Skillo] Chamada ao Skillo Administrativo executada com status ${header.CamelHttpResponseCode}...")
		
		// SKILLO OPERACIONAL
		.setHeader(Exchange.HTTP_URI, simple("{{skillo.oper.persist.endpoint}}"))
		.setHeader(Exchange.HTTP_METHOD, constant("GET"))
		.to("http://skillooper")
		.to("checkpoint:bean?message=[Skillo] Chamada ao Skillo Operacional executada com status ${header.CamelHttpResponseCode}...")
		
		.setBody(simple("true"))
		.to("seda:checkForCompletedRoute");
	}
}