package com.doubler;

public enum EnumRedirectPath {
	
	PROTOCOL("http"),
	DOMAIN("localhost"),
	PORT("8181"),
	LOGIN_URL("/login"){
		private String url = settingURL(this);
		public String getURL(){return url;}
	},
	
	LOGIN_ERROR_URL("/login_error"){
		private String url = settingURL(this);
		public String getURL(){return url;}
	},
	
	USER_OAUTH_GRANT_URL("/oauth20/authorize/grant"){
		private String url = settingURL(this);
		public String getURL(){return url;}
	},
	
	BOARD_LIST_URL("/boardList"){
		private String url = settingURL(this);
		public String getURL(){return url;}
	};
	
	
	private String value = null;
	
	private EnumRedirectPath(){}
	private EnumRedirectPath(String value){
		this.value = value;
	}
	
	private static String settingURL(EnumRedirectPath path){
		String url = PROTOCOL.getValue() + "://" + 
				   	 DOMAIN.getValue() + ":" + 
				   	 PORT.getValue() + path.getValue();
		return url;
	}
	
	public String getURL(){return null;}
	private String getValue(){
		return value;
	}
}	
