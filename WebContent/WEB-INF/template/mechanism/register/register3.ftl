<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>基本信息</title>
<!--
<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/common.css" rel="stylesheet" type="text/css" />
-->
<link href="${base}/resources/newmechanism/css/register.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/newmechanism/css/iconfont.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>

<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>
<script type="text/javascript">
$().ready(function() {
	
	var wait=10; 

		function CountDown() {  
			if (wait == 0) 
				{  
					location.href="${base}/mechanismLogin/index.jhtml"
				} 
			else 
				{  
					$(".z20ff0000").html(wait + "秒");  
					wait--;  
					setTimeout(function() {  
						CountDown()  
					}, 1000)  
				}  
		}  
		CountDown();

	
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
			机构账户注册
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
								<td height="40" class="z144e96f4" align="center">用户名设置</td>
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
								<td height="40" class="z144e96f4" align="center">账号设置</td>
							</tr>
							<tr>
								<td align="center">
									<span class="z20ffffff spanround bg4e96f4">02</span>
								</td>
							</tr>
						</table>
					</td>
					<td width="33%" class="pr">
						<hr class="pa bb4e96f4 w100 h1 mt64 z1 wzt0l0">
						<table cellpadding="0" cellspacing="0" border="0" class="z2 pa wzt0l5040">
							<tr>
								<td height="40" class="z144e96f4" align="center">注册成功</td>
							</tr>
							<tr>
								<td align="center">
									<span class="z20ffffff spanround bg4e96f4">03</span>
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
						<table cellpadding="0" cellspacing="0" border="0" width="800" align="center" class="m0auto">
							<tr>
								<td class="pa55">
									<table cellpadding="0" cellspacing="0" border="0" width="100%">
										<tr>
											<td align="center" class="iconfont" style="font-size: 100px; color: #23b954;" height="120">&#xe60f;</td>
										</tr>
										<tr>
											<td class="z207a7a7a" align="center">
												<span class="z204e96f4">${doctorName}</span> 恭喜您已成功开通 好康护 平台机构管理服务<br/>
												<span class="z20ff0000">10秒</span>后自动跳转至登录页，未自动跳转请<a href="${base}/mechanismLogin/index.jhtml" class="z204e96f4">点这里</a>
											</td>
										</tr>
									</table>
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