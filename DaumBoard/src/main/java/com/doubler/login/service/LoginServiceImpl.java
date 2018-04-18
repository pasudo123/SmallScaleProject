package com.doubler.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doubler.login.dao.LoginDao;
import com.doubler.login.domain.User;

@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	LoginDao loginDao;
	
	public boolean checkUser(User user){
		int count = loginDao.checkUser(user);

		if(count == 1)
			return true;

		return false;
	}
}
