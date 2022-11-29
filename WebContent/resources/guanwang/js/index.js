// JavaScript Document
$(function(){
	$('#banner').cycle({ 
			fx:'fade',
			pager:'#btn',
			prev:'.b_prev',
			next:'.b_next'
	});
	var btn_w = $("#btn").width() / 2;
	console.log(btn_w);
	$("#btn").css({"margin-left": "-" + btn_w + "px"});
	
	
	
	
	
	var sWidth = $("#slider_name").width();
	var len = $("#slider_name .silder_panel").length;
	var index = 0;
	var picTimer;
	
	var btn = "<a class='prev'>Prev</a><a class='next'>Next</a>";
	$("#slider_name").append(btn);

	$("#slider_name .silder_nav li").mouseenter(function() {																		
		index = $("#slider_name .silder_nav li").index(this);
		showPics(index);
	}).eq(0).trigger("mouseenter");


	// Prev
	$("#slider_name .prev").click(function() {
		index -= 1;
		if(index == -1) {index = len - 1;}
		showPics(index);
	});

	// Next
	$("#slider_name .next").click(function() {
		index += 1;
		if(index == len) {index = 0;}
		showPics(index);
	});

	// 
	$("#slider_name .silder_con").css("width",sWidth * (len));
	
	// mouse 
	$("#slider_name").hover(function() {
		clearInterval(picTimer);
	},function() {
		picTimer = setInterval(function() {
			showPics(index);
			index++;
			if(index == len) {index = 0;}
		},5000);  
	}).trigger("mouseleave");
	
	// showPics
	function showPics(index) {
		var nowLeft = -index*sWidth; 
		$("#slider_name .silder_con").stop(true,false).animate({"left":nowLeft},300);
		$("#slider_name .silder_nav li").removeClass("current").eq(index).addClass("current"); 
	}
	
	
	
	
})