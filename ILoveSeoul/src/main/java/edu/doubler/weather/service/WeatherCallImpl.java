package edu.doubler.weather.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.doubler.enum_api.EnumWeatherOnSKT;
import edu.doubler.weather.domain.WeatherContext;

@Service
public class WeatherCallImpl implements WeatherCall{

	private static final Logger logger = LoggerFactory.getLogger(WeatherCallImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public WeatherContext getWeatherContext(String lat, String lng) {
		
		// 날씨 정보에 대한 데이터
		WeatherContext weatherContext = new WeatherContext();
		
		String response = callApiAboutTMap(lat, lng);
		
		byte[] mapDatas = response.getBytes();
		ObjectMapper objectMapper = new ObjectMapper();
		
		objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		Map<String, Object> map = new HashMap<String, Object>();
		LinkedHashMap<String, Object> linkedMap = null;
		LinkedHashMap<String, Object> partLinkedMap = null;
		ArrayList<Object> list = null;
		
		try{
			map = objectMapper.readValue(mapDatas, HashMap.class);
			linkedMap = (LinkedHashMap<String, Object>) map.get("weather");
			list = (ArrayList<Object>) linkedMap.get("hourly");

			linkedMap = (LinkedHashMap<String, Object>) list.get(0);
			
			partLinkedMap = (LinkedHashMap<String, Object>) linkedMap.get("wind");
			weatherContext.setWdir((String) partLinkedMap.get("wdir"));	// 풍향
			weatherContext.setWspd((String) partLinkedMap.get("wspd"));	// 풍속
			
			partLinkedMap = (LinkedHashMap<String, Object>) linkedMap.get("precipitation");
			
			weatherContext.setType((String) partLinkedMap.get("type"));	// 강수형태 코드
			
			partLinkedMap = (LinkedHashMap<String, Object>) linkedMap.get("sky");
			weatherContext.setName((String) partLinkedMap.get("name"));	// 하늘 상태 코드
			
			partLinkedMap = (LinkedHashMap<String, Object>) linkedMap.get("temperature");
			weatherContext.setTc((String) partLinkedMap.get("tc"));
			weatherContext.setTmax((String) partLinkedMap.get("tmax"));
			weatherContext.setTmin((String) partLinkedMap.get("tmin"));
			weatherContext.setHuminity((String) linkedMap.get("humidity"));
		}
		
		catch(IOException e){
			e.printStackTrace();
		}
		
		return weatherContext;
	}
	
	private String callApiAboutTMap(String lat, String lng){
		
		/**
		 * https://hc.apache.org/httpcomponents-client-ga/tutorial/html/index.html
		 * **/
		
		/**
		 * HTTP에 대한 요청(request) & 응답(response)
		 * **/
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		String url = EnumWeatherOnSKT.WEATHER_URL.getURL();
		url += "?";
		url += "lat=" + lat;
		url += "&";
		url += "lon=" + lng;
		
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader(EnumWeatherOnSKT.HEADER_ACCEPT.getKey(), EnumWeatherOnSKT.HEADER_ACCEPT.getValue());
		httpGet.setHeader(EnumWeatherOnSKT.HEADER_CONTENT_TYPE.getKey(), EnumWeatherOnSKT.HEADER_CONTENT_TYPE.getValue());
		httpGet.setHeader(EnumWeatherOnSKT.APP_KEY.getKey(), EnumWeatherOnSKT.APP_KEY.getValue());
		
		CloseableHttpResponse httpResponse;
		InputStreamReader isr = null;
		BufferedReader br = null;
	
		// 한 줄씩 읽기 위함
		String inputLine;
		StringBuffer response = null;
				
		try{
			httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			
			isr = new InputStreamReader(httpEntity.getContent());
			br = new BufferedReader(isr);
			
			// 한 줄씩 읽기 위함
			response = new StringBuffer();
						
			while((inputLine = br.readLine()) != null){
				response.append(inputLine);
			}
			
			br.close();
			httpClient.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		return response.toString();
	}
}
