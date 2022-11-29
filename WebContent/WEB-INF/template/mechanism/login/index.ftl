<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/newmechanism/css/iconfont.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jsbn.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/prng4.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/rng.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/rsa.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/base64.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/js.js"></script>
<script type="text/javascript">
if(self != top) 
	{
		top.location = self.location;
	}
$().ready(function() {

	var $loginForm = $("#loginForm");
	var $username = $("#username");
	var $password = $("#password");
	var $isReusername = $("#isReusername");
	var $submit = $(":submit");
	
	[@flash_message /]
	
	// 记住用户名
	if (getCookie("username") != null) {
		$isReusername.prop("checked", true);
		$username.val(getCookie("username"));
		$password.focus();
	} else {
		$isReusername.prop("checked", false);
		$username.focus();
	}
	
	
	
	// 表单验证、记住用户名
	$loginForm.validate({
		
		submitHandler: function(form) {
			$.ajax({
				url: "${base}/common/public_key.jhtml",
				type: "GET",
				dataType: "json",
				cache: false,
				beforeSend: function() {
					$submit.prop("disabled", true);
				},
				success: function(data) {
					var rsaKey = new RSAKey();
					rsaKey.setPublic(b64tohex(data.modulus), b64tohex(data.exponent));
					var enPassword = hex2b64(rsaKey.encrypt($password.val()));
					$.ajax({
						url: $loginForm.attr("action"),
						type: "POST",
						data: {
							username: $username.val(),
							enPassword: enPassword
						},
						dataType: "json",
						cache: false,
						success: function(data) {
							if ($isReusername.prop("checked")) {
								addCookie("username", $username.val(), {expires: 7 * 24 * 60 * 60});
							} else {
								removeCookie("username");
							}
							$submit.prop("disabled", false);
							if (data.status == "200") {
									location.href = "${base}/mechanismLogin/switch.jhtml";
							} else {
								$.message("warn",data.message);
								
							}
						}
					});
				}
			});
		}
	});

});

</script>

<style>
	body {overflow: hidden;position: fixed;width:100%;height:100%;background: url("${base}/resources/newmechanism/images/login_bg.png") no-repeat;} 
	.main{
    text-align: center;
    border-radius: 20px;
    width: 500px;
    height: 250px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%,-50%);
	}

	.t{background: rgba(255,255,255,0.4); }
	.inp{border: none;text-align: center; color: #fff;background: rgba(255,255,255,0);font-size: 16px; width:400px; height: 40px;}
	.inp::-webkit-input-placeholder, textarea::-webkit-input-placeholder {/* WebKit browsers */color:#dddddd;}
	.inp:-moz-placeholder, textarea:-moz-placeholder {/* Mozilla Firefox 4 to 18 */color:#dddddd;}
	.inp::-moz-placeholder, textarea::-moz-placeholder {/* Mozilla Firefox 19+ */color:#dddddd;}
	.inp:-ms-input-placeholder, textarea:-ms-input-placeholder {/* Internet Explorer 10+ */color:#dddddd;}
	.z20ffffff{font-size: 20px; color: #ffffff;}
	.z16e4e4e4{font-size: 16px;color: #e4e4e4;}
	.z16e4e4e4 a{color: #e4e4e4;text-decoration: none;}
	.n{background: #2eb5f8; width: 500px; height: 50px; font-size: 16px; text-align: center;border: none;color: #fff;}
	.z16ffffff1633bbff{color: #fff;font-size: 16px; text-decoration: none;}
	.z16ffffff1633bbff a{color: #33bbff;text-decoration: none;}
</style>

</head>

<body>
<form id="loginForm" action="${base}/mechanismLogin/submit.jhtml" method="post">
<table cellpadding="0" cellspacing="0" border="0" width="500" class="main">
	<tr>
		<td height="60" class="t iconfont z20ffffff" width="60" align="center">
			<b>&#xe623;</b>
		</td>
		<td class="t">
			<input type="text" id="username" name="username" value="" placeholder="用户名/手机号" maxlength="${setting.usernameMaxLength}" class="inp">
		</td>
	</tr>
	<tr>
		<td colspan="2" height="20"></td>
	</tr>
	<tr>
		<td height="60" class="t iconfont z20ffffff" width="60" align="center">
			<b>&#xe638;</b>
		</td>
		<td class="t">
			<input type="password" id="password" name="password" value="" placeholder="请输入密码" class="inp">
		</td>
	</tr>
	<tr>
		<td colspan="2" class="z16e4e4e4">
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td width="130" height="40" align="center">
						<input type="checkbox" id="isReusername" name="isReusername" value="true"> 记住账户
					</td>
					<td height="40" align="right">
						<a href="${base}/mechanismLogin/find1.jhtml">忘记密码？</a>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td colspan="2" height="60" valign="bottom">
			<input type="submit" value="登录" class="n">
		</td>
	</tr>
	<tr>
		<td colspan="2" align="right" class="z16ffffff1633bbff">
			没有账号？<a href="${base}/mechanismRegister/index.jhtml">马上注册</a>
		</td>
	</tr>
</table>
</form>
</body>
</html>
