package com.test.servicemonitor.check;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.test.servicemonitor.check.impl.RestWebServiceLifeChecker;
import com.test.servicemonitor.check.impl.SqlLifeChecker;
import com.test.servicemonitor.integration.HttpRequestGateway;

@Component
public class CheckerFactory {
	public static final String HINT_SQL_USER = "user";
	public static final String HINT_SQL_PWD = "password";
	public static final String HINT_SQL_DRIVER = "driverClass";

	@Autowired
	private HttpRequestGateway httpGateway;

	private Map<String, SingleCheckerFactory> typeToFactoryMap = new HashMap<>();

	@PostConstruct
	public void init() {
		typeToFactoryMap.put("SQL", new SQLCheckerFactory());
		typeToFactoryMap.put("REST", new RestCheckerFactory());
		typeToFactoryMap.put("ALWAYS_FAIL_TRANSIENT", new AlwaysFailCheckerFactory(FailLevel.TRANSIENT));
		typeToFactoryMap.put("ALWAYS_FAIL_MEDIUM", new AlwaysFailCheckerFactory(FailLevel.MEDIUM));
		typeToFactoryMap.put("ALWAYS_FAIL_FATAL", new AlwaysFailCheckerFactory(FailLevel.FATAL));
	}

	public Set<String> getSupportedTypes() {
		return new HashSet<>(typeToFactoryMap.keySet());
	}

	public boolean isSupport(String checkerType) {
		return typeToFactoryMap.containsKey(checkerType);
	}

	public LifeChecker getChecker(String systemId, String type, String connectionString, Properties hints) {
		SingleCheckerFactory scf = typeToFactoryMap.get(type);
		if (scf == null)
			throw new IllegalArgumentException("Unsupported checker type [" + type + "]");
		return scf.getChecker(systemId, connectionString, hints);
	}

	private interface SingleCheckerFactory {
		LifeChecker getChecker(String systemId, String connectionString, Properties hints);
	}

	private class SQLCheckerFactory implements SingleCheckerFactory {
		@Override
		public LifeChecker getChecker(String systemId, String connectionString, Properties hints) {
			String user = hints.getProperty("user");
			String password = hints.getProperty("password");
			String driverClass = hints.getProperty("driverClass");
			try {
				SqlLifeChecker checker = new SqlLifeChecker(systemId, connectionString, driverClass, user, password);
				return checker;
			} catch (NullPointerException e) {
				throw new IllegalArgumentException("Hint [" + HINT_SQL_DRIVER + "] is missing", e);
			} catch (ClassNotFoundException e) {
				throw new IllegalArgumentException("Driver class not found in classpath: " + driverClass, e);
			}
		}
	}

	private class RestCheckerFactory implements SingleCheckerFactory {

		@Override
		public LifeChecker getChecker(String systemId, String connectionString, Properties hints) {
			RestWebServiceLifeChecker checker = new RestWebServiceLifeChecker(systemId, connectionString);
			Map<String, String> params = new HashMap<String, String>();
			CollectionUtils.mergePropertiesIntoMap(hints, params);
			checker.setParams(params);
			checker.setGateway(httpGateway);
			return checker;
		}

	}

	private class AlwaysFailCheckerFactory implements SingleCheckerFactory {

		private FailLevel level;

		public AlwaysFailCheckerFactory(FailLevel level) {
			super();
			this.level = level;
		}

		@Override
		public LifeChecker getChecker(String systemId, String connectionString, Properties hints) {
			return new LifeChecker() {
				@Override
				public CheckResult check() {
					return new SimpleCheckResult(false, "Always fail", level);
				}
			};
		}

	}

}
