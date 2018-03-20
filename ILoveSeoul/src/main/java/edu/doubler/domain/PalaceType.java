package edu.doubler.domain;

public class PalaceType {
	private String korName;		// 한국어
	private String engName;		// 영어
	private String address;		// 주소
	private String mapX;		// 위도 (가로)
	private String mapY;		// 경도 (세로)
	
	// -- getter() & setter()
	public String getKorName() {
		return korName;
	}

	public void setKorName(String korName) {
		this.korName = korName;
	}

	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMapX() {
		return mapX;
	}

	public void setMapX(String mapX) {
		this.mapX = mapX;
	}

	public String getMapY() {
		return mapY;
	}

	public void setMapY(String mapY) {
		this.mapY = mapY;
	}
}
