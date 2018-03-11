package edu.doubler.login.dao;

import edu.doubler.login.domain.AppleUser;

public interface UserDAO {
	
	// id, pw 체크
	public Object checkUser(AppleUser appleUser);
}
