package edu.doubler.multi_crawler.domain;

public enum EnumNaverSection {
	SECTION_POLITICS("sectionId", 100, "SECTION_POLITICS"),
	SECTION_ECONOMY("sectionId", 101, "SECTION_ECONOMY"),
	SECTION_SOCIETY("sectionId", 102, "SECTION_SOCIETY"),
	SECTION_LIFE_CURTURE("sectionId", 103, "SECTION_LIFE_CURTURE"),
	SECTION_WORLD("sectionId", 104, "SECTION_WORLD"),
	SECTION_IT_SCIENCE("sectionId", 105, "SECTION_IT_SCIENCE");
	
	private String key;
	private int sectionId;
	private String name;
	
	private EnumNaverSection(String key, int sectionId, String name){
		this.key = key;
		this.sectionId = sectionId;
		this.name = name;
	}
	
	public String getKey(){
		return key;
	}
	
	public int getSectionId(){
		return sectionId;
	}
	
	public String getName(){
		return name;
	}
}
