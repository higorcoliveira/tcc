package br.com.afferolab.hub.atento.ativos.bean;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.Handler;

import br.com.afferolab.utils.util.QueryBuilderUtil;
import br.com.afferolab.utils.xml.model.QueryXMLParameter;

public class ExecuteQueryCompanyArea {

	private static final String ANET_DATABASE_NAME = "{{anet.database.name}}";
	private static final String QUERY_XML = "anet.xml";
	private static final String PARAMETER_NAME = "ANET_DATABASE";
	private static final String QUERY_DISCRIMINATOR = "QUERY ANET COMPANY AREA";
	
	@Handler
	public String toSQL(CamelContext context) throws Exception {
		
		String anetDatabaseName = context.resolvePropertyPlaceholders(ANET_DATABASE_NAME);
		
		InputStream validQueryXML = this.getClass().getClassLoader().getResourceAsStream(QUERY_XML);
		
		QueryXMLParameter queriesXMLParameter = new QueryXMLParameter();
		queriesXMLParameter.setName(PARAMETER_NAME);
		queriesXMLParameter.addValue(anetDatabaseName);
		
		List<QueryXMLParameter> query = new ArrayList<>();
		query.add(queriesXMLParameter);
		
		return QueryBuilderUtil.build(QUERY_DISCRIMINATOR, validQueryXML, query);
	}
}