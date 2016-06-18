package com.test.servicemonitor.check.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PreDestroy;

import com.test.servicemonitor.check.CheckResult;
import com.test.servicemonitor.check.FailLevel;
import com.test.servicemonitor.check.LifeChecker;
import com.test.servicemonitor.check.SimpleCheckResult;
import com.test.servicemonitor.check.ThrowableEncolsingCheckResult;

/**
 * {@link LifeChecker} implementation that monitoring a SQL database
 *
 */
public class SqlLifeChecker extends AbstractLifeChecker {

	private String sql = "SELECT 1"; // not suitable for Oracle DB
	private Connection conn;
	private boolean closeAfterCheck = false;
	private String user;
	private String password;

	/**
	 * Construct an instance of this class
	 * 
	 * @param systemId
	 *            the ID of targeted system
	 * @param connectionString
	 *            the connection string
	 * @param driverClass
	 *            the driver class full qualified name (used to load the class)
	 * @throws ClassNotFoundException
	 *             if the driver class cannot be found in class path
	 */
	public SqlLifeChecker(String systemId, String connectionString, String driverClass) throws ClassNotFoundException {
		this(systemId, connectionString, driverClass, null, null);
	}

	/**
	 * Construct an instance of this class
	 * 
	 * @param systemId
	 *            the ID of targeted system
	 * @param connectionString
	 *            the connection string
	 * @param user
	 *            the database user name
	 * @param password
	 *            the database user password
	 * @param driverClass
	 *            the driver class full qualified name (used to load the class)
	 * @throws ClassNotFoundException
	 *             if the driver class cannot be found in class path
	 */
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
		// XXX log the result set to DEBUG level?
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

	/**
	 * Whether this checker close JDBC connection after each check. If not, the checker will reuse the established JDBC connection. The default value is
	 * {@code false}.
	 * 
	 * @return {@code true} if it does, else {@code false}.
	 */
	public boolean isCloseAfterCheck() {
		return closeAfterCheck;
	}

	/**
	 * Set whether this checker should close JDBC connection after each check. One might want to set this flag to {@code true} when the connection number of the
	 * database is strictly limited and the check period is relative long.
	 * 
	 * @param closeAfterCheck
	 *            the flag
	 */
	public void setCloseAfterCheck(boolean closeAfterCheck) {
		this.closeAfterCheck = closeAfterCheck;
	}

	/**
	 * Get the SQL statement that is used to monitoring the database. The default value is "SELECT 1".
	 * <p>
	 * Please note the default SQL statement is not valid for Oracle database since it always expect a FROM clause in a SQL querying (SELECT) statement.
	 * 
	 * @return the SQL statement
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * Set the SQL statement that is used to monitoring the database. The default value is "SELECT 1".
	 * 
	 * @see #getSql()
	 * 
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	@PreDestroy // try to release JDBC connection when shutting down Spring application context
	@Override
	public void stop() {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			// silent
		}
	}

}
