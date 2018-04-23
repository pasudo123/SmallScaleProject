package com.doubler.oauth.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.doubler.oauth.domain.TokenVo;
import com.doubler.oauth.service.ApiCallService;

//@CrossOrigin(origins="*")
@Controller
public class ApiCallController {
	/**
	 * API 호출 관련 컨트롤러
	 * **/
	Logger logger = LoggerFactory.getLogger(ApiCallController.class);
	
	@Autowired
	ApiCallService apiCallService;
	
	@RequestMapping(value="api_call/{reqScope}", method=RequestMethod.POST)
	public void apiCall(
	@PathVariable("reqScope") String reqScope,
	HttpServletRequest request, HttpServletResponse response){
		
		OAuthController oauthController = new OAuthController();
		Map<String, TokenVo> tokenMapData = oauthController.getTokenMapData();
		
		logger.info("== " + "게시판 api 호출");
		
		logger.info("== " + "accessToken 확인하기");
		
		/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 
		 * 		    [ 서버에서 CORS요청을 처리할 때 지정하는 헤더 ]
		 * - Access-Control-Allow-Origin: <origin> | *
		 * (1) <origin> : 요청 도메인의 URI 를 지정
		 * (2) * : 모든 도메인은 서버로 접근 허용
		 * 
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		
		/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 
		 * 참고 글 https://swagger.io/docs/specification/authentication/bearer-authentication/
		 * conn.setRequestProperty("Authorization", "Bearer " + accessToken);
		 * HTTP 인증 체계 / 무기명 인증 체계 /
		 * 해당 토큰의 소지자는 리소스에 액세스 할 권한이 있음을 알린다.
		 * 
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
		String accessToken = request.getHeader("Authorization").split("Bearer ")[1];
		if(tokenMapData.get(accessToken) == null){
			logger.info("== " + "accessToken 확인실패 : 유효하지 않은 토큰");
			try {
				response.getOutputStream().write("유효하지 않은 토큰".getBytes());
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		logger.info("== " + "accessToken 확인성공 : 유효 토큰");
		TokenVo tokenVo = tokenMapData.get(accessToken);
		
		logger.info("== " + "scope 확인");
		String scope = tokenVo.getScope();
		String scopeArray[] = scope.split("\\+");	// '+' 로 분리
		
		// 전달받은 스코프가 해당 액세스 스코프에 포함되는지 확인
		boolean scopeFlag = false;
		for(int i = 0; i < scopeArray.length; i++){
			if(reqScope.equals(scopeArray[i])){
				scopeFlag = true;
				break;
			}
		}
		
		if(!scopeFlag){
			logger.info("== " + "허용되지 않은 " + reqScope + " 권한입니다.");
			try {
				response.getOutputStream().write("허용되지 않은 scope".getBytes());
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		logger.info("== " + "허용된 " + reqScope + " 권한입니다.");
		
		// 현재 스코프 먼저 확인.
		switch (reqScope) {
			case "read":
				String result = apiCallService.apiCallByRead();
				try {
					response.getOutputStream().write(result.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
				
			case "write":
				apiCallService.apiCallByWrite("", "", "");
				break;
				
			case "delete":
				apiCallService.apiCallByDelete(0);
				break;
				
			default:
		}
	}
}
