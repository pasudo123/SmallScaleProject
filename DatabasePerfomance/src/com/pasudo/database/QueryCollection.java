package com.pasudo.database;

import javax.management.Query;

public class QueryCollection {
	private static String SELECT_QUERY = "SELECT * FROM ";
	private static String INSERT_QUERY = "INSERT INTO ";
	private static String SELECT_HINT_QUERY = "SELECT /*+ INDEX(";
	
	// Insert 구문, 테이블 이름 설정
	public static void addTableNameOnInsert(String tableName){
		clearInsertQuery();
		INSERT_QUERY = QueryCollection.INSERT_QUERY.concat(tableName + " VALUES(?, ?, ?)");
	}
	
	// Select 구문, 테이블 이름 설정
	public static void addTableNameOnSelect(String tableName){
		clearSelectQuery();
		clearSelectHintQuery();
		
		SELECT_QUERY = QueryCollection.SELECT_QUERY.concat(tableName + " ");
		SELECT_HINT_QUERY = QueryCollection.SELECT_HINT_QUERY.concat(tableName + ") */ * FROM " + tableName + " ");
	}
	
	// Select 구문, 오름차순
	public static void addOrderByAscOnSelect(String standard){
		SELECT_QUERY = QueryCollection.SELECT_QUERY.concat("ORDER BY " + standard + " ASC");
	}
	
	// Select 구문, 내림차순
	public static void addOrderByDescOnSelect(String standard){
		SELECT_QUERY = QueryCollection.SELECT_QUERY.concat("ORDER BY " + standard + " DESC");
	}
	
	// Select 힌트 구문, 오름차순
	public static void addOrderByAscOnHintSelect(String standard){
		SELECT_QUERY = QueryCollection.SELECT_HINT_QUERY.concat("ORDER BY " + standard + " ASC");
	}
	
	// Select 힌트 구문, 내림차순
	public static void addOrderByDescOnHintSelect(String standard){
		SELECT_QUERY = QueryCollection.SELECT_HINT_QUERY.concat("ORDER BY " + standard + " ASC");
	}
	
	// Select 쿼리 구문 획득
	public static String getSelectQuery(){
		return SELECT_QUERY;
	}
	
	// Select 힌트 쿼리 구문 획득
	public static String getSelectHintQuery(){
		return SELECT_HINT_QUERY;
	}
	
	// Insert 쿼리 구문 획득
	public static String getInsertQuery(){
		return INSERT_QUERY;
	}
	
	private static void clearSelectQuery(){
		SELECT_QUERY = "SELECT * FROM ";
	}
	
	private static void clearInsertQuery(){
		INSERT_QUERY = "INSERT INTO ";
	}
	
	private static void clearSelectHintQuery(){
		SELECT_HINT_QUERY = "SELECT /*+ INDEX(";
	}
}
