package br.com.tcc.integracao.route;

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

import br.com.tcc.integracao.test.util.CamelTestTemplate;
import br.com.tcc.integracao.util.RouteConstants;

@RunWith(CamelSpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class CheckForCompletedRouteRegressionTest extends CamelTestTemplate {

	private static final String CHECK_FOR_PERSIST_ROUTE_NAME = "CheckForCompletedRoute";
	private static final String ENDPOINT_CONSUMER = "seda:checkForCompletedRoute";
	
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
				
				interceptSendToEndpoint("checkpoint:success*")
				  .skipSendToOriginalEndpoint()
				  .to("mock:checkpointSuccess");
			}
		});
	}
	
	@Test
	public void checkForCompletedRoute_should_stop_when_message_has_body_false() throws CamelExecutionException, Exception{
		
		// Arrange
		Map<String, Object> header1 = new HashMap<>();
		header1.put(RouteConstants.HEADER_FINISHED, "finished");
		
		Map<String, Object> header2 = new HashMap<>();
		header2.put(RouteConstants.HEADER_FINISHED, "finished");
		
		Map<String, Object> header3 = new HashMap<>();
		header3.put(RouteConstants.HEADER_FINISHED, "finished");
		
		// Act
		startProducerTemplate().sendBodyAndHeaders(ENDPOINT_CONSUMER, "false", header1);
		startProducerTemplate().sendBodyAndHeaders(ENDPOINT_CONSUMER, "false", header2);
		startProducerTemplate().sendBodyAndHeaders(ENDPOINT_CONSUMER, "false", header3);
		
		BrowsableEndpoint be = getCamelContext().getEndpoint("mock:error", BrowsableEndpoint.class);
	    List<Exchange> list = be.getExchanges();
	    Thread.sleep(1000);
	    
		// Assert
	    Assert.assertEquals(1, list.size());
	}
	
	@Test
	public void checkForCompletedRoute_should_stop_when_message_has_body_null() throws CamelExecutionException, Exception{
		
		// Arrange
		Map<String, Object> header1 = new HashMap<>();
		header1.put(RouteConstants.HEADER_FINISHED, "finished");
		
		Map<String, Object> header2 = new HashMap<>();
		header2.put(RouteConstants.HEADER_FINISHED, "finished");
		
		Map<String, Object> header3 = new HashMap<>();
		header3.put(RouteConstants.HEADER_FINISHED, "finished");
		
		// Act
		startProducerTemplate().sendBodyAndHeaders(ENDPOINT_CONSUMER, null, header1);
		startProducerTemplate().sendBodyAndHeaders(ENDPOINT_CONSUMER, null, header2);
		startProducerTemplate().sendBodyAndHeaders(ENDPOINT_CONSUMER, null, header3);
		
		BrowsableEndpoint be = getCamelContext().getEndpoint("mock:error", BrowsableEndpoint.class);
	    List<Exchange> list = be.getExchanges();
	    Thread.sleep(1000);
	    
		// Assert
	    Assert.assertEquals(1, list.size());
	}

	@Test
	public void checkForCompletedRoute_should_success_when_message_has_body_true() throws CamelExecutionException, Exception{
		
		// Arrange
		Map<String, Object> header1 = new HashMap<>();
		header1.put(RouteConstants.HEADER_FINISHED, "finished");
		
		Map<String, Object> header2 = new HashMap<>();
		header2.put(RouteConstants.HEADER_FINISHED, "finished");
		
		Map<String, Object> header3 = new HashMap<>();
		header3.put(RouteConstants.HEADER_FINISHED, "finished");
		
		// Act
		startProducerTemplate().sendBodyAndHeaders(ENDPOINT_CONSUMER, "true", header1);
		startProducerTemplate().sendBodyAndHeaders(ENDPOINT_CONSUMER, "true", header2);
		startProducerTemplate().sendBodyAndHeaders(ENDPOINT_CONSUMER, "true", header3);
		
		BrowsableEndpoint be = getCamelContext().getEndpoint("mock:checkpointSuccess", BrowsableEndpoint.class);
	    List<Exchange> list = be.getExchanges();
	    Thread.sleep(1000);
	    
		// Assert
	    Assert.assertEquals(1, list.size());
	}
}
