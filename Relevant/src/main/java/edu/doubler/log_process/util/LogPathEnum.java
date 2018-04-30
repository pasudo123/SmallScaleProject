package edu.doubler.log_process.util;

public enum LogPathEnum {
	LOG_DIRECTORY_PATH("C:\\Users\\Daumsoft\\Desktop\\MelonLog\\click_log_2014");
	
	private String path;
	
	private LogPathEnum(String path){
		this.path = path;
	}
	
	public String getPath(){
		return path;
	}
}
