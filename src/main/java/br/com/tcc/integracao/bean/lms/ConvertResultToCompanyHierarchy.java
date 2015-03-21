package br.com.tcc.integracao.bean.lms;

import java.util.HashMap;

import org.apache.camel.CamelContext;

import br.com.tcc.integracao.ConvertResult;
import br.com.tcc.integracao.entity.lms.LMSCompanyHierachy;
import br.com.tcc.integracao.util.ColumnDatabasesConstants;

public class ConvertResultToCompanyHierarchy extends ConvertResult<LMSCompanyHierachy> {
	
	@Override
	protected LMSCompanyHierachy assembly(HashMap<String, Object> mapResult, CamelContext context) throws Exception {
		LMSCompanyHierachy companyHierarchy = new LMSCompanyHierachy();
		
		companyHierarchy.setCompanyHierarchy((String) mapResult.get(ColumnDatabasesConstants.COMPANY_HIERARCHY));
		companyHierarchy.setName((String) mapResult.get(ColumnDatabasesConstants.COMPANY_HIERARCHY_NAME));
		companyHierarchy.setDescription((String) mapResult.get(ColumnDatabasesConstants.COMPANY_HIERARCHY_DESCRIPTION));
		companyHierarchy.setIsInsert((Integer) mapResult.get(ColumnDatabasesConstants.INSERT));
		
		return companyHierarchy;
	}
}