package com.test.servicemonitor.integration;

import org.springframework.http.HttpMethod;
import org.springframework.messaging.handler.annotation.Header;

public interface HttpRequestGateway {
	String sendAndReceieve(@Header("url") String url, @Header("method") HttpMethod method, Object parameters);
}
