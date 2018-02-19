package com.pasudo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OracleLocalConnector implements ConnectionMaker{
	
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	@Override
	public void getConnetion(String user, String password, String url) {
		try {
			// DB 연결
			Class.forName("oracle.jdbc.driver.OracleDriver");
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
			
			getConnetion(_user, _pass, _url);
		}
	}

	@Override
	public void insertDatabase(List<String[]> allRowsData) {
		
		// 라인 : 0번째는 해당 칼럼의 헤더가 있기 때문에 생략 (TSV 기준)
		for(int line = 1; line < allRowsData.size(); line++){
			String[]data = allRowsData.get(line);
			executeInsertQuery(data[0], data[1], data[2]);
			
			if(line % 10000 == 0)
				System.out.println(data[0] + ", " + data[1] + ", " +  data[2]);
		}// for
		
		// connection >> close
		try {
			connection.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void executeInsertQuery(String DOC_SEQ, String TITLE, String REG_DT){
//		String query = "INSERT INTO PASUDO_DO VALUES(?, ?, ?)";
		String query = "INSERT INTO PASUDO_DOC_COPY VALUES(?, ?, ?)";
		
		try {
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setInt(1, Integer.parseInt(DOC_SEQ));
			preparedStatement.setString(2, TITLE);
			preparedStatement.setString(3, REG_DT);
			
			preparedStatement.executeUpdate();
		} 
		catch (SQLException e) {
			System.out.println("OracleLocalConnector : SQLException");
			System.out.println(DOC_SEQ + ", " + TITLE + ", " + REG_DT);
			System.out.println(query);
			e.printStackTrace();
		}
		finally{
			// statement >> close
			if(preparedStatement != null)
				try {
					preparedStatement.close();
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	@Override
	public List<String[]> selectDatabase(int flag) {
		if(flag == 1 || flag == -1)
			return executeSelectSortingQuery(flag);
		else
			return executeSelectQuery();
	}

	@Override
	public List<String[]> executeSelectQuery() {
		// 데이터 삽입 객체
		List<String[]> allRowsData = new ArrayList<String[]>();
				
		try {
			// 조회 쿼리
			String query = "SELECT * FROM PASUDO_DOC";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				String docSeq = String.valueOf(resultSet.getInt("DOC_SEQ"));
				String title = resultSet.getString("title");
				String registerDate = resultSet.getString("REG_DT");
				
				String[] rowDatas = {docSeq, title, registerDate};
				allRowsData.add(rowDatas);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return allRowsData;
	}

	@Override
	public List<String[]> executeSelectSortingQuery(int flag) {
		// 데이터 삽입 객체
		List<String[]> allRowsData = new ArrayList<String[]>();
		
		String sortingQuery = null;
		
		// +1 : 오름차순 (DOC_SEQ Column 기준)
		if(flag == 1)
			sortingQuery = "SELECT * FROM PASUDO_DOC ORDER BY DOC_SEQ ASC"; 
		// -1 : 내림차순 (DOC_SEQ Column 기준)
		if(flag == -1)
			sortingQuery = "SELECT * FROM PASUDO_DOC ORDER BY DOC_SEQ DESC";
		
		try {
			// 조회 쿼리
			preparedStatement = connection.prepareStatement(sortingQuery);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				String docSeq = String.valueOf(resultSet.getInt("DOC_SEQ"));
				String title = resultSet.getString("title");
				String registerDate = resultSet.getString("REG_DT");
				
				String[] rowDatas = {docSeq, title, registerDate};
				allRowsData.add(rowDatas);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return allRowsData;
	}
}
