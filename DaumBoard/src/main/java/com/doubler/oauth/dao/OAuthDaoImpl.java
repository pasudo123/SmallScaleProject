package com.doubler.oauth.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OAuthDaoImpl implements OAuthDao{

	private static final String NAME_SPACE = "com.doubler.oauth";
	
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public String getServiceURL(String clientId) {
		return sqlSession.selectOne(NAME_SPACE + ".checkServiceUrl", clientId);
	}

}
