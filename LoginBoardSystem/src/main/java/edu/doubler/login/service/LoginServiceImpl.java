package edu.doubler.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.doubler.login.dao.UserDAO;
import edu.doubler.login.domain.AppleUser;

@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	UserDAO userDao;
	
	@Override
	public boolean checkUser(AppleUser appleUser) {
		
		Integer count = (Integer) userDao.checkUser(appleUser);
		
		// 존재
		if(count == 1)
			return true;
		
		return false;
	}

}
