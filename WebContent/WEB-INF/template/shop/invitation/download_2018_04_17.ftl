
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<link href="${base}/resources/shop/css/invitation.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/shop/js/invitation.js"></script>
<title>App下载</title>
<link href="${base}/resources/shop/css/css.css" rel="stylesheet" type="text/css" />
<style>
	body{
		backgroud:#8abeff;
	}	
</style>
<script type="text/javascript"> 
	window.onload=function(){ 
	var fault="${fault}"; 
	if(fault!=null && fault!=''){
		alert(fault);
	}
} 

</script> 
</head>

<body>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tbody>
    <tr>
      <td class="d_1" valign="top">
      	<table cellpadding="0" cellspacing="0" border="0" width="58%" align="center" style="margin-top: 8.5rem;">
      		<tr>
      			<td align="center">[#if userType == "member"]${setting.memberInvitationMemberPoint}[#else]${setting.doctorInvitationMemberPoint}[/#if]积分已发放至您的账户</td>
      		</tr>
      	</table>	  	  
	  </td>
    </tr>
    <tr>
      <td valign="top" align="center" class="d_2">
      	[#if usersType == "doctor"]
  			<img src="${base}/resources/shop/images/doctor.jpg" style=""><br>客户端下载	
		[#else] 
  			<img src="${base}/resources/shop/images/member.jpg" style=""><br>客户端下载	 
		[/#if] 	  
	  </td>
    </tr>
    <tr>
    
    [#if usersType == "doctor"] 
  		<td valign="top" align="center" class="d_3"><input type="button" value="下载手机App" class="input_m_1 input_m_b_2 z_4c98f6_1 y_1 p_1" style="width: 90%;" onClick="location.href='http://www.haokanghu.cn/apk/doctor.html';" /></td> 
	[#else] 
 		<td valign="top" align="center" class="d_3"><input type="button" value="下载手机App" class="input_m_1 input_m_b_2 z_4c98f6_1 y_1 p_1" style="width: 90%;" onClick="location.href='http://www.haokanghu.cn/apk/patient.html';" /></td>
	[/#if] 
    </tr>
  </tbody>
</table>
</body>
</html>
