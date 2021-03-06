package edu.doubler.course.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class RouteInfo {
	private String Type;							// 경로 타입 (pointer || LineString)
	private String desc;							// 경로 설명 
	private String name;							// 경로 명칭
	private List<Coordinate> coordinateList;		// 좌표 배열
	private String time;							// 경로 통과 시간 (sec)
	private String distance;						// 경로 거리 (m)
	private String totalTime;						// 전체 소요 시간
	private String totalDistance;					// 전체 거리
	
	public RouteInfo(){
		coordinateList = new ArrayList<Coordinate>();
	}
	
	public void addCoordinate(double mapY, double mapX){
		Coordinate coordinate = new Coordinate(mapY, mapX);
		coordinateList.add(coordinate);
	}
	
	class Coordinate{
		double mapY;
		double mapX;
		
		Coordinate(double mapY, double mapX){
			this.mapY = mapY;
			this.mapX = mapX;
		}
		
		@Override
		public String toString() {
			return "[mapY=" + mapY + ", mapX=" + mapX + "]";
		}
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public String getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(String totalDistance) {
		this.totalDistance = totalDistance;
	}
	
	public List<Coordinate> getCoordinateList() {
		return coordinateList;
	}
	
	@Override
	public String toString() {
		return "RouteInfo [Type=" + Type + ", desc=" + desc + ", name=" + name + ", coordinateList=" + coordinateList 
				+ ", time=" + time + ", distance=" + distance + ", totalTime=" + totalTime + ", totalDistance="
				+ totalDistance + "]";
	}

	public LinkedHashMap<String, Object> getHashMap(){
		/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 
		 * https://stackoverflow.com/questions/10514473/string-to-hashmap-java
		 * 
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
		
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		
		/**
		 * 
		 * NULL 체크
		 * 
		 * **/
		if(getType() != null)
			map.put("type", this.getType());
		
		if(getDesc() != null)
			map.put("desc", this.getDesc());
		
		if(getName() != null)
			map.put("name", this.getName());
		
		if(getTime() != null)
			map.put("time", this.getTime());
		
		if(getDistance() != null)
			map.put("distance", this.getDistance());
		
		if(getTotalTime() != null)
			map.put("totalTime", this.getTotalTime());
		
		if(getTotalDistance() != null)
			map.put("totalDistance", this.getTotalDistance());
		
		if(getCoordinateList() != null)
			map.put("coordinateList", this.getCoordinateList().toString());

		return map;
	}
}
