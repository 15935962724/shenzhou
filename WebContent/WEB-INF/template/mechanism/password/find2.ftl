<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/newmechanism/css/register.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/newmechanism/css/message.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	
	[@flash_message /]
	
	// 表单验证
	$inputForm.validate({
		rules: {
			password: {
				required: true,
				pattern: /^[^\s&\"<>]+$/,
				minlength: 6,
				maxlength: 6
			},
			rePassword: {
				required: true,
				equalTo: "#password"
			},
		}
	});
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
						<hr class="pa bb4e96f4 w100 h1 mt64 z1 wzt0l0">
						<table cellpadding="0" cellspacing="0" border="0" class="z2 pa wzt0l5040">
							<tr>
								<td height="40" class="z144e96f4" align="center">重置密码</td>
							</tr>
							<tr>
								<td align="center">
									<span class="z20ffffff spanround bg4e96f4">02</span>
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
								<form id = "inputForm" action = "find3.jhtml" method="post">
								<input type = "hidden" name="username" value="${username}"/>
									<table cellpadding="0" cellspacing="0" border="0" width="100%">
										<tr>
											<td class="bae5e5e5" colspan="3">
												<input type="password" placeholder="新密码" id="password" name="password" class="banone ma20 w400 z14666666">
											</td>
										</tr>
										<tr>
											<td height="20" colspan="3"></td>
										</tr>
										<tr>
											<td class="bae5e5e5" colspan="3">
												<input type="password" placeholder="确认新密码" id="rePassword" name="rePassword" class="banone ma20 w400 z14666666">
											</td>
										</tr>
										<tr>
											<td height="60" colspan="3">
												
											</td>
										</tr>
										<tr>
											<td colspan="3">
												<input id="next" type="submit" value="确 认" class="cp bg4e96f4 z14ffffff w690 h40 br5">
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
