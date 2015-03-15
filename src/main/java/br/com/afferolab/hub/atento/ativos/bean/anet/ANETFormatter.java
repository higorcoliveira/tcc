package br.com.afferolab.hub.atento.ativos.bean.anet;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.Handler;
import org.apache.commons.lang.StringUtils;

import br.com.afferolab.hub.atento.ativos.bean.CommonFormatter;
import br.com.afferolab.hub.atento.ativos.entity.xml.Ativo;

import com.google.common.base.Preconditions;
import com.les.crypt.core.TranspositionCipherAlgorithm;

public class ANETFormatter extends CommonFormatter {
	
	private static final String ANET_PASSWORD_KEY = "{{anet.password.key}}";
	
	@Handler
	public List<Ativo> formatData(List<Ativo> ativos, CamelContext context) throws Exception {

		List<Ativo> listDataFormatted = new ArrayList<>();
		String anetPasswordKey = context.resolvePropertyPlaceholders(ANET_PASSWORD_KEY);
		
		for (Ativo ativo : ativos) {
			ativo.setEmail(formatEmail(ativo));
			ativo.setPassword(formatPassword(ativo, anetPasswordKey));
		}
		
		listDataFormatted.addAll(ativos);

		return listDataFormatted;
	}
	
	public String formatPassword(Ativo ativo, String key) {
		Preconditions.checkArgument(StringUtils.isNotBlank(ativo.getPassword()), "password nao pode ser nula/vazia.");
		
		TranspositionCipherAlgorithm tca = new TranspositionCipherAlgorithm(key);
		return tca.crypt(ativo.getPassword());
	}
}
