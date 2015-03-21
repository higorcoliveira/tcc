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
public class LMSPersistRoute extends SpringRouteBuilder {

	@Override
	@SuppressWarnings("unchecked")
	public void configure() throws Exception {

		errorHandler(defaultErrorHandler()
	    .maximumRedeliveries(3)
	    .redeliveryDelay(1000)
	    .retryAttemptedLogLevel(LoggingLevel.ERROR));
						
		onException(IllegalArgumentException.class, SQLException.class)
	    .handled(true)
	    .setHeader(RouteConstants.SYSTEM_FAIL, simple("LMS"))
	    .setBody(simple("false"))
	    .to("seda:checkForCompletedRoute");
		
		onException(Throwable.class)
		.handled(true)
		.setHeader(RouteConstants.SYSTEM_FAIL, simple("LMS"))
		.setBody(simple("false"))
	    .to("seda:checkForCompletedRoute");
		
		from("direct:lmsPersist").routeId("lmsPersistRoute")
		.setHeader(RouteConstants.HEADER_FINISHED, simple("true"))
		
		// COMPANY AREA		
		.to("checkpoint:bean?message= Iniciando inserção/alteração de Empresa Area...")
		.bean(ExecuteQueryCompanyArea.class)
		.to("checkpoint:bean?message= Discriminando dados de Empresa Area a serem inseridos/atualizados...")
		.to("jdbc:dataSource")
		.bean(ConvertResultToCompanyArea.class)
		.to("checkpoint:bean?message= Dados sendo inseridos/atualizados na Empresa Area...")
		.split(body()).stopOnException()
		  .choice()
		    .when(simple("${body.isInsert} == 1"))
		      .to("mybatisLMS:insertAllLMSCompanyAreas?statementType=InsertList")
		  .end()
		.end()
		.to("checkpoint:bean?message= Dados de Empresa Area inseridos com sucesso...")
		
		// COMPANY HIERARCHY
		.to("checkpoint:bean?message= Iniciando inserção/alteração de Empresa Hierarquia...")
		.bean(ExecuteQueryCompanyHierarchy.class)
		.to("checkpoint:bean?message= Discriminando dados de Empresa Hierarquia a serem inseridos/atualizados...")
		.to("jdbc:dataSource")
		.bean(ConvertResultToCompanyHierarchy.class)
		.to("checkpoint:bean?message= Dados sendo inseridos/atualizados na Empresa Hierarquia...")
		.split(body()).stopOnException()
		  .choice()
		    .when(simple("${body.isInsert} == 1"))
		      .to("mybatisLMS:insertAllLMSCompanyHierarchies?statementType=InsertList")
		  .end()
		.end()
		.to("checkpoint:bean?message= Dados de Empresa Hierarquia inseridos com sucesso...")
		
		// PARTICIPANT INSERT
		.to("checkpoint:bean?message= Iniciando inserção de Participantes...")
		.bean(ExecuteQueryParticipant.class, "toInsertSQL")
		.to("checkpoint:bean?message= Discriminando dados de Participantes a serem inseridos...")
		.to("checkpoint:bean?message= Dados sendo inseridos na Participante...")
		.to("jdbc:dataSource")
		.to("checkpoint:bean?message= Dados de Participantes inseridos com sucesso...")
		
		// PARTICIPANT UPDATE
		.to("checkpoint:bean?message= Iniciando alteração de Participantes...")
		.bean(ExecuteQueryParticipant.class, "toUpdateSQL")
		.to("checkpoint:bean?message=Discriminando dados de Participantes a serem alterados...")
		.to("checkpoint:bean?message=Dados sendo atualizados na Participante...")
		.to("jdbc:dataSource")
		.to("checkpoint:bean?message=Dados de Participantes atualizados com sucesso...")
		
		// HIERARCHY INSERT
		.to("checkpoint:bean?message=Iniciando alteração de Gerente Participante...")
		.bean(ExecuteQueryHierarchy.class, "toDeleteSQL")
		.to("checkpoint:bean?message= Removendo hierarquias antigas...")
		.to("jdbc:dataSource")
		.to("checkpoint:bean?message=Hierarquias antigas removidas com sucesso...")
		.bean(ExecuteQueryHierarchy.class, "toInsertSQL")
		.to("checkpoint:bean?message=Inserindo hierarquias...")
		.to("jdbc:dataSource")
		.to("checkpoint:bean?message=Hierarquias inseridas com sucesso...")
		
		.setBody(simple("true"))
		.to("seda:checkForCompletedRoute");
	}
}