package br.com.tcc.integracao.route.lms;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelSpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.tcc.integracao.test.util.CamelTestTemplate;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@RunWith(CamelSpringJUnit4ClassRunner.class)
public class ANETPersistRouteRegressionTest extends CamelTestTemplate {

	private static final String ANET_PERSIST_ROUTE_NAME = "ANETPersistRoute";
	private static final String ENDPOINT_CONSUMER = "direct:anetPersist";
	
	@Override
	public void init() throws Exception {
		RouteDefinition route = getCamelContext().getRouteDefinition(ANET_PERSIST_ROUTE_NAME);
		
		route.adviceWith(getCamelContext(), new AdviceWithRouteBuilder() {
			@Override
			public void configure() throws Exception {
				
				interceptSendToEndpoint("checkpoint:error*")
				  .skipSendToOriginalEndpoint()
				  .to("mock:error");
				
				interceptSendToEndpoint("checkpoint:bean*")
				  .skipSendToOriginalEndpoint()
				  .to("mock:checkpoint");
				
				interceptSendToEndpoint("seda:checkForCompletedRoute")
				  .skipSendToOriginalEndpoint()
				  .to("mock:direct");
			}
		});
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetTwoCompayAreaToInsert.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetTwoCompanyAreasInserted.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	@DatabaseTearDown(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetTwoCompanyAreasInserted.xml", type = DatabaseOperation.DELETE_ALL)
	public void anetPersistRoute_should_insert_two_company_areas() throws CamelExecutionException, Exception {
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, "");
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetNoneCompayAreaToInsert.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetNoneCompanyAreaInserted.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	@DatabaseTearDown(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetNoneCompanyAreaInserted.xml", type = DatabaseOperation.DELETE_ALL)
	public void anetPersistRoute_shouldnt_insert_company_area() throws CamelExecutionException, Exception {
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, "");
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetTwoCompayHierarchyToInsert.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetTwoCompanyHierarchiesInserted.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	@DatabaseTearDown(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetTwoCompanyHierarchiesInserted.xml", type = DatabaseOperation.DELETE_ALL)
	public void anetPersistRoute_should_insert_two_company_hierarchies() throws CamelExecutionException, Exception {
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, "");
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetNoneCompayHierarchyToInsert.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetNoneCompanyHierarchyInserted.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	@DatabaseTearDown(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetNoneCompanyHierarchyInserted.xml", type = DatabaseOperation.DELETE_ALL)
	public void anetPersistRoute_shouldnt_insert_company_hierarchy() throws CamelExecutionException, Exception {
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, "");
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetTwoParticipantsToInsert.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetTwoParticipantsInserted.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	@DatabaseTearDown(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetTwoParticipantsInserted.xml", type = DatabaseOperation.DELETE_ALL)
	public void anetPersistRoute_should_insert_two_participants() throws CamelExecutionException, Exception {
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, "");
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetTwoParticipantsToUpdate.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetTwoParticipantsUpdated.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	@DatabaseTearDown(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetTwoParticipantsUpdated.xml", type = DatabaseOperation.DELETE_ALL)
	public void anetPersistRoute_should_update_two_participants() throws CamelExecutionException, Exception {
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, "");
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetTwoParticipantsToInsertAndUpdate.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetTwoParticipantsInsertedAndUpdated.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	@DatabaseTearDown(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetTwoParticipantsInsertedAndUpdated.xml", type = DatabaseOperation.DELETE_ALL)
	public void anetPersistRoute_should_insert_and_update_two_participants() throws CamelExecutionException, Exception {
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, "");
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetOneParticipantDiffREToUpdate.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetOneParticipantDiffREToUpdated.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	@DatabaseTearDown(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetOneParticipantDiffREToUpdated.xml", type = DatabaseOperation.DELETE_ALL)
	public void anetPersistRoute_should_update_a_participant_with_different_re() throws CamelExecutionException, Exception {
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, "");
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetInsertHierarchies.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetHierarchiesInserted.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	@DatabaseTearDown(value = "/datasets/br/com/afferolab/hub/atento/ativos/anet/bdAnetHierarchiesInserted.xml", type = DatabaseOperation.DELETE_ALL)
	public void anetPersistRoute_should_insert_hierarchies() throws CamelExecutionException, Exception {
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, "");
	}
}