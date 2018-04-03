package edu.doubler.multi_crawler.factory;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;

import edu.doubler.multi_crawler.service.MultiCrawlService;

public class ParserFactory {
	ChromeDriver webDriver;
	
	public ChromeDriver init(String uri){
		System.setProperty(MultiCrawlService.SYSTEM_PROPERTY, MultiCrawlService.EXE_PATH);
		webDriver = new ChromeDriver();
		webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		webDriver.get(uri);
		
		return webDriver;
	}
}
