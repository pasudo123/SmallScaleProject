package com.pasudo.submain;

import com.pasudo.main.EnumDatabase;
import com.pasudo.main.EnumParseFile;

public interface Integration {
	// 디비 커넥터 설정
	public void setConnectorMaker(EnumDatabase database);
	
	// 파싱 메이커 설정
	public void setParseMaker(EnumParseFile parseFile);
	
	// 파일 데이터를 데이터베이스로 변환
	public void file2Database();
	
	// 데이터베이스를 파일 데이터로 변환
	public void database2File(String standard, Integer order);
}
