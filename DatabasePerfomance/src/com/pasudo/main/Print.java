package com.pasudo.main;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Print {
	
	private InputStreamReader inputStreamReader;
	private BufferedReader bufferedReader;
	private Map<String, Integer> resultMap;
	
	public void initPrint(){
		inputStreamReader = new InputStreamReader(System.in);
		bufferedReader = new BufferedReader(inputStreamReader);
		resultMap = new HashMap<String, Integer>();
	}
	
	public Map<String, Integer> start(){
		System.out.println("===== 파일 및 데이터베이스 입출력 =====");
		System.out.println("===== (1) DB 2 FILE | (2) FILE 2 DB =====");
		String content = null;
		int number = 0;
		
		try{
			number = Integer.parseInt(bufferedReader.readLine());
			
			switch(number){
				case 1:
					content = "===== DB 2 FILE 선택 =====";
					break;
				case 2:
					content = "===== FILE 2 DB 선택 =====";
					break;
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		resultMap.put("convert", number);
		return printChoice(content);
	}
	
	private Map<String, Integer> printChoice(String content){
		System.out.println(content);
		
		int dbChoice;
		int fileChoice;
		
		try{
			System.out.print("DB 선택 :\n1.Oracle(Local) | 2.Oracle(Remote) | 3.MySQL(Local) :: ");
			dbChoice = Integer.parseInt(bufferedReader.readLine());
			System.out.print("FILE 선택 :\n1.CSV | 2.TSV | 3.TaggedFormat | 4.JSON :: ");
			fileChoice = Integer.parseInt(bufferedReader.readLine());
			
			resultMap.put("db", dbChoice);
			resultMap.put("file", fileChoice);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		return resultMap;
	}
}
