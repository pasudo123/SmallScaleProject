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
import edu.doubler.enum_api.EnumParameterOnTourAPI;
import edu.doubler.enum_api.EnumSigunguCode;
import edu.doubler.enum_api.EnumURLOnTourAPI;

@Service
public class MenuServiceImpl implements MenuService{

	private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);
	private ObjectMapper objectMapper;
	private byte[] mapDatas;
	
	@Override
	public List<AreaBasedData> selectList(String parentCate, String childCate) {
		String response = setUrl(parentCate, childCate);
		return parseJson(response);
	}
	
	
	private String setUrl(String content, String sigungu){
		// 지역기반 관광리스트 조사
		String url = EnumURLOnTourAPI.SELECT_AREA_BASED_LIST.getURL();
		
		
		// 요청 기본 파라미터 
		// 인증키, 서비스 명, OS 구분, 한 페이지 결과 수, 페이지 번호
		url += "?";
		url += EnumParameterOnTourAPI.KEY.getKey() + "=" + EnumParameterOnTourAPI.KEY.getValue();
		url += "&";
		url += EnumParameterOnTourAPI.MOBILE_APP.getKey() + "=" + EnumParameterOnTourAPI.MOBILE_APP.getValue();
		url += "&";
		url += EnumParameterOnTourAPI.MOBILE_OS.getKey() + "=" + EnumParameterOnTourAPI.MOBILE_OS.getValue();
		url += "&";
		url += EnumParameterOnTourAPI.NUMBER_OF_ROWS.getKey() + "=" + EnumParameterOnTourAPI.NUMBER_OF_ROWS.getValue();
		url += "&";
		url += EnumParameterOnTourAPI.PAGE_NO.getKey() + "=" + EnumParameterOnTourAPI.PAGE_NO.getValue();
		
		
		// 지역기반 설정 파라미터
		// 정렬 구분, 지역코드
		url += "&";
		url += EnumParameterOnTourAPI.ARRANGE.getKey() + "=" + EnumParameterOnTourAPI.ARRANGE.getValue();
		url += "&";
		url += EnumParameterOnTourAPI.AREA_CODE.getKey() + "=" + EnumParameterOnTourAPI.AREA_CODE.getValue();
		
		
		// JSON 설정
		url += "&";
		url += EnumParameterOnTourAPI.JSON.getKey() + "=" + EnumParameterOnTourAPI.JSON.getValue();
		
		
		// 관광 타입 설정
		url += "&";
		EnumSigunguCode[]contentArray = EnumSigunguCode.values();
		for(EnumSigunguCode contentType : contentArray){
			if(contentType.getName().equals(content)){
				url += "&";
				url += contentType.getKey() + "=" + contentType.getCode();
				break;
			}
		}
		
		
		// 자치구 설정
		EnumSigunguCode[]sigunguArray = EnumSigunguCode.values();
		for(EnumSigunguCode sigunguType : sigunguArray){
			if(sigunguType.getName().equals(sigungu)){
				url += "&";
				url += sigunguType.getKey() + "=" + sigunguType.getCode();
				break;
			}
		}
		
		
		return useApiAboutTourAPI(url);
	}
	
	private String useApiAboutTourAPI(String url){
		// reference : https://www.journaldev.com/7146/apache-httpclient-example-closeablehttpclient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");	// UTF-8 인코딩
		
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
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

		return response.toString();
	}
	
	@SuppressWarnings("unchecked")
	private List<AreaBasedData> parseJson(String paramResponse){
		
		mapDatas = paramResponse.getBytes();
		objectMapper = new ObjectMapper();
		
		// reference : http://www.mkyong.com/java/jackson-was-expecting-double-quote-to-start-field-name/
		objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		Map<String, Object> map = new HashMap<String, Object>();
		LinkedHashMap<String, Object> linkedMap = null;
		List<Map<String, String>> parseData = null;
		List<AreaBasedData> dataList = new ArrayList<AreaBasedData>();
		
		try {
			// 경로를 타고 필요한 데이터를 획득하도록 한다.
			
			logger.info("데이터 파싱 =====");
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
			
//			logger.info(parseData.toString());
			
			for(Map<String, String> element : parseData){
				String title = String.valueOf(element.get("title"));					// 제목
				String addr = String.valueOf(element.get("addr1"));						// 주소
				String tel = String.valueOf(element.get("tel"));						// 전화번호
				String imageURL = String.valueOf(element.get("firstimage"));			// 대표 이미지
				String mapX = String.valueOf(element.get("mapx"));						// 위도 (가로)
				String mapY = String.valueOf(element.get("mapy"));						// 경도 (세로)
				String readCount = String.valueOf(element.get("readcount"));			// 조회수
				String contentTypeId = String.valueOf(element.get("contenttypeid"));	// 관광타입
				String sigunguCode = String.valueOf(element.get("sigungucode"));		// 시군구코드
				
				AreaBasedData areaBaseData = new AreaBasedData();
				areaBaseData.setTitle(title);
				areaBaseData.setAddr(addr);
				areaBaseData.setTel(tel);
				areaBaseData.setImageURL(imageURL);
				areaBaseData.setMapX(mapX);
				areaBaseData.setMapY(mapY);
				areaBaseData.setReadCount(readCount);
				areaBaseData.setContentTypeId(contentTypeId);
				areaBaseData.setSigunguCode(sigunguCode);
				
				dataList.add(areaBaseData);
			}// for
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return dataList;
	}
}
