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
							
							// -- value accessor : 주어진 데이터 객체에 대한 인코딩 값 반환
							// -- scale : 값을 픽셀 위치와 같은 시각적 표시 인코딩으로 맵핑
							// -- map function : 데이터 값에서 표시 값으로 맵핑
							// -- axis : 중심선 설정
							
							var width = $("svg").width();
							var height = $("svg").height();
							
// 							var xValue = function(d){
// 						    	return d[0];
// 						    }
						    
// 						    var yValue = function(d){
// 						    	return d[1];
// 						    }

							// -- domain : 0 ~ max [ 실제 값의 범위 ]
							// -- range : [ 실제 값에서 변환할 값의 범위 ] (픽셀 위치와 같은 시각적 표시 인코딩 맵핑)
							// 배열을 인자로 받고 해당 배열내에 첫번째 원소와 두번쨰 원소를 통해 시작과 끝의 범위 정의
							var xScale = d3.scaleLinear()
			              		.domain([0, d3.max(dataSet, function(d) { return d[0]; })])
				              	.range([ 0, width - 80]);
							
						    var yScale = d3.scaleLinear()
			    	      		.domain([0, d3.max(dataSet, function(d) { return d[1]; })])
				    	      	.range([ height - 30, 0 + 30]);
				    		
						    var refreshGraph = function(){
						    	xScale.domain(d3.extent(dataSet));
						    	yScale.domain(d3.extent(dataSet));
						    }
						    
						    var svg = d3.select("svg");
						    var main = svg.append("g")
						    	.attr("width", width)
						    	.attr("height", height)
						    	.attr("class", "main")

						    // -- axis : 축 생성 (x, y 축)
					    	// 매개변수 값으로 scale을 넘기면 range 범위를 적절히 판단하여 축을 생성
						    // draw the x axis
						    var xAxis = d3.axisBottom(xScale);
							
						    // X축 추가
						    main.append('g')
						    // margin 30px + y축 50px = 80px
						    .attr("transform", "translate(50," + (height - 30) + ")")
						    .attr("class", "X Axis")
						    .call(xAxis);
						    
						    // draw the y axis
						    var yAxis = d3.axisLeft(yScale);
						    
						    // Y축 추가
						    main.append('g')
						    .attr("transform", "translate(50, 0)")
						    .attr("class", "Y Axis")
						    .call(yAxis);
						    
						    var g = main.append("svg:g")
						    
							g.selectAll("scatter-dots")
								.data(dataSet)
								.enter()
								.append("svg:circle")
								.attr("cx", function(d, i){
									return xScale(d[0]);
								})
								.attr("cy", function(d, i){
									return yScale(d[1]);
								})
								.attr("r", 3)		
						} // success
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
	               <li id="1st">트위터와 뉴스 언급량 X 진료건수</li>
	               <li id="2nd">트위터와 뉴스 언급량 X 최저기온</li>
	               <li id="3rd">트위터와 뉴스 언급량 X 일교차</li>
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
					<div>
						설명글
					</div>
				</div>
			</div>
			
			<div id="d3Chart" class="correlationWrapper">
				<svg></svg>
			</div>
		</div>
	</BODY>
</HTML>