package edu.doubler.relevant.keyword;

import edu.doubler.log_process.domain.LogMapper;

public interface KeywordLogProcessor {
	// log 데이터 파싱 및 리턴
	public LogMapper getLogMapper(String line);
}
