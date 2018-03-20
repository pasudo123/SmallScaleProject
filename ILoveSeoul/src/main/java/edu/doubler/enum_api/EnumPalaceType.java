package edu.doubler.enum_api;

public enum EnumPalaceType {
	
	GYEONGBOK_GUNG("경복궁", "gyeongbokgung", "서울 종로구 세종로 1-91", "126.9769104248531", "37.577277219855624"),
	CHANGDEOK_GUNG("창덕궁", "changdeokgung", "서울 종로구 율곡로 99 (우)03072", "126.9918586658844", "37.57296523489999"),
	CHANGGYEONG_GUNG("창경궁", "changgyeonggung", "서울 종로구 창경궁로 185 (우)03072", "126.9938415437186", "37.57762529910132"),
	DEOKSU_GUNG("덕수궁", "deoksugung", "서울 중구 세종대로 99 (우)04519", "126.9739845076771", "37.566169143328715");
	
	private String korName;		// 한국어
	private String engName;		// 영어
	private String address;		// 주소
	private String mapX;		// 위도 (가로)
	private String mapY;		// 경도 (세로)
	
	private EnumPalaceType(String korName, String engName, String address, String mapX, String mapY) {
		this.korName = korName;
		this.engName = engName;
		this.address = address;
		this.mapX = mapX;
		this.mapY = mapY;
	}

	// getter()
	public String getKorName() {
		return korName;
	}

	public String getEngName() {
		return engName;
	}

	public String getAddress() {
		return address;
	}

	public String getMapX() {
		return mapX;
	}

	public String getMapY() {
		return mapY;
	}
}
