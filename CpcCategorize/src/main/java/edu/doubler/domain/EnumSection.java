package edu.doubler.domain;

public enum EnumSection {
	A("A"),
	B("B"),
	C("C"),
	D("D"),
	E("E"),
	F("F"),
	G("G"),
	H("H"),
	Y("Y");
	
	private String sectionName;
	
	private EnumSection(String sectionName){
		this.sectionName = sectionName;
	}
	
	public String getSectionName(){
		return sectionName;
	}
}
