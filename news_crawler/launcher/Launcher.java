package news_crawler.launcher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import news_crawler.domain.News;
import news_crawler.domain.NewsComment;

public class Launcher {
	
	static final String newsURI = "http://news.naver.com/main/read.nhn?mode=LSD&mid=shm&sid1=100&oid=421&aid=0003284907";
	
	/***************************************************************
	 * 
	 * 
	 * 		reference : http://toolsqa.com/selenium-tutorial/
	 * 
	 * 
	 ***************************************************************/
	
	private static OutputStreamWriter osw = new OutputStreamWriter(System.out);
	private static BufferedWriter bw = new BufferedWriter(osw);

	public static void main(String[]args) throws IOException, InterruptedException{
		News news = new Factory().newsCrawling(newsURI);
		
		bw.append(news.getTitle() + "\n");
		bw.append(news.getContent() + "\n");

		List<NewsComment> newsList = news.getComment();
		
		for(int i = 0; i < newsList.size(); i++){
			bw.append(newsList.get(i).getDate() + " | " + newsList.get(i).getComment());
			bw.append("\n");
		}
		
		bw.flush();
		bw.close();
	}
}
