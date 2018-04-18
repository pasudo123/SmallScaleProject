package com.doubler.login.dao;

import com.doubler.login.domain.User;

public interface LoginDao {
	public int checkUser(User user);
}
