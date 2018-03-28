package news_crawler.crawler;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import news_crawler.domain.News;
import news_crawler.domain.NewsComment;

public class NaverNewsCrawler implements Crawler{
	
	@Override
	public News parsingData(String uri) {
		System.setProperty(SYSTEM_PROPERTY, EXE_PATH);
		
		WebDriver webDriver = new ChromeDriver();
	
		webDriver.get(uri);
		
		sleepThread();
		
		News news = new News();
		
		WebElement newsTitleElement = webDriver.findElement(By.id("articleTitle"));
		WebElement newsContentElement = webDriver.findElement(By.id("articleBodyContents"));

		// 기사 제목, 기사 내용
		String newsTitle = newsTitleElement.getText();
		news.setTitle(newsTitle);
		String newsContent = newsContentElement.getText();
		news.setContent(newsContent);
				
		
		// [ 댓 글 더 보 기 ] 클릭
		WebElement viewMoreCommentElement = webDriver.findElement(By.className("u_cbox_in_view_comment"));
		viewMoreCommentElement.click();
		
		sleepThread();
		
		// [ 더 보 기 ] 계속 클릭
		while(!(webDriver.findElement(By.className("u_cbox_paginate")).getCssValue("display").equals("none"))){
			System.out.println(webDriver.findElement(By.className("u_cbox_paginate")).getCssValue("display"));
			viewMoreCommentElement = webDriver.findElement(By.className("u_cbox_paginate"));
			viewMoreCommentElement.click();
		}
		
		// 댓글 날짜, 댓글 내용
		List<WebElement> newsCommentList = webDriver.findElements(By.className("u_cbox_contents"));
		List<WebElement> newsDateList = webDriver.findElements(By.className("u_cbox_date"));
		
		// 댓글 입력
		int size = newsCommentList.size();
		for(int i = 0; i < size; i++){
			NewsComment newsComment = new NewsComment();
			newsComment.setDate(newsDateList.get(i).getText());
			newsComment.setComment(newsCommentList.get(i).getText());
			
			news.addComment(newsComment);
		}
		
		webDriver.close();
		
		return news;
	}
	
	private void sleepThread(){
		try {
			Thread.sleep(2000);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
