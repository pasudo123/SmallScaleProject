package com.apple.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apple.domain.User;
import com.apple.domain.UserDto;
import com.apple.login.service.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	LoginService loginService;
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping(value="/login")
	public String showLoginForm(HttpServletRequest request, Model model){
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("user") != null){
			model.addAttribute("loginBtnValue", "로그아웃");
			logger.info("계정 있는 상태");
		}
		else{
			// 계정이 없는 상태
			model.addAttribute("user", new User());
			model.addAttribute("formActionURL", "./process");
			model.addAttribute("loginBtnValue", "로그인");
			logger.info("계정 없는 상태");
		}
		
		return "/login/login_form";
	}
	
	@RequestMapping(value="/process")
	public String getUserInfo(
	HttpServletRequest request,
	@ModelAttribute("user") User user){
		
		UserDto userDto = loginService.getUser(user);
		
		if(userDto != null){
			logger.info("DB 계정 존재");
			request.setAttribute("id", userDto.getId());
			request.setAttribute("sessionID", request.getRequestedSessionId());
		}
		
		return "redirect:/";
	}
	
	
}
