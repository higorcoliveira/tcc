package br.com.tcc.integracao.lms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import br.com.tcc.integracao.bean.lms.MergeStrategyArea;
import br.com.tcc.integracao.entity.xml.Ativo;

public class MergeStrategyAreaTest {
	
	MergeStrategyArea bean = new MergeStrategyArea();
	
	/*
	 * metodo testado removeExistingAreas(List<Ativo> areasXML, Map<String, Ativo> areasDB)
	 */
	@Test
	public void removeExistingAreas_should_return_emptyList_when_area_have_in_db_and_xml() {
		// Arrange
		Ativo ativo = new Ativo();
		ativo.setSite("49 - SAO BERNARDO DO CAMPO ADM");
		List<Ativo> areasXML = new ArrayList<>();
		areasXML.add(ativo);
		
		Map<String, Ativo> areasDB = new HashMap<>();
		areasDB.put(ativo.getSite(), ativo);
		
		// Act
		List<Ativo> actual = bean.removeExistingAreas(areasXML, areasDB);
		
		// Assert
		Assert.assertTrue(actual.isEmpty());
	}
	
	@Test
	public void removeExistingAreas_should_return_list_with_one_area_when_area_not_have_in_db_and_have_in_xml() {
		// Arrange
		Ativo ativo1 = new Ativo();
		ativo1.setSite("49 - SAO BERNARDO DO CAMPO ADM");
		List<Ativo> areasXML = new ArrayList<>();
		areasXML.add(ativo1);
		
		Ativo ativo2 = new Ativo();
		ativo2.setSite("68 - SP - NSP - ADM");
		Map<String, Ativo> areasDB = new HashMap<>();
		areasDB.put(ativo2.getSite(), ativo2);
		
		// Act
		List<Ativo> actual = bean.removeExistingAreas(areasXML, areasDB);
		
		// Assert
		Assert.assertFalse(actual.isEmpty());
		Assert.assertEquals(ativo1.getSite(), actual.get(0).getSite());
	}
	
	@Test
	public void removeExistingAreas_should_return_list_with_one_area_when_db_is_empty_and_area_have_in_xml() {
		// Arrange
		Ativo ativo1 = new Ativo();
		ativo1.setSite("49 - SAO BERNARDO DO CAMPO ADM");
		List<Ativo> areasXML = new ArrayList<>();
		areasXML.add(ativo1);
		
		Map<String, Ativo> areasDB = new HashMap<>();
		
		// Act
		List<Ativo> actual = bean.removeExistingAreas(areasXML, areasDB);
		
		// Assert
		Assert.assertEquals(1, actual.size());
		Assert.assertEquals(ativo1.getSite(), actual.get(0).getSite());
	}
	
	@Test
	public void removeExistingAreas_should_return_list_with_one_area_when_xml_have_repeated_areas() {
		// Arrange
		Ativo ativo1 = new Ativo();
		ativo1.setSite("49 - SAO BERNARDO DO CAMPO ADM");
		Ativo ativo2 = new Ativo();
		ativo2.setSite("49 - SAO BERNARDO DO CAMPO ADM");
		List<Ativo> areasXML = new ArrayList<>();
		areasXML.add(ativo1);
		areasXML.add(ativo2);
		
		Ativo ativoDB = new Ativo();
		ativoDB.setSite("68 - SP - NSP - ADM");
		Map<String, Ativo> areasDB = new HashMap<>();
		areasDB.put(ativoDB.getSite(), ativoDB);
		
		// Act
		List<Ativo> actual = bean.removeExistingAreas(areasXML, areasDB);
		
		// Assert
		Assert.assertEquals(1, actual.size());
		Assert.assertEquals(ativo1.getSite(), actual.get(0).getSite());
	}
	
	@Test
	public void removeExistingAreas_should_return_list_with_one_area_when_xml_have_repeated_areas_and_db_is_empty() {
		// Arrange
		Ativo ativo1 = new Ativo();
		ativo1.setSite("49 - SAO BERNARDO DO CAMPO ADM");
		Ativo ativo2 = new Ativo();
		ativo2.setSite("49 - SAO BERNARDO DO CAMPO ADM");
		List<Ativo> areasXML = new ArrayList<>();
		areasXML.add(ativo1);
		areasXML.add(ativo2);
		
		Map<String, Ativo> areasDB = new HashMap<>();
		
		// Act
		List<Ativo> actual = bean.removeExistingAreas(areasXML, areasDB);
		
		// Assert
		Assert.assertEquals(1, actual.size());
		Assert.assertEquals(ativo1.getSite(), actual.get(0).getSite());
	}
	
	@Test
	public void removeExistingAreas_should_return_empty_list_when_xml_is_empty_and_db_have_one_area() {
		// Arrange
		List<Ativo> areasXML = new ArrayList<>();
		
		Ativo ativoDB = new Ativo();
		ativoDB.setSite("68 - SP - NSP - ADM");
		Map<String, Ativo> areasDB = new HashMap<>();
		areasDB.put(ativoDB.getSite(), ativoDB);
		
		// Act
		List<Ativo> actual = bean.removeExistingAreas(areasXML, areasDB);
		
		// Assert
		Assert.assertTrue(actual.isEmpty());
	}
	
	@Test
	public void removeExistingAreas_should_return_list_with_two_areas_when_xml_have_two_areas_and_db_have_one_area() {
		// Arrange
		Ativo ativo1 = new Ativo();
		ativo1.setSite("49 - SAO BERNARDO DO CAMPO ADM");
		Ativo ativo2 = new Ativo();
		ativo2.setSite("106 - SP-SAO PAULO-BARRA FUNDA(ADM)");
		List<Ativo> areasXML = new ArrayList<>();
		areasXML.add(ativo1);
		areasXML.add(ativo2);
		
		Ativo ativoDB = new Ativo();
		ativoDB.setSite("68 - SP - NSP - ADM");
		Map<String, Ativo> areasDB = new HashMap<>();
		areasDB.put(ativoDB.getSite(), ativoDB);
		
		// Act
		List<Ativo> actual = bean.removeExistingAreas(areasXML, areasDB);
		
		// Assert
		Assert.assertEquals(2, actual.size());
		Assert.assertEquals(ativo2.getSite(), actual.get(0).getSite());
		Assert.assertEquals(ativo1.getSite(), actual.get(1).getSite());
	}
	
	@Test
	public void removeExistingAreas_should_return_list_with_one_area_when_xml_have_two_areas_and_db_have_one_area() {
		// Arrange
		Ativo ativo1 = new Ativo();
		ativo1.setSite("49 - SAO BERNARDO DO CAMPO ADM");
		Ativo ativo2 = new Ativo();
		ativo2.setSite("68 - SP - NSP - ADM");
		List<Ativo> areasXML = new ArrayList<>();
		areasXML.add(ativo1);
		areasXML.add(ativo2);
		
		Ativo ativoDB = new Ativo();
		ativoDB.setSite("68 - SP - NSP - ADM");
		Map<String, Ativo> areasDB = new HashMap<>();
		areasDB.put(ativoDB.getSite(), ativoDB);
		
		// Act
		List<Ativo> actual = bean.removeExistingAreas(areasXML, areasDB);
		
		// Assert
		Assert.assertEquals(1, actual.size());
		Assert.assertEquals(ativo1.getSite(), actual.get(0).getSite());
	}

	@Test
	public void removeExistingAreas_should_return_list_with_none_area_when_xml_have_two_areas_and_db_have_two_areas() {
		// Arrange
		Ativo ativo1 = new Ativo();
		ativo1.setSite("49 - SAO BERNARDO DO CAMPO ADM");
		Ativo ativo2 = new Ativo();
		ativo2.setSite("68 - SP - NSP - ADM");
		List<Ativo> areasXML = new ArrayList<>();
		areasXML.add(ativo1);
		areasXML.add(ativo2);
		
		Ativo ativoDB1 = new Ativo();
		ativoDB1.setSite("49 - SAO BERNARDO DO CAMPO ADM");
		Ativo ativoDB2 = new Ativo();
		ativoDB2.setSite("68 - SP - NSP - ADM");
		Map<String, Ativo> areasDB = new HashMap<>();
		areasDB.put(ativoDB1.getSite(), ativoDB1);
		areasDB.put(ativoDB2.getSite(), ativoDB2);
		
		// Act
		List<Ativo> actual = bean.removeExistingAreas(areasXML, areasDB);
		
		// Assert
		Assert.assertTrue(actual.isEmpty());
	}
	
	@Test
	public void removeExistingAreas_should_return_list_with_two_areas_when_xml_have_two_areas_and_db_have_two_areas() {
		// Arrange
		Ativo ativo1 = new Ativo();
		ativo1.setSite("49 - SAO BERNARDO DO CAMPO ADM");
		Ativo ativo2 = new Ativo();
		ativo2.setSite("68 - SP - NSP - ADM");
		List<Ativo> areasXML = new ArrayList<>();
		areasXML.add(ativo1);
		areasXML.add(ativo2);
		
		Ativo ativoDB1 = new Ativo();
		ativoDB1.setSite("26 - SAO PAULO ROCHAVERA");
		Ativo ativoDB2 = new Ativo();
		ativoDB2.setSite("106 - SP-SAO PAULO-BARRA FUNDA(ADM)");
		Map<String, Ativo> areasDB = new HashMap<>();
		areasDB.put(ativoDB1.getSite(), ativoDB1);
		areasDB.put(ativoDB2.getSite(), ativoDB2);
		
		// Act
		List<Ativo> actual = bean.removeExistingAreas(areasXML, areasDB);
		
		// Assert
		Assert.assertEquals(2, actual.size());
		Assert.assertEquals(ativo1.getSite(), actual.get(0).getSite());
		Assert.assertEquals(ativo2.getSite(), actual.get(1).getSite());
	}
}
