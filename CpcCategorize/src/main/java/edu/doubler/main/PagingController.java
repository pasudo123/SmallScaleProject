package edu.doubler.main;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.doubler.dao.CpcDto;
import edu.doubler.service.CpcService;
import edu.doubler.service.PagingService;
import edu.doubler.util.BoardPaging;
import edu.doubler.util.PagingMovement;
import edu.doubler.util.PagingMovementImpl;

@Controller
public class PagingController {
	
	@Autowired
	private CpcService cpcService;
	@Autowired
	private PagingService pagingService;
	
	@RequestMapping(value="/classification/{sectionName}/{className}/{subClassName}/page", method=RequestMethod.POST)
	public String selectChildCpcData(
	@PathVariable(value="subClassName") String subClassName, 
	@RequestParam(value="pageChoose") String pageChoose,
	@RequestParam(value="page") String pageNum,  Model model){
		CpcDto cpcData = cpcService.selectCpcData(subClassName);
		model.addAttribute("cpcData", cpcData);
		
		/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 
		 *    하위목록을 조회하는 경우 (페이징 처리) 
		 * 
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
		/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 
		 * [ 페이지 관련 정보 전체를 리턴, boardPaging.getPagingInformation() ]
		 * - 전체 게시글, 현재 페이지, 필요 페이지, 첫번쨰 페이지 번호, 마지막 페이지 번호
		 * - 현재 블록, 첫번째 블록, 마지막 블록, 필요 블록
		 * - 한 페이지에 보여줄 글의 수, 한 화면당 페이지의 수 
		 * 
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
		// 페이징 처리**
		
		// 해당 서브 클래스에 대한 데이터 개수 추출 및 설정
		int dataCount = pagingService.getDataCount(subClassName);
		BoardPaging boardPaging = new BoardPaging(pageNum);
		boardPaging.setPagesCount(dataCount);
		
		PagingMovement pagingMovement = new PagingMovementImpl(boardPaging); 
		boardPaging = pagingMovement.chooseMovement(pageChoose);
		
		Map<String, Integer> pagingMap = boardPaging.getPagingInformation();
		List<CpcDto> childDataList = pagingService.getCpcDataList(pagingMap, dataCount, subClassName);
		
		model.addAttribute("subDataList", childDataList);
		model.addAttribute("pagingInfoMap", pagingMap);
		
		return "main_class_view";
	}
}
