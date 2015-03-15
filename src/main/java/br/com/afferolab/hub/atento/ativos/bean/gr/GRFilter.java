package br.com.afferolab.hub.atento.ativos.bean.gr;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.camel.Handler;

import br.com.afferolab.hub.atento.ativos.entity.xml.Ativo;
import br.com.afferolab.hub.atento.ativos.entity.xml.Ativos;
import br.com.afferolab.hub.atento.ativos.util.RoleGroupUtil;

public class GRFilter {
	
	@Handler
	public List<Ativo> filterMessage(Ativos ativos) {
		Set<Ativo> setAtivos = ativos.getAtivos();
		
		List<Ativo> listAtivosGR = new ArrayList<>(); 
		
		for (Ativo ativo : setAtivos) {
			if (RoleGroupUtil.getRoleGroupGR().contains(RoleGroupUtil.extractRoleGroup(ativo.getRoleGroup()))) {
				listAtivosGR.add(ativo);
			}
		}
		return listAtivosGR;
	}
}
