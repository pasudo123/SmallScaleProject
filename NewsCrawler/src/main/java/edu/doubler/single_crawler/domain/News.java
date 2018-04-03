package edu.doubler.single_crawler.domain;

import java.util.ArrayList;
import java.util.List;

/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
 * 뉴스
 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
public class News {
	private String title;						// 뉴스 제목
	private String content;						// 뉴스 내용
	private List<NewsComment> commentList; 		// 뉴스 댓글
	
	public News(){
		this.commentList = new ArrayList<NewsComment>();
	}
	
	//-- add News Comment
	public void addComment(NewsComment newsComment){
		commentList.add(newsComment);
	}
	
	public List<NewsComment> getComment(){
		return commentList;
	}

	//-- getter() & setter()
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
