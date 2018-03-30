package news_crawler.launcher;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import news_crawler.domain.News;
import news_crawler.domain.NewsComment;

/***************************************************************
 * 
 * 
 * 		reference : http://toolsqa.com/selenium-tutorial/
 * 
 * 
 ***************************************************************/
public class Launcher {
	
	
	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*
	 * [ 다음 뉴스  ]
	 * [ 네이버 뉴스  ]
	 * 
	 * 참고 ) 스포츠, 연애는 되지 않아서 따로 설정해주어야 한다.
	 *ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
	static final String newsURI = "http://news.naver.com/main/read.nhn?mode=LSD&mid=shm&sid1=100&oid=277&aid=0004207158";
	
	
	private static OutputStreamWriter osw = new OutputStreamWriter(System.out);
	private static BufferedWriter bw = new BufferedWriter(osw);
	
	public static void main(String[]args) throws IOException, InterruptedException{
		News news = new Factory().newsCrawling(newsURI);
		
		bw.append(news.getTitle() + "\n");
		bw.append(news.getContent() + "\n");

		List<NewsComment> newsList = news.getComment();
		bw.append(newsList.size() + "\n");
		
		for(int i = 0; i < newsList.size(); i++){
			bw.append(String.format("%s | %s", newsList.get(i).getDate(), newsList.get(i).getComment()));
			bw.append("\n");
		}
		
		bw.flush();
		bw.close();
	}
}
