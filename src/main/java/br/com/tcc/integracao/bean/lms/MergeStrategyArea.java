package br.com.tcc.integracao.bean.lms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import br.com.tcc.integracao.entity.xml.Ativo;

public class MergeStrategyArea implements AggregationStrategy {

	@Override
	@SuppressWarnings("unchecked")
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		List<Ativo> areasDB = newExchange.getIn().getBody(List.class);
		List<Ativo> areasXML = oldExchange.getIn().getBody(List.class);

		areasXML = removeExistingAreas(areasXML, convertToMap(areasDB));

		oldExchange.getIn().setBody(areasXML);

		return oldExchange;
	}
	
	public List<Ativo> removeExistingAreas(List<Ativo> areasXML, Map<String, Ativo> areasDB) {
		Map<String, Ativo> areasToInsert = new HashMap<>();
		
		for (Ativo ativo : areasXML) {
			if (!areasDB.containsKey(ativo.getSite())) {
				areasToInsert.put(ativo.getSite(), ativo);
			}
		}
		List<Ativo> listToinsert = new ArrayList<Ativo>(areasToInsert.values());
		
		return listToinsert;
	}

	private Map<String, Ativo> convertToMap(List<Ativo> areas) {
		Map<String, Ativo> mapAreas = new HashMap<>();
		for (Ativo area : areas) {
			mapAreas.put(area.getSite(), area);
		}
		return mapAreas;
	}
}
