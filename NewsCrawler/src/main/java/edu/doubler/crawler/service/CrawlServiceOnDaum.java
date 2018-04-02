package edu.doubler.crawler.service;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;

import edu.doubler.crawler.domain.News;
import edu.doubler.crawler.domain.NewsComment;

public class CrawlServiceOnDaum implements CrawlService{
	
	@Override
	public LinkedHashMap<String, Object> parseData(String uri) {
		System.setProperty(SYSTEM_PROPERTY, EXE_PATH);
		ChromeDriver webDriver = new ChromeDriver();
		webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		webDriver.get(uri);
		
		// WebElement : 제목, 내용
		WebElement newsTitleElement = webDriver.findElement(By.className("tit_view"));
		WebElement newsContentElement = webDriver.findElement(By.className("news_view"));

		FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(webDriver);
		fluentWait.withTimeout(Duration.ofMinutes(10));		// FluentWait 인스턴스가 조건을 기다리는 최대 시간 (10분 설정)
		fluentWait.pollingEvery(Duration.ofSeconds(10));	// CPU가 리소스에 접근하기 위한 폴링 간격 조절 (10초)
		fluentWait.ignoring(NoSuchElementException.class);
				
		// <입력 파라미터, apply() 메소드 반환 값>
		Function<WebDriver, Boolean> commentViewFunction = new Function<WebDriver, Boolean>(){
			@Override
			public Boolean apply(WebDriver webDriver) {
				// [ 댓 글 더 보 기 ] 계속 클릭 : 다음은 하나의 화면 내에서 댓글을 볼 수 있도록 관리 되어있음
				WebElement viewMoreComment = webDriver.findElement(By.xpath("//*[@id='alex-area']/div/div/div/div[3]/div[1]/a"));
				
				// 해당 요소 사이즈 확인
				Dimension dimension = viewMoreComment.getSize();
				int width = dimension.getWidth();
				int height = dimension.getWidth();
				
				// 중단 (클릭할 요소가 없기 때문에)
				if(width == 0 && height == 0){
					return true;
				}
				// 계속 시행
				else{
					viewMoreComment.click();
					return false;
				}
			}
		};
		
		fluentWait.until(commentViewFunction);
		
		/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 
		 * 				뉴스 객체 
		 * 
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
		News news = new News();
		
		// 기사 제목(개행제거), 기사 내용
		String newsTitle = newsTitleElement.getText().replaceAll("\n", "");	
		String newsContent = newsContentElement.getText().replaceAll("\n", "");;
		news.setTitle(newsTitle);
		news.setContent(newsContent);
	
		// 댓글 날짜, 댓글 내용
		List<WebElement> newsDateList = webDriver.findElements(By.className("txt_date"));
		List<WebElement> newsCommentList = webDriver.findElements(By.className("desc_txt"));
				
		// 댓글 순차 입력
		int size = newsCommentList.size();
		for(int i = 0; i < size; i++){
			NewsComment newsComment = new NewsComment();
			newsComment.setDate(newsDateList.get(i).getText());
			newsComment.setComment(newsCommentList.get(i).getText().replaceAll("\n", ""));
			
			news.addComment(newsComment);
		}
				
		// webDriver 닫기
		webDriver.close();
		
		
		/**
		 * 뉴스데이터를 map 으로 변환 및 반환
		 * **/
		return this.makeJsonObject(news);
	}
}
