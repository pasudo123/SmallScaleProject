package edu.doubler.menu.service;

import java.util.List;

import edu.doubler.domain.AreaBasedData;
import edu.doubler.domain.LocationBasedData;

public interface MenuService {
	
	// 위치기반으로 관광정보 조회 (위도, 경도, 반경)
	public List<Object> selectListOnLocationBasedInfo(String mapX, String mapY, String radius);
}
