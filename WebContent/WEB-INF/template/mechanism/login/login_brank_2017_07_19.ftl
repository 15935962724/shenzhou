<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("shop.login.title")}[#if systemShowPowered] - Powered By HaoKangHu[/#if]</title>
<meta name="author" content="HaoKangHu Team" />
<meta name="copyright" content="HaoKangHu" />
<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jsbn.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/prng4.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/rng.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/rsa.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/base64.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
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
								[#if redirectUrl??]
									location.href = "${redirectUrl}";
								[#else]
									location.href = "${base}/mechanism/common/main.jhtml";
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
<body>
	<div style = "width: 950px; margin: 0px auto; height:100px;"></div>
	<div class="container login">
		<div class="span12">
			[@ad_position id = 9 /]
		</div>
		<div class="span12 last">
			<div class="wrap">
				<div class="main">
					<div class="title">
						<strong>${message("shop.login.title")}</strong>USER LOGIN
					</div>
					<form id="loginForm" action="${base}/user/submit.jhtml" method="post">
						<table>
							<tr>
								<th>
									[#if setting.isEmailLogin]
										${message("shop.login.usernameOrEmail")}:
									[#else]
										${message("shop.login.username")}:
									[/#if]
								</th>
								<td>
									<input type="text" id="username" name="username" class="text" maxlength="${setting.usernameMaxLength}" />
								</td>
							</tr>
							<tr>
								<th>
									${message("shop.login.password")}:
								</th>
								<td>
									<input type="password" id="password" name="password" class="text" maxlength="${setting.passwordMaxLength}" autocomplete="off" />
								</td>
							</tr>
							[#if setting.captchaTypes?? && setting.captchaTypes?seq_contains("userLogin")]
								<tr>
									<th>
										${message("shop.captcha.name")}:
									</th>
									<td>
										<span class="fieldSet">
											<input type="text" id="captcha" name="captcha" class="text captcha" maxlength="4" autocomplete="off" /><img id="captchaImage" class="captchaImage" src="${base}/common/captcha.jhtml?captchaId=${captchaId}" title="${message("shop.captcha.imageTitle")}" />
										</span>
									</td>
								</tr>
							[/#if]
							<tr>
								<th>
									&nbsp;
								</th>
								<td>
									<label>
										<input type="checkbox" id="isReusername" name="isReusername" value="true" />${message("shop.login.isRememberUsername")}
									</label>
									<label>
										&nbsp;&nbsp;<a href="${base}/password/find.jhtml">${message("shop.login.findPassword")}</a>
									</label>
								</td>
							</tr>
							<tr>
								<th>
									&nbsp;
								</th>
								<td>
									<input type="submit" class="submit" value="${message("shop.login.submit")}" />
								</td>
							</tr>
							<tr class="register">
								<th>
									&nbsp;
								</th>
								<td>
									<dl>
										<dt>${message("shop.login.noAccount")}</dt>
										<dd>
											<a href="${base}/user/register.jhtml">${message("shop.login.register")}</a>
										</dd>
									</dl>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
	[#include "/mechanism/include/footer.ftl" /]
</body>
</html>