package com.doubler.oauth.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

import com.doubler.oauth.domain.TokenVo;

public class TokenGenerator {
	
	public static final int MINUTE = 15;
	public static final int SECOND = 60;
	
	public TokenVo getToken(){
		
		/**
		 * 액세스토큰
		 * 리프레쉬토큰
		 * 만료시간
		 * 토큰타입
		 * **/
		
		String accessToken = generateToken();
		String refreshToken = generateToken();
		String tokenType = "bearer";
		long expiresIn =  MINUTE * SECOND;
		long expiresTime = generateExpiresIn();
		
		TokenVo tokenVo = new TokenVo();
		tokenVo.setAccessToken(accessToken);
		tokenVo.setRefreshToken(refreshToken);
		tokenVo.setExpiresIn(expiresIn);
		tokenVo.setExpitesTime(expiresTime);
		tokenVo.setTokenType(tokenType);
		
		return tokenVo;
	}
	
	public String getRefreshToken(){
		return generateToken();
	}
	
	private String generateToken(){
		SecureRandom random = new SecureRandom();
		return new BigInteger(250, random).toString(32);
	}
	
	private long generateExpiresIn(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, MINUTE);
		Date expirationDate = calendar.getTime();
		
		return expirationDate.getTime();
	}
}
