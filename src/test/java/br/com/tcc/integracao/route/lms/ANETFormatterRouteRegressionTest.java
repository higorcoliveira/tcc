package br.com.tcc.integracao.route.lms;

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

import br.com.tcc.integracao.entity.xml.Ativo;
import br.com.tcc.integracao.entity.xml.Ativos;
import br.com.tcc.integracao.test.util.CamelTestTemplate;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@RunWith(CamelSpringJUnit4ClassRunner.class)
public class ANETFormatterRouteRegressionTest extends CamelTestTemplate {

	private static final String ANET_FORMATTER_ROUTE_NAME = "ANETFormatterRoute";
	private static final String ENDPOINT_CONSUMER = "direct:anetFormatter";
	
	@Override
	public void init() throws Exception {
		RouteDefinition route = getCamelContext().getRouteDefinition(ANET_FORMATTER_ROUTE_NAME);
		
		route.adviceWith(getCamelContext(), new AdviceWithRouteBuilder() {
			@Override
			public void configure() throws Exception {
				
				interceptSendToEndpoint("checkpoint:error*")
				  .skipSendToOriginalEndpoint()
				  .to("mock:error");
				
				interceptSendToEndpoint("checkpoint:bean*")
				  .skipSendToOriginalEndpoint()
				  .to("mock:checkpoint");
				
				interceptSendToEndpoint("direct:insertParticipantArea")
				  .skipSendToOriginalEndpoint()
				  .to("mock:directArea");
				
				interceptSendToEndpoint("direct:insertParticipantHierarchy")
				  .skipSendToOriginalEndpoint()
				  .to("mock:directHierarchy");
			}
		});
	}
	
	@Test
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetThreeParticipants.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	@DatabaseTearDown(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetThreeParticipants.xml", type = DatabaseOperation.DELETE_ALL)
	public void anetFormatterRoute_should_insert_two_participants() throws CamelExecutionException, Exception {
		// Arrange
		Ativos ativos = new Ativos();
		Set<Ativo> listAtivos = new HashSet<>();
		listAtivos.add(createAtivo("123456", "John Paul", "john@teste.com"));
		listAtivos.add(createAtivo("654321", "Israel D. Rodrigues", "israel@teste.com"));
		
		ativos.setAtivos(listAtivos);
		
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, ativos);
		Thread.sleep(4000);
		stopProducerTemplate();
	}
	
	/*
	 * utilitarios
	 */
	private Ativo createAtivo(String re, String name, String email) throws ParseException {
		Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2014-02-19");
		Date birthday = new SimpleDateFormat("yyyy-MM-dd").parse("1985-12-04");
		
		Ativo ativo = new Ativo();
		ativo.setSite("94 - SALVADOR ADM");
		ativo.setRoleGroup("GERENCIA (M3)");
		ativo.setRe(re);
		ativo.setName(name);
		ativo.setEmail(email);
		ativo.setPassword("abc");
		ativo.setActive(true);
		ativo.setGender("M");
		ativo.setCpf("10927857790");
		ativo.setContract("0040001250 - CORPORATIVO");
		ativo.setReSuperior("147845");
		ativo.setCivilState(1);
		ativo.setScholarity(5);
		ativo.setCostCenter(400510);
		ativo.setStartDate(startDate);
		ativo.setRg("563948218-0");
		ativo.setExecutiveDirectory("NEGOCIOS TELEFONICA/VIVO");
		ativo.setConsultancyName("ATENTO CONSULTORIA RIBEIRAO PRETO");
		ativo.setBirthday(birthday);
		
		return ativo;
	}
}
