package com.pasudo.parser;

import java.util.List;

public interface ParserMaker {
	
	// 파서 세팅
	public void settingParser();
	
	// 파일 데이터 읽기
	public List<String[]> read();
	
	// 파일 데이터 쓰기
	public void write(List<String[]> allRowsData);
	
	
	/***
	 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * 
	 * 	      파싱하는 데이터의 종류는 아래와 같다.
	 * (1) TaggedFormat (4주차 pt에 설명이 있다.)
	 * (2) TSV
	 * (3) CSV
	 * (4) JSON
	 * 
	 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 ***/
}
