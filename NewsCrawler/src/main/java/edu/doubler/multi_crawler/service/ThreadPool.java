package edu.doubler.multi_crawler.service;

import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;

import edu.doubler.multi_crawler.factory.ParserFactory;

public class ThreadPool implements Runnable{
	private ChromeDriver webDriver = null;
	private ParserOnNaverNews parserOnNaverNews = null;
	private String name = null;
	private String uri = null;
	
	private static Logger logger = Logger.getLogger(ThreadPool.class);
	
	@Override
	public void run() {
		/**
		 * START
		 * **/
		System.out.println("Start Crawling : " + name);

		process();
		
		/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 	  	 Thread.sleep ???
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
		
		/**
		 * END
		 * **/
		System.out.println("End Crawling : " + name);
	}
	
	public void process(){
		parserOnNaverNews = new ParserOnNaverNews();
		parserOnNaverNews.setting(new ParserFactory().init(uri), name);
		parserOnNaverNews.rankCrawling();
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setURI(String uri){
		this.uri = uri;
	}
}
