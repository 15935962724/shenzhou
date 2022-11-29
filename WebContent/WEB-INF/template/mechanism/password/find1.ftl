<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/newmechanism/css/register.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript">
var wait=60;  
function CountDown() {  
	var o = $("#sendCode");
		if (wait == 0) 
			{  
				o.attr("disabled", false);
				o.css("color","#4e96f3");
				o.val("获取验证码");  
				wait = 60;  
			} 
		else 
			{  
				o.attr("disabled", true);
				o.css("color","#8f8f8f");
				o.val("重新发送(" + wait + ")");  
				wait--;  
				setTimeout(function() {  
					CountDown();  
				}, 1000)  
			}  
	}  

		//发送验证码
	function sendCodes(){
	
		var username = $('#username').val();
	    if(username==""){
	    	$.message("warn","请输入手机号");
	        return;
	    }
		if(!/^1[34578]\d{9}$/.test(username)){
	    	$.message("warn","手机号输入有误");
	        return;
	    }
	
		 $.ajax({
					url: "${base}/mechanismRegister/sendCode.jhtml",
					type: "POST",
					data: {phone:username},
					dataType: "json",
					cache: false,
					async: false,
					success: function(data) {
						if (data.status == "200") {
							$.message("warn","验证码发送成功");
							CountDown();
						} else {
							$.message("warn","验证码发送失败");
						}
					}
				});
	  
	}

	//验证图片验证码
	function identifyingCaptcha(){
	
		var captchaId = '${captchaId}';
		var captcha = $("#captcha").val();
		var fals = false;
		 $.ajax({
					url: "identifyingCaptcha.jhtml",
					type: "POST",
					data: {captchaId:captchaId,captcha:captcha},
					dataType: "json",
					cache: false,
					async: false,
					success: function(data) {
					//alert(data);
						fals =  data;
					}
				});
		return fals;
	}

	//验证手机验证码
	function identifyingCode(){
	
		var code = $("#code").val();
		var fals = false;
		 $.ajax({
					url: "${base}/mechanismRegister/identifying.jhtml",
					type: "POST",
					data: {captcha:code},
					dataType: "json",
					cache: false,
					async: false,
					success: function(data) {
						fals = data;
					}
				});
		return fals;
	}

	//验证手机号是否在平台注册
	function identifyingUsername(){
	
		var username = $("#username").val();
		var fals = false;
		 $.ajax({
					url: "identifyingUsername.jhtml",
					type: "POST",
					data: {username:username},
					dataType: "json",
					cache: false,
					async: false,
					success: function(data) {
						fals = data;
					}
				});
		return fals;
	}

	//下一步
	function next(){
	
		var username = $("#username").val();
		var code = $("#code").val();
		var captcha = $("#captcha").val();
		if(username==""||code==""||captcha==""){
			$.message("warn","手机号或验证码必填");
			return false;
		}
	
		var identifyingUsernameFals = identifyingUsername();
		if(!identifyingUsernameFals){
			$.message("warn","该手机号未在本平台注册");
			return false;
		}
		
		var identifyingCaptchaFals = identifyingCaptcha();
		if(!identifyingCaptchaFals){
			$.message("warn","右侧验证码输入有误");
			return false;
		}
		
		var identifyingCodeFals = identifyingCode();
		if(!identifyingCodeFals){
			$.message("warn","手机验证码输入有误");
			return false;
		}
		$("#inputForm").submit();
	
	}

</script>

<script>
$().ready(function() {
	$("#randomImage").click(function(){
		$(this).attr("src", "captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());
	})
});
</script>
</head>
<body>
<table cellpadding="0" cellspacing="0" width="1200" border="0" align="center" class="m0auto">
	<tr>
		<td width="150" height="90" align="left">
			<img src="${base}/resources/mechanism/images/reglogo.png">
		</td>
		<td width="12" align="left">
			<span class="spanline"></span>
		</td>
		<td class="z16636363">
			重置密码
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="0" border="0" width="100%" class="bgf9f9f9">
	<tr>
		<td class="ptb50">
			<table cellpadding="0" cellspacing="0" border="0" width="1200" align="center" class="m0auto z14b9b9b9">
				<tr>
					<td width="33%" height="90" class="pr">
						<hr class="pa bb4e96f4 w100 h1 mt64 z1 wzt0l0">
						<table cellpadding="0" cellspacing="0" border="0" width="80" class="z2 pa wzt0l5040">
							<tr>
								<td height="40" class="z144e96f4" align="center">确认账号</td>
							</tr>
							<tr>
								<td align="center">
									<span class="z20ffffff spanround bg4e96f4">01</span>
								</td>
							</tr>
						</table>
					</td>
					<td width="33%" class="pr">
						<hr class="pa bbcccccc w100 h1 mt64 z1 wzt0l0">
						<table cellpadding="0" cellspacing="0" border="0" class="z2 pa wzt0l5040">
							<tr>
								<td height="40" align="center">重置密码</td>
							</tr>
							<tr>
								<td align="center">
									<span class="z20ffffff spanround bgdddddd">02</span>
								</td>
							</tr>
						</table>
					</td>
					<td width="33%" class="pr">
						<hr class="pa bbcccccc w100 h1 mt64 z1 wzt0l0">
						<table cellpadding="0" cellspacing="0" border="0" class="z2 pa wzt0l5040">
							<tr>
								<td height="40" align="center">重置成功</td>
							</tr>
							<tr>
								<td align="center">
									<span class="z20ffffff spanround bgdddddd">03</span>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td height="50" colspan="3"></td>
				</tr>
				<tr>
					<td colspan="3">
						<table cellpadding="0" cellspacing="0" border="0" width="800" align="center" class="m0auto k_1">
							<tr>
								<td class="pa55">
								<form id = "inputForm" action = "find2.jhtml" method="post">
									<table cellpadding="0" cellspacing="0" border="0" width="100%">
										<tr>
											<td class="bae5e5e5" colspan="3">
												<input type="text" placeholder="您的手机号" id = "username" name = "username"  class="banone ma20 w400 z14666666">
											</td>
										</tr>
										<tr>
											<td height="20" colspan="3"></td>
										</tr>
										<tr>
											<td class="btble5e5e5">
												<input type="text" placeholder="请输入右侧验证码" id="captcha" name="captcha" class="banone ma20 w400 z14666666">
											</td>
											<td class="btbe5e5e5" valign="middle">
												<span class="spanlinee5e5e5"></span>
											</td>
											<td align="center" width="110" class="btbre5e5e5">
												<img id="randomImage" style="cursor:pointer;" src="captcha.jhtml?captchaId=${captchaId}" width="">
											</td>
										</tr>
										<tr>
											<td height="20" colspan="3"></td>
										</tr>
										<tr>
											<td class="btble5e5e5">
												<input type="text" placeholder="您手机上的验证码" id="code" class="banone ma20 w400 z14666666">
											</td>
											<td class="btbe5e5e5" valign="middle">
												<span class="spanlinee5e5e5"></span>
											</td>
											<td align="center" width="110" class="btbre5e5e5">
												<input type="button" class="banone z144e96f4 bgffffff cp" value="获取验证码" id = "sendCode"  onclick = "sendCodes();">
											</td>
										</tr>
										<tr>
											<td height="60" colspan="3">
												
											</td>
										</tr>
										<tr>
											<td colspan="3">
												<input id="NextButton" type="button" onClick="next();" value="下一步" class="cp bg4e96f4 z14ffffff w690 h40 br5">
											</td>
										</tr>
									</table>
									</form>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td height="50" colspan="3"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="0" width="1200" border="0" align="center" class="m0auto">
	<tr>
		<td height="70" align="center" class="z14b4b4b4">
			©2017 好康护
		</td>
	</tr>
</table>

</body>
</html>
