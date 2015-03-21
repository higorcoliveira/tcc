package br.com.tcc.integracao.bean.lms;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.camel.Handler;

import br.com.tcc.integracao.entity.xml.Ativo;
import br.com.tcc.integracao.entity.xml.Ativos;
import br.com.tcc.integracao.util.RoleGroupUtil;

public class LMSFilter {

	@Handler
	public List<Ativo> filterMessage(Ativos ativos) {
		Set<Ativo> listAtivos = ativos.getAtivos();
		
		List<Ativo> listAtivosAnet = new ArrayList<Ativo>(); 
		
		for (Ativo ativo : listAtivos) {
			if (RoleGroupUtil.getRoleGroupLms().contains(RoleGroupUtil.extractRoleGroup(ativo.getRoleGroup()))) {
				listAtivosAnet.add(ativo);
			}
		}
		return listAtivosAnet;
	}
}
