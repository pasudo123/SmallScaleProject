<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%@ page import="java.util.*" %>
<!-- JSTL을 쓰기 위해서 jsp 페이지 앞에 선언해줌. -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%-- <script src="<c:url value="/resources/js/jquery-3.2.1.min.js" />"></script> --%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>boardList</title>
</head>
<body>
	<div>
		<table width="500" cellpadding="0" cellspacing="0" border="1">
			<tr>
				<td>날짜</td>
				<td>제목</td>
				<td>작성자</td>
				<td>조회수</td>
			</tr>
			
						
			<c:forEach items="${boardContent}" var="boardContent">
				<tr>
					<td>${boardContent.contentDate}</td>
					<td>
						<a href="boardList/boardContentView?contentNum=${boardContent.contentNum}">${boardContent.contentTitle}</a>
					</td>
					<td>${boardContent.contentWriter}</td>
					<td>${boardContent.contentHit}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div>
	<p><a href="boardWriteView">작성하기</a>
	</div>
	
<!-- 	static final int PRINT_CONTENT_COUNT = 5;	// 한 페이지에 보여줄 글의 수 -->
<!-- 	static final int PRINT_PAGE_COUNT = 3;		// 한 화면당 페이지의 수 -->
	<div>
		<!-- 현재 블록이 첫번째 블록이 아니면 보여주기 -->
		<c:if test="${pagingInfoMap.currentBlock != pagingInfoMap.firstBlock}">
			<span><a href="boardList?paging=begin">[처음]</a></span>
			<span><a href="boardList?paging=prev">[이전]</a></span>
		</c:if>
		
		<c:forEach var="page" begin="${(pagingInfoMap.currentBlock*pagingInfoMap.PRINT_PAGE_COUNT) - (pagingInfoMap.PRINT_PAGE_COUNT-1)}" end="${pagingInfoMap.currentBlock*pagingInfoMap.PRINT_PAGE_COUNT}">
			<c:if test="${page <= pagingInfoMap.lastPage}">
				<span><a href="boardList?paging=${page}">[ ${page} ]</a></span>
			</c:if>
		</c:forEach>
		
		<!-- 현재 블록이 마지막 블록이 아니면 보여주기 -->
		<c:if test="${pagingInfoMap.currentBlock != pagingInfoMap.lastBlock}">
			<span><a href="boardList?paging=next">[다음]</a></span>
			<span><a href="boardList?paging=end">[마지막]</a></span>
		</c:if>
	</div>
</body>

</html>