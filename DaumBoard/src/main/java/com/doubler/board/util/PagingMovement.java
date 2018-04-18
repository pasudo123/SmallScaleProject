package com.doubler.board.util;

public interface PagingMovement {
	
	//-- 페이지 이동 관련
	public BoardPaging chooseMovement(String paging);		// 이동 결정
	public BoardPaging moveBeginBlock();					// 처음 페이지 블록
	public BoardPaging movePrevBlock();						// 이전 페이지 블록
	public BoardPaging moveNextBlock();						// 이후 페이지 블록
	public BoardPaging moveEndBlock();						// 끝 페이지 블록
	public BoardPaging movePage(int pagingNum);				// 단순 페이지 이동
}
