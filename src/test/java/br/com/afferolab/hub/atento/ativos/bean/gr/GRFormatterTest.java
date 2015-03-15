package br.com.afferolab.hub.atento.ativos.bean.gr;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.afferolab.hub.atento.ativos.entity.xml.Ativo;

public class GRFormatterTest {

	GRFormatter formatter = new GRFormatter();
	
	/*
	 * metodo testado formatData()
	 */
	@Test
	public void formatData_should_return_list_formatted_with_two_elements() {
		// Arrange
		Ativo ativo1 = new Ativo();
		ativo1.setName("John");
		ativo1.setEmail("teste1@teste.com.br");
		ativo1.setPassword("1234");
		ativo1.setContract("12232134 - Contrato1");
		
		Ativo ativo2 = new Ativo();
		ativo2.setName("Paul");
		ativo2.setEmail("teste2@teste.com.br");
		ativo2.setPassword("4567");
		ativo2.setContract("124525252 - Contrato2");

		List<Ativo> listAtivos = new ArrayList<>();
		listAtivos.add(ativo1);
		listAtivos.add(ativo2);

		// Act
		List<Ativo> listFormatter = formatter.formatData(listAtivos);
		
		// Assert
		Assert.assertEquals(2, listFormatter.size());
		
		Assert.assertEquals("John", listFormatter.get(0).getName());
		Assert.assertEquals("teste1@teste.com.br", listFormatter.get(0).getEmail());
		Assert.assertEquals("cRDtpNCeBiql5KOQsKVyrA0sAiA=", listFormatter.get(0).getPassword());
		Assert.assertEquals("12232134", listFormatter.get(0).getContract());
		
		Assert.assertEquals("Paul", listFormatter.get(1).getName());
		Assert.assertEquals("teste2@teste.com.br", listFormatter.get(1).getEmail());
		Assert.assertEquals("g3h/BgpZSTrv3NSyNpmQ5zA+GG4=", listFormatter.get(1).getPassword());
		Assert.assertEquals("124525252", listFormatter.get(1).getContract());
	}
	
	/*
	 * metodo testado formatEmail(String email)
	 */
	@Test
	public void formatEmail_should_write_default_email_if_email_is_null() {
		// Arrange
		Ativo ativo = new Ativo();
		ativo.setEmail(null);
		ativo.setRe("123456");
		String expected = "atento_123456@atento.com.br";
		
		// Act
		String emailActual = formatter.formatEmail(ativo);
		
		// Assert
		Assert.assertEquals(expected, emailActual);
	}
	
	@Test
	public void formatEmail_should_write_default_email_if_email_is_empty() {
		// Arrange
		Ativo ativo = new Ativo();
		ativo.setEmail("");
		ativo.setRe("123456");
		String expected = "atento_123456@atento.com.br";
		
		// Act
		String emailActual = formatter.formatEmail(ativo);
		
		// Assert
		Assert.assertEquals(expected, emailActual);
	}
	
	@Test
	public void formatEmail_should_write_default_email_if_email_not_formatted1() {
		// Arrange
		Ativo ativo = new Ativo();
		ativo.setEmail("teste.com.br");
		ativo.setRe("123456");
		String expected = "GR123456@atento.com.br";
		
		// Act
		String emailActual = formatter.formatEmail(ativo);
		
		// Assert
		Assert.assertEquals(expected, emailActual);
	}
	
	@Test
	public void formatEmail_should_write_default_email_if_email_not_formatted2() {
		// Arrange
		Ativo ativo = new Ativo();
		ativo.setEmail("teste@.com.br");
		ativo.setRe("123456");
		String expected = "GR123456@atento.com.br";
		
		// Act
		String emailActual = formatter.formatEmail(ativo);
		
		// Assert
		Assert.assertEquals(expected, emailActual);
	}
	
	@Test
	public void formatEmail_should_write_default_email_if_email_not_formatted3() {
		// Arrange
		Ativo ativo = new Ativo();
		ativo.setEmail("testecom.br");
		ativo.setRe("123456");
		
		String expected = "GR123456@atento.com.br";
		
		// Act
		String emailActual = formatter.formatEmail(ativo);
		
		// Assert
		Assert.assertEquals(expected, emailActual);
	}
	
	@Test
	public void formatEmail_should_write_default_email_if_email_not_formatted4() {
		// Arrange
		Ativo ativo = new Ativo();
		ativo.setEmail("@com.br");
		ativo.setRe("123456");
		
		String expected = "GR123456@atento.com.br";
		
		// Act
		String emailActual = formatter.formatEmail(ativo);
		
		// Assert
		Assert.assertEquals(expected, emailActual);
	}
	
	@Test
	public void formatEmail_should_write_default_email_if_email_not_formatted6() {
		// Arrange
		Ativo ativo = new Ativo();
		ativo.setEmail(".@com.br");
		ativo.setRe("123456");
		
		String expected = "GR123456@atento.com.br";
		
		// Act
		String emailActual = formatter.formatEmail(ativo);
		
		// Assert
		Assert.assertEquals(expected, emailActual);
	}
	
	@Test
	public void formatEmail_should_write_default_email_if_email_not_formatted8() {
		// Arrange
		Ativo ativo = new Ativo();
		ativo.setEmail("teste()@com.br");
		ativo.setRe("123456");
		
		String expected = "GR123456@atento.com.br";
		
		// Act
		String emailActual = formatter.formatEmail(ativo);
		
		// Assert
		Assert.assertEquals(expected, emailActual);
	}
	
	@Test
	public void formatEmail_should_write_default_email_if_email_not_formatted9() {
		// Arrange
		Ativo ativo = new Ativo();
		ativo.setEmail("teste@teste@com.br");
		ativo.setRe("123456");
		
		String expected = "GR123456@atento.com.br";
		
		// Act
		String emailActual = formatter.formatEmail(ativo);
		
		// Assert
		Assert.assertEquals(expected, emailActual);
	}
	
	@Test
	public void formatEmail_should_write_email_if_email_is_formatted() {
		// Arrange
		Ativo ativo = new Ativo();
		ativo.setEmail("teste@com.br");
		ativo.setRe("123456");
		
		String expected = "teste@com.br";
		// Act
		String emailActual = formatter.formatEmail(ativo);
		
		// Assert
		Assert.assertEquals(expected, emailActual);
	}
	
	/*
	 * metodo testado formatPassword(Ativo ativo)
	 */
	@Test
	public void formatPassword_should_return_password_in_sha_if_password_is_null() {
		// Arrange
		Ativo ativo = new Ativo();
		ativo.setPassword(null);
		
		// Act
		String actualPassword = formatter.formatPassword(ativo);
		
		// Assert
		Assert.assertNotNull(actualPassword);
		Assert.assertEquals("2jmj7l5rSw0yVb/vlWAYkK/YBwk=", actualPassword);
	}
	
	@Test
	public void formatPassword_should_return_password_in_sha() {
		// Arrange
		Ativo ativo = new Ativo();
		ativo.setPassword("123456");
		
		// Act
		String actualPassword = formatter.formatPassword(ativo);
		
		// Assert
		Assert.assertNotNull(actualPassword);
		Assert.assertEquals("fEqNCco3Yq9h5ZUglD3CZJT4lBs=", actualPassword);
	}
	
	/*
	 * metodo testado formatContract(Ativo ativo)
	 */
	@Test (expected = IllegalArgumentException.class)
	public void formatContract_should_throw_exception_if_contract_is_null() {
		// Arrange
		Ativo ativo = new Ativo();
		ativo.setContract(null);
		
		// Act
		formatter.formatContract(ativo);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void formatContract_should_throw_exception_if_contract_is_empty() {
		// Arrange
		Ativo ativo = new Ativo();
		ativo.setContract("");
		
		// Act
		formatter.formatContract(ativo);
	}
	
	@Test
	public void formatContract_should_return_formattedContract_if_contract_is_valid() {
		// Arrange
		Ativo ativo = new Ativo();
		ativo.setContract("0040001250 - CORPORATIVO");
		
		// Act
		String actualContract = formatter.formatContract(ativo);
		
		// Assert
		Assert.assertEquals("0040001250", actualContract);
	}
}
