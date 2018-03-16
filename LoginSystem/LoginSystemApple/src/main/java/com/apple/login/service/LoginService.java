package com.apple.login.service;

import com.apple.domain.User;
import com.apple.domain.UserDto;

public interface LoginService {
	
	// 뷰에서 획득한 유저정보로 계정 존재여부 확인 및 데이터 획득
	public UserDto getUser(User user);
}
