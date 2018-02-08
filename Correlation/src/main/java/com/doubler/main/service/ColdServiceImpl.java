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
	
	@Override
	public List<ColdDTO> getColdList() {
		return coldDao.getColdList();
	}

	@Override
	public Map<String, List<Object>> correlationSumSourceAndTreatment() {
		List<ColdDTO> coldList = getColdList();
		
		daysSumSource = clear(daysSumSource);
		daysTreatment = clear(daysTreatment);
		
//		Integer minOfSumTwitterAndNews = coldDao.getMinSumTwitterAndNews();
//		Integer maxOfSumTwitterAndNews = coldDao.getMaxSumTwitterAndNews();
//		Integer minOfTreatment = coldDao.getMinTreatment();
//		Integer maxOfTreatment = coldDao.getMaxTreatment();
		
		// 트위터와 뉴스 언급량 X 진료건수
		for(ColdDTO coldDto : coldList){
//			Double xData = nomalization(coldDto.getSumTwitterAndNews(), minOfSumTwitterAndNews, maxOfSumTwitterAndNews);
//			Double yData = nomalization(coldDto.getTreatment(), minOfTreatment, maxOfTreatment);
			
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
		
//		Integer minOfSumTwitterAndNews = coldDao.getMinSumTwitterAndNews();
//		Integer maxOfSumTwitterAndNews = coldDao.getMaxSumTwitterAndNews();
//		Double minOfLowestTemperature = coldDao.getMinLowestTemperature();
//		Double maxOfLowestTemperature = coldDao.getMaxLowestTemperature();
		
		// 트위터와 뉴스 언급량 X 최저기온
		for(ColdDTO coldDto : coldList){
//			Double xData = nomalization(coldDto.getSumTwitterAndNews(), minOfSumTwitterAndNews, maxOfSumTwitterAndNews);
//			Double yData = nomalization(coldDto.getLowestTemperature(), minOfLowestTemperature, maxOfLowestTemperature);
			
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
		
//		Integer minOfSumTwitterAndNews = coldDao.getMinSumTwitterAndNews();
//		Integer maxOfSumTwitterAndNews = coldDao.getMaxSumTwitterAndNews();
//		Double minOfDiurnalRange = coldDao.getMinDiurnalRange();
//		Double maxOfDiurnalRange = coldDao.getMaxDiurnalRange();
		
		// 트위터와 뉴스 언급량 X 일교차
		for(ColdDTO coldDto : coldList){
//			Double xData = nomalization(coldDto.getSumTwitterAndNews(), minOfSumTwitterAndNews, maxOfSumTwitterAndNews);
//			Double yData = nomalization(coldDto.getDiuranalRange(), minOfDiurnalRange, maxOfDiurnalRange);
			
			Integer xData = coldDto.getSumTwitterAndNews();
			Double yData = coldDto.getDiuranalRange();
			
			daysSumSource.add(xData);
			daysDiurnalRange.add(yData);
		}
		
		parameterMap.put("x", daysSumSource);
		parameterMap.put("y", daysDiurnalRange);

		return parameterMap;
	}
	
	
	/**
	 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * 
	 * 		노멀라이징하고, 이후의 해당하는 값의 범주는 0 ~ 1 사이의 값을 가진다.
	 * 
	 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * **/
	@Override
	public Double nomalization(Integer value, Integer minValue, Integer maxValue){
		Double resultValue = ((double)(value - minValue) / (double)(maxValue - minValue));
//		System.out.println("Integer > " + resultValue);
		return (resultValue * 500);
	}
	
	@Override
	public Double nomalization(Double value, Double minValue, Double maxValue){
		Integer _minValue = (int) Math.round(minValue);
		Integer _maxValue = (int) Math.round(maxValue);
		Integer _value = (int) Math.round(value);
		Double resultValue = ((double)(_value - _minValue) / (double)(_maxValue - _minValue));
//		System.out.println("Double > " + resultValue);
		return (resultValue * 500);
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
