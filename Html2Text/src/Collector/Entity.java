package Collector;

public enum Entity {
	ENTITY_NBSP("&nbsp;", " "),
	ENTITY_AMP("&amp;", "&"),
	ENTITY_QUOT("&quot;", "\""),
	ENTITY_LT("&lt;", "<"),
	ENTITY_GT("&gt;", ">");
	
	private String origin;
	private String convert;
	
	private Entity(String origin, String convert){
		this.origin = origin;
		this.convert = convert;
	}
	
	public String getOrigin(){
		return origin;
	}
	
	public String getConvert(){
		return convert;
	}
}
