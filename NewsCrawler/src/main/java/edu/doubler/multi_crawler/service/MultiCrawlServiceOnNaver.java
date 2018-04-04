package edu.doubler.multi_crawler.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
		
		// 4 개의 풀을 fix
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		
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
				
				ThreadPool crawlThread = new ThreadPool();
				crawlThread.setName(naverSection.getName());
				crawlThread.setURI(uri);
				executorService.execute(crawlThread);
				
				/**
				 * WebDriver Tool 사용 (단순 한개씩 시도)
				 * **/
//				ParserOnNaverNews parserOnNaverNews = new ParserOnNaverNews();
//				parserOnNaverNews.setting(new ParserFactory().init(uri), naverSection.getName());
//				parserOnNaverNews.rankCrawling();
				
				// uri 초기화
				uri = EnumSite.NAVER.getSiteURI();
				uri += "&";
			}// for(섹션별)
		}// for(날짜별 : 7일)
		
		/* ExecutorService 종료 */
		executorService.shutdown();
		
		/* ExecutorService 종료되었는지 확인. */
		while(!executorService.isTerminated()) {}

	}// process() : @Override
	
	public static void main(String[]args){
		process();
	}
}
