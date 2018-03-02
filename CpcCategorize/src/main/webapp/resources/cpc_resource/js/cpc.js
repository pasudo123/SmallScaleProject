$(document).ready(function(){
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
		var name = this.text;
		var slashCount = urlProcess();
		var thisObject = this;
		
		// slashCount = 2 : /main/?
		// slashCount = 3 : /main/?/??
		// slashCount = 4 : /main/?/??/???
		
		if(name.length == 1){
			if(slashCount == 2)
				location.href = "./" + name;
			
			if(slashCount == 3)
				location.href = "../" + name;
			
			if(slashCount == 4)
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
			url:'./printCpcData',
			data:{
				"cpcCode" : name
			},
			
			success:function(data){
				// reference : http://blog.naver.com/PostView.nhn?blogId=k_rifle&logNo=177611312
				// reference : http://roqkffhwk.tistory.com/35
				// jQuery 1.4 이상의 버전에서 적용할 수 있는 방법 
				thisObject.after($('<div/>',{
					id:'originalDetailText',
					
				}));
				
				thisObject.after($('<div/>',{
					
				}));
			}
		});
	});

	var currentLink = $(location).attr('href');
	var linkName = currentLink.split("/");
	var urlName = linkName[linkName.length-1];
	
	if(urlName == '')
		$("div.classWrapper").css("display", "none");
	else
		$("div.classWrapper").css("display", "block");
});