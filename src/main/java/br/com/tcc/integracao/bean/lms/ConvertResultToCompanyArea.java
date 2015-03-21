package br.com.tcc.integracao.bean.lms;

import java.util.HashMap;

import org.apache.camel.CamelContext;

import br.com.tcc.integracao.ConvertResult;
import br.com.tcc.integracao.entity.lms.LMSCompanyArea;
import br.com.tcc.integracao.util.ColumnDatabasesConstants;

public class ConvertResultToCompanyArea extends ConvertResult<LMSCompanyArea> {
	
	@Override
	protected LMSCompanyArea assembly(HashMap<String, Object> mapResult, CamelContext context) throws Exception {
		LMSCompanyArea companyArea = new LMSCompanyArea();
		
		companyArea.setCompanyArea((String) mapResult.get(ColumnDatabasesConstants.COMPANY_AREA));
		companyArea.setName((String) mapResult.get(ColumnDatabasesConstants.COMPANY_AREA_NAME));
		companyArea.setDescription((String) mapResult.get(ColumnDatabasesConstants.COMPANY_AREA_DESCRIPTION));
		companyArea.setIsInsert((Integer) mapResult.get(ColumnDatabasesConstants.INSERT));
		
		return companyArea;
	}
}