package com.doubler.oauth.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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
import com.doubler.oauth.domain.CodeVo;
import com.doubler.oauth.domain.TokenVo;
import com.doubler.oauth.service.OAuthService;

@Controller
public class OAuthController {
	
	Logger logger = LoggerFactory.getLogger(OAuthController.class);
	private static Map<String, CodeVo> codeMapData = new HashMap<String, CodeVo>();
	private static Map<String, TokenVo> tokenMapData = new HashMap<String, TokenVo>();
	
	@Autowired 
	OAuthService oauthService;
	
	@RequestMapping(value="/oauth20/authorize", method=RequestMethod.GET)
	public String authorizeClientId(HttpServletRequest request, HttpServletResponse response){

		logger.info("== 인증서버 진입 : 코드 획득을 위함");
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
		// (1-1)   허용 >> DB 저장 >> Callback URL + state(CSRF 방지)
		// (1-2) 미 허용 >> callBack URL
		String answer = request.getParameter("answer");
		if(answer != null){
			logger.info("== 접근 권한 요청에 대한 응답");
			
			@SuppressWarnings("unchecked")
			HashMap<String, String> authMapData = (HashMap<String, String>) request.getSession().getAttribute("Authorization");
			String callbackUrl = authMapData.get("callbackUrl");
			String state = authMapData.get("state");
			String code = oauthService.generateCode();
			
			// callbackUrl 디코딩
			try {
				callbackUrl = URLDecoder.decode(callbackUrl, StandardCharsets.UTF_8.toString());
			} 
			catch (UnsupportedEncodingException e) { e.printStackTrace(); }
			
					
			if(answer.equals("allow")){
				/** 
				 * 세션에 있는 데이터들을 디비에 저장하여,
				 * 해당 계정에 대한 스코프를 설정,
				 * 현재는 데이터베이스에 미리 저장해놓은 상태
				 * 추후 수정해야함
				 * **/
				
				// GET 방식의  파라미터로 아래 내용 전달
				// - CSRF 방지를 위한 [state]
				// - [code]
				// - code 는 해쉬맵에 VO 밸류로 저장
				codeMapData.put(code, new CodeVo(code));
				callbackUrl = callbackUrl + "?state=" + state + "&code=" + code;
				logger.info("== 접근 권한 요청 >> 허용");
				logger.info(callbackUrl);
				return "redirect:" + callbackUrl;
			}
			else{
				logger.info("== 접근 권한 요청 >> 거부");
				return "redirect:" + callbackUrl;
			}
		}
		
		return EnumViewPath.OAUTH_GRANT_VIEW.getPath();
	}
	
	@RequestMapping(value="/oauth20/token", method=RequestMethod.GET)
	public void tokenClientId(HttpServletRequest request, HttpServletResponse response){
		logger.info("== 인증서버 진입 : 토큰 획득을 위함");
		
		// (1) token 획득
		// (1-1) Client Id & Client Secret Key 를 DB 비교
		// (1-2)   존재 >> code 확인 >> Access Token & Refresh Token & Token Type & expires_in JSON 형태로 반환
		// (1-3) 미 존재 >> callback URL, 에러 메세지 같이
		// (1-4) ** [필독] void 형태로 response 에 데이터를 담아 보냄.
		String clientId = request.getParameter("client_id");
		String clientSecret = request.getParameter("client_secret");
		String code = request.getParameter("code");
		String scope = request.getParameter("scope");
//		String callbackUrl = request.getParameter("callback_url");
		String sessionCode = codeMapData.get(code).getCode();
		
		// client_id & client_secret 확인
		boolean flag = oauthService.checkClientIdAndClientSecret(clientId, clientSecret);
		
		if(!flag){
			logger.info("== " + "Client ID & Client Secret KEY 미존재");
			try {
			response.sendError(400, "클라이언트 아이디 및 시크릿 미존재");
			return;
		} catch (IOException e) {e.printStackTrace();}
		}
		logger.info("== " + "Client ID & Client Secret KEY 존재");
		
		// code 확인
		logger.info("== code 검사");
		if(!sessionCode.equals(code)){
			logger.info("== code 검사 실패");
			try {
				response.sendError(400, "code 검사 실패");
				return;
			} catch (IOException e) {e.printStackTrace();}
		}
		
		codeMapData.remove(code);
		logger.info("== code 검사 성공");
		
		
		// 토큰 생성 JSON 형식, accessToken 을 키 값 tokenVo 를 밸류
		TokenVo tokenVo = oauthService.generateToken();
		tokenVo.setScope(scope);
		
		// 해쉬맵 저장 : 해당 액세스 토큰에 대한 관련 데이터 전부 저장
		tokenMapData.put(tokenVo.getAccessToken(), tokenVo);
		logger.info("== 토큰 생성 완료 및 해쉬맵 저장");
		
		response.setHeader("access_token", tokenVo.getAccessToken());
		response.setHeader("refresh_token", tokenVo.getRefreshToken());
		response.setHeader("token_type", tokenVo.getTokenType());
		response.setHeader("expires_in", String.valueOf(tokenVo.getExpiresIn()));
		try {
			response.getOutputStream().print(tokenVo.toJSON());
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, TokenVo> getTokenMapData(){
		return tokenMapData;
	}
}
