package edu.doubler.crawler.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.doubler.crawler.service.CrawlService;
import edu.doubler.crawler.service.CrawlServiceOnDaum;
import edu.doubler.crawler.service.CrawlServiceOnNaver;

@Controller
public class CrawlerController {

	private static final Logger logger = LoggerFactory.getLogger(CrawlerController.class);
	
	@RequestMapping("/crawler")
	public String mainView(Model model){
		
		// 관련 공부
		// logger.info(PageContext.REQUEST);
		
		// Daum Test
		// http://v.media.daum.net/v/20180330132100152
		
		// Naver Test
		// http://news.naver.com/main/read.nhn?mode=LSD&mid=shm&sid1=100&oid=001&aid=0009993216
		
		logger.info("main view 호출");
		return "news_crawler";
	}
	
	@RequestMapping(value="/crawler/gather", method=RequestMethod.POST)
	public String gatheringNewsData(
	@RequestParam("newsAddrInput") String uri, Model model){
		
		logger.info("수집 뉴스 URI : " + uri);
		CrawlService crawlingService = getCrawlingService(uri);
		
		
		model.addAttribute("newsGather", crawlingService.parseData(uri));
		
		return "news_result";
	}
	
	private CrawlService getCrawlingService(String uri){
		if(uri.contains("naver"))
			return new CrawlServiceOnNaver();
		
		if(uri.contains("daum"))
			return new CrawlServiceOnDaum();
			
		return null;
	}
}
