package edu.doubler.weather.domain;

public class WeatherContext {
	/**
	 * wind : 바람정보
	 * **/
	private String wdir;		// 풍향(degree)
	private String wspd;		// 풍속(m/s)
	
	/**
	 * precipitation : 강수정보
	 * **/
	private String type;		// 강수형태 코드 (0:현상없음, -1:비, -2:비/눈, -3:눈)
	
	/**
	 * sky : 하늘상태 정보
	 * **/
	private String name;		// 하늘 상태 코드 명
	
	/**
	 * temperature : 기온정보
	 * **/
	private String tc;			// 1시간 현재 기온
	private String tmax;		// 오늘의 최고 기온
	private String tmin;		// 오늘의 최저 기온
	
	private String huminity;	// 상대습도

	// getter() & setter()
	public String getWdir() {
		return wdir;
	}

	public void setWdir(String wdir) {
		this.wdir = wdir;
	}

	public String getWspd() {
		return wspd;
	}

	public void setWspd(String wspd) {
		this.wspd = wspd;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTc() {
		return tc;
	}

	public void setTc(String tc) {
		this.tc = tc;
	}

	public String getTmax() {
		return tmax;
	}

	public void setTmax(String tmax) {
		this.tmax = tmax;
	}

	public String getTmin() {
		return tmin;
	}

	public void setTmin(String tmin) {
		this.tmin = tmin;
	}

	public String getHuminity() {
		return huminity;
	}

	public void setHuminity(String huminity) {
		this.huminity = huminity;
	}

	@Override
	public String toString() {
		return "WeatherContext [wdir=" + wdir + ", wspd=" + wspd + ", type=" + type + ", name=" + name + ", tc=" + tc
				+ ", tmax=" + tmax + ", tmin=" + tmin + ", huminity=" + huminity + "]";
	}
}
