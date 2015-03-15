package br.com.afferolab.hub.atento.ativos.bean;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelContext;

import br.com.afferolab.utils.util.QueryBuilderUtil;
import br.com.afferolab.utils.xml.model.QueryXMLParameter;

public class ExecuteQueryEmployee {
	
	private static final String GR_DATABASE_NAME = "{{gr.database.name}}";
	private static final String ID_PUBLIC_POOL = "{{gr.idPublicPool}}";
	private static final String ID_ROLE_READONLY = "{{gr.idRoleReadOnly}}";
	private static final String QUERY_XML = "gr.xml";
	private static final String PARAMETER_DATABASE = "GR_DATABASE";
	private static final String PARAMETER_POOL = "ID_POOL";
	private static final String PARAMETER_READONLY = "ID_ROLE";
	private static final String INSERT_DISCRIMINATOR = "QUERY GESTAO RECURSOS EMPLOYEE INSERT";
	private static final String INSERT_POOL_DISCRIMINATOR = "QUERY GESTAO RECURSOS EMPLOYEE POOL INSERT";
	private static final String UPDATE_DISCRIMINATOR = "QUERY GESTAO RECURSOS EMPLOYEE UPDATE";
	
	public String toInsertSQL(CamelContext context) throws Exception {
		return toSQL(context, INSERT_DISCRIMINATOR);
	}
	
	public String toUpdateSQL(CamelContext context) throws Exception {
		return toSQL(context, UPDATE_DISCRIMINATOR);
	}
	
	public String toInsertPoolSQL(CamelContext context) throws Exception {
		String idPublicPool = context.resolvePropertyPlaceholders(ID_PUBLIC_POOL);
		String idReadOnly = context.resolvePropertyPlaceholders(ID_ROLE_READONLY);
		
		QueryXMLParameter publicPool = new QueryXMLParameter();
		publicPool.setName(PARAMETER_POOL);
		publicPool.addValue(idPublicPool);
		
		QueryXMLParameter readOnly = new QueryXMLParameter();
		readOnly.setName(PARAMETER_READONLY);
		readOnly.addValue(idReadOnly);
		
		List<QueryXMLParameter> queriesXML = new ArrayList<>();
		queriesXML.add(publicPool);
		queriesXML.add(readOnly);
		
		return toSQL(context, INSERT_POOL_DISCRIMINATOR, queriesXML);
	}
	
	private String toSQL(CamelContext context, String discriminator) throws Exception {
		List<QueryXMLParameter> empty = new ArrayList<>();
		return toSQL(context, discriminator, empty);
	}
	
	private String toSQL(CamelContext context, String discriminator, List<QueryXMLParameter> queries) throws Exception {
		String anetDatabaseName = context.resolvePropertyPlaceholders(GR_DATABASE_NAME);
		InputStream validQueryXML = this.getClass().getClassLoader().getResourceAsStream(QUERY_XML);
		
		QueryXMLParameter queryXMLParameter = new QueryXMLParameter();
		queryXMLParameter.setName(PARAMETER_DATABASE);
		queryXMLParameter.addValue(anetDatabaseName);
		
		List<QueryXMLParameter> queriesXML = new ArrayList<>();
		queriesXML.add(queryXMLParameter);
		queriesXML.addAll(queries);
		
		return QueryBuilderUtil.build(discriminator, validQueryXML, queriesXML);
	}
}
