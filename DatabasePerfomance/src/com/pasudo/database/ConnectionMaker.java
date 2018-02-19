package com.pasudo.database;

import java.util.List;

public interface ConnectionMaker {
	
	// 데이터베이스 연결 및 Connection 객체 획득 (로컬)
	public void getConnetion(String user, String password, String url);
	
	// 데이터베이스 값 삽입
	public void insertDatabase(List<String[]> allRowsData);
	
	// 데이터베이스 데이터 삽입 쿼리 실행
	public void executeInsertQuery(String DOC_SEQ, String TITLE, String REG_DT);
	
	// 데이터베이스 값 획득
	public List<String[]> selectDatabase(int flag);
	
	// 데이터 베이스 데이터 추출 쿼리 실행
	public List<String[]> executeSelectQuery();
	
	// 데이터 베이스 데이터 추출 쿼리 실행 [오름차순(true) & 내림차순(false)]
	public List<String[]> executeSelectSortingQuery(int flag);
}
