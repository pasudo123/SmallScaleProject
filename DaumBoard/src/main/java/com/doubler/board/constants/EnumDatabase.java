package com.doubler.board.constants;

public enum EnumDatabase {
	NAME_SPACE("BoardMapper"),
	QUERY_ON_GET_CONTENT_COUNT("getContentCount");
	
	private String value;
	
	private EnumDatabase(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
	
	@Override
	public String toString(){
		return this.value;
	}
}
