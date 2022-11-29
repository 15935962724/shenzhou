<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta charset="UTF-8">
<title>实名认证</title>
<link href="${base}/resources/web/css/css.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${base}/resources/web/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/web/js/js.js"></script>
</head>

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
function confirm() {  
	
	var realName = document.getElementById("realName").value; 
	var idCard = document.getElementById("idCard").value; 
	var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/; 
	
	if(realName==''||realName==null||realName==undefined){
		alert('姓名不能为空');
		return false;
	}
	if(idCard==''||idCard==null||idCard==undefined){
		alert('身份证不能为空');
		return false;
	}
	
	if(reg.test(idCard) === false) { 
		alert('身份证输入不合法'); 
		return false; 
	}
	
	if(!document.getElementById("box").checked){
    	alert('请同意实名认证协议');
    	return false;
	}
	
	document.getElementById("inputForm").submit(); 

}  
</script>

<body style="background:#f0f1f0">
<form id="inputForm" action="authentication.jhtml" method="POST">
     <div id="header" class="pf h329" style="border-bottom:#f0f1f0 1px solid;">
     	<div id="back" align="left"><a><img alt="" src="${base}/resources/web/images/backBtnImg.png" style="width:0.59rem; height:auto" class="backImg" onclick="window.history.back()"></a></div>
     	<div id="title" style="align-content: center" align="center" class="titleLabel">实名认证</div>
     </div>
     
     <div class="real_moddle">
		<div class="real_moddle_title" style="width:100%;height:2.5rem;text-align:center;line-height: 2.5rem;">
			<span style="font-size:0.833rem;color:#4c98f6;">此项信息一经验证，将不能修改，请认真填写</span>
    	</div>
     	<div class="real_moddle_name" style="width:25rem;height:3.166rem;background:#FFFFFF; position: relative">
     		<div class="real_name_left real_class_left">姓名</div>
     		<div style="width:1px;height:2.506rem;background:#e4e5e8;margin-left:5.416rem;top:0.33rem;position: absolute;"></div>
     		<div class="real_name_right" style="width:18.166;height:3.166rem;margin-left:5.78rem;top: 0rem;position: absolute;"><input type="text" id="realName" name="realName" size="15" placeholder="请输入姓名" class="real_input_style"></div>
     	</div>
     	
     	<div class="real_moddle_card" style="width:25rem;height:3.166rem;background:#FFFFFF;position: relative">
     		<div class="real_card_left real_class_left">身份证</div>
     		<div style="width:1px;height:2.506rem;background:#e4e5e8;margin-left:5.416rem;top:0.33rem;position: absolute;"></div>
     		<div class="real_card_right" style="width:18.166;height:3.166rem;margin-left:5.78rem;top: 0rem;position: absolute;"><input type="text" name="idCard" id="idCard" size="15" placeholder="请正确输入身份证号" class="real_input_style"></div>
     	</div>
     	
     	<div class="real_moddle_agreement" style="width:25rem;height:3.166rem;font-size:0.833rem;color:#444444;line-height:3.166rem;"><input type="checkbox" id="box" class="real_agreement_checkbox">同意<a href="js/"><span style="color:#4C98F6;">实名认证协议</span></a></div>
     </div>
     
	 <div class="real_footer">
	      <div class="real_footer_btn"><input type ="button" style="border-radius:10px; background:#4c98f6;height: 3.2rem;width: 25.66rem; font-size:1.25rem; margin: 0px auto; color: #ffffff" value="提交" onclick="confirm()"></div>
	 </div>
</form>	 
</body>
</html>
