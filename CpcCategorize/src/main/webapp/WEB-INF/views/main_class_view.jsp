<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="mainCpcWrapper">
	<span>${cpcData.getCode()}</span>
	<BR><BR>
	<span>${cpcData.getOriginalText()}</span>
	<BR>
	<span>${cpcData.getTranslationText()}</span>
</div>

<SCRIPT>
$(document).ready(function(){
	$('a.pagingBtn').on('click', function(){
		var pageId = $(this).attr('id');
		var pageNum = $('input#pageNum').val();
		var url = $(location).attr('href');
		
		$.ajax({
			type:'POST',
			url:url + '/page',
			data:{pageChoose : pageId, page : pageNum},
			dataType:'html',
			
			success:function(data){
				$('div.classWrapper').html(data);
			},
			error:function(request, status, error){
				alert(error);
			}
		});
	});
	
function urlProcess(){
		
		// jQuery 로 현재 URL 가져오는 방법
		// reference : http://88240.tistory.com/385
		var currentHost = $(location).attr('host');	// localhost:8181
		var currentPort = $(location).attr('port'); // 8181
		var currentLink = $(location).attr('href'); // http://localhost:8181/main/E
		
		// 슬라이스
		// reference : http://88240.tistory.com/380
		var splitHref = currentLink.split("" + currentHost + "");
		var linkName = splitHref[1];
		
		// reference : https://stackoverflow.com/questions/9823718/replace-forward-slash-character-in-javascript-string
		var slashCount = (linkName.match(/\//g)).length;
		
		return slashCount;
	}
	
	$('a.classHref, a.menuLink').attr('onclick', '').click(function(){
		var name = this.text;				// 해당 섹션, 클래스, 서브클래스 이름 중 하나
		var slashCount = urlProcess();		// 슬래시 개수
		var thisObject = this;
		
		// main >> classification 이후에 섹션, 클래스, 서브클래스 접근
		// slashCount = 2 : /main/
		// slashCount = 3 : /main/classification/?
		// slashCount = 4 : /main/classification/?/??
		// slashCount = 5 : /main/classification/?/??/???
		
		if(name.length == 1){
			if(slashCount == 2)
				location.href="./classification/" + name;
			
			if(slashCount == 3)
				location.href = "./" + name;
			
			if(slashCount == 4)
				location.href = "../" + name;
			
			if(slashCount == 5)
				location.href = "../../" + name;
			
			return;
		}
		
		if(name.length == 3){
			location.href = "./" + $(this).attr("id") + "/" + name;
			return;
		}
		
		if(name.length == 4){
			location.href = "./" + $(this).attr("id") + "/" + name;
			return;
		}
		
		// ajax 이용 [ 해당 내용에 대한 CPC 데이터 확인  ]
		// CPC 코드에 대한 데이터 값
		$.ajax({
			type:'POST',
			url:'../../../printCpcData',
			data:{cpcCode : name},
			dataType:"json",
			
			success:function(data){
				// reference : http://blog.naver.com/PostView.nhn?blogId=k_rifle&logNo=177611312
				// reference : http://roqkffhwk.tistory.com/35
				
				// [ jQuery 셀렉터 ]
				// reference : https://zetawiki.com/wiki/JQuery_%EC%85%80%EB%A0%89%ED%84%B0
				// jQuery 1.4 이상의 버전에서 적용할 수 있는 방법 
				
				// [ $().next() ] 는 해당 태그의 바로 다음 태그를 지칭한다.
				
				$('a.classHref').css("color", "black");
				$('a.classHref').next('div').css("display", "none");
				
				$(thisObject).css("color", "#f035b8");
				
				var animationWrapper = $(thisObject).next('div.animationWrapper');
				var object = data;
				
				$(animationWrapper).css("display", "block");
				$(animationWrapper).find('div.originalTextWrapper').text(object.originalText);
				$(animationWrapper).find('div.translationTextWrapper').text(object.translationText);
			}
		});
	});
});
</SCRIPT>

<c:forEach items="${subDataList}" var="subDataList">
	<div class="dataWrapper">
		<a class="classHref" id="${cpcData.getCode()}" href="javascript:void(0);">${subDataList.getCode()}</a>
		
		<div class="animationWrapper">
			<!-- ajax 를 통한 이후에 내용 추가  -->	
			<div class="originalTextWrapper"></div>
			<div class="translationTextWrapper"></div>
		</div>
		
		<br>
		<span class="translationText">${subDataList.getOriginalText()}</span>
	</div>
</c:forEach>


<div class="pagingWrapper">
	
	<!-- boardPaging 값 변환이 자주 일어나기 때문에 수정 -->
	<input type="hidden" value="${pagingInfoMap.currentPage}" id="pageNum"/>
	
	<!-- 현재 블록이 첫번째 블록이 아니면 보여주기 -->
	<!-- 현재 페이지 블록이 첫번째 페이지 블록이 아니면 보여주기 -->
	<c:if test="${pagingInfoMap.currentBlock != pagingInfoMap.firstBlock}">
		<span><a class="pagingBtn" id="begin" href="javascript:void(0);">[처음]</a></span>
		<span><a class="pagingBtn" id="prev" href="javascript:void(0);">[이전]</a></span>
	</c:if>
	
	<c:forEach var="page" begin="${(pagingInfoMap.currentBlock * pagingInfoMap.PRINT_PAGE_COUNT) - (pagingInfoMap.PRINT_PAGE_COUNT - 1)}" end="${pagingInfoMap.currentBlock * pagingInfoMap.PRINT_PAGE_COUNT}">
		<c:if test="${page <= pagingInfoMap.lastPage}">
			<span><a class="pagingBtn" id="${page}" href="javascript:void(0);">[ ${page} ]</a></span>
		</c:if>
	</c:forEach>
	
	<!-- 현재 블록이 마지막 블록이 아니면 보여주기 -->
	<!-- 현재 페이지 블록이 맞막 블록이 아니면 보여주기 -->
	<c:if test="${pagingInfoMap.currentBlock != pagingInfoMap.lastBlock}">
		<span><a class="pagingBtn" id="next" href="javascript:void(0);">[다음]</a></span>
		<span><a class="pagingBtn" id="end" href="javascript:void(0);">[마지막]</a></span>
	</c:if>
</div>