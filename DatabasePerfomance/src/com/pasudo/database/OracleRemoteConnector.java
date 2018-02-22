package com.pasudo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
 * 
 * Batch Insert Example : https://www.boraji.com/jdbc-batch-insert-example
 * 
 *ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/

public class OracleRemoteConnector implements ConnectionMaker{

	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	
	/** 현재에는 쓰이지 않음 **/
//	private ResultSet resultSet = null;
//	private List<String[]> resultAllRowsData = null;
	
	public OracleRemoteConnector(){
		connection = ConnectionMaker.decisionDatabase(this);
	}

	@Override
	public void insertDatabase(List<String[]> allRowsData) {
		int size = allRowsData.size();
		
		/** Batch START  **/
		batchInsertDatabase();
		String query = "INSERT INTO PASUDO_DOC_COPY VALUES(?, ?, ?)";
		try {
			preparedStatement = connection.prepareStatement(query);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/** Batch START  **/
		
		// 라인 : 0번째는 해당 칼럼의 헤더가 있기 때문에 생략 (TSV 기준)
		for(int line = 1; line < size; line++){
			String[]data = allRowsData.get(line);
			executeInsertQuery(data[0], data[1], data[2]);

			if(line % 10000 == 0)
//				System.out.println(data[0] + ", " + data[1] + ", " +  data[2]);
				System.out.println(size + " / " + line);
			
			/** add PreparedStatement batch **/
			try {
				preparedStatement.addBatch();
				preparedStatement.clearParameters();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}// for
		
		
		// connection >> close
		try {
			/** Batch END **/
			preparedStatement.executeBatch();
			connection.commit();
			
			connection.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void batchInsertDatabase(){
		try {
			connection.setAutoCommit(false);
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
			
//			preparedStatement.executeUpdate();
		} 
		catch (SQLException e) {
			System.out.println("OracleLocalConnector : SQLException");
			System.out.println(DOC_SEQ + ", " + TITLE + ", " + REG_DT);
			e.printStackTrace();
		}
		finally{
//			// statement >> close
//			if(preparedStatement != null)
//				try {
//					preparedStatement.close();
//				} 
//				catch (SQLException e) {
//					e.printStackTrace();
//				}
		}
	}

	@Override
	public List<String[]> selectDatabase(String sortCase, int flag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String[]> executeSelectQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String[]> executeSelectQueryBySortingOnDOC_SEQ(int flag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String[]> executeSelectQueryBySortingOnTITLE(int flag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String[]> executeSelectQueryBySortingOnREG_DT(int flag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String[]> resultSetProcess(String query) {
		// TODO Auto-generated method stub
		return null;
	}

}
