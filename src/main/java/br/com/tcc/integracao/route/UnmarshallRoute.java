package br.com.tcc.integracao.route;

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
		.to("checkpoint:bean?message=Recebendo mensagem da fila...")
		.to("checkpoint:bean?message=Descomprimindo mensagem...")
		.unmarshal().gzip()
		.to("checkpoint:bean?message=Mensagem descomprimida com sucesso...")
		.unmarshal("ativos")
		.to("checkpoint:bean?message=Lista de objetos montada...")
		.to("checkpoint:bean?message=Limpando Estrutura Intermediária...")
		.to("mybatis:cleanDatabase?statementType=Delete")
		.to("checkpoint:bean?message=Estrutura Intermediária limpa com sucesso...")
		.to("checkpoint:bean?message=Enviando para as rotas de validação LMS...")
		.multicast()
		.parallelProcessing()
		.to("direct:anetFormatter");
	}
}
