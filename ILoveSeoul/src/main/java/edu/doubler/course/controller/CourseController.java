package edu.doubler.course.controller;

import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.doubler.course.domain.EndAddress;
import edu.doubler.course.domain.MyAddress;
import edu.doubler.course.service.CourseMaker;
import edu.doubler.enum_api.EnumPalaceType;

@Controller
@RequestMapping(value="/course")
public class CourseController {
	
	@Autowired
	CourseMaker courseMaker;
	
	private static final Logger logger = LoggerFactory.getLogger(CourseController.class);
	
	@RequestMapping(value="/route", method=RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, Object> getRoute(
	@RequestParam("startPoint") String startPoint){
		
		logger.info("길찾기 버튼 클릭");
		logger.info(startPoint);
		
		// 주소 >> 좌표 변환 
		logger.info("주소 >> 좌표");
		MyAddress myAddress = courseMaker.geocoding(startPoint);
		
//		if(myAddress == null){
//			return null;
//		}
		
		EndAddress endAddress = new EndAddress();
		endAddress.setTitle(EnumPalaceType.GYEONGBOK_GUNG.getKorName());
		endAddress.setLatitude(EnumPalaceType.GYEONGBOK_GUNG.getMapX());
		endAddress.setLongitude(EnumPalaceType.GYEONGBOK_GUNG.getMapY());
		
//		ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
//		
//		[ 단순 좌표값만으로 경로 찾기를 하고자 하는 경우 ]
//	
//		ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
		linkedHashMap.put("startPointLng", myAddress.getLongitude());
		linkedHashMap.put("startPointLat", myAddress.getLatitude());
		linkedHashMap.put("endPointLng", endAddress.getLongitude());
		linkedHashMap.put("endPointLat", endAddress.getLatitude());
		
		// 좌표 >> 길찾기 수행
//		logger.info("좌표 >> 길찾기");
//		List<RouteInfo> routeList = courseMaker.getRoute(myAddress, endAddress);
		
//		if(routeList == null){
//			return null;
//		}
		
//		@SuppressWarnings("unchecked")
//		LinkedHashMap<String, Object>[]linkedHashMap = new LinkedHashMap[routeList.size()];
//		
//		for(int i = 0; i < linkedHashMap.length; i++){
//			linkedHashMap[i] = new LinkedHashMap<String, Object>();
//			linkedHashMap[i] = routeList.get(i).getHashMap();
//		}
		
		return linkedHashMap;
	}
}
