package com.test.servicemonitor.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.servicemonitor.check.CheckerFactory;
import com.test.servicemonitor.persistance.RemoteSystem;
import com.test.servicemonitor.persistance.RemoteSystemService;
import com.test.servicemonitor.util.Utils;
import com.test.servicemonitor.web.form.RemoteSystemForm;

/**
 * Controller of {@link RemoteSystem} CRUD pages
 *
 */
@Controller
@RequestMapping("/remoteSystem")
public class RemoteSystemController extends ControllerSupport {
	private static final String ROOT_PATH = "remoteSystem/main";
	private static final String REDIRECT_ROOT_PATH = "redirect:/remoteSystem/";
	private static final String EDIT_PATH = "remoteSystem/edit";
	private static final String ADD_PATH = "remoteSystem/add";
	private static final String DELETE_PATH = "remoteSystem/delete";

	@Autowired
	private RemoteSystemService remoteSystemService;

	@Autowired
	private CheckerFactory checkerFactory;

	@ModelAttribute("form")
	public RemoteSystemForm initForm() {
		return new RemoteSystemForm();
	}

	@RequestMapping("/")
	public String root(ModelMap model) {
		List<RemoteSystem> remoteSystems = remoteSystemService.getAll();
		model.put("remoteSystems", remoteSystems);
		return ROOT_PATH;
	}

	@RequestMapping("/add")
	public String add(@ModelAttribute("form") RemoteSystemForm form) {
		form.setSupportedCheckerTypes(getSupportedCheckerTypes());
		return ADD_PATH;
	}

	@RequestMapping("/edit")
	public String edit(@ModelAttribute("form") RemoteSystemForm form, ModelMap model) {
		RemoteSystem remoteSystem = remoteSystemService.get(form.getSystem_id());
		form.fromRemoteSystem(remoteSystem);
		form.setSupportedCheckerTypes(getSupportedCheckerTypes());
		return EDIT_PATH;
	}

	@RequestMapping("/delete")
	public String delete(@ModelAttribute("form") RemoteSystemForm form, ModelMap model) {
		RemoteSystem remoteSystem = remoteSystemService.get(form.getSystem_id());
		form.fromRemoteSystem(remoteSystem);
		return DELETE_PATH;
	}

	@RequestMapping("/addSubmit")
	public String addSubmit(@Valid @ModelAttribute("form") RemoteSystemForm form, BindingResult bindingResult,
			ModelMap model) {
		validateHints(form, bindingResult);
		if (bindingResult.hasErrors()) {
			model.put("errors", toErrorMsg(bindingResult.getAllErrors()));
		} else {
			RemoteSystem remoteSystem = form.toRemoteSystem();
			remoteSystemService.create(remoteSystem);
			model.put("msg", "Remote system [" + remoteSystem.getSystem_id() + "] added.");
		}
		return REDIRECT_ROOT_PATH;
	}

	@RequestMapping("/editSubmit")
	public String editSubmit(@Valid @ModelAttribute("form") RemoteSystemForm form, BindingResult bindingResult,
			ModelMap model) {
		validateHints(form, bindingResult);
		if (bindingResult.hasErrors()) {
			model.put("errors", toErrorMsg(bindingResult.getAllErrors()));
		} else {
			RemoteSystem remoteSystem = form.toRemoteSystem();
			remoteSystemService.update(remoteSystem);
			model.put("msg", "Remote system [" + remoteSystem.getSystem_id() + "] edited.");
		}
		return REDIRECT_ROOT_PATH;
	}

	@RequestMapping("/deleteSubmit")
	public String deleteSubmit(@ModelAttribute("form") RemoteSystemForm form, BindingResult bindingResult,
			ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.put("errors", toErrorMsg(bindingResult.getAllErrors()));
		} else {
			RemoteSystem remoteSystem = form.toRemoteSystem();
			remoteSystemService.delete(remoteSystem.getSystem_id());
			model.put("msg", "Remote system [" + remoteSystem.getSystem_id() + "] deleted.");
		}
		return REDIRECT_ROOT_PATH;
	}

	private void validateHints(RemoteSystemForm form, BindingResult bindingResult) {
		// TODO Design new mechanism to avoid duplicating hints validation logic in adding remote system entry and life checker construction.
		String checker_type = form.getChecker_type();
		if ("SQL".equals(checker_type)) {
			String hints = form.getHints();
			Properties hintsProp = Utils.parseHints(hints);
			if (hintsProp.getProperty("driverClass") == null) {
				bindingResult.rejectValue("hints", "hints.missing.driverClass", "[driverClass] is missing in hints.");
			}
		}
	}

	private List<String> getSupportedCheckerTypes() {
		List<String> list = new ArrayList<>(checkerFactory.getSupportedTypes());
		Collections.sort(list);
		return list;
	}

}
