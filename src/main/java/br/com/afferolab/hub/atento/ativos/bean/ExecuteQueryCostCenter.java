package br.com.afferolab.hub.atento.ativos.bean;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.Handler;

import br.com.afferolab.utils.util.QueryBuilderUtil;
import br.com.afferolab.utils.xml.model.QueryXMLParameter;

public class ExecuteQueryCostCenter {
	
	private static final String GR_DATABASE_NAME = "{{gr.database.name}}";
	private static final String QUERY_XML = "gr.xml";
	private static final String PARAMETER_NAME = "GR_DATABASE";
	private static final String QUERY_DISCRIMINATOR = "QUERY GESTAO RECURSOS COST CENTER";
	
	@Handler
	public String toSQL(CamelContext context) throws Exception {
		
		String grDatabaseName = context.resolvePropertyPlaceholders(GR_DATABASE_NAME);
		
		InputStream validQueryXML = this.getClass().getClassLoader().getResourceAsStream(QUERY_XML);
		
		QueryXMLParameter queriesXMLParameter = new QueryXMLParameter();
		queriesXMLParameter.setName(PARAMETER_NAME);
		queriesXMLParameter.addValue(grDatabaseName);
		
		List<QueryXMLParameter> query = new ArrayList<>();
		query.add(queriesXMLParameter);
		
		return QueryBuilderUtil.build(QUERY_DISCRIMINATOR, validQueryXML, query);
	}
}
