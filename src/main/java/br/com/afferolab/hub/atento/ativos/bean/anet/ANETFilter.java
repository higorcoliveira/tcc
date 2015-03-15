package br.com.afferolab.hub.atento.ativos.bean.anet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.camel.Handler;

import br.com.afferolab.hub.atento.ativos.entity.xml.Ativo;
import br.com.afferolab.hub.atento.ativos.entity.xml.Ativos;
import br.com.afferolab.hub.atento.ativos.util.RoleGroupUtil;

public class ANETFilter {

	@Handler
	public List<Ativo> filterMessage(Ativos ativos) {
		Set<Ativo> listAtivos = ativos.getAtivos();
		
		List<Ativo> listAtivosAnet = new ArrayList<Ativo>(); 
		
		for (Ativo ativo : listAtivos) {
			if (RoleGroupUtil.getRoleGroupAnet().contains(RoleGroupUtil.extractRoleGroup(ativo.getRoleGroup()))) {
				listAtivosAnet.add(ativo);
			}
		}
		return listAtivosAnet;
	}
}
