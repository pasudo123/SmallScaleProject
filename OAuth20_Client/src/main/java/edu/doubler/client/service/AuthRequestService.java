package edu.doubler.client.service;

import java.util.Map;

public interface AuthRequestService {
	
	// state 반환
	public String getState();
	
	// api url 에 파라미터 다 붙여서 전달 (인증)
	public String getApiFullUrlOnAuthorize();
	
	// CSRF 검증
	public boolean verifyCSRF(String sessionState, String parameterState);

	// URLConnection 을 통한 http 통신
	public Map<String, String> oauth20RequestToken();
}
