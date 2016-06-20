package com.test.servicemonitor.web.controller;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class MockServiceController extends ControllerSupport {

	@RequestMapping(path = { "/echo" })
	public @ResponseBody String echo(HttpServletRequest request) {
		logger.info("[Mock Service]: receving echo request, url is [{}] and method is [{}]", request.getRequestURL(), request.getMethod());
		StringBuilder sb = new StringBuilder("/echo");

		Map<String, String[]> paramMap = request.getParameterMap();
		char link = '?';
		for (Entry<String, String[]> entry : paramMap.entrySet()) {

			for (String val : entry.getValue()) {
				String exp = entry.getKey() + "=" + val;
				logger.info("request parameter [{}]", exp);
				sb.append(link).append(exp);
				if (link == '?')
					link = '&';
			}
		}

		return sb.toString();
	}

}
