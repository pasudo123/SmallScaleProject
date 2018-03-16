package com.apple.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apple.domain.User;
import com.apple.domain.UserDto;
import com.apple.login.dao.UserDao;

@Service
public class LoginServiceImpl implements LoginService{

	@Autowired 
	UserDao userDao;
	
	@Override
	public UserDto getUser(User user) {
		return userDao.getUser(user);
	}

}
