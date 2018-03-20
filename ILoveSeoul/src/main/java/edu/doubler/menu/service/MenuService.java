package edu.doubler.menu.service;

import java.util.List;

import edu.doubler.domain.AreaBasedData;

public interface MenuService {
	
	public List<AreaBasedData> selectList(String parentCate, String childCate);
}
