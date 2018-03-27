package edu.doubler.menu.controller;

import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.doubler.enum_api.EnumPalaceType;
import edu.doubler.menu.domain.LocationBasedData;
import edu.doubler.menu.service.MenuService;
import edu.doubler.weather.domain.WeatherContext;
import edu.doubler.weather.service.WeatherCall;

@Controller
public class MenuController {
	
	@Autowired
	MenuService menuServive;
	
	@Autowired 
	WeatherCall weatherCall;
	
	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
	EnumPalaceType[]palaceTypes = EnumPalaceType.values();
	
	// -- 시작화면
	@RequestMapping(value = "/")
	public String home(Model model) {
		
		// 서울날씨 보여주기 (경복궁 중심)
		WeatherContext weatherContext = weatherCall.getWeatherContext(EnumPalaceType.GYEONGBOK_GUNG.getMapY(), EnumPalaceType.GYEONGBOK_GUNG.getMapX());
		
		model.addAttribute("palaceTypes", palaceTypes);
		model.addAttribute("weatherContext", weatherContext);
		
		return "i_love_seoul/lls_palace_menu";
	}
	
	
	// -- 주변 살피기
	@RequestMapping(value = "/show_observe", method=RequestMethod.POST)
	public String observe(
	@RequestParam("palaceName") String palaceName,	// 고궁 이름	
	@RequestParam("mapX") String mapX,				// 위도
	@RequestParam("mapY") String mapY,				// 경도
	@RequestParam("pageNo") String pageNo,			// 한 페이지의 결과 수
	Model model){
		
		logger.info("\n");
		logger.info(palaceName + " 주변살피기");
		List<Object> locationDataList = menuServive.selectListOnLocationBasedInfo(mapX, mapY, "1000", pageNo);
		
		model.addAttribute("palaceName", palaceName);
		model.addAttribute("palaceMapX", mapX);
		model.addAttribute("palaceMapY", mapY);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("totalCount", ((LocationBasedData)locationDataList.get(0)).getTotalCount());
		model.addAttribute("locationDataList", locationDataList);
		
		return "i_love_seoul/lls_content";
	}
	
	
	// -- 주변 살피기 페이지 이동 ( 페이지 처리 )
	@RequestMapping(value = "/{palaceName}/observe_movement", method=RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, Object> observeMovement(
	@PathVariable("palaceName") String palaceName,
	@RequestParam("mapX") String mapX,
	@RequestParam("mapY") String mapY,
	@RequestParam("pageNo") String pageNo,
	Model model){
		
		logger.info("\n");
		logger.info("페이지 이동");
		logger.info(palaceName + " // " + mapX + " // " + mapY + " // " + pageNo);
		LinkedHashMap<String, Object> locationDataList = menuServive.selectPageMovement(mapX, mapY, "1000", pageNo);
		locationDataList.put("pageNo", pageNo);
		
		return locationDataList;
	}
	
	@RequestMapping(value = "/show_pathTo", method=RequestMethod.POST)
	public String pathTo(){
		
		logger.info("가는 길 메뉴 선택");
		
		return "i_love_seoul/lls_course";
	}
}