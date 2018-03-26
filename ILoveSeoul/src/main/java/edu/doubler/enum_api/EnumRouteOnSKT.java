package edu.doubler.enum_api;

public enum EnumRouteOnSKT {
	
	URI("uri", "https://api2.sktelecom.com/tmap/routes"),
	VERSION("version", "1"),
	
	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * 
	 * (1) TOLLGATE_FARE_OPTION : 요금 가중치 정보 / 로직 판단
	 * (2) ROAD_TYPE : 출발 지점의 도로 타입 / 가까운 도로
	 * (3) DIRECTION_OPTION : 출발 지점의 주행 방향 / 주행 방향 우선
	 * (4) 도착지 위도, 경도
	 * (5) 출발지 위도, 경도
	 * (6) POST 요청 시 앱 키 헤더에 포함
	 * 
	 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
	
	TOLLGATE_FARE_OPTION("tollgateFareOption", "16"),
	ROAD_TYPE("roadType", "32"),
	DIRECTION_OPTION("directionOption", "1"),
	END_X("endX"),
	END_Y("endY"),
	START_X("startX"),
	START_Y("startY"),
	HEADER("appKey", "447e11f0-2197-42fc-a399-435f1bb1fa0f");
	
	private String uri = null;
	private String key = null;
	private String value = null;
	
	private EnumRouteOnSKT(String key){
		this.key = key;
	}
	
	private EnumRouteOnSKT(String key, String value){
		this.key = key;
		this.value = value;
	}

	// -- getter() & setter()
	public String getURI() {
		return uri;
	}

	public void setURI(String uri) {
		this.uri = uri;
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
