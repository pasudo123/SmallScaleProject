<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<HTML>
    <HEAD>
        <META CHARSET="UTF-8">
        
        <link type="text/css" rel="stylesheet" href="/resources/css/_palace_menu.css"/>
        <SCRIPT type="text/javascript" src="/resources/js/jquery-3.3.1.min.js"></SCRIPT>
        
        <SCRIPT>
            $(document).ready(function(){
                // 궁궐 이름
            	var palaceName;
				var addr;
				var mapX;
				var mapY;
				
                $('span.mainTag').click(function(){
                    // 새로운 메뉴를 클릭하는 경우 (열기)
                    if($(this).next().is(":hidden")){
                        $('span.mainTag').css("color", "#3b3838");
                        $('span.mainTag').next().slideUp(200);

                        $(this).css("color", "royalblue");
                        $(this).next().slideDown(250);
                        palaceName = $(this).attr("id");
                        
                        addr = $(this).next().find('input#addr').val();
                        mapX = $(this).next().find('input#mapX').val();
                        mapY = $(this).next().find('input#mapY').val();
                        
                        alert(addr);
                    }
                    
                    // 기존의 메뉴를 클릭하는 경우 (닫기)
                    else{
                        $(this).css("color", "#3b3838");
                        $(this).next().slideUp(200);
                    }
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
                                            <li><span class="subTag"><c:out value="${palace.korName}" /> 가는길</span></li>
                                            <li><span class="subTag"><c:out value="${palace.korName}" /> 주변 살피기</span></li>
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