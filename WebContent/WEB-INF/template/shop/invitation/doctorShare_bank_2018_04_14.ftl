<!doctype html>
<html>
<head>
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta charset="UTF-8">
<title>分享赚积分</title>
<link href="${base}/resources/shop/css/css1.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${base}/resources/shop/css/light7.min.css">
<script type="text/javascript" src="${base}/resources/shop/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/light7.min.js" charset="utf-8"></script>
<style>
	@media only screen and (min-width: 414px) {
	  html {
	    font-size: 14.3476px !important;
	  }
	}

	.page{background: #509bf7;}	
	.bar-tab{height: 4.057rem;border-top: none;}
	.bar{background: #509bf7;}
</style>


<script type="text/javascript"> 
		 function share(key){  
		 
		 
		 if(key=='1'){
		 	 var str1=document.getElementById("paymentStatus").value;
             var str2=document.getElementById("paymentStatus").value;
		 }else{
			 var str1=document.getElementById("serveState").value;
             var str2=document.getElementById("serveState").value;
             
		 }
    		  alert(str1,str2);
              passValue(str1,str2);
              
} 

</script> 


</head>

<body>
<input type="hidden" id="paymentStatus" name="paymentStatus" value="微信好友" />
<input type="hidden" id="serveState" name="serveState" value="朋友圈" />
<!-- page 容器 -->
<div class="page">
	<!-- 这里是页面内容区 -->
	<div class="content">
<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td class="pr" align="center">
			<span class="pa spanz">活动说明</span>
			<img src="${base}/resources/shop/images/banner.png" class="imgb pa">
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="0" border="0" width="95%" align="center" class="mt165 z1ffffff m0auto wz mb5">
	<tr>
		<td>
				<!--1.<span>1积分＝1元</span>人民币，积分可累积无有效期限制。<br><br>

			2.积分可提现，只可下单时使用，或其他未开发功能使用（如商城中商品购买）<br><br>

			3.每邀请一位用户下载并注册“好康护”用户端双方各奖励<span>50积分</span>。<br><br>

			4.被邀请方通过“好康护”用户端首次完成订单流程可获得<span>50积分</span>。-->
			
			${poingExplain}
		</td>
	</tr>
	<tr>
		<td class="z06925ffffff p1" align="center">
			本活动最终解释权归“好康护”所有
		</td>
	</tr>
</table>
	</div>
<nav class="bar bar-tab fx">
	<table cellpadding="0" cellspacing="0" width="100%" border="0">
		<tr>
			<td align="center">
				<a href="javascript:;" onclick="share(2)">
					<img src="${base}/resources/shop/images/ic-fxpyq.png">
				</a>
			</td>
			<td align="center">
				<a href="javascript:;" onclick="share(1)">
					<img src="${base}/resources/shop/images/ic-fxwxhy.png">
				</a>
			</td>
		</tr>
	</table>
</nav>
</div>

</body>
</html>
