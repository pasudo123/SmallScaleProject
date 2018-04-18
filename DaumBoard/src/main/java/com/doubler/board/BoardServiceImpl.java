package com.doubler.board;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.doubler.board.dto.BoardContentDTO;
import com.doubler.board.util.BoardPagingNumber;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardDAO boardDao;
	private static BoardPagingNumber boardPagingNumber = new BoardPagingNumber();

	
	@Override
	public int getContentCount() {
		return boardDao.getContentCount();
	}
	
	@Override
	public List<BoardContentDTO> getBoardList(HashMap<String, Integer> map, int contentCount) {
		// 계산식.
		int currentPage = map.get("currentPage");
		int printContentCount = map.get("PRINT_CONTENT_COUNT");
		
		// start & end [큰번호가 우선으로]
		int startNum = (contentCount - printContentCount * (currentPage - 1)) - (printContentCount - 1);
		int endNum = (contentCount - printContentCount * (currentPage - 1));
		
		boardPagingNumber.setStartNum(startNum);
		boardPagingNumber.setEndNum(endNum);
		
		List<BoardContentDTO> boardList = boardDao.getBoardList(boardPagingNumber);
		return boardList;
	}

	@Override
	public void writeContent(int contentNum, String contentTitle, String contentWriter, String contentDetail) {
		boardDao.writeContent(contentNum, contentTitle, contentWriter, contentDetail);
	}

	@Override
	public BoardContentDTO getContent(int contentNum) {
		return boardDao.getContent(contentNum);
	}
	
	@Override
	public void updateHit(int contentNum){
		boardDao.updateHit(contentNum);
	}

	@Override
	public void deleteContent(int contentNum) {
		boardDao.deleteContent(contentNum);
	}

	@Override
	public void updateContentNum(int contentNum) {
		boardDao.updateContentNum(contentNum);
	}

	@Override
	public void updateContentDetail(int contentNum, String contentTitle, String contentDetail) {
		boardDao.updateContentDetail(contentNum, contentTitle, contentDetail);
	}

}
