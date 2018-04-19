package edu.doubler.client.util;

import java.math.BigInteger;
import java.security.SecureRandom;

public enum EnumSettings {
	
	CLIENT_ID("client_id", "KJgReXGkt5_K3KHbtiWx"),
	CLIENT_SECRET_KEY("client_secret", "ulT5KGzUch"),
	CLIENT_TO_OWNER_STATE("state"){
		// 토큰 생성 
		// 상태 토큰으로 CSRF 를 막기위한 일회성 토큰이다. (세션에 저장)
		public String generateState(){
			SecureRandom random = new SecureRandom();
			return new BigInteger(130, random).toString(32);
		}
	},
	CLIENT_TO_OWNER_API_URL("api_url", "http://localhost:8181/oauth20/authorize"),
	CLIENT_TO_OWNER_CALLBACK_URL("callback_url", "http://localhost:8180/oauth20/callback"),
	CLIENT_TO_OWNER_RESPONSE_TYPE("response_type", "code"),
	CLIENT_TO_OWNER_SCORE("scope","read");
	
	private String key = null;
	private String value = null;
	
	private EnumSettings(String key){
		this.key = key;
	}
	
	private EnumSettings(String key, String value){
		this.key = key;
		this.value = value;
	}
	
	public String generateState(){return null;}
	public String getKey(){ return key; }
	public String getValue(){ return value; }
}
