package br.com.tcc.integracao.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Preconditions;

public final class RoleGroupUtil {
	
	public static final String[] roleGroupAnet = {"M3", "M4", "M5", "E1", "E2", "S1", "S2", "S3", "P1", 
												"P2", "P3", "P4", "O7", "O8", "M1", "M2", "O1", "O2", "O3", 
												"O4", "O5", "O6", "N1", "N2", "OS1", "OS2"};
	
	public static List<String> getRoleGroupLms() {
		List<String> listRoleGroups = new ArrayList<String>();
		listRoleGroups = Arrays.asList(roleGroupAnet);
	
		return listRoleGroups;
	}
	
	public static String extractRoleGroup(String roleGroup) {
		Preconditions.checkArgument(!StringUtils.isEmpty(roleGroup), "RoleGroup deve ser diferente de vazio ou nulo.");
		String roleGroupAcronym = roleGroup.split("[\\(\\)]")[1];

		return roleGroupAcronym;
	}
}
