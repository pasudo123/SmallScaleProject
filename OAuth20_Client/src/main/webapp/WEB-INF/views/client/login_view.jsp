<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<H1>Double Sample App</H1>
	<H3>OAuth2.0 을 통한 OpenAPI 개발</H3>
	
	<div>
<%-- 		<form:form modelAttribute="user"> --%>
<!-- 			<table> -->
<!-- 				<tr> -->
<!-- 					<td>ID : </td> -->
<%-- 					<td><form:input path="userId" /></td> --%>
<!-- 				</tr> -->
				
<!-- 				<tr> -->
<!-- 					<td>PW : </td> -->
<%-- 					<td><form:password path="userPw" /></td> --%>
<!-- 				</tr> -->
				
<!-- 				<tr> -->
<!-- 					<td colspan="2"><input type="submit" value="로그인" /></td> -->
<!-- 				</tr> -->
<!-- 			</table> -->
<%-- 		</form:form> --%>
		<a href="/oauth20/authorize">인증하기</a>
	</div>
</body>
</html>