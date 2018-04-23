package com.doubler.board.dao;

public class BoardContentDTO {
	private String contentDate;		// 작성날짜
	private int contentNum;			// 작성번호
	private String contentTitle;	// 제목
	private String contentWriter;	// 작성자
	private String contentDetail;	// 내용
	private int contentHit;			// 조회수
	
	public BoardContentDTO(){}
	
	// 생성자.
	public BoardContentDTO(String contentDate, int contentNum, String contentTitle, String contentWriter, String contentDetail, int contentHit){
		this.contentDate = contentDate;
		this.contentNum = contentNum;
		this.contentTitle = contentTitle;
		this.contentWriter = contentWriter;
		this.contentDetail = contentDetail;
		this.contentHit = contentHit;
	}

	// -- getter & setter
	public String getContentDate() {
		return contentDate;
	}

	public void setContentDate(String contentDate) {
		this.contentDate = contentDate;
	}

	public int getContentNum() {
		return contentNum;
	}

	public void setContentNum(int contentNum) {
		this.contentNum = contentNum;
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

	public String getContentWriter() {
		return contentWriter;
	}

	public void setContentWriter(String contentWriter) {
		this.contentWriter = contentWriter;
	}

	public String getContentDetail() {
		return contentDetail;
	}

	public void setContentDetail(String contentDetail) {
		this.contentDetail = contentDetail;
	}

	public int getContentHit() {
		return contentHit;
	}

	public void setContentHit(int contentHit) {
		this.contentHit = contentHit;
	}
	
	
}
