package br.com.afferolab.hub.atento.ativos.route.gr;

import java.sql.SQLException;

import org.apache.camel.LoggingLevel;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import br.com.afferolab.hub.atento.ativos.bean.ExecuteQueryCostCenter;
import br.com.afferolab.hub.atento.ativos.bean.ExecuteQueryEmployee;
import br.com.afferolab.hub.atento.ativos.bean.gr.ConvertResultToCostCenter;
import br.com.afferolab.hub.atento.ativos.util.RouteConstants;

@Component
public class GRPersistRoute extends SpringRouteBuilder {

	@Override
	@SuppressWarnings("unchecked")
	public void configure() throws Exception {

		errorHandler(defaultErrorHandler()
	    .maximumRedeliveries(3)
	    .redeliveryDelay(1000)
	    .retryAttemptedLogLevel(LoggingLevel.ERROR));
						
		onException(IllegalArgumentException.class, SQLException.class)
        .handled(true)
        .setHeader(RouteConstants.SYSTEM_FAIL, simple("GR"))
        .setBody(simple("false"))
        .to("seda:checkForCompletedRoute");
		
		onException(Throwable.class)
		.handled(true)
		.setHeader(RouteConstants.SYSTEM_FAIL, simple("GR"))
		.setBody(simple("false"))
        .to("seda:checkForCompletedRoute");

		from("direct:gestaoRecursosPersist").routeId("GRPersistRoute")
		
		// COST CENTER
		.setHeader(RouteConstants.HEADER_FINISHED, simple("true"))
		.to("checkpoint:bean?message=[GR] Iniciando inserção/alteração do Cost Center...")
		.bean(ExecuteQueryCostCenter.class)
		.to("checkpoint:bean?message=[GR] Discriminando dados do Cost Center a serem inseridos/atualizados...")
		.to("jdbc:dataSource")
		.bean(ConvertResultToCostCenter.class)
		.to("checkpoint:bean?message=[GR] Dados sendo inseridos/atualizados no Cost Center...")
		.split(body()).stopOnException()
		  .choice()
		    .when(simple("${body.isInsert} == 1"))
		      .to("mybatisGR:insertAllGRCostCenters?statementType=InsertList")
		      .otherwise()
		      .to("mybatisGR:updateAllGRCostCenter?statementType=Update")
		  .end()
		.end()
		.to("checkpoint:bean?message=[GR] Dados do Cost Center inseridos/atualizados com sucesso...")
		
		// EMPLOYEE INSERT
		.to("checkpoint:bean?message=[GR] Iniciando inserção do Employee...")
		.bean(ExecuteQueryEmployee.class, "toInsertSQL")
		.to("checkpoint:bean?message=[GR] Discriminando dados do Employee a serem inseridos...")
		.to("checkpoint:bean?message=[GR] Dados sendo inseridos na Employee...")
		.to("jdbc:dataSource")
		.to("checkpoint:bean?message=[GR] Dados de Employee inseridos com sucesso...")
		
		// ROLE EMPLOYEE INSERT
		.to("checkpoint:bean?message=[GR] Iniciando inserção do Role Employee...")
		.bean(ExecuteQueryEmployee.class, "toInsertPoolSQL")
		.to("checkpoint:bean?message=[GR] Discriminando dados do Role Employee a serem inseridos...")
		.to("checkpoint:bean?message=[GR] Dados sendo inseridos na Role Employee...")
		.to("jdbc:dataSource")
		.to("checkpoint:bean?message=[GR] Dados de Role Employee inseridos com sucesso...")
		
		// EMPLOYEE UPDATE
		.to("checkpoint:bean?message=[GR] Iniciando alteração do Employee...")
		.bean(ExecuteQueryEmployee.class, "toUpdateSQL")
		.to("checkpoint:bean?message=[GR] Discriminando dados do Employee a serem alterados...")
		.to("checkpoint:bean?message=[GR] Dados sendo alterados na Employee...")
		.to("jdbc:dataSource")
		.to("checkpoint:bean?message=[GR] Dados de Employee alterados com sucesso...")
		
		.setBody(simple("true"))
		.to("seda:checkForCompletedRoute");
	}
}
