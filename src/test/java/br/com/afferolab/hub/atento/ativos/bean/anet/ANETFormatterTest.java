package br.com.afferolab.hub.atento.ativos.bean.anet;

import org.junit.Assert;
import org.junit.Test;

import br.com.tcc.integracao.bean.lms.LMSFormatter;
import br.com.tcc.integracao.entity.xml.Ativo;

public class ANETFormatterTest {

	private static final String PASSWORD_KEY = "dark";
	
	LMSFormatter formatter = new LMSFormatter();
	
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
	
	@Test
	public void formatEmail_should_write_email_if_email_is_formatted_with_one_char() {
		// Arrange
		Ativo ativo = new Ativo();
		ativo.setEmail("a@a.com.br");
		ativo.setRe("123456");
		
		String expected = "GR123456@atento.com.br";
		// Act
		String emailActual = formatter.formatEmail(ativo);
		
		// Assert
		Assert.assertEquals(expected, emailActual);
	}
	
	/*
	 * metodo testado formatPassword()
	 */
	@Test(expected = IllegalArgumentException.class)
	public void formatPassword_should_return_encripted_password_when_password_is_null() {
		// Arrange
		Ativo ativo = new Ativo();
		ativo.setPassword(null);
		
		// Act
		formatter.formatPassword(ativo, PASSWORD_KEY);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void formatPassword_should_return_encripted_password_when_password_is_empty() {
		// Arrange
		Ativo ativo = new Ativo();
		ativo.setPassword("");
		
		// Act
		formatter.formatPassword(ativo, PASSWORD_KEY);
	}
	
	@Test
	public void formatPassword_should_return_encripted_password() {
		// Arrange
		Ativo ativo = new Ativo();
		ativo.setPassword("abc");
		
		// Act
		String actualPassword = formatter.formatPassword(ativo, PASSWORD_KEY);
		
		// Assert
		Assert.assertNotNull(actualPassword);
		Assert.assertEquals("baacc", actualPassword);
	}
}
