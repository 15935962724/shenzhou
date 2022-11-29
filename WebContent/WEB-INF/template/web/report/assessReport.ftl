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
<script>

</script>
<style>
	.pull-to-refresh-layer{margin-top: 6.21rem;}	
	.bar{padding-right: 0px;padding-left: 0px;border-bottom:none;}
	.page{background: #f0f1f0;}
	.card{border-radius: 0; box-shadow: #ffffff 0px 0rem 0rem;margin: 0px;}
	.bl24c98f6{border-left: 2px solid #4c98f6;}
	.w05{width: 0.5rem;}
#layer{width:100%;height:100%; left:0;top:0; background-color:#CCC; position:absolute;z-index:9999;background-color:transparent; display:none;}
/*#report{width:100%;height:100%; left:0;top:0; background-color:#CCC; position:absolute;z-index:99;background-color:transparent; display:none;}*/
.layer{z-index:9999; /*FF IE7 该值为本身高的一半*/ margin-top:0px; position:fixed!important;/*FF IE7*/ position:absolute;/*IE6*/  _top:expression(eval(document.compatMode &&  document.compatMode=='CSS1Compat') ?  documentElement.scrollTop + (document.documentElement.clientHeight-this.offsetHeight)/2 :/*IE6*/document.body.scrollTop + (document.body.clientHeight - this.clientHeight)/2);/*IE5 IE5.5*/display:block;background-color:#FFF;-moz-border-radius:10px; -webkit-border-radius:10px; border-radius:10px;position:relative;-moz-box-shadow:0px 0px 20px #979797; -webkit-box-shadow:0px 0px 20px #979797; box-shadow:0px 0px 20px #979797;}
</style>
<script>
$(function () {		
		$(document).on('refresh', '.pull-to-refresh-content',function(e) {
		setTimeout(function() {
			var cardHTML =  '<div class="card-content">';
			for (var i = 0; i < 9; i++) {				
				cardHTML += '				<div class="card-content-inner">';
				cardHTML += '					<a href="javascript:;">';
				cardHTML += '						<table cellpadding="0" cellspacing="0" border="0" width="98%" class="m0auto tlf">';
				cardHTML += '							<tr>';
				cardHTML += '								<td class="bl24c98f6" style="width: 0.5rem;" rowspan="2"></td>';
				cardHTML += '								<td class="z_0782_909090_1 tdwb"><span class="z_106_4c98f6_1">诊评报告 </span>北京神州儿女康复机构北京神州儿女康复机构北京神州儿女康复机构</td>';
				cardHTML += '								<td align="right" class="w6 tdwb">诊评人：上官诸葛</td>';
				cardHTML += '							</tr>';
				cardHTML += '							<tr>';
				cardHTML += '								<td class="z_0782_323232_1 tdwb">偏瘫</td>';
				cardHTML += '								<td align="right" class="z_0782_909090_1 tdwb">2017-10-10</td>';
				cardHTML += '							</tr>';
				cardHTML += '						</table>';
				cardHTML += '					</a>';
				cardHTML += '				</div>';

			}
			cardHTML += '</div>';

			$(e.target).find('.card .card-content').replaceWith(cardHTML);
			$(".card-content").css("min-height" , window.screen.height + "px");  
		// done
			$.pullToRefreshDone('.pull-to-refresh-content');
		}, 2000);
	});
	
	
	$.init();
	
	$(".card-content").css("min-height" , window.screen.height + "px");  
	
});

function get_assess(id,count,assessReportId,patientMemberId)
	{
		var html;
		for(var i=0; i<=count-1; i++)
			{
				html = "";	
				if((i==id) &&($("#assess_" + i + "_info").html() == "")) 
					{
						
						//在这里调用ajax获取数据
						$.ajax({
							url: "/shenzhou/web/assessReport/assessReportDetails.jhtml",
							type: "GET",
							contentType:"application/x-www-form-urlencoded; charset=UTF-8",
							data: {assessReportId:assessReportId,patientMemberId:patientMemberId},
							traditional: true,  
							async: false,
							success: function(data) {
								var dataObj = eval('('+data+')');
								if(dataObj==null||dataObj.length<=0){
									$.fn.tips({content:'暂无报告数据'});
									return;
								}
									html += '						<table cellpadding="0" cellspacing="0" border="0" width="98%" class="m0auto tla" style="margin-top: 15px;">';
									html += '							<tr>';
									html += '								<td>';
									html += '									<b>现状说明：</b>'+dataObj.assessReport.nowExplain+'';
									html += '								</td>';
									html += '							</tr>';
									html += '							<tr>';
									html += '								<td>';
									html += '									<b>评估结果：</b>'+dataObj.assessReport.assessResult+'';
									html += '								</td>';
									html += '							</tr>';
									html += '							<tr>';
									html += '								<td>';
									html += '									<b>康复建议：</b>'+dataObj.assessReport.proposal+'';
									html += '								</td>';
									html += '							</tr>';
									html += '						</table>';
									html += '						<table cellpadding="0" cellspacing="0" border="0" width="98%" class="m0auto tla" id="plan_' + i + '_info">';
									for(var x=0;x<dataObj.recoveryPlans.length;x++)
										{
											html += '							<tr>';
											html += '								<td class="bbf0f1f0" id="plan_' + i + '_' + x + '" onClick="get_plan(' + i + ',' + x + ','+dataObj.recoveryPlans.length+','+dataObj.recoveryPlans[x].id+');">';
											html += '									<table cellpadding="0" cellspacing="0" border="0" width="100%">';
											html += '										<tr>';
											html += '											<td class="h208 z_0782_4c98f6_1">康复计划</td>';
											html += '											<td align="right" class="z_0782_909090_1">'+dataObj.recoveryPlans[x].startTime+'至'+dataObj.recoveryPlans[x].endTime+' <span class="iconfont">&#xe62e;</span></td>';
											html += '										</tr>';
											html += '									</table>';
											html += '								</td>';
											html += '							</tr>';	
											html += '							<tr>';
											html += '								<td id="plan_' + i + '_' + x + '_info"></td>';
											html += '							</tr>';	
										}
									html += '						</table>';	
						
						
								
							}
						});
						
					}
				$("#assess_" + i + "_info").html(html);
			}
		$(".infinite-scroll").scrollTop($("#assess_" + id).offset().top - $("#assess_1").offset().top);
	}
	
function get_plan(a_id,id,count,recoveryPlansId)
	{
	
		var patientMemberId = document.getElementById("patientMemberId").value;
		var html,icon;
		for(var i=0; i<=count-1; i++)
			{
				html = "";	
				icon = "&#xe62d;";
				if((i==id) &&($("#plan_" + a_id + "_" + i + "_info").html() == "")) 
					{
					
						$.ajax({
							url: "/shenzhou/web/assessReport/recoveryPlanDetails.jhtml",
							type: "GET",
							contentType:"application/x-www-form-urlencoded; charset=UTF-8",
							data: {recoveryPlanId:recoveryPlansId,patientMemberId:patientMemberId},
							traditional: true,  
							async: false,
							success: function(data) {
							var dataObj = eval('('+data+')');
								html += '计划内容';
								html += '									<table cellpadding="0" cellspacing="0" border="0" width="98%" align="right" class="mtb0645">';
								html += '										<tr>';
								html += '											<td colspan="2">';
								html += '												<b>计划人：</b><br>';
								html += '													'+dataObj.recoveryPlan.recoveryDoctoyDoctorName+'';
								html += '											</td>';
								html += '										</tr>';
								html += '										<tr>';
								html += '											<td colspan="2">';
								html += '												<b>康复项目：</b><br>';
								
								for(var x=0;x<dataObj.recoveryPlan.drillContents.length;x++){
									html += '													'+dataObj.recoveryPlan.drillContents[x].serverProjectCategory+' '+dataObj.recoveryPlan.drillContents[x].time+'次';
								}
								
								html += '											</td>';
								html += '										</tr>';
								html += '										<tr>';
								html += '											<td colspan="2">';
								html += '												<b>短期目标：</b><br>';
								html += '													'+dataObj.recoveryPlan.shortTarget+'';
								html += '											</td>';
								html += '										</tr>';
								html += '										<tr>';
								html += '											<td colspan="2">';
								html += '												<b>长期目标：</b><br>';
								html += '													'+dataObj.recoveryPlan.longTarget+'';
								html += '											</td>';
								html += '										</tr>';
								var j=1;
										html += '										<tr>';
										html += '											<td class="bbf0f1f0 h208 z_0782_4c98f6_1" onClick="get_record(' + a_id + ',' + i + ',' + j +',1,'+dataObj.recoveryPlan.id+')">康复记录</td>';
										html += '											<td align="right" class="bbf0f1f0 h208" id="record_' + a_id + '_' + i + '_' + j +'"><span class="iconfont">&#xe62e;</span></td>';
										html += '										</tr>';
										html += '										<tr>';
										html += '											<td colspan="2" id="record_' + a_id + '_' + i + '_' + j +'_info"></td>';
										html += '										</tr>';
								html += '										<tr>';
								html += '											<td class="bbf0f1f0 h208 z_0782_4c98f6_1">疗效总结</td>';
								html += '											<td align="right" class="bbf0f1f0 h208 z_0782_4c98f6_1" onclick="disp_hidden_d(123,\'plan_' + a_id + '_' + i + '_summary\')">添加</td>';
								html += '										</tr>';
								
								html += '										<tr>';
								html += '											<td colspan="2">';
								html += '												<b>疗效总结：</b><br>';
								if(dataObj.recoveryPlan.recoveryRecordEffect!=null&&dataObj.recoveryPlan.recoveryRecordEffect!=undefined){
									html += '													'+dataObj.recoveryPlan.recoveryRecordEffect+'';
								}
								html += '											</td>';
								
								
								
								html += '										<tr>';
								html += '											<td colspan="2" id="plan_' + a_id + '_' + i + '_summary"></td>';
								html += '										</tr>';
								html += '									</table>';
								icon = "&#xe62e;";	
									
									
								}
							});
									
					}
				$("#plan_" + a_id + "_" + i + " .iconfont").html(icon);
				$("#plan_" + a_id + "_" + i + "_info").html(html);
			}
		$(".infinite-scroll").scrollTop($("#plan_" + a_id + "_" + id).offset().top - $("#assess_1").offset().top);
	}
function get_record(a_id,p_id,id,count,recoveryPlansId)
	{
	
		var patientMemberId = document.getElementById("patientMemberId").value;
		var html,icon;
		for(var i=1; i<=count; i++)
			{
				html = "";	
				icon = "&#xe62d;";
				if((i==1) &&($("#record_" + a_id + "_" + p_id + "_" + i + "_info").html() == "")) 
					{
					
					$.ajax({
							url: "/shenzhou/web/assessReport/recoveryPlanDetails.jhtml",
							type: "GET",
							contentType:"application/x-www-form-urlencoded; charset=UTF-8",
							data: {recoveryPlanId:recoveryPlansId,patientMemberId:patientMemberId},
							traditional: true,  
							async: false,
							success: function(data) {
							var dataObj = eval('('+data+')');
								if(dataObj==null||dataObj.recoveryRecords.length<=0){
									$.fn.tips({content:'暂无记录数据'});
									return;
								}	
								html += '												<table cellpadding="0" cellspacing="0" border="0" width="98%" align="right">';
								var j = 1;
								for(var x=0;x<dataObj.recoveryRecords.length;x++)
									{
										html += '													<tr>';
										html += '														<td><span class="z_0782_4c98f6_1">&#8226;</span> '+dataObj.recoveryRecords[x].recoveryRecordDate+'</td>';
										html += '													</tr>';
										html += '													<tr>';
										html += '														<td style="padding-left: 1rem;">'+dataObj.recoveryRecords[x].recoveryRecordContent+'</td>';
										html += '													</tr>';
									}
								html += '												</table>';
								icon = "&#xe62e;";
										
									
									
								}
							});
						
					}
				$("#record_" + a_id + "_" + p_id + "_" + i + " .iconfont").html(icon);
				$("#record_" + a_id + "_" + p_id + "_" + i + "_info").html(html);
			}
		$(".infinite-scroll").scrollTop($("#record_" + a_id + "_" + p_id + "_" + id).offset().top - $("#assess_1").offset().top);	
	}
function disp_hidden_d(id,post_id)
	{
		$("#p_id").val(id);
		$("#post_id").val(post_id);
		$("#memo").val("");
		//$("#layer").css({"display":"block"});	
		$("#layer").toggle();
		$(".layer").css({"width": "24rem", "max-width": "576px", "height": "auto", "left": "13.3333333333333rem", "top": "9rem", "margin-left": "-12rem"});
	}
function post_summary()
	{
		var html = $("#memo").val();
		$("#" + $("#post_id").val()).html(html);
		$("#" + $("#post_id").val()).css({"padding": "1rem"});
		disp_hidden_d();
	}
</script>

</head>

<body style="background:#f0f1f0">
<!-- page 容器 -->
<input type="hidden" name="patientMemberId" id="patientMemberId" value="${patientMember.patientMemberId}">
<div class="page" id="page-infinite-scroll">
	<!-- 标题栏 -->
	<header class="bar bar-nav bgffffff" id="header" style="height: 8rem;">
		<table cellpadding="0" cellspacing="0" border="0" width="98%" align="center" style="border-bottom: 1px solid #f0f1f0;">
			<tr>
				<td align="center" class="wz w2"><a href="javascript:;" onclick="javascript:history.back(-1);"><img src="${base}/resources/web/images/j_2.png" class="imgw0667 hauto"></a></td>
				<td align="center">
					健康档案
				</td>
			</tr>
		</table>
		<table cellpadding="0" cellspacing="0" border="0" width="100%" align="center" class="m0auto" style=" border-bottom: 0.5rem solid #f0f1f0; margin-top: 0.5rem;">
			<tr>
				<td>
					<table cellpadding="0" cellspacing="0" border="0" width="98%" class="m0auto">
						<tr>
							<td valign="top" align="left" style="width: 5rem;"><img src="${patientMember.logo}" style="width:3.75rem; max-width: 90px;" class="br5"></td>
							<td class="z_1_323232_1" style="line-height: 1.4rem;" valign="top">
								${patientMember.name}<br>
								<span class="z_0782_666666_1 lh08" style="display: block;">
									${patientMember.birth}周岁 [#if patientMember.gender=="male"]男[/#if][#if patientMember.gender=="female"]女[/#if]
										<br>
									建档时间：${patientMember.createDate}
								</span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>  	
	</header>
	<!-- 这里是页面内容区 -->
	<div class="content pull-to-refresh-content infinite-scroll" data-ptr-distance="55" data-distance="100">
		<div class="pull-to-refresh-layer">
			<div class="preloader"></div>
			<div class="pull-to-refresh-arrow"></div>
		</div>
		<div class="card">
			<div class="card-content">
				
				[#list assessReportList as assessReport]
				<div class="card-content-inner">
					<a href="javascript:;" id="assess_2" onClick="get_assess(${assessReport_index},${assessReportSize},${assessReport.id},${patientMember.patientMemberId});">
						<table cellpadding="0" cellspacing="0" border="0" width="98%" class="m0auto tlf">
							<tr>
								<td class="bl24c98f6" style="width: 0.5rem;" rowspan="2"></td>
								<td class="z_106_4c98f6_1 tdwb">诊评报告 <span class="z_0782_909090_1">${assessReport.mechanismName}</span></td>
								<td align="left" class="w6 tdwb">诊评人：${assessReport.doctorName}</td>
							</tr>
							<tr>
								<td class="z_0782_323232_1 tdwb">${assessReport.diseaseName}</td>
								<td align="right" class="z_0782_909090_1 w6 tdwb">${assessReport.createDate}</td>
							</tr>
						</table>
					</a>
					<div id="assess_${assessReport_index}_info"></div>
				</div>
				[/#list]
				
				
			</div>
		</div>
		
		
		
	</div>	
</div>

	<div id="layer" style="display: none;">
		<div class="layer">
			<table cellpadding="0" cellspacing="0" border="0" style="width: 22rem;" class="m0auto">
				<tr>
					<td align="center" colspan="2" class="h229">添加总结</td>
				</tr>
				<tr>
					<td align="center" colspan="2">
						<textarea name="memo" id="memo" placeholder="请填写疗效总结" style="width: 22rem; height: 6rem; border: 1px solid #bebebe;"></textarea>
						<input name="p_id" id="p_id" type="hidden" value="" />
						<input name="post_id" id="post_id" type="hidden" value="" />
				  </td>
				</tr>
				<tr>
					<td align="left" class="h316">
						<input type="button" value="取消" style="width: 8rem;height: 2rem;line-height: 2rem;border: 1px solid #bebebe;background: #fff;" onClick="disp_hidden_d();">
					</td>
					<td align="right">
						<input type="button" value="确定" style="width: 8rem;height: 2rem;line-height: 2rem;border: 1px solid #4c98f6;background: #4c98f6;color: #feeeee" onClick="post_summary();">
					</td>
				</tr>
			</table>    
		</div>
	</div>

</body>
</html>
