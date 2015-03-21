package br.com.tcc.integracao.route;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelSpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.afferolab.utils.camel.response.IntegrationResponse;
import br.com.afferolab.utils.util.TestUtil;
import br.com.tcc.integracao.test.util.CamelTestTemplate;

@RunWith(CamelSpringJUnit4ClassRunner.class)
public class StartRouteRegressionTest extends CamelTestTemplate {
	
	private static final String START_ROUTE_NAME = "AtivosStartRoute";
	
	private static final String XML_FOLDER = "xml/";
	private static final String XML_NOT_IN_GZIP = "not_in_gzip.xml";
	private static final String XML_ID = "id.xml";
	private static final String XML_RE = "re.xml";
	private static final String XML_NOME = "nome.xml";
	private static final String XML_EMAIL = "email.xml";
	private static final String XML_DATA_INICIO = "data_inicio.xml";
	private static final String XML_DATA_DESLIGAMENTO = "data_desligamento.xml";
	private static final String XML_DATA_NASCIMENTO = "data_nascimento.xml";
	private static final String XML_SENHA = "senha.xml";
	private static final String XML_LOGIN_HABILITADO = "login_habilitado.xml";
	private static final String XML_SENHA_EXPIRADA = "senha_expirada.xml";
	private static final String XML_GERENTE = "gerente.xml";
	private static final String XML_GRUPO_CARGO = "grupo_cargo.xml";
	private static final String XML_RE_SUPERIOR = "re_superior.xml";
	private static final String XML_ATIVO = "ativo.xml";
	private static final String XML_SITUACAO_FUNCIONAL = "situacao_funcional.xml";
	private static final String XML_CPF = "cpf.xml";
	private static final String XML_SEXO = "sexo.xml";
	private static final String XML_CENTRODECUSTO = "centroDeCusto.xml";
	private static final String XML_ESTADOCIVIL = "estadoCivil.xml";
	private static final String XML_ESCOLARIDADE = "escolaridade.xml";
	private static final String XML_DIRETORIAEXECUTIVA = "diretoriaExecutiva.xml";
	private static final String XML_NUMTELEFONE = "numTelefone.xml";
	private static final String XML_CARGO = "cargo.xml";
	private static final String XML_CONTRATO = "contrato.xml";
	private static final String XML_AREAFUNCAO = "areaFuncao.xml";
	private static final String XML_SITE = "site.xml";
	private static final String XML_NOMECONSULTORIA = "nomeConsultoria.xml";
	private static final String XML_RG = "rg.xml";
	private static final String XML_EMAILCORPORATIVO = "emailCorporativo.xml";
	
	@Override
	public void init() throws Exception {
		RouteDefinition route = getCamelContext().getRouteDefinition(START_ROUTE_NAME);
		
		route.adviceWith(getCamelContext(), new AdviceWithRouteBuilder() {
			@Override
			public void configure() throws Exception {
				replaceFromWith("cxfrs:bean:rsServer");
				
				interceptSendToEndpoint("activemq:*")
				  .skipSendToOriginalEndpoint()
				  .to("mock:queue");
				
				interceptSendToEndpoint("checkpoint:*")
				  .skipSendToOriginalEndpoint()
				  .to("mock:checkpoint");
			}
		});
	}
	
	/*
	 * XMLValidation
	 * ID
	 */
	@Test
	public void startRoute_should_throw_zipException_when_body_is_not_in_gzip_format() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_NOT_IN_GZIP);
		byte[] bodyUncompressed = body.getBytes();
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyUncompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertBadRequest(reply, integrationResponse);
	}

	@Test
	public void startRoute_should_throw_xmlValidationException_when_id_is_negative() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_ID);
		String bodyModified = MessageFormat.format(body, -1);
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_pass_xmlValidation_when_id_is_positive() throws Exception {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_ID);
		String bodyModified = MessageFormat.format(body, 1);
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertOK(reply, integrationResponse);
	}

	@Test
	public void startRoute_should_pass_xmlValidation_when_id_is_empty() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_ID);
		String bodyModified = MessageFormat.format(body, "");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertOK(reply, integrationResponse);
	}
	
	/*
	 * RE
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_re_is_negative() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_RE);
		String bodyModified = MessageFormat.format(body, -1);
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_length_re_is_less_then_six() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_RE);
		String bodyModified = MessageFormat.format(body, "1");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_length_re_is_more_than_11() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_RE);
		String bodyModified = MessageFormat.format(body, "1234567891010");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_re_is_empty() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_RE);
		String bodyModified = MessageFormat.format(body, "");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_re_is_not_number() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_RE);
		String bodyModified = MessageFormat.format(body, "ABC");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_re_is_valid() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_RE);
		String bodyModified = MessageFormat.format(body, "123456789");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertOK(reply, integrationResponse);
	}
	
	/*
	 * NOME
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_name_length_more_than_50() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_NOME);
		String bodyModified = MessageFormat.format(body, "ABCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_name_id_valid() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_NOME);
		String bodyModified = MessageFormat.format(body, "ABCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertOK(reply, integrationResponse);
	}
	
	/*
	 * EMAIL
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_email_length_more_than_150() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_EMAIL);
		String bodyModified = MessageFormat.format(body, "ABCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_email_id_valid() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_EMAIL);
		String bodyModified = MessageFormat.format(body, "teste@afferolab.com.br");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertOK(reply, integrationResponse);
	}
	
	/*
	 * DATAINICIO
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_data_inicio_is_not_date() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_DATA_INICIO);
		String bodyModified = MessageFormat.format(body, 1);
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_data_inicio_is_invalid() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_DATA_INICIO);
		String bodyModified = MessageFormat.format(body, "2014-02-30");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_data_inicio_is_empty() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_DATA_INICIO);
		String bodyModified = MessageFormat.format(body, "");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_data_inicio_is_valid() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_DATA_INICIO);
		String bodyModified = MessageFormat.format(body, "2014-01-01");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertOK(reply, integrationResponse);
	}
	
	/*
	 * DATADESLIGAMENTO
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_data_desligamento_is_not_date() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_DATA_DESLIGAMENTO);
		String bodyModified = MessageFormat.format(body, 1);
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_data_desligamento_is_invalid() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_DATA_DESLIGAMENTO);
		String bodyModified = MessageFormat.format(body, "2014-02-30");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_data_desligamento_is_empty() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_DATA_DESLIGAMENTO);
		String bodyModified = MessageFormat.format(body, "");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertOK(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_data_desligamento_is_valid() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_DATA_DESLIGAMENTO);
		String bodyModified = MessageFormat.format(body, "2014-01-01");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertOK(reply, integrationResponse);
	}
	
	/*
	 * DATANASCIMENTO
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_data_nascimento_is_not_date() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_DATA_NASCIMENTO);
		String bodyModified = MessageFormat.format(body, 1);
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_data_nascimento_is_invalid() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_DATA_NASCIMENTO);
		String bodyModified = MessageFormat.format(body, "2014-02-30");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_data_nascimento_is_empty() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_DATA_NASCIMENTO);
		String bodyModified = MessageFormat.format(body, "");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_data_nascimento_is_valid() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_DATA_NASCIMENTO);
		String bodyModified = MessageFormat.format(body, "2014-01-01");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertOK(reply, integrationResponse);
	}
	
	/*
	 * SENHA
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_senha_length_more_than_20() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_SENHA);
		String bodyModified = MessageFormat.format(body, "AAAAAAAAAAAAAAAAAAAAA");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_senha_length_is_valid() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_SENHA);
		String bodyModified = MessageFormat.format(body, "AAAAAAAAAAAAAAAAAAAA");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertOK(reply, integrationResponse);
	}
	
	/*
	 * LOGINHABILITADO
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_login_habilitado_is_not_boolean() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_LOGIN_HABILITADO);
		String bodyModified = MessageFormat.format(body, "ABC");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_login_habilitado_is_one() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_LOGIN_HABILITADO);
		String bodyModified = MessageFormat.format(body, 1);
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertOK(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_login_habilitado_is_zero() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_LOGIN_HABILITADO);
		String bodyModified = MessageFormat.format(body, 0);
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertOK(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_login_habilitado_is_empty() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_LOGIN_HABILITADO);
		String bodyModified = MessageFormat.format(body, "");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	/*
	 * SENHAEXPIRADA
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_senha_expirada_is_empty() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_SENHA_EXPIRADA);
		String bodyModified = MessageFormat.format(body, "");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_senha_expirada_is_not_boolean() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_SENHA_EXPIRADA);
		String bodyModified = MessageFormat.format(body, "ABC");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_senha_expirada_is_one() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_SENHA_EXPIRADA);
		String bodyModified = MessageFormat.format(body, 1);
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertOK(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_senha_expirada_is_zero() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_SENHA_EXPIRADA);
		String bodyModified = MessageFormat.format(body, 0);
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertOK(reply, integrationResponse);
	}
	
	/*
	 * GERENTE
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_gerente_is_empty() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_GERENTE);
		String bodyModified = MessageFormat.format(body, "");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_gerente_is_not_boolean() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_GERENTE);
		String bodyModified = MessageFormat.format(body, "ABC");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_gerente_is_one() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_GERENTE);
		String bodyModified = MessageFormat.format(body, 1);
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertOK(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_gerente_is_zero() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_GERENTE);
		String bodyModified = MessageFormat.format(body, 0);
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertOK(reply, integrationResponse);
	}
	
	/*
	 * GRUPODECARGO
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_grupo_de_cargo_lenght_is_more_than_50() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_GRUPO_CARGO);
		String bodyModified = MessageFormat.format(body, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_grupo_de_cargo_lenght_is_empty() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_GRUPO_CARGO);
		String bodyModified = MessageFormat.format(body, "");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_grupo_de_cargo_is_valid() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_GRUPO_CARGO);
		String bodyModified = MessageFormat.format(body, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertOK(reply, integrationResponse);
	}
	
	/*
	 * RESUPERIOR
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_re_superior_is_empty() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_RE_SUPERIOR);
		String bodyModified = MessageFormat.format(body, "");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_re_superior_is_negative() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_RE_SUPERIOR);
		String bodyModified = MessageFormat.format(body, -1);
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_re_superior_is_not_a_number() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_RE_SUPERIOR);
		String bodyModified = MessageFormat.format(body, "ABC");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_re_superior_is_more_then_eleven() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_RE_SUPERIOR);
		String bodyModified = MessageFormat.format(body, "123456789112");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_re_superior_is_less_than_six() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_RE_SUPERIOR);
		String bodyModified = MessageFormat.format(body, "12345");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_re_superior_is_valid() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_RE_SUPERIOR);
		String bodyModified = MessageFormat.format(body, "12345678910");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertOK(reply, integrationResponse);
	}
	
	/*
	 * ATIVO
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_ativo_is_not_boolean() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_ATIVO);
		String bodyModified = MessageFormat.format(body, "ABC");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_ativo_is_empty() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_ATIVO);
		String bodyModified = MessageFormat.format(body, "");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_ativo_is_one() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_ATIVO);
		String bodyModified = MessageFormat.format(body, 1);
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertOK(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_ativo_is_zero() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_ATIVO);
		String bodyModified = MessageFormat.format(body, 0);
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertOK(reply, integrationResponse);
	}
	
	/*
	 * SITUACAOFUNCIONAL
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_situacao_funcional_is_empty() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_SITUACAO_FUNCIONAL);
		String bodyModified = MessageFormat.format(body, "");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_situacao_funcional_is_negative() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_SITUACAO_FUNCIONAL);
		String bodyModified = MessageFormat.format(body, -1);
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_situacao_funcional_is_more_than_five() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_SITUACAO_FUNCIONAL);
		String bodyModified = MessageFormat.format(body, 6);
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_situacao_funcional_is_less_than_one() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_SITUACAO_FUNCIONAL);
		String bodyModified = MessageFormat.format(body, 0);
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_situacao_funcional_is_valid() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_SITUACAO_FUNCIONAL);
		String bodyModified = MessageFormat.format(body, 5);
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		assertOK(reply, integrationResponse);
	}
	
	/*
	 * CPF
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_cpf_have_more_than_eleven_numbers() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_CPF);
		String bodyModified = MessageFormat.format(body, "123456789111");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_cpf_have_less_than_eleven_numbers() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_CPF);
		String bodyModified = MessageFormat.format(body, "1234567891");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_cpf_have_letters() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_CPF);
		String bodyModified = MessageFormat.format(body, "1234567891A");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_throw_xmlValidationException_when_cpf_is_empty() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_CPF);
		String bodyModified = MessageFormat.format(body, "");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_pass_xmlValidation_when_cpf_is_right() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_CPF);
		String bodyModified = MessageFormat.format(body, "12345678911");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertOK(reply, integrationResponse);
	}

	/*
	 * SEXO
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_sexo_have_numbers() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_SEXO);
		String bodyModified = MessageFormat.format(body, "53><0");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_pass_xmlValidation_when_sexo_is_m() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_SEXO);
		String bodyModified = MessageFormat.format(body, "M");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertOK(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_pass_xmlValidation_when_sexo_is_f() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_SEXO);
		String bodyModified = MessageFormat.format(body, "F");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertOK(reply, integrationResponse);
	}
	
	/*
	 * CENTRODECUSTO
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_centroDeCusto_have_letters() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_CENTRODECUSTO);
		String bodyModified = MessageFormat.format(body, "ABCDE");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_pass_xmlValidation_when_centroDeCusto_have_numbers() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_CENTRODECUSTO);
		String bodyModified = MessageFormat.format(body, String.valueOf("12345"));
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertOK(reply, integrationResponse);
	}
	
	/*
	 * ESTADOCIVIL
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_estadoCivil_have_letters() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_ESTADOCIVIL);
		String bodyModified = MessageFormat.format(body, "A");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_estadoCivil_is_greater_than_five() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_ESTADOCIVIL);
		String bodyModified = MessageFormat.format(body, "6");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_estadoCivil_is_between_one_and_five() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_ESTADOCIVIL);
		String bodyModified = MessageFormat.format(body, "3");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertOK(reply, integrationResponse);
	}
	
	/*
	 * ESCOLARIDADE
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_escolaridade_have_letters() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_ESCOLARIDADE);
		String bodyModified = MessageFormat.format(body, "A");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_pass_xmlValidation_when_escolaridade_have_numbers() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_ESCOLARIDADE);
		String bodyModified = MessageFormat.format(body, "1");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertOK(reply, integrationResponse);
	}
	
	/*
	 * DIRETORIAEXECUTIVA
	 */
	@Test
	public void startRoute_should_pass_xmlValidation_when_diretoriaExecutiva_have_numbers() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_DIRETORIAEXECUTIVA);
		String bodyModified = MessageFormat.format(body, 1234);
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertOK(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_pass_xmlValidation_when_diretoriaExecutiva_have_numbers_and_letters() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_DIRETORIAEXECUTIVA);
		String bodyModified = MessageFormat.format(body, "ABC / 1234");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertOK(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_diretoriaExecutiva_have_more_than_150_characters() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_DIRETORIAEXECUTIVA);
		String bodyModified = MessageFormat.format(body, "ABC / 1234 ABC / 1234 ABC / 1234 ABC / 1234 ABC / 1234 ABC / 1234 ABC / 1234 ABC / 1234 ABC / 1234 ABC / 1234 ABC ABC / 1234 ABC / 1234 ABC / 1234 ABC / 1234 ABC / 1234 ABC / 1234 ABC / 1234");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertBadRequest(reply, integrationResponse);
	}
	
	/*
	 * NUMTELEFONE
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_numTelefone_have_letters() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_NUMTELEFONE);
		String bodyModified = MessageFormat.format(body, "ABCD");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_throw_xmlValidationException_when_numTelefone_have_numbers_and_letters() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_NUMTELEFONE);
		String bodyModified = MessageFormat.format(body, "1234-1234");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_pass_xmlValidation_when_numTelefone_is_empty() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_NUMTELEFONE);
		String bodyModified = MessageFormat.format(body, "");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertOK(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_pass_xmlValidation_when_numTelefone_is_number() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_NUMTELEFONE);
		String bodyModified = MessageFormat.format(body, "318877102");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertOK(reply, integrationResponse);
	}
	
	/*
	 * CARGO
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_cargo_have_more_than_100_characters() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_CARGO);
		String bodyModified = MessageFormat.format(body, "CARGO CARGO CARGO CARGO CARGO CARGO CARGO CARGO CARGO CARGO CARGO CARGO CARGO CARGO CARGO CARGO CARGO CARGO");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_pass_xmlValidation_when_cargo_have_letters() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_CARGO);
		String bodyModified = MessageFormat.format(body, "CARGO / CARGO / CARGO");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertOK(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_pass_xmlValidation_when_cargo_is_empty() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_CARGO);
		String bodyModified = MessageFormat.format(body, "");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertOK(reply, integrationResponse);
	}
	
	/*
	 * CONTRATO
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_contrato_have_more_than_255_characters() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_CONTRATO);
		String bodyModified = MessageFormat.format(body, "CONTRATO CONTRATO CONTRATO CONTRATO CONTRATO CONTRATO CONTRATO CONTRATO CONTRATO CONTRATO CONTRATO CONTRATO CONTRATO CONTRATO CONTRATO CONTRATO CONTRATO CONTRATO CONTRATO CONTRATO CONTRATO CONTRATO CONTRATO CONTRATO CONTRATO CONTRATO CONTRATO CONTRATO CONTRATO CONTRATO");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_pass_xmlValidation_when_contrato_have_letters_and_numbers() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_CONTRATO);
		String bodyModified = MessageFormat.format(body, "CONTRATO CONTRATO CONTRATO 12345");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertOK(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_pass_xmlValidation_when_contrato_is_empty() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_CONTRATO);
		String bodyModified = MessageFormat.format(body, "");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertOK(reply, integrationResponse);
	}
	
	/*
	 * AREAFUNCAO
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_areaFuncao_have_more_than_255_characters() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_AREAFUNCAO);
		String bodyModified = MessageFormat.format(body, "Cliente - Atendimento Cliente - Atendimento Cliente - Atendimento Cliente - Atendimento Cliente - Atendimento Cliente - Atendimento Cliente - Atendimento Cliente - Atendimento Cliente - Atendimento Cliente - Atendimento Cliente - Atendimento Cliente - Atendimento Cliente - Atendimento");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_pass_xmlValidation_when_areaFuncao_have_numbers_and_letters() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_AREAFUNCAO);
		String bodyModified = MessageFormat.format(body, "Cliente - Atendimento Cliente");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertOK(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_pass_xmlValidation_when_areaFuncao_is_empty() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_AREAFUNCAO);
		String bodyModified = MessageFormat.format(body, "");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertOK(reply, integrationResponse);
	}
	
	/*
	 * SITE
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_site_have_more_than_255_characters() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_SITE);
		String bodyModified = MessageFormat.format(body, "035 - SP-S.B.CAMPO(ADM) 035 - SP-S.B.CAMPO(ADM) 035 - SP-S.B.CAMPO(ADM) 035 - SP-S.B.CAMPO(ADM) 035 - SP-S.B.CAMPO(ADM) 035 - SP-S.B.CAMPO(ADM) 035 - SP-S.B.CAMPO(ADM) 035 - SP-S.B.CAMPO(ADM) 035 - SP-S.B.CAMPO(ADM) 035 - SP-S.B.CAMPO(ADM) 035 - SP-S.B.CAMPO(ADM) 035 - SP-S.B.CAMPO(ADM) 035 - SP-S.B.CAMPO(ADM)");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_pass_xmlValidation_when_site_have_letters_and_numbers() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_SITE);
		String bodyModified = MessageFormat.format(body, "035 - SP-S.B.CAMPO(ADM)");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertOK(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_pass_xmlValidation_when_site_is_empty() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_SITE);
		String bodyModified = MessageFormat.format(body, "");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertOK(reply, integrationResponse);
	}
	
	/*
	 * NOMECONSULTORIA
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_nomeConsultoria_have_more_than_150_characters() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_NOMECONSULTORIA);
		String bodyModified = MessageFormat.format(body, "ATENTO BRASIL SA ATENTO BRASIL SA ATENTO BRASIL SA ATENTO BRASIL SA ATENTO BRASIL SA ATENTO BRASIL SA ATENTO BRASIL SA ATENTO BRASIL SA ATENTO BRASIL SA");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_pass_xmlValidation_when_nomeConsultoria_have_letters_and_numbers() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_NOMECONSULTORIA);
		String bodyModified = MessageFormat.format(body, "ATENTO BRASIL SA");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertOK(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_pass_xmlValidation_when_nomeConsultoria_is_empty() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_NOMECONSULTORIA);
		String bodyModified = MessageFormat.format(body, "");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertOK(reply, integrationResponse);
	}
	
	/*
	 * RG
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_rg_have_more_than_20_characters() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_RG);
		String bodyModified = MessageFormat.format(body, "121435234ABC-AASASAEAAAAAAA");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_pass_xmlValidation_when_rg_have_letters_and_numbers() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_RG);
		String bodyModified = MessageFormat.format(body, "21542755-0");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertOK(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_pass_xmlValidation_when_rg_is_empty() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_RG);
		String bodyModified = MessageFormat.format(body, "");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertOK(reply, integrationResponse);
	}
	
	/*
	 * EMAILCORPORATIVO
	 */
	@Test
	public void startRoute_should_throw_xmlValidationException_when_emailCorporativo_have_more_than_150_characters() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_EMAILCORPORATIVO);
		String bodyModified = MessageFormat.format(body, "email@teste.com.bremail@teste.com.bremail@teste.com.bremail@teste.com.bremail@teste.com.bremail@teste.com.brcom.bremail@teste.com.brcom.bremail@teste.com.br");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertBadRequest(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_pass_xmlValidation_when_emailCorporativo_have_letters_and_numbers() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_EMAILCORPORATIVO);
		String bodyModified = MessageFormat.format(body, "email123@teste.com.br");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertOK(reply, integrationResponse);
	}
	
	@Test
	public void startRoute_should_pass_xmlValidation_when_emailCorporativo_is_empty() throws IOException, JAXBException {
		// Arrange
		String body = util.getFileContent(XML_FOLDER + XML_EMAILCORPORATIVO);
		String bodyModified = MessageFormat.format(body, "");
		byte[] bodyCompressed = TestUtil.compress(bodyModified);
		
		// Act
		Response reply = createProxy("http://localhost:1114/hub/rest/atento/ativos").ativos(bodyCompressed);
		IntegrationResponse integrationResponse = TestUtil.createResponseFromEntity((InputStream) reply.getEntity());
		
		// Assert
		assertOK(reply, integrationResponse);
	}
}