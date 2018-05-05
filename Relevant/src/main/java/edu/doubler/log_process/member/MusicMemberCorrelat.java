package edu.doubler.log_process.member;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Hashtable;

import edu.doubler.log_process.domain.LogMapper;
import edu.doubler.log_process.keyword.KeywordLogLauncher;
import edu.doubler.log_process.keyword.KeywordLogProcessor;
import edu.doubler.log_process.keyword.KeywordLogProcessorImpl;

public class MusicMemberCorrelat implements Runnable{
	
	private String reWritePath = "C:\\Users\\Daumsoft\\Desktop\\MelonLog\\re_write";
	
	private BufferedWriter bw = null;
	private HashMap<String, MusicMember> memberMap;
	private File logFile = null;
	private boolean flag = false;

	public void setFlag(boolean flag){
		this.flag = flag;
	}
	
	public void setMap(HashMap<String, MusicMember> memberMap){
		this.memberMap = memberMap;
	}
	
	public void setLogFile(File logFile){
		this.logFile = logFile;
	}
	
	@Override
	public void run() {
		if(!flag){
			readLog(logFile);
			System.out.println(logFile.getName() + " 완료");
		}
		else{
			readClickLog(logFile);
		}
	}
	
	private void readClickLog(File logFile){
		BufferedReader br = null;
		String line = null;
		
		try {
			System.out.println(logFile.getAbsolutePath());
			br = new BufferedReader(new FileReader(logFile.getAbsolutePath()));
			
			while((line = br.readLine()) != null){
				String[]element = line.split("\t");
				
				if(element.length < 11)
					continue;
				
				String age = element[10];
				String clickCategory = element[2];
				
				MusicMemberLauncher.addClickLogData(age, clickCategory);
			}// while : 한 줄씩 읽기
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void readLog(File logFile){
		System.out.println("readLog 진입");
		
		String line = null;
		KeywordLogProcessor keywordLogProcessor = new KeywordLogProcessorImpl();
			
		FileInputStream fis = null;
		BufferedReader br = null;
		String newLogName = logFile.getName();
		File file = new File(reWritePath + "\\" + newLogName);
		
		try {
			// true 값을 통해서 맨 아랫단에 쓰겠다는 의미
			bw = new BufferedWriter(new FileWriter(file, true));
			
			fis = new FileInputStream(logFile.getAbsolutePath());
			br = new BufferedReader(new InputStreamReader(fis, "euc-kr"));
			
			while((line = br.readLine()) != null){
				LogMapper logMapper = keywordLogProcessor.getLogMapper(line);
				
				if(logMapper != null){
					String keyword = logMapper.getSearchKeyword();
					
					// 불필요한 키워드는 생략
					if(KeywordLogLauncher.isUselessPattern(keyword))
						continue;
					
					// 멤버 ID 를 통한 성별 및 연령대 획득
					// 새롭게 파일 작성
					MusicMember musicMember = memberMap.get(logMapper.getMemberId());
					reWriteLogFile(newLogName, logMapper, musicMember);
				}
			}// while : 한 줄씩 읽음
		} 
		catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}
		finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	private void reWriteLogFile(String newLogName, LogMapper logMapper, MusicMember musicMember){
		try{
			bw.write(logMapper.getDate() + "\t");
			bw.write(logMapper.getPlatform() + "\t");
			bw.write(logMapper.getSearchCategory() + "\t");
			bw.write(logMapper.getHighLevelCategory() + "\t");
			bw.write(logMapper.getLowLevelCategory() + "\t");
			bw.write(logMapper.getSearchKeyword() + "\t");
			bw.write(logMapper.getCategoryId() + "\t");
			bw.write(logMapper.getMemberId() + "\t");
			
			bw.write(musicMember.toTsvString());
			bw.write("\n");
		}
		catch(IOException e){
			System.out.println(e.getMessage());
			System.out.println("멤버와 로그 같이 쓰다가 에러나옴 무시");
			return;
		}
	}
}
