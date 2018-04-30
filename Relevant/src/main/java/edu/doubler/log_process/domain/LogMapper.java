package edu.doubler.log_process.domain;

public class LogMapper {
	
	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * 
	 * 	 [ 로그 데이터에 매핑되는 객체 ]
 	 * 
	 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
	
	private String date;				// 날짜
	private String platform;			// 유입 플랫폼
	private String searchCategory;		// tot는 최초 멜론 화면에서 검색한 경우, 그리고 artist, song, album는 상단 탭을 클릭하고 검색한 경우
	private String highLevelCategory;	// 대분류 카테고리
	private String lowLevelCategory;	// 대분류 내 소분류 카테고리 (리스트 내용들 : 곡명, 앨범명, 가수명 등등)
	private String searchKeyword;		// 검색 키워드
	private String categoryId;			// 대분류 ID
	private String memberId;			// 멤버 ID
	
	public LogMapper(String date, String platform, String searchCategory, String highLevelCategory, String lowLevelCategory, String searchKeyword, String categoryId, String memberId){
		this.date = date;
		this.platform = platform;
		this.searchCategory = searchCategory;
		this.highLevelCategory = highLevelCategory;
		this.lowLevelCategory = lowLevelCategory;
		this.searchKeyword = searchKeyword;
		this.categoryId = categoryId;
		this.memberId = memberId;
	}

	public String getDate() {
		return date;
	}

	public String getPlatform() {
		return platform;
	}

	public String getSearchCategory() {
		return searchCategory;
	}

	public String getHighLevelCategory() {
		return highLevelCategory;
	}

	public String getLowLevelCategory() {
		return lowLevelCategory;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public String getMemberId() {
		return memberId;
	}
}
