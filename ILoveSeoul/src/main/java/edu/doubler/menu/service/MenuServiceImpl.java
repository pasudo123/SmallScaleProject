package edu.doubler.menu.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.doubler.domain.AreaBasedData;
import edu.doubler.domain.LocationBasedData;
import edu.doubler.enum_api.EnumParameterOnTourAPI;
import edu.doubler.enum_api.EnumSigunguCode;
import edu.doubler.enum_api.EnumURLOnTourAPI;

@Service
public class MenuServiceImpl implements MenuService {

	private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);
	private ObjectMapper objectMapper;
	private byte[] mapDatas;

	@Override
	public List<Object> selectListOnLocationBasedInfo(String mapX, String mapY, String radius) {
		String url = batchSettingUrl(EnumURLOnTourAPI.SELECT_LOCATION_BASED_LIST);

		// 위도, 경도, 반경 설정
		EnumParameterOnTourAPI.MAP_X.setValue(mapX);
		EnumParameterOnTourAPI.MAP_Y.setValue(mapY);
		EnumParameterOnTourAPI.RADIUS.setValue(radius);

		url += EnumParameterOnTourAPI.MAP_X.getKey() + "=" + EnumParameterOnTourAPI.MAP_X.getValue();
		url += "&";
		url += EnumParameterOnTourAPI.MAP_Y.getKey() + "=" + EnumParameterOnTourAPI.MAP_Y.getValue();
		url += "&";
		url += EnumParameterOnTourAPI.RADIUS.getKey() + "=" + EnumParameterOnTourAPI.RADIUS.getValue();

		EnumParameterOnTourAPI.MAP_X.setValue(null);
		EnumParameterOnTourAPI.MAP_Y.setValue(null);
		EnumParameterOnTourAPI.RADIUS.setValue(null);
		
		// url 출력
		logger.info(url);
				
		String response = useApiAboutTourAPI(url);
		
		return parseJson(response);
	}
	
	private String batchSettingUrl(EnumURLOnTourAPI urlType) {

		/**
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 * 
		 * 공통적으로 들어갈 파라미터에 대해서 설정 (일괄처리)
		 * 
		 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		 **/

		String url = urlType.getURL();
		url += "?";

		EnumParameterOnTourAPI[] enumParameter = EnumParameterOnTourAPI.values();

		for (EnumParameterOnTourAPI param : enumParameter) {
			if (param.getValue() != null) {
				url += param.getKey() + "=" + param.getValue();
				url += "&";
			}
		}

		return url;
	}
	
	// -- api 호출
	private String useApiAboutTourAPI(String url) {
		// reference :
		// https://www.journaldev.com/7146/apache-httpclient-example-closeablehttpclient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"); // UTF-8

		CloseableHttpResponse httpResponse;
		InputStreamReader isr = null;
		BufferedReader br = null;

		// 한 줄씩 읽기 위함
		String inputLine;
		StringBuffer response = null;

		try {
			httpResponse = httpClient.execute(httpGet);
			isr = new InputStreamReader(httpResponse.getEntity().getContent());
			br = new BufferedReader(isr);

			// 한 줄씩 읽기 위함
			response = new StringBuffer();

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			br.close();
			httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response.toString();
	}
	
	@SuppressWarnings("unchecked")
	private List<Object> parseJson(String paramResponse) {

		mapDatas = paramResponse.getBytes();
		objectMapper = new ObjectMapper();

		// reference :
		// http://www.mkyong.com/java/jackson-was-expecting-double-quote-to-start-field-name/
		objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		Map<String, Object> map = new HashMap<String, Object>();
		LinkedHashMap<String, Object> linkedMap = null;
		List<Map<String, String>> parseData = null;
		List<Object> dataList = null;
		
		try {
			/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
			 * 
			 * 		   경로를 타고 들어가 필요한 데이터를 반환
			 * 
			 * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/

			logger.info("데이터 파싱 시작 =====");
			
			map = objectMapper.readValue(mapDatas, HashMap.class);

			// response 내부 추출
			linkedMap = (LinkedHashMap<String, Object>) map.get("response");

			// body 내부 추출
			linkedMap = (LinkedHashMap<String, Object>) linkedMap.get("body");

			// items 내부 추출
			linkedMap = (LinkedHashMap<String, Object>) linkedMap.get("items");

			// -- 실질적인 필요한 내용
			// item 내부의 내용 List<Map<String, String>> 형태로 받기
			parseData = (ArrayList<Map<String, String>>) linkedMap.get("item");

			// logger.info(parseData.toString());
			
			logger.info("데이터 파싱 완료 =====");
			
			// 반환 데이터
			dataList = new ArrayList<Object>();
			
			for (Map<String, String> element : parseData) {
				String title = String.valueOf(element.get("title")); 					// 제목
				String addr = String.valueOf(element.get("addr1")); 					// 주소
				String tel = String.valueOf(element.get("tel")); 						// 전화번호
				String imageURL1 = String.valueOf(element.get("firstimage")); 			// 대표 이미지
				String imageURL2 = String.valueOf(element.get("firstimage2")); 			// 썸네일
				String mapX = String.valueOf(element.get("mapx")); 						// 위도 (가로)
				String mapY = String.valueOf(element.get("mapy")); 						// 경도 (세로)
				String readCount = String.valueOf(element.get("readcount")); 			// 조회수
				String contentTypeId = String.valueOf(element.get("contenttypeid")); 	// 관광타입
				
				LocationBasedData locationBasedData = new LocationBasedData();
				locationBasedData.setTitle(title);
				locationBasedData.setAddr(addr);
				locationBasedData.setTel(tel);
				locationBasedData.setImageURL1(imageURL1);
				locationBasedData.setImageURL2(imageURL2);
				locationBasedData.setMapX(mapX);
				locationBasedData.setMapY(mapY);
				locationBasedData.setReadCount(readCount);
				locationBasedData.setContentTypeId(contentTypeId);
				
				// item 추가
				dataList.add(locationBasedData);
			} // for

		} catch (IOException e) {
			e.printStackTrace();
		}

		return dataList;
	}
}