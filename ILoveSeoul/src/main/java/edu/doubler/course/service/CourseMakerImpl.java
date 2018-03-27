package edu.doubler.course.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.doubler.course.domain.EndAddress;
import edu.doubler.course.domain.MyAddress;
import edu.doubler.course.domain.RouteInfo;
import edu.doubler.enum_api.EnumFullTextGeocodingOnSKT;
import edu.doubler.enum_api.EnumRouteOnSKT;

@Service
public class CourseMakerImpl implements CourseMaker{

	private static final Logger logger = LoggerFactory.getLogger(CourseMakerImpl.class);
	
	@Override
	public MyAddress geocoding(String fullAddress) {
		
		String uri = new String();
		uri += EnumFullTextGeocodingOnSKT.URI.getValue();
		uri += "?";
		
		// SKT API 지오코딩 파라미터
		EnumFullTextGeocodingOnSKT[] enumGeocoding = EnumFullTextGeocodingOnSKT.values();
		MyAddress myAddress = new MyAddress();
		
		for(EnumFullTextGeocodingOnSKT geocoding : enumGeocoding){
			if(geocoding == EnumFullTextGeocodingOnSKT.URI)
				continue;
			
			if(geocoding.getValue() == null){
				// 주소 세팅
				myAddress.setFullAddress(fullAddress);
				
				fullAddress = fullAddress.replaceAll(" ", "+");
				geocoding.setValue(fullAddress);
			}
			
			uri += geocoding.getKey() + "=" + geocoding.getValue();
			
			// 주소 세팅을 다시 지운다.
			if(geocoding == EnumFullTextGeocodingOnSKT.FULL_ADDR && geocoding.getValue() != null)
				geocoding.setValue(null);
			
			// 맨 마지막은 앤드 연산자 붙이지 않는다.
			if(geocoding != EnumFullTextGeocodingOnSKT.APP_KEY)
				uri += "&";
		}
		
		logger.info(uri);
		
		// api 호출 및 반환 받은 데이터 파싱 작업 실시 >> 좌표 추출
		HttpClientLib httpClientLib = new HttpClientLib();
		parsingData(myAddress, httpClientLib.getCoordinatesOnAPI(uri));
		
		return myAddress;
	}
	
	@SuppressWarnings("unchecked")
	private void parsingData(MyAddress myAddress, String result){
		
		byte[]mapData = result.getBytes();
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<Object> list = null;
		
		try{
			
		   /**********************************************************
			* 
			* 주소가 똑바로 입력 안된 경우 null 값이 뜨기 때문에 에러 처리 반드시 해주기
			* 
			*********************************************************/
			
			map = objectMapper.readValue(mapData, HashMap.class);
			map = (Map<String, Object>) map.get("coordinateInfo");
			list = (ArrayList<Object>) map.get("coordinate");
			map = (Map<String, Object>) list.get(0);

			String latitude = (String)map.get("lat");
			String longitude = (String)map.get("lon");
			
			myAddress.setLatitude(latitude);
			myAddress.setLongitude(longitude);
		}
		catch(IOException e){
			// 주소 똑바로 입력하라고 다시 클라이언트에게 알려준다.
			
			myAddress = null;
			e.printStackTrace();
			return;
		}
	}

	@Override
	public List<RouteInfo> getRoute(MyAddress startPoint, EndAddress endPoint) {
		
		String uri = new String();
		uri += EnumRouteOnSKT.URI.getValue();
		uri += "?";
		
		EnumRouteOnSKT[]enumRouteOnSKT = EnumRouteOnSKT.values();
		
//		logger.info(startPoint.getLatitude() + ", " + startPoint.getLongitude());
//		logger.info(endPoint.getLatitude() + ", " + endPoint.getLongitude());
		
		for(EnumRouteOnSKT routeElement : enumRouteOnSKT){
			if(routeElement == EnumRouteOnSKT.URI)
				continue;
			
			if(routeElement == EnumRouteOnSKT.END_X)
				routeElement.setValue(endPoint.getLatitude());
			
			if(routeElement == EnumRouteOnSKT.END_Y)
				routeElement.setValue(endPoint.getLongitude());
			
			if(routeElement == EnumRouteOnSKT.START_Y)
				routeElement.setValue(startPoint.getLatitude());
			
			if(routeElement == EnumRouteOnSKT.START_X)
				routeElement.setValue(startPoint.getLongitude());
			
			// 헤더만 제외
			if(routeElement != EnumRouteOnSKT.HEADER){
				uri += routeElement.getKey() + "=" + routeElement.getValue();
				uri += "&";
			}
		}
		
		// 마지막 '&' 연산 삭제
		uri = uri.substring(0, uri.length()-1);
		
		logger.info(uri);
		
		// httpClient POST 요청
		HttpClientLib httpClientLib = new HttpClientLib();
		String result = httpClientLib.getRouteOnAPI(uri);
		
		// 경남 통영시 북신동 일성한빛타운
		return parsingRouteData(result);
	}
	
	@SuppressWarnings("unchecked")
	private List<RouteInfo> parsingRouteData(String result){
		byte[]mapData = result.getBytes();
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		LinkedHashMap<String, Object> linkedMap = new LinkedHashMap<String, Object>();
		ArrayList<Object> list = null;
		
		// 경로 관련 리스트 및 클래스
		ArrayList<RouteInfo> routeList = new ArrayList<RouteInfo>();
		ArrayList<Object> coordList = new ArrayList<Object>();
		ArrayList<ArrayList<Object>> doubleCoordList = new ArrayList<ArrayList<Object>>();
		RouteInfo routeInfo = null;
		
		try{
			map = objectMapper.readValue(mapData, HashMap.class);
			list = (ArrayList<Object>) map.get("features");
			
			linkedMap = (LinkedHashMap<String, Object>) list.get(0);
			
			// 경로들을 하나씩 삽입
			/*************************************************
			 * 
			 * routeInfo 클래스에 삽입
			 * List<routeInfo> 리스트에 삽입
			 * 
			 *************************************************/
			for(int i = 0; i < list.size(); i++){
				routeInfo = new RouteInfo();
				
				map = (Map<String, Object>) list.get(i);
				linkedMap = (LinkedHashMap<String, Object>) map.get("geometry");
				
				// type & coordinate 추출
				routeInfo.setType((String)linkedMap.get("type"));
				coordList = (ArrayList<Object>) linkedMap.get("coordinates");
				
				// 일반 배열 (위도 경도)
				if(!coordList.toString().contains("[[")){
					double mapY = Double.parseDouble(String.valueOf(coordList.get(1)));
					double mapX = Double.parseDouble(String.valueOf(coordList.get(0)));
					
					routeInfo.addCoordinate(mapY, mapX);
				}
				// 이중 배열 ([(위도,경도)], ...)
				else{
					doubleCoordList = (ArrayList<ArrayList<Object>>) linkedMap.get("coordinates");
					
					for(int j = 0; j < doubleCoordList.size(); j++){
						double mapY = Double.parseDouble(String.valueOf(doubleCoordList.get(j).get(1)));
						double mapX = Double.parseDouble(String.valueOf(doubleCoordList.get(j).get(0)));
						
						routeInfo.addCoordinate(mapY, mapX);
					}
				}
				
				// 속성 추출
				linkedMap = (LinkedHashMap<String, Object>) map.get("properties");
				
				routeInfo.setDesc((String)linkedMap.get("description"));
				routeInfo.setName((String)linkedMap.get("name"));
				
				if(String.valueOf(linkedMap.get("distance")) != null)
					routeInfo.setDistance(String.valueOf(linkedMap.get("distance")));
				
				if(String.valueOf(linkedMap.get("time")) != null)
					routeInfo.setTime(String.valueOf(linkedMap.get("time")));
				
				if(String.valueOf(linkedMap.get("totalDistance")) != null)
					routeInfo.setTotalDistance(String.valueOf(linkedMap.get("totalDistance")));
				
				if(String.valueOf(linkedMap.get("totalTime")) != null)
					routeInfo.setTotalTime(String.valueOf(linkedMap.get("totalTime")));
				
				routeList.add(routeInfo);
			}// for 구문
		}
		catch(IOException e){
			e.printStackTrace();
			return null;
		}
		
		// 경남 통영시 북신동 일성한빛타운
//		for(int i = 0; i < routeList.size(); i++){
//			logger.info( i + " : " + routeList.get(i));
//		}
		
		return routeList;
	}
}
