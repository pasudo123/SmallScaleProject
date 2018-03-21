<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<HTML>
    <HEAD>
        <META CHARSET="UTF-8">
        <SCRIPT type="text/javascript" src="/resources/js/jquery-3.3.1.min.js"></SCRIPT>
        
        <SCRIPT>
        	$(document).ready(function(){
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
        	});
        </SCRIPT>
    </HEAD>

	<BODY>
		<div class="mapAndListWrapper" style="visibility:hidden;">
			<div class="mapWrapper">
				<div id="map"></div>
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
										길찾기.
									</div>
								</div>
							</li>
						</c:forEach>
					</ul>
				</div>
				
				<div class="listPointerWrapper">
					
				</div>
			</div>
		</div>
		<!-- 

		에러 발생)
		브라우저가 HTML 파싱을 한 이후에 DOM 트리를 구성하는 단계에서
		<div id="map" /> 가 아직 파싱되지 않은 상태로 div#map 을 참조하면 에러가 발생한다.
		
		 -->
		<SCRIPT>
		 	//지도를 담을 영역의 DOM 레퍼런스
			var container = document.getElementById('map');
			
			var palaceMapX = '${palaceMapY}';
		 	var palaceMapY = '${palaceMapX}';
		 	
		 	// 기본 : 제주도 위치
		 	if(palaceMapX == null || palaceMapY == null){
		 		palaceMapX = 33.450701;
		 		palaceMapY = 126.570667;
		 	}
		 	
			var options = { //지도를 생성할 때 필요한 기본 옵션
				center : new daum.maps.LatLng(palaceMapX, palaceMapY), 	//지도의 중심좌표.
				level : 5 												//지도의 레벨(확대, 축소 정도)
			};
		
			var map = new daum.maps.Map(container, options); 			//지도 생성 및 객체 리턴
		
			var markerPosition = new daum.maps.LatLng(palaceMapX, palaceMapY);
			// 마커를 생성합니다
			var marker = new daum.maps.Marker({
			    position: markerPosition
			});
			
			// 마커가 지도 위에 표시되도록 설정합니다
			marker.setMap(map);
		</SCRIPT>
	</BODY>
</HTML>