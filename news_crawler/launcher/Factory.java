package news_crawler.launcher;

import news_crawler.crawler.Crawler;
import news_crawler.crawler.NaverNewsCrawler;
import news_crawler.domain.News;

public class Factory {
	public News newsCrawling(String uri){
		if(uri.contains("naver")){
			Crawler crawler = new NaverNewsCrawler();
			
			return crawler.parsingData(uri);
		}
		
		return null;
	}
}
