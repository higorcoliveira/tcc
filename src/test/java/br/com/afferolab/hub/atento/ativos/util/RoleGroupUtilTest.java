package br.com.afferolab.hub.atento.ativos.util;

import org.junit.Assert;
import org.junit.Test;

import br.com.tcc.integracao.util.RoleGroupUtil;

public class RoleGroupUtilTest {

	@Test (expected = IllegalArgumentException.class)
	public void extractRoleGroup_should_throw_Illegal_argumentException_when_roleGroup_is_null() {
		// Arrange
		String roleGroup = null;
		
		// Act
		RoleGroupUtil.extractRoleGroup(roleGroup);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void extractRoleGroup_should_throw_Illegal_argumentException_when_roleGroup_is_empty() {
		// Arrange
		String roleGroup = "";
		
		// Act
		RoleGroupUtil.extractRoleGroup(roleGroup);
	}
	
	@Test
	public void extractRoleGroup_should_extract_acronym_with_two_letters() {
		// Arrange
		String roleGroup = "GERENCIA (M3)";
		
		// Act
		String extracted = RoleGroupUtil.extractRoleGroup(roleGroup);
		
		// Assert
		Assert.assertEquals("M3", extracted);
		
	}
	
	@Test
	public void extractRoleGroup_should_extract_acronym_with_three_letters() {
		// Arrange
		String roleGroup = "AUXILIARES/TECNICOS (OS1)";
		
		// Act
		String extracted = RoleGroupUtil.extractRoleGroup(roleGroup);
		
		// Assert
		Assert.assertEquals("OS1", extracted);
		
	}
}
