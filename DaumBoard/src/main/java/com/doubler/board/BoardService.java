package com.doubler.board;

import java.util.HashMap;
import java.util.List;

import com.doubler.board.dto.BoardContentDTO;

public interface BoardService {
	
	// 게시글 전체 카운트 가져오는 기능
	public int getContentCount();
		
	// 게시판 읽기
	public List<BoardContentDTO> getBoardList(HashMap<String, Integer> map, int contentCount);
	
	// 게시글 작성
	public void writeContent(int contentNum, String contentTitle, String contentWriter, String contentDetail);
	
	// 게시글 보기
	public BoardContentDTO getContent(int contentNum);
	
	// 조회수 업데이트
	public void updateHit(int contentNum);
	
	// 게시글 삭제
	public void deleteContent(int contentNum);
	
	// 게시글 삭제 ㅡ> 게시글 번호 업데이트
	public void updateContentNum(int contentNum);
	
	// 게시글 수정
	public void updateContentDetail(int contentNum, String contentTitle, String contentDetail);
}
