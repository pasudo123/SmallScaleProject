package edu.doubler.login.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.doubler.login.domain.AppleUser;

@Repository
public class UserDAOImpl implements UserDAO{

	@Autowired
	SqlSession sqlSession;
	private final String NAME_SPACE = "edu.doubler.login.dao.userDAO";
	
	@Override
	public Object checkUser(AppleUser appleUser) {
		return sqlSession.selectOne(NAME_SPACE + ".checkUser", appleUser);
	}
}
