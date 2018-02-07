package com.doubler.main.db;

import java.util.List;

public interface ColdDAO {
	
	// 감기 데이터 조회
	public List<ColdDTO> getColdList();
	
	
	// 언급 수, 최대값 최소값 조회
	public Integer getMaxSumTwitterAndNews();
	public Integer getMinSumTwitterAndNews();
	
	
	// 진료건수, 최대값 최소값 조회
	public Integer getMaxTreatment();
	public Integer getMinTreatment();
	
	
	// 최저기온, 최대값 최소값 조회
	public Double getMaxLowestTemperature();
	public Double getMinLowestTemperature();
	
	
	// 일교차, 최대값 최소값 조회
	public Double getMaxDiurnalRange();
	public Double getMinDiurnalRange();
}
