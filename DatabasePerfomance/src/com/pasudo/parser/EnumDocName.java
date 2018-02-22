package com.pasudo.parser;

public enum EnumDocName {
	
	// -- Column Header
	COLUMN_HEADER_DOC_SEQ("DOC_SEQ"),
	COLUMN_HEADER_DOC_TITLE("TITLE"),
	COLUMN_HEADER_DOC_REG_DT("REG_DT"),
	
	// -- Tagged format START, END
	TAG_COLUMN_START("^[START]"),
	TAG_COLUMN_DOC_SEQ("[DOC_SEQ]"),
	TAG_COLUMN_DOC_TITLE("[TITLE]"),
	TAG_COLUMN_DOC_REG_DT("[REG_DT]"),
	TAG_COLUMN_END("^[END]");
	
	private String name = null;
	
	private EnumDocName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
