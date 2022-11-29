<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta charset="UTF-8">
<title>我的</title>
<link href="${base}/resources/web/css/css.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${base}/resources/web/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/web/js/js.js"></script>
</head>

<script type="text/javascript">
$().ready(function() {
	var errorMessage = '${errorMessage}';
	if(errorMessage==''||errorMessage==null||errorMessage==undefined){
		return false;
	}
	alert(errorMessage);
});
</script>

<body style="background:#f0f1f0">

     <div id="header" class="pf h329">
     	<div id="title" style="align-content:center" align="center" class="titleLabel">个人中心</div>
     </div>
     
     <div class="moddlePostion">
        <div style="background:url(images/myCenter_top_Img.png); width: 100%;height:10.666rem;  text-align: center;">
		  <a href="${base}/web/member/toEditMemberData.jhtml">
              <img src="${member.logo}" class="myCenter_myTopImgs" style="border-radius: 50%;margin-top:2.666rem; border: #FFFFFF 1px solid;">
          </a>
          <div style="font-size:1.08rem; text-align: center; color:#ffffff;margin-top:0.71rem;">${member.name}</div>
        </div>
        
     	<div class="moddleMoney" style="background: #ffffff">
     		<a href="${base}/web/member/toMyWallet.jhtml">
     		    <div class="helthMoney floatl borderRight_color">
     		
     			   <div class="moneyLabel moddleMoneyClass_style">${member.balance}</div>
     			   <div class="helthLabel fontSize_color">健康账户</div>
     			
			    </div>
    		</a>
    		<a href="js/">
     		    <div class="discount floatl borderRight_color">
     		       <div class="discountMoney moddleMoneyClass_style">0</div>
     			   <div class="discountLabel fontSize_color">优惠券</div>
     		    </div>
     	    </a>
     		
     		<a href="js/">
     		    <div class="integration floatl">
     		       <div class="integrationPoint moddleMoneyClass_style">${member.point}<span style="font-size:1rem; color:#444444">分</span></div>
     			   <div class="integrationLabel fontSize_color">康护积分</div>
     		    </div>
			</a>
     	</div>
     	
        
     	<div class="moddleClass" style="background: #ffffff">
     		<div class="moddleClassTop" style="border-bottom:1px solid #f0f1f0;">
     		<a href="toPatientList.jhtml">
     			<div class="management floatl borderRight_color" style="text-align: center;">
     				<div><img src="${base}/resources/web/images/myCenter_management_img.png" class="managementImg"></div>
     				<div class="managementLabel margin_Top fontSize_color">就诊人管理</div>
     			</div>
     		</a>
     			
			<a href="toMyDoctorList.jhtml">
				<div class="myDoctor floatl borderRight_color" style="text-align: center;">
					<div><img src="${base}/resources/web/images/myCenter_myDoctor_Img.png" class="myDoctorImg"></div>
					<div class="myDoctorLabel margin_Top fontSize_color">我的医生</div>
				</div>
			</a>
   		
    		<a href="toSetAccount.jhtml">
    			 <div class="settingSystem floatl" style="text-align: center;">
    		    	<div><img src="${base}/resources/web/images/myCenter_system_Img.png" class="setSystemImg"></div>
    		    	<div class="setSystemLabel margin_Top fontSize_color">账户设置</div>
    		    </div>
    		</a>
     		</div>
     		
     		<div class="moddleClassFooter" style="border-bottom:1px solid #f0f1f0;">
     	    <a href="js/">
     	    	<div class="feedbook floatl borderRight_color" style="text-align: center;">
     				<div><img src="${base}/resources/web/images/myCenter_feedbook_Img.png" class="feedbookImg"></div>
     				<div class="feedbookLabel margin_Top fontSize_color">意见反馈</div>
     			</div>
     	    </a>
			<a href="js/">
				<div class="helper floatl borderRight_color" style="text-align: center;">
					<div><img src="${base}/resources/web/images/myCenter_helper_Img.png" class="helperImg"></div>
					<div class="helperLabel margin_Top fontSize_color">帮助中心</div>
				</div>
			</a>
     		</div>
     		
     	</div>
     </div>
     
    <div id="food">
	<table cellpadding="0" cellspacing="0" border="0" width="100%" >
		<tr>
			<td width="20%" style="height:4.376rem;" valign="top"><a href="${base}/web/login/homePage.jhtml" ><img src="${base}/resources/web/images/home_h_1.png"><span>首页</span></a></td>
			<td width="20%" valign="top"><a href="javascript:;"><img src="${base}/resources/web/images/im_h_1.png"><span>咨询</span></a></td>
			<td width="20%" valign="top"><a href="javascript:;" class="buy"><img src="${base}/resources/web/images/buy_1.png"><span>预约<br>康复</span></a></td>
			<td width="20%" valign="top"><a href="javascript:;"><img src="${base}/resources/web/images/order_h_1.png"><span>订单</span></a></td>
			<td width="20%" valign="top"><a href="javascript:;" class="as"><img src="${base}/resources/web/images/my_l_1.png"><span>我的</span></a></td>
		</tr>
	</table>
    </div>
</body>
</html>
