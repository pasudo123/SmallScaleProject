<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE HTML>
<HTML>
    <HEAD>
        <META CHARSET="UTF-8">
        <spring:url value="/resources/cpc_resource/css/section_menu.css" var="sectionMenuCSS" />
        <spring:url value="/resources/cpc_resource/css/section_class_menu.css" var="sectionClassMenuCSS" />
        
        <spring:url value="/resources/cpc_resource/js/jquery-3.3.1.min.js" var="jQuery" />
        <spring:url value="/resources/cpc_resource/js/cpc.js" var="cpcJS" />
        <spring:url value="/resources/cpc_resource/js/paging.js" var="pagingJS" />
        
        <link type="text/css" rel="stylesheet" href="${sectionMenuCSS}" >
        <link type="text/css" rel="stylesheet" href="${sectionClassMenuCSS}" >
		
		<script type="text/javascript" src="${jQuery}"></script>
   		<script type="text/javascript" src="${cpcJS}"></script>
    	<script type="text/javascript" src="${pagingJS}"></script>
    </HEAD>

    <BODY>
        <div class="Wrapper">
            <div class="titleWrapper">
                <div>
                    <span>CPC</span><span>분류표</span>
                </div>
            </div>

            <hr>
            <div class="menuWrapper">
                <div class="menuBar">
                    <ul>
                    <c:forEach items="${mainCpcSection}" var="section">
                    	<li><a class="menuLink" href="javascript:void(0);">${section.getCode()}</a></li>
                    	<li class="gapBar"></li>
                    </c:forEach>
                    </ul>
                </div>
            </div>
            <hr>
            
            <div class="classWrapper">
            	<c:import url="main_class_view.jsp" charEncoding="UTF-8"></c:import>
            </div>
        </div>
    </BODY>
</HTML>