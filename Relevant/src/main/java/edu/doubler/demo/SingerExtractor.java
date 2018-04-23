package edu.doubler.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SingerExtractor {
	public static void main(String[]args){
		
		/**
		 * (1) 날짜시간
		 * (2) 플랫폼
		 * (3) 검색영역 클릭
		 * (4) 검색정보
		 * (5) SO : 곡명 / AR : 아티스트
		 * (6) 가수명
		 * (7) 노래 PK
		 * (8) 유저 PK
		 * **/

		String ReadFilePath = "C:\\Users\\Daumsoft\\Desktop\\다음소프트 과제\\11주차 과제\\회원테이블 예시\\click_log_2014";
		String writeFilePath = "C:\\Users\\Daumsoft\\Desktop\\다음소프트 과제\\11주차 과제\\회원테이블 예시\\SingerList";
		
		File directory = new File(ReadFilePath);
		File[] fileList = directory.listFiles();
		
		BufferedReader br = null;
		BufferedWriter bw = null;
		
		String inputLine = new String();
		StringBuilder sb = new StringBuilder();
		
		List<HashSet<String>> singerSet = new ArrayList<HashSet<String>>();
		int index = -1;
		
		try{
			// 일별 파일 리스트
			for(File f : fileList){
				File dateFileList = new File(f.getAbsolutePath());
				File[] logList = dateFileList.listFiles();
				
				// 시간별 로그 리스트
				for(File log : logList){
					System.out.println(log);
					System.out.println(log.getName());
					
					FileInputStream fis = new FileInputStream(log);
					br = new BufferedReader(new InputStreamReader(fis, "euc-kr"));

					FileOutputStream fos = new FileOutputStream(writeFilePath + "\\" +log.getName());
					bw = new BufferedWriter(new OutputStreamWriter(fos, "euc-kr"));	
					
					singerSet.add(new HashSet<String>());
					
					while((inputLine = br.readLine()) != null){
						String singer = inputLine.split("\\t")[5];
						singer = singer.split("      ")[0];
						sb.append(singer);
						sb.append("&&&");
					}
					
					index = 0;
					String[] singerList = sb.toString().split("&&&");
					for (String s : singerList)
						singerSet.get(index).add(s);

					for (String s : singerSet.get(0)) {
						fos.write(s.getBytes());
						fos.write("\n".getBytes());
					}
					
					/**
					 * 파일 입출력 처리
					 * **/
					
					
					singerSet = new ArrayList<HashSet<String>>();
					index = -1;
					break;
				}
				
				break;
			}
		}
		catch(IOException e){
			System.out.println(e);
		}
		finally{
			if(br != null)
				try {
					br.close();
				} catch (IOException e) {e.printStackTrace();}
		}
		
		
		String line = sb.toString().replaceAll("\\s", "");
//		System.out.println(line);
//		System.out.println(singerSet.get(0).size());
//		for(String s : singerSet.get(0)){
//			System.out.println(s);
//		}
		
		// 언급량 뽑기 (왜)
		// 어느 시기에 해당 가수를 사람들이 많이 찾았음을 알 수 있다.
		// 이를 연령별로 혹은 성별로 구분해서 보여줄 수 있다. 그럼 특정 가수는 어느 연령별에게 혹은 어느 성별에 인기가 많은지 알 수 있다.
	}
}
