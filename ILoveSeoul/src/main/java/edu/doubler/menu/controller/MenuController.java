package edu.doubler.menu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.doubler.domain.AreaBasedData;
import edu.doubler.domain.PalaceType;
import edu.doubler.enum_api.EnumPalaceType;
import edu.doubler.menu.service.MenuService;

@Controller
public class MenuController {
	
	@Autowired
	MenuService menuServive;
	
	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
	EnumPalaceType[]palaceTypes = EnumPalaceType.values();
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		model.addAttribute("palaceTypes", palaceTypes);
		return "i_love_seoul/lls_palace_menu";
	}
	
	@RequestMapping(value = "/select_sigungu", method = RequestMethod.POST)
	public String selectMenu(
	@RequestParam("parentCate") String parentCate,
	@RequestParam("childCate") String childCate,
	Model model){
		
		logger.info("선택 카테고리 : " + parentCate);
		logger.info("선택한 자치구 : " + childCate);
		
		List<AreaBasedData> dataList = menuServive.selectList(parentCate, childCate);
		
		for(AreaBasedData data : dataList){
			logger.info(data.toString());
		}
		
		model.addAttribute("dataList", dataList);
		
		return "i_love_seoul/lls_list";
	}
}
