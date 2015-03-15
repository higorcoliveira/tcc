package br.com.afferolab.hub.atento.ativos.bean.gr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import br.com.afferolab.hub.atento.ativos.entity.xml.Ativo;

public class MergeStrategyCostCenter implements AggregationStrategy {

	@Override
	@SuppressWarnings("unchecked")
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		
		List<Ativo> costCentersDB = newExchange.getIn().getBody(List.class);
		List<Ativo> costCentersXML = oldExchange.getIn().getBody(List.class);
		
		costCentersXML = removeExistedCostCenters(costCentersXML, convertToMap(costCentersDB));
		
		oldExchange.getIn().setBody(costCentersXML);
		
		return oldExchange;
	}
	
	public List<Ativo> removeExistedCostCenters(List<Ativo> costCentersXML, Map<Integer, Ativo> costCentersDB) {
		Map<Integer, Ativo> costCenterToInsert = new HashMap<>();
		
		for (Ativo ativo : costCentersXML) {
			if (!costCentersDB.containsKey(ativo.getCostCenter())) {
				costCenterToInsert.put(ativo.getCostCenter(), ativo);
			}
		}
		List<Ativo> listToinsert = new ArrayList<Ativo>(costCenterToInsert.values());
		
		return listToinsert;
	}
	
	private Map<Integer, Ativo> convertToMap(List<Ativo> costCenters) {
		Map<Integer, Ativo> mapCostCenters = new HashMap<>();
		for (Ativo costCenter : costCenters) {
			mapCostCenters.put(costCenter.getCostCenter(), costCenter);
		}
		return mapCostCenters;
	}
}
