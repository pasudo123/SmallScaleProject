package com.doubler;

public enum EnumViewPath {
	
	/**
	 * 컨트롤러에서 리턴하는 [ view path ]
	 * **/
	
	LOGIN_VIEW("login/login_view"),
	LOGIN_ERROR_VIEW("login/login_error"),
	OAUTH_GRANT_VIEW("oauth/oauth_grant"),
	BOARD_LIST_VIEW("board/board_list"),
	BOARD_WRITE_VIEW("board/board_write"),
	BOARD_CONTENT_VIEW("board/board_content");
	
	private String path = null;
	
	private EnumViewPath(){}
	private EnumViewPath(String path){
		this.path = path;
	}
	
	public String getPath(){ return path; }
}
