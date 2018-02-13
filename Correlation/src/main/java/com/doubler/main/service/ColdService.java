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
	
	
	// ㅡㅡㅡ (3) 진료건수와 [????] 에 따른 상관분석 ㅡㅡㅡ
	// (3-1) 습도
	public Map<String, List<Object>> correlationTreatmentAndMoisture();

	
	// 전체 데이터 카운트
	public int getCount();
	
	// ㅡㅡㅡ 상관계수를 구하기위한 데이터 평균 값들
	public int getAvgTwitterAndNews();		// 뉴스 트위터 언급량 평균
	public int getAvgTreament();			// 진료건수 평균
	public int getAvgLowestTemperature();	// 최저기온 평균
	public int getAvgDiurnalRange();		// 일교차 평균
	public int getAvgMoisture();			// 습도 평균
	
	// ㅡㅡㅡ 상관계수 구하는 메소드
	public Double setCorrelationCoefficient(Map<String, List<Object>> parameterMap);
	public Double getCorrelationCoefficient(Map<String, List<Object>> parameterMap, int xAvg, int yAvg);

	
	// ㅡㅡㅡ 오라클 내부에서 상관계수를 구해서 값을 획득하는 메소드
	public List<Object> getCorrelationSumSourceAndTreatment();
	public List<Object> getCorrelationSumSourceAndLowestTemperature();
	public List<Object> getCorrelationSumSourceAndDiurnalRange();
	
	public List<Object> getCorrelationLowestTemperatureAndTreatment();
	public List<Object> getCorrelationLowestTemperatureAndMoisture();
	public List<Object> getCorrelationTreatmentAndMoisture();
}
