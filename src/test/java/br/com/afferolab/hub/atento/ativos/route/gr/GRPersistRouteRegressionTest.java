package br.com.afferolab.hub.atento.ativos.route.gr;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelSpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import br.com.afferolab.hub.atento.util.CamelTestTemplate;

@RunWith(CamelSpringJUnit4ClassRunner.class)
public class GRPersistRouteRegressionTest extends CamelTestTemplate {

	private static final String GR_PERSIST_ROUTE_NAME = "GRPersistRoute";
	private static final String ENDPOINT_CONSUMER = "direct:gestaoRecursosPersist";
	
	@Override
	public void init() throws Exception {
		RouteDefinition route = getCamelContext().getRouteDefinition(GR_PERSIST_ROUTE_NAME);
		
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
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGROneEmployeeToUpdate.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGROneEmployeeUpdated.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void grPersistRoute_should_update_one_employee() throws CamelExecutionException, Exception {
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, "");
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGROneEmployeeToInsert.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGROneEmployeeInserted.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void grPersistRoute_should_insert_one_employee() throws CamelExecutionException, Exception {
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, "");
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGROneEmployeeToUpdateByCpf.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGROneEmployeeUpdatedByCpf.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void grPersistRoute_should_update_one_employee_by_cpf() throws CamelExecutionException, Exception {
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, "");
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGROneEmployeeToUpdateByRE.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGROneEmployeeUpdatedByRE.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void grPersistRoute_should_update_one_employee_by_re() throws CamelExecutionException, Exception {
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, "");
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGROneCostCenterToUpdate.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGROneCostCenterToUpdated.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void grPersistRoute_should_update_one_cost_center() throws CamelExecutionException, Exception {
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, "");
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGROneCostCenterToInsert.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGROneCostCenterToInserted.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void grPersistRoute_should_insert_one_cost_center() throws CamelExecutionException, Exception {
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, "");
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGROneCostCenterAndOneEmployeeToInsert.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGROneCostCenterAndOneEmployeeToInserted.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void grPersistRoute_should_insert_one_employee_and_one_cost_center_when_have_one_employee_and_one_cost_center() throws CamelExecutionException, Exception {
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, "");
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGROneCostCenterAndOneEmployeeToUpdate.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGROneCostCenterAndOneEmployeeToUpdated.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void grPersistRoute_should_update_one_employee_and_one_cost_center_when_have_one_employee_and_one_cost_center() throws CamelExecutionException, Exception {
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, "");
	}
	
	@Test
	@DatabaseSetup(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGROneEmployeeAndOneCostCenterToInsertAndUpdate.xml")
	@ExpectedDatabase(value = "/datasets/br/com/afferolab/hub/atento/ativos/gr/bdGROneEmployeeAndOneCostCenterToInsertedAndUpdated.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void grPersistRoute_should_insert_and_update_employee_and_cost_center_when_have_one_employee_and_one_cost_center() throws CamelExecutionException, Exception {
		// Act
		startProducerTemplate().sendBody(ENDPOINT_CONSUMER, "");
	}
}
