package com.pasudo.main;

import com.pasudo.submain.Integration;
import com.pasudo.submain.PerformTimer;

public class Main {
	private static Integration integration = null;
	private static PerformTimer performTimer = null;
	
	// 데이터베이스와 파싱 객체를 담고있는 통합 객체 획득
	private static Integration getIntegration(){
		Integration integration = new Integration();
		
		return integration;
	}
		
	public static void main(String[]args){
		integration = getIntegration();
//		settingIntegration(integration);
		
		//-- 타이머 생성 & 시간 측정 (시작)
		settingPerformTimer();
		performTimer.start();
		
//		convertDatabase2File(integration);
		convertFile2Database(integration);
		
		//-- 시간 측정 (종료)
		performTimer.end();
	}
	
	// 통합객체를 통해서 데이터베이스와 파서 세팅
	private static void settingIntegration(Integration integration){
		/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 *  [ Database Connector 설정 ]
		 **ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
//		integration.setOracleLocalConnector();
		integration.setOracleRemoteConnector();
		
		/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 		  [ Parser 설정 ]
		 **ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
//		integration.setCsvParserMaker();
//		integration.setJsonParseMaker();
//		integration.setTaggedFormatParseMaker();
		integration.setTsvParseMaker();
	}
	
	private static void settingPerformTimer(){
		performTimer = new PerformTimer();
	}
	
	// 파일을 데이터베이스로 변환
	private static void convertFile2Database(Integration integration){
		integration.inputDatabase();
	}
	
	// 데이터베이스를 파일로 변환
	private static void convertDatabase2File(Integration integration){
		
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
