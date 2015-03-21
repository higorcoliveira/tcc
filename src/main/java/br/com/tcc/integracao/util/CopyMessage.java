package br.com.tcc.integracao.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import br.com.tcc.integracao.entity.xml.Ativo;
import br.com.tcc.integracao.entity.xml.Ativos;

public class CopyMessage {

	@Handler
	public Ativos copyMessage(Ativos ativos) throws InstantiationException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		
		Set<Ativo> oldSet = ativos.getAtivos();
		
		Ativos newAtivos = new Ativos();
		
		for (Ativo ativo : oldSet) {
			Ativo newAtivo = (Ativo) BeanUtils.cloneBean(ativo);
			
			newAtivos.add(newAtivo);
		}
		
		return newAtivos;
	}
}
