package com.pasudo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pasudo.parser.EnumDocName;


/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
 * 
 * Batch Insert Example : https://www.boraji.com/jdbc-batch-insert-example
 * 
 *ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/

public class OracleRemoteConnector implements ConnectionMaker{

	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	private String DOC_SEQ = EnumDocName.COLUMN_HEADER_DOC_SEQ.getName();
	private String TITLE = EnumDocName.COLUMN_HEADER_DOC_TITLE.getName();
	private String REG_DT = EnumDocName.COLUMN_HEADER_DOC_REG_DT.getName();
	
	public OracleRemoteConnector(){
		connection = ConnectionMaker.decisionDatabase(this);
	}

	@Override
	public void insertDatabase(List<String[]> allRowsData) {
		int size = allRowsData.size();
		String query = null;
		
		/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 
		 *  	     [ jdbc batch ]  
		 * 
		 *ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
		
		try{
			connection.setAutoCommit(false);
			query = QueryCollection.getInsertQuery();
			preparedStatement = connection.prepareStatement(query);
		} 
		catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		/** add PreparedStatement batch **/
		// 라인 : 0번째는 해당 칼럼의 헤더가 있기 때문에 생략 (TSV 기준)
		for(int line = 1; line < size; line++){
			String[]data = allRowsData.get(line);
			executeInsertQuery(data[0], data[1], data[2]);

			if(line % 10000 == 0)
				System.out.println(size + " / " + line);
			
			/** add PreparedStatement batch **/
			try {
				preparedStatement.addBatch();
				preparedStatement.clearParameters();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}// for
		
		
		try {
			/** Batch END **/
			preparedStatement.executeBatch();
			connection.commit();
			/** Batch END **/
			
			preparedStatement.close();
			connection.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void executeInsertQuery(String DOC_SEQ, String TITLE, String REG_DT) {
		try {
			preparedStatement.setInt(1, Integer.parseInt(DOC_SEQ));
			preparedStatement.setString(2, TITLE);
			preparedStatement.setString(3, REG_DT);
		} 
		catch (SQLException e) {
			System.out.println("Oracle [Remote] Connector : SQLException");
			System.out.println(DOC_SEQ + ", " + TITLE + ", " + REG_DT);
			e.printStackTrace();
		}
	}

	@Override
	public List<String[]> selectDatabase(String sortCase, Integer order) {
		if(sortCase.equals(DOC_SEQ) || sortCase.equals(TITLE) || sortCase.equals(REG_DT)){
			if(order ==  1)
				QueryCollection.addOrderByAscOnSelect(sortCase);
			if(order == -1)
				QueryCollection.addOrderByDescOnSelect(sortCase);
		}
			
		return executeSelectQuery(QueryCollection.getSelectQuery());
	}

	@Override
	public List<String[]> executeSelectQuery(String paramQuery) {
		String query = paramQuery;
		return resultSetProcess(query);
	}

	@Override
	public List<String[]> resultSetProcess(String query) {
		List<String[]> allRowsData = new ArrayList<String[]>();
		
		try {
			// 조회 쿼리
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				String docSeq = String.valueOf(resultSet.getInt(DOC_SEQ));
				String title = resultSet.getString(TITLE);
				String registerDate = resultSet.getString(REG_DT);
				
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
