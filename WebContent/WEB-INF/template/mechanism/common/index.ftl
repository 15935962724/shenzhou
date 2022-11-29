<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/newmechanism/css/iconfont.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/chart.bundle.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/jq_scroll.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>
<script type="text/javascript">
$().ready(function() {
			

	var $listForm = $("#listForm");
	var $yesterday = $("#yesterday");//昨天
	var $tomorrow = $("#tomorrow");//明天
	var $today = $("#today");//今天
	var $date = $("#date");
	var $num = $("#num");
	
	
	[@flash_message /]
	
	// 前一天
	$yesterday.click(function() {
	    var num = $num.val();
	    num--;
	    $num.val(num);
		var yesterday =GetDateStr(num);
		$date.val(yesterday);
		$listForm.submit();
		
	});
	//后一天
	$tomorrow.click(function() {
		 var num = $num.val();
		 num++;
		$num.val(num);
		var tomorrow =GetDateStr(num);
		$date.val(tomorrow);
		$listForm.submit();
	});
	//今天
	$today.click(function() {
		$num.val('0');
		$date.val('');
		$listForm.submit();
	});
	
	
				function GetDateStr(AddDayCount) { 
					var dd = new Date(); 
					dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期 
					var y = dd.getFullYear(); 
					var m = dd.getMonth()+1;//获取当前月份的日期 
					var d = dd.getDate(); 
					return y+"-"+m+"-"+d; 
				} 

});
	
</script>


<script>
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
				
				$(".ruler").css({"width" : (i * widt).toString() + "px" });
				
					//$(".ruler .hour:nth-of-type(1)").css({"left" : "20px" });
				
					$(".ruler .hour:nth-of-type(" + i + ")").css({"left" : ((i-1) * widt).toString() + "px" });
				//if(i==1)
				//	sty += ".ruler .hour:nth-of-type(" + i + "):after {content: '" + start_str  + "';left:0px;}";
				//else
					sty += ".ruler .hour:nth-of-type(" + i + "):after {content: '" + start_str  + "';}";
			}
		var li_width = (num)*widt+200;
		sty += "#ruler_frame #scrollDiv{width:" + li_width + "px}";
		sty += "#ruler_frame #scrollDivframe{width:" + li_width + "px}";
		sty += "#ruler_frame ul{width:" + li_width + "px}";
		sty += "#ruler_frame ul li{width:" + li_width + "px}";
		sty += "#ruler_frame ul li .det{width:" + (li_width-202) + "px}";
		
		sty += "</style>";
		$("head").append(sty);
	}

function creat_ruler(starttime,endtime)
	{
		var start_t=starttime.split(":");
		var end_t=endtime.split(":");
		if(parseInt(end_t[1]) > 0)
			{
				end_t[0] = parseInt(end_t[0]) + 1;
			}
		var num = end_t[0] - parseInt(start_t[0]);
		//var hour_r = 100 / num;
		var hour_r = "264";
		creat_html(num+1,"ruler");
		$(".ruler .hour").css({"width" : hour_r + "px"});
		creat_css(num+1,hour_r,parseInt(start_t[0]));
	}
	
	

$(function(){
	creat_ruler('${mechanism.workDate.startTime}','${mechanism.workDate.endTime}');
	$("#scrollDiv").Scroll({line:1,speed:500,timer:3000,up:"but_up",down:"but_down"});
	$("#scrollname").Scroll({line:1,speed:500,timer:3000,up:"but_up",down:"but_down"});
	var ruler_frame_height = $("#ruler_frame").height();
	
	$("#control").click(function(){
		if($("#control").html() == "展　开")
			{
				$("#ruler_frame").css({"height" : ruler_frame_height + "px"});
				$("#control").html("收　缩");
			}
		else
			{
				$("#ruler_frame").css({"height" : 315 + "px"});
				$("#control").html("展　开");
			}
	})
	
		$("#ruler_frame").scroll(function(){
		$("#scrollname").css({"left" :$("#ruler_frame").scrollLeft() + "px" });
		
		//console.log($("#ruler_frame").scrollLeft());
	})
	
});

</script>
<style>
.b_t_r_dcdcdc_1{border-top:1px solid #dcdcdc;border-right:1px solid #dcdcdc;}	
#scrollname{position: absolute; z-index: 999;top: 0px; left: 0px; background: #fff; width: 200px;}
#scrollname li{width: 200px; height:47px; line-height: 45px; font-size: 14px; color: #4c98f6; text-align: center; background: #fff;border-bottom: 1px solid #dcdcdc;border-right:1px solid #dcdcdc;}
.det {
	position: relative;
	margin: 0px auto;
	height: 45px;
	background: #fff;
	border: 1px solid #fff;;
	z-index: 1;
}
.work{
	position: absolute;
	background: #6cb0fe;
	height: 40px;
	z-index: 2;
	text-align: center;
	font-family: "SimSun", Arial;
	font-size: 14px;
	color: #fff;
	padding-top: 5px;
	border-left:1px solid #fff;
	border-right:1px solid #fff;
}
.ruler {
	position: relative;
	width: auto;
	margin: 0px auto;
	height: 100px;
	border-bottom: 1px solid #b8b8b8;
	
}
.ruler .hour{
	position: absolute;
	border-left: 1px solid #494949;
	height: 65px;
	top:35px;
	width: 286px;
	background: #ececec;
}	
.ruler .min {
	position: absolute;
	border-left: 1px solid #494949;
	height: 32px;
	top: 33px;
}
.ruler .hour:after {
	position: absolute;
	bottom: 75px;
	left:-16px;
	font: 11px/1 sans-serif;
}
.ruler .min {
	height: 32px;
}
.ruler .min:nth-of-type(6) {
	height: 51px;
	top: 14px;
}
.ruler .min:nth-of-type(1) {
	left: 8.33%;
}
.ruler .min:nth-of-type(2) {
	left: 16.66%;
}
.ruler .min:nth-of-type(3) {
	left: 24.99%;
}
.ruler .min:nth-of-type(4) {
	left: 33.32%;
}
.ruler .min:nth-of-type(5) {
	left: 41.65%;
}
.ruler .min:nth-of-type(6) {
	left: 49.98%;
}
.ruler .min:nth-of-type(7) {
	left: 58.31%;
}
.ruler .min:nth-of-type(8) {
	left: 66.64%;
}
.ruler .min:nth-of-type(9) {
	left: 74.97%;
}
.ruler .min:nth-of-type(10) {
	left: 83.3%;
}
.ruler .min:nth-of-type(11) {
	left: 91.63%;
}
.ruler .min:nth-of-type(12) {
	left: 100%;
}
.dis{display:none;}
.z40{font-size: 40px;}
.z50{font-size: 50px;}
	

</style>
</head>

<body>
<div class="m020">
	<table cellpadding="0" cellspacing="0" class="0" border="0" width="100%">
		<tr>
			<td>
				<ul id="sysj">
					<li>
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td colspan="2" class="z20616161" height="45">
									<span class="z279fff z40 iconfont">&#xe66a;</span>
									今日新增患者
								</td>
							</tr>
							<tr>
								<td colspan="2" align="center" valign="middle" class="h75 z16646464">
									<span class="z36279fff z75">${hangAddCount}</span>人
								</td>
							</tr>
							<tr>
								<td width="50%" align="center" class="z16646464">总患者</td>
								<td width="50%" align="center" class="z16646464">已流失</td>
							</tr>
							<tr>
								<td align="center" class="z16ff811b bra6a6a6">${patientCount}人</td>
								<td align="center" class="z16ff811b">${hangCount}人</td>
							</tr>
						</table>
					</li>
					<li>
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td colspan="2" class="z20616161" height="45">
									<span class="z279fff z40 iconfont">&#xe62a;</span>
									今日预约次数
								</td>
							</tr>
							<tr>
								<td colspan="2" align="center" valign="middle" class="h75 z16646464">
									<span class="z36279fff z75">${toDayAboutCount}</span>次
								</td>
							</tr>
							<tr>
								<td width="50%" align="center" class="z16646464">今日服务</td>
								<td width="50%" align="center" class="z16646464">明日服务</td>
							</tr>
							<tr>
								<td align="center" class="z16ff811b bra6a6a6">${toDayServerCount}次</td>
								<td align="center" class="z16ff811b">${futureServerCount}次</td>
							</tr>
						</table>
					</li>
					<li>
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td colspan="2" class="z20616161" height="45">
									<span class="z279fff z40 iconfont">&#xe622;</span>
									今日充值金额
								</td>
							</tr>
							<tr>
								<td colspan="2" align="center" valign="middle" class="h75 z16646464">
									<span class="z36279fff z75">${toDaySumRecharge!"0"}</span>元
								</td>
							</tr>
							<tr>
								<td width="50%" align="center" class="z16646464">总金额</td>
								<td width="50%" align="center" class="z16646464">已消费</td>
							</tr>
							<tr>
								<td align="center" class="z16ff811b bra6a6a6">${sumRecharge}元</td>
								<td align="center" class="z16ff811b">${sumConsumption!"0"}元</td>
							</tr>
						</table>
					</li>
				</ul>				
			</td>
		</tr>
		<tr>
			<td height="10"></td>
		</tr>
		<tr>
			<td>
				<table cellpadding="0" cellspacing="0" border="0" width="100%" class="k_2">
					<tr>
						<td class="pa20">

			            	<form id="listForm" action="index.jhtml" method="get">
			            	<input type="hidden" id="date" name="date" value="${date}" />
			        		<input type="hidden" id="num" name="num" value="${num}" />
			            	<table cellpadding="0" cellspacing="0" border="0" width="100%" id="cont">
			            		<tr class="b2292f7">
			            			<td width="200" height="55" align="center" class="z16ffffff">医生姓名</td>
			            			<td id="ruler_date">
										<table cellpadding="0" cellspacing="0" border="0" width="100%">
											<tr>
												<td width="10%" height="40" align="center"></td>
												<td width="90%" align="right" class="pr">
												<input id="nameOrphone" type="text"  name="nameOrphone" value="${nameOrphone}" placeholder="医师姓名" class="z16999999 bgffffff banone btlb2081d3 h26 pa tac" style="right: 0px;margin-right: 292px;"><input type="submit" value=" 搜 索 " class="z162d9dfc bgffffff btlb2081d3 br2081d3 h30">
												<input type="button" name="yesterday" id="yesterday" value=" 前一天 " class="z16999999 bgffffff banone btlb2081d3 h30" /><input type="button" name="today" id="today" value=" 今天 " class="z162d9dfc bgffffff banone btlb2081d3 h30" /><input type="button" name="tomorrow" id="tomorrow" value=" 后一天 " class="z16999999 bgffffff banone btlb2081d3 br2081d3 h30" />　&nbsp;
												</td>
											</tr>
										</table>
			           				</td>
			            		</tr>
			            		<tr>
			            			<td colspan="2" class="blrbdcdcdc" style="position: relative;">
			            			<div class="bbdcdcdc syrq">${date}</div>
			            				<div style="overflow-x:scroll; overflow-y: hidden; " class="brdcdcdc" id="ruler_frame">
											<div class="ruler" style="margin-left: 200px;"></div>
											<div style=" overflow: hidden;" id="scrollDivframe" class="pr">
												<div id="scrollDiv">
													<ul id="ruler_cont">
													[#list page.content as doctorMechanismRelation]
														 <li class="brdcdcdc bbdcdcdc">
															<table cellpadding="0" cellspacing="0" border="0">
																<tr>
																	<td width="200" height="45" valign="middle" align="center" class="brdcdcdc z14279fff">${doctorMechanismRelation.doctor.name}</td>
																	<td>
																		<div class="det">
																			[#list orderMechanismDate("null",mechanism.id,date,doctorMechanismRelation.doctor.id) as order ]
																					<div class="work" style="width:${indexWidth(order.startTime,order.endTime)}px;margin-left:${indexMarginLeft(order.startTime,mechanism.workDate.startTime)}px;">
																						${order.startTime}-${order.endTime}<br />${order.patientMemberName}
																						<!--
																							margin-left:（(课程开始时间转分钟-上班开始时间转分钟)/ 5 * 22）+1
																							width:（课程结束时间转分钟-课程开始时间转分钟）/ 5 * 22
																						-->
																					</div>
																			[/#list]
																		</div>
																	</td>
																</tr>
															</table>
														</li>
													[/#list]	
													</ul>						
												</div>
												<div id="scrollname" class="b_t_dcdcdc_1 b_r_dcdcdc_1">
			          								<ul style="width: 200px;">
			          								[#list page.content as doctorMechanismRelation]
			          									<li style="width: 200px;">${doctorMechanismRelation.doctor.name}</li>
			          								[/#list]
			          								</ul>
			          							</div>
			           						</div>
			            				</div>
										<div class="scroltit"><div class="updown" id="but_up">向下</div><div class="updown" id="but_down">向上</div></div>
			            				<script>
											$("#ruler_frame").css({"width" : ($("#cont").width()-100)+"px"});
										</script>
			            			</td>
			            		</tr>
			            		
			            		<tr>
			            			<td colspan="2" align="center" class="blbdcdcdc brdcdcdc z14e60001" bgcolor="#e1e1e1" style="cursor: pointer;" height="40" id="control" >收　缩</td>
			            		</tr>
			
			            		<tr>
			            			<td colspan="2">
			            				<table cellpadding="0" cellspacing="0" border="0" width="100%">
											<tr>
										   	    [@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
													[#include "/mechanism/include/pagination.ftl"]
												[/@pagination]
											</tr>
			            				</table>
			            			</td>
			            		</tr>
			            		<tr>
			            			<td colspan="2" height="50"></td>
			            		</tr>
			            	</table>
			            	</form>   
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
</body>
</html>
