package OpenAPI;

public enum EnumContentTypeId {
	
	TOURISM(12),				// 관광지
	CULTURAL_FACILITIES(14),	// 문화시설
	FESTIVAL(15),				// 축제/공연/행사
	LEPORTS(28),				// 레포츠
	LODGMENT(32),				// 숙박
	SHOPPING(38),				// 쇼핑
	FOOD(39);					// 음식
	
	private int id;
	
	private EnumContentTypeId(int id){
		this.id = id;
	}
	
	public int getId(){return id;}
}
