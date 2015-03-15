package br.com.afferolab.hub.atento.ativos.bean;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import br.com.afferolab.hub.atento.ativos.util.RouteConstants;

import com.google.common.base.Preconditions;

public class AggregateStrategy implements AggregationStrategy {

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		Preconditions.checkArgument(newExchange != null, "newExchange deve ser diferente de null.");
		
		if (oldExchange == null) {
			setSystemFailHeader(newExchange);
			return newExchange;
		}

		String success = (String) newExchange.getIn().getBody();
		boolean bodySuccess = Boolean.parseBoolean(success);

		if (!bodySuccess) {
			oldExchange.getIn().setBody(newExchange.getIn().getBody());
			oldExchange.getIn().setHeader(RouteConstants.SYSTEM_FAIL, newExchange.getIn().getHeader(RouteConstants.SYSTEM_FAIL));
			
			setSystemFailHeader(oldExchange);
		}
		return oldExchange;
	}
	
	public void setSystemFailHeader(Exchange newExchange) {
		String systemFail = (String) newExchange.getIn().getHeader(RouteConstants.SYSTEM_FAIL);
		
		if (systemFail == null) {
			systemFail = "";
		}
		
		switch (systemFail) {
			case RouteConstants.GR_FAIL:
				newExchange.getIn().setHeader(RouteConstants.ROUTE_GR_FAIL, systemFail);
				break;
			case RouteConstants.ANET_FAIL:
				newExchange.getIn().setHeader(RouteConstants.ROUTE_ANET_FAIL, systemFail);
				break;
			case RouteConstants.SKILLO_FAIL:
				newExchange.getIn().setHeader(RouteConstants.ROUTE_SKILLO_FAIL, systemFail);
				break;
			default:
				break;
		}
	}
}
