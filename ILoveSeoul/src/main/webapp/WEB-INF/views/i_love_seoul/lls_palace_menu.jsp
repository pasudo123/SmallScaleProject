<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<HTML>
    <HEAD>
        <META CHARSET="UTF-8">
        <link type="text/css" rel="stylesheet" href="/resources/css/_palace_content.css"/>
        <link type="text/css" rel="stylesheet" href="/resources/css/_palace_menu.css"/>
        <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=0455bd291b8e2cb0f8f7532ada1c8f41"></script>
        <SCRIPT type="text/javascript" src="/resources/js/jquery-3.3.1.min.js"></SCRIPT>
        
        <SCRIPT>
            $(document).ready(function(){
            	
                // 궁궐 이름
            	var palaceName;
				var addr;
				var mapX;
				var mapY;
				var radius= "testRadius";
				
				// 메인 메뉴 클릭
                $('span.mainTag').click(function(){
                    // 새로운 메뉴를 클릭하는 경우 (열기)
                    if($(this).next().is(":hidden")){
                        $('span.mainTag').css("color", "#3b3838");
                        $('span.mainTag').next().slideUp(200);

                        $(this).css("color", "royalblue");
                        $(this).next().slideDown(250);
                        palaceName = $(this).text();
                        
                        addr = $(this).next().find('input#addr').val();
                        mapX = $(this).next().find('input#mapX').val();
                        mapY = $(this).next().find('input#mapY').val();
                    }
                    
                    // 기존의 메뉴를 클릭하는 경우 (닫기)
                    else{
                        $(this).css("color", "#3b3838");
                        $(this).next().slideUp(200);
                    }
                });
                
                // subTag 클릭
                $('span.subTag').click(function(){
                	var purpose = $(this).attr('id');
                	var urlText;
					
                	// 메뉴 닫기
                	$(this).closest('ul').slideUp(200);
                	$('span#' + palaceName).css("color", "#3b3838");
                	
                	// 가는 길, 주변 살피기
                	if(purpose == "pathTo")
                		urlText = "/show_pathTo";
                	else if(purpose = "observe")
                		urlText = "/show_observe";
                	
					// 이름 변경              	
                	$('span.palaceName').text(palaceName);
                	
                	$.ajax({
                		data : {"palaceName":palaceName, "mapX" : mapX, "mapY" : mapY, "radius" : radius},
                		type : "POST",
                		url : urlText,
                		success:function(data){
                			
                        	// https://brunch.co.kr/@ourlove/79
                        	// 지도 달린 내용을 연다. (display vs visibility)
                        	// - 공간의 차지 여부
                        	
                			$('div.contentWrapper').html(data);
                			$('div.mapAndListWrapper').css("visibility", "visible");
                		}
                	});
                });
            });
        </SCRIPT>
    </HEAD>

    <BODY>
        <div class="bodyWrapper">
            <div class="topLineWrapper"></div>
            
            <div class="menuWrapper">
                <div class="highLevelMenu">
                    <div>
                        <div>
                            <div class="leftSide">
                                <img class="logo" src="resources/image/block.png"  />
                                <div>
                                    <div>I</div>
                                    <div>Love</div>
                                    <div>Seoul</div>
                                </div>
                            </div>

                            <div class="center">
                                <ul>
                                
                                	<c:forEach items="${palaceTypes}" var="palace" >
                                		<li class="mainList"><span class="mainTag" id="${palace.engName}">${palace.korName}</span>
                               			<ul class="subList">
                               				<!-- 위도 경도 주소 -->
                               				<li><input type="hidden" id="mapX" value="${palace.mapX}" ></input></li>
                               				<li><input type="hidden" id="mapY" value="${palace.mapY}" ></input></li>
                               				<li><input type="hidden" id="addr" value="${palace.address}" ></input></li>
                                            <li><span class="subTag" id="pathTo"><c:out value="${palace.korName}" /> 가는길</span></li>
                                            <li><span class="subTag" id="observe"><c:out value="${palace.korName}" /> 주변 살피기</span></li>
                                        </ul>
                                		</li>
                                	</c:forEach>           
                                </ul>                                    
                            </div>

                            <div class="rightSide"></div>
                        </div>
                    </div>
                </div>
                <hr>
            </div>
            <!-- menu Bar -->


			<!-- 게시글 상단의 이미지 및 타이틀 -->
			<div class="imageAndTitleWrapper">
				<c:import url="./lls_image_title.jsp" charEncoding="UTF-8"></c:import>
			</div>
	
			<div class="contentWrapper">
				<c:import url="./lls_content.jsp" charEncoding="UTF-8"></c:import>
			</div>
	
			<div class="botLineWrapper">
	            
            </div>
        </div>
    </BODY>
</HTML>