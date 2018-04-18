package edu.doubler.client.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import edu.doubler.client.util.EnumSettings;

public class AuthRequestService{
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
	private HashMap<String, String> getAuthorizationParameter(){
		HashMap<String, String> map = new HashMap<String, String>();
		
		for(EnumSettings setting : EnumSettings.values()){
			if(setting.getKey().equals("client_secret"))
				continue;
			
			if(setting.getKey().equals("api_url"))
				continue;
			
			if(setting.getKey().equals("state"))
				map.put(setting.getKey(), setting.generateState());
			else
				map.put(setting.getKey(), setting.getValue());
		}
		
		return map;
	}
	
	public String getApiFullUrl(){
		
		HashMap<String, String> map = getAuthorizationParameter();
		Object[]keyArray = map.keySet().toArray();
		
		StringBuilder url = new StringBuilder("");
		url.append(EnumSettings.CLIENT_TO_OWNER_API_URL.getValue());
		url.append("?");
		
		for(int i = 0; i < keyArray.length; i++){
			
			String key = (String) keyArray[i];
			String value = map.get(key);
			
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
