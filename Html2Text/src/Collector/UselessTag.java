package Collector;

public enum UselessTag {
	
	USELESS_SCRIPT("script"),
	USELESS_STYLE("style"),
	USELESS_IFRAME("iframe");
	
	private String tagName;
	
	private UselessTag(String tagName){
		this.tagName = tagName;
	}
	
	public String getTagName(){
		return tagName;
	}
}
