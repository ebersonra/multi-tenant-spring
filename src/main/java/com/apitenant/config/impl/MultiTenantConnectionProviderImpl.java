package com.apitenant.config.impl;

import static com.apitenant.util.MultiTenantConstants.DEFAULT_TENANT_ID;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider {

	private static final long serialVersionUID = 1L;

	@Autowired
	private DataSource dataSource;
	
	
	@Override
	public Connection getAnyConnection() throws SQLException {
		return dataSource.getConnection();
	}
	
	@Override
	public void releaseAnyConnection(Connection connection) throws SQLException {
		connection.close();
	}

	@Override
	public Connection getConnection(String tenantIdentifier) throws SQLException {
		
		final Connection connection = getAnyConnection();
		
		try {
			
			if(!StringUtils.isEmpty(tenantIdentifier)) {
				connection.createStatement().execute("USE " + tenantIdentifier);
			}else {
				connection.createStatement().execute("USE " + DEFAULT_TENANT_ID);
			}
		}catch (SQLException sqlEx) {
            throw new HibernateException("Não foi possível alterar a conexão JDBC ao schema especificado [" + tenantIdentifier + "]",sqlEx);
        }
		return connection;
	}

	@Override
	public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
		
		try {
			connection.createStatement().execute("USE " + DEFAULT_TENANT_ID);
		}catch(SQLException sqlEx) {
			
			throw new HibernateException("Erro ao configurar schema para: " + tenantIdentifier, sqlEx);
		}
		
		connection.close();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean isUnwrappableAs(Class unwrapType) {
		return false;
	}
	
	@Override
	public <T> T unwrap(Class<T> unwrapType) {
		return null;
	}
	@Override
	public boolean supportsAggressiveRelease() {
		return false;
	}

	
}
