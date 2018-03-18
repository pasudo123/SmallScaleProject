package OpenAPI;

public enum EnumParameterOnTourAPI {
	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * [ 파라미터 ]
	 * - 인증키
	 * - 사용 OS
	 * - 서비스명
	 * - 리턴 타입 
	 * - 한 페이지의 결과수
	 * - 페이지 번호
	 * - 정렬순 (조회순 : 이미지 있는 것 위주)
	 * - 지역코드 (서울, 고정값)
	 * - 시군구 코드
	 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
	KEY("ServiceKey", "tyrbXoE8jfVmZvX%2FFKgs9vd4zUEwUB30QLmcqddik7nLGFOLSmwZxil%2FQ1Hj9u6q%2BsjZhFJwFOngtvc6EcGrSA%3D%3D"),
	MOBILE_OS("MobileOS", "ETC"),
	MOBILE_APP("MobileApp", "doublerTour"),
	JSON("_type", "json"),
	NUMBER_OF_ROWS("numOfRows", "10"),
	PAGE_NO("pageNo"),
	ARRANGE("arrange", "P"),
	AREA_CODE("areaCode", "1"),
	CONTENT_TYPE_ID("contentTypeId");
	
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
