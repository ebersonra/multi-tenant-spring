package com.apitenant.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.apitenant.context.TenantContext;

import static com.apitenant.util.MultiTenantConstants.DEFAULT_TENANT_ID;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {


	@Override
	public String resolveCurrentTenantIdentifier() {
		String tenantId = TenantContext.getCurrentTenant();
		
		if(!StringUtils.isEmpty(tenantId)) {
			return tenantId;
		}
		
		return DEFAULT_TENANT_ID;
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}

	
}
