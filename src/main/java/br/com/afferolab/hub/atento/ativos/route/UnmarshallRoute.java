package br.com.afferolab.hub.atento.ativos.route;

import java.util.zip.ZipException;

import javax.xml.bind.JAXBException;

import org.apache.camel.LoggingLevel;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class UnmarshallRoute extends SpringRouteBuilder {
	
	@Override
	@SuppressWarnings("unchecked")
	public void configure() throws Exception {

		errorHandler(defaultErrorHandler()
	    .maximumRedeliveries(3)
	    .redeliveryDelay(1000)
	    .retryAttemptedLogLevel(LoggingLevel.ERROR));
				
		onException(ZipException.class, JAXBException.class)
        .handled(true)
        .to("checkpoint:error?executionStatus=processed_with_error");
		
		onException(Throwable.class)
		.handled(true)
        .to("checkpoint:error?executionStatus=processed_with_error");
		
		from("activemq:queue:{{activemq.queue.name}}?requestTimeoutCheckerInterval=60")
		.routeId("UnmarshallRoute")
		.to("checkpoint:bean?message=[Ativos] Recebendo mensagem da fila...")
		.to("checkpoint:bean?message=[Ativos] Descomprimindo mensagem...")
		.unmarshal().gzip()
		.to("checkpoint:bean?message=[Ativos] Mensagem descomprimida com sucesso...")
		.unmarshal("ativos")
		.to("checkpoint:bean?message=[Ativos] Lista de objetos montada...")
		.to("checkpoint:bean?message=[Ativos] Limpando Estrutura Intermediária...")
		.to("mybatis:cleanDatabase?statementType=Delete")
		.to("checkpoint:bean?message=[Ativos] Estrutura Intermediária limpa com sucesso...")
		.to("checkpoint:bean?message=[Ativos] Enviando para as rotas de validação do GR, Aulanet e Skillo...")
		.multicast()
		.parallelProcessing()
		.to("direct:gestaoRecursosFormatter", "direct:anetFormatter", "direct:skilloFormatter");
	}
}
