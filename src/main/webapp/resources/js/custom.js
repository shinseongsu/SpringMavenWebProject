$(document).ready(function() {
	
	var numAc = $("article").size();
	console.log(numAc);
	var widSec = 200 * numAc;
	var widTotal = widSec + 600;
	
	$("section").width(widTotal);
	$("body").height(widSec);
	$("html, body").scrollTop(widSec);
	
	
	$(window).on("scroll", function() {
		var scroll = $(this).scrollTop();
		console.log(scroll);
		$("section").stop().animate({"left":-scroll},100);
	});
	
	$("article h2").on("click", function(e){
		e.preventDefault();
		
		var index = $(this).parent().index();
		var src = $(this).children("a").attr("href");
		var posAc = 200 * index;
		
		$("article").removeClass("on");
		$(this).parent().addClass("on");
		$("article p img").attr({"src":""});
		$(this).siblings("p").children("img").attr({"src":src});
		$("html, body").scrollTop(posAc);
	});
	
	$("span").on("click", function() {
		$("article").removeClass("on");
	});
});