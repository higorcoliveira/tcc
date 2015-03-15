package br.com.afferolab.hub.atento.ativos.bean.gr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import br.com.afferolab.hub.atento.ativos.entity.xml.Ativo;

public class MergeStrategyCostCenterTest {
	
	MergeStrategyCostCenter mergeStrategy = new MergeStrategyCostCenter();
	
	@Test
	public void removeExistedCostCenters_should_return_with_one_element_when_dblist_is_empty() {
		// Arrange
		List<Ativo> costCentersXML = new ArrayList<>();
		Map<Integer, Ativo> costCentersDB = new HashMap<>();
		
		Ativo ativoXML = new Ativo();
		ativoXML.setCostCenter(123456);
		costCentersXML.add(ativoXML);
		
		// Act
		List<Ativo> actual = mergeStrategy.removeExistedCostCenters(costCentersXML, costCentersDB);
		
		// Assert
		Assert.assertEquals(1, actual.size());
	}
	
	@Test
	public void removeExistedCostCenters_should_return_with_none_element_when_dblist_is_not_empty() {
		// Arrange
		List<Ativo> costCentersXML = new ArrayList<>();
		Map<Integer, Ativo> costCentersDB = new HashMap<>();
		
		Ativo ativoDB = new Ativo();
		ativoDB.setCostCenter(123456);
		costCentersDB.put(123456, ativoDB);
		
		// Act
		List<Ativo> actual = mergeStrategy.removeExistedCostCenters(costCentersXML, costCentersDB);
		
		// Assert
		Assert.assertEquals(0, actual.size());
	}
	
	@Test
	public void removeExistedCostCenters_should_return_with_none_element_when_dblist_contains_one_element() {
		// Arrange
		List<Ativo> costCentersXML = new ArrayList<>();
		Map<Integer, Ativo> costCentersDB = new HashMap<>();
		
		Ativo ativoDB = new Ativo();
		ativoDB.setCostCenter(123456);
		
		costCentersDB.put(123456, ativoDB);
		
		Ativo ativoXML = new Ativo();
		ativoXML.setCostCenter(123456);
		
		costCentersXML.add(ativoXML);
		
		// Act
		List<Ativo> actual = mergeStrategy.removeExistedCostCenters(costCentersXML, costCentersDB);
		
		// Assert
		Assert.assertEquals(0, actual.size());
	}
	
	@Test
	public void removeExistedCostCenters_should_return_with_one_element_when_dblist_not_contains_element() {
		// Arrange
		List<Ativo> costCentersXML = new ArrayList<>();
		Map<Integer, Ativo> costCentersDB = new HashMap<>();
		
		Ativo ativoDB = new Ativo();
		ativoDB.setCostCenter(123456);
		
		costCentersDB.put(123456, ativoDB);
		
		Ativo ativoXML = new Ativo();
		ativoXML.setCostCenter(789111);
		
		costCentersXML.add(ativoXML);
		
		// Act
		List<Ativo> actual = mergeStrategy.removeExistedCostCenters(costCentersXML, costCentersDB);
		
		// Assert
		Assert.assertEquals(1, actual.size());
	}

	@Test
	public void removeExistedCostCenters_should_return_with_none_element_when_dblist_contains_two_elements_and_xmlList_have_oneElement() {
		// Arrange
		List<Ativo> costCentersXML = new ArrayList<>();
		Map<Integer, Ativo> costCentersDB = new HashMap<>();
		
		Ativo ativoDB1 = new Ativo();
		ativoDB1.setCostCenter(123456);
		Ativo ativoDB2 = new Ativo();
		ativoDB2.setCostCenter(789111);
		
		costCentersDB.put(123456, ativoDB1);
		costCentersDB.put(789111, ativoDB2);
		
		Ativo ativoXML = new Ativo();
		ativoXML.setCostCenter(789111);
		
		costCentersXML.add(ativoXML);
		
		// Act
		List<Ativo> actual = mergeStrategy.removeExistedCostCenters(costCentersXML, costCentersDB);
		
		// Assert
		Assert.assertEquals(0, actual.size());
	}
	
	@Test
	public void removeExistedCostCenters_should_return_with_one_element_when_dblist_contains_two_elements_and_xmlList_have_oneElement() {
		// Arrange
		List<Ativo> costCentersXML = new ArrayList<>();
		Map<Integer, Ativo> costCentersDB = new HashMap<>();
		
		Ativo ativoDB1 = new Ativo();
		ativoDB1.setCostCenter(123456);
		Ativo ativoDB2 = new Ativo();
		ativoDB2.setCostCenter(789111);
		
		costCentersDB.put(123456, ativoDB1);
		costCentersDB.put(789111, ativoDB2);
		
		Ativo ativoXML = new Ativo();
		ativoXML.setCostCenter(631211);
		
		costCentersXML.add(ativoXML);
		
		// Act
		List<Ativo> actual = mergeStrategy.removeExistedCostCenters(costCentersXML, costCentersDB);
		
		// Assert
		Assert.assertEquals(1, actual.size());
	}
	
	@Test
	public void removeExistedCostCenters_should_return_with_one_element_when_dblist_contains_one_elements_and_xmlList_have_two_elements() {
		// Arrange
		List<Ativo> costCentersXML = new ArrayList<>();
		Map<Integer, Ativo> costCentersDB = new HashMap<>();
		
		Ativo ativoDB1 = new Ativo();
		ativoDB1.setCostCenter(123456);
		
		costCentersDB.put(123456, ativoDB1);
		
		Ativo ativoXML1 = new Ativo();
		ativoXML1.setCostCenter(123456);
		Ativo ativoXML2 = new Ativo();
		ativoXML2.setCostCenter(231232);
		
		costCentersXML.add(ativoXML1);
		costCentersXML.add(ativoXML2);
		
		// Act
		List<Ativo> actual = mergeStrategy.removeExistedCostCenters(costCentersXML, costCentersDB);
		
		// Assert
		Assert.assertEquals(1, actual.size());
	}
	
	@Test
	public void removeExistedCostCenters_should_return_with_two_elements_when_dblist_contains_one_element_and_xmlList_have_two_elements() {
		// Arrange
		List<Ativo> costCentersXML = new ArrayList<>();
		Map<Integer, Ativo> costCentersDB = new HashMap<>();
		
		Ativo ativoDB1 = new Ativo();
		ativoDB1.setCostCenter(123456);
		
		costCentersDB.put(123456, ativoDB1);
		
		Ativo ativoXML1 = new Ativo();
		ativoXML1.setCostCenter(312131);
		Ativo ativoXML2 = new Ativo();
		ativoXML2.setCostCenter(231232);
		
		costCentersXML.add(ativoXML1);
		costCentersXML.add(ativoXML2);
		
		// Act
		List<Ativo> actual = mergeStrategy.removeExistedCostCenters(costCentersXML, costCentersDB);
		
		// Assert
		Assert.assertEquals(2, actual.size());
	}
	
	@Test
	public void removeExistedCostCenters_should_return_with_none_element_when_dblist_contains_two_elements_and_xmlList_have_two_elements() {
		// Arrange
		List<Ativo> costCentersXML = new ArrayList<>();
		Map<Integer, Ativo> costCentersDB = new HashMap<>();
		
		Ativo ativoDB1 = new Ativo();
		ativoDB1.setCostCenter(123456);
		Ativo ativoDB2 = new Ativo();
		ativoDB2.setCostCenter(145678);
		
		costCentersDB.put(123456, ativoDB1);
		costCentersDB.put(145678, ativoDB2);
		
		Ativo ativoXML1 = new Ativo();
		ativoXML1.setCostCenter(123456);
		Ativo ativoXML2 = new Ativo();
		ativoXML2.setCostCenter(145678);
		
		costCentersXML.add(ativoXML1);
		costCentersXML.add(ativoXML2);
		
		// Act
		List<Ativo> actual = mergeStrategy.removeExistedCostCenters(costCentersXML, costCentersDB);
		
		// Assert
		Assert.assertEquals(0, actual.size());
	}
	
	
	@Test
	public void removeExistedCostCenters_should_return_with_none_element_when_dblist_contains_two_elements_and_xmlList_have_two_elements_repeated() {
		// Arrange
		List<Ativo> costCentersXML = new ArrayList<>();
		Map<Integer, Ativo> costCentersDB = new HashMap<>();
		
		Ativo ativoDB2 = new Ativo();
		ativoDB2.setCostCenter(145678);
		
		costCentersDB.put(145678, ativoDB2);
		
		Ativo ativoXML1 = new Ativo();
		ativoXML1.setCostCenter(123456);
		Ativo ativoXML2 = new Ativo();
		ativoXML2.setCostCenter(123456);
		
		costCentersXML.add(ativoXML1);
		costCentersXML.add(ativoXML2);
		
		// Act
		List<Ativo> actual = mergeStrategy.removeExistedCostCenters(costCentersXML, costCentersDB);
		
		// Assert
		Assert.assertEquals(1, actual.size());
	}
	
	@Test
	public void removeExistedCostCenters_should_return_with_none_element_when_dblist_not_contains_elements_and_xmlList_have_two_elements_repeated() {
		// Arrange
		List<Ativo> costCentersXML = new ArrayList<>();
		Map<Integer, Ativo> costCentersDB = new HashMap<>();
		
		Ativo ativoXML1 = new Ativo();
		ativoXML1.setCostCenter(123456);
		Ativo ativoXML2 = new Ativo();
		ativoXML2.setCostCenter(123456);
		
		costCentersXML.add(ativoXML1);
		costCentersXML.add(ativoXML2);
		
		// Act
		List<Ativo> actual = mergeStrategy.removeExistedCostCenters(costCentersXML, costCentersDB);
		
		// Assert
		Assert.assertEquals(1, actual.size());
	}
}
