package br.com.afferolab.hub.atento.ativos.bean.gr;

import java.util.HashMap;

import org.apache.camel.CamelContext;

import br.com.afferolab.hub.atento.ativos.bean.ConvertResult;
import br.com.afferolab.hub.atento.ativos.entity.gr.GrCostCenter;
import br.com.afferolab.hub.atento.ativos.util.ColumnDatabasesConstants;

public class ConvertResultToCostCenter extends ConvertResult<GrCostCenter> {

	@Override
	protected GrCostCenter assembly(HashMap<String, Object> mapResult, CamelContext context) throws Exception {
		
		GrCostCenter costCenter = new GrCostCenter();
		
		costCenter.setIdCostCenter((Long) mapResult.get(ColumnDatabasesConstants.ID_COST_CENTER));
		costCenter.setName((String) mapResult.get(ColumnDatabasesConstants.COST_CENTER_NAME));
		costCenter.setIsInsert((Integer) mapResult.get(ColumnDatabasesConstants.INSERT));
		
		return costCenter;
	}
}
