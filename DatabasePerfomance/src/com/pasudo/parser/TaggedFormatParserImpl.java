package com.pasudo.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaggedFormatParserImpl implements ParserMaker{
	
	private BufferedWriter bufferedWriter = null;	// 파일 쓰기 위함
	private FileWriter fileWriter = null;
	
	private BufferedReader bufferedReader = null;	// 파일 읽기 위함
	private FileReader fileReader = null;	
	
	// Tagged Format 에 맞춘 형식
	private static final String DOC_START_TAG = "^[START]";
	private static final String DOC_COLUMN_DOC_SEQ = "[DOC_SEQ]";
	private static final String DOC_COLUMN_TITLE = "[TITLE]";
	private static final String DOC_COLUMN_REG_DT = "[REG_DT]";
	private static final String DOC_END_TAG = "^[END]";
	
	@Override
	public void settingReadParser() {
		try{
			fileReader = new FileReader("src/File/tagged_format.txt");
			bufferedReader = new BufferedReader(fileReader);
		}
		catch(IOException e){
			System.out.println("TaggedParserImpl : Reader IOException");
			e.printStackTrace();
		}
	}


	@Override
	public List<String[]> read() {
		// 데이터 읽기 이전에 세팅
		settingReadParser();
		
		List<String[]> allRowsData = new ArrayList<String[]>();
		String[] data = new String[3];
		String line = null;
		
		data[0] = DOC_COLUMN_DOC_SEQ;
		data[1] = DOC_COLUMN_TITLE;
		data[2] = DOC_COLUMN_REG_DT;
		allRowsData.add(data);
		data = new String[3];
		
		try {
			while((line = bufferedReader.readLine()) != null){
				
				if(line.equals(DOC_COLUMN_DOC_SEQ))
					data[0] = bufferedReader.readLine();
					
				if(line.equals(DOC_COLUMN_TITLE))
					data[1] = bufferedReader.readLine();
						
				if(line.equals(DOC_COLUMN_REG_DT)){
					data[2] = bufferedReader.readLine();
					allRowsData.add(data);
					data = new String[3];
				}
			}
		} catch (IOException e) {
			System.out.println("TaggedParserImpl : bufferdReader.readLine IOException");
			e.printStackTrace();
		}
		
		return allRowsData;
	}

	@Override
	public void settingWriteParser() {
		try {
			fileWriter = new FileWriter("src/File/tagged_format.txt");
			bufferedWriter = new BufferedWriter(fileWriter);
		} 
		catch (IOException e) {
			System.out.println("TaggedParserImpl : Writer IOException");
			e.printStackTrace();
		}
	}

	@Override
	public void write(List<String[]> allRowsData) {
		// 데이터를 쓰기 이전에 세팅
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
