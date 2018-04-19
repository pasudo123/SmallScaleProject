package edu.doubler.client.util;

public enum EnumClient2OAuth {
	CLIENT_ID("client_id", "KJgReXGkt5_K3KHbtiWx"),
	CLIENT_SECRET_KEY("client_secret", "ulT5KGzUch"),
	CLIENT_TO_OAUTH_CODE("code"),
	CLIENT_TO_OWNER_SCOPE("scope","read"),
	CLIENT_TO_OAUTH_API_URL("api_url", "http://localhost:8181/oauth20/token"),
	CLIENT_TO_OAUTH_CALLBACK_URL("callback_url", "http://localhost:8180/oauth20/token/callback"),
	CLIENT_TO_OAUTH_RESPONSE_TYPE("response_type", "token");
	
	private String key = null;
	private String value = null;
	
	private EnumClient2OAuth(String key){
		this.key = key;
	}
	
	private EnumClient2OAuth(String key, String value){
		this.key = key;
		this.value = value;
	}
	
	public void setValue(String value){
		this.value = value;
	}
	
	public String getKey(){ return key; }
	public String getValue(){ return value; }
}
