package com.db.sysgob.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.db.sysgob.entity.Dependency;
import com.db.sysgob.entity.Role;
import com.db.sysgob.entity.User;
import com.db.sysgob.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService userWS;
	
	@RequestMapping(value = "/acceso")
	public String systemAccess(ModelMap model, HttpSession session) {
		return "login";
	}

	@RequestMapping(value = "/acceso", method = RequestMethod.POST)
	public String validateUser(ModelMap model, String username, String password, HttpSession session) {
		String view = "";

		try {
			log.debug("Validating user credentials: username=" + username + ", password=" + password);
			
			User userEntered = userWS.findByUsername(username);
			log.debug("User entered= " + userEntered);
			
			User user = userWS.findById(userEntered.getUserId());
			log.debug("Actual user= " + user);

			/* Validate password */
			if (user.getPassword().equals(password)) {
				log.debug("User credentials: CORRECT [" + user + "]");
				Role role = userWS.findUserRole(user.getRoleId());
				Dependency dependency = userWS.findUserDependency(role.getDependencyId());
				
				session.setAttribute("user", user);
				session.setAttribute("role", role);
				session.setAttribute("dependency", dependency);
				
				view = "redirect:/home";
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
