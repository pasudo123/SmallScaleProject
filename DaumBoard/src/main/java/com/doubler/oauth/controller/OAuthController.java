package com.doubler.oauth.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.doubler.EnumRedirectPath;
import com.doubler.EnumViewPath;
import com.doubler.oauth.service.OAuthService;

@Controller
public class OAuthController {
	
	Logger logger = LoggerFactory.getLogger(OAuthController.class);
	
	@Autowired 
	OAuthService oauthService;
	
	@RequestMapping(value="/oauth20/authorize", method=RequestMethod.GET)
	public String authorizeClientId(HttpServletRequest request, HttpServletResponse response){

		logger.info("== 인증서버 진입");
		logger.info("== request.getParamter - 세션 저장 시도");
		Map<String, String> authMapData = new HashMap<String, String>();
		authMapData.put("clientId", request.getParameter("client_id"));
		authMapData.put("state", request.getParameter("state"));
		authMapData.put("callbackUrl", request.getParameter("callback_url"));
		authMapData.put("reponseType", request.getParameter("response_type"));
		authMapData.put("scope", request.getParameter("scope"));
		request.getSession().setAttribute("Authorization", authMapData);
		logger.info("== request.getParamter - 세션 저장 성공");
		
		
		// (1) Client ID 를 통해서 Service URL 확인
		// (1-1)   일치 >> 로그인 URL 
		// (1-2) 불 일치 >> 해당 서비스 애플리케이션 아니라는 문구
		logger.info("== service URL 체크");
		boolean flag = oauthService.checkServiceURL(authMapData.get("callbackUrl"), authMapData.get("clientId"));
		logger.info("== service URL 체크  - 성공");
		
		// 서비스 URL 이 아닌 경우
		if(!flag){
			logger.info("== service URL 등록  X");
			return "redirect:" + EnumRedirectPath.LOGIN_ERROR_URL.getURL();
		}
		
		logger.info("== service URL 등록  O");
		// 로그인 창을 띄워주며 redirect
		return "redirect:" + EnumRedirectPath.LOGIN_URL.getURL();
	}
	
	@RequestMapping(value="/oauth20/authorize/grant", method=RequestMethod.GET)
	public String showAOuthGrantView(HttpServletRequest request){
		
		// (1) 로그인 왼료 이후, 권한 부여 
		// (1-1)   허용 >> DB 저장 >> Callback URL
		// (1-2) 미 허용 >> callBack URL
		
		/** 
		 * 세션에 있는 데이터들을 디비에 저장해야 함 스코프 
		 * 일단은 보류 [2018.04.18]
		 * **/
		
		return EnumViewPath.OAUTH_GRANT_VIEW.getPath();
	}
	
	@RequestMapping(value="/oauth20/authorize/grant/{answer}")
	public String 
	
}
