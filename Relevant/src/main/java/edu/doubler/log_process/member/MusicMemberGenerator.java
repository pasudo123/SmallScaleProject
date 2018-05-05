package edu.doubler.log_process.member;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Hashtable;

public class MusicMemberGenerator {
	public static void main(String[]args){
		String readFilePathDir = "C:\\Users\\Daumsoft\\Desktop\\MelonLog\\click_log_2014";
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
						// 멤버 추가하면서, 성별과 연령대를 생성자에서 결정
						if(memberTable.get(memberKey) == null){
							memberTable.put(memberKey, new MusicMember(memberKey));
						}// if
						
					}// while
				}// try
				
				catch (IOException e) {
					e.printStackTrace();
				}
			}// 시간별 로그 리스트
			
		}// 일별 파일 리스트
		
		// Member 파일 쓰기
		File file = new File("C:\\Users\\Daumsoft\\Desktop\\MelonLog\\member\\member.txt");
		BufferedWriter bw = null;
		
		try {
			bw = new BufferedWriter(new FileWriter(file, true));
			
			// memberTable 을 추가.
			Enumeration<String> enumeration = memberTable.keys();
			
			while(enumeration.hasMoreElements()){
				String key = enumeration.nextElement();
				MusicMember musicMember = memberTable.get(key);

				bw.write((musicMember.toTsvString()));
				bw.write("\n");
			}
		}  
		catch (IOException e) {
			System.out.println(e.getMessage());
			System.out.println("파일 못찾았다고 에러나옴, 바로 종료");
			System.exit(1);
		}
		finally{
			if(bw != null)
				try {
					bw.close();
				} 
				catch (IOException e) {e.printStackTrace();}
		}
	}
}
