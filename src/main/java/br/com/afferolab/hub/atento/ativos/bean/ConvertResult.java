package br.com.afferolab.hub.atento.ativos.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.Handler;

public abstract class ConvertResult<T> {

	@Handler
	public List<T> convert(List<HashMap<String, Object>> result, CamelContext context) throws Exception {
		List<T> elements = new ArrayList<>();
		
		for (HashMap<String, Object> mapResult : result) {
			elements.add(assembly(mapResult, context));
		}
		
		return elements;
	}
	
	protected abstract T assembly(HashMap<String, Object> mapResult, CamelContext context) throws Exception;
}