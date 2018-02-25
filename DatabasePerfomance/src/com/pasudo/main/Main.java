package com.pasudo.main;

import com.pasudo.database.QueryCollection;
import com.pasudo.submain.Integration;
import com.pasudo.submain.IntegrationImpl;
import com.pasudo.submain.PerformTimer;

public class Main {
	private static Integration integration = null;
	private static PerformTimer performTimer = null;
	private static EnumParseFile TYPE_PARSE = null;
	private static EnumDatabase TYPE_DB = null;
	private static String tableName = null;
	
	
	// 데이터베이스와 파싱 객체를 담고있는 통합 객체 획득
	private static Integration getIntegration(){
		Integration integration = new IntegrationImpl();
		return integration;
	}
	
		
	public static void main(String[]args){
		integration = getIntegration();
		settingParseMaker();
		settingDB();
		
		//-- 타이머 생성 & 시간 측정 (시작)
		settingPerformTimer();
		performTimer.start();
		
		// INSERT 테이블 결정 & SELECT 테이블 결정
		// 해당 코드도 결국 QueryCollection 이 변경되면 변경될 수 밖에 없는 구조를 가지고 있다.
//		tableName = "PASUDO_DOC_COPY";
		QueryCollection.addTableNameOnInsert(tableName);
		QueryCollection.addTableNameOnSelect(tableName);
		
//		convertDatabase2File();
		convertFile2Database();
		
		//-- 시간 측정 (종료)
		performTimer.end();
	}
	
	private static void settingParseMaker(){
		/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 		  [ Parser 설정 ]
		 **ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
		TYPE_PARSE = EnumParseFile.TSV;
//		TYPE_PARSE = EnumParseFile.TAGGED_FORMAT;
//		TYPE_PARSE = EnumParseFile.JSON;
//		TYPE_PARSE = EnumParseFile.CSV;
		
		integration.setParseMaker(TYPE_PARSE);
	}
	
	private static void settingDB(){
		/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 *  [ Database Connector 설정 ]
		 **ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
		TYPE_DB = EnumDatabase.ORACLE_LOCAL;
//		TYPE_DB = EnumDatabase.ORACLE_REMOTE;
//		TYPE_DB = EnumDatabase.MYSQL_LOCAL;
		
		integration.setConnectorMaker(TYPE_DB);
	}
	
	
	private static void convertFile2Database(){
		/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 *     [ FILE >> DATABASE ]
		 **ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
		integration.file2Database();
	}
	
	// 데이터베이스를 파일로 변환
	private static void convertDatabase2File(){
		/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 *     [ DATABASE >> FILE ]
		 **ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
		
		/**
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 
		 * -- [ 정렬 기준  ] : DOC_SEQ // TITLE // REG_DT
		 * -- [ 오름차순 or 내림차순  ]
		 * 
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * **/
		
		integration.database2File("REG_DT", -1);
	}
	
	private static void settingPerformTimer(){
		performTimer = new PerformTimer();
	}
}
