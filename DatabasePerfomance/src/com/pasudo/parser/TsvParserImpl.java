package com.pasudo.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

public class TsvParserImpl implements ParserMaker{
	
	private TsvParserSettings settings = null;		// TSV 파일 셋팅 객체
	private TsvParser tsvParser = null;				// TSV 파서 객체

	private BufferedReader bufferedReader = null;	// 파일 읽기 위함
	private FileReader fileReader = null;			// 
	
	// 헤더 칼럼 명
	public static final String COLUMN_HEADER_DOC_SEQ = "DOC_SEQ";
	public static final String COLUMN_HEADER_TITLE = "TITLE";
	public static final String COLUMN_HEADER_REG_DT = "REF_DT";
	
	@Override
	public void settingParser() {
		settings = new TsvParserSettings();
		settings.getFormat().setLineSeparator("\r\n");		// 라인단위로 읽음
		tsvParser = new TsvParser(settings);
	}
	
	@Override
	public List<String[]> read() {
		// 파싱하기 이전에 세팅
		settingParser();
		
		try {
			fileReader = new FileReader("src/File/doc.tsv");
		}
		catch (FileNotFoundException e) {
			System.out.println("TsvParserImpl : FileNotFoundException");
			e.printStackTrace();
		}
		
		bufferedReader = new BufferedReader(fileReader);
		List<String[]> allRowsData = tsvParser.parseAll(bufferedReader);
		
		// List : 전체 라인의 수
		// String[] : 각각의 칼럼 0 ~ 2 까지 존재한다.
		return allRowsData;
	}

	@Override
	public void write(List<String[]> allRowsData) {
		
	}
}
