package edu.doubler.log_process.domain;

public class KeywordMentionMapper {
	private String keyword;
	private int mentionCount;
	
	public KeywordMentionMapper(String keyword, int mentionCount){
		this.keyword = keyword;
		this.mentionCount = mentionCount;
	}
	
	public String getKeyword(){
		return keyword;
	}
	
	public int getMentionCount(){
		return mentionCount;
	}
}
