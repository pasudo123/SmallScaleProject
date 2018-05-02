package edu.doubler.log_process.member;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MusicMemberLauncher {
	
	private static final String LOG_PATH = "C:\\Users\\Daumsoft\\Desktop\\MelonLog\\click_log_2014";
	private static final String MEMBER_PATH = "C:\\Users\\Daumsoft\\Desktop\\MelonLog\\member\\member.txt";
	private static final String NEW_PATH = "C:\\Users\\Daumsoft\\Desktop\\MelonLog\\re_write";
	
	public static Hashtable<String, Hashtable<String, Integer>> ageAndCateTable = new Hashtable<String, Hashtable<String, Integer>>();
	private HashMap<String, MusicMember> memberMap;
	
	public static void main(String[]args){
		MusicMemberLauncher musicMemberLauncher = new MusicMemberLauncher();
		musicMemberLauncher.readMemberProcess();
		
//		musicMemberLauncher.process(LOG_PATH);
		musicMemberLauncher.getClickLog(NEW_PATH);
		
		Hashtable<String, Integer> table1960 = ageAndCateTable.get("1960");
		Hashtable<String, Integer> table1970 = ageAndCateTable.get("1970");
		Hashtable<String, Integer> table1980 = ageAndCateTable.get("1980");
		Hashtable<String, Integer> table1990 = ageAndCateTable.get("1990");
		Hashtable<String, Integer> table2000 = ageAndCateTable.get("2000");
		
		System.out.println("1960년대생 검색영역 : " + ageAndCateTable.get("1960").toString());
		System.out.println("1970년대생 검색영역 : " + ageAndCateTable.get("1970").toString());
		System.out.println("1980년대생 검색영역 : " + ageAndCateTable.get("1980").toString());
		System.out.println("1990년대생 검색영역 : " + ageAndCateTable.get("1990").toString());
		System.out.println("2000년대생 검색영역 : " + ageAndCateTable.get("2000").toString());
		
	}
	
	public void process(String path){
		System.out.println("병렬처리");
		
		File[]dailyDirectoryList = new File(path).listFiles();
		int directorySize = dailyDirectoryList.length;
		
		// 최대 스레드 개수 5개인 스레드 풀 생성
		ExecutorService executorService = Executors.newFixedThreadPool(5);
			
		for(int dirIndex = 0; dirIndex < directorySize; dirIndex++){
			System.out.println(dailyDirectoryList[dirIndex].getAbsolutePath());
			String absolutePath = dailyDirectoryList[dirIndex].getAbsolutePath();
			
			File dailyFile = new File(absolutePath);
			File[] logFileList = dailyFile.listFiles();
			
			for(File logFile : logFileList){
				MusicMemberCorrelat musicMemberCorrelat = new MusicMemberCorrelat();
				musicMemberCorrelat.setFlag(false);
				musicMemberCorrelat.setLogFile(logFile);
				musicMemberCorrelat.setMap(memberMap);
				executorService.execute(musicMemberCorrelat);
			}
		}
		
		executorService.shutdown();
		while(!executorService.isTerminated()){}
		
		System.out.println("rewrite 완료 (기존 로그에 멤버 포함에서 덧대어 쓰기)");
		System.out.println("연령별 및 성별에 따른 데이터 쓰기");
		
		/**
		 * 해당 디렉토리 내의 연령별 클릭 로그 분석
		 * **/
	}
	
	// 멤버 파일 읽고 해쉬맵 저장
	private void readMemberProcess(){
		System.out.println("멤버파일 읽기");
		
		File memberFile = new File(MEMBER_PATH);
		String inputLine = null;
		BufferedReader br = null;
		memberMap = new HashMap<String, MusicMember>();
		
		try {
			br = new BufferedReader(new FileReader(memberFile));
			
			while((inputLine = br.readLine()) != null){
				String memberKey = inputLine.split("\t")[0];
				String memberGender = inputLine.split("\t")[1];
				String memberBirthday = inputLine.split("\t")[2];
				
				memberMap.put(memberKey, new MusicMember(memberKey, memberGender, memberBirthday));
			}
		} 
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.out.println("파일 못찾았다고 뜨는 에러, 그냥 종료");
			System.exit(1);
		}
		catch(IOException e){
			System.out.println(e.getMessage());
			System.out.println("파일 IO에러, 그냥 종료");
			System.exit(1);
		}
	}
	
	private void getClickLog(String path){
		File[]logFileList = new File(path).listFiles();
		
		// 최대 스레드 개수 5개인 스레드 풀 생성
		ExecutorService executorService = Executors.newFixedThreadPool(5);
				
		for(File logFile : logFileList){
			MusicMemberCorrelat musicMemberCorrelat = new MusicMemberCorrelat();
			musicMemberCorrelat.setFlag(true);
			musicMemberCorrelat.setLogFile(logFile);
			musicMemberCorrelat.setMap(memberMap);
			
			executorService.execute(musicMemberCorrelat);
		}
		
		executorService.shutdown();
		while(!executorService.isTerminated()){}
		
		System.out.println("getClickLog 완료");
	}
	
	public static synchronized void addClickLogData(String age, String clickCategory){
		if(ageAndCateTable.get(age) != null){
			Hashtable<String, Integer> table = ageAndCateTable.get(age);
			
			if(table.get(clickCategory) != null)
				table.put(clickCategory, table.get(clickCategory) + 1);
			else
				table.put(clickCategory, 1);
		}
		else{
			Hashtable<String, Integer> table = new Hashtable<String, Integer>();
			table.put(clickCategory, 1);
			
			MusicMemberLauncher.ageAndCateTable.put(age, table);
		}
	}
}
