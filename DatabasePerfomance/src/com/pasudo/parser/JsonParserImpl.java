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
	
	private static final String COLUMN_DOC_SEQ = "DOC_SEQ";
	private static final String COLUMN_TITLE = "TITLE";
	private static final String COLUMN_REG_DT = "REG_DT";
	
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
			fileWriter = new FileWriter("src/File/doc.json");
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
		
		// rowNumber
		int number = 1;
		
		for (String[] rowDatas : allRowsData) {
			
			jsonSubObject = new JSONObject();
			
			jsonSubObject.put(COLUMN_DOC_SEQ, rowDatas[0]);
			jsonSubObject.put(COLUMN_TITLE, rowDatas[1]);
			jsonSubObject.put(COLUMN_REG_DT, rowDatas[2]);
			
			jsonArray.add(jsonSubObject);

//			number++;
//			
//			if(number == 10)
//				break;
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
