package com.doubler.oauth.domain;

public class CodeVo {
	private String code;

	public CodeVo(String code){
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}