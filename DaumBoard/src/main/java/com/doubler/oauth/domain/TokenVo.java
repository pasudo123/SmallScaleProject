package com.doubler.oauth.domain;

public class TokenVo{
	private String accessToken;
	private String refreshToken;
	private String scope;			// 사용 범위 ('+' 로 split)
	private long expiresIn;			// 타이머
	private long expiresTime;		// 만료시간 (현재날짜 + 타이머) 와 비교한다.
	private String tokenType = "bearer";
	
	public String getAccessToken() {
		return accessToken;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}
	
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	public long getExpiresIn() {
		return expiresIn;
	}
	
	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}
	
	public String getTokenType() {
		return tokenType;
	}
	
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	
	public long getExpitesTime() {
		return expiresTime;
	}

	public void setExpitesTime(long expitesTime) {
		this.expiresTime = expitesTime;
	}
	
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String toJSON(){
		return String.format("{\"access_token\":\"%s\",\"refresh_token\":\"%s\",\"token_type\":\"%s\",\"expires_in\":\"%s\"}", 
				getAccessToken(), getRefreshToken(), getTokenType(), getExpiresIn());
	}
}
