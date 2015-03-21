package br.com.tcc.integracao.route;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.spi.BrowsableEndpoint;
import org.apache.camel.test.junit4.CamelSpringJUnit4ClassRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import br.com.afferolab.utils.util.TestUtil;
import br.com.tcc.integracao.entity.xml.Ativo;
import br.com.tcc.integracao.entity.xml.Ativos;
import br.com.tcc.integracao.test.util.CamelTestTemplate;

@RunWith(CamelSpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UnmarshallRouteRegressionTest extends CamelTestTemplate {

	private static final String UNMARSHALL_ROUTE_NAME = "UnmarshallRoute";
	private static final String XML_FOLDER = "xml/";
	private static final String XML_VALID = "re.xml";
	private static final String XML_INVALID = "invalid.xml";
	
	@Override
	public void init() throws Exception {
		RouteDefinition route = getCamelContext().getRouteDefinition(UNMARSHALL_ROUTE_NAME);
		
		route.adviceWith(getCamelContext(), new AdviceWithRouteBuilder() {
			@Override
			public void configure() throws Exception {
				
				interceptSendToEndpoint("checkpoint:error*")
				  .skipSendToOriginalEndpoint()
				  .to("mock:error");
				
				interceptSendToEndpoint("checkpoint:bean*")
				  .skipSendToOriginalEndpoint()
				  .to("mock:checkpoint");
				
				interceptSendToEndpoint("direct:*")
				  .skipSendToOriginalEndpoint()
				  .to("mock:direct");
			}
		});
	}
	
	@Test
	public void unmarshallRoute_should_throw_exception_when_xml_is_invalid() throws Exception {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_INVALID);
		byte[] bodyCompressed = TestUtil.compress(body);
		
		// Act
		startProducerTemplate().sendBody("activemq:queue:{{activemq.queue.name}}", bodyCompressed);
		
		BrowsableEndpoint be = getCamelContext().getEndpoint("mock:error", BrowsableEndpoint.class);
	    List<Exchange> list = be.getExchanges();
	    Thread.sleep(2000);
	    
	    // Assert
	    Assert.assertEquals(1, list.size());
	}
	
	@Test
	public void unmarshallRoute_should_send_to_route_when_xml_is_valid() throws Exception {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_VALID);
		String bodyModified = MessageFormat.format(body, "123456");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		startProducerTemplate().sendBody("activemq:queue:{{activemq.queue.name}}", bodyCompressed);
		
		BrowsableEndpoint be = getCamelContext().getEndpoint("mock:direct", BrowsableEndpoint.class);
	    List<Exchange> list = be.getExchanges();
	    Thread.sleep(2000);
	    Ativos ativos = (Ativos) list.get(0).getIn().getBody();
	    
	    Set<Ativo> ativo = ativos.getAtivos();
	    
	    // Assert
	    Assert.assertNotNull(ativo);
	    Assert.assertEquals(1, ativo.size());
	}
}
