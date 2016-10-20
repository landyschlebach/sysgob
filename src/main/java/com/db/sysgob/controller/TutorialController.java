package com.db.sysgob.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TutorialController {
	private final String TAG = TutorialController.class.getSimpleName();
	private static final Logger log = LoggerFactory.getLogger("sysgob_log");
	
	@RequestMapping("/tutorial")
	public String loadTutorial(ModelMap model) {
		log.debug(TAG, "Loading tutorial view");
		return "tutorial";
	}
}
