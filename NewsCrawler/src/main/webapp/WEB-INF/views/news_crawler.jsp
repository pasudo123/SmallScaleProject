<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- c:url로  -->
<c:url value="/resources/css/_news_crawler.css" var="cssPath" />
<c:url value="/resources/js/jquery-3.3.1.min.js" var="jQueryPath" />

<!DOCTYPE HTML>
<HTML>
    <HEAD>
        <META CHARSET="UTF-8">
        <link type="text/css" rel="stylesheet" href="${cssPath}"/>
        <SCRIPT type="text/javascript" src="${jQueryPath}"></SCRIPT>
        <SCRIPT>
            $(document).ready(function(){
				
            	// form 태그 획득
            	var form = $('form#newsAddrForm');
				
				$(form).submit(function(e){
					
					// 정상적 폼 실행을 막는다.
					e.preventDefault();
					
					// TODO
					// 직렬화
					var formData = $(form).serialize();
					console.log(formData);
// 					var newsAddr = $('input#newsAddrInput').val();
// 					alert(newsAddr);
					
					$.ajax({
						type:'POST',
						url:$(form).attr('action'),
						data:formData,
// 						async:false,
						success:function(data){
							$('div.newsDataWrapper').html(data);
							
							// Clear the Input Tag
							$('input#newsAddrInput').val('');
						}
					});
				});
            });
        </SCRIPT>
    </HEAD>

    <BODY>
        <div class="bodyWrapper">
            <div class="topLineWrapper">
                <div>2018 뉴스 및 댓글 수집기</div>

                <div>
                    <img src="http://static.news.naver.net/image/news/2014/favicon/favicon.ico"/>
                    <img src="http://m2.daumcdn.net/img-media/2010ci/Daum_favicon.ico" />
                </div>
            </div>

			<div class="inputWrapper">
			    <form id="newsAddrForm" action="/crawler/gather">
			        <div class="formWrapper">
			            <input type="text" id="newsAddrInput" name="newsAddrInput" placeholder="수집할 주소를 입력하세요."/>
			        </div>
			
			        <div class="submitWrapper">
			            <input id="button" type="submit" value="수집" />
			        </div>
			    </form>
			</div>
	
			<div class="newsDataWrapper">
				<c:import url="news_result.jsp" ></c:import>
			</div>

            <div class="bottomLineWrapper">
                <div>DoubleR</div>
            </div>
        </div>
    </BODY>
</HTML>