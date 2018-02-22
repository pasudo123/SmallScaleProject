package com.pasudo.database;

public class QueryCollection {
	private static String SELECT_QUERY = "SELECT * FROM ";
	private static String INSERT_QUERY = "INSERT INTO ";
	
	public static void addTableNameOnSelect(String tableName){
		clearSelectQuery();
		SELECT_QUERY = QueryCollection.SELECT_QUERY.concat(tableName + " ");
	}
	
	public static void addTableNameOnInsert(String tableName){
		clearInsertQuery();
		INSERT_QUERY = QueryCollection.INSERT_QUERY.concat(tableName + " VALUES(?, ?, ?)");
	}
	
	public static void addOrderByAscOnSelect(String standard){
		SELECT_QUERY = QueryCollection.SELECT_QUERY.concat("WHERE " + standard + " ORDER BY ASC");
	}
	
	public static void addOrderByDescOnSelect(String standard){
		SELECT_QUERY = QueryCollection.SELECT_QUERY.concat("WHERE " + standard + " ORDER BY DESC");
	}
	public static String getSelectQuery(){
		return SELECT_QUERY;
	}
	
	public static String getInsertQuery(){
		return INSERT_QUERY;
	}
	
	private static void clearSelectQuery(){
		SELECT_QUERY = "SELECT * FROM ";
	}
	
	private static void clearInsertQuery(){
		INSERT_QUERY = "INSERT INTO ";
	}
}
