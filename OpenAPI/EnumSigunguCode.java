package OpenAPI;

public enum EnumSigunguCode {
	GANGNAM_GU(1),			// 강남구
	GANGDONG_GU(2),			// 강동구
	GANGBUK_GU(3),			// 강북구
	GANGSEO_GU(4),			// 강서구
	GWANAK_GU(5),			// 관악구
	GWANGJIN_GU(6),			// 광진구
	GURO_GU(7),				// 구로구
	KUMCHEON_GU(8),			// 금천구
	NOWON_GU(9),			// 노원구
	DOBONG_GU(10),			// 도봉구
	DONGDAEMUN_GU(11),		// 동대문구
	DONGJAK_GU(12),			// 동작구
	MAPO_GU(13),			// 마포구
	SEODAEMUN_GU(14),		// 서대문구
	SEOCHO_GU(15),			// 서초구
	SEONGDONG_GU(16),		// 성동구
	SEONGBUK_GU(17),		// 성북구
	SONGPA_GU(18),			// 송파구
	YANGCHEON_GU(19),		// 양천구
	YEONGDEUNGPO_GU(20),	// 영등포구
	YONGSAN_GU(21),			// 용산구
	EUNPYEONG_GU(22),		// 은평구
	CHONGNO_GU(23),			// 종로구
	JUNG_GU(24),			// 중구
	JUNGRANG_GU(25);		// 중랑구
	
	private int code;
	private EnumSigunguCode(int code){
		this.code = code;
	}
	public int getCode(){return code;}
}
