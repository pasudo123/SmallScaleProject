package edu.doubler.enum_api;

public enum EnumParameterOnTourAPI {
	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * [ 파라미터 ]
	 * - 인증키
	 * - 사용 OS
	 * - 서비스명
	 * - 리턴 타입 
	 * - 한 페이지의 결과수 ( 4개 )
	 * - 정렬순 (조회순 : 이미지 있는 것 위주)
	 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * - 위도 (mapX)
	 * - 경도 (mapY)
	 * - 반경 (3Km)
	 * - 페이지 번호
	 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
	
	// ㅡㅡ 공통 파라미터
	KEY("ServiceKey", "tyrbXoE8jfVmZvX%2FFKgs9vd4zUEwUB30QLmcqddik7nLGFOLSmwZxil%2FQ1Hj9u6q%2BsjZhFJwFOngtvc6EcGrSA%3D%3D"),
	MOBILE_OS("MobileOS", "ETC"),
	MOBILE_APP("MobileApp", "doublerTour"),
	JSON("_type", "json"),
	NUMBER_OF_ROWS("numOfRows", "4"),
	ARRANGE("arrange", "P"),
	
	// ㅡㅡ 특정 파라미터 ( LocationBasedList )
	MAP_X("mapX"),
	MAP_Y("mapY"),
	RADIUS("radius"),
	PAGE_NO("pageNo");
	
	private String key = null;
	private String value = null;
	
	private EnumParameterOnTourAPI(String key){
		this.key = key;
	}
	
	private EnumParameterOnTourAPI(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public void setValue(String value){
		this.value = value;
	}
	
	public String getKey(){return key;}
	public String getValue(){return value;};
}
