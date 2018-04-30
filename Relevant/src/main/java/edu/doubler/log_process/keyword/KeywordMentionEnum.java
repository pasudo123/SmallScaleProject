package edu.doubler.log_process.keyword;

public enum KeywordMentionEnum {
	MENTION_ON_MONTH("MONTH", 30),
	MENTION_ON_WEEKLY("WEEKLY", 7),
	MENTION_ON_DAILY("DAILY", 1);
	
	private String standard;
	private int days;
	
	private KeywordMentionEnum(String standard, int days){
		this.standard = standard;
		this.days = days;
	}
	
	public String getStandard(){
		return standard;
	}
	
	public int getDays(){
		return days;
	}
}
