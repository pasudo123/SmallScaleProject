package edu.doubler.single_crawler.service;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;

import edu.doubler.single_crawler.domain.News;
import edu.doubler.single_crawler.domain.NewsComment;

@Service
public class CrawlServiceOnNaver implements CrawlService{

	@Override
	public LinkedHashMap<String, Object> parseData(String uri) {
		System.setProperty(SYSTEM_PROPERTY, EXE_PATH);
		ChromeDriver webDriver = new ChromeDriver();
		webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		webDriver.get(uri);
		
		/***************************************************************************/
		/**        내용과 별개로 댓글을 보기위한 새로운 페이지가 따로 존재하기 때문에 미리 값을 저장	  **/
		// WebElement : 제목, 내용
		WebElement newsTitleElement = webDriver.findElement(By.id("articleTitle"));
		WebElement newsContentElement = webDriver.findElement(By.id("articleBodyContents"));
		
		// 기사 제목(개행제거), 기사 내용
		String newsTitle = newsTitleElement.getText().replaceAll("\n", "");
		String newsContent = newsContentElement.getText().replaceAll("\n", "");;
		/***************************************************************************/
		
		FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(webDriver);
		fluentWait.withTimeout(Duration.ofMinutes(10));		// 인스턴스 조건을 기다리는 최대 시간 (10분 설정)
//		fluentWait.pollingEvery(Duration.ofSeconds(3));	// CPU가 리소스에 접근하기 위한 폴링 간격 조절 (10초)
		fluentWait.ignoring(NoSuchElementException.class);
		
		// [ 댓 글 더 보 기 ] 블럭 확인
		WebElement viewMoreBlock = webDriver.findElement(By.className("u_cbox_view_comment"));
		
		// [ 댓 글 더 보 기 ] 클릭
		WebElement viewMoreCommentElement = webDriver.findElement(By.className("u_cbox_in_view_comment"));
		
		// [ 댓 글 더 보 기 ] 블럭 CSS 확인
		Boolean isDisplay = viewMoreBlock.getCssValue("display").equals("block")?true:false;
		if(isDisplay)
			viewMoreCommentElement.click();
		
		Function<WebDriver, Boolean> commentViewFunction = new Function<WebDriver, Boolean>(){
			@Override
			public Boolean apply(WebDriver webDriver) {
				// [ 더 보 기 ] 계속 클릭
				WebElement viewMoreComment = webDriver.findElement(By.xpath("//*[@id='cbox_module']/div/div[9]"));
				
				// css 값 확인
				Boolean isDisplay = viewMoreComment.getCssValue("display").equals("block")?true:false;
				
				// 중단 (클릭할 요소가 없기 때문에)
				if(!isDisplay){
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
		news.setTitle(newsTitle);
		news.setContent(newsContent);
				
		// 댓글 날짜, 댓글 내용
		List<WebElement> newsDateList = webDriver.findElements(By.className("u_cbox_date"));
		List<WebElement> newsCommentList = webDriver.findElements(By.className("u_cbox_contents"));
		
		// 댓글 입력
		int size = newsCommentList.size();
		for(int i = 0; i < size; i++){
			NewsComment newsComment = new NewsComment();
			newsComment.setDate(newsDateList.get(i).getText());
			newsComment.setComment(newsCommentList.get(i).getText().replaceAll("\n", ""));
			
			news.addComment(newsComment);
		}
		
		webDriver.close();
		
		/**
		 * 뉴스데이터를 map 으로 변환 및 반환
		 * **/
		return this.makeJsonObject(news);
	}
}
