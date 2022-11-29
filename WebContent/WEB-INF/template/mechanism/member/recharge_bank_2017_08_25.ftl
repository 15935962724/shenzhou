<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/js.js"></script>

<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	
	[@flash_message /]
	
		$.validator.addClassRules({
		modifyBalance: {
			required: true,
			extension: "${setting.priceScale}"
		}
		
	});
	
	// 表单验证
	$inputForm.validate({
		rules: {
			mobile: {
				required: true,
				pattern: /^1[3|4|5|7|8][0-9]{9}$/,
				
			},
			name: "required",
			modifyBalance: {
				required: true,
				min: 0,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			},
			messages: {
				mobile: {
					pattern: "电话号码有误"
				}
			},
		
		},
		
		submitHandler: function(form) {
			form.submit();
		}
	});
	
});
</script>


</head>
<body>
 <div class="nav">管理导航：<a href="javascript:;">管理首页</a></div>
<div class="seat">
  <div class="left_z">账户充值</div>
  <div class="export"><font class="z_14_ff0000_1">*</font> 为必填项</div>
</div>
<div class="detail">
	<form id="inputForm" method="post" action="balanceSave.jhtml">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="z_14_323232_1">
	      <tr>
	        <td colspan="2">&nbsp;</td>
	      </tr>
	      <tr>
	        <td width="150" height="40" align="right" valign="top" class="p_t_5_1">充值账号<span class="z_14_ff0000_1">*</span></td>
	        <td height="40" valign="top" class="p_l_r_20_1"><input type="text" placeholder="请输入对方手机号" name="mobile" id="mobile" class="inp_1 w_450_1" /></td>
	      </tr>
	      <tr>
	        <td width="150" height="40" align="right" valign="top" class="p_t_5_1">收款人姓名<span class="z_14_ff0000_1">*</span></td>
	        <td height="40" valign="top" class="p_l_r_20_1"><input type="text" placeholder="请输入对方姓名" name="name" id="name" class="inp_1 w_450_1" /></td>
	      </tr>
	      <tr>
	        <td width="150" height="40" align="right" valign="top" class="p_t_5_1">充值金额<span class="z_14_ff0000_1">*</span></td>
	        <td height="40" valign="top" class="p_l_r_20_1"><input type="text" placeholder="请输入充值金额" name="modifyBalance" id="modifyBalance" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();" class="inp_1 w_180_1" /> 元</td>
	      </tr>
	      <tr>
	        <td height="40" align="right" valign="top">备注<span class="z_14_ff0000_1">&nbsp;</span></td>
	        <td height="40" valign="top" class="p_l_r_20_1 p_b_10_1"><textarea name="memo" id="memo" rows="5" class="inp_2 h_80_1 w_450_1"></textarea></td>
	      </tr>
	      <tr>
	        <td height="10" align="right" valign="top">&nbsp;</td>
	        <td height="10" valign="top">&nbsp;</td>
	      </tr>
	      <tr>
	        <td height="40" align="right" valign="top">&nbsp;</td>
	        <td height="40" valign="top" class="p_l_r_20_1">
		        <input type="submit" name="button7" id="button7" value=" 充 值 " class="button_1 b_4188d1_1" />　
		        <input type="reset" name="button7" id="button7" value=" 重 置 " class="button_1 b_ececec_1 z_12_707070_1" />
	        </td>
	      </tr>
	  </table>
	</form>
</div>      
<div class="clear"></div>
</body>
</html>