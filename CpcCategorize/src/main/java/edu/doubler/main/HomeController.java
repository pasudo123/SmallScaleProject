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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.doubler.dao.CpcDto;
import edu.doubler.service.CpcService;

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
	
	// 기본 홈 화면
	@RequestMapping(value="/")
	public String init(HttpServletRequest request, Model model){
		/** ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ **/
		model.addAttribute("mainCpcSection", getCpcSection());
		/** ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ **/
		
		return getMainView();
	}
	
	
	// 섹션 선택 이후 클래스 추출
	@RequestMapping(value="/{sectionName}")
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
	@RequestMapping(value="/{sectionName}/{className}")
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
	@RequestMapping(value="/{sectionName}/{className}/{subClassName}")
	public String selectSubClass(
	@PathVariable(value="subClassName") String subClassName, Model model){
		/** ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ **/
		model.addAttribute("mainCpcSection", getCpcSection());
		/** ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ **/
		
		childDataList = cpcService.selectChildBySubClass(subClassName);
		cpcData = cpcService.selectCpcData(subClassName);
		
		model.addAttribute("cpcData", cpcData);
		model.addAttribute("subDataList", childDataList);
		
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
		
		System.out.println(cpcDataDetail.getTranslationText());
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(map);
		
		return jsonString;
	}
	
	
	// main_view.jsp 리턴 메소드
	private String getMainView(){
		return "main_view";
	}
	
	
	// CpcSection 획득 메소드
	// 해당 메소드가 모든 url 요청해서 중복해서 일어나기 때문에
	// 이와 같은 문제들에 대해서 따로 공부해야함
	private List<CpcDto> getCpcSection(){
		return cpcService.selectCpcSection();
	}
}
