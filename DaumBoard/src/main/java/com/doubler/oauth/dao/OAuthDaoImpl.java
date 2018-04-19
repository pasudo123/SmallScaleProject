package com.doubler.oauth.dao;

import java.util.HashMap;
import java.util.Map;

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

	@Override
	public int getClientInfo(String clientId, String clientSecret) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("clientId", clientId);
		map.put("clientSecret", clientSecret);
		
		System.out.println(map.toString());
		
		return sqlSession.selectOne(NAME_SPACE + ".getClientInfo", map);
	}
}
