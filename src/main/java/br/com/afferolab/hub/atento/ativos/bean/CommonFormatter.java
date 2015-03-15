package br.com.afferolab.hub.atento.ativos.bean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.afferolab.hub.atento.ativos.entity.xml.Ativo;

public abstract class CommonFormatter {
	
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-]{2,63})+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public String formatEmail(Ativo ativo) {
		String emailFormatted = ativo.getEmail();
		
		if (emailFormatted == null || emailFormatted.isEmpty()){
			emailFormatted = "atento_" + ativo.getRe() + "@atento.com.br";
		}

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(emailFormatted);
		
		if (!matcher.matches()) {
			emailFormatted = "GR" + ativo.getRe() + "@atento.com.br";
		}
		return emailFormatted;
	}
}
