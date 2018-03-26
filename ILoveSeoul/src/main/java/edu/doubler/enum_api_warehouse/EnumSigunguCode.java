package edu.doubler.enum_api_warehouse;

public enum EnumSigunguCode {
	GANGNAM_GU("강남구", "sigunguCode", 1),			// 강남구
	GANGDONG_GU("강동구", "sigunguCode", 2),			// 강동구
	GANGBUK_GU("강북구", "sigunguCode", 3),			// 강북구
	GANGSEO_GU("강서구", "sigunguCode", 4),			// 강서구
	GWANAK_GU("관악구", "sigunguCode", 5),			// 관악구
	GWANGJIN_GU("광진구", "sigunguCode", 6),			// 광진구
	GURO_GU("구로구", "sigunguCode", 7),				// 구로구
	KUMCHEON_GU("금천구", "sigunguCode", 8),			// 금천구
	NOWON_GU("노원구", "sigunguCode", 9),				// 노원구
	DOBONG_GU("도봉구", "sigunguCode", 10),			// 도봉구
	DONGDAEMUN_GU("동대문구", "sigunguCode", 11),		// 동대문구
	DONGJAK_GU("동작구", "sigunguCode", 12),			// 동작구
	MAPO_GU("마포구", "sigunguCode", 13),				// 마포구
	SEODAEMUN_GU("서대문구", "sigunguCode", 14),		// 서대문구
	SEOCHO_GU("서초구", "sigunguCode", 15),			// 서초구
	SEONGDONG_GU("성동구", "sigunguCode", 16),		// 성동구
	SEONGBUK_GU("성북구", "sigunguCode", 17),			// 성북구
	SONGPA_GU("송파구", "sigunguCode", 18),			// 송파구
	YANGCHEON_GU("양천구", "sigunguCode", 19),		// 양천구
	YEONGDEUNGPO_GU("영등포구", "sigunguCode", 20),	// 영등포구
	YONGSAN_GU("용산구", "sigunguCode", 21),			// 용산구
	EUNPYEONG_GU("은평구", "sigunguCode", 22),		// 은평구
	CHONGNO_GU("종로구", "sigunguCode", 23),			// 종로구
	JUNG_GU("중구", "sigunguCode", 24),				// 중구
	JUNGRANG_GU("중랑구", "sigunguCode", 25);			// 중랑구
	
	private String name;
	private String key;
	private int code;
	
	private EnumSigunguCode(String name, String key, int code){
		this.name = name;
		this.key = key;
		this.code = code;
	}
	
	public String getName(){return name;};
	public String getKey(){return key;}
	public int getCode(){return code;}
}
