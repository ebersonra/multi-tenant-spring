package com.apitenant.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TenantContext {
	
	private static Logger logger = LoggerFactory.getLogger(TenantContext.class.getSimpleName());
	
	private static ThreadLocal<String> currentTenant = new ThreadLocal<>();

	public static void setCurrentTenant(String tenant) {
		logger.debug("Configuração tenant para: " + tenant);
		currentTenant.set(tenant);
	}
	
	public static String getCurrentTenant() {
		return currentTenant.get();
	}

	public static void clear() {
		currentTenant.set(null);
	}
}
