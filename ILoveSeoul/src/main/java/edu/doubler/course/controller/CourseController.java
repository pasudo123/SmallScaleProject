package edu.doubler.course.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.doubler.course.domain.EndAddress;
import edu.doubler.course.domain.MyAddress;
import edu.doubler.course.domain.RouteInfo;
import edu.doubler.course.service.CourseMaker;
import edu.doubler.enum_api.EnumPalaceType;

@Controller
@RequestMapping(value="/course")
public class CourseController {
	
	@Autowired
	CourseMaker courseMaker;
	
	private static final Logger logger = LoggerFactory.getLogger(CourseController.class);
	
	@RequestMapping(value="/route", method=RequestMethod.POST)
	public String getRoute(Model model,
	@RequestParam("startPoint") String startPoint,
	@RequestParam("endPoint") String endPoint){
		
		logger.info("길찾기 버튼 클릭");
		logger.info(startPoint);
		logger.info(endPoint);
		
		// 주소 >> 좌표 변환 
		logger.info("주소 >> 좌표");
		MyAddress myAddress = courseMaker.geocoding(startPoint);
		
		EndAddress endAddress = new EndAddress();
		endAddress.setTitle(EnumPalaceType.GYEONGBOK_GUNG.getKorName());
		endAddress.setLatitude(EnumPalaceType.GYEONGBOK_GUNG.getMapX());
		endAddress.setLongitude(EnumPalaceType.GYEONGBOK_GUNG.getMapY());
		
		// 좌표 >> 길찾기 수행
		logger.info("좌표 >> 길찾기");
		List<RouteInfo> routeList = courseMaker.getRoute(myAddress, endAddress);
		
		model.addAttribute("routeList", routeList);
		
		return "i_love_seoul/lls_course";
	}
}
