package com.test.servicemonitor.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class RemoteSystemDaoJdbcImpl extends GenericDaoJdbcSupport implements RemoteSystemDao {

	@Autowired
	private DataSource dataSource;

	@PostConstruct
	public void init() {
		setDataSource(dataSource);
		// executeUpdate("DROP TABLE REMOTE_SYSTEM");
		// executeUpdate(
		// "CREATE TABLE REMOTE_SYSTEM ( SYSTEM_ID VARCHAR(100) PRIMARY KEY
		// ,CHECKER_TYPE VARCHAR(100) ,CONNECTION_STRING VARCHAR(600) ,HINTS
		// VARCHAR(MAX) ,CHECK_PERIOD INT ,PERIOD_UNIT VARCHAR(15), DISABLED
		// CHAR(1))");
	}

	private static final String SQL_ALL_COL = "SYSTEM_ID, CHECKER_TYPE, CONNECTION_STRING, HINTS, CHECK_PERIOD, PERIOD_UNIT, DISABLED";
	private static final String SQL_SELECT = "select " + SQL_ALL_COL + " from REMOTE_SYSTEM";
	private static final String SQL_WHERE_SYSID = " where SYSTEM_ID=:systemId";

	@Override
	public RemoteSystem get(RemoteSystem entity) {
		String sql = SQL_SELECT + SQL_WHERE_SYSID;
		Map<String, String> paramMap = Collections.singletonMap("systemId", entity.getSystemId());
		return DataAccessUtils.singleResult(executeQuery(sql, paramMap, new RemoteSystemRowMapper()));
	}

	@Override
	public List<RemoteSystem> getAll() {
		return executeQuery(SQL_SELECT, new RemoteSystemRowMapper());
	}

	@Override
	public void save(RemoteSystem entity) {
		String sql = "insert into REMOTE_SYSTEM (" + SQL_ALL_COL
				+ ") values (:systemId, :checkerType, :connectionString, :hints, :checkPeriod, :periodUnit, :disabled)";
		executeUpdate(sql, toParamMap(entity));
	}

	@Override
	public void update(RemoteSystem entity) {
		String sql = "update REMOTE_SYSTEM "
				+ "set CHECKER_TYPE=:checkerType, CONNECTION_STRING=:connectionString, HINTS=:hints, CHECK_PERIOD=:checkPeriod, PERIOD_UNIT=:periodUnit, DISABLED=:disabled"
				+ SQL_WHERE_SYSID;
		executeUpdate(sql, toParamMap(entity));
	}

	@Override
	public void delete(RemoteSystem entity) {
		String sql = "delete from REMOTE_SYSTEM" + SQL_WHERE_SYSID;
		Map<String, String> paramMap = Collections.singletonMap("systemId", entity.getSystemId());
		executeUpdate(sql, paramMap);
	}

	private Map<String, Object> toParamMap(RemoteSystem entity) {
		Map<String, Object> map = new HashMap<>();
		map.put("systemId", entity.getSystemId());
		map.put("checkerType", entity.getCheckerType());
		map.put("connectionString", entity.getConnectionString());
		map.put("hints", entity.getHints());
		map.put("checkPeriod", entity.getCheckPeriod());
		map.put("periodUnit", entity.getPeriodUnit());
		map.put("disabled", entity.isDisabled() ? "Y" : "N");
		return map;
	}

	private class RemoteSystemRowMapper implements RowMapper<RemoteSystem> {
		@Override
		public RemoteSystem mapRow(ResultSet rs, int rowNum) throws SQLException {
			RemoteSystem bean = new RemoteSystem();
			bean.setSystemId(rs.getString("SYSTEM_ID"));
			bean.setCheckerType(rs.getString("CHECKER_TYPE"));
			bean.setConnectionString(rs.getString("CONNECTION_STRING"));
			bean.setHints(rs.getString("HINTS"));
			bean.setCheckPeriod(rs.getInt("CHECK_PERIOD"));
			bean.setPeriodUnit(rs.getString("PERIOD_UNIT"));
			bean.setDisabled("Y".equals(rs.getString("DISABLED")));
			return bean;
		}
	}

}
