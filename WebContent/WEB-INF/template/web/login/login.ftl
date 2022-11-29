<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<link href="${base}/resources/web/css/css.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>

<script type="text/javascript">
$().ready(function() {
	var errorMessage = '${errorMessage}';
	if(errorMessage==''||errorMessage==null||errorMessage==undefined){
		return false;
	}
	alert(errorMessage);
});
</script>

</head>
<body style="background:#f0f1f0">
<form id="inputForm" action="login.jhtml" method="POST">
     <div id="header" class="pf h329">
     	<div id="back" align="left"><a><img alt="" src="${base}/resources/web/images/backBtnImg.png" style="width:0.59rem; height:auto" class="backImg" onclick="window.history.back()"></a></div>
     	<div id="title" style="align-content: center" align="center" class="titleLabel">用户登录</div>
     </div>
     
     <div id="moddle" class="moddlePositon" style="width: 100%; height: 7.333rem; background: #FFFFFF">
     	<div id="account" style="width: 100%;height: 3.66rem; border-bottom:solid 1px;color: 
     	#d6d7dc; border-top:solid 1px;color:#d6d7dc">
     		<div id="phone" class="phoneImg"><img alt="" src="${base}/resources/web/images/phone_login_img.png" style="width:0.875rem; height:auto" ></div>
			<div id="phoneInput" class="phoneInputs" style="height: 3.66rem;"><input type="text" name="phones" size="15" id="phones" placeholder="请输入手机号" onblur="onblus()" class="phone" value="18345176555"></div>
     	</div>
     	<div id="password" style="width: 100%;height: 3.66rem;border-bottom:solid 1px;color: 
     	#d6d7dc">
     		<div class="passwordImg"><img alt="" src="${base}/resources/web/images/password_login_img.png" style="width:0.875rem; height:auto" ></div>
     		<div id="passwordInput" class="passwordInputs" style="height: 3.66rem;"><input type="password" name="passwords" id="passwords" placeholder="请输入密码" class="password" value="111111"></div>
     	</div>
     </div>
     
     <div id="footer" class="footers">
     	<div id="loginBtn" class="loginBtn"><input type ="button" style=" border-radius:10px; background:#4c98f6 ;height: 3.2rem;width: 25.66rem; font-size:1.25rem; margin: 0px auto; color: #ffffff" value="登录"  onclick="submitss()"></div>
     	<div id="register" class="register"><a href="toRegister.jhtml" style="font-size:0.83rem; color:#4c98f6">注册</a></div>
     	<div id="forgetPassword" class="forgetPassword"><a href="toForgetPassword.jhtml" style="font-size: 0.83rem; color:#909090">忘记密码</a></div>
     </div>
</form>     
</body>
<script language="javascript">

<!--提交登录-->
function submitss(){
	var login = onblus();
	var passwd = isPasswd();
	if(login == false ||passwd == false){
		return false;
	}
	
	document.getElementById("inputForm").submit();    
}


<!--验证密码(只能输入6-20个字母、数字、下划线 )-->
function isPasswd() {  
	var passwords = document.getElementById("passwords").value; 
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

//验证用户名是否可用
 function onblus(){
   	var phones = document.getElementById("phones").value; 
	var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
	if (phones == null || phones == undefined || phones == '') { 
		alert("手机号不能为空");
		return false;
	} 
	
	if(!myreg.test(phones)){ 
    	alert('请输入有效的手机号码！'); 
   	 	return false; 
	} 
  } 

</script>

</html>
