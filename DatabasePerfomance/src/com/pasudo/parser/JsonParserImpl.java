package com.pasudo.parser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/***
 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
 * 
 * 
 * reference : http://huskdoll.tistory.com/38
 * 
 * 
 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
 * ***/

public class JsonParserImpl implements ParserMaker{
	
	private JSONObject jsonObject = null;
	private JSONObject jsonSubObject = null;
	private JSONArray jsonArray = null;
	
	private BufferedWriter bufferedWriter = null;	// 파일 쓰기 위함
	private FileWriter fileWriter = null;

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
			fileWriter = new FileWriter("src/File/json_REG_DT_DESC.json");
			bufferedWriter = new BufferedWriter(fileWriter);

			jsonArray = new JSONArray();
			jsonObject = new JSONObject();
		} 
		catch (IOException e) {
			System.out.println("TaggedParserImpl : IOException");
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void write(List<String[]> allRowsData) {
		// JSON 파싱하기 이전에 세팅
		settingWriteParser();		
		
		for (String[] rowDatas : allRowsData) {
			
			jsonSubObject = new JSONObject();
			
			jsonSubObject.put(EnumDocName.COLUMN_HEADER_DOC_SEQ.getName(), rowDatas[0]);
			jsonSubObject.put(EnumDocName.COLUMN_HEADER_DOC_TITLE.getName(), rowDatas[1]);
			jsonSubObject.put(EnumDocName.COLUMN_HEADER_DOC_REG_DT.getName(), rowDatas[2]);
			
			jsonArray.add(jsonSubObject);
		}// for
		
		// flush 및 close
		try {
			jsonObject.put("DOC", jsonArray);
			bufferedWriter.append(jsonObject.toJSONString());
			
			bufferedWriter.flush();
			fileWriter.flush();
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
