<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<meta charset="UTF-8">
		<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/chart.css" />" />
		<SCRIPT type="text/javascript" src="<c:url value="/resources/js/d3.min.js" />"></SCRIPT>
		<SCRIPT type="text/javascript" src="<c:url value="/resources/js/jquery-3.3.1.min.js" />"></SCRIPT>
		<SCRIPT>
			$(document).ready(function(){
				$('li').click(function(){
					var idValue = $(this).attr("id");
					var url = null;
					
					if(idValue == "1st")
						url = "./viewCorrelationSource-Treatment";
					if(idValue == "2nd")
						url = "./viewCorrelationSource-LowestTemperature";
					if(idValue == "3rd")
						url = "./viewCorrelationSource-DiurnalRange";
					
					$.ajax({
						url : url,
						dataType:"json",
						success:function(data){
							alert("success");
							
							var obj = data;
							
							// 각각 x축 그리고 y축으로 둔다.
							var x = obj.x;
							var y = obj.y;
							var size = x.length;
							
							// 2차원 배열 형성
							var dataSet = new Array();
							// 반복
							for(var i = 0; i < size; i++){
								var dataSmallSet = new Array();
								
								dataSmallSet.push(x[i]);
								dataSmallSet.push(y[i]);
								dataSet.push(dataSmallSet);
							}
							
							var element = d3.select("svg")
							.selectAll("circle")
							.data(dataSet)
							.enter()
							.append("circle")
							.attr("class", "mark")
							.attr("cx", function(d, i){
								return d[0]*500;
							})
							.attr("cy", function(d, i){
								return d[1]*500;
							})
							.attr("r", 5)
						}
					});
				});
			});
		</SCRIPT>
	</HEAD>
	
<!--

	- d3.js 에는 레이아웃 기능이 없기 때문에 SVG 도형을 생성하고 직접 그려야 한다. 
	- 

-->
	<BODY>
		<div class="bodyWrapper">
			<div class="titleBar">
				<h3>
					상관분석
					<div>[ 감기 데이터 상관분석 ]</div>
				</h3>
			</div>
			
			<div class="menuBar">
				<ul>
	               <li id="1st">가나다라마바사아자차카타파하</li>
	               <li id="2nd">가나다라마바사아자차카타파하</li>
	               <li id="3rd">가나다라마바사아자차카타파하</li>
	               <li id="4th">가나다라마바사아자차카타파하</li>
	               <li>가나다라마바사아자차카타파하</li>
	               <li>가나다라마바사아자차카타파하</li>
	               <li>가나다라마바사아자차카타파하</li>
	               <li>가나다라마바사아자차카타파하</li>
	               <li>가나다라마바사아자차카타파하</li>
	               <li>가나다라마바사아자차카타파하</li>
	           </ul>
			</div>
			
			<div class="explainChart">
				<div>
					설명글 
				</div>
			</div>
			
			<div id="d3Chart" class="correlationWrapper">
				<svg></svg>
			</div>
		</div>
	</BODY>
</HTML>