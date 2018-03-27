<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- 
포괄적인 데이터 바인딩 인식 태그 제공 

Spring form 태그 라이브러리는 Spring Web MVC 와 통합된다.
컨트롤러에 처리되는 커맨드 객체 및 참조 데이터에 대한 태그 액세스를 제공

 -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    
<!DOCTYPE HTML>
<HTML>
    <HEAD>
        <META CHARSET="UTF-8">
        <SCRIPT type="text/javascript" src="/resources/js/jquery-3.3.1.min.js"></SCRIPT>
        <link type="text/css" rel="stylesheet" href="/resources/css/_palace_course.css"/>
   		<SCRIPT type="text/javascript">
   			$(document).ready(function(){
//    			ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
//    			* 
//    			*	[ form 태그를 통한 ajax 방식  ]
//    			*
// 				*	reference : http://blog.teamtreehouse.com/create-ajax-contact-form
//    			*
// 				ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
   					
		   		// form 태그 획득			
   				var form = $('form#routeForm');
   				
   				$(form).submit(function(e){
   					
   					// 정상적 폼 실행을 막음
   					e.preventDefault();
   					
   					// TODO
   					// 직렬화, 키/밸류 쌍으로 변경해서 데이터 변환
   					// form 데이터 안의 name 값을 키 값으로 한다.
   					var formData = $(form).serialize();
   					console.log(formData);
   					
   					// Submit the form using AJAX.
   					$.ajax({
   					    type: 'POST',
   					    url: $(form).attr('action'),
   					    data: formData,
   					    async:false,
   					    success:function(data){
   					    	
//    					    	for(var i = 0; i < data.length; i++){
//    					    		var coordList = getCoordinate(data[i].type, data[i].coordinateList);
//    					    		var name = data[i].name;
//    					    		var desc = data[i].desc;
   					    		
//    					    		var totalTime;
//    					    		var totalDistance;;
//    					    		var time;
//    					    		var dist;
   					    		
//    					    		if(i == 0){
//    					    			totalTime = data[0].totalTime;
//    					    			totalDistance = data[0].totalDistance;
//    					    		}
//    					    		else{
//    					    			time = data[i].time;
//    					    			dist = data[i].distance;
//    					    		}
//    					    	}
   					    	
   					    	var startPoint = getCoordinate(data[0].type, data[0].coordinateList);
   					    	var endPoint = getCoordinate(data[data.length-1].type, data[data.length-1].coordinateList);
   					    	
   					    	// 출발점 위도 경도를 파라미터 삽입
 					    	initTMap(startPoint[0].split("=")[1], startPoint[1].split("=")[1], endPoint[0].split("=")[1], endPoint[1].split("=")[1]);
   					    	
							// Clear the form
							$('input#startPoint').val('');
   					    }
   					})
   				});
   			});
   			
   			//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
   			// 티맵 지도 초기화
   			
   			var map;
   			
   			function initTMap(startMapY, startMapX, endMapY, endMapX){
   				$('div#map_div').remove();
   				var $divTag = $("<div id='map_div'></div>");
   				$('div.mapWrapper').append($divTag);
   				
   				console.log(startMapY + " : " + startMapX);
   				console.log(endMapY + " : " + endMapX);
   				
   				// 맵 생성 : Tmap.map 을 이용하여 지도가 들어갈 div, 넓이, 높이 설정
   				map = new Tmap.Map({
   					div:'map_div',
					width : "880px",
					height : "660px",
   				});
   				
   				// REST API에서 제공되는 경오, 교통정보, POI 데이터 쉽게 처리 클래스
   				var tData = new Tmap.TData();
				
   				// 시작 좌표 (출발 좌표)
   				var s_lonLat = new Tmap.LonLat(Number(startMapX), Number(startMapY));
//    				var s_lonLat = new Tmap.LonLat(startMapX, startMapY);
   				
   				// 종료 좌표 (고궁 좌표)
   				var e_lonLat = new Tmap.LonLat(Number(endMapX), Number(endMapY));
//    				var e_lonLat = new Tmap.LonLat(endMapX, endMapY);
   				
   				var optionObj = {
 					reqCoordType:"EPSG:4326",	// 요청 좌표계 옵션 설정
   					resCoordType:"EPSG:4326"		// 응답 좌표계 옵션 설정
   				}
   				
   				// 경로 탐색 데이터를 콟백 함수를 통해 XML로 리턴
   				tData.getRoutePlan(s_lonLat, e_lonLat, optionObj);
   				
   				tData.events.register("onComplete", tData, onComplete);		//데이터 로드가 성공적으로 완료되었을 때 발생하는 이벤트를 등록합니다.
   				tData.events.register("onProgress", tData, onProgress);		//데이터 로드중에 발생하는 이벤트를 등록합니다.
   				tData.events.register("onError", tData, onError);			//데이터 로드가 실패했을 떄 발생하는 이벤트를 등록합니다.
   				
//    				map.setCenter(new Tmap.LonLat("126.986072", "37.570028").transform("EPSG:4326", "EPSG:3857"), 15);
   			}
   			
   			//데이터 로드가 성공적으로 완료되었을 때 발생하는 이벤트 함수 입니다. 
   			function onComplete(){
   			      console.log(this.responseXML); 	//xml로 데이터를 받은 정보들을 콘솔창에서 확인할 수 있습니다.
   			      
   			      var kmlForm = new Tmap.Format.KML({extractStyles:true}).read(this.responseXML);
   			      var vectorLayer = new Tmap.Layer.Vector("vectorLayerID");
   			      vectorLayer.addFeatures(kmlForm);
   			      map.addLayer(vectorLayer);
   			      
   			      //경로 그리기 후 해당영역으로 줌  
   			      map.zoomToExtent(vectorLayer.getDataExtent());
   			   }
   			
   			//데이터 로드중 발생하는 이벤트 함수입니다.
   			function onProgress(){
   				//alert("onComplete");
   			}
   			
   			//데이터 로드시 에러가 발생시 발생하는 이벤트 함수입니다.
   			function onError(){
   				alert("onError");
   			}
   			//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
   			
   			
   			// 문자열로 받은 좌표 배열 값 파싱
   			function getCoordinate(type, coordinateList){
   				console.log(coordinateList);
   				var data = coordinateList;
   				var res;
   				
   				// 문자열을 배열로 변환
   				if(type == "LineString"){
   					data = data.substring(1, data.length-1);
   					data = new Array(data);
   					
   					// replaceALL 효과
   					// reference : http://www.codejs.co.kr/%EC%9E%90%EB%B0%94%EC%8A%A4%ED%81%AC%EB%A6%BD%ED%8A%B8%EC%97%90%EC%84%9C-replace%EB%A5%BC-replaceall-%EC%B2%98%EB%9F%BC-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0/
   					data = data[0].replace(/\[/gi, "");
   					data = data.replace(/\]/gi, "");
   				}
   				else if(type == "Point"){
   					data = data.substring(2, data.length-2);
   				}
   				
   				data = data.split(",");
   				return data;
   			}
   		</SCRIPT>
   	</HEAD>
   	
   	<BODY>
   		<div class="courseWrapper" style="visibility:hidden;">
   			<form id="routeForm" action="/course/route">
				<div class="startPointWrapper">
					<div class="labelWrapper">출발지</div>
					<div class="formWrapper">
						<input type="text" id="startPoint" name="startPoint" placeholder="출발 주소를 입력하세요."/>
					</div>
				</div>
				<div class="submitWrapper">
					<input id="button" type="submit" value="길찾기" />
				</div>
			</form>
			
			<div class="mapWrapper">
<!-- 				<div id="map_div"> -->
<!-- 				</div> -->
			</div>			   		
   		</div>
   	</BODY>
</HTML>