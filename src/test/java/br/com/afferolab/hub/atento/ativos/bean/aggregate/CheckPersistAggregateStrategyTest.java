package br.com.afferolab.hub.atento.ativos.bean.aggregate;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.tcc.integracao.AggregateStrategy;
import br.com.tcc.integracao.util.RouteConstants;

public class CheckPersistAggregateStrategyTest {
	
	CamelContext ctx = null;
	
	AggregateStrategy aggregate = new AggregateStrategy();
	
	@Before
	public void init() {
		ctx = new DefaultCamelContext();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void aggregate_should_throw_illegal_argument_exception_when_newExchange_is_null() {
		// Arrange
		Exchange oldExchange = null;
		Exchange newExchange = null;
		
		// Act
		aggregate.aggregate(oldExchange, newExchange);
	}
	
	@Test
	public void aggregate_should_return_newExchange_when_oldExchange_is_null() {
		// Arrange
		Exchange oldExchange = null;
		Exchange newExchange = new DefaultExchange(ctx);
		newExchange.getIn().setHeader(RouteConstants.SYSTEM_FAIL, "GR");
		newExchange.getIn().setBody("true");
		
		// Act
		Exchange exchange = aggregate.aggregate(oldExchange, newExchange);
		
		// Assert
		Assert.assertNotNull(exchange);
		Assert.assertEquals("GR", exchange.getIn().getHeader(RouteConstants.ROUTE_GR_FAIL));
		Assert.assertEquals("true", exchange.getIn().getBody());
	}
	
	@Test
	public void aggregate_should_return_oldExchange_with_body_true_and_header_fail_empty_when_newExchange_have_body_true() {
		// Arrange
		Exchange oldExchange = new DefaultExchange(ctx);
		oldExchange.getIn().setBody("true");
		
		Exchange newExchange = new DefaultExchange(ctx);
		newExchange.getIn().setBody("true");
		
		// Act
		Exchange exchange = aggregate.aggregate(oldExchange, newExchange);
		
		// Assert
		Assert.assertNotNull(exchange);
		Assert.assertNull(exchange.getIn().getHeader(RouteConstants.ROUTE_GR_FAIL));
		Assert.assertNull(exchange.getIn().getHeader(RouteConstants.ROUTE_ANET_FAIL));
		Assert.assertEquals("true", exchange.getIn().getBody());
	}
	
	@Test
	public void aggregate_should_return_oldExchange_with_body_false_and_header_fail_not_empty_when_newExchange_have_body_false() {
		// Arrange
		Exchange oldExchange = new DefaultExchange(ctx);
		oldExchange.getIn().setBody("true");
		
		Exchange newExchange = new DefaultExchange(ctx);
		newExchange.getIn().setHeader(RouteConstants.SYSTEM_FAIL, "GR");
		newExchange.getIn().setBody("false");
		
		// Act
		Exchange exchange = aggregate.aggregate(oldExchange, newExchange);
		
		// Assert
		Assert.assertNotNull(exchange);
		Assert.assertEquals("GR", exchange.getIn().getHeader(RouteConstants.ROUTE_GR_FAIL));
		Assert.assertEquals("false", exchange.getIn().getBody());
	}
	
	@Test
	public void aggregate_should_return_oldExchange_with_body_false_and_header_fail_not_empty_when_newExchange_have_body_true() {
		// Arrange
		Exchange oldExchange = new DefaultExchange(ctx);
		oldExchange.getIn().setHeader(RouteConstants.ROUTE_GR_FAIL, "GR");
		oldExchange.getIn().setBody("false");
		
		Exchange newExchange = new DefaultExchange(ctx);
		newExchange.getIn().setBody("true");
		
		// Act
		Exchange exchange = aggregate.aggregate(oldExchange, newExchange);
		
		// Assert
		Assert.assertNotNull(exchange);
		Assert.assertEquals("GR", exchange.getIn().getHeader(RouteConstants.ROUTE_GR_FAIL));
		Assert.assertEquals("false", exchange.getIn().getBody());
	}
}
