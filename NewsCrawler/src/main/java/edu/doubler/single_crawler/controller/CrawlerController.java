package edu.doubler.single_crawler.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.doubler.single_crawler.service.CrawlService;
import edu.doubler.single_crawler.service.CrawlServiceOnDaum;
import edu.doubler.single_crawler.service.CrawlServiceOnNaver;

@Controller
public class CrawlerController {

	private static final Logger logger = LoggerFactory.getLogger(CrawlerController.class);
	
	@RequestMapping("/crawler")
	public String mainView(Model model){
		
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
