package com.db.sysgob.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TutorialController {
	private static final Logger log = LoggerFactory.getLogger(TutorialController.class);
	
	@RequestMapping("/tutorial")
	public String loadTutorial(ModelMap model, HttpSession session) {
		log.debug("Loading tutorial view");
		return "tutorial";
	}
}
