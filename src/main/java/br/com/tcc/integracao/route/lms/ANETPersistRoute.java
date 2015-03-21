package br.com.tcc.integracao.route.lms;

import java.sql.SQLException;

import org.apache.camel.LoggingLevel;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import br.com.tcc.integracao.ExecuteQueryCompanyArea;
import br.com.tcc.integracao.ExecuteQueryCompanyHierarchy;
import br.com.tcc.integracao.ExecuteQueryHierarchy;
import br.com.tcc.integracao.ExecuteQueryParticipant;
import br.com.tcc.integracao.bean.lms.ConvertResultToCompanyArea;
import br.com.tcc.integracao.bean.lms.ConvertResultToCompanyHierarchy;
import br.com.tcc.integracao.util.RouteConstants;

@Component
public class ANETPersistRoute extends SpringRouteBuilder {

	@Override
	@SuppressWarnings("unchecked")
	public void configure() throws Exception {

		errorHandler(defaultErrorHandler()
	    .maximumRedeliveries(3)
	    .redeliveryDelay(1000)
	    .retryAttemptedLogLevel(LoggingLevel.ERROR));
						
		onException(IllegalArgumentException.class, SQLException.class)
	    .handled(true)
	    .setHeader(RouteConstants.SYSTEM_FAIL, simple("ANET"))
	    .setBody(simple("false"))
	    .to("seda:checkForCompletedRoute");
		
		onException(Throwable.class)
		.handled(true)
		.setHeader(RouteConstants.SYSTEM_FAIL, simple("ANET"))
		.setBody(simple("false"))
	    .to("seda:checkForCompletedRoute");
		
		from("direct:anetPersist").routeId("ANETPersistRoute")
		.setHeader(RouteConstants.HEADER_FINISHED, simple("true"))
		
		// COMPANY AREA		
		.to("checkpoint:bean?message=[ANET] Iniciando inserção/alteração de Empresa Area...")
		.bean(ExecuteQueryCompanyArea.class)
		.to("checkpoint:bean?message=[ANET] Discriminando dados de Empresa Area a serem inseridos/atualizados...")
		.to("jdbc:dataSource")
		.bean(ConvertResultToCompanyArea.class)
		.to("checkpoint:bean?message=[ANET] Dados sendo inseridos/atualizados na Empresa Area...")
		.split(body()).stopOnException()
		  .choice()
		    .when(simple("${body.isInsert} == 1"))
		      .to("mybatisANET:insertAllANETCompanyAreas?statementType=InsertList")
		  .end()
		.end()
		.to("checkpoint:bean?message=[ANET] Dados de Empresa Area inseridos com sucesso...")
		
		// COMPANY HIERARCHY
		.to("checkpoint:bean?message=[ANET] Iniciando inserção/alteração de Empresa Hierarquia...")
		.bean(ExecuteQueryCompanyHierarchy.class)
		.to("checkpoint:bean?message=[ANET] Discriminando dados de Empresa Hierarquia a serem inseridos/atualizados...")
		.to("jdbc:dataSource")
		.bean(ConvertResultToCompanyHierarchy.class)
		.to("checkpoint:bean?message=[ANET] Dados sendo inseridos/atualizados na Empresa Hierarquia...")
		.split(body()).stopOnException()
		  .choice()
		    .when(simple("${body.isInsert} == 1"))
		      .to("mybatisANET:insertAllANETCompanyHierarchies?statementType=InsertList")
		  .end()
		.end()
		.to("checkpoint:bean?message=[ANET] Dados de Empresa Hierarquia inseridos com sucesso...")
		
		// PARTICIPANT INSERT
		.to("checkpoint:bean?message=[ANET] Iniciando inserção de Participantes...")
		.bean(ExecuteQueryParticipant.class, "toInsertSQL")
		.to("checkpoint:bean?message=[ANET] Discriminando dados de Participantes a serem inseridos...")
		.to("checkpoint:bean?message=[ANET] Dados sendo inseridos na Participante...")
		.to("jdbc:dataSource")
		.to("checkpoint:bean?message=[ANET] Dados de Participantes inseridos com sucesso...")
		
		// PARTICIPANT UPDATE
		.to("checkpoint:bean?message=[ANET] Iniciando alteração de Participantes...")
		.bean(ExecuteQueryParticipant.class, "toUpdateSQL")
		.to("checkpoint:bean?message=[ANET] Discriminando dados de Participantes a serem alterados...")
		.to("checkpoint:bean?message=[ANET] Dados sendo atualizados na Participante...")
		.to("jdbc:dataSource")
		.to("checkpoint:bean?message=[ANET] Dados de Participantes atualizados com sucesso...")
		
		// HIERARCHY INSERT
		.to("checkpoint:bean?message=[ANET] Iniciando alteração de Gerente Participante...")
		.bean(ExecuteQueryHierarchy.class, "toDeleteSQL")
		.to("checkpoint:bean?message=[ANET] Removendo hierarquias antigas...")
		.to("jdbc:dataSource")
		.to("checkpoint:bean?message=[ANET] Hierarquias antigas removidas com sucesso...")
		.bean(ExecuteQueryHierarchy.class, "toInsertSQL")
		.to("checkpoint:bean?message=[ANET] Inserindo hierarquias...")
		.to("jdbc:dataSource")
		.to("checkpoint:bean?message=[ANET] Hierarquias inseridas com sucesso...")
		
		.setBody(simple("true"))
		.to("seda:checkForCompletedRoute");
	}
}