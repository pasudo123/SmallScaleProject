package com.doubler.oauth.service;

public interface ApiCallService {
	
	// 읽기 api 호출
	public String apiCallByRead();
	
	// 쓰기 api 호출
	public String apiCallByWrite(String name, String title, String content);
	
	// 삭제 api 호출
	public String apiCallByDelete(int contentNum);
}
