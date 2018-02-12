package com.doubler.main.db;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ColdDAOImpl implements ColdDAO{
	private String nameSpace = "com.doubler.main.db.ColdDAO";
	
	@Autowired
	private SqlSession sqlSession = null;
	
	@Override
	public List<ColdDTO> getColdList() {
		return sqlSession.selectList(nameSpace + ".getColdList");
	}

	@Override
	public int getCount() {
		return sqlSession.selectOne(nameSpace + ".getCount");
	}
	
	@Override
	public int getAvgTwitterAndNews() {
		return sqlSession.selectOne(nameSpace + ".getAvgTwitterAndNews");
	}

	@Override
	public int getAvgTreament() {
		return sqlSession.selectOne(nameSpace + ".getAvgTreament");
	}

	@Override
	public int getAvgLowestTemperature() {
		return sqlSession.selectOne(nameSpace + ".getAvgLowestTemperature");
	}

	@Override
	public int getAvgDiurnalRange() {
		return sqlSession.selectOne(nameSpace + ".getAvgDiurnalRange");
	}

	@Override
	public int getAvgMoisture() {
		return sqlSession.selectOne(nameSpace + ".getAvgMoisture");
	}
}
