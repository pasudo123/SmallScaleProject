package com.doubler;

public enum EnumScope {
	SCOPE_READ("read"),
	SCOPE_WRITE("write"),
	SCOPE_DELETE("delete");
	
	private String scope;
	
	private EnumScope(String scope){
		this.scope = scope;
	}
	
	public String getScope(){
		return scope;
	}
}
