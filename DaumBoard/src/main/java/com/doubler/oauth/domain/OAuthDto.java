package com.doubler.oauth.domain;

public class OAuthDto {
	private String clientId;
	private String clientSecretKey;
	private String serviceUrl;
	private String serviceScope;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecretKey() {
		return clientSecretKey;
	}

	public void setClientSecretKey(String clientSecretKey) {
		this.clientSecretKey = clientSecretKey;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getServiceScope() {
		return serviceScope;
	}

	public void setServiceScope(String serviceScope) {
		this.serviceScope = serviceScope;
	}
}
