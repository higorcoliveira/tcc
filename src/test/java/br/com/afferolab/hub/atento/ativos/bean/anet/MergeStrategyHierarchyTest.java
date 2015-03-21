package br.com.afferolab.hub.atento.ativos.bean.anet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import br.com.tcc.integracao.bean.lms.MergeStrategyHierarchy;
import br.com.tcc.integracao.entity.xml.Ativo;

public class MergeStrategyHierarchyTest {
	
	MergeStrategyHierarchy bean = new MergeStrategyHierarchy();
	
	/*
	 * metodo testado removeExistingHierarchies(List<Ativo> hierarchiesXML, Map<String, Ativo> hierarchiesDB)
	 */
	
	@Test
	public void removeExistingHierarchies_should_return_emptyList_when_hierarchy_have_in_db_and_xml() {
		// Arrange
		Ativo ativo = new Ativo();
		ativo.setRoleGroup("Atendentes (O1)");
		List<Ativo> hierarchiesXML = new ArrayList<>();
		hierarchiesXML.add(ativo);
		
		Map<String, Ativo> hierarchiesDB = new HashMap<>();
		hierarchiesDB.put(ativo.getRoleGroup(), ativo);
		
		// Act
		List<Ativo> actual = bean.removeExistingHierarchies(hierarchiesXML, hierarchiesDB);
		
		// Assert
		Assert.assertTrue(actual.isEmpty());
	}
	
	@Test
	public void removeExistingHierarchies_should_return_list_with_one_hierarchy_when_hierarchy_not_have_in_db_and_have_in_xml() {
		// Arrange
		Ativo ativo1 = new Ativo();
		ativo1.setRoleGroup("Atendentes (O1)");
		List<Ativo> hierarchiesXML = new ArrayList<>();
		hierarchiesXML.add(ativo1);
		
		Ativo ativo2 = new Ativo();
		ativo2.setRoleGroup("Superintendência (M4)");
		Map<String, Ativo> hierarchiesDB = new HashMap<>();
		hierarchiesDB.put(ativo2.getRoleGroup(), ativo2);
		
		// Act
		List<Ativo> actual = bean.removeExistingHierarchies(hierarchiesXML, hierarchiesDB);
		
		// Assert
		Assert.assertFalse(actual.isEmpty());
		Assert.assertEquals(ativo1.getRoleGroup(), actual.get(0).getRoleGroup());
	}
	
	@Test
	public void removeExistingHierarchies_should_return_list_with_one_hierarchy_when_db_is_empty_and_hierarchy_have_in_xml() {
		// Arrange
		Ativo ativo1 = new Ativo();
		ativo1.setRoleGroup("Atendentes (O1)");
		List<Ativo> hierarchiesXML = new ArrayList<>();
		hierarchiesXML.add(ativo1);
		
		Map<String, Ativo> hierarchiesDB = new HashMap<>();
		
		// Act
		List<Ativo> actual = bean.removeExistingHierarchies(hierarchiesXML, hierarchiesDB);
		
		// Assert
		Assert.assertEquals(1, actual.size());
		Assert.assertEquals(ativo1.getRoleGroup(), actual.get(0).getRoleGroup());
	}
	
	@Test
	public void removeExistingHierarchies_should_return_list_with_one_hierarchy_when_xml_have_repeated_hierarchies() {
		// Arrange
		Ativo ativo1 = new Ativo();
		ativo1.setRoleGroup("Atendentes (O1)");
		Ativo ativo2 = new Ativo();
		ativo2.setRoleGroup("Atendentes (O1)");
		List<Ativo> hierarchiesXML = new ArrayList<>();
		hierarchiesXML.add(ativo1);
		hierarchiesXML.add(ativo2);
		
		Ativo ativoDB = new Ativo();
		ativoDB.setRoleGroup("Superintendência (M4)");
		Map<String, Ativo> hierarchiesDB = new HashMap<>();
		hierarchiesDB.put(ativoDB.getRoleGroup(), ativoDB);
		
		// Act
		List<Ativo> actual = bean.removeExistingHierarchies(hierarchiesXML, hierarchiesDB);
		
		// Assert
		Assert.assertEquals(1, actual.size());
		Assert.assertEquals(ativo1.getRoleGroup(), actual.get(0).getRoleGroup());
	}
	
	@Test
	public void removeExistingHierarchies_should_return_list_with_one_hierarchy_when_xml_have_repeated_hierarchies_and_db_is_empty() {
		// Arrange
		Ativo ativo1 = new Ativo();
		ativo1.setRoleGroup("Atendentes (O1)");
		Ativo ativo2 = new Ativo();
		ativo2.setRoleGroup("Atendentes (O1)");
		List<Ativo> hierarchiesXML = new ArrayList<>();
		hierarchiesXML.add(ativo1);
		hierarchiesXML.add(ativo2);
		
		Map<String, Ativo> hierarchiesDB = new HashMap<>();
		
		// Act
		List<Ativo> actual = bean.removeExistingHierarchies(hierarchiesXML, hierarchiesDB);
		
		// Assert
		Assert.assertEquals(1, actual.size());
		Assert.assertEquals(ativo1.getRoleGroup(), actual.get(0).getRoleGroup());
	}
	
	@Test
	public void removeExistingHierarchies_should_return_empty_list_when_xml_is_empty_and_db_have_one_hierarchy() {
		// Arrange
		List<Ativo> hierarchiesXML = new ArrayList<>();
		
		Ativo ativoDB = new Ativo();
		ativoDB.setRoleGroup("Superintendência (M4)");
		Map<String, Ativo> hierarchiesDB = new HashMap<>();
		hierarchiesDB.put(ativoDB.getRoleGroup(), ativoDB);
		
		// Act
		List<Ativo> actual = bean.removeExistingHierarchies(hierarchiesXML, hierarchiesDB);
		
		// Assert
		Assert.assertTrue(actual.isEmpty());
	}
	
	@Test
	public void removeExistingHierarchies_should_return_list_with_two_hierarchies_when_xml_have_two_hierarchies_and_db_have_one_hierarchy() {
		// Arrange
		Ativo ativo1 = new Ativo();
		ativo1.setRoleGroup("Atendentes (O1)");
		Ativo ativo2 = new Ativo();
		ativo2.setRoleGroup("Supervisão (O7)");
		List<Ativo> hierarchiesXML = new ArrayList<>();
		hierarchiesXML.add(ativo1);
		hierarchiesXML.add(ativo2);
		
		Ativo ativoDB = new Ativo();
		ativoDB.setRoleGroup("Superintendência (M4)");
		Map<String, Ativo> hierarchiesDB = new HashMap<>();
		hierarchiesDB.put(ativoDB.getRoleGroup(), ativoDB);
		
		// Act
		List<Ativo> actual = bean.removeExistingHierarchies(hierarchiesXML, hierarchiesDB);
		
		// Assert
		Assert.assertEquals(2, actual.size());
		Assert.assertEquals(ativo1.getRoleGroup(), actual.get(0).getRoleGroup());
		Assert.assertEquals(ativo2.getRoleGroup(), actual.get(1).getRoleGroup());
	}
	
	@Test
	public void removeExistingHierarchies_should_return_list_with_one_hierarchy_when_xml_have_two_hierarchies_and_db_have_one_hierarchy() {
		// Arrange
		Ativo ativo1 = new Ativo();
		ativo1.setRoleGroup("Atendentes (O1)");
		Ativo ativo2 = new Ativo();
		ativo2.setRoleGroup("Superintendência (M4)");
		List<Ativo> hierarchiesXML = new ArrayList<>();
		hierarchiesXML.add(ativo1);
		hierarchiesXML.add(ativo2);
		
		Ativo ativoDB = new Ativo();
		ativoDB.setRoleGroup("Superintendência (M4)");
		Map<String, Ativo> hierarchiesDB = new HashMap<>();
		hierarchiesDB.put(ativoDB.getRoleGroup(), ativoDB);
		
		// Act
		List<Ativo> actual = bean.removeExistingHierarchies(hierarchiesXML, hierarchiesDB);
		
		// Assert
		Assert.assertEquals(1, actual.size());
		Assert.assertEquals(ativo1.getRoleGroup(), actual.get(0).getRoleGroup());
	}

	@Test
	public void removeExistingHierarchies_should_return_list_with_none_hierarchy_when_xml_have_two_hierarchies_and_db_have_two_hierarchies() {
		// Arrange
		Ativo ativo1 = new Ativo();
		ativo1.setRoleGroup("Atendentes (O1)");
		Ativo ativo2 = new Ativo();
		ativo2.setRoleGroup("Superintendência (M4)");
		List<Ativo> hierarchiesXML = new ArrayList<>();
		hierarchiesXML.add(ativo1);
		hierarchiesXML.add(ativo2);
		
		Ativo ativoDB1 = new Ativo();
		ativoDB1.setRoleGroup("Atendentes (O1)");
		Ativo ativoDB2 = new Ativo();
		ativoDB2.setRoleGroup("Superintendência (M4)");
		Map<String, Ativo> hierarchiesDB = new HashMap<>();
		hierarchiesDB.put(ativoDB1.getRoleGroup(), ativoDB1);
		hierarchiesDB.put(ativoDB2.getRoleGroup(), ativoDB2);
		
		// Act
		List<Ativo> actual = bean.removeExistingHierarchies(hierarchiesXML, hierarchiesDB);
		
		// Assert
		Assert.assertTrue(actual.isEmpty());
	}
	
	@Test
	public void removeExistingHierarchies_should_return_list_with_two_hierarchies_when_xml_have_two_hierarchies_and_db_have_two_hierarchies() {
		// Arrange
		Ativo ativo1 = new Ativo();
		ativo1.setRoleGroup("Atendentes (O1)");
		Ativo ativo2 = new Ativo();
		ativo2.setRoleGroup("Superintendência (M4)");
		List<Ativo> hierarchiesXML = new ArrayList<>();
		hierarchiesXML.add(ativo1);
		hierarchiesXML.add(ativo2);
		
		Ativo ativoDB1 = new Ativo();
		ativoDB1.setRoleGroup("Gerência (M3)");
		Ativo ativoDB2 = new Ativo();
		ativoDB2.setRoleGroup("Supervisão (O7)");
		Map<String, Ativo> hierarchiesDB = new HashMap<>();
		hierarchiesDB.put(ativoDB1.getRoleGroup(), ativoDB1);
		hierarchiesDB.put(ativoDB2.getRoleGroup(), ativoDB2);
		
		// Act
		List<Ativo> actual = bean.removeExistingHierarchies(hierarchiesXML, hierarchiesDB);
		
		// Assert
		Assert.assertEquals(2, actual.size());
		Assert.assertEquals(ativo2.getRoleGroup(), actual.get(0).getRoleGroup());
		Assert.assertEquals(ativo1.getRoleGroup(), actual.get(1).getRoleGroup());
	}
}
