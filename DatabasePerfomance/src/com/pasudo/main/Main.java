package com.pasudo.main;

import com.pasudo.submain.Integration;
import com.pasudo.submain.PerformTimer;

public class Main {
	private static Integration integration = null;
	private static PerformTimer performTimer = null;
	// private static final
	// 로컬(Oracle, MySQL)
	// 서버(Oracle)
	
	public static void main(String[]args){
		integration = getIntegration();
		settingIntegration(integration);
		settingPerformTimer();
		
		performTimer.start();
		
//		convertDatabase2File(integration);
		convertFile2Database(integration);
		
		performTimer.end();
	}
	
	// 데이터베이스와 파싱 객체를 담고있는 통합 객체 획득
	public static Integration getIntegration(){
		Integration integration = new Integration();
		
		return integration;
	}
	
	// 통합객체를 통해서 데이터베이스와 파서 세팅
	public static void settingIntegration(Integration integration){
		integration.setOracleLocalConnector();
		
//		integration.setJsonParseMaker();
		integration.setTaggedFormatParseMaker();
//		integration.setTsvParseMaker();
	}
	
	public static void settingPerformTimer(){
		performTimer = new PerformTimer();
	}
	
	// 파일을 데이터베이스로 변환
	public static void convertFile2Database(Integration integration){
		integration.inputDatabase();
	}
	
	// 데이터베이스를 파일로 변환
	public static void convertDatabase2File(Integration integration){
		
		/**
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 
		 * 오름차순 : +1
		 * 내림차순 : -1
		 * 그외경우 :  0
		 * 
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * **/
		
		integration.outputDatabase("REG_DT", -1);
	}
}
