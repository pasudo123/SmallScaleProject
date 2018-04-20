<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<center><h2>Access Token을 가지고 api호출을 위한 페이지</h2></center>
	
	<h3>게시판 조회</h3>
	<form action="http://localhost:8180/oauth20/ex/read">
		액세스 토큰 입력 : <input type="text" name="accessToken" placeholder="access token 입력" />
		<BR>
		<input type="hidden" value="http://localhost:8181/api_call/read" name="apiUrl" />
		<input type="submit" value="게시판 조회" />
	</form>
	
	<br>
	<hr>
	<br>
	
	<h3>게시판 글 작성</h3>
	<form action="http://localhost:8180/oauth20/ex/write">
		액세스 토큰 입력 : <input type="text" name="accessToken" placeholder="access token 입력" />
		<BR>
		작성자 입력 : <input type="text" name="name" placeholder="작성자 입력" />	
		<BR>
		제목 입력 : <input type="text" name="title" placeholder="제목 입력" />
		<BR>
		내용 입력 : <input type="text" name="content" placeholder="내용 입력" />
		<BR>
		<input type="hidden" value="http://localhost:8181/api_call/write" name="apiUrl" />
		<input type="submit" value="작성하기" />
	</form>
	
	<br>
	<hr>
	<br>
	
	<h3>게시판 글 삭제</h3>
	<form action="http://localhost:8180/oauth20/ex/delete">
		액세스 토큰 입력 : <input type="text" name="accessToken" placeholder="access token 입력" />
		<BR>
		게시글 삭제 번호 입력 : <input type="text" name="number" placeholder="게시글 삭제 번호 입력" />
		<BR>
		<input type="hidden" value="http://localhost:8181/api_call/delete" name="apiUrl" />
		<input type="submit" value="api 호출" />
	</form>
</body>
</html>