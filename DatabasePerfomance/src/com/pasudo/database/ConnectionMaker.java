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
		if(connectionMaker instanceof OracleLocalConnector)
			return getConnectionOnOracle(EnumUserAccount.ORACLE_LOCAL.getID(), EnumUserAccount.ORACLE_LOCAL.getPW(), EnumUserAccount.ORACLE_LOCAL.getURL());
		
		// Oracle 원격 연결
		if(connectionMaker instanceof OracleRemoteConnector)
			return getConnectionOnOracle(EnumUserAccount.ORACLE_REMOTE.getID(), EnumUserAccount.ORACLE_REMOTE.getPW(), EnumUserAccount.ORACLE_REMOTE.getURL());
		
		// Mysql 로컬 연결
		if(connectionMaker instanceof MysqlLocalConnector)
			return null;
		
		return null;
	}
	
	// static method [ ORACLE ]
	static Connection getConnectionOnOracle(String user, String password, String url){
		
		Connection connection = null;

		try {
			// DB 연결
			Class.forName(EnumUserAccount.getClassForName(EnumUserAccount.ORACLE));
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
	static Connection getConnectionMySQL(String user, String password, String url){
		return null;
	}

	
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
