package com.doubler.main.db;

public class ColdDTO {

	private int sequenceNumber = 0;			// 순서
	private int coldDate = 0;				// 날짜	
	private String weekDay = null;			// 요일			
	private int treatment = 0;				// 진료건수
	private double lowestTemperature = 0;	// 최저기온
	private double diurnalRange = 0;		// 일교차
	private double moisture = 0;			// 습도
	private int twitter = 0;				// 트위터 건수
	private int news = 0;					// 뉴스 건수
	private int sumTwitterAndNews = 0;		// 트위터 + 뉴스 건수
	
	// 디폴트 생성자
	public ColdDTO(){}
	
	// 생성자
	public ColdDTO(Integer sequenceNumber, Integer coldDate, String weekDay, Integer treatment, Double lowestTemperature,
			Double diurnalRange, Double moisture, Integer twitter, Integer news, Integer sumTwitterAndNews) {
		super();
		this.sequenceNumber = sequenceNumber;
		this.coldDate = coldDate;
		this.weekDay = weekDay;
		this.treatment = treatment;
		this.lowestTemperature = lowestTemperature;
		this.diurnalRange = diurnalRange;
		this.moisture = moisture;
		this.twitter = twitter;
		this.news = news;
		this.sumTwitterAndNews = sumTwitterAndNews;
	}
	
	
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public int getColdDate() {
		return coldDate;
	}

	public void setColdDate(int coldDate) {
		this.coldDate = coldDate;
	}

	public String getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}

	public int getTreatment() {
		return treatment;
	}

	public void setTreatment(int treatment) {
		this.treatment = treatment;
	}

	public double getLowestTemperature() {
		return lowestTemperature;
	}

	public void setLowestTemperature(double lowestTemperature) {
		this.lowestTemperature = lowestTemperature;
	}

	public double getDiurnalRange() {
		return diurnalRange;
	}

	public void setDiurnalRange(double diurnalRange) {
		this.diurnalRange = diurnalRange;
	}

	public double getMoisture() {
		return moisture;
	}

	public void setMoisture(double moisture) {
		this.moisture = moisture;
	}

	public int getTwitter() {
		return twitter;
	}

	public void setTwitter(int twitter) {
		this.twitter = twitter;
	}

	public int getNews() {
		return news;
	}

	public void setNews(int news) {
		this.news = news;
	}

	public int getSumTwitterAndNews() {
		return sumTwitterAndNews;
	}

	public void setSumTwitterAndNews(int sumTwitterAndNews) {
		this.sumTwitterAndNews = sumTwitterAndNews;
	}

	@Override
	public String toString() {
		return "ColdDTO [sequenceNumber=" + sequenceNumber + ", coldDate=" + coldDate + ", weekDay=" + weekDay
				+ ", treatment=" + treatment + ", lowestTemporature=" + lowestTemperature + ", diurnalRange="
				+ diurnalRange + ", moisture=" + moisture + ", twitter=" + twitter + ", news=" + news
				+ ", sumTwitterAndNews=" + sumTwitterAndNews + "]";
	}
}
