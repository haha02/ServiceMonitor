package com.test.servicemonitor.web.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.test.servicemonitor.check.CheckResult;
import com.test.servicemonitor.check.impl.RestWebServiceLifeChecker;
import com.test.servicemonitor.integration.HttpRequestGateway;

@Controller
@RequestMapping("/")
public class MainController {

	@Autowired
	private HttpRequestGateway gateway;

	@RequestMapping(path = { "/", "index*" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String index() {
		return "index";
	}

	@RequestMapping(path = { "/echo" })
	@ResponseBody
	public String echo(HttpServletRequest request) {
		System.out.println(request.getRequestURL());
		System.out.println(request.getMethod());
		StringBuilder sb = new StringBuilder("echo");
		Map<String, String[]> paramMap = request.getParameterMap();
		char link = '?';
		for (Entry<String, String[]> entry : paramMap.entrySet()) {

			for (String val : entry.getValue()) {
				String exp = entry.getKey() + "=" + val;
				System.out.println(exp);
				sb.append(link).append(exp);
				if (link == '?')
					link = '&';
			}
		}
		return sb.toString();
	}

	@RequestMapping(path = { "/test" })
	@ResponseBody
	public String doTest() {
		Map<String, String> param = new HashMap<>();
		param.put("p1", "a");
		param.put("p2", "c");
		// param.put("http-method", "POST");
		RestWebServiceLifeChecker checker = new RestWebServiceLifeChecker("sys1", "http://localhost:8080/test/echo");
		checker.setGateway(gateway);
		checker.setParams(param);
		CheckResult result = checker.check();
		return "test:" + result.isPassed();
	}

}
