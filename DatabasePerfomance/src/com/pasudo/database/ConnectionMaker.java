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
	public List<String[]> selectDatabase(String sortCase, int flag);
	
	
	// 데이터 베이스 데이터 추출 쿼리 실행 [ Basic ]
	public List<String[]> executeSelectQuery();
	
	
	// 데이터 베이스 데이터 추출 쿼리 실행 [ DOC_SEQ 컬럼에 대한 오름차순 & 내림차순 ]
	public List<String[]> executeSelectQueryBySortingOnDOC_SEQ(int flag);
	
	
	// 데이터 베이스 데이터 추출 쿼리 실행 [ TITLE 컬럼에 대한 오름차순 & 내림차순 ]
	public List<String[]> executeSelectQueryBySortingOnTITLE(int flag); 
	
	
	// 데이터 베이스 데이터 추출 쿼리 실행 [ REG_DT 컬럼에 대한 오름차순 & 내림차순 ]
	public List<String[]> executeSelectQueryBySortingOnREG_DT(int flag); 
	
	
	// 데이터 베이스 추출, resultSet Process (중복 쿼리 제거)
	public List<String[]> resultSetProcess(String query);
}
