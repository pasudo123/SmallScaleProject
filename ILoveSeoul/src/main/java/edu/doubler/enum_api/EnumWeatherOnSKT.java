package edu.doubler.enum_api;

public enum EnumWeatherOnSKT {
	
	WEATHER_URL,
	APP_KEY("appKey", "447e11f0-2197-42fc-a399-435f1bb1fa0f"),
	HEADER_ACCEPT("Accept", "application/json"),
	HEADER_CONTENT_TYPE("Content-Type", "application/json; charset=UTF8");
	
	private String url = "https://api2.sktelecom.com/weather/current/hourly";
	private String key = null;
	private String value = null;
	
	private EnumWeatherOnSKT(){}

	private EnumWeatherOnSKT(String key, String value){
		this.key = key;
		this.value = value;
	}
	
	public String getURL(){
		return url;
	}
	
	public String getKey() {
		return key;
	}
	public String getValue() {
		return value;
	}
}
