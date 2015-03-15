package br.com.afferolab.hub.atento.ativos.bean.anet;

import java.util.HashMap;

import org.apache.camel.CamelContext;

import br.com.afferolab.hub.atento.ativos.bean.ConvertResult;
import br.com.afferolab.hub.atento.ativos.entity.anet.AnetCompanyHierachy;
import br.com.afferolab.hub.atento.ativos.util.ColumnDatabasesConstants;

public class ConvertResultToCompanyHierarchy extends ConvertResult<AnetCompanyHierachy> {
	
	@Override
	protected AnetCompanyHierachy assembly(HashMap<String, Object> mapResult, CamelContext context) throws Exception {
		AnetCompanyHierachy companyHierarchy = new AnetCompanyHierachy();
		
		companyHierarchy.setCompanyHierarchy((String) mapResult.get(ColumnDatabasesConstants.COMPANY_HIERARCHY));
		companyHierarchy.setName((String) mapResult.get(ColumnDatabasesConstants.COMPANY_HIERARCHY_NAME));
		companyHierarchy.setDescription((String) mapResult.get(ColumnDatabasesConstants.COMPANY_HIERARCHY_DESCRIPTION));
		companyHierarchy.setIsInsert((Integer) mapResult.get(ColumnDatabasesConstants.INSERT));
		
		return companyHierarchy;
	}
}