package br.com.tcc.integracao.util;

public final class RouteConstants {

	public static final String HEADER_STATUS = "success";
	public static final String SYSTEM_FAIL = "systemFail";
	
	public static final String ROUTE_LMS_FAIL = "routeLmsFail";
	
	public static final String LMS_FAIL = "LMS";
	
	public static final String HEADER_FINISHED = "finished";
	
	public static final Integer BATCH_SIZE = 100;
	public static final Integer BATCH_TIME_OUT = 3000;
	
	public static final String LMS_DATABASE_NAME = "{{lms.database.name}}";
	public static final String QUERY_XML = "lms.xml";
	public static final String PARAMETER_NAME = "LMS_DATABASE";
	public static final String QUERY_HIERARCHY_DISCRIMINATOR = "QUERY LMS COMPANY HIERARCHY";
	public static final String DELETE_DISCRIMINATOR = "QUERY LMS HIERARCHY DELETE";
	public static final String INSERT_DISCRIMINATOR = "QUERY LMS HIERARCHY INSERT";
	public static final String INSERT_PARTICIPANT_DISCRIMINATOR = "QUERY LMS PARTICIPANT TO INSERT";
	public static final String UPDATE_PARTICIPANT_DISCRIMINATOR = "QUERY LMS PARTICIPANT TO UPDATE";
	private static final String QUERY_AREA_DISCRIMINATOR = "QUERY ANET COMPANY AREA";
}
