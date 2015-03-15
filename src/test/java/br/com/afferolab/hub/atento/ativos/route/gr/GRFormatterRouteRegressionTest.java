package br.com.afferolab.hub.atento.ativos.route.gr;

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
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@RunWith(CamelSpringJUnit4ClassRunner.class)
public class GRFormatterRouteRegressionTest extends CamelTestTemplate {

	private static final String GR_FORMATTER_ROUTE_NAME = "GRFormatterRoute";
	private static final String ENDPOINT_CONSUMER = "direct:gestaoRecursosFormatter";
	
	@Override
	public void init() throws Exception {
		RouteDefinition route = getCamelContext().getRouteDefinition(GR_FORMATTER_ROUTE_NAME);
		
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
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGROneEmployee.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGRTwoEmployee.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	@DatabaseTearDown(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGRTwoEmployee.xml", type = DatabaseOperation.DELETE_ALL)
	public void grFormatterRoute_should_insert_one_employee_and_one_cost_center() throws CamelExecutionException, Exception {
		// Arrange
		Ativos ativos = new Ativos();
		Set<Ativo> listAtivos = new HashSet<>();
		listAtivos.add(createAtivo(300987, "123456"));
		
		ativos.setAtivos(listAtivos);
		
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, ativos);
		Thread.sleep(4000);
		stopProducerTemplate();
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGROneEmployee.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGRThreeEmployee.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	@DatabaseTearDown(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGRThreeEmployee.xml", type = DatabaseOperation.DELETE_ALL)
	public void grFormatterRoute_should_insert_two_employees_and_two_cost_centers() throws CamelExecutionException, Exception {
		// Arrange
		Ativos ativos = new Ativos();
		Set<Ativo> listAtivos = new HashSet<>();
		listAtivos.add(createAtivo(300987, "123456"));
		listAtivos.add(createAtivo(300988, "654321"));
		
		ativos.setAtivos(listAtivos);
		
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, ativos);
		Thread.sleep(4000);
		stopProducerTemplate();
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGROneEmployee.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGROneEmployeeAndSameCostCenter.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	@DatabaseTearDown(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGROneEmployeeAndSameCostCenter.xml", type = DatabaseOperation.DELETE_ALL)
	public void grFormatterRoute_should_insert_one_employee_and_none_cost_center() throws CamelExecutionException, Exception {
		// Arrange
		Ativos ativos = new Ativos();
		Set<Ativo> listAtivos = new HashSet<>();
		listAtivos.add(createAtivo(111111, "123456"));
		
		ativos.setAtivos(listAtivos);
		
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, ativos);
		Thread.sleep(4000);
		stopProducerTemplate();
	}
	
	/*
	 * utilitarios
	 */
	private Ativo createAtivo(Integer costCenter, String re) {
		Ativo ativo = new Ativo();
		ativo.setName("John Paul");
		ativo.setRe(re);
		ativo.setEmail("john@teste.com");
		ativo.setPassword("123");
		ativo.setActive(true);
		ativo.setCpf("10927857790");
		ativo.setContract("0040001250 - CORPORATIVO");
		ativo.setPhone("3232323232");
		ativo.setCostCenter(costCenter);
		ativo.setRoleGroup("GERENCIA (M3)");
		
		return ativo;
	}
}