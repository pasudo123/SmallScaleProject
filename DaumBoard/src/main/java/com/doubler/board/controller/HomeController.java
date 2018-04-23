package com.doubler.board.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.doubler.EnumViewPath;
import com.doubler.board.dao.BoardContentDTO;
import com.doubler.board.service.BoardService;
import com.doubler.board.util.BoardPaging;
import com.doubler.board.util.BoardSequenceNumber;
import com.doubler.board.util.PagingMovement;
import com.doubler.board.util.PagingMovementImpl;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private BoardService boardService;
	
	private static Integer contentNumber = null;								// 번호표
	private BoardContentDTO boardContentDto = new BoardContentDTO();			// DTO 객체
	private BoardSequenceNumber boardSequenceNum = new BoardSequenceNumber();	// 게시글 시퀀스 객체
	private static BoardPaging boardPaging = new BoardPaging();					// 페이징 처리 객체
	private PagingMovement pagingMovement = new PagingMovementImpl();			// 페이징 이동 객체
	
	
//	// 게시판 리스트 보여주는 컨트롤러
//	@RequestMapping(value="boardList", method=RequestMethod.GET)
//	public String showboardList(HttpServletRequest request, Model model){
//		int contentCount = boardService.getContentCount();				// 최근에 작성된 번호 추출
//		contentNumber = boardSequenceNum.getContentNumber(contentCount);
//		boardPaging.setPagesCount(contentCount);
//		
//		String pageNumber = request.getParameter("paging");
//		
//		if(pageNumber != null){
//			boardPaging = pagingMovement.chooseMovement(pageNumber);
//		}
//		
//		model.addAttribute("pagingInfoMap", boardPaging.getPagingInformation());
//		model.addAttribute("boardContent", boardService.getBoardList(boardPaging.getPagingInformation(), contentCount));
//		
//		return EnumViewPath.BOARD_LIST_VIEW.getPath();
//	}
//	
	
	// 게시판 글쓰기 폼 보여주는 컨트롤러
	@RequestMapping(value="boardWriteView")
	public String showBoardwriteView(Model model){
		return "boardWriteView";
	}
	
	
	// 게시글 작성 컨트롤러
	@RequestMapping(value="boardWrite", method=RequestMethod.POST)
	public String writeContent(HttpServletRequest request, Model model){
		if(contentNumber == null){
			int contentCount = boardService.getContentCount();
			contentNumber = boardSequenceNum.getContentNumber(contentCount);
		}
		
		// 가장 늦은 번호 + 1
		contentNumber = boardSequenceNum.getPlusContentNumber(contentNumber);
		
		// 번호, 제목, 작성자, 내용
		int contentNum = HomeController.contentNumber;
		String contentWriter = request.getParameter("writer");	
		String contentTitle = request.getParameter("title");
		String contentDetail = request.getParameter("content");
		
		boardService.writeContent(contentNum, contentTitle, contentWriter, contentDetail);
		
		return "redirect:" + EnumViewPath.BOARD_WRITE_VIEW.getPath() + "?paging=1";
	}
	
	
	// 게시글 보기 컨트롤러
	@RequestMapping(value="boardList/boardContentView", method=RequestMethod.GET)
	public String getContent(HttpServletRequest request, Model model){
		String contentNumStr = request.getParameter("contentNum");
		int contentNumInt = Integer.parseInt(contentNumStr);
		
		// 조회수 증가 ㅡ> 특정 게시글 조회
		boardService.updateHit(contentNumInt);
		boardContentDto = boardService.getContent(contentNumInt);
		model.addAttribute("content", boardContentDto);
		
		return "/boardContentView";
	}
	
	
	// 게시글 수정 컨트롤러
	@RequestMapping(value="/boardModify", method=RequestMethod.POST)
	public String modifyContent(HttpServletRequest request){
		String contentNumStr = request.getParameter("contentNum");
		
		int contentNumInt = Integer.parseInt(contentNumStr);
		String contentTitle = request.getParameter("title");
		String contentDetail = request.getParameter("content");
		
		boardService.updateContentDetail(contentNumInt, contentTitle, contentDetail);
		
		return "redirect:boardList?paging=1";
	}
	
	
	// 게시글 삭제 컨트롤러
	@RequestMapping(value="/boardDelete", method=RequestMethod.GET)
	public String deleteContent(HttpServletRequest request){
		String contentNumStr = request.getParameter("contentNum");
		int contentNumInt = Integer.parseInt(contentNumStr);
		
//		-- 해당 contentNum 번호 우선 저장
//		-- 해당 contentNum 번호를 가진 데이터 제거
//		-- 저장한 contentNum보다 큰 번호(최근 게시글)의 contentNum을 전체 -1 설정
		
		boardService.deleteContent(contentNumInt);
		boardService.updateContentNum(contentNumInt);
		
		return "redirect:boardList?paging=1";
	}
}
