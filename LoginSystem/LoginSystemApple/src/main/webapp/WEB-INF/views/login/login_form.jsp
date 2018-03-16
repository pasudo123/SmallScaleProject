<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<HTML>
    <HEAD>
    	<script type="text/javascript" src="<c:url value="/resources/js/jquery-3.3.1.min.js" />"></script>
        <link rel="stylesheet" href="<c:url value="/resources/css/LoginFormCSS.css" />" media="screen" type="text/css" /></link>
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,700">
        <META CHARSET="UTF-8">
        
        <SCRIPT>
        	$(document).ready(function(){
        		
        	});
        </SCRIPT>
    </HEAD>
    
    <BODY>
	    <div class="container">
	      <div class="titleWrapper">APPLE</div>
	      <div class="welcomeUserIDWrapper"></div>
	      
	      <div id="login">
	        <form:form action="${formActionURL}" modelAttribute="user" method="POST">
	          <fieldset class="clearfix">
	            <p>
	            <span class="fontawesome-user"></span>
	            <form:input path="id" placeholder="Username" /></p> <!-- JS because of IE support; better: placeholder="Username" -->
	            <p>
	            <span class="fontawesome-lock"></span>
	            <form:password path="pw" placeholder="Password" /></p> <!-- JS because of IE support; better: placeholder="Password" -->
	            <p><input type="submit" value="${loginBtnValue}"></p>
	          </fieldset>
	        </form:form>
	      </div> <!-- end login -->
	    </div>
    </BODY>
</HTML>