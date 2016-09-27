package com.db.sysgob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("login")
public class LoginController {

	@RequestMapping("validate")
	public void validateUser() {
		
	}
	
	@RequestMapping("error")
	public void invalidUser() {
		
	}
}
