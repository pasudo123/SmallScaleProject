package edu.doubler.dao;

import java.util.List;

public interface CpcDao {
	
	// [ 전체 CPC 섹션 획득  ]
	public List<CpcDto> selectCpcSection();
	
	// [ 해당 코드를 통해 CPC 데이터 획득  ]
	public CpcDto selectCpcData(String code);
	
	// [ 섹션을 통해 클래스 획득  ]
	public List<CpcDto> selectClassBySection(String sectionName);
	
	// [ 클래스를 통해 서브 클래스 획득 ]
	public List<CpcDto> selectSubClassByClass(String className);
	
	// [ 서브 클래스를 통해 하위 목록 회득 ]
	public List<CpcDto> selectChildBySubClass(String subClassName);
}
