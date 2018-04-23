package com.doubler.board.dao;

import java.util.List;

import com.doubler.board.util.BoardPagingNumber;

public interface BoardDAO {
	
	// 게시글 전체 카운트 가져오는 기능
	public int getContentCount();
	
	// 게시판의 게시글 전체 다 가져오는 기능
	public List<BoardContentDTO> getBoardList();
	
	// 게시판의 게시글 가져오는 기능
	public List<BoardContentDTO> getBoardList(BoardPagingNumber boardPagingNumber);
	
	// 게시판에서 글 작성하는 기능
	public void writeContent(int contentNum, String contentTitle, String contentWriter, String contentDetail);
	
	// 게시판에서 특정 글 보는 기능
	public BoardContentDTO getContent(int contentNum);
	
	// 조회수 업데이트
	public void updateHit(int contentNum);

	// 게시판에서 특정 글 삭제 기능
	public void deleteContent(int contentNum);
	
	// 게시판 글 삭제 이후 글 번호 업데이트
	public void updateContentNum(int contentNum);
	
	// 게시판 글 수정 기능
	public void updateContentDetail(int contentNum, String contentTitle, String contentDetail);
}
