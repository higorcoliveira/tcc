package br.com.tcc.integracao;

import java.io.InputStream;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;

public class XMLValidation {

	@Handler
	public void validate(Exchange exchange) throws Exception {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("ativos.xsd");
		String body = exchange.getIn().getBody(String.class);
		JAXBUtil.validate(is, body);
	}
}	
