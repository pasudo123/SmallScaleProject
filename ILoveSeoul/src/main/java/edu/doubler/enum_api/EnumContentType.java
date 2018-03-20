package edu.doubler.enum_api;

public enum EnumContentType {
	
	TOURISM("contentTypeId", 12),				// 관광지
	CULTURAL_FACILITIES("contentTypeId", 14),	// 문화시설
	FESTIVAL("contentTypeId", 15),				// 축제/공연/행사
	LEPORTS("contentTypeId", 28),				// 레포츠
	LODGMENT("contentTypeId", 32),				// 숙박
	SHOPPING("contentTypeId", 38),				// 쇼핑
	FOOD("contentTypeId", 39);					// 음식
	
	private String key;
	private int id;
	
	private EnumContentType(String key, int id){
		this.id = id;
	}
	
	public String getKey(){return key;}
	public int getId(){return id;}
}
