package edu.doubler.menu.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.doubler.enum_api.EnumPalaceType;
import edu.doubler.menu.service.MenuService;

@Controller
public class MenuController {
	
	@Autowired
	MenuService menuServive;
	
	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
	EnumPalaceType[]palaceTypes = EnumPalaceType.values();
	
	// -- 시작화면
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		
		model.addAttribute("palaceTypes", palaceTypes);
		
		return "i_love_seoul/lls_palace_menu";
	}
	
	// -- 주변 살피기
	@RequestMapping(value = "/show_observe", method=RequestMethod.POST)
	public String observe(
	@RequestParam("palaceName") String palaceName,	// 고궁 이름	
	@RequestParam("mapX") String mapX,				// 위도
	@RequestParam("mapY") String mapY,				// 경도
	@RequestParam("radius") String radius,			// 반경
	Model model){
		
		logger.info(palaceName + " 주변살피기");
		List<Object> locationDataList = menuServive.selectListOnLocationBasedInfo(mapX, mapY, "1000");
		
		model.addAttribute("palaceName", palaceName);
		model.addAttribute("palaceMapX", mapX);
		model.addAttribute("palaceMapY", mapY);
		model.addAttribute("locationDataList", locationDataList);
		
		return "i_love_seoul/lls_content";
	}
	
	// -- 주변 살피기 페이지 이동 ( 페이지 처리 )
	@RequestMapping(value = "/observe_movement", method=RequestMethod.GET)
	public String observeMovement(
	@RequestParam("page") String page,
	Model model){
		
		
		
		return null;
	}
	
	@RequestMapping(value = "/show_pathTo", method=RequestMethod.POST)
	public String pathTo(){
		
		logger.info("가는 길");
		
		return null;
	}
	
	// -- 길찾기 
}