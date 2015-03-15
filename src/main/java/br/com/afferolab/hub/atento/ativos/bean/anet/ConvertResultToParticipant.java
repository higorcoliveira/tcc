package br.com.afferolab.hub.atento.ativos.bean.anet;

import java.util.Date;
import java.util.HashMap;

import org.apache.camel.CamelContext;

import br.com.afferolab.hub.atento.ativos.bean.ConvertResult;
import br.com.afferolab.hub.atento.ativos.entity.anet.AnetParticipant;
import br.com.afferolab.hub.atento.ativos.util.ColumnDatabasesConstants;

public class ConvertResultToParticipant extends ConvertResult<AnetParticipant> {

	@Override
	protected AnetParticipant assembly(HashMap<String, Object> mapResult, CamelContext context) throws Exception {
		AnetParticipant participant = new AnetParticipant();
		
		participant.setId((Integer) mapResult.get(ColumnDatabasesConstants.PARTICIPANT_ID));
		participant.setActive((Boolean) mapResult.get(ColumnDatabasesConstants.PARTICIPANT_ACTIVE));
		participant.setAdmissionDate((Date) mapResult.get(ColumnDatabasesConstants.PARTICIPANT_ADMISSION_DATE));
		participant.setCompanyArea((String) mapResult.get(ColumnDatabasesConstants.PARTICIPANT_COMPANY_AREA));
		participant.setCompanyHierarchy((String) mapResult.get(ColumnDatabasesConstants.PARTICIPANT_COMPANY_HIERACHY));
		participant.setConsultingName((String) mapResult.get(ColumnDatabasesConstants.PARTICIPANT_CONSULTING_NAME));
		participant.setContract((String) mapResult.get(ColumnDatabasesConstants.PARTICIPANT_CONTRACT));
		participant.setCostCenter((String) mapResult.get(ColumnDatabasesConstants.PARTICIPANT_COST_CENTER));
		participant.setCpf((String) mapResult.get(ColumnDatabasesConstants.PARTICIPANT_CPF));
		participant.setEmail((String) mapResult.get(ColumnDatabasesConstants.PARTICIPANT_EMAIL));
		participant.setExecutiveBoard((String) mapResult.get(ColumnDatabasesConstants.PARTICIPANT_EXECUTIVE_BOARD));
		participant.setFullBirthDay((Date) mapResult.get(ColumnDatabasesConstants.PARTICIPANT_FULLBIRTHDAY));
		participant.setGender((String) mapResult.get(ColumnDatabasesConstants.PARTICIPANT_GENDER));
		participant.setMaritalStatus((Integer) mapResult.get(ColumnDatabasesConstants.PARTICIPANT_MARITAL_STATUS));
		participant.setName((String) mapResult.get(ColumnDatabasesConstants.PARTICIPANT_NAME));
		participant.setPassword((String) mapResult.get(ColumnDatabasesConstants.PARTICIPANT_PASSWORD));
		participant.setRe((String) mapResult.get(ColumnDatabasesConstants.PARTICIPANT_RE));
		participant.setReSuperior((String) mapResult.get(ColumnDatabasesConstants.PARTICIPANT_RE_SUPERIOR));
		participant.setRg((String) mapResult.get(ColumnDatabasesConstants.PARTICIPANT_RG));
		participant.setScholarity((Integer) mapResult.get(ColumnDatabasesConstants.PARTICIPANT_SCHOLARITY));
		
		return participant;
	}
}