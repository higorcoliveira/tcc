package br.com.afferolab.hub.atento.ativos.bean.skillo;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.Handler;
import org.apache.commons.lang.StringUtils;

import br.com.afferolab.hub.atento.ativos.entity.xml.Ativo;

public class SkilloFormatter {

	@Handler
	public List<Ativo> formatData(List<Ativo> ativos, CamelContext context) throws Exception {

		List<Ativo> listDataFormatted = new ArrayList<>();
		
		for (Ativo ativo : ativos) {
			ativo.setPassword(ativo.getCpf());
			ativo.setFunction(formatFunction(ativo));
		}
		
		listDataFormatted.addAll(ativos);

		return listDataFormatted;
	}
	
	String formatFunction(Ativo ativo) {
		String function = ativo.getRoleGroup();
		
		if (StringUtils.isNotBlank(ativo.getFunctionArea())) {
			function = String.format("%s - %s", ativo.getFunctionArea(), ativo.getRoleGroup());
		}
		
		return function;
	}
}