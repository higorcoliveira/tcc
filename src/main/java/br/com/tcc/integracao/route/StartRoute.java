package br.com.tcc.integracao.route;

import java.util.zip.ZipException;

import org.apache.camel.LoggingLevel;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import br.com.tcc.integracao.XMLValidation;

@Component
public class StartRoute extends SpringRouteBuilder {
	
	@Override
	public void configure() throws Exception {
		
		errorHandler(defaultErrorHandler()
        .maximumRedeliveries(3)
        .redeliveryDelay(1000)
        .retryAttemptedLogLevel(LoggingLevel.ERROR));
		
		onException(ZipException.class)
        .handled(true)
        .to("checkpoint:bean?executionStatus=processed_with_error");
		
		onException(Throwable.class)
		.handled(true)
        .to("checkpoint:bean?executionStatus=processed_with_error");
		
		from("cxfrs:bean:rsServer").routeId("AtivosStartRoute")
		.to("checkpoint:bean?executionStatus=start_process&saveFile=true")
		.to("checkpoint:bean?message=XML sendo descomprimido...")
		.unmarshal().gzip()
		.to("checkpoint:bean?message=XML descomprimido...")
		.bean(XMLValidation.class)
		.to("checkpoint:bean?message=XML validado com sucesso...")		
		.marshal().gzip()
		.to("checkpoint:bean?message=Iniciando processamento da carga...")
	    .inOnly("activemq:queue:{{activemq.queue.name}}");
	}
}