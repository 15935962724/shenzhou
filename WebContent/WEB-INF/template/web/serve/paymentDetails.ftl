<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta charset="UTF-8">
<title>付款详情</title>
<link href="${base}/resources/web/css/css.css" rel="stylesheet" type="text/css">
<script src="${base}/resources/web/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/web/tips/jquery.tips.js"></script>
<script>
    $(function() { 
    
		$("#postmoney").on("click",function(){
			$("#layer").css({"display":"block"});
			var txts = $("#PassFrame input");  
			for (var i = 0; i < txts.length; i++) {  
				var t = txts[i];  
				t.index = i;  
				t.value = "";
				t.setAttribute("readonly", true);  
				t.onkeyup = function() {  
					if(event.keyCode==8)
						{
							var prev = this.index - 1;
							if(prev < 0) return;
							txts[this.index].setAttribute("readonly", true);
							txts[prev].removeAttribute("readonly"); 
							txts[prev].value="";
							txts[prev].focus();  
						}
					else  
						{
							//this.value = this.value.replace(/^(.).*$/, '$1');  
							var next;  
							if(event.keyCode < 48 || event.keyCode > 57)
								{
									alert('支付密码只能为数字');
									txts[this.index].value="";
									txts[this.index].focus;
									return;
								}
							else
								{
									next = this.index + 1;
								}
							if (next > txts.length - 1) 
								{
									var sn = document.getElementById("sn").value; 
							    	var number1 = document.getElementById("number1").value; 
							    	var number2 = document.getElementById("number2").value; 
							    	var number3 = document.getElementById("number3").value; 
							    	var number4 = document.getElementById("number4").value; 
							    	var number5 = document.getElementById("number5").value; 
							    	var number6 = document.getElementById("number6").value; 
									var numbers = number1+number2+number3+number4+number5+number6;
									
									
									//这里ajax验证支付密码
									$.ajax({
										url: "/shenzhou/web/order/payment.jhtml",
										type: "GET",
										contentType:"application/x-www-form-urlencoded; charset=UTF-8",
										data: {sn:sn,paymentPassword:numbers,paymentMethodId:'4'},
										traditional: true,  
										async: false,
										success: function(data) {
										var dataObj = eval('('+data+')');
											if(dataObj.status=='400'){
												$.fn.tips({content:'+dataObj.message+'});
											}else{
												$.fn.tips({content:'支付成功'});	
												document.getElementById("inputForm").submit(); 									
											}
										}
										});
									
									
									return;
									
									
								}
							txts[this.index].setAttribute("readonly", true);
							txts[next].removeAttribute("readonly");  
							txts[next].focus();  
						}
				}  
			}  
			txts[0].removeAttribute("readonly");  
			txts[0].focus();
		})		
    }); 	
</script>
</head>
<form id="inputForm" action="${base}/web/order/toOrderList.jhtml" method="GET" enctype="multipart/form-data">
</form>
<body style="background:#f0f1f0">
<input type="hidden" name="sn" id="sn" value="${sn}">
     <div id="header" class="pf h329" style="border-bottom:#f0f1f0 1px solid;">
     	<div id="back" align="left"><a href="js/"><img alt="" src="${base}/resources/web/images/backBtnImg.png" style="width:0.59rem; height:auto" class="backImg"></a></div>
     	<div id="title" style="align-content: center" align="center" class="titleLabel">付款详情</div>
     </div>
     
     <div class="Pay_moddle">
     	<div class="pay_moddle_top">
     		<div class="pay_moddle_topID"><span class="pay_moddle_topStyle">订单号：${order.sn}</span></div>
     		<div style="margin-top:0.4rem;"><span class="pay_moddle_topStyle">项目名称：${order.project.name}</span></div>
     		<div ><span class="pay_moddle_topStyle">服务医生：${order.doctor.name}</span></div>
     		<div ><span class="pay_moddle_topStyle">康复患者：${order.patientMember.name}</span></div>
     		<div ><span class="pay_moddle_topStyle">实付金额：${amount}元</span></div>
     		<div ><span class="pay_moddle_topStyle">预约时间：${workDayDate}  ${startTime}</span></div>
     		<div ><span class="pay_moddle_topStyle">服务地址：${address}</span></div>
     	</div>
     	<div class="pay_moddle_footer">
     		<div class="pay_moddle_footer_payMode">
     			<div style="float:left;"><span style="font-size:1rem;color:#323232;margin-left:0.833rem;">账户余额：¥${balance}</span></div>
     			<a href="javascript:;"><div style="float: right;"><span style="font-size:1rem;color:#4c98f6;margin-right:0.833rem;">余额不足，请充值 ></span></div></a>
     		</div>
     		<div class="pay_moddle_footer_payMethed">
     			<div style="float:left;margin-top:1rem;"><span style="font-size:1rem;color:#323232;margin-left:0.833rem;">支付方式：</span></div>
     			<div style="float: right;margin-top:2rem;margin-right:5rem;">
     				<input type="radio" id="money" name="money" value="money" class="pay_information_moneyStyle" checked> 账户余额
     	 	        <input type="radio" id="money" name="money" value="other" class="pay_information_other pay_information_moneyStyle" disabled> 其他
     			</div>
     		</div>
     	</div>
     </div>
     
     <div class="Pay_footerBtn">
     	<div class="pay_OKBtn"><input type ="button" id="postmoney" style="background:#4c98f6 ;height: 3.2rem;width:100%; font-size:1.25rem; margin: 0px auto; color: #ffffff" value="确认付款"></div>
     </div>
<div id="layer" style="display: none; background:rgba(0,0,0,0.5);">
	<div class="layer" style="overflow-y:hidden; width: 14rem; margin: 3rem auto; height: 12rem;border-radius: 0.5rem;">
		<form id="myFilter">
		<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z_106_323232_1">
			<tr>
				<td id="closemoney" class="z_106_909090_1 h208" valign="top" width="30" align="center" onClick="$('#layer').css({'display':'none'});">X</td>
				<td valign="top" align="center">请输入支付密码</td>
				<td class="z_106_909090_1 h208" valign="middle" width="30" align="center">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="3" style="border-top: 1px solid #bcbcbc;" class="z_256_323232_1 lh170" align="center" valign="middle">
					<span class="z_106_909090_1">￥</span>${amount}
				</td>
			</tr>
			<tr>
				<td colspan="3" style="padding-left: 0.7rem;" id="PassFrame">
					<input type="number" id="number1" name="number1" style="width: 2rem;height: 2rem;border: 1px solid #bcbcbc;border-right: 0px; text-align: center;float: left;-webkit-text-security:disc;text-security:disc;" maxlength="1">
					<input type="number" id="number2" name="number2" style="width: 2rem;height: 2rem;border: 1px solid #bcbcbc;border-right: 0px; text-align: center;float: left;-webkit-text-security:disc;text-security:disc;" maxlength="1">
					<input type="number" id="number3" name="number3" style="width: 2rem;height: 2rem;border: 1px solid #bcbcbc;border-right: 0px; text-align: center;float: left;-webkit-text-security:disc;text-security:disc;" maxlength="1">
					<input type="number" id="number4" name="number4" style="width: 2rem;height: 2rem;border: 1px solid #bcbcbc;border-right: 0px; text-align: center;float: left;-webkit-text-security:disc;text-security:disc;" maxlength="1">
					<input type="number" id="number5" name="number5" style="width: 2rem;height: 2rem;border: 1px solid #bcbcbc;border-right: 0px; text-align: center;float: left;-webkit-text-security:disc;text-security:disc;" maxlength="1">
					<input type="number" id="number6" name="number6" style="width: 2rem;height: 2rem;border: 1px solid #bcbcbc;text-align: center;float: left; " maxlength="1">
				</td>
			</tr>
			<tr>
				<td colspan="3" class="z_085_909090_1 paa05" align="right" style="padding-right: 1rem;"><a href="javascript:;">忘记密码</a></td>
			</tr>
		</table>
		</form>
	</div>
</div>

</body>
</html>
