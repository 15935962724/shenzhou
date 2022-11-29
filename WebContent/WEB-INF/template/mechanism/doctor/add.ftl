<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("Common.Main.title")} - Powered By HaoKangHu</title>
<meta name="author" content="HaoKangHu Team" />
<meta name="copyright" content="HaoKangHu" />
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>

<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	
	[@flash_message /]
	
	
	// 表单验证
	$inputForm.validate({
		rules: {
			username: {
				required: true,
				//pattern: /^[0-9]+$/,
				pattern: /^1[34578]\d{9}$/,
				minlength: 11,
				maxlength: 11
			},
			password: {
				required: true,
				pattern: /^[^\s&\"<>]+$/,
				minlength: ${setting.passwordMinLength},
				maxlength: ${setting.passwordMaxLength}
			},
			name: {
				required: true
			},
			gender: {
				required: true
			},
			doctorCategoryId: {
				required: true
			},
			mechanismRoleId: {
				required: true
			},
		},
		messages: {
			username: {
				pattern: "${message("手机号码不正确")}",
				remote: "${message("admin.member.disabledExist")}"
			},
			password: {
				pattern: "${message("admin.validate.illegal")}"
			}
		},
		
		submitHandler: function(form) {
			$('#username').attr("disabled",false); 
			form.submit();
		}
		
	});

	$("#appo").click(function(){
		if($(this).is(':checked'))
			{
				$("#target").show();
			}
		else
			{
				$("#target").hide();
			}
	})

});
</script>

<script type="text/javascript">

function get_key(id)
	{
		var username = $("#" + id).val();
		if(username.length==11){
		    var pattern = /^1[34578]\d{9}$/;
		    if(!pattern.test(username)){
		       $.message("warn","手机号码输入有误!");
		       return ;
		    } 
		    
		     $.ajax( {     
                "type": "GET",  
                "url": 'username.jhtml',  
                "contentType": "application/x-www-form-urlencoded; charset=utf-8",   
                "traditional": true,  
                "async": false,
                "data": {  
                    username:username  
                },  
                success: function(data) {   
                	if(data.status==200){
                		return;
                	}
                	
                	if(data.status==300){
                		var doctor = data.data;
                		
                		$('#username').attr("disabled",true); 
                		$('#password').val(doctor.password);
                		$('#password').attr("disabled",true); 
                		$('#name').val(doctor.name);
                		$('#name').attr("disabled",true); 
                		$('#'+doctor.gender).prop("checked",true);
                		$('#'+doctor.gender).attr("disabled", false);
                		return ;
                	}
                	
                	if(data.status==400){
                	 	$.message("warn","手机号码不允许为空!");
		       		 	return ;
                	}
                	
                	if(data.status==500){
                	 	$.message("warn","'+data.message+'");
		       		 	return ;
                	}
                }     
       	 	}); 
		    
		}
	}

</script>

</head>
<body>
<div class="m020">
	<table cellpadding="0" cellspacing="0" border="0" class="k_1 mtb30" id="page_nav">
		<tr>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="k_2">
		<tr>
			<td class="pa20">
			
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">新增员工</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							<a href="javascript:;">返回</a>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td colspan="2" class="bgf5fafe pa20">
						<form id="inputForm" action="save.jhtml" method="post">
							<table cellpadding="0" cellspacing="0" border="0" width="100%">
								<tr>
									<td height="50" width="120" align="right">手机号码：<span class="z14ff0000">*</span></td>
									<td>
										<input type="number" name="username" id="username" runat="server" value="" class="z14323232 inputkd9d9d9bgf6f6f6 h30 w420 m1020 plr10" maxlength="11" minlength="11" onKeyUp="get_key(this.id);" >
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">登录密码：<span class="z14ff0000">*</span></td>
									<td>
										<input type="password" id = "password" name="password" value="" class="z14323232 inputkd9d9d9bgf6f6f6 h30 w420 m1020 plr10">
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">员工姓名：<span class="z14ff0000">*</span></td>
									<td>
										<input type="text" id = "name" name="name" value="" class="z14323232 inputkd9d9d9bgf6f6f6 h30 w420 m1020 plr10">
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">员工性别：<span class="z14ff0000">*</span></td>
									<td class="plr20 ptb10">
									[#list genders as gender ]
										<input type="radio" id="${gender}" name="gender" value="${gender}"> ${message("Member.Gender."+gender)}
										&nbsp;　&nbsp;
									[/#list]
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">员工职级：<span class="z14ff0000">*</span></td>
									<td class="plr20 ptb10">
										<select name = "doctorCategoryId" class="z14323232 h30 w100">
											<option value= "">请选择</option>
											[#list doctorCategorys as doctorCategory]
											<option value="${doctorCategory.id}">${doctorCategory.name}</option>
											[/#list]
										</select>
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">员工角色：<span class="z14ff0000">*</span></td>
									<td class="plr20 ptb10">
										<select name = "mechanismRoleId" class="z14323232 h30 w100">
											<option value= "">请选择</option>
											[#list mechanism.mechanismRoles as mechanismRole]
											<option value="${mechanismRole.id}">${mechanismRole.name}</option>
											[/#list]
										</select>
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">开通预约：<span class="z14ff0000">*</span></td>
									<td class="plr20 ptb10">
										<input type="checkbox" id="appo" name="isAbout"> 设置该员工患者端为可预约
									</td>
								</tr>
								<tr>
									<td></td>
									<td class="plr20">
										<table cellpadding="0" cellspacing="0" border="0" width="100%" class="dpn" id="target">
											<tr>
												<td height="50">
													  日工作目标：<input type="text" name="dayWorkTarget" class="z14717171 inputkd9d9d9bgf6f6f6 h20 w80" value="1000"> <span class="z12939393">元/日</span>　　提成占比：　<input type="text" name="percentage" class="z14717171 inputkd9d9d9bgf6f6f6 h20 w80" value="30"> <span class="z12939393">%</span>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td height="50"></td>
									<td>
										<input type="submit" value="提交" class="button_3 ml20">
										&nbsp;　&nbsp;
										<input type="button" value="取消" class="button_4 z14fefefe" onClick="$('#myform')[0].reset();">
									</td>
								</tr>
							</table>
							</form>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td colspan="2">
							
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
</body>
</html>