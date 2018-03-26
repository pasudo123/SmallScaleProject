package edu.doubler.course.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import edu.doubler.enum_api.EnumRouteOnSKT;

public class HttpClientLib {
	
	protected String getCoordinatesOnAPI(String uri){
		
		// [ GET ] : 주소를 좌표 변환
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet(uri);
		httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"); // UTF-8
		httpGet.setHeader("Accept", "application/json");
		
		return getResponse(httpClient, httpGet);
	}
	
	protected String getRouteOnAPI(String uri){
		
		// [ POST ] : 출발점과 도착점을 가지고 경로 표시
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpPost httpPost = new HttpPost(uri);
		
		// 헤더에 기본 내용 및 앱 키 추가
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"); // UTF-8
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader(EnumRouteOnSKT.HEADER.getKey(), EnumRouteOnSKT.HEADER.getValue());
		
		return getResponse(httpClient, httpPost);
	}
	
	// HttpResponse 일련의 동작
	private String getResponse(HttpClient httpClient, Object object){
		CloseableHttpResponse httpResponse = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		String inputLine = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			if(object instanceof HttpPost)
				httpResponse = (CloseableHttpResponse) httpClient.execute((HttpPost)object);
			if(object instanceof HttpGet)
				httpResponse = (CloseableHttpResponse) httpClient.execute((HttpGet)object);
			
			isr = new InputStreamReader(httpResponse.getEntity().getContent());
			br = new BufferedReader(isr);
			
			while((inputLine = br.readLine()) != null){
				sb.append(inputLine);
			}
			
			br.close();
			httpResponse.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
}
