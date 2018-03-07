package edu.doubler.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Autowired
	private CpcService cpcService;
	
	private List<CpcDto> classDataList = null;
	private List<CpcDto> subClassDataList = null;
	private List<CpcDto> childDataList = null;
	private CpcDto cpcData = null;
	private CpcDto cpcDataDetail = null;
	
	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * 
	 * 				 	   [ Paging ]
	 * 
	 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
	
	@Autowired
	private PagingService pagingService;
	
	// 기본 홈 화면
	@RequestMapping(value="/")
	public String init(HttpServletRequest request, Model model){
		/** ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ **/
		model.addAttribute("mainCpcSection", getCpcSection());
		/** ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ **/
		
		return getMainView();
	}
	
	
	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * 
	 * 						 [ 공부 내용  ]
	 * 
	 * value = "key/{sectionName}" 으로 명시적으로 해당 요청 URL 에 대한 따로 선언적 구분을 해준다.
	 * c out 보안 이유
	 * application/json , json 차이
	 * c prefix 와 spring prefix 차이
	 * 
	 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
	
	// 섹션 선택 이후 클래스 추출
	@RequestMapping(value="/classification/{sectionName}")
	public String selectSection(
	@PathVariable(value="sectionName") String sectionName,
	Model model){
		/** ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ **/
		model.addAttribute("mainCpcSection", getCpcSection());
		/** ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ **/
		
		classDataList = cpcService.selectClassBySection(sectionName);
		cpcData = cpcService.selectCpcData(sectionName);
		
		model.addAttribute("cpcData", cpcData);
		model.addAttribute("subDataList", classDataList);
		
		return getMainView();
	}
	
	
	// 클래스 선택 이후 서브 클래스 추출
	// @PathVariable을 이용한 경로 변수 지정
	@RequestMapping(value="/classification/{sectionName}/{className}")
	public String selectClass(
	@PathVariable(value="className") String className, Model model){
		/** ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ **/
		model.addAttribute("mainCpcSection", getCpcSection());
		/** ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ **/
		
		subClassDataList = cpcService.selectSubClassByClass(className);
		cpcData = cpcService.selectCpcData(className);
		
		model.addAttribute("cpcData", cpcData);
		model.addAttribute("subDataList", subClassDataList);
		
		return getMainView();
	}
	
	// 서브 클래스 선택 이후 서브 클래스 하위 목록 전체 조회
	// @PathVariable 을 이용
	@RequestMapping(value="/classification/{sectionName}/{className}/{subClassName}")
	public String selectSubClass(
	@PathVariable(value="subClassName") String subClassName, Model model){
		/** ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ **/
		model.addAttribute("mainCpcSection", getCpcSection());
		/** ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ **/
		
		cpcData = cpcService.selectCpcData(subClassName);
		model.addAttribute("cpcData", cpcData);
		
			
//		[ 2018 03 07 ]
//		기존의 코드에는 GET 방식으로 a 태그에 붙여서 오고 있다. 하지만 해당 내용을  ajax 로 수정해서 바꾸어야 한다. 
//		request.getParameter("paging") 이기 때문에 view 내용을 수정하여야 한다.
//		해당 내용 값들은 begin, prev, pagingNumber(Integer), next, end 이렇게 총 5개 존재
		
		BoardPaging boardPaging = new BoardPaging();				// 페이징 처리 이동 객체
		int dataCount = pagingService.getDataCount(subClassName);	// subClassName 의 하위 데이터 개수 추출
		boardPaging.setPagesCount(dataCount);						// 게시글 개수 설정
		
		PagingMovement pagingMovement = new PagingMovementImpl(boardPaging); 
		boardPaging = pagingMovement.chooseMovement("1");
		
		Map<String, Integer> pagingMap = boardPaging.getPagingInformation();
		childDataList = pagingService.getCpcDataList(pagingMap, dataCount, subClassName);
		
		model.addAttribute("subDataList", childDataList);
		model.addAttribute("pagingInfoMap", pagingMap);
		
		return getMainView();
	}
	
	// ajax 이용 상세 데이터 획득
	@RequestMapping(value="/printCpcData", method=RequestMethod.POST, produces = "application/json; charset=utf8")
	@ResponseBody
	public String selectCpcData(@RequestParam("cpcCode") String paramCode, Model model) throws JsonGenerationException, JsonMappingException, IOException{
		
		cpcDataDetail = cpcService.selectCpcData(paramCode);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", cpcDataDetail.getCode());
		map.put("originalText", cpcDataDetail.getOriginalText());
		map.put("translationText", cpcDataDetail.getTranslationText());
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(map);
		
		return jsonString;
	}
	
	
	// main_view.jsp 리턴 메소드
	private String getMainView(){
		return "main_view";
	}
	
	
	// CpcSection 획득 메소드
	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * 
	 * 저장될 캐시의 이름으로 속성값을 지정 ( 현재 section 으로 지정 )
	 * 따라서, getCpcSection()을 호출은 먼저 메소드를 실제로 호출하기 전에
	 * 캐시 주소를 확인한 다음 다음 결과를 캐싱한다.
	 * 
	 * 여러 캐시의 지원도 가능하다.
	 * ex)
	 * @Cacheable({"addresses", "directory"})
	 * 필요한 결과에 포함된 캐시가 있으면 결과가 리턴되고 메소드는 호출되지 않는다.
	 * 
	 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
	private List<CpcDto> getCpcSection(){
		return cpcService.selectCpcSection();
	}
}
