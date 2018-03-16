package com.apple.login.dao;

import com.apple.domain.User;
import com.apple.domain.UserDto;

public interface UserDao{
	
	public UserDto getUser(User user);
}
