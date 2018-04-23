<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!DOCTYPE>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>게시판 리스트 조회</title>
	</head>
	<body>
		<script>
			function getBoardList(){
				
				var URL = "http://localhost:8181/api_call/read";
				var accessToken = '';
				
				accessToken = document.getElementById("accessToken").value;
				if(accessToken == ''){
					alert("accessToken 미 입력");
					return;
				}	
				
				// accessToken 출력
				console.log(accessToken);
				
				// XMLHttpRequest 는 same-origin 정책을 따른다.
				// 따라서 다른 도메인으로 요청이 불가 (CORS)
				var xmlHttp = new XMLHttpRequest();
				xmlHttp.onreadystatechange = function(){
					if(this.readyState == 4 && this.status == 200){
						document.getElementById("responseData").innerHTML = this.responseText;
					}
				};
				
				xmlHttp.open("POST", URL, true);
				xmlHttp.setRequestHeader("Authorization", "Bearer " + accessToken);
				xmlHttp.send();
				
			}
		</script>
		
		<h3>게시판 조회</h3>
		
		액세스 토큰 입력 : <input type="text" id="accessToken" placeholder="access token 입력" />
		
		<BR>
		
		<button type="button" onclick="getBoardList()">게시판 리스트 조회</button>
		
		<BR><HR><BR>
		
		<div id="responseData"></div>
	</body>
</html>