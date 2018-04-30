package edu.doubler.log_process.keyword;

import edu.doubler.log_process.domain.LogMapper;

public class KeywordLogProcessorImpl implements KeywordLogProcessor{
	@Override
	public LogMapper getLogMapper(String line){
		String[]logElement = line.split("\t");
		
		if(logElement.length < 8)
			return null;
		
		String date = logElement[0];
		String platform = logElement[1];
		String searchCategory = logElement[2];
		String highLevelCategory = logElement[3];
		String lowLevelCategory = logElement[4];
		String searchKeyword = logElement[5].replaceAll("\\s","");
		String categoryId = logElement[6];
		String memberId = logElement[7];
		
		// 파싱한 로그 매퍼 객체 
		LogMapper logMapper = new LogMapper(date, platform, searchCategory, highLevelCategory, lowLevelCategory, searchKeyword, categoryId, memberId);
		
		return logMapper;
	}

}
