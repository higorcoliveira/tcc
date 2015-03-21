package br.com.tcc.integracao.util;

public final class RouteConstants {

	public static final String HEADER_STATUS = "success";
	public static final String SYSTEM_FAIL = "systemFail";
	
	public static final String ROUTE_GR_FAIL = "routeGRFail";
	public static final String ROUTE_ANET_FAIL = "routeAnetFail";
	public static final String ROUTE_SKILLO_FAIL = "routeSkilloFail";
	
	public static final String GR_FAIL = "GR";
	public static final String ANET_FAIL = "ANET";
	public static final String SKILLO_FAIL = "SKILLO";
	
	public static final String HEADER_FINISHED = "finished";
	
	public static final Integer BATCH_SIZE = 100;
	public static final Integer BATCH_TIME_OUT = 3000;
	
	public static final String LMS_DATABASE_NAME = "{{lms.database.name}}";
	public static final String QUERY_XML = "lms.xml";
	public static final String PARAMETER_NAME = "LMS_DATABASE";
	public static final String QUERY_DISCRIMINATOR = "QUERY LMS COMPANY HIERARCHY";
	public static final String DELETE_DISCRIMINATOR = "QUERY LMS HIERARCHY DELETE";
	public static final String INSERT_DISCRIMINATOR = "QUERY LMS HIERARCHY INSERT";
	public static final String INSERT_PARTICIPANT_DISCRIMINATOR = "QUERY LMS PARTICIPANT TO INSERT";
	public static final String UPDATE_PARTICIPANT_DISCRIMINATOR = "QUERY LMS PARTICIPANT TO UPDATE";
}
