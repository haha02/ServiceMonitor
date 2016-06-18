package com.test.servicemonitor.check;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.test.servicemonitor.check.impl.RestWebServiceLifeChecker;
import com.test.servicemonitor.check.impl.SqlLifeChecker;
import com.test.servicemonitor.integration.HttpRequestGateway;

/**
 * Factory class for acquiring {@link LifeChecker} instances.
 *
 */
@Component
public class CheckerFactory {
	public static final String HINT_SQL_USER = "user";
	public static final String HINT_SQL_PWD = "password";
	public static final String HINT_SQL_DRIVER = "driverClass";

	@Autowired
	private HttpRequestGateway httpGateway;

	@Autowired
	private BeanFactory beanFactory;

	private Map<String, SingleCheckerFactory> typeToFactoryMap = new HashMap<>();

	/**
	 * Initializing method, should be called after dependency injection.
	 */
	@PostConstruct
	public void init() {
		typeToFactoryMap.put("SQL", new SQLCheckerFactory());
		typeToFactoryMap.put("REST", new RestCheckerFactory());
		typeToFactoryMap.put("ALWAYS_FAIL_TRANSIENT", new AlwaysFailCheckerFactory(FailLevel.TRANSIENT));
		typeToFactoryMap.put("ALWAYS_FAIL_MEDIUM", new AlwaysFailCheckerFactory(FailLevel.MEDIUM));
		typeToFactoryMap.put("ALWAYS_FAIL_FATAL", new AlwaysFailCheckerFactory(FailLevel.FATAL));
	}

	/**
	 * Get supported checker types.
	 * 
	 * @return checker types
	 */
	public Set<String> getSupportedTypes() {
		return new HashSet<>(typeToFactoryMap.keySet());
	}

	/**
	 * Indicates whether checker instances of a given checker type can be acquired by this factory instance.
	 * 
	 * @param checkerType
	 *            the checker type
	 * @return {@code true} if the factory can construct an instance of the checker type, {@code false} otherwise.
	 */
	public boolean isSupport(String checkerType) {
		return typeToFactoryMap.containsKey(checkerType);
	}

	/**
	 * Acquire a {@link LifeChecker} instance
	 * 
	 * @param systemId
	 *            the targeted system ID
	 * @param type
	 *            checker type
	 * @param connectionString
	 *            connection string
	 * @param hints
	 *            additional hints
	 * @return
	 * @throws IllegalArgumentException
	 *             if the checker type does not supported by this factory or checker cannot be properly constructed.
	 */
	public LifeChecker getChecker(String systemId, String type, String connectionString, Properties hints) {
		if (!isSupport(type))
			throw new IllegalArgumentException("Unsupported checker type [" + type + "]");
		SingleCheckerFactory scf = typeToFactoryMap.get(type);
		return scf.getChecker(systemId, connectionString, hints);
	}

	/**
	 * {@link LifeChecker} factory that supports only one checker type
	 *
	 */
	private interface SingleCheckerFactory {
		LifeChecker getChecker(String systemId, String connectionString, Properties hints);
	}

	/**
	 * Factory for constructing {@link SqlLifeChecker}
	 *
	 */
	private class SQLCheckerFactory implements SingleCheckerFactory {
		@Override
		public LifeChecker getChecker(String systemId, String connectionString, Properties hints) {
			String user = hints.getProperty("user");
			String password = hints.getProperty("password");
			String driverClass = hints.getProperty("driverClass");
			try {
				Object[] constructorArgs = new Object[] { systemId, connectionString, driverClass, user, password };
				SqlLifeChecker checker = beanFactory.getBean(SqlLifeChecker.class, constructorArgs);
				// new SqlLifeChecker(systemId, connectionString, driverClass, user, password);
				return checker;
			} catch (NullPointerException e) {
				throw new IllegalArgumentException("Hint [" + HINT_SQL_DRIVER + "] is missing", e);
			}
			// catch (ClassNotFoundException e) {
			// throw new IllegalArgumentException("Driver class not found in classpath: " + driverClass, e);
			// }
		}
	}

	/**
	 * Factory for constructing {@link RestWebServiceLifeChecker}
	 *
	 */
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

	/**
	 * Factory for constructing checkers that always failed.
	 * 
	 */
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

				@Override
				public void stop() {
				}
			};
		}

	}

}
