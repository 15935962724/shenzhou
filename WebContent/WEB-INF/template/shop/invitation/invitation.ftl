<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telemobile=no" name="format-detection">
<link href="${base}/resources/shop/css/invitation.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery-1.10.2.min.js"></script>
<title>积分领取</title>
<script>
	function get_f()
	{
		var mobile=$("#mobile").val();
		if(mobile==null || mobile=='')
			{
				alert('请填写需要领取积分的手机号');
				return false;
			}
		if(!(/^1[3456789]\d{9}$/.test(mobile)))
			{ 
		        alert("您填写的手机号有误，请重新填写");  
		        return false;
			}
		return true;
		//$("#myform").submit();
	}
</script>




<script type="text/javascript"> 
	window.onload=function(){ 
	
	

	
	
	//$("table").on("click",".img231289",function(){
	//	get_f();
	//})
	
	
	
	var fault="${fault}"; 
	if(fault!=null && fault!=''){
		alert(fault);
		window.location.href='${setting.siteUrl}/shop/invitation/invitationDownLoad.jhtml';
	}
} 

</script> 

</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<img src="${base}/resources/shop/images/invitation/receive_01.png"  class="fimg"></td>
	</tr>
	<tr>
		<td class="pr z1565656">
			<img src="${base}/resources/shop/images/invitation/receive_02.png" class="fimg">
			<div class="pa d1 t15">您的好友 <span class="z10824c98f6">${name}</span> 送您好康护大礼包！</div>
		</td>
	</tr>
	<tr>
		<td>
			<img src="${base}/resources/shop/images/invitation/receive_03.png"  class="fimg"></td>
	</tr>
	<tr>
		<td class="pr z1666666">
			<img src="${base}/resources/shop/images/invitation/receive_04.png"  class="fimg">
			<div class="pa d1 t2">好康护App汇聚了优秀的康复机构和康复医技人<br>
员，旨在为您提供高品质的康复服务</div>
		</td>
	</tr>
	<tr>
		<td>
			<img src="${base}/resources/shop/images/invitation/receive_05.png"  class="fimg"></td>
	</tr>
	<tr>
		<td class="pr">
			<img src="${base}/resources/shop/images/invitation/receive_06.png"  class="fimg">
			<form id="myform"  method="get" action="beforehandLogin.jhtml" onSubmit="return get_f();">
			<input type="hidden" id="phone" name="phone" value="${phone}">
      		<input type="hidden" id="userType" name="userType" value="${userType}">
      		<input type="hidden" id="usersType" name="usersType" value="${usersType}">
			<table cellpadding="0" cellspacing="0" width="100%" border="0" class="pa ft">
				<tr>
					<td align="center" class="tp05">
						<input type="text" placeholder="请输入您的手机号" id = "mobile" name="mobile" class="inp">
					</td>
				</tr>
				<tr>
					<td align="center" class="tp05">
						<!--img src="${base}/resources/shop/images/invitation/get.png" class="img231289"-->
						<input type="image" src="${base}/resources/shop/images/invitation/get.png" class="img231289">
					</td>
				</tr>
			</table>
			</form>
		</td>
	</tr>
	<tr>
		<td class="pr z1ffffff">
			<img src="${base}/resources/shop/images/invitation/receive_07.png" class="fimg">
			<div class="pa d1 t02">本活动最终解释权归“好康护”所有</div>
		</td>
	</tr>
</table>
</body>
</html>
