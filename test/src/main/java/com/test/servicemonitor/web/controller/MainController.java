package com.test.servicemonitor.web.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.servicemonitor.main.MainScheduler;
import com.test.servicemonitor.persistance.MonitorStatus;
import com.test.servicemonitor.persistance.MonitorStatusService;

/**
 * Controller of index page
 *
 */
@Controller
@RequestMapping("/")
public class MainController {

	private static final String INDEX_PATH = "index";
	private static final String REDIRECT_INDEX_PATH = "redirect:/index";

	@Autowired
	private MonitorStatusService monitorStatusService;

	@Autowired
	private MainScheduler mainScheduler;

	/**
	 * Index page
	 * 
	 */
	@RequestMapping(path = { "/", "index*" }, method = { RequestMethod.GET })
	public String index(ModelMap modelMap) {
		List<MonitorStatus> statusList = monitorStatusService.getAll();
		Collections.sort(statusList, new Comparator<MonitorStatus>() {
			@Override
			public int compare(MonitorStatus o1, MonitorStatus o2) {
				return o1.getSystem_id().compareTo(o2.getSystem_id());
			}
		});
		modelMap.put("statusList", statusList);
		return INDEX_PATH;
	}

	/**
	 * Start to monitor a remote system
	 * 
	 */
	@RequestMapping(path = { "/start" }, method = { RequestMethod.GET })
	public String start(@RequestParam("system_id") String system_id, ModelMap modelMap) {
		mainScheduler.start(system_id);
		return REDIRECT_INDEX_PATH;
	}

	/**
	 * Stop monitoring a remote system
	 * 
	 */
	@RequestMapping(path = { "/stop" }, method = { RequestMethod.GET })
	public String stop(@RequestParam("system_id") String system_id, ModelMap modelMap) {
		mainScheduler.stop(system_id);
		return REDIRECT_INDEX_PATH;
	}

}
