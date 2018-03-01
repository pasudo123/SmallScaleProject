$(document).ready(function(){
	function urlProcess(){
		// jQuery 로 현재 URL 가져오는 방법
		// reference : http://88240.tistory.com/385
		var currentLink = $(location).attr('href');
		
		// 슬라이스
		// reference : http://88240.tistory.com/380
		var linkName = currentLink.split("/");
		var urlName = linkName[linkName.length-1];
		
		return urlName;
	}
	
	$('a.menuLink').attr('onclick', '').click(function(){
		var sectionName = this.text;
		var urlName = urlProcess();
		
		// 현재 url이 section 이 아닌 class를 바라보고 있는 경우
		// " href = ../ " 로 접근한다. 
		if(urlName.length >= 3){
			location.href = "../" + sectionName;
			return;
		}
		
		location.href = "./" + sectionName;	
	});
	
	$('a.classHref').attr('onclick', '').click(function(){
		alert('asdaadsas');
		
		var urlName = urlProcess();
		
		if(urlName >= 4){
			location.href = "../" + $(this).attr("id") + "/" + this.text;
			return;
		}
			
		location.href = "./" + $(this).attr("id") + "/" + this.text;
	});
	
	
	var currentLink = $(location).attr('href');
	var linkName = currentLink.split("/");
	var urlName = linkName[linkName.length-1];
	
	if(urlName == '')
		$("div.classWrapper").css("display", "none");
	else
		$("div.classWrapper").css("display", "block");
});