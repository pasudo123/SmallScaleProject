package com.pasudo.main;

import java.util.Map;

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
		// 출력 내용
		Print print = new Print();
		print.initPrint();
		Map<String, Integer> resultMap = print.start();
		
		// 통합 객체 및 파싱 및 디비 세팅
		integration = getIntegration();
		settingParseMaker(resultMap.get("file"));
		settingDB(resultMap.get("db"));
		
		//-- 타이머 생성 & 시간 측정 (시작)
		settingPerformTimer();
		performTimer.start();
		
		// INSERT 테이블 결정 & SELECT 테이블 결정
		// 해당 코드도 결국 QueryCollection 이 변경되면 변경될 수 밖에 없는 구조를 가지고 있다.
		tableName = "PASUDO_DOC";
		QueryCollection.addTableNameOnInsert(tableName);
		QueryCollection.addTableNameOnSelect(tableName);

		if(resultMap.get("convert") == 1)
			convertDatabase2File();
		else
			convertFile2Database();
		
		//-- 시간 측정 (종료)
		performTimer.end();
	}
	
	private static void settingParseMaker(int choice){
		/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 		  [ Parser 설정 ]
		 **ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
		switch(choice){
		case 1:
			TYPE_PARSE = EnumParseFile.CSV;
			break;
		case 2:
			TYPE_PARSE = EnumParseFile.TSV;
			break;
		case 3:
			TYPE_PARSE = EnumParseFile.TAGGED_FORMAT;
			break;
		case 4:
			TYPE_PARSE = EnumParseFile.JSON;
			break;
		}
		
		integration.setParseMaker(TYPE_PARSE);
	}
	
	private static void settingDB(int choice){
		/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 *  [ Database Connector 설정 ]
		 **ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
		switch(choice){
		case 1:
			TYPE_DB = EnumDatabase.ORACLE_LOCAL;
			break;
		case 2:
			TYPE_DB = EnumDatabase.ORACLE_REMOTE;
			break;
		case 3:
			TYPE_DB = EnumDatabase.MYSQL_LOCAL;
			break;
		}
		
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
		 * -- [ 오름차순(1) or 내림차순(-1) or 일반(0) ]
		 * 
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * **/

		integration.database2File("DOC_SEQ", -1);
	}
	
	private static void settingPerformTimer(){
		performTimer = new PerformTimer();
	}
}
