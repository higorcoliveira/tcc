package br.com.afferolab.hub.atento.ativos.bean.skillo;

import org.junit.Assert;
import org.junit.Test;

import br.com.afferolab.hub.atento.ativos.entity.xml.Ativo;

public class SkilloFormatterTest {

	SkilloFormatter formatter = new SkilloFormatter();
	
	@Test
	public void formatFunction_should_return_formated_function_when_function_area_is_empty() {
		// Arrange
		String roleGroup = "Supervisão (O7)";
		
		Ativo ativo = new Ativo();
		ativo.setRoleGroup(roleGroup);
		ativo.setFunctionArea("");
		
		// Act
		String function = formatter.formatFunction(ativo);
		
		// Assert
		Assert.assertEquals(roleGroup, function);
	}
	
	@Test
	public void formatFunction_should_return_formated_function_when_function_area_is_null() {
		// Arrange
		String roleGroup = "Supervisão (O7)";
		
		Ativo ativo = new Ativo();
		ativo.setRoleGroup(roleGroup);
		
		// Act
		String function = formatter.formatFunction(ativo);
		
		// Assert
		Assert.assertEquals(roleGroup, function);
	}
	
	@Test
	public void formatFunction_should_return_formated_function_when_function_area_is_not_empty() {
		// Arrange
		String roleGroup = "Supervisão (O7)";
		String functionArea = "Cliente - Atendimento";
		
		Ativo ativo = new Ativo();
		ativo.setRoleGroup(roleGroup);
		ativo.setFunctionArea(functionArea);
		
		String expected = String.format("%s - %s", functionArea, roleGroup);
		
		// Act
		String function = formatter.formatFunction(ativo);
		
		// Assert
		Assert.assertEquals(expected, function);
	}
}