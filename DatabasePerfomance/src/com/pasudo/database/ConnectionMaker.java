package com.pasudo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public interface ConnectionMaker {	

	// static method [ DB 결정  ]
	static Connection decisionDatabase(ConnectionMaker connectionMaker){

		// Oracle 로컬 연결
		if(connectionMaker instanceof OracleLocalConnector){
			EnumUserAccount.ORACLE_LOCAL.setUserAccount();
			return getConnectionOnOracle(EnumUserAccount.ORACLE_LOCAL.getID(), EnumUserAccount.ORACLE_LOCAL.getPW(), EnumUserAccount.ORACLE_LOCAL.getURL());
		}
		// Oracle 원격 연결
		if(connectionMaker instanceof OracleRemoteConnector){
			EnumUserAccount.ORACLE_REMOTE.setUserAccount();
			return getConnectionOnOracle(EnumUserAccount.ORACLE_REMOTE.getID(), EnumUserAccount.ORACLE_REMOTE.getPW(), EnumUserAccount.ORACLE_REMOTE.getURL());
		}
		// Mysql 로컬 연결
		if(connectionMaker instanceof MysqlLocalConnector)
			EnumUserAccount.MYSQL_LOCAL.setUserAccount();
		
		return getConnectionOnMySQL(EnumUserAccount.MYSQL_LOCAL.getID(), EnumUserAccount.MYSQL_LOCAL.getPW(), EnumUserAccount.MYSQL_LOCAL.getMysqlBatchUrl());
//		return getConnectionOnMySQL(EnumUserAccount.MYSQL_LOCAL.getID(), EnumUserAccount.MYSQL_LOCAL.getPW(), EnumUserAccount.MYSQL_LOCAL.getURL());
	}
	
	// static method [ ORACLE ]
	static Connection getConnectionOnOracle(String user, String password, String url){
		
		Connection connection = null;

		try {
			// DB 연결
			EnumUserAccount.ORACLE.setClassForName();
			Class.forName(EnumUserAccount.ORACLE.getClassForName());
			connection = DriverManager.getConnection(url, user, password);
		} 
		catch (ClassNotFoundException e) {
			System.out.println("OracleConnector : ClassNotFound");
			e.printStackTrace();
		}
		catch (SQLException e){
			System.out.println("OracleConnector : SQLException");
			@SuppressWarnings("resource")
			Scanner inputLine = new Scanner(System.in);
			
			// SQL 에러 경우, 새롭게 user password, url을 새롭게 삽입한다.
			String _user = inputLine.next();
			String _pass = inputLine.next();
			String _url = inputLine.next();
			
			getConnectionOnOracle(_user, _pass, _url);
		}
		
		return connection;
	}
	
	// static method [ MYSQL ]
	static Connection getConnectionOnMySQL(String user, String password, String url){
		
		Connection connection = null;
		
		try{
			EnumUserAccount.MYSQL.setClassForName();
			Class.forName(EnumUserAccount.MYSQL.getClassForName());
			System.out.println(url);
			connection = DriverManager.getConnection(url, user, password);
		}
		catch (ClassNotFoundException e) {
			System.out.println("MysqlConnector : ClassNotFound");
			e.printStackTrace();
		}
		catch (SQLException e){
			System.out.println("MysqlConnector : SQLException");
			@SuppressWarnings("resource")
			Scanner inputLine = new Scanner(System.in);
			
			// SQL 에러 경우, 새롭게 user password, url을 새롭게 삽입한다.
			String _user = inputLine.next();
			String _pass = inputLine.next();
			String _url = inputLine.next();
			
			getConnectionOnOracle(_user, _pass, _url);
		}
		
		return connection;
	}
	
	
	// 데이터베이스 값 삽입
	public void insertDatabase(List<String[]> allRowsData);
	
	
	// 데이터베이스 데이터 삽입 쿼리 실행
	public void executeInsertQuery(String DOC_SEQ, String TITLE, String REG_DT);
	
	
	// 데이터베이스 값 획득
	public List<String[]> selectDatabase(String sortCase, Integer order);
	
	
	// 데이터 베이스 데이터 추출 쿼리 실행 
	public List<String[]> executeSelectQuery(String paramQuery);
	

	// resultSet Process 중복제거 위한 메소드
	public List<String[]> resultSetProcess(String query);
	
	
	/**  ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ   **/
}
