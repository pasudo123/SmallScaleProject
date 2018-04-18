package com.doubler.login.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.doubler.login.domain.User;

@Repository
public class LoginDaoImpl implements LoginDao{
	
	private static final String NAME_SPACE = "com.doubler.login";
	
	@Autowired
	SqlSession sqlSession;
	
	// User 존재 여부 반환
	public int checkUser(User user){
		return sqlSession.selectOne(NAME_SPACE + ".checkUser", user);
	}
	
}
