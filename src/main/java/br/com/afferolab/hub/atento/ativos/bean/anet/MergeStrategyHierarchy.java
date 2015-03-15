package br.com.afferolab.hub.atento.ativos.bean.anet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import br.com.afferolab.hub.atento.ativos.entity.xml.Ativo;

public class MergeStrategyHierarchy implements AggregationStrategy {

	@Override
	@SuppressWarnings("unchecked")
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		List<Ativo> hierarchiesDB = newExchange.getIn().getBody(List.class);
		List<Ativo> hierarchiesXML = oldExchange.getIn().getBody(List.class);

		hierarchiesXML = removeExistingHierarchies(hierarchiesXML, convertToMap(hierarchiesDB));

		oldExchange.getIn().setBody(hierarchiesXML);

		return oldExchange;
	}
	
	public List<Ativo> removeExistingHierarchies(List<Ativo> hierarchiesXML, Map<String, Ativo> hierarchiesDB) {
		Map<String, Ativo> hierarchiesToInsert = new HashMap<>();
		
		for (Ativo ativo : hierarchiesXML) {
			if (!hierarchiesDB.containsKey(ativo.getRoleGroup())) {
				hierarchiesToInsert.put(ativo.getRoleGroup(), ativo);
			}
		}
		List<Ativo> listToinsert = new ArrayList<Ativo>(hierarchiesToInsert.values());
		
		return listToinsert;
	}

	private Map<String, Ativo> convertToMap(List<Ativo> areas) {
		Map<String, Ativo> mapHierarchies = new HashMap<>();
		for (Ativo area : areas) {
			mapHierarchies.put(area.getRoleGroup(), area);
		}
		return mapHierarchies;
	}
}
