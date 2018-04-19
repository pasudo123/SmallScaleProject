package com.doubler.oauth.service;

import com.doubler.oauth.domain.TokenVo;

public interface OAuthService {
	
	public String generateCode();
	
	public TokenVo generateToken();
	
	public boolean checkServiceURL(String serviceUrl, String clientId);

	public boolean checkClientIdAndClientSecret(String clientId, String clientSecret);
}
