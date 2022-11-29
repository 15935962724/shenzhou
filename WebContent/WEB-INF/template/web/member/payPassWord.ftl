<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta charset="UTF-8">
<title>忘记支付密码</title>
<link href="${base}/resources/web/css/css.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${base}/resources/web/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/web/js/js.js"></script>

<script type="text/javascript">
$().ready(function() {
	var errorMessage = '${errorMessage}';
	if(errorMessage==''||errorMessage==null||errorMessage==undefined){
		return false;
	}
	alert(errorMessage);
});
</script>

<script type="text/javascript">  
var wait=60;  

function CountDown(o) {  
	
	$.ajax({
	url: "${base}/web/member/send_code.jhtml",
	type: "GET",
	dataType: "json",
	cache: false,
	async: false,
	success: function(message) {
			if(message==true){
				alert('验证码发送成功');
			}else{
				alert('验证码发送失败,请重新登陆再试');
				keys = false;
				return ;
			}
		}
	});
	
	verification(o);
}  


function verification(o) {  
	if (wait == 0) 
		{  
			o.removeAttribute("disabled");            
			o.value="获取验证码";  
			wait = 60;  
		} 
	else 
		{  
			o.setAttribute("disabled", true);  
			o.value="重新发送(" + wait + ")";  
			wait--;  
			setTimeout(function() {  
				CountDown(o)  
			}, 1000)  
		}  
}  


function confirm() {  

	var payPassword = document.getElementById("payPassword").value; 
	var payPasswordAgain = document.getElementById("payPasswordAgain").value; 
	
	if(payPassword==''||payPassword==null||payPassword==undefined){
		alert('密码不能为空');
		return false;
	}
	if(payPasswordAgain==''||payPasswordAgain==null||payPasswordAgain==undefined){
		alert(密码不能为空);
		return false;
	}

	if(payPassword!=payPasswordAgain){
		alert('两次密码输入不一致');
		return false;
	}
	
	var patrn=/^\d{6}$/;  
	if (!patrn.test(payPassword)){
		alert('密码只能输入6位数字'); 
   	 	return false; 
	}
	
	document.getElementById("inputForm").submit(); 

}  



</script> 
</head>

<body style="background:#f0f1f0">
<form id="inputForm" action="payPassWord.jhtml" method="POST">
     <div id="header" class="pf h329" style="border-bottom:#f0f1f0 1px solid;">
     	<div id="back" align="left"><a href="js/"><img alt="" src="images/backBtnImg.png" style="width:0.59rem; height:auto" class="backImg" onclick="window.history.back()"></a></div>
     	<div id="title" style="align-content: center" align="center" class="titleLabel">设置支付密码</div>
     </div>   
     
     <div class="real_moddle">
		
    	<div class="real_moddle_title" style="width:100%;height:2.5rem;">
			
    	</div>
    	
     	<div class="setting_pay_password" style="width:25rem;height:3.166rem;background:#FFFFFF; position: relative">
     		<div class="real_name_left real_class_left">验证码</div>
     		<div style="width:1px;height:2.506rem;background:#e4e5e8;margin-left:5.416rem;top:0.33rem;position: absolute;"></div>
     		<div class="real_name_right" style="width:12.666rem;height:3.166rem;margin-left:5.78rem;top: 0rem;position: absolute;"><input type="text" size="15" name="code" id="code" placeholder="请输入验证码" class="real_input_cord_style"></div>
     		<div style="width:1px;height:2.506rem;background:#e4e5e8;margin-left:18.789rem;top:0.33rem;position: absolute;"></div>
     		<div class="real_name_cord_btn"><input type="button" id="getVerificationCode" style="font-size: 0.83rem; color:#4C98F6;border:0px;height:1.166rem; background:#fff;" value="获取验证码" onClick="CountDown(this);"></div>
     	</div>
     	
     	<div class="real_moddle_card" style="width:25rem;height:3.166rem;background:#FFFFFF;position: relative">
     		<div class="real_card_left real_class_left">支付密码</div>
     		<div style="width:1px;height:2.506rem;background:#e4e5e8;margin-left:5.416rem;top:0.33rem;position: absolute;"></div>
     		<div class="real_card_right" style="width:18.166;height:3.166rem;margin-left:5.78rem;top: 0rem;position: absolute;"><input type="text" name="payPassword" id="payPassword" size="15" placeholder="请输入支付密码" class="real_input_style"></div>
     	</div>
     	
     	<div class="real_moddle_card" style="width:25rem;height:3.166rem;background:#FFFFFF;position: relative">
     		<div class="real_card_left real_class_left">重输密码</div>
     		<div style="width:1px;height:2.506rem;background:#e4e5e8;margin-left:5.416rem;top:0.33rem;position: absolute;"></div>
     		<div class="real_card_right" style="width:18.166;height:3.166rem;margin-left:5.78rem;top: 0rem;position: absolute;"><input type="text" name="payPasswordAgain" id="payPasswordAgain" size="15" placeholder="请再次输入支付密码" class="real_input_style"></div>
     	</div>
     	
     </div>
     
	 <div class="setting_pay_footer">
	      <div class="real_footer_btn"><input type ="button" style="border-radius:10px; background:#4c98f6;height: 3.2rem;width: 25.66rem; font-size:1.25rem; margin: 0px auto; color: #ffffff" value="提交" onclick="confirm()"></div>
	      <div class="fogert_pay_btn"><a href="js/" style="font-size:1rem;text-align:right;color:#909090;">忘记支付密码</a></div>
	 </div>
</form>	 
</body>
</html>
