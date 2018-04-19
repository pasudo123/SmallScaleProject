package com.doubler.oauth.dao;

public interface OAuthDao {
	public String getServiceURL(String clientId);
	
	public int getClientInfo(String clientId, String clientSecret);
}
