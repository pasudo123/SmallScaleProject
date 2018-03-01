package edu.doubler.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	// 기본 홈 화면
	@RequestMapping("/")
	public String init(HttpServletRequest request, Model model){
		/** ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ **/
		model.addAttribute("mainCpcSection", getCpcSection());
		/** ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ **/
		
		return getMainView();
	}
	
	
	// 섹션 선택 이후 클래스 추출
	@RequestMapping("/{sectionName}")
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
	@RequestMapping("/{sectionName}/{className}")
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
	@RequestMapping("/{sectionName}/{className}/{subClassName}")
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
