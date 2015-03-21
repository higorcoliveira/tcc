package br.com.tcc.integracao.route.lms;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelSpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.tcc.integracao.entity.xml.Ativo;
import br.com.tcc.integracao.test.util.CamelTestTemplate;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@RunWith(CamelSpringJUnit4ClassRunner.class)
public class ANETInsertParticipantAreaRouteRegressionTest extends CamelTestTemplate {

	private static final String ANET_INSERT_PARTICIPANTE_AREA_ROUTE_NAME = "ANETInsertParticipantAreaRoute";
	private static final String ENDPOINT_CONSUMER = "direct:insertParticipantArea";
	
	@Override
	public void init() throws Exception {
		
		RouteDefinition route = getCamelContext().getRouteDefinition(ANET_INSERT_PARTICIPANTE_AREA_ROUTE_NAME);
		
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
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdOneArea.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdOneAreaInserted.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void anetInsertParticipantAreaRoute_should_insert_one_area() throws CamelExecutionException, Exception {
		// Arrange
		List<Ativo> listAtivos = new ArrayList<>();
		listAtivos.add(createSite("50 - SAO BERNARDO DO CAMPO"));
		
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, listAtivos);
		stopProducerTemplate();
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdOneArea.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdOneArea.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void anetInsertParticipantAreaRoute_should_not_insert_area() throws CamelExecutionException, Exception {
		// Arrange
		List<Ativo> listAtivos = new ArrayList<>();
		listAtivos.add(createSite("49 - SAO BERNARDO DO CAMPO ADM"));
		
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, listAtivos);
		stopProducerTemplate();
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdOneArea.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdTwoAreasInserted.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void anetInsertParticipantAreaRoute_should_insert_two_areas() throws CamelExecutionException, Exception {
		// Arrange
		List<Ativo> listAtivos = new ArrayList<>();
		listAtivos.add(createSite("50 - SAO BERNARDO DO CAMPO"));
		listAtivos.add(createSite("51 - SAO PAULO"));
		
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, listAtivos);
		stopProducerTemplate();
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdOneArea.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdOneAreaInserted.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void anetInsertParticipantAreaRoute_should_insert_one_area_when_area_is_repeated() throws CamelExecutionException, Exception {
		// Arrange
		List<Ativo> listAtivos = new ArrayList<>();
		listAtivos.add(createSite("50 - SAO BERNARDO DO CAMPO"));
		listAtivos.add(createSite("50 - SAO BERNARDO DO CAMPO"));
		
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, listAtivos);
		stopProducerTemplate();
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdOneArea.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdOneAreaInserted.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void anetInsertParticipantAreaRoute_should_insert_one_area_when_area_have_in_db() throws CamelExecutionException, Exception {
		// Arrange
		List<Ativo> listAtivos = new ArrayList<>();
		listAtivos.add(createSite("49 - SAO BERNARDO DO CAMPO ADM"));
		listAtivos.add(createSite("50 - SAO BERNARDO DO CAMPO"));
		
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, listAtivos);
		stopProducerTemplate();
	}
	
	/*
	 * utilitario
	 */
	private Ativo createSite(String area) {
		Ativo ativo = new Ativo();
		ativo.setSite(area);
		
		return ativo;
	}
}
