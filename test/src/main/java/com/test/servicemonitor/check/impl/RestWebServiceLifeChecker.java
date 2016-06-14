package com.test.servicemonitor.check.impl;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;

import com.test.servicemonitor.check.CheckResult;
import com.test.servicemonitor.check.SimpleCheckResult;
import com.test.servicemonitor.check.ThrowableEncolsingCeckResult;
import com.test.servicemonitor.integration.HttpRequestGateway;

public class RestWebServiceLifeChecker extends AbstractLifeChecker {
	public static final String HINT_METHOD = "http-method";
	private Map<String, String> params = Collections.emptyMap();

	private HttpRequestGateway gateway;

	public RestWebServiceLifeChecker(String systemId, String connectionString) {
		super(systemId, connectionString);
	}

	@Override
	public CheckResult check() {
		Assert.notNull(gateway, "Missing property [gateway]");
		try {
			HttpMethod method = resolveMethod();
			String requestUrl = buildRequestUrl(method);
			logger.debug("[{}]: sending request to [{}]", getSystemId(), requestUrl);
			String response = gateway.sendAndReceieve(requestUrl, method, params);
			logger.debug("[{}]: got response [{}]", getSystemId(), response);
			return new SimpleCheckResult(true);
		} catch (Exception e) {
			return new ThrowableEncolsingCeckResult(e);
		}
	}

	private HttpMethod resolveMethod() {
		HttpMethod method = HttpMethod.GET;
		String methodHint = params.get(HINT_METHOD);// param never null
		if (methodHint != null) {
			try {
				method = HttpMethod.valueOf(methodHint);
			} catch (Exception e) {
				throw new IllegalStateException("Invalid value of hint [method]: " + methodHint, e);
			}
		}
		return method;
	}

	private String buildRequestUrl(HttpMethod method) {
		if (!HttpMethod.GET.equals(method)) {
			return getConnectionString();
		}
		StringBuilder sb = new StringBuilder(getConnectionString());
		char link = '?';
		for (Entry<String, String> entry : params.entrySet()) {
			String key = entry.getKey();
			if (!HINT_METHOD.equals(key)) {
				sb.append(link).append(key).append('=').append(entry.getValue());
				link = '&';
			}
		}
		return sb.toString();
	}

	public void setGateway(HttpRequestGateway gateway) {
		this.gateway = gateway;
	}

	public void setParams(Map<String, String> params) {
		if (params != null)
			this.params = params;
	}

}
