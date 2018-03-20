<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<HTML>
    <HEAD>
        <META CHARSET="UTF-8">
        <link type="text/css" rel="stylesheet" href="<c:url value='/resources/css/_standard.css'/>" /></link>
        <SCRIPT type="text/javascript" src="<c:url value='/resources/js/jquery-3.3.1.min.js'/>"></SCRIPT>
    
        <SCRIPT>
            $(document).ready(function(){
            	
      			var highCate;
      			
                $('span.highCate').click(function(){
                	highCate = $(this).text();
                	
                    $('div.sigunguWrapper').css({
                        display:"block"
                    })

                    $('span.highCate').css({
                        color:"#3b3838"
                    });

                    $(this).css({
                        color:"rgb(70, 31, 212)"
                    });
                });
                
                // reference : https://stackoverflow.com/questions/21533629/how-to-make-a-form-submit-on-div-click-using-jquery
                $('div.sigungu').click(function(){
                	var childCate = $(this).text();
                	
                	$.ajax({
                		url:"./select_sigungu",
                		type:"POST",
                		data:{"parentCate" :highCate, "childCate" : childCate},
                		success:function(data){
                			$('div.middleWrapper').html(data);
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
                                <img class="logo" src="<c:url value='resources/image/block.png'/>"  />
                                <div>
                                    <div>I</div>
                                    <div>Love</div>
                                    <div>Seoul</div>
                                </div>
                            </div>
                            <div class="center">
                                <div>
                                    <span class="highCate" id="TOURISM">관광</span>
                                    <span class="highCate" id="CULTURAL_FACILITIES">문화</span>
                                    <span class="highCate" id="FESTIVAL">공연/행사</span>
                                    <span class="highCate" id="FOOD">음식</span>
                                    <span class="highCate" id="LODGMENT">숙박</span>
                                </div>
                            </div>
                            <div class="rightSide"></div>
                        </div>
                    </div>
                </div>
                
                <hr>

                <div class="lowLevelMenu">
                    <div class="sigunguWrapper">
                        <div>
                        	<c:forEach var="sigungu" items="${sigungu}" begin="0" end="13">
                        		<div class="sigungu">${sigungu}</div>
                        	</c:forEach>
                        </div>
                        	<div><c:forEach var="sigungu" items="${sigungu}" begin="14">
                        		<div class="sigungu">${sigungu}</div>
                        	</c:forEach>
                        </div>
                    </div>
                </div>
            </div>
            <!-- menu Bar -->

            <div class="middleWrapper">
                <c:import var="dataList" url="./lls_list.jsp" charEncoding="UTF-8"></c:import>
            </div>

            <div class="botLineWrapper"></div>
        </div>
    </BODY>
</HTML>