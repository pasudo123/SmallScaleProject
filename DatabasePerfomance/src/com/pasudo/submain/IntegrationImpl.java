package com.pasudo.submain;

import java.util.List;

import com.pasudo.database.ConnectionMaker;
import com.pasudo.database.MysqlLocalConnector;
import com.pasudo.database.OracleLocalConnector;
import com.pasudo.database.OracleRemoteConnector;
import com.pasudo.main.EnumDatabase;
import com.pasudo.main.EnumParseFile;
import com.pasudo.parser.CsvParserImpl;
import com.pasudo.parser.JsonParserImpl;
import com.pasudo.parser.ParserMaker;
import com.pasudo.parser.TaggedFormatParserImpl;
import com.pasudo.parser.TsvParserImpl;

public class IntegrationImpl implements Integration{
	// ConnectionMaker & ParseMaker 를 합침
	private ConnectionMaker connectionMaker = null;
	private ParserMaker parserMaker = null;
	
	
	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * 
	 *				   [ Connector ]
	 *
	 * (1) -- Oracle (Local)
	 * (2) -- Oracle (Remote)
	 * (3) -- MySQL (Local)
	 * 
	 **ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
	@Override
	public void setConnectorMaker(EnumDatabase database) {
		switch (database) {
		// 오라클 로컬 커넥터 세팅
		case ORACLE_LOCAL:
			connectionMaker = new OracleLocalConnector();
			break;

		// 오라클 원격 커넥터 세팅
		case ORACLE_REMOTE:
			connectionMaker = new OracleRemoteConnector();
			break;

		// MySQL 로컬 커넥터 세팅
		case MYSQL_LOCAL:
			connectionMaker = new MysqlLocalConnector();
		}
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
	@Override
	public void setParseMaker(EnumParseFile parseFile) {
		switch (parseFile) {
		// TSV 파싱 메이커 세팅
		case TSV: // TSV 파싱 메이커 세팅
			parserMaker = new TsvParserImpl();
			break;

		// TaggedFormat 파싱 메이커 세팅
		case TAGGED_FORMAT:
			parserMaker = new TaggedFormatParserImpl();
			break;

		// JSON 파싱 메이커 세팅
		case JSON:
			parserMaker = new JsonParserImpl();
			break;

		// CSV 파싱 메이커 세팅
		case CSV:
			parserMaker = new CsvParserImpl();
		}
		
		return;
	}

	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * 
	 *		   		[ FILE >> DATABASE ] 
	 *
	 **ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
	@Override
	public void file2Database() {
		List<String[]> allRowsData = parserMaker.read();
		connectionMaker.insertDatabase(allRowsData);
	}

	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * 
	 *		   		[ DATABASE >> FILE ] 
	 *
	 **ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
	@Override
	public void database2File(String standard, Integer order) {
		List<String[]> allRowsData = connectionMaker.selectDatabase(standard, order);
		parserMaker.write(allRowsData);
	}
}
