<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="mainCpcWrapper">
	<span>${cpcData.getCode()}</span>
	<BR><BR>
	<span>${cpcData.getOriginalText()}</span>
	<BR>
	<span>${cpcData.getTranslationText()}</span>
</div>

<c:forEach items="${subDataList}" var="subDataList">
	<div class="dataWrapper">
		<a class="classHref" id="${cpcData.getCode()}" href="javascript:void(0);">${subDataList.getCode()}</a>
		
		<div class="animationWrapper">
			<!-- ajax 를 통한 이후에 내용 추가  -->	
			<div class="originalTextWrapper"></div>
			<div class="translationTextWrapper"></div>
		</div>
		
		<br>
		<span class="translationText">${subDataList.getOriginalText()}</span>
	</div>
</c:forEach>