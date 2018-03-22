package edu.doubler.menu.service;

import java.util.LinkedHashMap;
import java.util.List;

public interface MenuService {
	
	// 위치기반으로 관광정보 조회 (위도, 경도, 반경)
	public List<Object> selectListOnLocationBasedInfo(String mapX, String mapY, String radius, String pageNo);

	
	// Responsebody 를 위한 json 데이터 획득
	public LinkedHashMap<String, Object> selectPageMovement(String mapX, String mapY, String radius, String pageNo);
}
