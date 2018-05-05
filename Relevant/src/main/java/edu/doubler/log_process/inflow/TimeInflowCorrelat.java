package edu.doubler.log_process.inflow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import edu.doubler.log_process.domain.TimeInflowMapper;

public class TimeInflowCorrelat {
	
	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * 
	 * [ 시간당 유입량  ]
	 * - 해당 날짜시간과 해당 로그파일 내부의 라인 개수를 파악
	 * - 날짜과 라인 개수에 따른 상관성을 분석
	 * 
	 * [ 하는 이유  ]
	 * - 해당 시간에 사이트가 사람들의 트래픽을 이용해 색다른 서비스를 시행할 수 있기 때문
	 * 
	 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
	
	private static final String ORIGIN_FILE_PATH = "C:\\Users\\Daumsoft\\Desktop\\MelonLog\\click_log_2014";
	private static final String EXIST_FILE_PATH = "C:\\Users\\Daumsoft\\Desktop\\MelonLog\\result";
	
	public static void main(String[]args){
		TimeInflowCorrelat timeInflowCorrelat = new TimeInflowCorrelat();
		timeInflowCorrelat.getTimeInflowOnSite(ORIGIN_FILE_PATH);
	}
	
	public void getTimeInflowOnSite(String path){
		File[]dailyDirectoryList = new File(path).listFiles();
		int lastIndex = dailyDirectoryList.length - 1;
		
		HashMap<String, HashMap<String, Integer>> timeInflowMap = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, Integer> inflowMap = new HashMap<String, Integer>();
		
		File dailyDirectory = dailyDirectoryList[lastIndex];

		// 0000(시작), 0010, 0020 ... 2340, 2350(끝)
		ArrayList<TimeInflowMapper> list = new ArrayList<TimeInflowMapper>();
		
		// 파일 존재 여부 확인
		if(isExistFile(dailyDirectory.getName())){
			System.out.println("존재하는 파일 읽기");
			
			FileReader fr = null;
			BufferedReader br = null;
			String inputLine = null;
			
			try {
				fr = new FileReader(EXIST_FILE_PATH + "\\" + dailyDirectory.getName() + ".txt");
				br = new BufferedReader(fr);
				
				while((inputLine = br.readLine()) != null){
					System.out.println(inputLine);
				}
			} 
			catch (IOException e) {
				System.out.println(e.getMessage());
				System.out.println("파입 입출력 에러 그냥 종료");
				System.exit(1);
			}
			finally{
				
			}
		}// if
		
		else{
			System.out.println(dailyDirectory.getName());
			System.out.println("새롭게 쓰기");
			
			File[] logFileList = new File(dailyDirectory.getAbsolutePath()).listFiles();
			FileReader fr = null;
			BufferedReader br = null;
			
			// 로그 파일 읽기
			for(File logFile : logFileList){
				try {
					fr = new FileReader(logFile);
					br = new BufferedReader(fr);
					int lines = 0;
					
					while(br.readLine() != null)
						++lines;
					
					// 시간 split
					String time = logFile.getName().split("\\.")[3];
					inflowMap.put(time, lines);
					
				} 
				catch (IOException e) {
					System.out.println(e.getMessage());
					System.out.println("파일 입출력 하다가 에러 그냥 종료하자.");
					System.exit(1);
				}
			}
			
			timeInflowMap.put(dailyDirectory.getName(), inflowMap);
			
			Set<String> keySet = inflowMap.keySet();
			Iterator<String> it = keySet.iterator();
			
			while(it.hasNext()){
				String time = it.next();
				int lines = inflowMap.get(time);
				
				list.add(new TimeInflowMapper(time, lines));
			}
			
			// 정렬
			list.sort(new Comparator<TimeInflowMapper>(){
				@Override
				public int compare(TimeInflowMapper o1, TimeInflowMapper o2) {
					return Integer.parseInt(o1.getTime()) - Integer.parseInt(o2.getTime());
				}
			});
				
			File file = new File(EXIST_FILE_PATH + "\\" + dailyDirectory.getName() + ".txt");
			FileWriter fw = null;
			BufferedWriter bw = null;
			
			try {
				fw = new FileWriter(file);
				bw = new BufferedWriter(fw);
				
				// 파일 쓰기.
				for(int i = 0; i < list.size(); i++){
					System.out.println(list.get(i).getTime() + " : " + list.get(i).getLines() + " 줄");
					bw.write(list.get(i).getTime() + " : " + list.get(i).getLines() + " 줄");
					bw.write("\n");
				}
				
				bw.flush();
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				if(bw != null){
					try{
						bw.close();
					}
					catch(IOException e){
						e.printStackTrace();
					}
				}
			}
		}// else
	}
	
	private boolean isExistFile(String fileName){
		System.out.println(fileName);
		File file = new File(EXIST_FILE_PATH + "\\" + fileName + ".txt");
		
		return file.exists();
	}
}
