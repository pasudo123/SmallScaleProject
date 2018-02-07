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
	public Integer getMaxSumTwitterAndNews() {
		return sqlSession.selectOne(nameSpace + ".getMaxSumTwitterAndNews");
	}

	@Override
	public Integer getMinSumTwitterAndNews() {
		return sqlSession.selectOne(nameSpace + ".getMinSumTwitterAndNews");
	}

	@Override
	public Double getMaxLowestTemperature() {
		return sqlSession.selectOne(nameSpace + ".getMaxLowestTemperature");
	}

	@Override
	public Double getMinLowestTemperature() {
		return sqlSession.selectOne(nameSpace + ".getMinLowestTemperature");
	}

	@Override
	public Double getMaxDiurnalRange() {
		return sqlSession.selectOne(nameSpace + ".getMaxDiurnalRange");
	}

	@Override
	public Double getMinDiurnalRange() {
		return sqlSession.selectOne(nameSpace + ".getMinDiurnalRange");
	}

	@Override
	public Integer getMaxTreatment() {
		return sqlSession.selectOne(nameSpace + ".getMaxTreatment");
	}

	@Override
	public Integer getMinTreatment() {
		return sqlSession.selectOne(nameSpace + ".getMinTreatment");
	}
}
