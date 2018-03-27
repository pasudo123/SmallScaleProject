<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<HTML>
    <HEAD>
        <META CHARSET="UTF-8">
        <link type="text/css" rel="stylesheet" href="/resources/css/_palace_content.css"/>
        <SCRIPT type="text/javascript" src="/resources/js/jquery-3.3.1.min.js"></SCRIPT>
        
        <SCRIPT>
        	$(document).ready(function(){
        		
        		var totalCount = '${totalCount}';
        		var pageNo = '${pageNo}';
        		
        		// [ 이전 ] 보이기
        		if(pageNo == '' || pageNo == 1)
        			$('div.prev').css("visibility", "hidden");
        		else
        			$('div.prev').css("visibility", "visible");
        		
        		if(pageNo == '' || pageNo * 4 > totalCount)
        			$('div.next').css("visibility", "hidden");
        		else
        			$('div.next').css("visibility", "visible");
				
        		// 길 찾기 버튼 클릭
        		$('div.locationPathToWrapper').click(function(){
        			// 길찾기 url 창 띄워주기 
        			// http://map.daum.net/link/to/name(도착 장소명), mapY(도착 위도) ,mapX(도착 경도)
        			var tag = $(this).prev();
        		
        			var name = tag.find('div.locationTitle').text();
        			var mapX = tag.find('input#mapX').val();
        			var mapY = tag.find('input#mapY').val();
        			
        			var url = "http://map.daum.net/link/to/" + name + "," + mapX + "," + mapY;
        			
        			// 창 새롭게 띄우기
                    window.open(url, "_blank");  
        		});
        		
//         		ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
//         		*
//         		*	  	   [ 페이지 이동 처리 관련 jQuery 로직 ]
//         		*
//         		ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        		// 이전 혹은 다음 버튼 클릭
        		$('div.prev').find('span').click(function(){
        			var pageNo = $('input#pageNo').val();
        			shift(parseInt(pageNo)-1);
        		});
        		
				$('div.next').find('span').click(function(){
					var pageNo = $('input#pageNo').val();
					shift(parseInt(pageNo)+1);
        		});
				
				function shift(pageNo){
					var mapX = $('div.listPointerWrapper').find('input#mapX').val();
					var mapY = $('div.listPointerWrapper').find('input#mapY').val();
					var palaceName = $('div.listPointerWrapper').find('input#palaceName').val();
					
// 					ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
// 					*
// 					*			[  AJAX 통신  ] - async : false
// 					*
// 					ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
					$.ajax({
                		data : {"mapX" : mapX, "mapY" : mapY, "pageNo" : pageNo},
                		dataType : 'json',
                		type : "POST",
                		async : false,
                		url : "./" + palaceName + "/observe_movement",
                		
                		success:function(data){
                			var array = data.item;
                			
                			// json 내용
                			for(var i in array){
//                 				console.log(array[i].title);
//                 				console.log(array[i].addr1);
//                 				console.log(array[i].tel);
                				
                				var typeIndex = parseInt(i) + 1;
                				var telText = " - ";
                				
                				$('div.list').find('li:nth-of-type(' + typeIndex + ')').find('div.thumbnailWrapper img').attr("src", array[i].firstimage2);
                				$('div.list').find('li:nth-of-type(' + typeIndex + ')').find('input#mapX').val(array[i].mapx);
                				$('div.list').find('li:nth-of-type(' + typeIndex + ')').find('input#mapY').val(array[i].mapy);
                				$('div.list').find('li:nth-of-type(' + typeIndex + ')').find('input#bigImage').val(array[i].firstimage1);
                				$('div.list').find('li:nth-of-type(' + typeIndex + ')').find('div.locationTitle').text(array[i].title);
                				$('div.list').find('li:nth-of-type(' + typeIndex + ')').find('div.locationAddr').text(array[i].addr1);
                				
                				if(array[i].tel == null)
                					telText = " - ";
                				else
                					telText = array[i].tel;
                				
                				$('div.list').find('li:nth-of-type(' + typeIndex + ')').find('div.locationTel').text(telText);
                			}
                			
                			
                			// 페이지 관련 처리
                			var totalCount = data.totalCount;
                			var pageNo = data.pageNo;
                			$('input#pageNo').val(pageNo);
                			
                    		if(pageNo == '' || pageNo == 1)
                    			$('div.prev').css("visibility", "hidden");
                    		else
                    			$('div.prev').css("visibility", "visible");
                    		
                    		if(pageNo == '' || pageNo * 4 > totalCount)
                    			$('div.next').css("visibility", "hidden");
                    		else
                    			$('div.next').css("visibility", "visible");
                    		
                    		
                    		// 장소 네 곳을 보여준다.
                    		makerArray(mapY, mapX, array);
                    		
                		}// success(function(){})
                	});
				}
        	});
        </SCRIPT>
    </HEAD>

	<BODY class="contentBody">
		<div class="mapAndListWrapper" style="visibility:hidden;">
			<div class="mapWrapper">
			</div>
	
			<div class="listWrapper">
				<div class="listTitleWrapper">
				</div>
				
				<div class="list">
					<ul>
						<c:forEach items="${locationDataList}" var="locationDataList">
							<li>
								<div>
									<div class="thumbnailWrapper">
										<img src="${locationDataList.imageURL2}" />
									</div>
									
									<div class="locationInfoWrapper">
										
										<!-- 위도 / 경도 / 지도 값 -->
										<input type="hidden" id="mapX" value="${locationDataList.mapX}" />
										<input type="hidden" id="mapY" value="${locationDataList.mapY}" />
										<input type="hidden" id="bigImage" value="${locationDataList.imageURL1}" />
										
										<div class="locationTitle">${locationDataList.title}</div>
										
										<!-- JSTL 에서 if - else 구문 -->
										<c:choose>
											<c:when test="${locationDataList.tel ne 'null'}">
												<div class="locationTel">${locationDataList.tel}</div>
											</c:when>
											
											<c:otherwise>
												<div class="locationTel"> - </div>
											</c:otherwise>
										</c:choose>
										
										<div class="locationAddr">${locationDataList.addr}</div>
									</div>
									
									<div class="locationPathToWrapper">
										<img src="/resources/image/pathTo.png" />
									</div>
								</div>
							</li>
						</c:forEach>
					</ul>
				</div>
				
				<div class="listPointerWrapper">
					<input type="hidden" name="palaceName" id="palaceName" value="${palaceName}" />
					<input type="hidden" name="pageNo" id="pageNo" value="${pageNo}" />
					<input type="hidden" name="mapX" id="mapX" value="${palaceMapX}" />
					<input type="hidden" name="mapY" id="mapY" value="${palaceMapY}" />
					<div class="prev"><span>이전</span></div>
					<div class="next"><span>다음</span></div>
				</div>
			</div>
		</div>
		<!-- 

		에러 발생)
		브라우저가 HTML 파싱을 한 이후에 DOM 트리를 구성하는 단계에서
		<div id="map" /> 가 아직 파싱되지 않은 상태로 div#map 을 참조하면 에러가 발생한다.
		
		 -->
		<SCRIPT>
			initMap('${palaceMapY}', '${palaceMapX}')
			
			// 전역변수
			var map;
			
			function initMap(mapY, mapX){
				// 동적 지도 생성
				// <div id=map</div> 을 동적으로	
				$('div#map').remove();
			 	var $divTag = $("<div id='map'></div>");
			 	$('div.mapWrapper').append($divTag);
			 	
				var container = document.getElementById('map');
				
				var palaceMapX = mapY;
			 	var palaceMapY = mapX;
			 	
			 	// 기본 : 제주도 위치
			 	if(palaceMapX == null || palaceMapY == null){
			 		palaceMapX = 33.450701;
			 		palaceMapY = 126.570667;
			 	}
			 	
				var options = { //지도를 생성할 때 필요한 기본 옵션
					center : new daum.maps.LatLng(palaceMapX, palaceMapY), 	//지도의 중심좌표.
					level : 5 												//지도의 레벨(확대, 축소 정도)
				};
			
				map = new daum.maps.Map(container, options); 			//지도 생성 및 객체 리턴
			
				var markerPosition = new daum.maps.LatLng(palaceMapX, palaceMapY);
				// 마커를 생성합니다
				var marker = new daum.maps.Marker({
				    position: markerPosition
				});
				
				// 마커가 지도 위에 표시되도록 설정합니다
				marker.setMap(map);
			}
			
			// 커스텀 오버레이 메소드
			function makerArray(mapY, mapX, array){
				initMap(mapY, mapX);
				
				console.log(mapY + " : " + mapX)
				
				//
				//
				// 여기서 주변 네 곳의 위치를 찍어주는 로직이다.
				// 이미지
				var imageSrc = "http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
			    
			    for(var i in array){
			    	console.log(array[i].mapy);
			    	console.log(array[i].mapx);
			    	console.log(array[i].title);
			    	console.log("\n");
			    	
			    	var a = array[i].mapy;
			    	var b = array[i].mapx;
			    	
			    	// 마커 이미지의 이미지 크기 입니다
				    var imageSize = new daum.maps.Size(24, 35); 
				    
				    // 마커 이미지를 생성합니다    
				    var markerImage = new daum.maps.MarkerImage(imageSrc, imageSize); 
				    
			    	var maker = new daum.maps.Marker({
			    		map:map,	// 마커를 표시할 지도
			    		position:new daum.maps.LatLng(a, b),
			    		title:array[i].title,
			    		image:markerImage
			    	});
			    }
			}
		</SCRIPT>
	</BODY>
</HTML>