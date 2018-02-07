package com.doubler.main.db;

public class ColdDTO {

	private Integer sequenceNumber = null;			// 순서
	private Integer coldDate = null;				// 날짜	
	private String weekDay = null;					// 요일			
	private Integer treatment = null;				// 진료건수
	private Double lowestTemperature = null;		// 최저기온
	private Double diurnalRange = null;				// 일교차
	private Double moisture = null;					// 습도
	private Integer twitter = null;					// 트위터 건수
	private Integer news = null;					// 뉴스 건수
	private Integer sumTwitterAndNews = null;		// 트위터 + 뉴스 건수
	
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
	
	// getter() & setter()
	public Integer getsequenceNumber() {
		return sequenceNumber;
	}
	public void setsequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public Integer getColdDate() {
		return coldDate;
	}
	public void setColdDate(Integer coldDate) {
		this.coldDate = coldDate;
	}
	public String getWeekDay() {
		return weekDay;
	}
	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}
	public Integer getTreatment() {
		return treatment;
	}
	public void setTreatment(Integer treatment) {
		this.treatment = treatment;
	}
	public Double getLowestTemperature() {
		return lowestTemperature;
	}
	public void setLowestTemporature(Double lowestTemperature) {
		this.lowestTemperature = lowestTemperature;
	}
	public Double getDiuranalRange() {
		return diurnalRange;
	}
	public void setDiuranalRange(Double diurnalRange) {
		this.diurnalRange = diurnalRange;
	}
	public Double getMoisture() {
		return moisture;
	}
	public void setMoisture(Double moisture) {
		this.moisture = moisture;
	}
	public Integer getTwitter() {
		return twitter;
	}
	public void setTwitter(Integer twitter) {
		this.twitter = twitter;
	}
	public Integer getNews() {
		return news;
	}
	public void setNews(Integer news) {
		this.news = news;
	}
	public Integer getSumTwitterAndNews() {
		return sumTwitterAndNews;
	}
	public void setSumTwitterAndNews(Integer sumTwitterAndNews) {
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
