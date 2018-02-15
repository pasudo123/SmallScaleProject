package com.pasudo.parser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TaggedFormatParserImpl implements ParserMaker{
	
	private BufferedWriter bufferedWriter = null;	// 파일 쓰기 위함
	private FileWriter fileWriter = null;
	
	// Tagged Format 에 맞춘 형식
	private static final String DOC_START_TAG = "^[START]";
	private static final String DOC_COLUMN_DOC_SEQ = "[DOC_SEQ]";
	private static final String DOC_COLUMN_TITLE = "[TITLE]";
	private static final String DOC_COLUMN_REG_DT = "[REG_DT]";
	private static final String DOC_END_TAG = "^[END]";
	
	@Override
	public void settingReadParser() {
		return;
	}


	@Override
	public List<String[]> read() {
		return null;
	}

	@Override
	public void settingWriteParser() {
		try {
			fileWriter = new FileWriter("src/File/tagged_format.txt");
			bufferedWriter = new BufferedWriter(fileWriter);
		} 
		catch (IOException e) {
			System.out.println("TaggedParserImpl : IOException");
			e.printStackTrace();
		}
	}

	@Override
	public void write(List<String[]> allRowsData) {
		// 데이터를 쓰기이전에 세팅
		settingWriteParser();
		
		for(String[] rowDatas : allRowsData){
			try {
				
				bufferedWriter.append(DOC_START_TAG);
				bufferedWriter.newLine();
				bufferedWriter.append(DOC_COLUMN_DOC_SEQ);
				bufferedWriter.newLine();
				bufferedWriter.append(rowDatas[0]);
				bufferedWriter.newLine();
				bufferedWriter.append(DOC_COLUMN_TITLE);
				bufferedWriter.newLine();
				bufferedWriter.append(rowDatas[1]);
				bufferedWriter.newLine();
				bufferedWriter.append(DOC_COLUMN_REG_DT);
				bufferedWriter.newLine();
				bufferedWriter.append(rowDatas[2]);
				bufferedWriter.newLine();
				bufferedWriter.append(DOC_END_TAG);
				bufferedWriter.newLine();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
