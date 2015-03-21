package br.com.afferolab.hub.atento.ativos.route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.CamelExecutionException;
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

import br.com.afferolab.hub.atento.util.CamelTestTemplate;
import br.com.tcc.integracao.util.RouteConstants;

@RunWith(CamelSpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class CheckForPersistRouteRegressionTest extends CamelTestTemplate {

	private static final String CHECK_FOR_PERSIST_ROUTE_NAME = "CheckForPersistRoute";
	private static final String ENDPOINT_CONSUMER = "seda:checkForPersistRoute";
	
	@Override
	public void init() throws Exception {
		RouteDefinition route = getCamelContext().getRouteDefinition(CHECK_FOR_PERSIST_ROUTE_NAME);
		
		route.adviceWith(getCamelContext(), new AdviceWithRouteBuilder() {
			@Override
			public void configure() throws Exception {
				
				interceptSendToEndpoint("direct:*")
				  .skipSendToOriginalEndpoint()
				  .to("mock:direct");
				
				interceptSendToEndpoint("checkpoint:error*")
				  .skipSendToOriginalEndpoint()
				  .to("mock:error");
				
				interceptSendToEndpoint("checkpoint:bean*")
				  .skipSendToOriginalEndpoint()
				  .to("mock:checkpoint");
			}
		});
	}
	
	@Test
	public void checkForPersist_route_should_stop_route_when_first_message_has_body_true_and_second_and_third_messages_has_body_false() throws CamelExecutionException, Exception {
		// Arrange
		Map<String, Object> headers1 = new HashMap<>();
		headers1.put(RouteConstants.HEADER_STATUS, "true");
		
		Map<String, Object> headers2 = new HashMap<>();
		headers2.put(RouteConstants.HEADER_STATUS, "true");
		headers2.put(RouteConstants.SYSTEM_FAIL, "GR");
		
		Map<String, Object> headers3 = new HashMap<>();
		headers3.put(RouteConstants.HEADER_STATUS, "true");
		headers3.put(RouteConstants.SYSTEM_FAIL, "Skillo");
		
		// Act
		startProducerTemplate().sendBodyAndHeaders(ENDPOINT_CONSUMER, "true", headers1);
		startProducerTemplate().sendBodyAndHeaders(ENDPOINT_CONSUMER, "false", headers2);
		startProducerTemplate().sendBodyAndHeaders(ENDPOINT_CONSUMER, "false", headers3);
		stopProducerTemplate();
		
		BrowsableEndpoint be = getCamelContext().getEndpoint("mock:error", BrowsableEndpoint.class);
	    List<Exchange> list = be.getExchanges();
	    Thread.sleep(1000);
		
		// Assert
	    Assert.assertEquals(1, list.size());
	}
	
	@Test
	public void checkForPersist_route_should_stop_route_when_first_message_has_body_false_and_second_and_third_has_body_true() throws CamelExecutionException, Exception {
		// Arrange
		Map<String, Object> headers1 = new HashMap<>();
		headers1.put(RouteConstants.HEADER_STATUS, "true");
		headers1.put(RouteConstants.SYSTEM_FAIL, "ANET");
		
		Map<String, Object> headers2 = new HashMap<>();
		headers2.put(RouteConstants.HEADER_STATUS, "true");
		
		Map<String, Object> headers3 = new HashMap<>();
		headers3.put(RouteConstants.HEADER_STATUS, "true");
		
		// Act
		startProducerTemplate().sendBodyAndHeaders(ENDPOINT_CONSUMER, "false", headers1);
		startProducerTemplate().sendBodyAndHeaders(ENDPOINT_CONSUMER, "true", headers2);
		startProducerTemplate().sendBodyAndHeaders(ENDPOINT_CONSUMER, "true", headers3);
		stopProducerTemplate();
		
		BrowsableEndpoint be = getCamelContext().getEndpoint("mock:error", BrowsableEndpoint.class);
	    List<Exchange> list = be.getExchanges();
	    Thread.sleep(1000);
		
		// Assert
	    Assert.assertEquals(1, list.size());
	}
	
	@Test
	public void checkForPersist_route_should_stop_route_when_all_messages_has_body_false() throws CamelExecutionException, Exception {
		// Arrange
		Map<String, Object> headers1 = new HashMap<>();
		headers1.put(RouteConstants.HEADER_STATUS, "true");
		headers1.put(RouteConstants.SYSTEM_FAIL, "GR");
		
		Map<String, Object> headers2 = new HashMap<>();
		headers2.put(RouteConstants.HEADER_STATUS, "true");
		headers2.put(RouteConstants.SYSTEM_FAIL, "ANET");
		
		Map<String, Object> headers3 = new HashMap<>();
		headers3.put(RouteConstants.HEADER_STATUS, "true");
		headers3.put(RouteConstants.SYSTEM_FAIL, "Skillo");
		
		// Act
		startProducerTemplate().sendBodyAndHeaders(ENDPOINT_CONSUMER, "false", headers1);
		startProducerTemplate().sendBodyAndHeaders(ENDPOINT_CONSUMER, "false", headers2);
		startProducerTemplate().sendBodyAndHeaders(ENDPOINT_CONSUMER, "false", headers3);
		stopProducerTemplate();
		
		BrowsableEndpoint be = getCamelContext().getEndpoint("mock:error", BrowsableEndpoint.class);
	    List<Exchange> list = be.getExchanges();
	    Thread.sleep(1000);
		
		// Assert
	    Assert.assertEquals(1, list.size());
	}
	
	@Test
	public void checkForPersist_route_should_proceed_route_when_all_messages_has_body_true() throws CamelExecutionException, Exception {
		// Arrange
		Map<String, Object> headers1 = new HashMap<>();
		headers1.put(RouteConstants.HEADER_STATUS, "true");
		
		Map<String, Object> headers2 = new HashMap<>();
		headers2.put(RouteConstants.HEADER_STATUS, "true");
		
		Map<String, Object> headers3 = new HashMap<>();
		headers3.put(RouteConstants.HEADER_STATUS, "true");
		
		// Act
		startProducerTemplate().sendBodyAndHeaders(ENDPOINT_CONSUMER, "true", headers1);
		startProducerTemplate().sendBodyAndHeaders(ENDPOINT_CONSUMER, "true", headers2);
		startProducerTemplate().sendBodyAndHeaders(ENDPOINT_CONSUMER, "true", headers3);
		stopProducerTemplate();
		
		BrowsableEndpoint be = getCamelContext().getEndpoint("mock:direct", BrowsableEndpoint.class);
	    List<Exchange> list = be.getExchanges();
	    Thread.sleep(1000);
		
		// Assert
	    Assert.assertEquals(3, list.size());
	}
}
