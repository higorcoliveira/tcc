package br.com.afferolab.hub.atento.ativos.bean.gr;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Handler;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import br.com.afferolab.hub.atento.ativos.bean.CommonFormatter;
import br.com.afferolab.hub.atento.ativos.entity.xml.Ativo;

import com.google.common.base.Preconditions;

public class GRFormatter extends CommonFormatter {

	@Handler
	public List<Ativo> formatData(List<Ativo> ativos) {

		List<Ativo> listDataFormatted = new ArrayList<>();

		for (Ativo ativo : ativos) {
			ativo.setEmail(formatEmail(ativo));
			ativo.setPassword(formatPassword(ativo));
			ativo.setContract(formatContract(ativo));
		}
		listDataFormatted.addAll(ativos);

		return listDataFormatted;
	}

	public String formatPassword(Ativo ativo) {
		String password = ativo.getPassword() == null ? "" : ativo.getPassword();
		byte[] passwordEncoded = new byte[1];
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			passwordEncoded = Base64.encodeBase64(md.digest(password.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.getMessage();
		}
		return new String(passwordEncoded);
	}

	public String formatContract(Ativo ativo) {
		String contract = ativo.getContract();
		Preconditions.checkArgument(!StringUtils.isEmpty(contract), "Contrato deve ser diferente de null ou vazio");
		
		String contractFormatted = contract.substring(0, contract.indexOf("-")).trim();
		
		return contractFormatted;
	}
}
