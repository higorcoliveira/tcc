package br.com.tcc.integracao;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;

public class BatchSizePredicate implements Predicate {

	private int size;

	public BatchSizePredicate(int size) {
		this.size = size;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean matches(Exchange exchange) {
		Boolean match = false;
		
		if (exchange != null) {
			List<Object> list = exchange.getIn().getBody(List.class);
			
			if (!list.isEmpty() && list.size() == size) {
				match = true;
			}
		}
		
		return match;
	}
}