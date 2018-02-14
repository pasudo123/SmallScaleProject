package com.pasudo.submain;

import java.util.List;

import com.pasudo.database.ConnectionMaker;
import com.pasudo.database.OracleLocalConnector;
import com.pasudo.parser.ParserMaker;
import com.pasudo.parser.TsvParserImpl;

public class Integration {
	// ConnectionMaker & ParseMaker 를 합침
	
	private ConnectionMaker connectionMaker = null;
	private ParserMaker parseMaker = null;

	// 오라클 로컬 커넥터 세팅 
	public void setOracleLocalConnector(){
		connectionMaker = new OracleLocalConnector();
	}
	
	// TSV 파싱 메이커 세팅
	public void setTsvParseMaker(){
		parseMaker = new TsvParserImpl();
	}
	
	// 데이터를 **로컬** 데이터베이스에 [ 삽입  ]
	public void inputDatabase(){
		// Oracle 로컬 연결
		if(connectionMaker instanceof OracleLocalConnector)
			connectionMaker.getConnetion("pasudo", "pasudopass", "jdbc:oracle:thin:@localhost:1521:xe");
		
		// MySQL 연결
		
		// Oracle 원격 연결
		
		
		// 파일 데이터 값 획득 및 데이터베이스 값 삽입
		List<String[]> allRowsData = parseMaker.read();
		connectionMaker.insertDatabase(allRowsData);
	}
	
	// 데이버베이스에서 데이터 [ 추출  ]
	public void outputDatabase(){
		// Oracle 로컬 연결
		if(connectionMaker instanceof OracleLocalConnector)
			connectionMaker.getConnetion("pasudo", "pasudopass", "jdbc:oracle:thin:@localhost:1521:xe");
		
		// MySQL 연결
		
		// Oracle 원격 연결
		
		List<String[]> allRowsData = connectionMaker.selectDatabase();
	}
	
	// -- GETTER --
	// ConnectionMaker 획득
	public ConnectionMaker getConnectionMaker() {
		return connectionMaker;
	}

	// -- GETTER --
	// ParseMaker 획득
	public ParserMaker getParseMaker() {
		return parseMaker;
	}
}
