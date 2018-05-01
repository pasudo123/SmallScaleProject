package edu.doubler.preprocess.person;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

public class MusicMemberGenerator {
	public static void main(String[]args){
		String readFilePathDir = "C:\\Users\\Daumsoft\\Desktop\\다음소프트 과제\\11주차 과제\\회원테이블 예시\\click_log_2014";
		File directory = new File(readFilePathDir);
		File[] fileList = directory.listFiles();
		
		Hashtable<String, MusicMember> memberTable = new Hashtable<String, MusicMember>();
		
		// 일별 파일 리스트
		for(File f : fileList){
			File dateFileList = new File(f.getAbsolutePath());
			File[] logList = dateFileList.listFiles();
			System.out.println(f);
			
			// 시간별 로그 리스트
			for(File log : logList){
				
				FileInputStream fis = null;
				BufferedReader br = null;
				String inputLine = null;
				
				try {
					fis = new FileInputStream(log);
					br = new BufferedReader(new InputStreamReader(fis, "euc-kr"));
					
					while((inputLine = br.readLine()) != null){
						String[]splitArray = inputLine.split("\t");
						String memberKey = splitArray[splitArray.length-1];
						
						// 멤버 추가
						if(memberTable.get(memberKey) == null){
							memberTable.put(memberKey, new MusicMember(memberKey));
						}// if
						
						/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
						 * 데이터베이스 추가 여부를 결정
						 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
						
					}// while
				}// try
				
				catch (IOException e) {
					e.printStackTrace();
				}
			}// 시간별 로그 리스트
		}// 일별 파일 리스트
	}
}
