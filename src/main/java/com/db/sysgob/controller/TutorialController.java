package com.db.sysgob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("tutorial")
public class TutorialController {
	
	public String loadTutorial(ModelMap model, 
			@ModelAttribute("user") String user, 
			@ModelAttribute("roleId") Long roleId,
			@ModelAttribute("dependencyId") Long dependencyId) {
		
		/* Basic User Info */	    
		model.addAttribute("user", user);
	    model.addAttribute("roleId", roleId);
	    model.addAttribute("dependencyId", dependencyId);
		
		return "tutorial";
	}
}
