package edu.doubler.log_process.inflow;

import java.io.File;
import java.util.HashMap;

public class TimeInflowCorrelat {
	
	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * 
	 * [ 시간당 유입량  ]
	 * 
	 * - 해당 날짜와 로그시간을 제목으로 가진 파일
	 * - 해당 파일에 대한 파일 크기(KB) 의 상관도 분석
	 * 
	 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
	
	public static void main(String[]args){
		// 일간 폴더에 대해서 수행
		TimeInflowCorrelat timeInflowCorrelat = new TimeInflowCorrelat();
		timeInflowCorrelat.getTimeInflowOnSite("C:\\Users\\Daumsoft\\Desktop\\MelonLog\\click_log_2014");
	}
	
	public void getTimeInflowOnSite(String path){
		File[]dailyDirectoryList = new File(path).listFiles();
		
		HashMap<String, HashMap<String, String>> timeInflowMap = new HashMap<String, HashMap<String, String>>();
		
		for(File dailyDirectory : dailyDirectoryList){
			File[] logFileList = new File(dailyDirectory.getAbsolutePath()).listFiles();
			
			HashMap<String, String> inflowMap = new HashMap<String, String>();
			
			
			for(File logFile : logFileList){
				Long byteSize = logFile.length();
				Long kbSize = byteSize / 1024L;
				String size = kbSize + "KB";
				String time = logFile.getName().split("\\.")[3];
				
				inflowMap.put(time, size);
			}
			
			timeInflowMap.put(dailyDirectory.getName(), inflowMap);
			break;
		}
		
		// 0000(시작), 0010, 0020 ... 2340, 2350(끝)
		System.out.println(timeInflowMap.get("20140101").toString());
	}
}
