package com.doubler.oauth.service;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doubler.oauth.dao.OAuthDao;

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
		
		if(serviceUrl.contains(url))
			return true;
		
		return false;
	}
}
