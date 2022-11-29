<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加管理员</title>
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
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $name = $("#name");

	[@flash_message /]

	// 表单验证
	$inputForm.validate({
		rules: {
			name: {
				required: true,
			},
			username: {
				required: true,
				pattern: /^[0-9a-z_A-Z]+$/,
				minlength: 2,
				maxlength: 20,
				remote: {
					url: "check_username.jhtml",
					cache: false
				}
			},
			password: {
				required: true,
				pattern: /^[^\s&\"<>]+$/,
				minlength: 4,
				maxlength: 20
			},
			rePassword: {
				required: true,
				equalTo: "#password"
			},
			email: {
				email: true
				[#if !setting.isDuplicateEmail]
					,remote: {
						url: "check_email.jhtml",
						cache: false
					}
				[/#if]
			},
			roleIds: "required"
		},
		messages: {
			username: {
				pattern: "${message("admin.validate.illegal")}",
				remote: "${message("admin.validate.exist")}"
			},
			password: {
				pattern: "${message("admin.validate.illegal")}"
			}
		}
	});
	
});

</script>
</head>
<body>
        <div class="seat">
        	<div class="left_z">添加管理员</div>
            <div class="export"><font class="z_14_ff0000_1">*</font> 为必填项</div>
        </div>
        <div class="detail">
        <form  id="inputForm" action="save.jhtml" method="post">
	        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="z_14_323232_1">
	          <tr>
	            <td colspan="2">&nbsp;</td>
	          </tr>
	          <tr>
	            <th width="150" height="40" align="right" valign="top" class="p_t_5_1">姓名<span class="z_14_ff0000_1">*</span></th>
	            <td height="40" valign="top" class="p_l_r_20_1"><input type="text" name="name" id="name" class="inp_1 w_450_1" /></td>
	          </tr>
	       	  <tr>
	            <th width="150" height="40" align="right" valign="top" class="p_t_5_1">部门</th>									  
	            <td height="40" valign="top" class="p_l_r_20_1"><input type="text" name="department" id="department" class="inp_1 w_450_1" /></td>
	          </tr>
	          <tr>
	            <th width="150" height="40" align="right" valign="top" class="p_t_5_1">用户名<span class="z_14_ff0000_1">*</span></th>
	            <td height="40" valign="top" class="p_l_r_20_1"><input type="text" name="username" id="username" class="inp_1 w_450_1" /></td>
	          </tr>
	          <tr>
	            <th width="150" height="40" align="right" valign="top" class="p_t_5_1">密码<span class="z_14_ff0000_1">*</span></th>
	            <td height="40" valign="top" class="p_l_r_20_1"><input type="password" name="password" id="password" class="inp_1 w_450_1" /></td>
	          </tr>
	          <tr>
	            <th width="150" height="40" align="right" valign="top" class="p_t_5_1">确认密码<span class="z_14_ff0000_1">*</span></th>
	            <td height="40" valign="top" class="p_l_r_20_1"><input type="password" name="rePassword" class="inp_1 w_450_1" /></td>
	          </tr>
	          <tr>
	            <th width="150" height="40" align="right" valign="top" class="p_t_5_1">E-mail</th>
	            <td height="40" valign="top" class="p_l_r_20_1"><input type="text" name="email" id="email" class="inp_1 w_450_1" /></td>
	          </tr>
	          
	          <tr>
	            <th height="40" align="right" valign="top">角色<span class="z_14_ff0000_1">*</span></th>
	            <td height="40" valign="top" class="p_l_r_20_1">
			            <span class="fieldSet">
						[#list mechanismRoles as role]
							<label>
								<input type="checkbox" name="roleIds" value="${role.id}" />${role.name}
							</label>
						[/#list]
						</span>
	            </td>
	          </tr>
	          <tr>
	            <th width="150" height="40" align="right" valign="top" class="p_t_5_1">设置</th>
	            <td height="40" valign="top" class="p_l_r_20_1"><input name="isEnabled" type="checkbox" value="true" checked="checked" />是否启用</td>
	          </tr>
	          <tr>
	            <td height="40" align="right" valign="top">&nbsp;</td>
	            <td height="40" valign="top" class="p_l_r_20_1">
	              <input type="submit" name="button7" id="button7" value=" 提 交 " class="button_1 b_4188d1_1" />　
	              <input type="reset" name="button7" id="button7" value=" 重 置 " class="button_1 b_ececec_1 z_12_707070_1" />
	            </td>
	          </tr>
	        </table>
        </form>
        </div>      
     <div class="clear"></div>

</body>
</html>