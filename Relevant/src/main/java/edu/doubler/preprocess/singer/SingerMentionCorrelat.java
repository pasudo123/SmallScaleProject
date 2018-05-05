package edu.doubler.preprocess.singer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Scanner;

import edu.doubler.preprocess.database.JdbcConnector;
import edu.doubler.preprocess.util.EnumOnDate;

public class SingerMentionCorrelat {
	/**
	 * (1) 1월부터 12월까지 각각의 월별로 키워드를 중복없이 저장 (2) 1월부터 12월까지 월별로 읽으면서 해당 키워드에 대한 월별
	 * 언급량 확인
	 **/
	private static final String readFilePathDir = "C:\\Users\\Daumsoft\\Desktop\\다음소프트 과제\\11주차 과제\\회원테이블 예시\\click_log_2014";
	private static final String mentionDirPath = "C:\\Users\\Daumsoft\\Desktop\\다음소프트 과제\\11주차 과제\\회원테이블 예시\\SingerListOnMention";
	
	public static void main(String[] args) {
		SingerMentionCorrelatFile singerMentionCorrlat = new SingerMentionCorrelatFile();
		singerMentionCorrlat.initConnection();
		singerMentionCorrlat.keywordCorrelatMention(mentionDirPath);
	}
}

class SingerMentionCorrelatFile {
	
	private static final String monthKeywordList = "C:\\Users\\Daumsoft\\Desktop\\다음소프트 과제\\11주차 과제\\회원테이블 예시\\SingerListOnMonth";
	private static final String readFilePathDir = "C:\\Users\\Daumsoft\\Desktop\\다음소프트 과제\\11주차 과제\\회원테이블 예시\\click_log_2014";
	private static final String mentionDirPath = "C:\\Users\\Daumsoft\\Desktop\\다음소프트 과제\\11주차 과제\\회원테이블 예시\\SingerListOnMention";
	
	Connection connection = null;
	
	public void process() {
		// 월별 가수
		File[] file = new File(mentionDirPath).listFiles();
		FileInputStream fis = null;
		BufferedReader br = null;
		String inputLine = null;

		for (File monthLogFile : file) {
			try {
				fis = new FileInputStream(monthLogFile);
				br = new BufferedReader(new InputStreamReader(fis));

				Hashtable<String, Integer> table = new Hashtable<String, Integer>();

				// 해쉬 테이블
				while ((inputLine = br.readLine()) != null) {
					table.put(inputLine, 0);
				}

				// 언급량 카운트
				// 1월달 전체 데이터를 다시 읽어들인다.
				String month = monthLogFile.getName().substring(0, 2);
				System.out.println("중복없앤 해당 월의 키워드 : " + monthLogFile.getName());
				System.out.println(month + "월 달" + " 키워드 개수 : " + table.size());
				
				File[] originLogDirList = new File(readFilePathDir).listFiles();

				for (File originLogDir : originLogDirList) {
					String dirName = originLogDir.getName().substring(4, 6);

					System.out.println("읽어들일 디렉토리 : " + originLogDir);

					// 동일한 월별 로그 내의 일별로그
					if (month.equals(dirName)) {
						File[] originLogFileList = new File(originLogDir.getAbsolutePath()).listFiles();

						// 로그 파일 다수를
						for (File originLogFile : originLogFileList) {
							// System.out.println("읽어들일 로그파일 : " +
							// originLogFile);

							FileInputStream originFis = new FileInputStream(originLogFile);
							BufferedReader originBr = new BufferedReader(new InputStreamReader(originFis, "euc-kr"));

							// 읽어들인다.
							while ((inputLine = originBr.readLine()) != null) {
								String singer = null;

								try {
									String[] splitter = inputLine.split("\t");

									if (splitter.length <= 4)
										continue;

									singer = splitter[5];
									singer = singer.split("      ")[0];
									singer = singer.replaceAll("\\s", "");

									// 테이블에 해당 키 값 존재시 값 (+1) 추가
									if (table.get(singer) != null)
										table.put(singer, table.get(singer) + 1);
								} catch (ArrayIndexOutOfBoundsException e) {
									System.out.println(e.getMessage() + " : " + inputLine);
									continue;
								}

							} // while()

							originBr.close();
						} // for() : 하나의 로그 파일을 읽어들임
					} // if() : 동일한 월별 로그 내의 일별로그
				} // for : 월별로그

				System.out.println(month + "월의 키워드 언급량 (DB 혹은 파일) 저장 시도");

				FileOutputStream fos = new FileOutputStream(mentionDirPath + "\\" + month + "달 키워드 언급량.txt");
				for (String key : table.keySet()) {

					// 언급량 100 이상
					if (table.get(key) >= 100) {
						String line = key + "\t" + table.get(key);
						fos.write(line.getBytes());
						fos.write("\n".getBytes());
					}
				}

				fos.close();

				System.out.println(month + "월의 키워드 언급량 (DB 혹은 파일) 저장 완료");

				// System.out.println("break문");
				// break;
			}

			catch (IOException e) {
				System.out.println(e.getMessage());
				return;
			}
		} // for
	}
	
	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 *
	 * 						코드 개선 라인
	 * 
	 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
//	public String keywordCorrelatMention(String path){
//		File[]directory = new File(path).listFiles();
//		
//		// 20140101 / 20140102 / 20140103 / ... : 일별 폴더
//		for(File d : directory){
//			File[]logFileList = new File(d.getAbsolutePath()).listFiles();
//			BufferedReader br = null;
//			String inputLine = null;
//			
//			// K01.C.20140101.0000
//			// K01.C.20140101.0010 ...
//			for(File log : logFileList){
//				FileInputStream fis = null;
//				
//				try {
//					fis = new FileInputStream(log);
//					br = new BufferedReader(new InputStreamReader(fis, "euc-kr"));
//					// 한 라인씩 읽어들인다.
//					while((inputLine = br.readLine()) != null){
//						System.out.println(inputLine);
//						new Scanner(System.in).next();
//					}
//				} 
//				catch (FileNotFoundException e) {
//					System.out.println(e.getMessage() + " : " + "파일을 찾지 못해서 다음 파일로 이동 ~");
//					continue;
//				}
//				catch(IOException e){
//					System.out.println(e.getMessage() + " : " + "입출력 에러인데, 그냥 다음 파일로 넘어감");
//					continue;
//				}
//			}// for (로그파일)
//			
//			try{
//				if(br != null)
//					br.close();
//			}
//			catch(IOException e){
//				System.out.println("BufferedReader 를 닫는데도 에러라서 시스템 종료");
//				System.exit(1);
//			}
//		}// for (디렉토리)
//		
//		return null;
//	}
	
	public void keywordCorrelatMention(String path){
		File[]logFileList = new File(path).listFiles();
		Hashtable<String, Integer> table = new Hashtable<String, Integer>();
		
		// 01달 키워드 언급량 ...
		for (File log : logFileList) {
			BufferedReader br = null;
			String inputLine = null;

			FileInputStream fis = null;
			
			System.out.println("읽을 파일 명 : " + log);
			
			try {
				fis = new FileInputStream(log);
				br = new BufferedReader(new InputStreamReader(fis));
				// 한 라인씩 읽어들인다.
				while ((inputLine = br.readLine()) != null) {
					
					String keyword = inputLine.split("\t")[0];
					int count = Integer.parseInt(inputLine.split("\t")[1]);
					/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
					 *				해쉬 테이블로 값 설정  		
					 *ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
					if(table.get(keyword) != null)
						table.put(keyword, table.get(keyword) + count);
					else
						table.put(keyword, count);
				}
				
				// 해쉬 테이블로 한달동안 언급된 키워드 모두 저장된 상태
				// 이후 클리어 실시
				updateMentionCountOnKeyword(log.getName(), table);
				table.clear();
			} 
			catch (FileNotFoundException e) {
				System.out.println(e.getMessage() + " : " + "파일을 찾지 못해서 다음 파일로 이동 ~");
				continue;
			} 
			catch (IOException e) {
				System.out.println(e.getMessage() + " : " + "입출력 에러인데, 그냥 다음 파일로 넘어감");
				continue;
			} 
			finally {
				try {
					if (br != null)
						br.close();
				} catch (IOException e) {
					System.out.println("BufferedReader 를 닫는데도 에러라서 시스템 종료");
					System.exit(1);
				}
			}

		} // for (로그파일 읽는 구문)
	}
	
	public void initConnection(){
		connection = JdbcConnector.getConnection();
	}
	
	public void updateMentionCountOnKeyword(String fileName, Hashtable<String, Integer> table){
		// file : 01달 키워드 언급량.txt
		String month = fileName.substring(0, 2);
		String dateColumn = getMonth(month);
		
		// 널값 나와서 리턴
		if(dateColumn == null)
			return;
		
		String updateQuery = "UPDATE KEYWORD_MENTION_TB SET " + dateColumn + " = ? WHERE KEYWORD = ?";
		System.out.println("쿼리 >> " + updateQuery);
		try {
			PreparedStatement ps = connection.prepareStatement(updateQuery);
			
			for(String key : table.keySet()){
				int count = table.get(key);
				String keyword = key;
				
//				System.out.println(keyword + " : " + count);
				
				ps.setInt(1, count);
				ps.setString(2, keyword);
				ps.executeUpdate();
			}
			
			connection.commit();
			ps.close();
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("틈만 나면 SQLException");
			System.exit(1);
		}
		
		
	}
	
	// month 를 영문으로 리턴 (데이터베이스 컬럼과 매칭시키기 위함)
	private String getMonth(String month){
		
		for(EnumOnDate date : EnumOnDate.values()){
			if(date.getDateValue().equals(month))
				return date.getDateEngName();
		}
		
		return null;
	}
	
	// 테이블 내 해당 키워드 존재 여부
	private boolean isKeyword(String keyword){
		
		String selectQuery = "SELECT count(*) FROM KEYWORD_MENTION_TB WHERE KEYWORD = ?";
		int count = 0;
		try {
			PreparedStatement ps = connection.prepareStatement(selectQuery);
			ps.setString(1, keyword);
			ResultSet rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("SQLException 문제가 있다.");
			System.exit(1);
		}
		
		return (count >= 1)? true:false;
	}
}