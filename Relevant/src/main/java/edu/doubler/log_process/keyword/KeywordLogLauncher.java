package edu.doubler.log_process.keyword;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;

import edu.doubler.log_process.util.LogPathEnum;
import edu.doubler.preprocess.database.JdbcConnector;

public class KeywordLogLauncher {
	
	private ExecutorService executorService;
	private Connection connection;
	public static Set<String> keywordSet;
	
	public static void main(String[]args){

		KeywordLogLauncher logLauncher = new KeywordLogLauncher();
		
		
		/**
		 * - 멀티스레드
		 * - 로그파일 파싱
		 * - 해쉬셋 동기화 및 패턴 적용
		 * **/
		logLauncher.initHashSet();
		logLauncher.initThreadPool(5);
		logLauncher.process();
		
		
		/**
		 * - DB 저장
		 * **/
		logLauncher.connection = JdbcConnector.getConnection();
		logLauncher.insertKeyword();
	}
	
	private void process(){
		/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 
		 * 					    파일 경로
		 * 
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
		String path = LogPathEnum.LOG_DIRECTORY_PATH.getPath();
		
		// 일별 파일 리스트
		File[]dailyDirectoryList = new File(path).listFiles();
		for(File dailyDirectory : dailyDirectoryList){
			
			String absolutePath = dailyDirectory.getAbsolutePath();
			File dailyFile = new File(absolutePath);

			// 로그 파일 리스트
			File[] logFileList = dailyFile.listFiles();
			
			for(File logFile : logFileList){
				/**
				 * 파일 읽는 것.
				 * **/
				KeywordExtractor keywordExtractor = new KeywordExtractor();
				keywordExtractor.setLogFile(logFile);
				executorService.execute(keywordExtractor);
			}
		}
		
		executorService.shutdown();
		while(!executorService.isTerminated());

		System.out.println(keywordSet.size());
	}
	

	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * 
	 * 멀티스레드 환경에서 동기화 처리
	 * 
	 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
	public static synchronized void addKeywordAtSet(String keyword){
		if(!isUselessPattern(keyword))
			keywordSet.add(keyword);
	}
	
	public static boolean isUselessPattern(String keyword){
		
		/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 
		 * 숫자만 있는 경우, 알파벳만 있는 경우, 한글 한 개만 존재하는 경우,
		 * 자음만 존재하는 경우, 모음만 있는 경우
		 * 
		 * [위의 경우에 해당 하는 것들] => 불필요한 패턴이므로 true
		 * 
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
		for(KeywordPatternEnum pattern : KeywordPatternEnum.values()){
			Matcher m = pattern.getPattern().matcher(keyword);
			if(m.find()){
				return true;
			}
		}
		
		/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 물음표 존재하는 것도 제거
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
		if(keyword.contains("?"))
			return true;
		
		return false;
	}
	
	private void initHashSet(){
		Set<String> set = new HashSet<String>();
		keywordSet = set;
	}
	
	private void initThreadPool(int n){
		this.executorService = Executors.newFixedThreadPool(n);
	}
	
	private void insertKeyword(){
		String insertQuery = "INSERT INTO KEYWORD_TB VALUES(?)";
		String insertIntoTableQuery1 = "INSERT INTO KEYWORD_MENTION_TB (KEYWORD) SELECT KEYWORD FROM KEYWORD_TB";
		String insertIntoTableQuery2 = "INSERT INTO KEYWORD_MENTION_DAY_TB (KEYWORD) SELECT KEYWORD FROM KEYWORD_TB";
		
		PreparedStatement insertPstmt = null;
		PreparedStatement insertIntoTablePstmt1 = null;
		PreparedStatement insertIntoTablePstmt2 = null;
		
		try {
			connection.setAutoCommit(false);
			
			insertPstmt = connection.prepareStatement(insertQuery);
			int count = 0;
			
			Iterator<String> it = keywordSet.iterator();
			
			while(it.hasNext()){
				String keyword = it.next();
				System.out.println(keyword);
				
				count++;
				insertPstmt.setString(1, keyword);
				insertPstmt.addBatch();
				
				if(count % 10000 == 0){
					insertPstmt.executeBatch();
					connection.commit();
				}
			}// while : keywordSet 삽입
			
			insertPstmt.executeBatch();
			connection.commit();
			
			insertIntoTablePstmt1 = connection.prepareStatement(insertIntoTableQuery1);
			insertIntoTablePstmt1.executeUpdate();
			insertIntoTablePstmt1.close();
			
			insertIntoTablePstmt2 = connection.prepareStatement(insertIntoTableQuery2);
			insertIntoTablePstmt2.executeUpdate();
			insertIntoTablePstmt2.close();
			
			insertPstmt.close();
			connection.close();
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("넣다가 에러 발생해서 그냥 무시");
			System.exit(1);
		}
	}
}
