package edu.doubler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.doubler.dao.CpcDao;
import edu.doubler.dao.CpcDto;
import edu.doubler.domain.EnumSection;

@Service
public class CpcServiceImpl implements CpcService{

	@Autowired
	private CpcDao cpcDao;
	private List<CpcDto> sectionData = null;
	private List<CpcDto> classData = null;
	private List<CpcDto> subClassData = null;

	@Override
	public List<CpcDto> selectCpcSection() {
		/**
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 
		 * 전체 CPC 섹션 획득
		 * 
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 **/
		
		return cpcDao.selectCpcSection();
	}

	
	@Override
	public CpcDto selectCpcData(String code) {
		/**
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 
		 * 코드를 통해 CPC 데이터 획득
		 * 
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 **/
		return cpcDao.selectCpcData(code);
	}

	
	@Override
	public List<CpcDto> selectClassBySection(String sectionName) {
		/**
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 
		 * 섹션을 통해서 클래스 내용을 추출
		 * 
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 **/

		EnumSection[] enumSection = EnumSection.values();
		for(EnumSection sectionType : enumSection){
			if(sectionType.getSectionName().equals(sectionName)){
				sectionData = cpcDao.selectClassBySection(sectionName);
				
				return sectionData;
			}
		}
		
		return null;
	}

	@Override
	public List<CpcDto> selectSubClassByClass(String className) {
		/**
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 
		 * 클래스를 통해서 서브 클래스 추출
		 * 
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 **/
		
		classData = cpcDao.selectSubClassByClass(className);

		return classData;
	}

	@Override
	public List<CpcDto> selectChildBySubClass(String subClassName) {
		/**
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 
		 * 서브 클래스를 통해 하위 목록 추출
		 * 
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 **/
		
		subClassData = cpcDao.selectChildBySubClass(subClassName);
		
		return subClassData;
		
	}
}
