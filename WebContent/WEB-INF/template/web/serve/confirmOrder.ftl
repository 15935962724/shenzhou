<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta charset="UTF-8">
<title>确认订单</title>
<link href="${base}/resources/web/css/css.css" rel="stylesheet" type="text/css">
<script src="${base}/resources/web/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/web/tips/jquery.tips.js"></script>
<script>
function add_1()
	{
		$("#num").val(parseInt($("#num").val()) + 1);
		$("#num_t").val($("#num").val());
		if(($("#money").val()=="") || isNaN($("#money").val()))
			{
				alert('数据获取错误');
				return false;
			}
		else
			{
				$("#count_money_2").html("￥" + $("#num").val()*$("#money").val());
				$("#count_money_1").html($("#count_money_2").html());
			}
		
	}
function sub_1()
	{
		if($("#num").val()>1)
			{
				$("#num").val(parseInt($("#num").val()) - 1);
				$("#num_t").val($("#num").val());
			}
		if(($("#money").val()=="") || isNaN($("#money").val()))
			{
				alert('数据获取错误');
				return false;
			}
		else
			{
				$("#count_money_2").html("￥" + $("#num").val()*$("#money").val());
				$("#count_money_1").html($("#count_money_2").html());
			}
		
	}
	
</script>

<script>
function aClick() { 
	var startTime = "${startTime}";
	if(startTime==''||startTime==null||startTime==undefined){
		$.fn.tips({content:'请选择时间'});
		return false;
	}
	document.getElementById("inputForm").submit(); 
 }

</script>
</head>

<body style="background:#f0f1f0">
<form id="inputForm" action="/shenzhou/web/order/submitOrder.jhtml" method="GET" >
<input type="hidden" name="startTime" id="startTime" value="${startTime}">
<input type="hidden" name="workDayId" id="workDayId" value="${workDayId}">
<input type="hidden" name="projectId" id="projectId" value="${project.id}">
<input type="hidden" name="num" id="num" value="${num}">
<input type="hidden" name="memo" id="memo" value="用户下单">
     <div id="header" class="pf h329" style="border-bottom:#f0f1f0 1px solid;">
     	<div id="back" align="left"><a href="js/"><img alt="" src="${base}/resources/web/images/backBtnImg.png" style="width:0.59rem; height:auto" class="backImg"></a></div>
     	<div id="title" style="align-content: center" align="center" class="titleLabel">确认订单</div>
     </div>
     
     <div class="confirm_order_top">
     	<div class="confirmOrder_top">
     		<div class="confirmOrder_top_left">
     			<div class="confirm_order_topImg"><img src="${project.logo}" class="confirmOrder_Img"></div>
     		</div>
     		<div class="confirmOrder_top_right">
				<div class="confirmOrder_top_right_title"><span style="font-size:1rem;color:#323232;">${project.name}</span></div>
    		    <div class="confirmOrder_top_right_intoduce"><span style="font-size:1rem;color:#323232;">${introduce}</span></div>
    		    <div class="confirmOrder_top_right_money"><span style="font-size:1rem;color:#db0101;">${project.price}</span><span style="font-size:0.75rem;color:#323232;">/${project.time}分钟</span></div>
     		</div>
     	</div>
     	<div class="confirmOrder_footer">
     		<div class="confirmOrder_top_footer">
     			<div class="confirmOrder_footerDcotr"><span style="margin-left:0.833rem;line-height:1.98rem; font-size:0.833rem;color:#666666;">服务医师:${doctor.name}  ${doctorPhone}</span></div>
     			<div class="confirmOrder_footerExpert"><span style="margin-left:0.833rem;font-size:0.833rem;color:#666666;">服务地址：${mechanism.address}</span></div>
     		</div>
     	</div>
     </div>
     <div class="confirm_order_moddle">
     	 <div class="confirmOrder_moddle_numbers">
     	 	 <div class="confirmOrde_moddle_number_leftStyle"><span style="margin-left:0.833rem; font-size:1rem;color:#323232;line-height:3.33rem;">预约次数：</span></div>
     	 	 <div class="confirmOrde_moddle_number_right">
     	 	 	
     	 	 	<a href="javascript:;" class="confirmOrder_moddle_number_addAnd" onClick="add_1();"><span style="font-size:1.8rem;color:#aaaaaa;">+</span></a>
     	 	 	<input type="text" id="num_t" readonly value="1" style="width: 20px;width:1.93rem;height:1.93rem; font-size:1rem;float:right;margin-top:0.6rem;text-align: center;">
     	 	 	<input type="hidden" id="num" value="${num}"><input type="hidden" id="money" value="${project.price}">
     	 	 	<!--num:预约次数；money：每节课价格-->
     	 	 	<a href="javascript:;" class="confirmOrder_moddle_number_lessAnd" onClick="sub_1();"><span style="font-size:2rem;color:#aaaaaa;line-height: 1.8rem;">-</span></a>
       	 	 </div>
     	 </div>
     	 <div class="confirmOrder_moddle_times confirmOrder_moddle_footerStyle">
     	 	 <div class="confirmOrde_moddle_number_leftStyle"><span style="margin-left:0.833rem; font-size:1rem;color:#323232;line-height:3.33rem;">预约时间：</span></div>
     	 	 <div class="confirmOrde_moddle_number_rightStyle">
     	 	 	<a href="javascript:;" onclick="location.href='${base}/web/order/toConfirmTime.jhtml?projectId=${project.id}&num=' + $('#num').val();"><span style="font-size:1rem;color:#4c98f6;margin-right:0.833rem;float:right;">[#if startTime!=""]${startTime}-${endTime}[/#if][#if startTime==""]请选择[/#if]</span></a>
     	 	 </div>
     	 </div>
     	 <div class="confirmOrder_moddle_neekname confirmOrder_moddle_footerStyle">
     	 	 <div class="confirmOrde_moddle_number_leftStyle"><span style="margin-left:0.833rem; font-size:1rem;color:#323232;line-height:3.33rem;">康护患者：</span></div>
     	 	 <div class="confirmOrde_moddle_number_rightStyle">
     	 	 	 <select name="patientId" id="patientId" class="confirmOrder_moddle_neekname_right">
     	 	 	 [#list patientMemberList as patientMember]
     	 	    	<option value="${patientMember.id}">${patientMember.name}</option>
     	 	     [/#list]	
     	 	    </select>
     	 	 </div>
     	 </div>
     	 <div class="confirmOrder_moddle_Money confirmOrder_moddle_footerStyle">
     	 	 <div class="confirmOrde_moddle_number_rightStyle">
     	 	 	 <span style="font-size:0.85rem;color:#323232;margin-right:0.833rem;float:right;">费用小计：<span id="count_money_1">￥${price}</span></span>
     	 	 </div>
     	 </div>
     </div>
     
     <div class="confirm_order_footer">
		 <div class="confirmOrder_footer_totalMoney" style="text-align:right;line-height:3.58rem;"><span style="font-size:1rem;color:#323232;">实付款：</span><span style="font-size:1.25rem;color:#cc0000;margin-right:1rem;" id="count_money_2">¥${price}</span></div>
     	 <a href="javascript:void(0)" onclick="aClick();"><div class="confirmOrder_footer_totalSubmit" style="text-align:center;line-height:3.58rem;"><span style="font-size:1.25rem;color:#FFFFFF;">提交订单</span></div></a>
     </div>
 </form>
</body>
</html>
