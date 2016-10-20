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
	private final String TAG = LoginController.class.getSimpleName();
	private static final Logger log = LoggerFactory.getLogger("sysgob_log");

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
			log.debug(TAG, "Validating user credentials");

			UserBasicInfo user = userWS.findByUsername(username);
			User userEntered = userWS.findById(user.getUserId());

			/* Validate password */
			if (user != null && userEntered.getPassword().equals(password)) {

				log.debug(TAG, "User credentials: CORRECT [" + userEntered + "]");

				/* Add basic user info */
				model.addAttribute("user", username);
				model.addAttribute("roleId", user.getRoleId());
				model.addAttribute("role", user.getRoleName());
				model.addAttribute("dependencyId", user.getDependencyId());
				model.addAttribute("dependency", user.getDependencyName());
				view = "index";
			} else {
				log.debug(TAG, "User credentials: WRONG [" + userEntered + "]");
				model.addAttribute("failure", true);
				view = "login";
			}

		} catch (Exception e) {
			log.debug(TAG, e.getMessage());
			model.addAttribute("blocked", true);
			view = "login";
		}

		return view;
	}

}
