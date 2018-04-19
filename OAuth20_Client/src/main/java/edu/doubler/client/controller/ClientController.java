package edu.doubler.client.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.runner.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.doubler.client.domain.StateVo;
import edu.doubler.client.domain.User;
import edu.doubler.client.service.AuthRequestService;
import edu.doubler.client.util.EnumClient2OAuth;
import edu.doubler.client.util.EnumClientViewPath;

@Controller
public class ClientController {
	
	Logger logger = LoggerFactory.getLogger(ClientController.class);
	private static final String CLIENT_DIRECTORY = "client";
	private static Map<String, StateVo> stateMapData = new HashMap<String, StateVo>();
	
	@Autowired
	AuthRequestService authRequestService;
	
	@RequestMapping(value="/oauth20")
	public String initView(Model model){
		
		model.addAttribute("user", new User());
		
		return CLIENT_DIRECTORY + "/" + "login_view";
	}
	
	@RequestMapping(value="/oauth20/authorize")
	public String authorizationRequest(HttpServletRequest request){
		/**
		 * [ Client ㅡㅡ> Resource Owner ]
		 * - Authorization-Request
		 * - 해당 DaumBoard 의 로그인 페이지로 리다이렉트
		 * **/
		
		String apiUrl = authRequestService.getApiFullUrlOnAuthorize();
		
		// 상태토큰 세션 저장 (CSRF 방지를 위함)
		String state = authRequestService.getState();
		logger.info("== state from client : " + state);
		stateMapData.put(state, new StateVo(state));
		return "redirect:" + apiUrl;
	}
	
	@RequestMapping(value="/oauth20/authorize/callback", method=RequestMethod.GET)
	public String showCallbackView(HttpServletRequest request, HttpServletResponse response){
		/**
		 * [ Resource Owner ㅡㅡ> Client ]
		 * - 권한 사용 허용 및 거부에 대한 결과 접수
		 * **/
		
		String state = request.getParameter("state");
		String code = request.getParameter("code");
		
		logger.info("== state from oauth : " + state);
		logger.info("== code from oauth : " + code);
		
		String sesstionState = stateMapData.get(state).getState();
		logger.info(sesstionState);
		boolean verification = authRequestService.verifyCSRF(sesstionState, state);
		
		if(!verification){
			return EnumClientViewPath.CLIENT_OAUTH_URL.getURL();
		}
		
		// code 저장
		// state 삭제
		EnumClient2OAuth.CLIENT_TO_OAUTH_CODE.setValue(code);
		stateMapData.remove(state);
		return CLIENT_DIRECTORY + "/" + "callback_view";
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value="/oauth20/token")
	public String tokenRequest(Model model){
		/**
		 * [ Client ㅡㅡ> Authorization Server ]
		 * - 아래의 파라미터를 보내고 이후 Access Token 을 발급받는다.
		 * - client_ID
		 * - client_Secret_KEY
		 * - response_Type
		 * - api_url
		 * - callback_url
		 * - code
		 * 
		 * URLConnection 을 이용해서 보내고 이후에 토큰을 반환받는다.
		 * **/
		
		logger.info("== " + "oauth20/token : 코드 전송");
		Map<String, String> map = authRequestService.oauth20RequestToken();
		logger.info("== " + "oauth20/token : 액세스 토큰 반환");
		logger.info("== " + map.toString());
		
		model.addAttribute("access_token", map.get("access_token"));
		model.addAttribute("refresh_token", map.get("refresh_token"));
		model.addAttribute("token_type", map.get("token_type"));
		model.addAttribute("expires_in", map.get("expires_in"));
		model.addAttribute("jsonLine", map.get("jsonLine"));
		
		/**
		 * [ Authorization Server ㅡㅡ> Client ]
		 * **/
		if(map == null)
			return CLIENT_DIRECTORY + "/" + "token_error_view";
		return CLIENT_DIRECTORY + "/" + "token_view";
	}
	
	
	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * 사용하지 않는다.
	 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
	@RequestMapping(value="/oauth20/token/callback")
	public String showCallbackView(HttpServletRequest reqeust){
		/**
		 * [ Authorization Server ㅡㅡ> Client ]
		 * **/
		
		logger.info("== " + "token/callback 컨트롤러 진입");
		return CLIENT_DIRECTORY + "/" + "token_view";
	}
}
