package edu.doubler.enum_api;

public enum EnumFullTextGeocodingOnSKT {
	
	URI("uri", "https://api2.sktelecom.com/tmap/geo/fullAddrGeo"),
	VERSION("version", "1"),
	FORMAT("format", "json"),
	FULL_ADDR("fullAddr", null),
	APP_KEY("appKey", "447e11f0-2197-42fc-a399-435f1bb1fa0f");
	
	private String key = null;
	private String value = null;
	
	private EnumFullTextGeocodingOnSKT(){}
	
	private EnumFullTextGeocodingOnSKT(String key, String value){
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
