package edu.doubler.login.controller;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.doubler.login.domain.AppleUser;
import edu.doubler.login.service.LoginService;

@Controller
public class LoginController {
	
	@Autowired 
	LoginService loginService;
	
	@RequestMapping(value="/")
	public String Launcher(Model model){
		
		// 도메인 객체 
		model.addAttribute("AppleUser", new AppleUser());
		
		return "/login_view/login_form_apple";
	}
	
	@RequestMapping(value="/login")
	public String logIn(
	@ModelAttribute("AppleUser") AppleUser appleUser){
		
		boolean check = loginService.checkUser(appleUser);
		
		// check = true
		if(check)
			return "board_view/board_list_screen";
		
		return "/login_view/login_form_apple";
	}
}