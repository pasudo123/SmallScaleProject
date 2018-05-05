package edu.doubler.preprocess.singer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;

import edu.doubler.preprocess.util.EnumOnPattern;

public class SingerExtractor {
	
	/**
	 * 
	 * 한달의 키워드 데이터들 (12개 파일) 을 하나의 파일로 합친다.
	 * [데이터베이스] 에 저장한다.
	 * 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 * 
	 * **/
	
	private static Connection connection;
	private static PreparedStatement ps;
	
	public static void main(String[]args) throws ClassNotFoundException, SQLException{
		String dbUrl = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
		String dbId = "doubler";
		String dbPw = "doublerpass";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connection = DriverManager.getConnection(dbUrl, dbId, dbPw);
		connection.setAutoCommit(false);
		
		SingerExtractor singerExt = new SingerExtractor();
		
		try {
			singerExt.readFile("C:\\Users\\Daumsoft\\Desktop\\다음소프트 과제\\11주차 과제\\회원테이블 예시\\SingerListOnMention");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		ps.executeBatch();
		connection.commit();
		ps.close();
		connection.close();
	}
	
	public void readFile(String parameterPath) throws IOException, SQLException{
		String path = parameterPath;
		File[]fileList = new File(path).listFiles();
		
		BufferedReader br = null;
		String inputLine = null;
		
		int count = 0;
		String query = "INSERT INTO KEYWORD_TB VALUES(?)";
		ps = connection.prepareStatement(query);
		
		for(File f : fileList){
			FileInputStream fis = new FileInputStream(f);
			br = new BufferedReader(new InputStreamReader(fis));
			
			System.out.println(f);
			
			while((inputLine = br.readLine()) != null){
				
				String keyword = inputLine.split("\t")[0];
				boolean bool = false;
				
				// 정규식 적용
				for(EnumOnPattern pattern : EnumOnPattern.values()){
					Matcher m = pattern.getPattern().matcher(keyword);
					if(m.find()){
						bool = true;
						break;
					}
				}
				
				// 패턴 확인
				// 물음표 존재 여부
				// 해당 키워드 존재 여부
				if(bool) continue;
				if(keyword.contains("?")) continue;
				
				try {
					count++;
					ps.setString(1, keyword);
					ps.addBatch();
					
					if(count % 10000 == 0){
						ps.executeBatch();
						connection.commit();
					}
				} 
				catch (SQLException e) {
					System.out.println(e.getMessage());
					System.out.println("넣다가 에러 발생해서 그냥 무시");
					continue;
				}
			}
		}
	}
}
