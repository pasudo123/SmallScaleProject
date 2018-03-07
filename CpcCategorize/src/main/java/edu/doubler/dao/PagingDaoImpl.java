package edu.doubler.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.doubler.util.BoardPagingNumber;

@Repository
public class PagingDaoImpl implements PagingDao{

	@Autowired
	private SqlSession sqlSession;
	private static final String NAME_SPACE = "edu.douler.dao.PagingDao.";
	private Map<String, Object> parameterMap = new HashMap<String, Object>();
	
	@Override
	public int getDataCount(String subClassName) {
		CpcDtoString cpcDtoString = new CpcDtoString();
		cpcDtoString.setStringVariable(subClassName);
		
		return sqlSession.selectOne(NAME_SPACE + "getDataCount", cpcDtoString);
	}

	@Override
	public List<CpcDto> getCpcDataList(BoardPagingNumber boardPagingNumber, String subClassName) {

		parameterMap.put("startNumer", boardPagingNumber.getStartNum());
		parameterMap.put("endNumber", boardPagingNumber.getEndNum());
		parameterMap.put("keyword", subClassName);
		
		return sqlSession.selectList(NAME_SPACE + "getCpcDataList", parameterMap);
	}
}
