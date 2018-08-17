package com.apitenant.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.apitenant.context.TenantContext;

@Component
public class TenantInterceptor extends HandlerInterceptorAdapter {
	
	@Value("${jwt.header}")
	private String tokenHeader;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String authToken = request.getHeader(this.tokenHeader);
		String tenantId = "tenantId from authToken";
		TenantContext.setCurrentTenant(tenantId);
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		TenantContext.clear();
	}
}
