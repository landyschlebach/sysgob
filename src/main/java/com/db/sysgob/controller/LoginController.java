package com.db.sysgob.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.db.sysgob.entity.User;
import com.db.sysgob.entity.UserBasicInfo;
import com.db.sysgob.service.UserService;

@Controller
@RequestMapping("/login")
@SessionAttributes("user")
public class LoginController {
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService userWS;

	@RequestMapping(value = "/acceso")
	public String systemAccess(ModelMap model) {
		return "login";
	}

	@RequestMapping(value = "/acceso", method = RequestMethod.POST)
	public String validateUser(ModelMap model, String username, String password) {
		String view = "";

		try {
			log.debug("Validating user credentials");

			UserBasicInfo userEntered = userWS.findByUsername(username);
			User user = userWS.findById(userEntered.getUserId());

			/* Validate password */
			if (user.getPassword().equals(password)) {

				log.debug("User credentials: CORRECT [" + user + "]");

				/* Add basic user info */
				model.addAttribute("user", username);
				model.addAttribute("roleId", userEntered.getRoleId());
				model.addAttribute("role", userEntered.getRoleName());
				model.addAttribute("dependencyId", userEntered.getDependencyId());
				model.addAttribute("dependency", userEntered.getDependencyName());
				view = "index";
			} else {
				log.debug("User credentials: WRONG [" + user + "]");
				model.addAttribute("failure", true);
				view = "login";
			}

		} catch (Exception e) {
			log.debug(e.getMessage());
			model.addAttribute("blocked", true);
			view = "login";
		}

		return view;
	}

}
