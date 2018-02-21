package com.pasudo.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OracleLocalConnector implements ConnectionMaker{
	
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private List<String[]> resultAllRowsData = null;
	
	public OracleLocalConnector(){
		connection = ConnectionMaker.decisionDatabase(this);
	}

	@Override
	public void insertDatabase(List<String[]> allRowsData) {
		int size = allRowsData.size();
		
		// 라인 : 0번째는 해당 칼럼의 헤더가 있기 때문에 생략 (TSV 기준)
		for(int line = 1; line < size; line++){
			String[]data = allRowsData.get(line);
			executeInsertQuery(data[0], data[1], data[2]);
			
			if(line % 10000 == 0)
//				System.out.println(data[0] + ", " + data[1] + ", " +  data[2]);
				System.out.println(size + " / " + line);
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
		String query = "INSERT INTO PASUDO_DOC VALUES(?, ?, ?)";
//		String query = "INSERT INTO PASUDO_DOC_COPY VALUES(?, ?, ?)";
		
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
	public List<String[]> selectDatabase(String sortCase, int flag) {
		switch(sortCase){
			case "DOC_SEQ":
				if(flag == 1 || flag == -1)
					resultAllRowsData = executeSelectQueryBySortingOnDOC_SEQ(flag);
				break;
				
			case "TITLE":
				if(flag == 1 || flag == -1)
					resultAllRowsData = executeSelectQueryBySortingOnTITLE(flag);
				break;
				
			case "REG_DT":
				if(flag == 1 || flag == -1)
					resultAllRowsData = executeSelectQueryBySortingOnREG_DT(flag);
				break;
				
			default:
				resultAllRowsData = executeSelectQuery();
		}
		return resultAllRowsData;
	}

	@Override
	public List<String[]> executeSelectQuery() {
		// 조회 쿼리
		String query = "SELECT * FROM PASUDO_DOC";
					
		return resultSetProcess(query);
	}

	@Override
	public List<String[]> executeSelectQueryBySortingOnDOC_SEQ(int flag) {
		String sortingQuery = null;
		
		// +1 : 오름차순 (DOC_SEQ Column 기준)
		if(flag == 1)
			sortingQuery = "SELECT * FROM PASUDO_DOC ORDER BY DOC_SEQ ASC"; 
		// -1 : 내림차순 (DOC_SEQ Column 기준)
		if(flag == -1)
			sortingQuery = "SELECT * FROM PASUDO_DOC ORDER BY DOC_SEQ DESC";
		
		return resultSetProcess(sortingQuery);
	}
	
	@Override
	public List<String[]> executeSelectQueryBySortingOnTITLE(int flag) {
		String sortingQuery = null;
		
		// +1 : 오름차순 (DOC_SEQ Column 기준)
		if(flag == 1)
			sortingQuery = "SELECT * FROM PASUDO_DOC ORDER BY TITLE ASC"; 
		// -1 : 내림차순 (DOC_SEQ Column 기준)
		if(flag == -1)
			sortingQuery = "SELECT * FROM PASUDO_DOC ORDER BY TITLE DESC";
		
		return resultSetProcess(sortingQuery);
	}
	
	@Override
	public List<String[]> executeSelectQueryBySortingOnREG_DT(int flag) {
		String sortingQuery = null;
		
		// +1 : 오름차순 (DOC_SEQ Column 기준)
		if(flag == 1)
			sortingQuery = "SELECT * FROM PASUDO_DOC ORDER BY REG_DT ASC"; 
		// -1 : 내림차순 (DOC_SEQ Column 기준)
		if(flag == -1)
			sortingQuery = "SELECT * FROM PASUDO_DOC ORDER BY REG_DT DESC";
		
		return resultSetProcess(sortingQuery);
	}

	@Override
	public List<String[]> resultSetProcess(String query) {
		// 데이터 삽입 객체
		List<String[]> allRowsData = new ArrayList<String[]>();
		
		try {
			// 조회 쿼리
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				String docSeq = String.valueOf(resultSet.getInt("DOC_SEQ"));
				String title = resultSet.getString("TITLE");
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
