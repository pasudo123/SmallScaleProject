package com.doubler.main.db;

import java.util.List;

public interface ColdDAO {
	
	// 감기 데이터 조회
	public List<ColdDTO> getColdList();
	
	// 전체 데이터 카운트
	public int getCount();
	
	// ㅡㅡㅡ 상관계수를 구하기위한 데이터 평균 값들
	public int getAvgTwitterAndNews();		// 뉴스 트위터 언급량 평균
	public int getAvgTreament();			// 진료건수 평균
	public int getAvgLowestTemperature();	// 최저기온 평균
	public int getAvgDiurnalRange();		// 일교차 평균
	public int getAvgMoisture();			// 습도 평균
}
