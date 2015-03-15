package br.com.afferolab.hub.atento.ativos.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.apache.camel.Handler;
import org.apache.commons.beanutils.BeanUtils;

import br.com.afferolab.hub.atento.ativos.entity.xml.Ativo;
import br.com.afferolab.hub.atento.ativos.entity.xml.Ativos;

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
