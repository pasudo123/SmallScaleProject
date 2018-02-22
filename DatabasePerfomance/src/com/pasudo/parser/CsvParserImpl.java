package com.pasudo.parser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;

/***
 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
 * 
 * 
 * reference : https://www.univocity.com/pages/parsers-tutorial
 * 
 * 
 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
 * ***/

public class CsvParserImpl implements ParserMaker{
	
	private CsvParserSettings csvParserSettings = null;	// CSV 읽기 파일 셋팅 객체
	private CsvParser csvParser = null;					// CSV 읽기 파서 객체
	
	private CsvWriterSettings csvWriterSettings = null;	// CSV 쓰기 파일 셋팅 객체	
	private CsvWriter csvWriter = null;					// CSV 쓰기 객체
	
	private BufferedWriter bufferedWriter = null;		// 파일 쓰기 위함
	private FileWriter fileWriter = null;
		
	@Override
	public void settingReadParser() {
		// TODO Auto-generated method stub
	}

	@Override
	public void settingWriteParser() {
		try {
			csvWriterSettings = new CsvWriterSettings();
			
			// 널 값에 대한 문자 시퀀스 설정
			csvWriterSettings.setNullValue("NULL");
			
			// Empty 값에 대한 문자 시퀀스 설정
			csvWriterSettings.setEmptyValue("Empty");
			
			// DB ㅡ> CSV (일반)
			fileWriter = new FileWriter("src/File/doc_csv.csv");
			bufferedWriter = new BufferedWriter(fileWriter);
			
			csvWriter = new CsvWriter(bufferedWriter, csvWriterSettings);
		} 
		catch (IOException e) {
			System.out.println("CsvParserImpl : IOException");
			e.printStackTrace();
		}

	}

	@Override
	public List<String[]> read() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void write(List<String[]> allRowsData) {
		settingWriteParser();
	
		csvWriter.writeHeaders(EnumDocName.COLUMN_HEADER_DOC_SEQ.getName(), EnumDocName.COLUMN_HEADER_DOC_TITLE.getName(), EnumDocName.COLUMN_HEADER_DOC_REG_DT.getName());
		
		for(String[]data : allRowsData){
			csvWriter.writeRow(data);
		}
		
		csvWriter.close();
	}
}
