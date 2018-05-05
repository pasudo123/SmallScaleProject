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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import edu.doubler.preprocess.util.EnumOnDate;

public class SingerExtractorByMonth {
	
	/**
	 * 
	 * 10분단위의 로그 데이터들을 한달 단위로 합친다. 이 과정에서 중복을 제거한다.
	 * 결과적으로 중복 없는 키워드들의 한달 데이터
	 * 
	 * **/
	
	static List<HashSet<String>> singerList = new ArrayList<HashSet<String>>(); 
	static String readFilePathDir = "C:\\Users\\Daumsoft\\Desktop\\다음소프트 과제\\11주차 과제\\회원테이블 예시\\SingerListOnTime";
	static String writeFilePathDir = "C:\\Users\\Daumsoft\\Desktop\\다음소프트 과제\\11주차 과제\\회원테이블 예시\\SingerListOnMonth";
	
	public static void main(String[]args){
		
		boolean[]bool = new boolean[13];			// 1 ~ 12
		Arrays.fill(bool, false);
		
		StringBuilder[] sb = new StringBuilder[13];	// 1 ~ 12
		for(int i = 1; i <= 12; i++){
			sb[i] = new StringBuilder();
		}
		
		File file = new File(readFilePathDir);
		
		for(File log : file.listFiles()){
			String logName = log.getName();
			String logDate = logName.split("\\.")[2];
			logDate = logDate.substring(0, logDate.length()-2);		// 시분 제거
//			logDate = logDate.substring(4, logDate.length());		// 년도 제거
			
			// 파일 읽기
			FileInputStream fis = null;
			BufferedReader br = null;
			
			try {
				fis = new FileInputStream(log);
				br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
			} 
			catch (FileNotFoundException | UnsupportedEncodingException e) {
				System.out.println(e.getMessage());
				return;
			}
			
//			System.out.println(logName);
//			System.out.println(logDate);
			
			if(logDate.equals(EnumOnDate.JANUARY.toString())){
				readAndAppend(br, sb[EnumOnDate.JANUARY.getNumber()]);
			}
			else if (logDate.equals(EnumOnDate.FEBUARY.toString())) {
				// 1월달 데이터 (단 1회만)
				// 중복 제거 및 데이터 저장
				if(!bool[EnumOnDate.JANUARY.getNumber()]){
					write(EnumOnDate.JANUARY, deleteOverlap(sb[EnumOnDate.JANUARY.getNumber()]));
					bool[EnumOnDate.JANUARY.getNumber()] = true;
				}
				
				readAndAppend(br, sb[EnumOnDate.FEBUARY.getNumber()]);
			}

			else if (logDate.equals(EnumOnDate.MARCH.toString())) {
				// 2월달 데이터 (단 1회만)
				// 중복 제거 및 데이터 저장
				if(!bool[EnumOnDate.FEBUARY.getNumber()]){
					write(EnumOnDate.FEBUARY, deleteOverlap(sb[EnumOnDate.FEBUARY.getNumber()]));
					bool[EnumOnDate.FEBUARY.getNumber()] = true;
				}
				
				readAndAppend(br, sb[EnumOnDate.MARCH.getNumber()]);
			}

			else if (logDate.equals(EnumOnDate.APRIL.toString())) {
				// 3월달 데이터
				// 중복 제거 및 데이터 저장
				if(!bool[EnumOnDate.MARCH.getNumber()]){
					write(EnumOnDate.MARCH, deleteOverlap(sb[EnumOnDate.MARCH.getNumber()]));
					bool[EnumOnDate.MARCH.getNumber()] = true;
				}
				
				readAndAppend(br, sb[EnumOnDate.APRIL.getNumber()]);
			}

			else if (logDate.equals(EnumOnDate.MAY.toString())) {
				// 4월 작성
				if(!bool[EnumOnDate.APRIL.getNumber()]){
					write(EnumOnDate.APRIL, deleteOverlap(sb[EnumOnDate.APRIL.getNumber()]));
					bool[EnumOnDate.APRIL.getNumber()] = true;
				}
				
				readAndAppend(br, sb[EnumOnDate.MAY.getNumber()]);
			}

			else if (logDate.equals(EnumOnDate.JUNE.toString())) {
				if(!bool[EnumOnDate.MAY.getNumber()]){
					write(EnumOnDate.MAY, deleteOverlap(sb[EnumOnDate.MAY.getNumber()]));
					bool[EnumOnDate.MAY.getNumber()] = true;
				}
				
				readAndAppend(br, sb[EnumOnDate.JUNE.getNumber()]);
			}

			else if (logDate.equals(EnumOnDate.JULY.toString())) {
				if(!bool[EnumOnDate.JUNE.getNumber()]){
					write(EnumOnDate.JUNE, deleteOverlap(sb[EnumOnDate.JUNE.getNumber()]));
					bool[EnumOnDate.JUNE.getNumber()] = true;
				}
				
				readAndAppend(br, sb[EnumOnDate.JULY.getNumber()]);
			}
			
			else if (logDate.equals(EnumOnDate.AUGUST.toString())) {
				if(!bool[EnumOnDate.JULY.getNumber()]){
					write(EnumOnDate.JULY, deleteOverlap(sb[EnumOnDate.JULY.getNumber()]));
					bool[EnumOnDate.JULY.getNumber()] = true;
				}
				
				readAndAppend(br, sb[EnumOnDate.AUGUST.getNumber()]);
			}
			
			else if (logDate.equals(EnumOnDate.SEPTEMBER.toString())) {
				if(!bool[EnumOnDate.AUGUST.getNumber()]){
					write(EnumOnDate.AUGUST, deleteOverlap(sb[EnumOnDate.AUGUST.getNumber()]));
					bool[EnumOnDate.AUGUST.getNumber()] = true;
				}
				
				readAndAppend(br, sb[EnumOnDate.SEPTEMBER.getNumber()]);
			}
			
			else if (logDate.equals(EnumOnDate.OCTOBER.toString())) {
				if(!bool[EnumOnDate.SEPTEMBER.getNumber()]){
					write(EnumOnDate.SEPTEMBER, deleteOverlap(sb[EnumOnDate.SEPTEMBER.getNumber()]));
					bool[EnumOnDate.SEPTEMBER.getNumber()] = true;
				}
				
				readAndAppend(br, sb[EnumOnDate.OCTOBER.getNumber()]);
			}
			
			else if (logDate.equals(EnumOnDate.NOVEMBER.toString())) {
				if(!bool[EnumOnDate.OCTOBER.getNumber()]){
					write(EnumOnDate.OCTOBER, deleteOverlap(sb[EnumOnDate.OCTOBER.getNumber()]));
					bool[EnumOnDate.OCTOBER.getNumber()] = true;
				}
				
				readAndAppend(br, sb[EnumOnDate.NOVEMBER.getNumber()]);
			}
			
			else if (logDate.equals(EnumOnDate.DECEMBER.toString())) {
				if(!bool[EnumOnDate.NOVEMBER.getNumber()]){
					write(EnumOnDate.NOVEMBER, deleteOverlap(sb[EnumOnDate.NOVEMBER.getNumber()]));
					bool[EnumOnDate.NOVEMBER.getNumber()] = true;
				}
				
				readAndAppend(br, sb[EnumOnDate.DECEMBER.getNumber()]);
			}
		}
		
		// 마지막 달
		if(!bool[EnumOnDate.DECEMBER.getNumber()]){
			write(EnumOnDate.DECEMBER, deleteOverlap(sb[EnumOnDate.DECEMBER.getNumber()]));
			bool[EnumOnDate.DECEMBER.getNumber()] = true;
		}
	}
	
	public static void readAndAppend(BufferedReader br, StringBuilder sb){
		String inputLine = null;
		
		try {
			while((inputLine = br.readLine()) != null){
				sb.append(inputLine);
				sb.append("&&&");
			}
		} 
		catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}
	}
	
	// 중복제거
	public static HashSet<String> deleteOverlap(StringBuilder sb){
		String[]singerList = sb.toString().split("&&&");
		HashSet<String> singerSet = new HashSet<String>();
		
		// 공백제거 및 셋에 추가 (중복 제거)
		for(String singer : singerList){
			singer = singer.replaceAll("\\s", "");
			singerSet.add(singer);
		}
		
		return singerSet;
	}
	
	public static void write(EnumOnDate dateType, HashSet<String> singerSet){
		System.out.println(dateType + " 날짜의 키워드 중복제거하고 작성");
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(writeFilePathDir + "\\" + dateType.getDateValue() + "month KeywordList.log");
			System.out.println(singerSet.size());
			for(String singer : singerSet){
				fos.write(singer.getBytes());
				fos.write("\n".getBytes());
			}
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return;
		} 
		catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}
		finally{
			if(fos != null)
				try {
					fos.close();
				} catch (IOException e) {e.printStackTrace();}
		}
		
	}
}