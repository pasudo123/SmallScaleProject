<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<link type="text/css" rel="stylesheet" href="/resources/css/_palace_content.css"/>
        
<div class="mapAndListWrapper">
	<div class="mapWrapper">

        <!-- javascript Key : 0455bd291b8e2cb0f8f7532ada1c8f41 -->
        
		<div id="map"></div>
    </div>

    <div class="listWrapper">

    </div>
</div>

<!-- 

에러 발생)
브라우저가 HTML 파싱을 한 이후에 DOM 트리를 구성하는 단계에서
<div id="map" /> 가 아직 파싱되지 않은 상태로 div#map 을 참조하면 에러가 발생한다.

 -->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=0455bd291b8e2cb0f8f7532ada1c8f41"></script>
<SCRIPT>
	var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
	
	var options = { //지도를 생성할 때 필요한 기본 옵션
	    center: new daum.maps.LatLng(33.450701, 126.570667), //지도의 중심좌표.
	    level: 3 //지도의 레벨(확대, 축소 정도)
	};
	
	var map = new daum.maps.Map(container, options); //지도 생성 및 객체 리턴
</SCRIPT>