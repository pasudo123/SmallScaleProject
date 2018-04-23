package com.doubler.board.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.doubler.EnumViewPath;
import com.doubler.board.dao.BoardContentDTO;
import com.doubler.board.service.BoardService;
import com.doubler.board.util.BoardPaging;
import com.doubler.board.util.BoardSequenceNumber;
import com.doubler.board.util.PagingMovement;
import com.doubler.board.util.PagingMovementImpl;

@Controller
public class BoardController {
	
	// 로그
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired 
	private BoardService boardService;
	
	private static Integer contentNumber = null;								// 번호표
	private BoardContentDTO boardContentDto = new BoardContentDTO();			// DTO 객체
	private BoardSequenceNumber boardSequenceNum = new BoardSequenceNumber();	// 게시글 시퀀스 객체
	private static BoardPaging boardPaging = new BoardPaging();					// 페이징 처리 객체
	private PagingMovement pagingMovement = new PagingMovementImpl();			// 페이징 이동 객체
	
	
	@RequestMapping(value="/boardList/{number}", method=RequestMethod.GET)
	public String showBoardList(
	@PathVariable("number") String number,
	HttpServletRequest request, Model model){
		
		/** 게시판 조회 **/
		logger.info("== 게시판 조회");
		
		/**
		 * 현재 게시판의 전체 게시글 개수 조회 - contentCount
		 * 페이징 처리를 위한 게시글 개수를 설정 - setPagesCount
		 * **/
		int contentCount = boardService.getContentCount();					// 최근에 작성된 번호 추출
		boardPaging.setPagesCount(contentCount);
		
		/**
		 * GET Parameter 로 받은 페이징 처리 번호
		 * **/
//		String pageNumber = request.getParameter("paging");
//		if(pageNumber != null)
//			boardPaging = pagingMovement.chooseMovement(pageNumber);
		
//		HashMap<String, Integer> pagingInfoMap = boardPaging.getPagingInformation();
		List<BoardContentDTO> boardContentList = boardService.getBoardList(boardPaging.getPagingInformation(), contentCount);
		
//		model.addAttribute("pagingInfoMap", pagingInfoMap);
		model.addAttribute("boardContent", boardContentList);
		
		return EnumViewPath.BOARD_LIST_VIEW.getPath();
	}
}
