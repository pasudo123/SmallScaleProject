package edu.doubler.log_process.member;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class MemberCounter {
	
	private static final String MEMBER_PATH = "C:\\Users\\Daumsoft\\Desktop\\MelonLog\\member\\member.txt";
	private static final HashMap<String, Integer> map = new HashMap<String, Integer>();
	
	
	public static void main(String[]args){
		count();
	}
	
	public static void count(){
		File file = new File(MEMBER_PATH);
		FileReader fr = null;
		BufferedReader br = null;
		String line = null;
		
		map.put("M", 0);
		map.put("F", 0);
		map.put("1960", 0);
		map.put("1970", 0);
		map.put("1980", 0);
		map.put("1990", 0);
		map.put("2000", 0);
		
		try{
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			while((line = br.readLine()) != null){
				String memberId = line.split("\t")[0];
				String memberGender = line.split("\t")[1];
				String memberAges = line.split("\t")[2];
				
				map.put(memberGender, map.get(memberGender) + 1);
				map.put(memberAges, map.get(memberAges) + 1);
			}
		}
		catch(IOException e){
			System.out.println(e.getMessage());
			System.out.println("강제 종료");
			System.exit(1);
		}
		
		System.out.println(map.get("M"));
		System.out.println(map.get("F"));
		System.out.println(map.get("1960"));
		System.out.println(map.get("1970"));
		System.out.println(map.get("1980"));
		System.out.println(map.get("1990"));
		System.out.println(map.get("2000"));
	}
}
