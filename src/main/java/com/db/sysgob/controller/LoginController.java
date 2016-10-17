package com.db.sysgob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.db.sysgob.entity.UserBasicInfo;
import com.db.sysgob.repository.UserRepository;

@Controller
@SessionAttributes("user")
@RequestMapping(value="login")
public class LoginController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserBasicInfo user;
	
	@RequestMapping(value="acceso")
	public String systemAccess(ModelMap model) {
		return "login";
	}
	
	@RequestMapping(value="validar", method = RequestMethod.POST)
	public String validateUser(ModelMap model, @RequestParam String username) {
	    String view = "";
	    
	    try {
	    user = userRepository.getUserByName(username);
	    
	    model.addAttribute("user", username);
	    model.addAttribute("role", user.getRoleId());
	    model.addAttribute("dependency", user.getDependencyId());
	    view = "index";
	    
	    } catch(Exception e) {
	    	e.getMessage();
	    	model.addAttribute("failure", true);
	    	view = "login";
	    }
	    
	    return view;
	}
	
	@RequestMapping(value="error")
	public String invalidUser(ModelMap model) {
		return "login";
	}
}
