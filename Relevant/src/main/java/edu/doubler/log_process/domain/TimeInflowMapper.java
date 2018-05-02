package edu.doubler.log_process.domain;

public class TimeInflowMapper {
	private String time;
	private int lines;
	
	public TimeInflowMapper(String time, int lines){
		this.time = time;
		this.lines = lines;
	}
	
	public String getTime(){
		return time;
	}
	
	public int getLines(){
		return lines;
	}
}
