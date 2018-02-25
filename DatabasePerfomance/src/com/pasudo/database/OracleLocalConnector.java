package com.pasudo.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pasudo.parser.EnumDocName;

public class OracleLocalConnector implements ConnectionMaker{
	
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	private String DOC_SEQ = EnumDocName.COLUMN_HEADER_DOC_SEQ.getName();
	private String TITLE = EnumDocName.COLUMN_HEADER_DOC_TITLE.getName();
	private String REG_DT = EnumDocName.COLUMN_HEADER_DOC_REG_DT.getName();
	
	// 기본 생성자
	public OracleLocalConnector(){
		connection = ConnectionMaker.decisionDatabase(this);
	}

	@Override
	public void insertDatabase(List<String[]> allRowsData) {
		int size = allRowsData.size();
		String query = null;
		int counter = 1;
		
		/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 
		 *  	     [ jdbc batch ]  
		 * 
		 *ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
		
		try{
			// 기본적으로 커밋의 자동수행을 false 로 변경한다.( 기본이 true 이기 때문에 )
			connection.setAutoCommit(false);
			query = QueryCollection.getInsertQuery();
			preparedStatement = connection.prepareStatement(query);
		} 
		catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		// 라인 : 0번째는 해당 칼럼의 헤더가 있기 때문에 생략 (TSV 기준)
		for(int line = 1; line < size; line++){
			String[]data = allRowsData.get(line);
			executeInsertQuery(data[0], data[1], data[2]);
			
			try {
				/** add PreparedStatement batch **/
				if(line % 1000 == 0){
					preparedStatement.executeBatch();
					connection.commit();
					
					System.out.println("Batch "+ (counter++) +" executed successfully");
					System.out.println(size + " / " + line);
				}
				
				// executeUpdate() 메소드 대신에 addBatch() 메소드 실행 
				// addBatch() : 쿼리와 파라미터들을 배치에 추가 이후 executeBatch() 실행
				preparedStatement.addBatch();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
				
		}// for
		
		// connection >> close
		try {
			/** Batch END **/
			// executeBatch() 메소드를 수행해서 한번에 쿼리를 수행한다.
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
	
	public void executeInsertQuery(String DOC_SEQ, String TITLE, String REG_DT){
		try {
			preparedStatement.setInt(1, Integer.parseInt(DOC_SEQ));
			preparedStatement.setString(2, TITLE);
			preparedStatement.setString(3, REG_DT);
			
//			preparedStatement.executeUpdate();
		} 
		catch (SQLException e) {
			System.out.println("Oracle [Local] Connector : SQLException");
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
		// 조회 쿼리
		String query = paramQuery;
		return resultSetProcess(query);
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
