package edu.doubler.multi_crawler.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.doubler.multi_crawler.domain.EnumNaverSection;
import edu.doubler.multi_crawler.domain.EnumSite;
import edu.doubler.multi_crawler.factory.ParserFactory;

public class MultiCrawlServiceOnNaver implements MultiCrawlService{

//	@Override
	public static void process() {
		/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 
		 * 						[ 날짜 계산 ]
		 * 
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int days = calendar.get(Calendar.DATE);
		
		calendar.set(year, month-1, days);
		
		// 날짜별 + 섹션별
		for(int day = 0; day < 7; day++){
			
			calendar.add(Calendar.DATE, -1*day);
			Date agoDate = calendar.getTime();
			
			String crawlTime = simpleDateFormat.format(agoDate);
			
			String uri = EnumSite.NAVER.getSiteURI();
			uri += "&";
			
			// 해당 날짜에 대한 섹션 uri 구성
			EnumNaverSection enumNaverSection[] = EnumNaverSection.values();
			for(EnumNaverSection naverSection : enumNaverSection){
				uri += naverSection.getKey() + "=" + naverSection.getSectionId();
				uri += "&";
				uri += "date=" + crawlTime;
				
				/**
				 * WebDriver Tool 사용
				 * **/
				ParserOnNaverNews parserOnNaverNews = new ParserOnNaverNews();
				parserOnNaverNews.crawling(new ParserFactory().init(uri), naverSection.getName());
				
				
				// uri 초기화
				uri = EnumSite.NAVER.getSiteURI();
				uri += "&";
				break;
			}// for(섹션별)
			break;
		}// for(날짜별)
		
		
	}// process() : @Override
	
	public static void main(String[]args){
		process();
	}
}
