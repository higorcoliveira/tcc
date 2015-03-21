package br.com.tcc.integracao;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import br.com.tcc.integracao.entity.xml.Ativo;

public class ListAggregationStrategy implements AggregationStrategy {

	@Override
	@SuppressWarnings("unchecked")
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		Message newIn = newExchange.getIn();
		Ativo newBody = (Ativo) newIn.getBody();
		
		List<Ativo> list = null;
		
		if (oldExchange == null) {
			list = new ArrayList<>();
			list.add(newBody);
            newIn.setBody(list);
			
			return newExchange;
		} else {
			Message in = oldExchange.getIn();
			list = in.getBody(List.class);
			list.add(newBody);
			
			return oldExchange;
		}
	}
}