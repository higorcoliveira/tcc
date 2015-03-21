package br.com.tcc.integracao.route.lms;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelSpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import br.com.tcc.integracao.entity.xml.Ativo;
import br.com.tcc.integracao.test.util.CamelTestTemplate;

@RunWith(CamelSpringJUnit4ClassRunner.class)
public class ANETInsertParticipantHierarchyRouteRegressionTest extends CamelTestTemplate {

	private static final String ANET_INSERT_PARTICIPANTE_HIERARCHY_ROUTE_NAME = "ANETInsertParticipantHierarchyRoute";
	private static final String ENDPOINT_CONSUMER = "direct:insertParticipantHierarchy";
	
	@Override
	public void init() throws Exception {
		RouteDefinition route = getCamelContext().getRouteDefinition(ANET_INSERT_PARTICIPANTE_HIERARCHY_ROUTE_NAME);
		
		route.adviceWith(getCamelContext(), new AdviceWithRouteBuilder() {
			@Override
			public void configure() throws Exception {
				
				interceptSendToEndpoint("checkpoint:error*")
				  .skipSendToOriginalEndpoint()
				  .to("mock:error");
				
				interceptSendToEndpoint("checkpoint:bean*")
				  .skipSendToOriginalEndpoint()
				  .to("mock:checkpoint");
				
				interceptSendToEndpoint("seda:anetCheckForPersistRoute")
				  .skipSendToOriginalEndpoint()
				  .to("mock:direct");
			}
		});
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdOneHierarchy.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdOneHierarchyInserted.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void anetInsertParticipantHierarchyRoute_should_insert_one_hierarchy() throws CamelExecutionException, Exception {
		// Arrange
		List<Ativo> listAtivos = new ArrayList<>();
		listAtivos.add(createHierarchy("Gerencia (M4)"));
		
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, listAtivos);
		stopProducerTemplate();
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdOneHierarchy.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdOneHierarchy.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void anetInsertParticipantHierarchyRoute_should_not_insert_hierarchy() throws CamelExecutionException, Exception {
		// Arrange
		List<Ativo> listAtivos = new ArrayList<>();
		listAtivos.add(createHierarchy("Gerencia (M3)"));
		
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, listAtivos);
		stopProducerTemplate();
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdOneHierarchy.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdTwoHierarchiesInserted.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void anetInsertParticipantHierarchyRoute_should_insert_two_hierarchies() throws CamelExecutionException, Exception {
		// Arrange
		List<Ativo> listAtivos = new ArrayList<>();
		listAtivos.add(createHierarchy("Gerencia (M4)"));
		listAtivos.add(createHierarchy("Gerencia (M5)"));
		
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, listAtivos);
		stopProducerTemplate();
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdOneHierarchy.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdOneHierarchyInserted.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void anetInsertParticipantHierarchyRoute_should_insert_one_hierarchy_when_hierarchy_is_repeated() throws CamelExecutionException, Exception {
		// Arrange
		List<Ativo> listAtivos = new ArrayList<>();
		listAtivos.add(createHierarchy("Gerencia (M4)"));
		listAtivos.add(createHierarchy("Gerencia (M4)"));
		
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, listAtivos);
		stopProducerTemplate();
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdOneHierarchy.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdOneHierarchyInserted.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void anetInsertParticipantHierarchyRoute_should_insert_one_hierarchy_when_hierarchy_have_in_db() throws CamelExecutionException, Exception {
		// Arrange
		List<Ativo> listAtivos = new ArrayList<>();
		listAtivos.add(createHierarchy("Gerencia (M3)"));
		listAtivos.add(createHierarchy("Gerencia (M4)"));
		
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, listAtivos);
		stopProducerTemplate();
	}
	
	/*
	 * utilitario
	 */
	private Ativo createHierarchy(String hierarchy) {
		Ativo ativo = new Ativo();
		ativo.setRoleGroup(hierarchy);
		
		return ativo;
	}

}
