package edu.doubler.relevant.keyword;

import java.util.regex.Pattern;

public enum KeywordPatternEnum {
	
	PATTERN_NUMBER("(^[0-9]*$)"),
	PATTERN_ALPHABET("(^[a-zA-Z]*$)"),
	PATTERN_HANGUL1("(^[가-힣]?$)"),
	PATTERN_HANGUL2("(^[ㄱ-ㅎ]*$)"),
	PATTERN_HANGUL3("(^[ㅏ-ㅣ]*$)");
	
	private Pattern pattern;
	
	private KeywordPatternEnum(String s){
		pattern = Pattern.compile(s);
	}
	
	public Pattern getPattern(){
		return pattern;
	}
}
