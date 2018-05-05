package edu.doubler.log_process.keyword;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

import org.json.simple.JSONObject;

import edu.doubler.log_process.BufferedReaderCallback;
import edu.doubler.log_process.domain.KeywordMentionMapper;
import edu.doubler.log_process.domain.LogMapper;

public class KeywordMentionCorrelat {
	
	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * 
	 * [ 키워드 언급량 ]
	 * 
	 * - 템플릿 콜백 메소드 패턴 이용
	 * - 파일의 마지막 날
	 * - 파일의 마지막 날   ~  7일 (일 주일)
	 * - 파일의 마지막 날   ~ 30일    (한 달)
	 * 
	 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
	
	boolean isPossibleDaily = false;
	boolean isPossibleWeekly = false;
	boolean isPossibleMonthly = false;
	
	private Hashtable<String, Integer> correlatDailyMentionTable;
	private Hashtable<String, Integer> correlatWeeklyMentionTable[];
	private Hashtable<String, Integer> correlatMonthlyMentionTable;
	
	public static void main(String[]args){
		KeywordMentionCorrelat keywordMentionCorrelat = new KeywordMentionCorrelat();
		keywordMentionCorrelat.getKeywordMention("C:\\Users\\Daumsoft\\Desktop\\MelonLog\\click_log_2014");
	}
	
	public void getKeywordMention(String path){

		BufferedReaderCallback brCallback = new BufferedReaderCallback(){
			@Override
			public Hashtable<String, Integer> doSomethingWithReader(File file) throws IOException {
				
				String line = null;
				KeywordLogProcessor keywordLogProcessor = new KeywordLogProcessorImpl();
				Hashtable<String, Integer> table = new Hashtable<String, Integer>();
				
				FileInputStream fis = new FileInputStream(file.getAbsolutePath());
				BufferedReader br = new BufferedReader(new InputStreamReader(fis, "euc-kr"));

				while((line = br.readLine()) != null){
					LogMapper logMapper = keywordLogProcessor.getLogMapper(line);
					
					if(logMapper != null){
						String keyword = logMapper.getSearchKeyword();
						
						/** 불필요한 키워드는 따로 저장 혹은 생략 선택 **/
						if(KeywordLogLauncher.isUselessPattern(keyword)){
//							writeUselessLog(line);
							continue;
						}
						
						if(table.get(keyword) == null){
							table.put(keyword, 1);
						}
						else
							table.put(keyword, table.get(keyword) + 1);
					}
					else{
						/** null 데이터면 해당 Line 따로 저장 혹은 생략 선택 **/
						
					}
				}// while : 한 줄씩 읽음
				
				br.close();
				
				// 전달받은 BufferedReader 객체로부터 모두 읽어들이고 이후에 반환
				return table;
			}
		};
		
		process(path, brCallback); 
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void process(String path, BufferedReaderCallback brCallback){
		
		ArrayList<Hashtable<String, Integer>> monthlyMentionList = null;
		ArrayList<Hashtable<String, Integer>> weeklyMentionList[] = null;
		Hashtable<String, Integer> dailyMentionTable = null;
		
		int monthCount = 0;
		int weekCount = 0, weekIndex = 0;
		
		
		try{
			File[]dailyDirectoryList = new File(path).listFiles();
			int directorySize = dailyDirectoryList.length;
			
			if(directorySize >= KeywordMentionEnum.MENTION_ON_WEEKLY.getDays()){
				int weeklySize = directorySize / KeywordMentionEnum.MENTION_ON_WEEKLY.getDays();
				weeklyMentionList = new ArrayList[weeklySize];
				
				for(int i = 0; i < weeklySize; i++)
					weeklyMentionList[i] = new ArrayList<Hashtable<String, Integer>>();
				
				isPossibleWeekly = true;
			}
			
			if(directorySize >= KeywordMentionEnum.MENTION_ON_MONTH.getDays()){
				monthlyMentionList = new ArrayList<Hashtable<String, Integer>>();
				isPossibleMonthly = true;
			}
						
			isPossibleDaily = true;
			
			/**
			 * 폴더 및 파일 읽기
			 * **/
			// 디렉토리 리스트 (한달치)
			for(int dirIndex = directorySize-1; dirIndex >= 0; dirIndex--){
				System.out.println(dailyDirectoryList[dirIndex].getAbsolutePath());
				
				String absolutePath = dailyDirectoryList[dirIndex].getAbsolutePath();
				File dailyFile = new File(absolutePath);
				
				File[] logFileList = dailyFile.listFiles();
				
				int tableIndex = 0;
				int length = logFileList.length;
				
				Hashtable[] table = new Hashtable[length];
				
				for(File logFile : logFileList){
					table[tableIndex++] = brCallback.doSomethingWithReader(logFile);
				}
				
				weekCount++;
				monthCount++;
				dailyMentionTable = calcMention(table);
				
				// 일간 : 처음 단 한번만 수행한다.
				if(isPossibleDaily){
					correlatDailyMentionTable = dailyMentionTable;
					isPossibleDaily = false;
				}
				
				// 주간 : 누적
				if(isPossibleWeekly){
					if(weekCount <= KeywordMentionEnum.MENTION_ON_WEEKLY.getDays()){
						weeklyMentionList[weekIndex].add(dailyMentionTable);
					}
					else{
						if(weekIndex + 1 < weeklyMentionList.length){
							weeklyMentionList[++weekIndex].add(dailyMentionTable);
							weekCount = 1;
						}
					}
				}
				
				// 월간 : 누적
				if(isPossibleMonthly && monthCount <= KeywordMentionEnum.MENTION_ON_MONTH.getDays()){
					monthlyMentionList.add(dailyMentionTable);
				}
				
			}// 디렉토리 다 읽어들임
			
			
			/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
			 * 
			 * - 일간 상위 키워드 정리
			 * - 주간 상위 키워드 정리
			 * - 월간 상위 키워드 정리
			 * 
			 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
			
			// 주간 상위 키워드를 추출할 수 있는 경우
			if(isPossibleWeekly){
				int length = weeklyMentionList.length;
				Hashtable[][] weeklyMetionTables = new Hashtable[weeklyMentionList.length][7];
				correlatWeeklyMentionTable = new Hashtable[weeklyMentionList.length];
				
				for(int i = 0; i < length; i++){
					for(int j = 0; j < weeklyMentionList[i].size(); j++){
						weeklyMetionTables[i][j] = weeklyMentionList[i].get(j);
					}
					
					correlatWeeklyMentionTable[i] = calcMention(weeklyMetionTables[i]);
				}
			}
			
			// 월간 상위 키워드를 추출할 수 있는 경우
			if(isPossibleMonthly){
				int size = monthlyMentionList.size();
				Hashtable[] monthlyMentionTables = new Hashtable[size];
				
				for(int i = 0; i < monthlyMentionList.size(); i++)
					monthlyMentionTables[i] = monthlyMentionList.get(i);
				
				correlatMonthlyMentionTable = calcMention(monthlyMentionTables);
			}
			
			System.out.println("===============");
			System.out.println(correlatDailyMentionTable.size());
			for(int i = 0; i < correlatWeeklyMentionTable.length; i++)
				System.out.print(correlatWeeklyMentionTable[i].size() + "  ");
			System.out.println();
			System.out.println(correlatMonthlyMentionTable.size());
			
			// 해당 횟수 미만은 제거.
			reductionTable(correlatDailyMentionTable, 100);
			reductionTable(correlatWeeklyMentionTable, 500);
			reductionTable(correlatMonthlyMentionTable, 1000);
			
			System.out.println("===============");
			System.out.println("== 카운트 미만 키워드 제거 ==");
			System.out.println(correlatDailyMentionTable.size());
			for(int i = 0; i < correlatWeeklyMentionTable.length; i++)
				System.out.print(correlatWeeklyMentionTable[i].size() + "  ");
			System.out.println();
			System.out.println(correlatMonthlyMentionTable.size());
			
			/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
			 * 
			 * 키워드 & 언급량이 정렬된 ArrayList 반환
			 * 
			 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
			ArrayList<KeywordMentionMapper> sortedDailyMention = getSortedKeywordMentionArray(correlatDailyMentionTable);
			ArrayList<KeywordMentionMapper> sortedWeeklyMention[] = getSortedKeywordMentionArray(correlatWeeklyMentionTable);
			ArrayList<KeywordMentionMapper> sortedMonthlyMention = getSortedKeywordMentionArray(correlatMonthlyMentionTable);
			
			// [ 3개 - 5개 - 10개  ]
			System.out.println("== 일간 상위 키워드 ==");
			for(int i = sortedDailyMention.size() - 1; i >= sortedDailyMention.size() - 3; i--)
				System.out.println(sortedDailyMention.get(i).getKeyword() + " : " + sortedDailyMention.get(i).getMentionCount());
			
			System.out.println("== 주간 상위 키워드 ==");
			for(int i = 0; i < sortedWeeklyMention.length; i++){
				System.out.println("== " + (sortedWeeklyMention.length - i) + " 주차 상위 키워드 ==");
				for(int j = sortedWeeklyMention[i].size() - 1; j >= sortedWeeklyMention[i].size() - 5; j--){
					System.out.println(sortedWeeklyMention[i].get(j).getKeyword() + " : " + sortedWeeklyMention[i].get(j).getMentionCount());
				}
			}
			
			System.out.println("== 월간 상위 키워드 ==");
			for(int i = sortedMonthlyMention.size() - 1; i >= sortedMonthlyMention.size() - 10; i--)
				System.out.println(sortedMonthlyMention.get(i).getKeyword() + " : " + sortedMonthlyMention.get(i).getMentionCount());
			
			System.out.println("\n\n===================");
			System.out.println("== 일간 상위 키워드 ==");
			System.out.println(toJsonString(sortedDailyMention));
			for(int i = 0; i < sortedWeeklyMention.length; i++){
				System.out.println("== " + (sortedWeeklyMention.length - i) + " 주차 상위 키워드 ==");
				System.out.println(toJsonString(sortedWeeklyMention[i]));
			}
			System.out.println("== 월간 상위 키워드 ==");
			System.out.println(toJsonString(sortedMonthlyMention));
		}
		catch(IOException e){
			System.out.println(e.getMessage());
			System.out.println("문제 발생");
			System.exit(1);
		}
	}
	
	// table 배열을 하나의 테이블로 전환 : 언급량 누적 
	@SuppressWarnings("rawtypes")
	private Hashtable<String, Integer> calcMention(Hashtable[]table){
		
		Hashtable<String, Integer> resTable = new Hashtable<String, Integer>();
		
		/** 테이블 배열에 있는 모든 키워드들을 포함 **/
		for(int i = 0; i < table.length; i++){
			@SuppressWarnings("unchecked")
			Iterator<String> it = table[i].keySet().iterator();
			
			while(it.hasNext()){
				String key = it.next();
				Integer cnt = (Integer)table[i].get(key);
				
				if(resTable.get(key) == null)
					resTable.put(key, cnt);
				else
					resTable.put(key, resTable.get(key) + cnt);
			}
		}
		
		
		return resTable;
	}
	
	// deleteCount 미만의 횟수를 가진 키워드 삭제
	private void reductionTable(Hashtable<String, Integer> table, int deleteCount){
		Object dummy = new Object();
		synchronized(dummy){
			Enumeration<String> enumeration = table.keys();
			try{
				while (enumeration.hasMoreElements()) {
					String key = enumeration.nextElement();
					int cnt = table.get(key);
		
					if (cnt < deleteCount)
						table.remove(key);
				}
			}
			catch(ConcurrentModificationException e){
				System.out.println(e.getMessage());
				System.out.println("해쉬테이블 Iterator 부분에서 오류");
				System.exit(1);
			}
		}
	}
	
	// deleteCount 미만의 횟수를 가진 키워드 삭제
	// 주간 키워드에 대한 [ 메소드 오버로딩  ]
	private void reductionTable(Hashtable<String, Integer> table[], int deleteCount){
		Object dummy = new Object();
		
		for(int i = 0; i < table.length; i++){
			synchronized(dummy){
				Enumeration<String> enumeration = table[i].keys();
				try{
					while (enumeration.hasMoreElements()) {
						String key = enumeration.nextElement();
						int cnt = table[i].get(key);
			
						if (cnt < deleteCount)
							table[i].remove(key);
					}
				}
				catch(ConcurrentModificationException e){
					System.out.println(e.getMessage());
					System.out.println("해쉬테이블 Iterator 부분에서 오류");
					System.exit(1);
				}
			}
		}// for
	}
	
	// List 를 Json 으로 변환
	@SuppressWarnings("unchecked")
	private String toJsonString(ArrayList<KeywordMentionMapper> list){
		int length = list.size()-1;
		JSONObject jsonObject = new JSONObject();
		
		for(int i = length; i >= 0; i--){
			jsonObject.put(list.get(i).getKeyword(), list.get(i).getMentionCount());
		}
		
		return jsonObject.toJSONString();
	}
	
	// 
	
	// 문자열을 JSON으로 변환
	@SuppressWarnings("unchecked")
	private String toJsonString(Hashtable<String, Integer> table){
		// 하루치 키워드 언급량
		
		JSONObject dailyJsonObject = new JSONObject();
		for(Entry<String, Integer> entrySet : table.entrySet()){
			String key = entrySet.getKey();
			dailyJsonObject.put((String)key, String.valueOf(table.get(key)));
		}
		
		return dailyJsonObject.toJSONString();
	}

	private ArrayList<KeywordMentionMapper> getSortedKeywordMentionArray(Hashtable<String, Integer> table){
		ArrayList<KeywordMentionMapper> list = new ArrayList<KeywordMentionMapper>();

		Enumeration<String> enumeration = table.keys();
		while(enumeration.hasMoreElements()){
			String keyword = enumeration.nextElement();
			int mentionCount = table.get(keyword);
			
			list.add(new KeywordMentionMapper(keyword, mentionCount));
		}
		
		list.sort(new Comparator<KeywordMentionMapper>(){
			@Override
			public int compare(KeywordMentionMapper obj1, KeywordMentionMapper obj2) {
				return (obj1.getMentionCount() - obj2.getMentionCount());
			}
		});
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	// [ 메소드 오버로딩  ]
	private ArrayList<KeywordMentionMapper>[] getSortedKeywordMentionArray(Hashtable<String, Integer> table[]){
		ArrayList<KeywordMentionMapper> list[] = new ArrayList[table.length];
		
		for(int i = 0; i < list.length; i++)
			list[i] = new ArrayList<KeywordMentionMapper>();
		
		for(int i = 0; i < table.length; i++){
			Enumeration<String> enumeration = table[i].keys();
			while(enumeration.hasMoreElements()){
				String keyword = enumeration.nextElement();
				int mentionCount = table[i].get(keyword);
				
				list[i].add(new KeywordMentionMapper(keyword, mentionCount));
			}
			
			list[i].sort(new Comparator<KeywordMentionMapper>(){
				@Override
				public int compare(KeywordMentionMapper obj1, KeywordMentionMapper obj2) {
					return (obj1.getMentionCount() - obj2.getMentionCount());
				}
			});
		}
		
		return list;
	}
	
	private void writeUselessLog(String line){
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			fw = new FileWriter("C:\\Users\\Daumsoft\\Desktop\\MelonLog\\useless\\uselessLog.log", true);
			bw = new BufferedWriter(fw);
			
			bw.write(line);
			bw.write("\n");
			bw.flush();
			bw.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
