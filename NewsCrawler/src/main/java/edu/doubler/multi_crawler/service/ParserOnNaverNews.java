package edu.doubler.multi_crawler.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;

import edu.doubler.single_crawler.domain.News;
import edu.doubler.single_crawler.domain.NewsComment;

public class ParserOnNaverNews {
	ChromeDriver webDriver;
	News news;
	
	public void crawling(ChromeDriver paramWebDriver, String name){
		this.webDriver = paramWebDriver;
		
		List<WebElement> links = webDriver.findElements(By.tagName("a"));
		List<String> rankUrlList = new ArrayList<String>();
		
		int count = 0;
		for(int i = 0; i < links.size(); i++){
			String rankingUrl = links.get(i).getAttribute("href");
			
			if(rankingUrl.contains("rankingSeq")){
				rankUrlList.add(rankingUrl);
				count++; // 실제 랭킹 1위 ~ 10위
				i++;
				
				if(count >= 10)
					break;
			}
		}
		
		for(int rank = 0; rank < rankUrlList.size(); rank++){
			// 해당 URL 로 이동 후 데이터 긁어오기
			webDriver.navigate().to(rankUrlList.get(rank));
			crawling();
			save(name, rank);
		}
	}
	
	// 데이터 긁어오기
	private void crawling(){
		
		/***************************************************************************/
		/**        내용과 별개로 댓글을 보기위한 새로운 페이지가 따로 존재하기 때문에 미리 값을 저장	  **/
		// WebElement : 제목, 내용
		WebElement newsTitleElement = webDriver.findElement(By.id("articleTitle"));
		WebElement newsContentElement = webDriver.findElement(By.id("articleBodyContents"));
		
		// 기사 제목(개행제거), 기사 내용
		String newsTitle = newsTitleElement.getText().replaceAll("\n", "");
		String newsContent = newsContentElement.getText().replaceAll("\n", "");;
		/***************************************************************************/
		
		
		/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 
		 * 				[ WebDriver 에서 명시적인 대기를 위함 - 반복적인 작업에 대한 설정 ]
		 * 
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
		FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(webDriver);
		fluentWait.withTimeout(Duration.ofMinutes(10));		// 인스턴스 조건을 기다리는 최대 시간 (10분 설정)
		
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
		};// commentViewFunction() 익명 클래스
		
		fluentWait.until(commentViewFunction);
		
		/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 
		 * 				뉴스 객체 
		 * 
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
		news = new News();
		
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
	}
	
	// 데이터 파일 저장
	private void save(String name, int rank){
		String fileName = name + "-" + (rank+1);
		
		File file = new File("C:\\CrawlingData\\" + fileName + ".txt");
		BufferedWriter bw = null;
		
		try {
			if(!file.exists())
				file.createNewFile();
			
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(news.getTitle());
			bw.write("\n");
			bw.write(news.getContent());
			bw.write("\n============================================\n");
			
			List<NewsComment> newsList = news.getComment();

			for(int i = 0; i < newsList.size(); i++){
				bw.write(newsList.get(i).getDate() + " | " + newsList.get(i).getComment());
				bw.write("\n");
				
				if(i % 100 == 0)
					bw.flush();
			}
			
			bw.flush();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try{
				bw.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
