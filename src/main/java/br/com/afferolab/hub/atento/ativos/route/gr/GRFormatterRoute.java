package br.com.afferolab.hub.atento.ativos.route.gr;

import org.apache.camel.LoggingLevel;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import br.com.afferolab.hub.atento.ativos.bean.BatchSizePredicate;
import br.com.afferolab.hub.atento.ativos.bean.ListAggregationStrategy;
import br.com.afferolab.hub.atento.ativos.bean.gr.GRFilter;
import br.com.afferolab.hub.atento.ativos.bean.gr.GRFormatter;
import br.com.afferolab.hub.atento.ativos.bean.gr.MergeStrategyCostCenter;
import br.com.afferolab.hub.atento.ativos.util.CopyMessage;
import br.com.afferolab.hub.atento.ativos.util.RouteConstants;

@Component
public class GRFormatterRoute extends SpringRouteBuilder {

	@Override
	public void configure() throws Exception {
		
		errorHandler(defaultErrorHandler()
	    .maximumRedeliveries(3)
	    .redeliveryDelay(1000)
	    .retryAttemptedLogLevel(LoggingLevel.ERROR));
						
		onException(IllegalArgumentException.class)
        .handled(true)
        .setHeader(RouteConstants.SYSTEM_FAIL, simple("GR"))
        .setBody(simple("false"))
        .to("seda:checkForPersistRoute");
		
		onException(Throwable.class)
		.handled(true)
		.setHeader(RouteConstants.SYSTEM_FAIL, simple("GR"))
		.setBody(simple("false"))
        .to("seda:checkForPersistRoute");
		
		from("direct:gestaoRecursosFormatter").routeId("GRFormatterRoute")
		.setHeader(RouteConstants.HEADER_STATUS, simple("true"))
		.bean(CopyMessage.class)
		.to("checkpoint:bean?message=[GR] Filtrando dados...")
		.bean(GRFilter.class)
		.to("checkpoint:bean?message=[GR] Dados filtrados com sucesso...")
		.bean(GRFormatter.class)
		.to("checkpoint:bean?message=[GR] Dados formatados com sucesso...")
		.to("checkpoint:bean?message=[GR] Employees sendo inseridos na intermediária...")
		.split(body()).stopOnException()
			.aggregate(constant(true), new ListAggregationStrategy())
			.completionPredicate(new BatchSizePredicate(RouteConstants.BATCH_SIZE))
			.completionTimeout(RouteConstants.BATCH_TIME_OUT)
				.to("mybatis:insertGrEmployee?statementType=InsertList")
			.end()
		.end()
		.to("checkpoint:bean?message=[GR] Employees inseridos com sucesso na intermediária...")
		.enrich("mybatis:selectAllCostCenters?statementType=SelectList", new MergeStrategyCostCenter())
		.choice()
		  .when(simple("${body.size()} > 0"))
		  	.to("mybatis:insertGrCostCenter?statementType=InsertList")
		  	.to("checkpoint:bean?message=[GR] CostCenters inseridos com sucesso na intermediária...")
		  .otherwise()
		    .to("checkpoint:bean?message=[GR] Não existe nenhum costCenter para ser atualizado...")
		 .end()
		.setBody(simple("true"))
		.to("checkpoint:bean?message=[GR] Enviando para rota de inserção/atualização dos dados...")
		.to("seda:checkForPersistRoute");
	}
}
