package com.ptsolution.web.controllet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping(value = "/login.htm")
	public String loginPage(){
		return "login";
	}
}
