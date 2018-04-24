package edu.doubler.preprocess.singer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

public class SingerMentionCorrelat {
	/**
	 * (1) 1월부터 12월까지 각각의 월별로 키워드를 중복없이 저장
	 * (2) 1월부터 12월까지 월별로 읽으면서 해당 키워드에 대한 월별 언급량 확인
	 * **/
	
	private static final String monthKeywordList = "C:\\Users\\Daumsoft\\Desktop\\다음소프트 과제\\11주차 과제\\회원테이블 예시\\SingerListOnMonth";
	private static final String readFilePathDir = "C:\\Users\\Daumsoft\\Desktop\\다음소프트 과제\\11주차 과제\\회원테이블 예시\\click_log_2014";
	private static final String mentionDirPath = "C:\\Users\\Daumsoft\\Desktop\\다음소프트 과제\\11주차 과제\\회원테이블 예시\\SingerListOnMention";
	
	public static void main(String[]args){
		// 월별 가수
		File[]file = new File(monthKeywordList).listFiles();
		FileInputStream fis = null;
		BufferedReader br = null;
		String inputLine = null;
		for(File monthLogFile : file){
			
			try {
				fis = new FileInputStream(monthLogFile);
				br = new BufferedReader(new InputStreamReader(fis));
				
				Hashtable<String, Integer> table = new Hashtable<String, Integer>();
				
				// 해쉬 테이블
				while((inputLine = br.readLine()) != null){
					table.put(inputLine, 0);
				}
				
				// 언급량 카운트
				// 1월달 전체 데이터를 다시 읽어들인다.
				String month = monthLogFile.getName().substring(0, 2);
				System.out.println("중복없앤 해당 월의 키워드 : " + monthLogFile.getName());
				System.out.println(month + "월 달" + " 키워드 개수 : " + table.size());
				
				File[]originLogDirList = new File(readFilePathDir).listFiles();
				
				for(File originLogDir : originLogDirList){
					String dirName = originLogDir.getName().substring(4, 6);
					
					System.out.println("읽어들일 디렉토리 : " + originLogDir);
					
					// 동일한 월별 로그 내의 일별로그
					if(month.equals(dirName)){
						File[]originLogFileList = new File(originLogDir.getAbsolutePath()).listFiles();
						
						// 로그 파일 다수를
						for(File originLogFile : originLogFileList){
//							System.out.println("읽어들일 로그파일 : " + originLogFile);
							
							FileInputStream originFis = new FileInputStream(originLogFile);
							BufferedReader originBr = new BufferedReader(new InputStreamReader(originFis, "euc-kr"));
							
							// 읽어들인다.
							while((inputLine = originBr.readLine()) != null){
								String[]splitter = inputLine.split("\\t");
								
								if(splitter.length <= 4)
									continue;
								
								String singer = splitter[5];
								singer = singer.split("      ")[0];
								singer = singer.replaceAll("\\s", "");
								
								// 테이블에 해당 키 값 존재시 값 (+1) 추가
								if(table.get(singer) != null)
									table.put(singer, table.get(singer) + 1);
								
							}// while()
							
							originBr.close();
						}// for() : 하나의 로그 파일을 읽어들임
					}// if() : 동일한 월별 로그 내의 일별로그
				}// for : 월별로그
				
				System.out.println(month + "월의 키워드 언급량 (DB 혹은 파일) 저장 시도");
				
				FileOutputStream fos = new FileOutputStream(mentionDirPath + "\\" + month + "달 키워드 언급량.txt");
				for(String key : table.keySet()){
					
					// 언급량 100 이상
					if(table.get(key) >= 100){
						String line = key + "\t" + table.get(key);
						fos.write(line.getBytes());
						fos.write("\n".getBytes());
					}
				}
				
				fos.close();
				
				System.out.println(month + "월의 키워드 언급량 (DB 혹은 파일) 저장 완료");
				System.out.println("break문");
				break;
			} 
			
			catch(IOException e){
				System.out.println(e.getMessage());
				return;
			}
			catch(ArrayIndexOutOfBoundsException e){
				System.out.println(e.getMessage() + " : " + inputLine);
				return;
			}
			
		}// for
	}
}

class SingerMentionThread implements Runnable{
	@Override
	public void run() {
		
	}
}