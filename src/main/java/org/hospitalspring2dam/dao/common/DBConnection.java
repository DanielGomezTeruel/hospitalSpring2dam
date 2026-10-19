package org.hospitalspring2dam.dao.common;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import jakarta.annotation.PreDestroy;
import org.hospitalspring2dam.common.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

/**
 *  @description Class for creating a connection to a DB using DriverManager class.
 *  Reads the information from a property file (mysql-properties.xml)
 */

@Component
public class DBConnection {

	private final Configuration config;
	private final DataSource hikariDataSource;

	public DBConnection(Configuration config) {
		this.config = config;
		hikariDataSource = getHikariPool();
	}

	private DataSource getHikariPool() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl(config.getProperty("urlDB"));
		hikariConfig.setUsername(config.getProperty("user_name"));
		hikariConfig.setPassword(config.getProperty("password"));
		hikariConfig.setMaximumPoolSize(4);

		hikariConfig.addDataSourceProperty("cachePrepStmts", true);
		hikariConfig.addDataSourceProperty("prepStmtCacheSize", 250);
		hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);

		return new HikariDataSource(hikariConfig);
	}

	public Connection getConnection() {
		Connection con = null;
		try {
			con = hikariDataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return con;
	}

	public void closeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@PreDestroy
	public void closePool() {
		((HikariDataSource) hikariDataSource).close();
	}
}
