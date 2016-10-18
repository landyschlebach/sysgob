package com.db.sysgob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	@Autowired
	private UserService userWS;
	
	@Autowired
	private UserBasicInfo user;
	
	@Autowired
	private User userEntered;
	
	@RequestMapping(value="/acceso")
	public String systemAccess(ModelMap model) {
		return "login";
	}
	
	@RequestMapping(value="/acceso", method = RequestMethod.POST, params={"user"})
	public String validateUser(ModelMap model, @ModelAttribute("userInfo") User username) {
	    String view = "";
	    
	    try {
		    user = userWS.findByUsername(username.getUser());
		    userEntered = userWS.findById(user.getUserId());
		    
		    /* Validate password */
		    if(user != null && userEntered.getPassword().equals(username.getPassword())) {
		    
		    	/* Add basic user info */
			    model.addAttribute("user", username);
			    model.addAttribute("roleId", user.getRoleId());
			    model.addAttribute("dependencyId", user.getDependencyId());
			    view = "index";
		    } else {
		    	model.addAttribute("failure", true);
		    	view = "login";
		    }
	    
	    } catch(Exception e) {
	    	e.getMessage();
	    	model.addAttribute("blocked", true);
	    	view = "login";
	    }
	    
	    return view;
	}
	
}
