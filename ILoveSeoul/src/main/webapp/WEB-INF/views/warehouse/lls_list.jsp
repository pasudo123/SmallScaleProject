<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<HTML>
    <HEAD>
        <META CHARSET="UTF-8">
        <link type="text/css" rel="stylesheet" href="<c:url value='/resources/css/_list.css'/>" /></link>
        <SCRIPT type="text/javascript" src="<c:url value='/resources/js/jquery-3.3.1.min.js'/>"></SCRIPT>
    </HEAD>
    	
    <BODY>
    	<div class="listWrapper">
    		<ul>
	    		<c:forEach items="${dataList}" var="data" varStatus="status">
				<li>
					<div>
						<div class="contentWrapper">
							<div class="imageWrapper">
								<img src="${data.imageURL}" />
							</div>
							<div class="textWrapper">
								<!-- 30줄 넘어가면 글자 자동으로 생략 CSS -->
								<div>
								
								</div>
							</div>
						</div>
					</div>
				</li>
				</c:forEach>
    		</ul>
    	</div>
    </BODY>
</HTML>