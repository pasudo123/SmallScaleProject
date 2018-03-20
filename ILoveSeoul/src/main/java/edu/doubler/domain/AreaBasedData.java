package edu.doubler.domain;

public class AreaBasedData {
	
	private String title = null;			// 제목
	private String addr = null;				// 주소
	private String tel = null;				// 전화번호
	private String imageURL = null;			// 대표 이미지
	private String mapX = null;				// 위도 (가로)
	private String mapY = null;				// 경도 (세로)
	private String readCount = null;		// 조회수
	private String contentTypeId = null;	// 관광타입
	private String sigunguCode = null;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
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
	public String getReadCount() {
		return readCount;
	}
	public void setReadCount(String readCount) {
		this.readCount = readCount;
	}
	public String getContentTypeId() {
		return contentTypeId;
	}
	public void setContentTypeId(String contentTypeId) {
		this.contentTypeId = contentTypeId;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getSigunguCode() {
		return sigunguCode;
	}
	public void setSigunguCode(String sigunguCode) {
		this.sigunguCode = sigunguCode;
	}
	@Override
	public String toString() {
		return "AreaBasedData [title=" + title + ", addr=" + addr + ", tel=" + tel + ", imageURL=" + imageURL
				+ ", mapX=" + mapX + ", mapY=" + mapY + ", readCount=" + readCount + ", contentTypeId=" + contentTypeId
				+ ", sigunguCode=" + sigunguCode + "]";
	}
}
