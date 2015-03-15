package br.com.afferolab.hub.atento.ativos.bean;

import java.io.InputStream;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;

import br.com.afferolab.utils.http.exception.XMLValidationException;
import br.com.afferolab.utils.util.JAXBUtil;

public class XMLValidation {

	@Handler
	public void validate(Exchange exchange) throws XMLValidationException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("ativos.xsd");
		String body = exchange.getIn().getBody(String.class);
		JAXBUtil.validate(is, body);
	}
}
	
	
