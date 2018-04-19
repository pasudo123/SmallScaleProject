package com.doubler.oauth.service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import com.doubler.oauth.dao.OAuthDao;
import com.doubler.oauth.domain.TokenVo;

@Service
public class OAuthServiceImpl implements OAuthService{
	
	@Autowired
	OAuthDao oauthDao;
	
	// 코드 생성 ( 액세스 토큰을 얻기 위한 일회성 : 유효시간 10분 ) 
	public String generateCode(){
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}
	
	public boolean checkServiceURL(String serviceUrl, String clientId){
		String url = oauthDao.getServiceURL(clientId);
		
		// 서비스 URL 인증
		if(serviceUrl.contains(url))
			return true;
		
		return false;
	}
	
	public boolean checkClientIdAndClientSecret(String clientId, String clientSecret){
		int count = oauthDao.getClientInfo(clientId, clientSecret);
		
		// 존재
		if(count == 1)
			return true;
		
		return false;
	}
	
	public TokenVo generateToken(){
		
		/**
		 * 반환 내용
		 * - access_token
		 * - refresh_token
		 * - token_type : bearer
		 * - expires_in : 3600
		 * 
		 * 이 JSON 형태로 이루어져있다.
		 * **/
		
		TokenGenerator tokenGenerator = new TokenGenerator();
		TokenVo tokenVo = tokenGenerator.getToken();
//		System.out.println(tokenVo.toJSON());
		return tokenVo;
	}

	public static void main(String[]args){
		OAuthServiceImpl oa = new OAuthServiceImpl();
		
		// 1/1000초 단위 >> 1000 Value >> 1초
		// 1분 > 60초 > 3600 Value
		// 
		oa.generateToken();
	}
}
