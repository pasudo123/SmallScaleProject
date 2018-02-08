package com.doubler.main.service;

import java.util.List;
import java.util.Map;

import com.doubler.main.db.ColdDTO;

public interface ColdService {
	
	// 감기관련 데이터 조회
	public List<ColdDTO> getColdList();
	
	// ㅡㅡㅡ (1) 트위터와 뉴스 언급량과 [????] 에 따른 상관분석 ㅡㅡㅡ
	// (1-1) 진료건수
	public Map<String, List<Object>> correlationSumSourceAndTreatment();
	
	// (1-2) 최저기온 
	public Map<String, List<Object>> correlationSumSourceAndLowestTemperature();
	
	// (1-3) 일교차
	public Map<String, List<Object>> correlationSumSourceAndDiurnalRange();
	
	
	// ㅡㅡㅡ (2) 최저기온과 [????] 에 따른 상관분석 ㅡㅡㅡ
	// (2-1) 진료건수
	public Map<String, List<Object>> correlationLowestTemperatureAndTreatment();
	
	// (2-2) 습도
	public Map<String, List<Object>> correlationLowestTemperatureAndMoisture();
	
	// (2-3) 날짜
	public Map<String, List<Object>> correlationLowestTemperatureAndColdDate();
}
