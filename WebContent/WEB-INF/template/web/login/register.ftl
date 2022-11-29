<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta charset="UTF-8">
<title>注册</title>
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
	var key = phoneTrue();
	if(key==false){
		return false;
	}
	var phones = document.getElementById("phones").value; 
	$.ajax({
	url: "send_code.jhtml",
	type: "GET",
	data: {phone: phones},
	dataType: "json",
	cache: false,
	async: false,
	success: function(message) {
			if(message==true){
				alert('验证码发送成功');
			}else{
				alert('验证码发送失败,请稍候再试');
				return ;
			}
		}
	});
	
	verification(o);  
}  

//验证码倒计时
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
				verification(o)  
			}, 1000)  
		}  
}  

function phoneTrue() {  
	var phones = document.getElementById("phones").value; 
	var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
	
	if(phones==''||phones==null||phones==undefined){
		alert('手机号不能为空');
		return false;
	}
	
	if(!myreg.test(phones)){ 
    	alert('请输入有效的手机号码！'); 
   	 	return false; 
	} 
	
	var keys = true;
	
	 $.ajax({
		url: "check_username.jhtml",
		type: "GET",
		data: {username: phones},
		dataType: "json",
		cache: false,
		async: false,
		success: function(message) {
			if(message==false){
				alert('该账号已注册,请登录');
				keys = false;
			}
		}
	});
	
	return keys;
} 
</script> 
<script type="text/javascript">  
function confirm() {  
	var key = phoneTrue();
	if(key==false){
		return false;
	}
	
	var passwd = isPasswd();
	if(passwd == false){
		return false;
	}
	
	var phones = document.getElementById("phones").value; 
	var code = document.getElementById("authCode").value; 
	var keyword = document.getElementById("keyword").value; 
	
	if(code==''||code==null||code==undefined){
		alert('验证码不能为空');
		return false;
	}
	if(keyword==''||keyword==null||keyword==undefined){
		alert(keyword);
		return false;
	}
	if(phones==''||phones==null||phones==undefined){
		return false;
	}
	
	document.getElementById("inputForm").submit(); 

}  


function isPasswd() {  
	var passwords = document.getElementById("keyword").value; 
	var patrn=/^(\w){6,20}$/;  
	
	if (passwords == null || passwords == undefined || passwords == '') { 
		alert('密码不能为空'); 
		return false;
	} 
	
	if (!patrn.test(passwords)){
		alert('密码只能输入6-20个字母、数字、下划线'); 
   	 	return false; 
	}
} 
</script> 
</head>

<body style="background:#f0f1f0"> 
<form id="inputForm" action="register.jhtml" method="POST">
    <div id="header" class="pf h329">
     	<div id="back" align="left"><a><img alt="" src="${base}/resources/web/images/backBtnImg.png" style="width:0.59rem; height:auto" class="backImg" onclick="window.history.back()"></a></div>
     	<div id="title" style="align-content: center" align="center" class="titleLabel">用户注册</div>
    </div>
    
    <div id="moddle" class="moddlePositon" style="width: 100%; height: 11rem; background: #FFFFFF">
     	<div id="account" style="width: 100%;height: 3.66rem; border-bottom:solid 1px;color: 
     	#d6d7dc; border-top:solid 1px;color:#d6d7dc">
     		<div id="phone" class="phoneImg"><img alt="" src="${base}/resources/web/images/phone_login_img.png" style="width:0.875rem; height:auto" ></div>
			<div id="phoneInput" class="phoneInputs" style="height: 3.66rem;"><input type="text" name="phones" id="phones" size="15" placeholder="请输入手机号" class="phone" style="font-size:1rem;" onblur="phoneTrue()"></div>
     	</div>
     	<div id="password" style="width: 100%;height: 3.66rem;border-bottom:solid 1px;color: 
     	#d6d7dc">
     		<div class="passwordImg"><img alt="" src="${base}/resources/web/images/password_login_img.png" style="width:0.875rem; height:auto" ></div>
     		<div id="passwordInput" class="passwordInputs" style="height: 3.66rem;"><input type="password" name="keyword" id="keyword" placeholder="请输入密码" class="password" style="font-size:1rem;"></div>
     	</div>
     	
     	<div id="code" style="width: 100%;height: 3.66rem;border-bottom:solid 1px;color: 
     	#d6d7dc">
     		<div class="codeImg"><img alt="" src="${base}/resources/web/images/regeter_code_img.png" style="width:0.875rem; height:auto" ></div>
     		<div ><input type="text" name="authCode" id="authCode" placeholder="请输入验证码" class="codeinput" style="font-size:1rem;"><input type="button" id="code" name="code" style="font-size: 0.83rem; color:#4C98F6; margin-left: 1.382rem; border:0px;height:1.83rem; background:#fff;" value="获取验证码" onClick="CountDown(this);"></div>
		
     	</div>
     </div>
     
     <div id="footer" class="footers">
     	<div class="registerBtn"><input type ="button" style=" border-radius:10px; background:#4c98f6 ;height: 3.2rem;width: 25.666rem; font-size:1.25rem; margin: 0px auto; color: #ffffff" value="注册" onclick="confirm()"></div>
     	<div id="login" class="loginBtn"><a href="toLogin.jhtml" style="font-size:0.83rem; color:#4c98f6">已有账户，马上登录</a></div>
     </div>
</form>      
</body>
</html>
