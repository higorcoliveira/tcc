package br.com.afferolab.hub.atento.ativos.bean;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelContext;

import br.com.afferolab.utils.util.QueryBuilderUtil;
import br.com.afferolab.utils.xml.model.QueryXMLParameter;

public class ExecuteQueryParticipant {

	private static final String ANET_DATABASE_NAME = "{{anet.database.name}}";
	private static final String QUERY_XML = "anet.xml";
	private static final String PARAMETER_NAME = "ANET_DATABASE";
	private static final String INSERT_DISCRIMINATOR = "QUERY ANET PARTICIPANT TO INSERT";
	private static final String UPDATE_DISCRIMINATOR = "QUERY ANET PARTICIPANT TO UPDATE";
	
	public String toInsertSQL(CamelContext context) throws Exception {
		return toSQL(context, INSERT_DISCRIMINATOR);
	}
	
	public String toUpdateSQL(CamelContext context) throws Exception {
		return toSQL(context, UPDATE_DISCRIMINATOR);
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