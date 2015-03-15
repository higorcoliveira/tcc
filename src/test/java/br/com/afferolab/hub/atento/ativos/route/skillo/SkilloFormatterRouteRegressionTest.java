package br.com.afferolab.hub.atento.ativos.route.skillo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelSpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.afferolab.hub.atento.ativos.entity.xml.Ativo;
import br.com.afferolab.hub.atento.ativos.entity.xml.Ativos;
import br.com.afferolab.hub.atento.util.CamelTestTemplate;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@RunWith(CamelSpringJUnit4ClassRunner.class)
public class SkilloFormatterRouteRegressionTest extends CamelTestTemplate {

	private static final String SKILLO_FORMATTER_ROUTE_NAME = "SkilloFormatterRoute";
	private static final String ENDPOINT_CONSUMER = "direct:skilloFormatter";
	
	@Override
	public void init() throws Exception {
		RouteDefinition route = getCamelContext().getRouteDefinition(SKILLO_FORMATTER_ROUTE_NAME);
		
		route.adviceWith(getCamelContext(), new AdviceWithRouteBuilder() {
			@Override
			public void configure() throws Exception {
				
				interceptSendToEndpoint("checkpoint:error*")
				  .skipSendToOriginalEndpoint()
				  .to("mock:error");
				
				interceptSendToEndpoint("checkpoint:bean*")
				  .skipSendToOriginalEndpoint()
				  .to("mock:checkpoint");
				
				interceptSendToEndpoint("seda:checkForPersistRoute")
				  .skipSendToOriginalEndpoint()
				  .to("mock:direct");
			}
		});
	}
	
	@Test
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/skillo/bdSkilloTwoEmployees.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	@DatabaseTearDown(value = "/datasets/br/com/afferolab/hub/atento/ativos/skillo/bdSkilloTwoEmployees.xml", type = DatabaseOperation.DELETE_ALL)
	public void skilloFormatterRoute_should_insert_two_employees() throws CamelExecutionException, Exception {
		// Arrange
		Ativos ativos = new Ativos();
		Set<Ativo> listAtivos = new HashSet<>();
		listAtivos.add(createAtivo("102039", "John Paul", "29647570899"));
		listAtivos.add(createAtivo("109593", "Israel D. Rodrigues", "25956883839"));
		
		ativos.setAtivos(listAtivos);
		
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, ativos);
		Thread.sleep(4000);
		stopProducerTemplate();
	}
	
	private Ativo createAtivo(String re, String name, String cpf) throws ParseException {
		Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2014-02-19");
		Date birthday = new SimpleDateFormat("yyyy-MM-dd").parse("1985-12-04");
		
		Ativo ativo = new Ativo();
		
		ativo.setRe(re);
		ativo.setName(name);
		ativo.setEmail("teste@teste.com");
		ativo.setStartDate(startDate);
		ativo.setBirthday(birthday);
		ativo.setLoginEnabled(true);
		ativo.setExpiredPassword(false);
		ativo.setManager(false);
		ativo.setRoleGroup("Supervisão (O7)");
		ativo.setReSuperior("898797");
		ativo.setActive(true);
		ativo.setFunctionalSituation(5);
		ativo.setCpf(cpf);
		ativo.setGender("M");
		ativo.setCostCenter(331707);
		ativo.setCivilState(3);
		ativo.setScholarity(5);
		ativo.setPhone("1138895205");
		ativo.setRole("SUPERVISOR SUPORTE TECNICO I");
		ativo.setContract("40005260 - LIBER - PROATIVIDADE AVANCADA");
		ativo.setSite("032 - SP-SAO PAULO-LIBERDADE(OP)");
		ativo.setExecutiveDirectory("NEGOCIOS TELEFONICA/VIVO");
		ativo.setConsultancyName("ATENTO BRASIL SA");
		ativo.setRg("305182183-0");
		ativo.setFunctionArea("Cliente - Atendimento");
		
		return ativo;
	}
}