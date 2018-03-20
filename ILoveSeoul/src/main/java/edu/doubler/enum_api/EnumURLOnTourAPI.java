package edu.doubler.enum_api;

public enum EnumURLOnTourAPI {
	
	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * 
	 * [ 국문 관광정보 서비스 API ] 
	 * 
	 * @since 2018 03 17
	 * @author doubleR
	 * @category mashup service : url
	 * @ses https://www.data.go.kr/dataset/15000496/openapi.do
	 * 
	 * - 활용 사례명 : doublerTour
	 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
	SELECT_AREA_BASED_LIST("http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList");
	
	private String url = null;
	
	private EnumURLOnTourAPI(String url){
		this.url = url;
	}
	
	public String getURL(){ return url; }
}
