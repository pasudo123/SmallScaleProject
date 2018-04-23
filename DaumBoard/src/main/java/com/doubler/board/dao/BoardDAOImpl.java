package com.doubler.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.doubler.board.constants.EnumDatabase;
import com.doubler.board.util.BoardPagingNumber;

@Repository
public class BoardDAOImpl implements BoardDAO{
	
	@Autowired
	private SqlSession sqlSession;
	private BoardContentDTO boardContentDto = new BoardContentDTO();
	
	@Override
	public int getContentCount() {
		return sqlSession.selectOne(EnumDatabase.NAME_SPACE + "." +EnumDatabase.QUERY_ON_GET_CONTENT_COUNT);
	}
	
	@Override
	public List<BoardContentDTO> getBoardList(){
		return sqlSession.selectList(EnumDatabase.NAME_SPACE.getValue() + ".getFullBoardList");
	}
	
	@Override
	public List<BoardContentDTO> getBoardList(BoardPagingNumber boardPagingNumber) {
		return sqlSession.selectList(EnumDatabase.NAME_SPACE.getValue() + ".getBoardList", boardPagingNumber);
	}
	
	@Override
	public void writeContent(int contentNum, String contentTitle, String contentWriter, String contentDetail) {
		// DTO 객체를 통한 파라미터 전달
		boardContentDto.setContentNum(contentNum);
		boardContentDto.setContentTitle(contentTitle);
		boardContentDto.setContentWriter(contentWriter);
		boardContentDto.setContentDetail(contentDetail);
		
		sqlSession.insert(EnumDatabase.NAME_SPACE.getValue() + ".writeContent", boardContentDto);
	}

	@Override
	public BoardContentDTO getContent(int contentNum) {
		boardContentDto.setContentNum(contentNum);
		return sqlSession.selectOne(EnumDatabase.NAME_SPACE.getValue() + ".getContent", boardContentDto);
	}
	
	@Override
	public void updateHit(int contentNum){
		boardContentDto.setContentNum(contentNum);
		sqlSession.update(EnumDatabase.NAME_SPACE.getValue() + ".updateHit", boardContentDto);
	}

	@Override
	public void deleteContent(int contentNum) {
		boardContentDto.setContentNum(contentNum);
		sqlSession.delete(EnumDatabase.NAME_SPACE.getValue() + ".deleteContent", boardContentDto);
	}

	@Override
	public void updateContentNum(int contentNum) {
		boardContentDto.setContentNum(contentNum);
		sqlSession.update(EnumDatabase.NAME_SPACE + ".updateContentNum", boardContentDto);
	}

	@Override
	public void updateContentDetail(int contentNum, String contentTitle, String contentDetail) {
		boardContentDto.setContentNum(contentNum);
		boardContentDto.setContentTitle(contentTitle);
		boardContentDto.setContentDetail(contentDetail);
		
		sqlSession.update(EnumDatabase.NAME_SPACE + ".updateContentDetail", boardContentDto);
	}
}
