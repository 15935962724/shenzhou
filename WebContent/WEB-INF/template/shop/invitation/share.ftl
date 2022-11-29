<!doctype html>
<html>
<head>
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta charset="UTF-8">
<title>分享赚积分</title>
<link href="${base}/resources/shop/css/share.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${base}/resources/shop/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/qrcode.js"></script>
<script>
$(function(){
		var w = document.body.clientWidth / 2.88548;
		var qrcode = new QRCode('qrcode', {
		text: '${setting.siteUrl}/shop/invitation/invitation.jhtml?usersType=member&message=android&phone=${phone}&userType=${userType}',
		width: w,
		height: w,
		colorDark : '#000000',
		colorLight : '#ffffff',
		correctLevel : QRCode.CorrectLevel.H
	}); 
	$("#scan").click(function(){
		$(".c").toggle();
	})
	//alert(document.body.clientWidth);
	
})
</script>

<script type="text/javascript"> 
		 function wxShare(key){  
			 if(key=='1'){
			 
			 	if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
	    				 var str1="微信好友";
		             	 var str2="微信好友";
		             	 alert(str1,str2);
		          		 passValue(str1,str2);
					}else{
						var result = window.android.shareToFriend();
					}
			 }else{
	             if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
					     var str1="朋友圈";
			             var str2="朋友圈";
			             alert(str1,str2);
			      		 passValue(str1,str2);
					}else{
						var result = window.android.shareToCircle();
					}
			 }	
		} 

</script> 
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<img src="${base}/resources/shop/images/share/01.png"  class="fimg"></td>
	</tr>
	<tr>
		<td class="pr">
			<img src="${base}/resources/shop/images/share/02.png" class="fimg">
			<a href = "${base}/rule.html"><span class="pa hdgz">活动规则</span></a>
		</td>
	</tr>
	<tr>
		<td>
			<img src="${base}/resources/shop/images/share/03.png" class="fimg"></td>
	</tr>
	<tr>
		<td>
			<img src="${base}/resources/shop/images/share/04.png" class="fimg"></td>
	</tr>
	<tr>
		<td>
			<img src="${base}/resources/shop/images/share/05.png"  class="fimg"></td>
	</tr>
	<tr>
		<td>
			<img src="${base}/resources/shop/images/share/06.png"  class="fimg"></td>
	</tr>
	<tr>
		<td>
			<img src="${base}/resources/shop/images/share/07.png"  class="fimg"></td>
	</tr>
	<tr>
		<td>
			<img src="${base}/resources/shop/images/share/08.png"  class="fimg"></td>
	</tr>
	<tr>
		<td class="pr">
			<img src="${base}/resources/shop/images/share/09.png" class="fimg">
			<img src="${base}/resources/shop/images/share/k.png" class="pa agreek" border="0">
			<img src="${base}/resources/shop/images/share/g.png" class="pa agreeg" border="0">
			<a href="${base}/agreement.html" class="pa zgreez">我已阅读并同意“邀好友 奖健康金”用户服务协议</a>
		</td>
	</tr>
	<tr>
		<td class="pr">
			<img src="${base}/resources/shop/images/share/10.png" class="fimg">
			<table cellpadding="0" cellspacing="0" border="0" width="90%" class="pa imgl14t06">
				<tr>
					<td width="33%" align="center">
						<a href="javascript:;" onClick="wxShare('1');">
							<img src="${base}/resources/shop/images/share/weixin.png" class="img3877">
						</a>
					</td>
					<td width="33%" align="center">
						<a href="javascript:;" onClick="wxShare('2');">
							<img src="${base}/resources/shop/images/share/friends.png" class="img3877">
						</a>
					</td>
					<td width="33%" align="center" class="pr">
						<img src="${base}/resources/shop/images/share/scan.png" class="img3877" id="scan">
						<table cellpadding="0" cellspacing="0" border="0" class="pa c">
							<tr>
								<td align="center">请朋友通过扫码领取</td>
							</tr>
							<tr>
								<td style="padding: 1rem;">
									<div id="qrcode">
									</div>
								</td>
							</tr>
						</table>
					</td>					
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<img src="${base}/resources/shop/images/share/11.png"  class="fimg">
		</td>
	</tr>
	<tr>
		<td class="pa h635707">
			<img src="${base}/resources/shop/images/share/12.png"  class="fimg">
			<span class="pr foodz">本活动最终解释权归“好康护”所有</span>
		</td>
	</tr>
</table>
</body>

</body>
</html>
