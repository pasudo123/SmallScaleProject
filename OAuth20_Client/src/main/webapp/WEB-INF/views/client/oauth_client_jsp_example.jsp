<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.lang.StringBuilder" %>

<!DOCTYPE>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>게시판 리스트 조회</title>
	</head>
	<body>
		<%
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
				StringBuilder resBuilder = new StringBuilder();
				
				// 응답 데이터를 한 줄씩 읽어들인다.
				while((inputLine = br.readLine()) != null){
					resBuilder.append(inputLine);
				}
				
				br.close();
				System.out.println(resBuilder.toString());
			}
			catch(IOException e){
				System.out.println(e.getMessage());
			}
		%>
	</body>
</html>