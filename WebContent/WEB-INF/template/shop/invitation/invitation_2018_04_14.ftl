<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telemobile=no" name="format-detection">
<link href="${base}/resources/shop/css/invitation.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/shop/js/invitation.js"></script>
<title>积分领取</title>
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script src="js/jquery.js"></script>
<script>
	function get_f()
	{
		var mobile=$("#mobile").val();
		if(mobile==null || mobile=='')
			{
				alert('请填写需要领取积分的手机号');
				return false;
			}
		if(!(/^1[34578]\d{9}$/.test(mobile)))
			{ 
		        alert("您填写的手机号有误，请重新填写");  
		        return false;
			}
		$("#myform").submit();
	}
</script>

<script type="text/javascript"> 
	window.onload=function(){ 
	var fault="${fault}"; 
	if(fault!=null && fault!=''){
		alert(fault); 
		window.location.href='http://192.168.1.31:8080/shenzhou/shop/invitation/invitationDownLoad.jhtml';
	}
} 

</script> 

</head>

<body>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tbody>
    <tr>
      <td class="td_1">
      	<table cellpadding="0" cellspacing="0" border="0" width="20%" align="center" style="margin-top: 5rem;">
      		<tr>
      			<td align="center">${doctorInvitationMemberPoint}
      			</td>
      		</tr>
      	</table>	  
	  </td>
    </tr>
    <tr>
      <td class="td_2" valign="top">
      	<table cellpadding="0" cellspacing="0" border="0" width="80%" align="center">
      		<tr>
      			<td align="center">${doctorInvitationMemberPointExplain}
      			</td>
      		</tr>
      	</table>
      </td>
    </tr>
    <tr>
      <td class="td_3" valign="top">
      	<table cellpadding="0" cellspacing="0" border="0" width="80%" align="center">
      		<form name="myform" id="myform" method="get" action="beforehandLogin.jhtml">
      		<input type="hidden" id="phone" name="phone" value="${phone}">
      		<input type="hidden" id="userType" name="userType" value="${userType}">
      		<input type="hidden" id="usersType" name="usersType" value="${usersType}">
      		<tr>
      			<td>
      				<input type="text" id="mobile" name="mobile" placeholder="请输入手机号码" class="input_m_1 input_m_b_1 z_fff_1">
      			</td>
      		</tr>
      		<tr>
      			<td><input type="button" value="立即领取" class="input_m_1 input_m_b_2 z_4c98f6_1 y_1 p_1" onClick="get_f();" /></td>
      		</tr>
      		</form>
      	</table>
      </td>
    </tr>
  </tbody>
</table>
</body>
</html>
