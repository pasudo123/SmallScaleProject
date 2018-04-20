package edu.doubler.client.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.lang.StringBuilder;

public class AuthClientExample {
	
	// 게시판 리스트 조회 API
	public static void main(String[]args){
		String token = "YOUR_ACCESS_TOKEN";		// 3rd Application 이 발급받은 액세스 토큰 입력
		String header = "Bearer " + token;		// "Bearer" 다음에 공백 추가
		
		try{
			// 요청 API URL
			String apiURL = "http://localhost:8181/api_call/read";
			URL url = new URL(apiURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			// 'POST' 방식 이용
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", header);
			conn.setDoOutput(true);
			
			BufferedReader br = null;
			int responseCode = conn.getResponseCode();
			
			if(responseCode == 200){
				// 정상적으로 api 호출
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			}
			else{
				// 에러 발생
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			
			String inputLine;
			StringBuilder response = new StringBuilder();
			
			// 응답 데이터를 한 줄씩 읽어들인다.
			while((inputLine = br.readLine()) != null){
				response.append(inputLine);
			}
			
			br.close();
			System.out.println(response.toString());
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
}