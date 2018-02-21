package com.pasudo.submain;

import java.util.List;

import com.pasudo.database.ConnectionMaker;
import com.pasudo.database.OracleLocalConnector;
import com.pasudo.database.OracleRemoteConnector;
import com.pasudo.parser.CsvParserImpl;
import com.pasudo.parser.JsonParserImpl;
import com.pasudo.parser.ParserMaker;
import com.pasudo.parser.TaggedFormatParserImpl;
import com.pasudo.parser.TsvParserImpl;

public class Integration {
	// ConnectionMaker & ParseMaker 를 합침
	private ConnectionMaker connectionMaker = null;
	private ParserMaker parseMaker = null;
	
	
	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * 
	 *				   [ Connector ]
	 *
	 * (1) Oracle (Local)
	 * (2) Oracle (Remote)
	 * (3) MySQL (Local)
	 * 
	 **ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
	// 오라클 로컬 커넥터 세팅 
	public void setOracleLocalConnector(){
		connectionMaker = new OracleLocalConnector();
	}
	
	// 오라클 원격 커넥터 세팅
	public void setOracleRemoteConnector(){
		connectionMaker = new OracleRemoteConnector();
	}
	
	
	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * 
	 *				    [ Parsing ] 
	 *
	 * (1) -- TSV
	 * (2) -- TaggedFormat
	 * (3) -- JSON
	 * (4) -- CSV
	 * 
	 **ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
	// TSV 파싱 메이커 세팅
	public void setTsvParseMaker(){
		parseMaker = new TsvParserImpl();
	}
	
	// TaggedFormat 파싱 메이커 세팅
	public void setTaggedFormatParseMaker(){
		parseMaker = new TaggedFormatParserImpl();
	}
	
	// JSON 파싱 메이커 세팅
	public void setJsonParseMaker(){
		parseMaker = new JsonParserImpl();
	}
	
	// CSV 파싱 메이커 세팅
	public void setCsvParserMaker(){
		parseMaker = new CsvParserImpl();
	}
	
	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * 
	 *		    [ DataBase, Input & Output ] 
	 *
	 * (1) Input
	 * (2) Output
	 * 
	 **ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
	// 파일의 데이터를 데이터베이스에 [ 삽입  ]
	public void inputDatabase(){
		// 파일 데이터 값 획득 및 데이터베이스 값 삽입
		List<String[]> allRowsData = parseMaker.read();
		connectionMaker.insertDatabase(allRowsData);
	}
	
	// 데이버베이스에서 데이터 [ 추출  ] 후 파일로 변환
	public void outputDatabase(String sortCase, int flag){
		List<String[]> allRowsData = connectionMaker.selectDatabase(sortCase, flag);
		parseMaker.write(allRowsData);
	}
}
