package com.test.servicemonitor.integration;

import org.springframework.http.HttpMethod;
import org.springframework.messaging.handler.annotation.Header;

/**
 * Gateway to Spring Integration based HTTP requesting machinery
 *
 */
public interface HttpRequestGateway {
	/**
	 * Send HTTP request and receive the response content
	 * 
	 * @param url
	 *            the request URL
	 * @param method
	 *            the HTTP method to use
	 * @param parameters
	 *            HTTP parameters, the object will be convert to parameter map using Spring Integration's object-to-map transformer.
	 * @return the response content
	 */
	String sendAndReceieve(@Header("url") String url, @Header("method") HttpMethod method, Object parameters);
}
