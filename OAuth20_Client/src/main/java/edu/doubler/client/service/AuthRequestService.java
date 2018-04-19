package edu.doubler.client.service;

public interface AuthRequestService {
	
	// state 반환
	public String getState();
	
	// api url 에 파라미터 다 붙여서 전달 (인증)
	public String getApiFullUrlOnAuthorize();
	
	// api url 
	public String getApiFullUrlOnToken();
	
	// CSRF 검증
	public boolean verifyCSRF(String sessionState, String parameterState);
}
