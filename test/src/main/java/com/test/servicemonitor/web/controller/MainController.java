package com.test.servicemonitor.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.servicemonitor.persistance.MonitorStatus;
import com.test.servicemonitor.persistance.MonitorStatusService;

@Controller
@RequestMapping("/")
public class MainController {

	@Autowired
	private MonitorStatusService monitorStatusService;

	@RequestMapping(path = { "/", "index.*" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String index(ModelMap modelMap) {
		List<MonitorStatus> statusList = monitorStatusService.getAll();
		modelMap.put("statusList", statusList);
		return "index";
	}

}
