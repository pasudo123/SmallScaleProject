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
			function getList(){
				
			}
		</script>
		
		<h3>게시판 조회</h3>
		
		<form action="http://localhost:8180/oauth20/ex/read">
			액세스 토큰 입력 : <input type="text" name="accessToken" placeholder="access token 입력" />
			<BR>
			<input type="hidden" value="http://localhost:8181/api_call/read" name="apiUrl" />
			<input type="submit" value="게시판 조회" />
		</form>
	</body>
</html>