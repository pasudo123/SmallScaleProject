$(document).ready(function(){
	var currentLink = $(location).attr('href');
	var linkName = currentLink.split("/");
	var urlName = linkName[linkName.length-1];
	
	if(urlName == '')
		$("div.classWrapper").css("display", "none");
	else
		$("div.classWrapper").css("display", "block");
});