package com.doubler.board;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.doubler.board.dto.BoardContentDTO;
import com.doubler.board.util.BoardPagingNumber;

@Repository
public class BoardDAOImpl implements BoardDAO{
	
	@Autowired
	private SqlSession sqlSession;
	
	private String nameSpace = "com.doubler.board.BoardDAO";
	private BoardContentDTO boardContentDto = new BoardContentDTO();
	
	@Override
	public int getContentCount() {
		return sqlSession.selectOne(nameSpace + ".getContentCount");
	}
	
	@Override
	public List<BoardContentDTO> getBoardList(){
		return sqlSession.selectList(nameSpace + ".getFullBoardList");
	}
	
	@Override
	public List<BoardContentDTO> getBoardList(BoardPagingNumber boardPagingNumber) {
		return sqlSession.selectList(nameSpace + ".getBoardList", boardPagingNumber);
	}
	
	@Override
	public void writeContent(int contentNum, String contentTitle, String contentWriter, String contentDetail) {
		// DTO 객체를 통한 파라미터 전달
		boardContentDto.setContentNum(contentNum);
		boardContentDto.setContentTitle(contentTitle);
		boardContentDto.setContentWriter(contentWriter);
		boardContentDto.setContentDetail(contentDetail);
		
		sqlSession.insert(nameSpace + ".writeContent", boardContentDto);
	}

	@Override
	public BoardContentDTO getContent(int contentNum) {
		boardContentDto.setContentNum(contentNum);
		return sqlSession.selectOne(nameSpace + ".getContent", boardContentDto);
	}
	
	@Override
	public void updateHit(int contentNum){
		boardContentDto.setContentNum(contentNum);
		sqlSession.update(nameSpace + ".updateHit", boardContentDto);
	}

	@Override
	public void deleteContent(int contentNum) {
		boardContentDto.setContentNum(contentNum);
		sqlSession.delete(nameSpace + ".deleteContent", boardContentDto);
	}

	@Override
	public void updateContentNum(int contentNum) {
		boardContentDto.setContentNum(contentNum);
		sqlSession.update(nameSpace + ".updateContentNum", boardContentDto);
	}

	@Override
	public void updateContentDetail(int contentNum, String contentTitle, String contentDetail) {
		boardContentDto.setContentNum(contentNum);
		boardContentDto.setContentTitle(contentTitle);
		boardContentDto.setContentDetail(contentDetail);
		
		sqlSession.update(nameSpace + ".updateContentDetail", boardContentDto);
	}
}
