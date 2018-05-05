package edu.doubler.log_process.keyword;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.doubler.log_process.domain.LogMapper;

public class KeywordExtractor implements Runnable{
	
	private KeywordLogProcessor logProcessor;
	private File logFile;
	
	public void setLogFile(File logFile){
		this.logFile = logFile;
	}
	
	@Override
	public void run() {
		logProcessor = new KeywordLogProcessorImpl();
		extractKeyword();
	}
	
	private void extractKeyword(){
		FileInputStream fis = null;
		BufferedReader br = null; 
		String line= null;
		
		try{
			fis = new FileInputStream(logFile.getAbsolutePath());
			br = new BufferedReader(new InputStreamReader(fis, "euc-kr"));
			
			while((line = br.readLine()) != null){
				Object object = logProcessor.getLogMapper(line);
				LogMapper logMapper = null;
				
				if(object instanceof LogMapper){
					logMapper = (LogMapper) object;
				}
				if(object instanceof String){
					// 로그 저장
				}
				
				if(logMapper != null){
					KeywordLogLauncher.addKeywordAtSet(logMapper.getSearchKeyword());
				}// if : null 이 아니면,
			}
		}
		catch(IOException e){
			System.out.println(e.getMessage());
			System.out.println("에러나서 바로 리턴");
			return;
		}
		finally{
			try{
				if(fis != null)
					fis.close();
				if(br != null)
					br.close();
			}
			catch(IOException e){e.printStackTrace();}
		}
	}
}
