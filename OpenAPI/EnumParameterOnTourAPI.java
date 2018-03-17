package OpenAPI;

public enum EnumParameterOnTourAPI {
	KEY("ServiceKey", "tyrbXoE8jfVmZvX%2FFKgs9vd4zUEwUB30QLmcqddik7nLGFOLSmwZxil%2FQ1Hj9u6q%2BsjZhFJwFOngtvc6EcGrSA%3D%3D"),
	MOBILE_OS("MobileOS", "ETC"),
	MOBILE_APP("MobileApp", "doublerTour"),
	JSON("&_type", "json");
	
	private String key = null;
	private String value = null;
	
	// 
	private EnumParameterOnTourAPI(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey(){return key;}
	public String getValue(){return value;};
}
