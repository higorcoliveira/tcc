package br.com.afferolab.hub.atento.ativos.bean.gr;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import br.com.afferolab.hub.atento.ativos.entity.xml.Ativo;
import br.com.afferolab.hub.atento.ativos.entity.xml.Ativos;

public class GRFilterTest {
	
	GRFilter beanFilter = new GRFilter();
	
	@Test
	public void filterMessage_should_return_empty_list() {
		// Arrange
		Ativo ativo2 = new Ativo();
		ativo2.setRoleGroup("OPERADOR (O1)");
		
		Set<Ativo> listAtivos = new HashSet<>();
		listAtivos.add(ativo2);
		
		Ativos ativos = new Ativos();
		ativos.setAtivos(listAtivos);
		
		// Act
		List<Ativo> actual = beanFilter.filterMessage(ativos);
		
		// Assert
		Assert.assertNotNull(actual);
		Assert.assertEquals(0, actual.size());
	}
	
	@Test
	public void filterMessage_should_return_list_with_one_element_when_role_group_is_from_GR() {
		// Arrange
		Ativo ativo = new Ativo();
		ativo.setRoleGroup("GERENCIA (M5)");
		
		Set<Ativo> listAtivos = new HashSet<>();
		listAtivos.add(ativo);
		
		Ativos ativos = new Ativos();
		ativos.setAtivos(listAtivos);
		
		// Act
		List<Ativo> actual = beanFilter.filterMessage(ativos);
		
		// Assert
		Assert.assertNotNull(actual);
		Assert.assertEquals(1, actual.size());
	}
	
	@Test
	public void filterMessage_should_return_list_with_one_element_when_role_group_is_from_GR_and_other_not() {
		// Arrange
		Ativo ativo1 = new Ativo();
		ativo1.setRoleGroup("GERENCIA (M3)");
		
		Ativo ativo2 = new Ativo();
		ativo2.setRoleGroup("OPERADOR (O1)");
		
		Set<Ativo> listAtivos = new HashSet<>();
		listAtivos.add(ativo1);
		listAtivos.add(ativo2);
		
		Ativos ativos = new Ativos();
		ativos.setAtivos(listAtivos);
		
		// Act
		List<Ativo> actual = beanFilter.filterMessage(ativos);
		
		// Assert
		Assert.assertNotNull(actual);
		Assert.assertEquals(1, actual.size());
		Assert.assertEquals("GERENCIA (M3)", actual.get(0).getRoleGroup());
	}
}
