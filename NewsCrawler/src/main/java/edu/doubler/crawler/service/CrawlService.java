package edu.doubler.crawler.service;

import java.util.LinkedHashMap;

import edu.doubler.crawler.domain.News;

public interface CrawlService {
	
	static final String EXE_PATH = "C:\\Users\\Daumsoft\\workspace_Spring\\NewsCrawler\\src\\main\\resources\\WebDriver\\chromedriver.exe";
	static final String SYSTEM_PROPERTY = "webdriver.chrome.driver";
	
	/**
	 * 파싱된 데이터의 값을 맵 형태로 변환
	 * **/
	default LinkedHashMap<String, Object> makeJsonObject(News news){
		LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
		
		linkedHashMap.put("title", news.getTitle());
		linkedHashMap.put("content", news.getContent());
		linkedHashMap.put("comment", news.getComment());
		
		return linkedHashMap;
	}
	
	public LinkedHashMap<String, Object> parseData(String uri);
}
