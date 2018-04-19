package edu.doubler.client.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import edu.doubler.client.util.EnumClient2OAuth;
import edu.doubler.client.util.EnumClient2OWNER;

@Service
public class AuthRequestServiceImpl implements AuthRequestService{
	
	Logger logger = LoggerFactory.getLogger(AuthRequestServiceImpl.class);
	
	/**
	 * [ Client ㅡㅡ> Resource Owner ]
	 * [ Client Side ]
	 * 
	 * - api URL 획득 : Resource Owner URL
	 * - CSRF 방지를 위한 상태 토큰 생성
	 * - 클라이언트 아이디 획득
	 * - 콜백 URL (리다이렉트 URL) 획득
	 * - Response 타입 획득 : 코드를 획득한다는 의미
	 * **/
	
	private String state = null;
	
	public String getState(){
		return state;
	}
	
	private HashMap<String, String> getAuthorizationParameter(){
		HashMap<String, String> map = new HashMap<String, String>();
		
		for(EnumClient2OWNER client2owner : EnumClient2OWNER.values()){
			if(client2owner.getKey().equals("client_secret"))
				continue;
			
			// 이후에 따로 넣을 것이기 때문에 제외
			if(client2owner.getKey().equals("api_url"))
				continue;
			
			if(client2owner.getKey().equals("state")){
				this.state = client2owner.generateState();
				client2owner.setValue(state);
				map.put(client2owner.getKey(), client2owner.getValue());
			}
			else
				map.put(client2owner.getKey(), client2owner.getValue());
		}
		
		return map;
	}
	
	private HashMap<String, String> getTokenParameter(){
		HashMap<String, String> map = new HashMap<String, String>();
		
		for(EnumClient2OAuth client2oauth : EnumClient2OAuth.values()){
			
			if(client2oauth.getKey().equals("api_url"))
				continue;
			
			map.put(client2oauth.getKey(), client2oauth.getValue());
		}
	
		return map;
	}
	
	public String getApiFullUrlOnAuthorize(){
		
		HashMap<String, String> map = getAuthorizationParameter();
		Object[]keyArray = map.keySet().toArray();
		
		StringBuilder url = new StringBuilder("");
		url.append(EnumClient2OWNER.CLIENT_TO_OWNER_API_URL.getValue());
		url.append("?");
		
		for(int i = 0; i < keyArray.length; i++){
			
			String key = (String) keyArray[i];
			String value = map.get(key);
			
			// callbackUrl 인코딩
			if(key.equalsIgnoreCase("callback_url")){
				try {
					value = URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
				} 
				catch (UnsupportedEncodingException e) {e.printStackTrace();}
			}
			
			url.append(key);
			url.append("=");
			url.append(value);
			
			if(i < keyArray.length - 1)
				url.append("&");
		}
		
		return url.toString();
	}
	
	public boolean verifyCSRF(String sessionState, String parameterState){
		logger.info("== " + "CSRF 검증");
		
		if(sessionState.equals(parameterState)){
			logger.info("== " + "CSRF 공격을 받지 않음");
			return true;
		}
		
		logger.info("== " + "CSRF 공격을 받았음");
		return false;
	}
	
	public String getApiFullUrlOnToken(){
		
		HashMap<String, String> map = getTokenParameter();
		Object[]keyArray = map.keySet().toArray();
		
		StringBuilder url = new StringBuilder("");
		url.append(EnumClient2OAuth.CLIENT_TO_OAUTH_API_URL.getValue());
		url.append("?");
		
		for(int i = 0; i < keyArray.length; i++){
			
			String key = (String) keyArray[i];
			String value = map.get(key);
			
			// callbackUrl 인코딩
			if(key.equalsIgnoreCase("callback_url")){
				try {
					value = URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
				} 
				catch (UnsupportedEncodingException e) {e.printStackTrace();}
			}
			
			url.append(key);
			url.append("=");
			url.append(value);
			
			if(i < keyArray.length - 1)
				url.append("&");
		}
		
		return url.toString();
	}
}
