package br.com.tcc.integracao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.Handler;

import br.com.afferolab.utils.util.QueryBuilderUtil;
import br.com.afferolab.utils.xml.model.QueryXMLParameter;
import br.com.tcc.integracao.util.RouteConstants;

public class ExecuteQueryCompanyHierarchy {

	@Handler
	public String toSQL(CamelContext context) throws Exception {
		
		String anetDatabaseName = context.resolvePropertyPlaceholders(RouteConstants.LMS_DATABASE_NAME);
		
		InputStream validQueryXML = this.getClass().getClassLoader().getResourceAsStream(RouteConstants.QUERY_XML);
		
		QueryXMLParameter queriesXMLParameter = new QueryXMLParameter();
		queriesXMLParameter.setName(RouteConstants.PARAMETER_NAME);
		queriesXMLParameter.addValue(anetDatabaseName);
		
		List<QueryXMLParameter> query = new ArrayList<>();
		query.add(queriesXMLParameter);
		
		return QueryBuilderUtil.build(RouteConstants.QUERY_DISCRIMINATOR, validQueryXML, query);
	}
}