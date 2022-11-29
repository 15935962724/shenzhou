<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加角色</title>
<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/js.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $name = $("#name");

	[@flash_message /]
	var mechanismImageIndex = 0;

	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
		},
		submitHandler: function(form) {
			form.submit();
		}
	});
	
});

</script>
</head>
<body>
        <div class="seat">
        	<div class="left_z">添加角色</div>
            <div class="export"><font class="z_14_ff0000_1">*</font> 为必填项</div>
        </div>
        <div class="detail">
        <form  id="inputForm" action="save.jhtml" method="post" enctype="multipart/form-data">
	        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="z_14_323232_1">
	          <tr>
	            <td colspan="2">&nbsp;</td>
	          </tr>
	          <tr>
	            <th width="150" height="40" align="right" valign="top" class="p_t_5_1">名称<span class="z_14_ff0000_1">*</span></th>
	            <td height="40" valign="top" class="p_l_r_20_1"><input type="text" name="name" id="textfield" class="inp_1 w_450_1" /></td>
	          </tr>
	       	  <tr>
	            <th width="150" height="40" align="right" valign="top" class="p_t_5_1">描述</th>
	            <td height="40" valign="top" class="p_l_r_20_1"><input type="text" name="description" id="textfield" class="inp_1 w_450_1" /></td>
	          </tr>
	          <tr>
	            <th height="10" align="right" valign="top">&nbsp;</th>
	            <td height="10" valign="top">&nbsp;</td>
	          </tr>
	          <tr>
	            <th height="40" align="right" valign="top">基本信息</th>
	            <td height="40" valign="top" class="p_l_r_20_1">
			            <input name="authorities" type="checkbox"  value="mechanismview" /> 企业信息
			            <input name="authorities" type="checkbox"  value="projectlist" /> 服务项目
			            <input name="authorities" type="checkbox"  value="serverProjectCotegorylist" /> 服务分类
			            <input name="authorities" type="checkbox"  value="evaluatelist" /> 评价信息
	            </td>
	          </tr>
	          <tr>
	            <th height="40" align="right" valign="top">医师管理</th>
	            <td height="40" valign="top" class="p_l_r_20_1">
			            <input name="authorities" type="checkbox" value="doctorlist" /> 医师技师
			            <input name="authorities" type="checkbox" value="workDaylist" /> 排班管理
	            </td>
	          </tr>
	          <tr>
	            <th height="40" align="right" valign="top">用户管理</th>
	            <td height="40" valign="top" class="p_l_r_20_1">
			            <input name="authorities" type="checkbox" value="memberlist" /> 用户信息
			            <input name="authorities" type="checkbox" value="patientlist" /> 患者信息
	            </td>
	          </tr>
	          <tr>
	            <th height="40" align="right" valign="top">财务管理</th>
	            <td height="40" valign="top" class="p_l_r_20_1">
			            <input name="authorities" type="checkbox"  value="orderlist" /> 订单管理
			            <input name="authorities" type="checkbox"  value="memberrecharge" /> 账户充值
			            <input name="authorities" type="checkbox"  value="refundslist" /> 退款管理
	            </td>
	          </tr>
	          <tr>
	            <th height="40" align="right" valign="top">统计分析</th>
	            <td height="40" valign="top" class="p_l_r_20_1">
			            <input name="authorities" type="checkbox"  value="statisticscharge" /> 收费统计
			            <input name="authorities" type="checkbox"  value="depositlist" /> 预存款统计
			            <input name="authorities" type="checkbox"  value="rechargeLoglist" /> 充值统计
			            <input name="authorities" type="checkbox"  value="statisticslist" /> 收费月报
			            <input name="authorities" type="checkbox"  value="ordercharge" /> 预约统计
			            <input name="authorities" type="checkbox"  value="projectcharge" /> 项目统计
			            <input name="authorities" type="checkbox"  value="memberpatienthealthType" /> 患者状态
	            </td>
	          </tr>
	         
	          <tr>
	            <th height="40" align="right" valign="top">权限管理</th>
	            <td height="40" valign="top" class="p_l_r_20_1">
			            <input name="authorities" type="checkbox"  value="userlist" /> 管理员
			            <input name="authorities" type="checkbox"  value="mechanismrolelist" /> 角色
	            </td>
	          </tr>
	          <tr>
	            <td height="40" align="right" valign="top">&nbsp;</td>
	            <td height="40" valign="top" class="p_l_r_20_1">
	              <input type="submit" name="button7" id="button7" value=" 提 交 " class="button_1 b_4188d1_1" />　<input type="reset" name="button7" id="button7" value=" 重 置 " class="button_1 b_ececec_1 z_12_707070_1" />
	            </td>
	          </tr>
	        </table>
        </form>
        </div>      
     <div class="clear"></div>

</body>
</html>