package com.apple.login.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.apple.domain.User;
import com.apple.domain.UserDto;

@Repository
public class UserDaoImpl implements UserDao{
	
	@Autowired
	SqlSession sqlSession;
	private static final String NAME_SPACE = "com.apple.login.dao.UserDao";
	
	public UserDto getUser(User user){
		return sqlSession.selectOne(NAME_SPACE + ".getUser", user);
	}
}
