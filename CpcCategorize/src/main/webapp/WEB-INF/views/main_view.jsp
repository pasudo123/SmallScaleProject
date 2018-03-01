<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<HTML>
    <HEAD>
        <META CHARSET="UTF-8">
        <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/section_menu.css" />" >
    	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/section_class_menu.css" />" >
   		<script type="text/javascript" src="<c:url value="/resources/js/jquery-3.3.1.min.js" />"></script>
   		<script type="text/javascript" src="<c:url value="/resources/js/cpc.js" />"></script>
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