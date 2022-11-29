<!doctype html>
<html>
<head>
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta charset="UTF-8">
<title>邀好友 领奖励</title>
<link href="${base}/resources/shop/css/download.css" rel="stylesheet" type="text/css" />
<script src="${base}/resources/shop/js/jquery-1.10.2.min.js"></script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<img src="${base}/resources/shop/images/invitation/download_01.png"  class="fimg">
		</td>
	</tr>
	<tr>
		<td class="pr z1565656">
			<img src="${base}/resources/shop/images/invitation/download_02.png"  class="fimg">
			<div class="pa dz236248" [#if !fals]style="top:1.1rem;"[/#if]>
			[#if !fals]
			您好友 <span class="z10824c98f6">${name}</span> 送您的康护大礼包已发放至您的账户，请点击下载按钮或扫描二维码下载应用。
			[#else]
				<!--以下为已注册用户或已被邀请用户文字内容-->
				您已是好康护的老朋友啦！已为您准备专属邀请链接，立刻登录好康护app分享您的专属邀请链接给朋友，每成功邀请1位好友注册使用，即可获得${point}健康金哦！
			[/#if]
			</div>
		</td>
	</tr>
	<tr>
		<td class="pr z1565656 dlh25">
			<img src="${base}/resources/shop/images/invitation/download_03.png"  class="fimg">
			<div class="pa" style="left: 0px;top: 20px;width: 100%;text-align: center; letter-spacing: 3px;">

			<img src="http://app2.haokanghu.cn/resources/shop/images/member.jpg" class="dcode"><br>
				好康护用户端
			</div>
		</td>
	</tr>
	<tr>
		<td class="pr">
			<img src="${base}/resources/shop/images/invitation/download_04.png" class="fimg">
			<a href="http://app2.haokanghu.cn/apk/patient.html">
				<img src="${base}/resources/shop/images/invitation/down.png" class="pa dimg">
			</a>
		</td>
	</tr>
	<tr>
		<td>
			<img src="${base}/resources/shop/images/invitation/download_05.png" class="fimg"></td>
	</tr>
</table>
</body>
</html>
