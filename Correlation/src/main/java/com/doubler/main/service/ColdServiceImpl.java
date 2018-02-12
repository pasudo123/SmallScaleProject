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
	
	// 상관변수에 대한 상관계수 & 상관변수 리스트[0], [1] (value 가 List<Object> 타입이기 때문에)
	private Double correlationCoefficient = null;
	private List<Object> twoObjectList = new ArrayList<Object>();
	private List<Object> correlationCoefficientArray = new ArrayList<Object>();
	
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
		
		twoObjectList.clear();
		twoObjectList.add("daysSumSource");
		twoObjectList.add("daysTreatment");
		parameterMap.put("labelXAndY", twoObjectList);
		
		parameterMap.put("x", daysSumSource);
		parameterMap.put("y", daysTreatment);
		
		correlationCoefficient = setCorrelationCoefficient(parameterMap);
		correlationCoefficientArray.clear();
		correlationCoefficientArray.add(correlationCoefficient);
		parameterMap.put("correlationCoefficient", correlationCoefficientArray);
		
		
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
		
		twoObjectList.clear();
		twoObjectList.add("daysSumSource");
		twoObjectList.add("daysLowestTemperature");
		parameterMap.put("labelXAndY", twoObjectList);
		
		parameterMap.put("x", daysSumSource);
		parameterMap.put("y", daysLowestTemperature);
		
		correlationCoefficient = setCorrelationCoefficient(parameterMap);
		correlationCoefficientArray.clear();
		correlationCoefficientArray.add(correlationCoefficient);
		parameterMap.put("correlationCoefficient", correlationCoefficientArray);
		
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
			Double yData = coldDto.getDiurnalRange();
			
			daysSumSource.add(xData);
			daysDiurnalRange.add(yData);
		}
		
		twoObjectList.clear();
		twoObjectList.add("daysSumSource");
		twoObjectList.add("daysDiurnalRange");
		parameterMap.put("labelXAndY", twoObjectList);
		
		parameterMap.put("x", daysSumSource);
		parameterMap.put("y", daysDiurnalRange);
		
		correlationCoefficient = setCorrelationCoefficient(parameterMap);
		correlationCoefficientArray.clear();
		correlationCoefficientArray.add(correlationCoefficient);
		parameterMap.put("correlationCoefficient", correlationCoefficientArray);

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
		
		twoObjectList.clear();
		twoObjectList.add("daysLowestTemperature");
		twoObjectList.add("daysTreatment");
		parameterMap.put("labelXAndY", twoObjectList);
		
		parameterMap.put("x", daysLowestTemperature);
		parameterMap.put("y", daysTreatment);
		
		correlationCoefficient = setCorrelationCoefficient(parameterMap);
		correlationCoefficientArray.clear();
		correlationCoefficientArray.add(correlationCoefficient);
		parameterMap.put("correlationCoefficient", correlationCoefficientArray);
		
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
		
		twoObjectList.clear();
		twoObjectList.add("daysLowestTemperature");
		twoObjectList.add("daysMoisture");
		parameterMap.put("labelXAndY", twoObjectList);
		
		parameterMap.put("x", daysLowestTemperature);
		parameterMap.put("y", daysMoisture);
		
		correlationCoefficient = setCorrelationCoefficient(parameterMap);
		correlationCoefficientArray.clear();
		correlationCoefficientArray.add(correlationCoefficient);
		parameterMap.put("correlationCoefficient", correlationCoefficientArray);
		
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
		
		twoObjectList.clear();
		twoObjectList.add("daysLowestTemperature");
		twoObjectList.add("daysColdDate");
		parameterMap.put("labelXAndY", twoObjectList);
		
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

	@Override
	public int getAvgTwitterAndNews() {
		return coldDao.getAvgTwitterAndNews();
	}

	@Override
	public int getAvgTreament() {
		return coldDao.getAvgTreament();
	}

	@Override
	public int getAvgLowestTemperature() {
		return coldDao.getAvgLowestTemperature();
	}

	@Override
	public int getAvgDiurnalRange() {
		return coldDao.getAvgDiurnalRange();
	}

	@Override
	public int getAvgMoisture() {
		return coldDao.getAvgMoisture();
	}
	
	@Override
	public int getCount(){
		return coldDao.getCount();
	}
	
	@Override
	public Double setCorrelationCoefficient(Map<String, List<Object>> parameterMap){
		// labelXAndY 로 해당 파라미터 값을 받는다.
		List<Object> twoParameterList = parameterMap.get("labelXAndY");
		
		int xAvg = 0, yAvg = 0;
		
		// [ x 와 y 에 대한 평균 값 구하기 ]
		// x 변수 : 트위터 뉴스 언급량 혹은 최저기온 으로만 [현재는] 되어있음
		if(twoParameterList.get(0).equals("daysSumSource"))
			xAvg = getAvgTwitterAndNews();
		if(twoParameterList.get(0).equals("daysLowestTemperature"))
			xAvg = getAvgLowestTemperature();
		
		// y 변수 : 
		if(twoParameterList.get(1).equals("daysTreatment"))
			yAvg = getAvgTreament();
		if(twoParameterList.get(1).equals("daysLowestTemperature"))
			yAvg = getAvgLowestTemperature();
		if(twoParameterList.get(1).equals("daysDiurnalRange"))
			yAvg = getAvgDiurnalRange();
		if(twoParameterList.get(1).equals("daysTreatment"))
			yAvg = getAvgTreament();
		if(twoParameterList.get(1).equals("daysMoisture"))
			yAvg = getAvgMoisture();
		
		return getCorrelationCoefficient(parameterMap, xAvg, yAvg);
	}
	
	@Override
	public Double getCorrelationCoefficient(Map<String, List<Object>> parameterMap, int xAvg, int yAvg){
		int size = coldDao.getCount();
		
		double front = 0, back = 0;
		
		double covariance = 0;
		
		// (1) 공분산 구하기
		for(int i = 0; i < size; i++){
			// 각각의 데이터 변수 캐스팅
			if(parameterMap.get("x").get(i) instanceof Integer)
				front = (Integer) parameterMap.get("x").get(i);
			if(parameterMap.get("x").get(i) instanceof Double)
				front = (Double) parameterMap.get("x").get(i);
			
			if(parameterMap.get("y").get(i) instanceof Integer)
				back = (Integer) parameterMap.get("y").get(i);
			if(parameterMap.get("y").get(i) instanceof Double)
				back = (Double) parameterMap.get("y").get(i);
			
			front = front - xAvg;
			back = back - yAvg;
			
			// 선형결합
			covariance += (front * back);
		}
		
		System.out.println("공분산 >> " + covariance);
		
		double variableX = 0, variableY = 0;
		double variableXY = 0;
		
		// (2) x의 표준편차, y의 표준편차 
		for(int i = 0; i < size; i++){
			// 각각의 데이터 변수 캐스팅
			if(parameterMap.get("x").get(i) instanceof Integer)
				front = (Integer) parameterMap.get("x").get(i);
			if(parameterMap.get("x").get(i) instanceof Double)
				front = (Double) parameterMap.get("x").get(i);
			
			if(parameterMap.get("y").get(i) instanceof Integer)
				back = (Integer) parameterMap.get("y").get(i);
			if(parameterMap.get("y").get(i) instanceof Double)
				back = (Double) parameterMap.get("y").get(i);
			
			front = front - xAvg;
			back = back - yAvg;
			
			front = Math.pow(front, 2);
			back = Math.pow(back, 2);
			
			variableX += front;
			variableY += back;
		}
		
		variableXY = variableX * variableY;
		variableXY = Math.sqrt(variableXY);
		
		// 소수점 자르기
		return Double.parseDouble(String.format("%.3f", (covariance / variableXY)));
	}

}
