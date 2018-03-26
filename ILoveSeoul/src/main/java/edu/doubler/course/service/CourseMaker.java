package edu.doubler.course.service;

import java.util.List;

import edu.doubler.course.domain.EndAddress;
import edu.doubler.course.domain.MyAddress;
import edu.doubler.course.domain.RouteInfo;

public interface CourseMaker {
	
	// 주소 >> 좌표 변환
	public MyAddress geocoding(String fullAddress);
	
	// 출발지 도착지 좌표로 길찾기 수행
	public List<RouteInfo> getRoute(MyAddress startPoint, EndAddress endPoint);
}
