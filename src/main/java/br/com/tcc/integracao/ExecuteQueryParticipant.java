package br.com.tcc.integracao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelContext;

import br.com.afferolab.utils.util.QueryBuilderUtil;
import br.com.afferolab.utils.xml.model.QueryXMLParameter;
import br.com.tcc.integracao.util.RouteConstants;

public class ExecuteQueryParticipant {

	public String toInsertSQL(CamelContext context) throws Exception {
		return toSQL(context, RouteConstants.INSERT_PARTICIPANT_DISCRIMINATOR);
	}
	
	public String toUpdateSQL(CamelContext context) throws Exception {
		return toSQL(context, RouteConstants.UPDATE_PARTICIPANT_DISCRIMINATOR);
	}
	
	private String toSQL(CamelContext context, String discriminator) throws Exception {
		String anetDatabaseName = context.resolvePropertyPlaceholders(RouteConstants.LMS_DATABASE_NAME);
		InputStream validQueryXML = this.getClass().getClassLoader().getResourceAsStream(RouteConstants.QUERY_XML);
		
		QueryXMLParameter queriesXMLParameter = new QueryXMLParameter();
		queriesXMLParameter.setName(RouteConstants.PARAMETER_NAME);
		queriesXMLParameter.addValue(anetDatabaseName);
		
		List<QueryXMLParameter> query = new ArrayList<>();
		query.add(queriesXMLParameter);
		
		return QueryBuilderUtil.build(discriminator, validQueryXML, query);
	}
}