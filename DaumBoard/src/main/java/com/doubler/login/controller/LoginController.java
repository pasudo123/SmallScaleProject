package com.doubler.login.controller;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.doubler.EnumRedirectPath;
import com.doubler.EnumViewPath;
import com.doubler.login.domain.User;
import com.doubler.login.service.LoginService;

@Controller
public class LoginController {
	
	Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	LoginService loginService;
	
	// 로그인 화면
	@RequestMapping(value="/login")
	public String showLoginView(Model model){
		
		model.addAttribute("formActionValue", "login/login_check");
		model.addAttribute("user", new User());
		
		logger.info("== loginPage");
		return EnumViewPath.LOGIN_VIEW.getPath();
	}
	
	// 서비스 URL 불일치시, 로그인 실패 화면
	@RequestMapping(value="/login_error")
	public String showLoginErrView(){
		logger.info("== loginErrPage");
		return EnumViewPath.LOGIN_ERROR_VIEW.getPath();
	}
	
	// 유저 존재 여부
	@RequestMapping(value="/login/login_check")
	public String checkUserAndReturnView(
	@ModelAttribute("user") User user, 
	Model model,
	HttpSession httpSession){
		
		logger.info("== loingCheck");
		logger.info("== authorization Map Data : " + httpSession.getAttribute("Authorization").toString());
		
		// 유저 존재
		if(loginService.checkUser(user)){
			logger.info("== 유저 존재");
			
			if(httpSession.getAttribute("Authorization") != null){
				logger.info("== 권한 사용 유저");
				return "redirect:" + EnumRedirectPath.USER_OAUTH_GRANT_URL.getURL();
			}
			
			logger.info("== 권한 미사용 유저");
			// 게시판이 있는 페이지로 리다이렉트
			httpSession.setAttribute("user", user);
			return "redirect:" + EnumRedirectPath.BOARD_LIST_URL.getURL();
		}

		// 유저 미존재
		logger.info("== 유저 미존재");
		return "redirect:" + EnumRedirectPath.LOGIN_URL.getURL();
	}
}
