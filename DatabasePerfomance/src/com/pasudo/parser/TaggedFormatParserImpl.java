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

	@Override
	public void settingReadParser() {
		try{
			fileReader = new FileReader("src/File/tagged_format_TITLE_ASC.txt");
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
		
		data[0] = EnumDocName.TAG_COLUMN_DOC_SEQ.getName();
		data[1] = EnumDocName.TAG_COLUMN_DOC_TITLE.getName();
		data[2] = EnumDocName.TAG_COLUMN_DOC_REG_DT.getName();
		allRowsData.add(data);
		data = new String[3];
		
		try {
			while((line = bufferedReader.readLine()) != null){
				
				if(line.equals(EnumDocName.TAG_COLUMN_DOC_SEQ.getName()))
					data[0] = bufferedReader.readLine();
					
				if(line.equals(EnumDocName.TAG_COLUMN_DOC_TITLE.getName()))
					data[1] = bufferedReader.readLine();
						
				if(line.equals(EnumDocName.TAG_COLUMN_DOC_REG_DT.getName())){
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
			fileWriter = new FileWriter("src/File/tagged_format_TITLE_DESC.txt");
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
				
				bufferedWriter.append(EnumDocName.TAG_COLUMN_START.getName());
				bufferedWriter.newLine();
				
				bufferedWriter.append(EnumDocName.TAG_COLUMN_DOC_SEQ.getName());
				bufferedWriter.newLine();
				bufferedWriter.append(rowDatas[0]);
				bufferedWriter.newLine();
				bufferedWriter.append(EnumDocName.TAG_COLUMN_DOC_TITLE.getName());
				bufferedWriter.newLine();
				bufferedWriter.append(rowDatas[1]);
				bufferedWriter.newLine();
				bufferedWriter.append(EnumDocName.TAG_COLUMN_DOC_REG_DT.getName());
				bufferedWriter.newLine();
				bufferedWriter.append(rowDatas[2]);
				bufferedWriter.newLine();
				
				bufferedWriter.append(EnumDocName.TAG_COLUMN_END.getName());
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
