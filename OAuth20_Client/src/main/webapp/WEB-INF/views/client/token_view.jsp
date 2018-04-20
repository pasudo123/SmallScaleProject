<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>Access Token 발급 받고 성공한 화면</h2>
	<BR>
	<h4>Access Token : <c:out value="${access_token}"></c:out></h4>
	<h4>Refresh Token : <c:out value="${refresh_token}"></c:out></h4>
	<h4>Token Type : <c:out value="${token_type}"></c:out></h4>
	<h4>Expires in : <c:out value="${expires_in}"></c:out></h4>
	<h4>json Line : <c:out value="${jsonLine}"></c:out></h4>
</body>
</html>