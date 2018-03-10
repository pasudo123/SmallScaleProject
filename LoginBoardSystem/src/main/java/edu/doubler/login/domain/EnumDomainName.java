package edu.doubler.login.domain;

public enum EnumDomainName {
	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * 
	 * [ apple ]
	 * - Port Name : HTTP/1.1
	 * - Port Number : 8181
	 * 
	 * [ banana ]
	 * - Port Name : HTTP/1.1
	 * - Port Number : 8180
	 * 
	 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
	
	// ENUM_TYPE
	APPLE("login_view/login_form_apple"),
	BANANA("login_view/login_form_banana");
	
	private String viewPath;
	
	private EnumDomainName(String viewPath){
		this.viewPath = viewPath;
	}
	
	public String getViewPath(){
		return viewPath;
	}
}
