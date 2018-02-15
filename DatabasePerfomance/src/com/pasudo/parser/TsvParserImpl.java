package com.pasudo.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import com.univocity.parsers.tsv.TsvWriter;
import com.univocity.parsers.tsv.TsvWriterSettings;

/***
 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
 * 
 * 
 * reference : https://www.univocity.com/pages/parsers-tutorial
 * 
 * 
 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
 * ***/

public class TsvParserImpl implements ParserMaker{
	
	private TsvParserSettings parserSettings = null;	// TSV 파일 셋팅 객체
	private TsvParser tsvParser = null;					// TSV 파서 객체
	
	private TsvWriterSettings writerSettings = null;	
	private TsvWriter tsvWriter = null;
	
	private BufferedReader bufferedReader = null;	// 파일 읽기 위함
	private FileReader fileReader = null;			// 
	
	private BufferedWriter bufferedWriter = null;	// 파일 쓰기 위함
	private FileWriter fileWriter = null;
	
	// 헤더 칼럼 명
	public static final String COLUMN_HEADER_DOC_SEQ = "DOC_SEQ";
	public static final String COLUMN_HEADER_TITLE = "TITLE";
	public static final String COLUMN_HEADER_REG_DT = "REG_DT";
	
	@Override
	public void settingReadParser() {
		parserSettings = new TsvParserSettings();
		parserSettings.getFormat().setLineSeparator("\r\n");		// 라인단위로 읽음
		tsvParser = new TsvParser(parserSettings);
	}
	
	@Override
	public List<String[]> read() {
		// 데이터 읽기전에 파서 세팅
		settingReadParser();
		
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
	public void settingWriteParser() {
		try {
			writerSettings = new TsvWriterSettings();
			writerSettings.getFormat().setLineSeparator("\r\n");
			
			fileWriter = new FileWriter("src/File/doc_copy.tsv");
			bufferedWriter = new BufferedWriter(fileWriter);
			
			tsvWriter = new TsvWriter(bufferedWriter, writerSettings);
		} 
		catch (IOException e) {
			System.out.println("TsvParserImpl : IOException");
			e.printStackTrace();
		}
	}
	
	@Override
	public void write(List<String[]> allRowsData) {
		// 데이터 쓰기 전에 파서 세팅
		settingWriteParser();
		
		// 헤더 삽입
		tsvWriter.writeHeaders(COLUMN_HEADER_DOC_SEQ, COLUMN_HEADER_TITLE, COLUMN_HEADER_REG_DT);
		
		for(int i = 0; i < allRowsData.size(); i++){
			tsvWriter.writeRow(allRowsData.get(i));
		}
		
		tsvWriter.close();
	}
}
