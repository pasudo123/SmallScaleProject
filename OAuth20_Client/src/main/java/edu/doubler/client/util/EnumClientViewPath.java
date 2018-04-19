package edu.doubler.client.util;

public enum EnumClientViewPath {
	
	PROTOCOL("http"),
	DOMAIN("localhost"),
	PORT("8180"),
	CLIENT_OAUTH_URL("/oauth20"){
		private String url = settingURL(this);
		public String getURL(){return url;}
	};
	
	private String value = null;
	
	private EnumClientViewPath(){}
	private EnumClientViewPath(String value){
		this.value = value;
	}
	
	private static String settingURL(EnumClientViewPath path){
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
