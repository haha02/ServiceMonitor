package com.test.servicemonitor.persistance;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import com.test.servicemonitor.main.SystemConfig;

/**
 * Abstract JDBC DAO implementation.
 * <p>
 * This class extends Spring's {@link NamedParameterJdbcDaoSupport} and provide
 * additional SQL logging feature that can be switched on/off via system
 * configuration (determined by calling {@link SystemConfig#isLogSQL()}).
 *
 */
public abstract class GenericDaoJdbcSupport extends NamedParameterJdbcDaoSupport {
	/**
	 * logger
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SystemConfig systemConfig;

	protected <T> T executeQuery(String sql, ResultSetExtractor<T> extractor) {
		logSQL(sql, null);
		T result = getJdbcTemplate().query(sql, extractor);
		return result;
	}

	protected <T> T executeQuery(String sql, Map<String, ?> paramMap, ResultSetExtractor<T> extractor) {
		logSQL(sql, paramMap);
		T result = getNamedParameterJdbcTemplate().query(sql, paramMap, extractor);
		return result;
	}

	protected <T> List<T> executeQuery(String sql, RowMapper<T> extractor) {
		logSQL(sql, null);
		List<T> entityList = getJdbcTemplate().query(sql, extractor);
		return entityList;
	}

	protected <T> List<T> executeQuery(String sql, Map<String, ?> paramMap, RowMapper<T> extractor) {
		logSQL(sql, paramMap);
		List<T> entityList = getNamedParameterJdbcTemplate().query(sql, paramMap, extractor);
		return entityList;
	}

	protected int executeUpdate(String sql) {
		logSQL(sql, null);
		int updateCount = getJdbcTemplate().update(sql);
		return updateCount;
	}

	protected int executeUpdate(String sql, Map<String, ?> paramMap) {
		logSQL(sql, paramMap);
		int updateCount = getNamedParameterJdbcTemplate().update(sql, paramMap);
		return updateCount;
	}

	private void logSQL(String sql, Map<String, ?> paramMap) {
		if (!systemConfig.isLogSQL())
			return;
		logger.info("Executing SQL: [{}] with parameter map [{}]", sql, paramMap);
	}
}
