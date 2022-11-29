<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/mechanism/css/ext.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/common.css" rel="stylesheet" type="text/css" />
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
$().ready(function() {

	var $loginForm = $("#loginForm");
	var $username = $("#username");
	var $password = $("#password");
	var $captcha = $("#captcha");
	var $captchaImage = $("#captchaImage");
	var $isReusername = $("#isReusername");
	var $submit = $(":submit");
	
	// 记住用户名
	if (getCookie("username") != null) {
		$isReusername.prop("checked", true);
		$username.val(getCookie("username"));
		$password.focus();
	} else {
		$isReusername.prop("checked", false);
		$username.focus();
	}
	
	// 更换验证码
	$captchaImage.click(function() {
		$captchaImage.attr("src", "${base}/common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());
	});
	
	// 表单验证、记住用户名
	$loginForm.validate({
		rules: {
			username: "required",
			password: "required"
			[#if setting.captchaTypes?? && setting.captchaTypes?seq_contains("userLogin")]
				,captcha: "required"
			[/#if]
		},
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
							[#if setting.captchaTypes?? && setting.captchaTypes?seq_contains("userLogin")]
								,captchaId: "${captchaId}",
								captcha: $captcha.val()
							[/#if]
						},
						dataType: "json",
						cache: false,
						success: function(message) {
							if ($isReusername.prop("checked")) {
								addCookie("username", $username.val(), {expires: 7 * 24 * 60 * 60});
							} else {
								removeCookie("username");
							}
							$submit.prop("disabled", false);
							if (message.type == "success") {
							alert(redirectUrl);
								[#if redirectUrl??]
									location.href = "${redirectUrl}";
								[#else]
									//location.href = "${base}/mechanism/common/main.jhtml";
									location.href = "${base}/mechanismLogin/switch.jhtml";
								[/#if]
							} else {
								$.message(message);
								[#if setting.captchaTypes?? && setting.captchaTypes?seq_contains("userLogin")]
									$captcha.val("");
									$captchaImage.attr("src", "${base}/common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());
								[/#if]
							}
						}
					});
				}
			});
		}
	});

});

</script>

</head>

<body style="height:400px;">
<table width="730" border="0" align="center" cellpadding="0" cellspacing="0" class="table_1">
  <tr>
    <td height="280" width="430"><div>
    <form id="loginForm" action="${base}/mechanismLogin/submit.jhtml" method="post">
	    <table width="350" cellpadding="0" cellspacing="0" border="0" align="center" class="mar_t_1">
	    	<tr>
	        	<td colspan="2" align="right">还没账号？<a href="${base}/user/register.jhtml" class="f">马上注册</a></td>
	        </tr>
	    	<tr>
	        	<td colspan="2"><input type="text" id="username" name="username" value="" placeholder="请输入用户名" maxlength="${setting.usernameMaxLength}" /></td>
	        </tr>
	    	<tr>
	        	<td colspan="2">&nbsp;</td>
	        </tr>
	    	<tr>
	        	<td colspan="2"><input  type="password" id="password" name="password" value=""  /></td>
	        </tr>
	    	<tr>
	        	<td><input type="checkbox" id="isReusername" name="isReusername" value="true" />
	        	  记住账号</td>
	        	<td align="right"><a href="${base}/password/find.jhtml">忘记密码？</a></td>
	        </tr>
	    	<tr>
	        	<td colspan="2">&nbsp;</td>
	        </tr>
	    	<tr>
	        	<td colspan="2" align="center"><input type="submit" name="button" id="button" value="提交" /></td>
	        </tr>
	    </table>
    </form>
    </div></td>
    <td><img src="${base}/resources/mechanism/images/l_r_1.jpg" width="300" height="241" /></td>
  </tr>
</table>

</body>
</html>
