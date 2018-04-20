package edu.doubler.client.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ClientExController {
	
	/**
	 * oauth Client 테스트를 위한 컨트롤러
	 * **/
	private static final String CLIENT_DIRECTORY = "client";
	
	Logger logger = LoggerFactory.getLogger(ClientExController.class);
	
	@RequestMapping(value="/oauth20/ex")
	public String exampleView(){
		return CLIENT_DIRECTORY + "/" + "oauth_ex_jsp_view";
	}
	
	@RequestMapping(value="/oauth20/ex/{scope}", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String beforeApiCall(
	@PathVariable("scope") String scope,
	HttpServletRequest request){
		
		String accessToken = request.getParameter("accessToken");
		String apiUrl = request.getParameter("apiUrl");
		HashMap<String, String> map = new HashMap<String, String>();
		
		if(scope.equals("read")){
			return httpConnection(null, accessToken, apiUrl);
		}
		
		if(scope.equals("write")){
			String name = request.getParameter("name");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			map.put("name", name);
			map.put("title", title);
			map.put("content", content);
			return httpConnection(map, accessToken, apiUrl);
			
		}
		
		if(scope.equals("delete")){
			String number = request.getParameter("number");
			map.put("number", number);
			return httpConnection(map, accessToken, apiUrl);
		}
		
		return null;
	}
	
	private String httpConnection(HashMap<String, String> map, String accessToken, String apiUrl){
		
		URL url = null;
		HttpURLConnection conn = null;
		String postParam = new String();
		String responseLine = null;
		
		try {
			String header = "Bearer " + accessToken;
			
			logger.info("== " + "api요청 시도");
			logger.info("== apiUrl : " + apiUrl);
			
			url = new URL(apiUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			
			// map 데이터를 삽입
			if(map != null){
				for(String key : map.keySet()){
					postParam += postParam + key + "=" + map.get(key) + "&";
//					conn.setRequestProperty(key, map.get(key));
				}
			}
			
			logger.info("== http 프로토콜로 전송 데이터 : " + postParam.toString());
			
			// 헤더에 데이터 추가
			conn.setRequestProperty("Authorization", header);
			conn.setDoOutput(true);
			conn.getOutputStream().write(postParam.getBytes());
			
			BufferedReader br = null;
			
			int responseCode = conn.getResponseCode();
			if(responseCode == 200){	
				logger.info("== " + "api요청 성공");
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));	
			}
			else{
				logger.info("== " + "api요청 실패");
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				
				return null;
			}
			
			String inputLine = null;
			StringBuilder sb = new StringBuilder();
			while((inputLine = br.readLine()) != null)
				sb.append(inputLine);
			
			br.close();
			responseLine = sb.toString();
		}
		catch(Exception e){
			logger.info(e.getMessage());
		}
		
		logger.info("== " + responseLine);
		
		return responseLine;
	}
}
