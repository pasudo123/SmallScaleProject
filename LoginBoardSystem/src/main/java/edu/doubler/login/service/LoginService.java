package edu.doubler.login.service;

import edu.doubler.login.domain.AppleUser;

public interface LoginService {
	
	// id, pw 체크
	public boolean checkUser(AppleUser appleUser);
	
	// 로그인 인증
	// 로그인 결과
}
