package edu.doubler.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CpcDaoImpl implements CpcDao{

	@Autowired
	private SqlSession sqlSession;
	
	// 맵퍼의 xml의 namespace를 연결하고자 하는 인터페이스의 풀 패키지 명으로 지정 --
	private static final String NAME_SPACE = "edu.doubler.dao.CpcDao.";
	private CpcDto cpcDto = new CpcDto();
	private CpcDtoString cpcDtoString = new CpcDtoString();
	
	@Override
	public List<CpcDto> selectCpcSection(){
		return sqlSession.selectList(NAME_SPACE + "selectCpcSection");
	}
	
	@Override
	public CpcDto selectCpcData(String code) {
		cpcDtoString.setStringVariable(code);
		
		return sqlSession.selectOne(NAME_SPACE + "selectCpcData", cpcDtoString);
	}
	
	@Override
	public List<CpcDto> selectClassBySection(String sectionName){
		cpcDtoString.setStringVariable(sectionName);
		
		// 섹션을 통해 클래스 추출
		// 전체 (*) 추출
		return sqlSession.selectList(NAME_SPACE + "selectClassBySection", cpcDtoString);
	}

	@Override
	public List<CpcDto> selectSubClassByClass(String className) {
		cpcDtoString.setStringVariable(className);
		
		// 클래스를 통해 서브클래스 추출
		// 전체 (*) 추출
		return sqlSession.selectList(NAME_SPACE + "selectSubClassByClass", cpcDtoString);
	}

	@Override
	public List<CpcDto> selectChildBySubClass(String subClassName) {
		cpcDtoString.setStringVariable(subClassName);
		
		// 서브 클래스를 통해 하위 목록 전체 추출
		// code 만 추출
		return sqlSession.selectList(NAME_SPACE + "selectChildBySubClass", cpcDtoString);
	}
}

