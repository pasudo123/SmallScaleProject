package edu.doubler.preprocess.singer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingerExtractorByTime {
	public static void main(String[]args){
		
		boolean flag = true;
		
		String readFilePathDir = "C:\\Users\\Daumsoft\\Desktop\\다음소프트 과제\\11주차 과제\\회원테이블 예시\\click_log_2014";
		String writeFilePathDir = "C:\\Users\\Daumsoft\\Desktop\\다음소프트 과제\\11주차 과제\\회원테이블 예시\\SingerListOnTime";
		
		File directory = new File(readFilePathDir);
		File[] fileList = directory.listFiles();
		
		// 스레드 풀 구현.
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		
		// 일별 파일 리스트
		for(File f : fileList){
			File dateFileList = new File(f.getAbsolutePath());
			File[] logList = dateFileList.listFiles();
			
			// 시간별 로그 리스트
			for(File log : logList){
				int month = Integer.parseInt(log.getName().split("\\.")[2].substring(4, 6));
				
				/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
				 * 
				 * 			    	 2014년 12월 부터 시작해야함
				 * 
				 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
				if(month < 12)
					continue;
				System.out.println(log.getName());
				
				String readLogFilePath = log.getAbsolutePath();
				String writeLogFilePath = writeFilePathDir + "\\" +log.getName();
				
				SingerExtractorByTimeThread sinExtThread = new SingerExtractorByTimeThread();
				sinExtThread.setLogName(log.getName());
				sinExtThread.setReadFilePath(readLogFilePath);
				sinExtThread.setWriteFilePath(writeLogFilePath);
				sinExtThread.initHashSet();
				
				// 스레드 풀 실행
				executorService.execute(sinExtThread);
//				break;
			}
//			break;
		}
		
		executorService.shutdown();
	}
}


class SingerExtractorByTimeThread implements Runnable{
	
	private String logName = null;
	private String readFilePath = null;
	private String writeFilePath = null;
	private List<HashSet<String>> singerSet = new ArrayList<HashSet<String>>();
	private StringBuilder sb = new StringBuilder();
	
	@Override
	public void run() {
		processOnTime();
		
		try {
			Thread.sleep(500);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(logName + " == 작업 수행 완료");
	}
	
	public void setLogName(String logName){
		this.logName = logName;
	}
	
	public void setReadFilePath(String readFilePath){
		this.readFilePath = readFilePath;
	}

	public void setWriteFilePath(String writeFilePath){
		this.writeFilePath = writeFilePath;
	}
	
	public void initHashSet(){
		this.singerSet.add(new HashSet<String>());
	}
	
	private void processOnTime(){
		FileInputStream fis = null;
		FileOutputStream fos = null;
		BufferedReader br = null;
		String inputLine = null;
		
		try {
			fis = new FileInputStream(readFilePath);
			br = new BufferedReader(new InputStreamReader(fis, "euc-kr"));
			
			fos = new FileOutputStream(writeFilePath);
			
			while((inputLine = br.readLine()) != null){
				String singer = inputLine.split("\\t")[5];
				singer = singer.replaceAll("\\s", "");
//				singer = singer.split("      ")[0];	// line 132 와 호환
				sb.append(singer);
				// split 하기 위함
				sb.append("&&&");
			}
			
			// split 이후에 "\\s" 공백 제거
			String[] singerList = sb.toString().split("&&&");
			for (String s : singerList){
//				s = s.replaceAll("\\s", "");		// line 123 과 호환
				singerSet.get(0).add(s);
			}
			
			// 가수를 한 줄씩 모은다.
			for (String s : singerSet.get(0)) {
				fos.write(s.getBytes());
				fos.write("\n".getBytes());
			}
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return;
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
			return;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}
		finally{
			if(br != null)
				try {
					br.close();
				} catch (IOException e) {e.printStackTrace();}
			
			if(fos != null)
				try {
					fos.close();
				} catch (IOException e) {e.printStackTrace();}
		}
		
		return;
	}
}