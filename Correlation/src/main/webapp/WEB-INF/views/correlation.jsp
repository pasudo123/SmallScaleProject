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
					
					// 각각의 레이블
					var xLabel;
					var yLabel;
					
					if(idValue == "1st"){
						url = "./viewCorrelationSource-Treatment";
						xLabel = "트위터 X 뉴스 언급량";	
						yLabel = "진료건수";
					}
					
					if(idValue == "2nd"){
						url = "./viewCorrelationSource-LowestTemperature";
						xLabel = "트위터 X 뉴스 언급량";
						yLabel = "최저기온";
					}
					
					if(idValue == "3rd"){
						url = "./viewCorrelationSource-DiurnalRange";
						xLabel = "트위터 X 뉴스 언급량";
						yLabel = "일교차";
					}
					
					if(idValue == "4th"){
						url = "./viewCorrelationLowestTemperature-Treatment";
						xLabel = "최저기온";
						yLabel = "진료건수";
					}
					
					if(idValue == "5th"){
						url = "./viewCorrelationLowestTemperature-Moisture";
						xLabel = "최저기온";
						yLabel = "습도";
					}
					
					if(idValue == "6th"){
						url = "./viewCorrelationLowestTemperature-ColdDate";
						xLabel = "최저기온";
						yLabel = "날짜";
					}
					
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
							
							// 반복 & 데이터 셋 형성
							for(var i = 0; i < size; i++){
								var dataSmallSet = new Array();
								
								dataSmallSet.push(x[i]);
								dataSmallSet.push(y[i]);
								dataSet.push(dataSmallSet);
							}
							
							// 상관계수 값 획득 및 html 태그 내 삽입
							var correlationCoefficient = obj.correlationCoefficient;
							$("div.explainChart").find("div.explain").text(correlationCoefficient);
							console.log(correlationCoefficient);
							
							var divWidth = $("div.correlationWrapper").width();
							var divHeight = $("div.correlationWrapper").height();
							
							var margin = {top:20, right:20, bottom:30, left:40},
								width = divWidth - margin.left - margin.right,
								height = divHeight - margin.top - margin.bottom;
							
							var padding = {top:20, right:20, bottom:30, left:40};
							
							d3.select('div#d3Chart').select('svg').remove();

							var canvas = d3.select('div#d3Chart').append('svg')
							.attr("width", width + margin.left + margin.right + 80)
							.attr("height", height + margin.top + margin.bottom + 30)
							.attr("padding-left", 30)
							.append("g")
							.attr("transform", "translate(" + (margin.left + 80) + ", " + margin.top + ")");
							
						 	// -- domain : 0 ~ max [ 실제 값의 범위 ]
							// -- range : [ 실제 값에서 변환할 값의 범위 ] (픽셀 위치와 같은 시각적 표시 인코딩 맵핑)
							// 배열을 인자로 받고 해당 배열내에 첫번째 원소와 두번쨰 원소를 통해 시작과 끝의 범위 정의
							var xScale = d3.scaleLinear()
			              		.domain([d3.min(dataSet, function(d) { return d[0]; }) - 1, 
			              				 d3.max(dataSet, function(d) { return d[0]; }) + 1 ])
				              	.range([0, width]);
							
						    var yScale = d3.scaleLinear()
			    	      		.domain([d3.min(dataSet, function(d) { return d[1]; }) - 1, 
			    	      				 d3.max(dataSet, function(d) { return d[1]; }) + 1 ])
				    	      	.range([ height, 0]);
						    
						    // -- axis : 축 생성 (x, y 축)
					    	// 매개변수 값으로 scale을 넘기면 range 범위를 적절히 판단하여 축을 생성
						    // draw the x & y [ axis ]
						    var xAxis = d3.axisBottom().scale(xScale);
						    var yAxis = d3.axisLeft().scale(yScale);
						    
						    // X축 추가
						    canvas.append('g')
						    .attr("transform", "translate(0," + height + ")")
						    .attr("class", "X Axis")
						    .call(xAxis);
						    
						    // Y축 추가
						    canvas.append('g')
						    .attr("class", "Y Axis")
						    .call(yAxis);
						    
						    // X축 레이블
						    canvas.append("text")
							.attr("transform", "translate(" + (width/2) + ", " + (height + margin.top + 20) + ")")
						    .attr("text-anchor", "middle")
						    .text(xLabel);
						    
						    // Y축 레이블
						    canvas.append("text")
						    .attr("transform", "rotate(-90)")
						    .attr("y", 0 - (margin.left+50))
						    .attr("x", 0 - (height/2))
						    .attr("text-anchor", "middle")
						    .text(yLabel);
						    
						    var g = canvas.append("svg:g")
						    
							g.selectAll("scatter-dots")
								.data(dataSet)
								.enter()
								.append("circle")
								.attr("cx", function(d, i){
									return xScale(d[0]);
								})
								.attr("cy", function(d, i){
									return yScale(d[1]);
								})
								.attr("r", 3);
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
	               <li id="1st">Twitter & News X 진료건수</li>
	               <li id="2nd">Twitter & News X 최저기온</li>
	               <li id="3rd">Twitter & News X 일교차</li>
	               <li id="4th">최저기온 X 진료건수</li>
	               <li id="5th">최저기온 X 습도</li>
	               <li id="6th">최저기온 X 날짜</li>
	           </ul>
			</div>
			
			<div class="explainChart">
				<div>
				상관계수
					<div class="explain">
						
					</div>
				</div>
			</div>
			
			<div id="d3Chart" class="correlationWrapper">
			</div>
		</div>
	</BODY>
</HTML>