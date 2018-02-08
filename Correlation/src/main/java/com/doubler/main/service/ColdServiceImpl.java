package com.doubler.main.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doubler.main.db.ColdDAO;
import com.doubler.main.db.ColdDTO;

@Service
public class ColdServiceImpl implements ColdService{
	
	@Autowired
	private ColdDAO coldDao;
	
	// 반환형태 
	private Map<String, List<Object>> parameterMap = new HashMap<String, List<Object>>();
	
	// 상관변수 구하기 위한 단일 변수 
	private List<Object> daysSumSource = null;
	private List<Object> daysTreatment = null;
	private List<Object> daysLowestTemperature = null;
	private List<Object> daysDiurnalRange = null;
	private List<Object> daysMoisture = null;
	private List<Object> daysColdDate = null;
	
	@Override
	public List<ColdDTO> getColdList() {
		return coldDao.getColdList();
	}

	@Override
	public Map<String, List<Object>> correlationSumSourceAndTreatment() {
		List<ColdDTO> coldList = getColdList();
		
		daysSumSource = clear(daysSumSource);
		daysTreatment = clear(daysTreatment);
		
		// 트위터와 뉴스 언급량 X 진료건수
		for(ColdDTO coldDto : coldList){
			Integer xData = coldDto.getSumTwitterAndNews();
			Integer yData = coldDto.getTreatment();
			
			daysSumSource.add(xData);
			daysTreatment.add(yData);
		}
		
		parameterMap.put("x", daysSumSource);
		parameterMap.put("y", daysTreatment);

		return parameterMap;
	}

	@Override
	public Map<String, List<Object>> correlationSumSourceAndLowestTemperature() {
		List<ColdDTO> coldList = getColdList();
		
		daysSumSource = clear(daysSumSource);
		daysLowestTemperature = clear(daysLowestTemperature);
		
		// 트위터와 뉴스 언급량 X 최저기온
		for(ColdDTO coldDto : coldList){
			Integer xData = coldDto.getSumTwitterAndNews();
			Double yData = coldDto.getLowestTemperature();
			
			daysSumSource.add(xData);
			daysLowestTemperature.add(yData);
		}
		
		parameterMap.put("x", daysSumSource);
		parameterMap.put("y", daysLowestTemperature);

		return parameterMap;
	}

	@Override
	public Map<String, List<Object>> correlationSumSourceAndDiurnalRange() {
		List<ColdDTO> coldList = getColdList();
		
		daysSumSource = clear(daysSumSource);
		daysDiurnalRange = clear(daysDiurnalRange);
		
		// 트위터와 뉴스 언급량 X 일교차
		for(ColdDTO coldDto : coldList){
			Integer xData = coldDto.getSumTwitterAndNews();
			Double yData = coldDto.getDiuranalRange();
			
			daysSumSource.add(xData);
			daysDiurnalRange.add(yData);
		}
		
		parameterMap.put("x", daysSumSource);
		parameterMap.put("y", daysDiurnalRange);

		return parameterMap;
	}
	
	@Override
	public Map<String, List<Object>> correlationLowestTemperatureAndTreatment() {
		List<ColdDTO> coldList = getColdList();
		
		daysLowestTemperature = clear(daysLowestTemperature);
		daysTreatment = clear(daysTreatment);
		
		// 최저기온 X 진료건수
		for(ColdDTO coldDto : coldList){
			Double xData = coldDto.getLowestTemperature();
			Integer yData = coldDto.getTreatment();
			
			daysLowestTemperature.add(xData);
			daysTreatment.add(yData);
		}
		
		parameterMap.put("x", daysLowestTemperature);
		parameterMap.put("y", daysTreatment);
		
		return parameterMap;
	}

	@Override
	public Map<String, List<Object>> correlationLowestTemperatureAndMoisture() {
		List<ColdDTO> coldList = getColdList();
		
		daysLowestTemperature = clear(daysLowestTemperature);
		daysMoisture = clear(daysMoisture);
		
		// 최저기온 X 습도
		for(ColdDTO coldDto : coldList){
			Double xData = coldDto.getLowestTemperature();
			Double yData = coldDto.getMoisture();
			
			daysLowestTemperature.add(xData);
			daysMoisture.add(yData);
		}
		
		parameterMap.put("x", daysLowestTemperature);
		parameterMap.put("y", daysMoisture);
		
		return parameterMap;
	}

	@Override
	public Map<String, List<Object>> correlationLowestTemperatureAndColdDate() {
		List<ColdDTO> coldList = getColdList();
		
		daysLowestTemperature = clear(daysLowestTemperature);
		daysColdDate = clear(daysColdDate);
		
		// 최저기온 X 날짜
		for(ColdDTO coldDto : coldList){
			Double xData = coldDto.getLowestTemperature();
			Integer yData = coldDto.getColdDate();
			
			daysLowestTemperature.add(xData);
			daysColdDate.add(yData);
		}
		
		parameterMap.put("x", daysLowestTemperature);
		parameterMap.put("y", daysColdDate);
		
		return parameterMap;
	}
	
	// List 초기화
	public List<Object> setList(List<Object> parameterList){
		parameterList = new ArrayList<Object>();
		return parameterList;
	}
	
	// List 클리어
	public List<Object> clear(List<Object> parameterList){
		if(parameterList == null){
			return setList(parameterList);
		}
		
		parameterList.clear();
		return parameterList;
	}

}
