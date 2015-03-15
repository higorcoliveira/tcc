package br.com.afferolab.hub.atento.ativos.bean.anet;

import java.util.HashMap;

import org.apache.camel.CamelContext;

import br.com.afferolab.hub.atento.ativos.bean.ConvertResult;
import br.com.afferolab.hub.atento.ativos.entity.anet.AnetCompanyArea;
import br.com.afferolab.hub.atento.ativos.util.ColumnDatabasesConstants;

public class ConvertResultToCompanyArea extends ConvertResult<AnetCompanyArea> {
	
	@Override
	protected AnetCompanyArea assembly(HashMap<String, Object> mapResult, CamelContext context) throws Exception {
		AnetCompanyArea companyArea = new AnetCompanyArea();
		
		companyArea.setCompanyArea((String) mapResult.get(ColumnDatabasesConstants.COMPANY_AREA));
		companyArea.setName((String) mapResult.get(ColumnDatabasesConstants.COMPANY_AREA_NAME));
		companyArea.setDescription((String) mapResult.get(ColumnDatabasesConstants.COMPANY_AREA_DESCRIPTION));
		companyArea.setIsInsert((Integer) mapResult.get(ColumnDatabasesConstants.INSERT));
		
		return companyArea;
	}
}