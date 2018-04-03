package edu.doubler.multi_crawler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.doubler.multi_crawler.service.MultiCrawlService;
import edu.doubler.multi_crawler.service.MultiCrawlServiceOnDaum;
import edu.doubler.multi_crawler.service.MultiCrawlServiceOnNaver;

@Controller
@RequestMapping(value="/multi_crawler")
public class MultiCrawlerController {
	
	@RequestMapping(value="/create")
	public String gatheringNewForAWeek(){
		
		String name = "naver";
		MultiCrawlService multiCrawlService = null;
		
		if(name.equals("naver"))
			multiCrawlService = new MultiCrawlServiceOnNaver();
		else
			multiCrawlService = new MultiCrawlServiceOnDaum();
		
		// 해당 내용 실행
//		multiCrawlService.process();
		
		return null;
	}
}
