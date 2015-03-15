package br.com.afferolab.hub.atento.ativos.bean;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelContext;

import br.com.afferolab.utils.util.QueryBuilderUtil;
import br.com.afferolab.utils.xml.model.QueryXMLParameter;

public class ExecuteQueryHierarchy {

	private static final String ANET_DATABASE_NAME = "{{anet.database.name}}";
	private static final String QUERY_XML = "anet.xml";
	private static final String PARAMETER_NAME = "ANET_DATABASE";
	private static final String DELETE_DISCRIMINATOR = "QUERY ANET HIERARCHY DELETE";
	private static final String INSERT_DISCRIMINATOR = "QUERY ANET HIERARCHY INSERT";
	
	public String toDeleteSQL(CamelContext context) throws Exception {
		return toSQL(context, DELETE_DISCRIMINATOR);
	}
	
	public String toInsertSQL(CamelContext context) throws Exception {
		return toSQL(context, INSERT_DISCRIMINATOR);
	}
	
	private String toSQL(CamelContext context, String discriminator) throws Exception {
		String anetDatabaseName = context.resolvePropertyPlaceholders(ANET_DATABASE_NAME);
		InputStream validQueryXML = this.getClass().getClassLoader().getResourceAsStream(QUERY_XML);
		
		QueryXMLParameter queriesXMLParameter = new QueryXMLParameter();
		queriesXMLParameter.setName(PARAMETER_NAME);
		queriesXMLParameter.addValue(anetDatabaseName);
		
		List<QueryXMLParameter> query = new ArrayList<>();
		query.add(queriesXMLParameter);
		
		return QueryBuilderUtil.build(discriminator, validQueryXML, query);
	}
}