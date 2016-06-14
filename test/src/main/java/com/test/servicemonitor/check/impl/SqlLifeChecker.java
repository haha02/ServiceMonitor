package com.test.servicemonitor.check.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.test.servicemonitor.check.CheckResult;
import com.test.servicemonitor.check.FailLevel;
import com.test.servicemonitor.check.SimpleCheckResult;
import com.test.servicemonitor.check.ThrowableEncolsingCheckResult;

public class SqlLifeChecker extends AbstractLifeChecker {

	private String sql = "SELECT 1"; // not suitable for Oracle DB
	private Connection conn;
	private boolean closeAfterCheck = false;
	private String user;
	private String password;

	public SqlLifeChecker(String systemId, String connectionString, String driverClass) throws ClassNotFoundException {
		this(systemId, connectionString, driverClass, null, null);
	}

	public SqlLifeChecker(String systemId, String connectionString, String driverClass, String user, String password)
			throws ClassNotFoundException {
		super(systemId, connectionString);
		Class.forName(driverClass);
		this.user = user;
		this.password = password;
	}

	@Override
	public CheckResult check() {
		try {
			if (conn == null || conn.isClosed()) {
				openConnection();
			}
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			logResultSet(rs);
			if (closeAfterCheck) {
				closeConnection();
			}
		} catch (SQLException e) {
			return new ThrowableEncolsingCheckResult(e, FailLevel.FATAL);
		}
		return new SimpleCheckResult(true);
	}

	private void logResultSet(ResultSet rs) {
		// TODO log the result set to DEBUG level
	}

	private void openConnection() throws SQLException {
		if (user != null)
			conn = DriverManager.getConnection(getConnectionString(), user, password);
		else
			conn = DriverManager.getConnection(getConnectionString());
	}

	private void closeConnection() throws SQLException {
		if (conn != null && !conn.isClosed()) {
			conn.close();
		}
	}

	public boolean isCloseAfterCheck() {
		return closeAfterCheck;
	}

	public void setCloseAfterCheck(boolean closeAfterCheck) {
		this.closeAfterCheck = closeAfterCheck;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

}
