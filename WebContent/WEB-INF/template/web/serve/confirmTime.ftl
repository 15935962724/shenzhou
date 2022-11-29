<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<title>好康护 - 让幸福简单起来</title>
<link rel="stylesheet" href="${base}/resources/web/light7/css/light7.min.css">
<link rel="stylesheet" href="${base}/resources/web/light7/css/light7-swiper.min.css">
<link href="${base}/resources/web/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/web/font/iconfont.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/web/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/web/light7/js/light7.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${base}/resources/web/light7/js/light7-swiper.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${base}/resources/web/js/js.js"></script>
<script type="text/javascript" src="${base}/resources/web/tips/jquery.tips.js"></script>
<style>
	.page{background: #ffffff;}
	.bar{height:3.125rem; background: #fff;}
	.bar .icon {padding: 1.0625rem .1rem;line-height: 1rem;}
	.bar .button{margin-top: 0.5rem;}
	.title{line-height: 3.125rem;}
	.bar-nav~.content{top: 3.125rem;}
	.content-block {margin: 0.825rem 0;}
	.picker-modal{background: #f3f3f3;}

</style>
<script>


var AppoCourseTime = 50, appoMoney = 100;  //上一页传过来的
var rulerStartTime = "00:00", rulerEndTime = "24:00";
var workDayIds = '';	
function ToggleTag(id,count,workDayId)
	{	
		workDayIds = workDayId;
		$("#workDayId").val(workDayId);
		id=id+1;
		for(var i = 1; i <= count; i++)
			{
				$("#tag_" + (i-1)).removeClass("z_106_4c98f6_1");
				$("#tag_" + (i-1)).removeClass("bgffffff");
				if(i == id)
					{
						$("#tag_" + (i-1)).addClass("z_106_4c98f6_1");
						$("#tag_" + (i-1)).addClass("bgffffff");
						
						$.ajax({
								url: "/shenzhou/web/order/doctorWorkDayDetails.jhtml",
								type: "GET",
								contentType:"application/x-www-form-urlencoded; charset=UTF-8",
								data: {workDayId:workDayId},
								traditional: true,  
								async: false,
								success: function(data) {
								var dataObj = eval('('+data+')');
										html = '';
										html += '			<div class="slide" id="rulerSlide">';
										html += '				<div style="height:15rem;" class="slided" id="rulerFrame">';
										
										//这里加循环机构时间
										for(var i=0;i<dataObj.mechanismItemList.length;i++){	
										//alert('+dataObj.mechanismItemList[i].width+');
										//alert('+dataObj.mechanismItemList[i].distance+');
											html += '					<div class="pr" id="mach">';
											html += '						<div class="z_106_4c98f6_1 pa mach"  style="width:'+dataObj.mechanismItemList[i].width+'rem;margin-left:'+dataObj.mechanismItemList[i].distance+'rem;">';
											html += '							'+dataObj.mechanismItemList[i].meschanismName+'';
											html += '						</div>';
											html += '					</div>';
										}
										//这里循环订单时间
										
										html += '					<div class="det" onclick="ClickRuler();">';
										html += '						<div class="work" style="width:160.08rem;margin-left: 0rem;"></div>';
										for(var i=0;i<dataObj.orderItemList.length;i++){
										html += '						<div class="appo" style="width:'+dataObj.orderItemList[i].width+'rem;margin-left: '+dataObj.orderItemList[i].distance+'rem;">已预约</div>';
										}
										//html += '						<div class="lock" style="width:3.4444rem;margin-left: 8.4444rem;">已锁定</div>';
										//html += '						<div class="myappo" style="width:3.4444rem;margin-left: 13rem;">您已约</div>';
										html += '						<div class="temp" style="">当前选择</div>';
										html += '					</div>';
										html += '					<div class="ruler"></div>';
										html += '				</div>';
										html += '			</div>';
									}
								});
						
						
						$('#rulerSlide').replaceWith(html);
						$.pullToRefreshDone('#SetTime');
						$("#appoTime").val();
						creat_ruler();
						
					}
			}
	}

	
function set_min_width()
	{
		var start_t=rulerStartTime.split(":");
		var end_t=rulerEndTime.split(":");
		if(parseInt(end_t[1]) > 0)
			{
				end_t[0] = parseInt(end_t[0]) + 1;
			}
		var num = $(".det").width() / (end_t[0] - parseInt(start_t[0])) / 12;
		return num;
	}

function set_width_item()
	{
		var item_arr = $("#items").val().split("|");
		var item_num = $("#num").val();
		if(item_num <= 0)
			{
				alert("预约次数应大于0");
				return false;
			}
		var item_time = item_arr[0] / 5;
		$("#temp").css("width",(item_time * item_num * set_min_width())+ "px" );
	}

function set_margin_left(  )
	{
		var start_t = 0;
		
		var appoTime = $("#appoTime").val();
		
		var startTime = appoTime.replace("时",":");
		startTime = startTime.replace("分","");
		startTime = startTime.replace(" ","");
		
		appoTime = appoTime.replace("分","");
		
		var appo_arr = appoTime.split("时 ");
		
		$.ajax({
		url: "/shenzhou/web/order/verifyTime.jhtml",
		type: "GET",
		contentType:"application/x-www-form-urlencoded; charset=UTF-8",
		data: {workDayId:workDayIds,startTime:startTime,count:${count},projectId:${projectId}},
		traditional: true,  
		async: false,
		success: function(data) {
		var dataObj = eval('('+data+')');
				var obj = eval('('+dataObj.data+')');
				if(dataObj.status=='400'){
					$.fn.tips({content:dataObj.message});
					return;
				}else{
					var num = (parseInt(appo_arr[0]) * 60 + parseInt(appo_arr[1]))  / 5;
					$(".temp").css({"display" : "block", "margin-left" : obj.distance+ "rem", "width" :  obj.width+"rem"})
					var moveNum =  ((parseInt(appo_arr[0]) * 60 )  / 5 - 5) * set_min_width() - Math.abs($("#rulerSlide").position().left);
					
					$("#rulerSlide").scrollLeft(moveNum);
					
					$("#rulerMoney").html("预约价格约为：￥" + obj.countPrice);
					
					$("#startTime").val(obj.startTime);
					$("#endTime").val(obj.endTime);
					$("#countPrice").val(obj.countPrice);
					$("#num").val(obj.count);
				}
				
			}
		});
		
	}

function creat_html(num,classname)
	{
		var html = "";
		for (var i = 1; i <= num; i++)
			{
				if(i != num)
					{
						html += '	<div class="hour">';
						html += '		<div class="min"></div>';
						html += '		<div class="min"></div>';
						html += '		<div class="min"></div>';
						html += '		<div class="min"></div>';
						html += '		<div class="min"></div>';
						html += '		<div class="min"></div>';
						html += '		<div class="min"></div>';
						html += '		<div class="min"></div>';
						html += '		<div class="min"></div>';
						html += '		<div class="min"></div>';
						html += '		<div class="min"></div>';
						html += '	</div>'
					}
				else
					{
						html += '<div class="hour"></div>';
					}
			}
		$("." + classname).html(html);		
	}
function creat_css(num,widt,start)
	{
		var start_num,start_str,sty="<style>";
		$("#rulerSlide").css({"width" : $(document).width() + "px" });
		$("#rulerFrame").css({"width" : ((num-1) * widt).toString() + "rem" });
		$("#mach").css({"width" : ((num-1) * widt).toString() + "rem" });
		$(".det").css({"width" : ((num-1) * widt).toString() + "rem" });
		$(".ruler").css({"width" : ((num-1) * widt).toString() + "rem" });
		for(var i=1; i<=num; i++)
			{
				start_num = start + i - 1;
				if(start_num < 10)
					{
						start_str = "0" + start_num.toString() + ":00";
					}
				else
					{
						start_str = start_num.toString() + ":00";
					}
				$(".ruler .hour:nth-of-type(" + i + ")").css({"left" : ((i-1) * widt).toString() + "rem"});
				if(i==1)
					sty += ".ruler .hour:nth-of-type(" + i + "):after {content: '" + start_str  + "';left:0px;}";
				else
					sty += ".ruler .hour:nth-of-type(" + i + "):after {content: '" + start_str  + "';}";
			}	
		sty += "</style>";
		$("head").append(sty);
	}

function creat_ruler()
	{
		var start_t=rulerStartTime.split(":");
		var end_t=rulerEndTime.split(":");
		if(parseInt(end_t[1]) > 0)
			{
				end_t[0] = parseInt(end_t[0]) + 1;
			}
		var num = end_t[0] - parseInt(start_t[0]);
		//var hour_r = 100 / num;
		var hour_r = "6.67"
		creat_html(num+1,"ruler");
		$(".ruler .hour").css({"width" : hour_r + "rem"});
		creat_css(num+1,hour_r,parseInt(start_t[0]));
	}

function mouseX()
	{
		var e = event || window.event;
		var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
		var x = e.pageX || e.clientX + scrollX;  //当前屏幕点击位置（x轴）
		return Math.abs($(".det").position().left) + x;
	}
	
	

function ClickRuler()
	 {
		var posi = mouseX();
		var rulerLen = $(".det").width();
		var t = Math.round(posi * 288 / rulerLen) * 5; //获取点击的时间（单位：分钟）
		var h = parseInt(t/60);
		if(h<10)
			h = "0" + h;
		var m = t % 60;
		if(m<10)
			m = "0" + m;
		$("#appoTime").val(h + "时 " + m + "分");
		set_margin_left();
	}
	
	

</script>
<script>
$(function () {		

	creat_ruler();
	
	$("#appoTime").picker({
		  toolbarTemplate: '<header class="bar bar-nav">\
		  <button class="button button-link pull-right close-picker" onclick="set_margin_left();">确定</button>\
		  <h1 class="title">选择时间</h1>\
		  </header>',
		  cols: [
			{
			  textAlign: 'center',
			  values: ['0时', '1时', '2时', '3时', '4时', '5时', '6时', '7时', '8时', '9时', '10时', '11时', '12时', '13时', '14时', '15时', '16时', '17时', '18时', '19时', '20时', '21时', '22时', '23时']
			},
			{
			  textAlign: 'center',
			  values: ['0分', '5分', '10分', '15分', '20分', '25分', '30分', '35分', '40分', '45分', '50分', '55分']
			}
		  ]
		});
	
	
	if(${workDaySize}=='0'){
		$.fn.tips({content:'医生本日暂无排班'});
	}
	if(${workDaySize}!='0'){
		ToggleTag(0,${doctorWorkDays?size},${doctorWorkDays[0].id});
	}
	$.init();	
	
});
</script>

<script>
function confirm() {  
var startTime = document.getElementById("startTime").value;
	var endTime = document.getElementById("endTime").value; 
	var countPrice = document.getElementById("countPrice").value; 
	var num = document.getElementById("num").value; 
	var projectId = document.getElementById("projectId").value; 
	
	if(startTime==''||startTime==null||startTime==undefined){
		$.fn.tips({content:'请选择时间'});
		return false;
	}
	if(endTime==''||endTime==null||endTime==undefined){
		$.fn.tips({content:'请选择时间'});
		return false;
	}
	document.getElementById("inputForm").submit(); 
}

</script>

</head>

<body>
<form id="inputForm" action="/shenzhou/web/order/confirmOrder.jhtml" method="GET">
<input type="hidden" name="startTime" id="startTime" value="">
<input type="hidden" name="endTime" id="endTime" value="">
<input type="hidden" name="countPrice" id="countPrice" value="">
<input type="hidden" name="num" id="num" value="">
<input type="hidden" name="projectId" id="projectId" value="${projectId}">
<input type="hidden" name="workDayId" id="workDayId" value="">
<!-- page 容器 -->
<div class="page">
	<!-- 标题栏 -->
	<header class="bar bar-nav">
		<a href="" onclick="javascript:history.back(-1);" class="icon icon-left pull-left"></a>
		<button class="button pull-right">确认</button>
		<h1 class="title lh3125">项目详情</h1>
	</header>

	<!-- 这里是页面内容区 -->
	<div class="content">
		<div id="data-nav" class="h3916 z_0924_acc6e5_1 slide">
			[#list doctorWorkDays as workDay]
				<div class="slided h3916 w4166" id="tag_${workDay_index}" onClick="ToggleTag(${workDay_index},${doctorWorkDays?size},${workDay.id});">
					<span class="slides">
						<!-- 周四 --><br>${workDay.workDayDate?string("MM-dd")}
					</span>
				</div>
			[/#list]
		</div>
		<div id="SetTime">
			<div style="margin: 0px 1rem;">
				<input type="text" id="appoTime" placeholder="选择时间" style="border: 1px solid #4c98f6; text-align: center; border-radius: 0.2rem; width: 6.25rem;"  class="z_0924_333333_1 lh170">
				<span style="float: right; " id="rulerMoney">可点击时间轴进行时间选择</span>
				<div class="clear_box"></div>
			</div>
			<div class="slide" id="rulerSlide">
			</div>
		</div>
		<div class="z_0782_909090_1 lh170 pa1">
			<span class="cue bgcfe7bd"></span>绿色背景代表可预约状态<br>
			<span class="cue bgeeeeee"></span>浅灰色背景代表医师休息状态<br>
			<span class="cue bg7294bc"></span>蓝色背景代表已锁定状态<br>
			<span class="cue bgf6ae33"></span>黄色背景代表已被预约状态<br>
			<span class="cue bgc77777"></span>红色背景代表您已预约状态
		</div>
	</div>
</div>
</form>
</body>
</html>
