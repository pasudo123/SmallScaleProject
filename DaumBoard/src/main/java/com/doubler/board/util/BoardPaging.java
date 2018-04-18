package com.doubler.board.util;

import java.util.HashMap;

public class BoardPaging {
	static final int PRINT_CONTENT_COUNT = 5;	// 한 페이지에 보여줄 글의 수
	static final int PRINT_PAGE_COUNT = 3;		// 한 화면당 페이지의 수
	
	private static Integer contentCount = null;	// 전체 게시글의 수
	
	private Integer currentPage = null;		// 현재 페이지
	private Integer needPages = null;		// 필요 페이지 수
	
	private Integer currentBlock = null;	// 현재 페이지 블록
	private Integer needBlocks = null;		// 필요 페이지 블록의 수

	private Integer firstPageNum = null;	// 첫번째 페이지
	private Integer lastPageNum = null;		// 마지막 페이지
	
	private Integer firstBlock = null;		// 첫번째 페이지 블록
	private Integer lastBlock = null;		// 마지막 페이지 블록

	private HashMap<String, Integer> pagingInfoMap = new HashMap<String, Integer>();
	
	// 생성자
	public BoardPaging(){
		this.setCurrentPage(1);
		this.setCurrentBlock(1);
		this.setFirstPageNum(1);
		this.setFirstBlock(1);
	}
	
	// 게시글 전체 개수 세터
	public void setPagesCount(int contentCount){
		BoardPaging.contentCount = contentCount;
	}
	
	// 현재 페이지 세터
	public void setCurrentPage(int num){
		this.currentPage = num;
	}
	
	// 현재 페이지 블록 세터
	public void setCurrentBlock(int block){
		this.currentBlock = block;
	}
	
	// 첫번째 페이지 세터
	public void setFirstPageNum(int num){
		this.firstPageNum = num;
	}
	
	// 첫번째 페이지 블록 세터
	public void setFirstBlock(int block){
		this.firstBlock = block;
	}
	
	public Integer getContentCount(){
		return contentCount;
	}
	
	public Integer getCurrentPage(){
		return currentPage;
	}
	
	public Integer getFirstPageNum(){
		return firstPageNum;
	}

	public Integer getCurrentBlock(){
		return currentBlock;
	}
	
	public Integer getFirstBlock(){
		return firstBlock;
	}
	
	
	// 마지막 페이지 번호
	public Integer getLastPageNum(){
		lastPageNum = 0;
		
		if(contentCount % PRINT_CONTENT_COUNT != 0)
			lastPageNum += 1;
		lastPageNum += contentCount / PRINT_CONTENT_COUNT;
		
		return lastPageNum;
	}
	
	
	// 필요 페이지 개수 리턴
	public Integer getNeedPages(){
		needPages = 0;
		
		if(contentCount % PRINT_CONTENT_COUNT != 0)
			needPages += 1;
		needPages += contentCount / PRINT_CONTENT_COUNT;
		
		return needPages;
	}
	
	
	// 필요 페이지 블록 리턴
	public Integer getNeedBlocks(){
		needBlocks = 0;
		
		if(needPages % PRINT_PAGE_COUNT != 0)
			needBlocks += 1;
		needBlocks += needPages / PRINT_PAGE_COUNT;
		
		return needBlocks;
	}
	
	
	// 마지막 페이지 블록 리턴
	public Integer getLastBlock(){
		lastBlock = 0;
		
		if(contentCount % (PRINT_CONTENT_COUNT * PRINT_PAGE_COUNT) != 0)
			lastBlock += 1;
		lastBlock += contentCount / (PRINT_CONTENT_COUNT * PRINT_PAGE_COUNT);
		
		return lastBlock;
	}
	
	/**
	 *
	 * 전체개수를 파악하지 말고 부분부분 조회구문을 이용한다. (이후 리팩토링 작업)
	 * 
	 * static final int PRINT_CONTENT_COUNT = 5;	// 한 페이지에 보여줄 글의 수
	 * static final int PRINT_PAGE_COUNT = 3;		// 한 화면당 페이지의 수
	 * 
	 ***/
	
	// 페이징 관련 정보 전체 리턴
	public HashMap<String, Integer> getPagingInformation(){
		pagingInfoMap.put("PRINT_CONTENT_COUNT", PRINT_CONTENT_COUNT);	
		pagingInfoMap.put("PRINT_PAGE_COUNT", PRINT_PAGE_COUNT);	
		
		pagingInfoMap.put("contentCount", getContentCount());	// 전체 게시글
		
		pagingInfoMap.put("currentPage", getCurrentPage());		// 현재 페이지
		pagingInfoMap.put("firstPage", getFirstPageNum());		// 첫번째 페이지 번호
		pagingInfoMap.put("lastPage", getLastPageNum());		// 마지막 페이지 번호
		pagingInfoMap.put("needPages", getNeedPages());			// 필요 페이지
		
		pagingInfoMap.put("currentBlock", getCurrentBlock());	// 현재 블록
		pagingInfoMap.put("firstBlock", getFirstBlock());		// 첫번째 블록
		pagingInfoMap.put("lastBlock", getLastBlock());			// 마지막 블록
		pagingInfoMap.put("needBlocks", getNeedBlocks());		// 필요 블록
		
		return pagingInfoMap;
	}
}
