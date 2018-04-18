package edu.doubler.client.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.doubler.client.domain.User;
import edu.doubler.client.service.AuthRequestService;
import edu.doubler.client.util.EnumSettings;

@Controller
public class ClientController {
	
	Logger logger = LoggerFactory.getLogger(ClientController.class);
	private static final String CLIENT_DIRECTORY = "client";
	
	@RequestMapping(value="/oauth20")
	public String initView(Model model){
		
		model.addAttribute("user", new User());
		
		return CLIENT_DIRECTORY + "/" + "login_view";
	}
	
	@RequestMapping(value="/oauth20/callback")
	public String showCallbackView(){
		
		return CLIENT_DIRECTORY + "/" + "callback_view";
	}
	
	@RequestMapping(value="/oauth20/authorize")
	public String authorizationRequest(HttpServletRequest request){
		/**
		 * [ Client ㅡㅡ> Resource Owner ]
		 * - Authorization-Request
		 * - 해당 DaumBoard 의 로그인 페이지로 리다이렉트
		 * **/
		
		AuthRequestService authRequestService = new AuthRequestService();
		String apiUrl = authRequestService.getApiFullUrl();
		
		// 상태토큰 세션 저장 (CSRF 방지를 위함)
		String state = EnumSettings.CLIENT_TO_OWNER_STATE.generateState();
		request.getSession().setAttribute("state", state);
		
		return "redirect:" + apiUrl;
	}
}
