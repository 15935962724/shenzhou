<!DOCTYPE HTML>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>好康护 - 让幸福简单起来</title>

<link href="${base}/resources/newmechanism/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/newmechanism/css/iconfont.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/newmechanism/css/main.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/newmechanism/css/animate.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/newmechanism/css/tipso.min.css" rel="stylesheet" >
<link href="${base}/resources/newmechanism/css/style.css" rel="stylesheet" >
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${base}/resources/newmechanism/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/slider/slider.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/tipso.min.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>


<script type="text/javascript">

window.onscroll = function(){
	if($(".left-side").offset().top>38)
		{
			$(".left-side").css({"top":"0px"});
		}
	else
		{
			$(".left-side").css({"top":"38px"});		
		}
}	

$().ready(function() {
	
	var $headerUsername = $("#username");
	//var username = getCookie("username");
	//var name = getCookie("name");
	var name = '${doctor.name}';
	
	//if (username != null) {
	//	$headerUsername.text(username).show();
	//} 
	
	if (username != null) {
		$headerUsername.text(name).show();
	} 
	
	var $inputForm = $("#inputForm");
	var $birthplaceId = $("#birthplaceId");//户籍地区
	var $areaId = $("#areaId");//现住地区
	var $editInfoFrom = $("#EditInfoFrom");//修改个人资料
	
	var $updateDoctor = $("#updateDoctor");//提交按钮
	
	//修改资料
	$updateDoctor.click(function(){
	var formData=new FormData($('#EditInfoFrom')[0]);
			   	$.ajax({
				//几个参数需要注意一下
                type: "POST",//方法类型
                dataType: "json",//预期服务器返回的数据类型
                url:  $editInfoFrom.attr("action"),//url
                data: formData,
                async: false,
			    cache: false,
			    contentType: false,
			    processData: false,
                success: function (map) {
                    console.log(map.data);//打印服务端返回的数据(调试用)
                    if (map.status == "200") {
                        $headerUsername.text(map.data.name).show();
                        $.message("warn","资料修改成功");
                        $('#number').click();
                    }
                    ;
                },
                error : function() {
                    alert("异常！");
                }
	  		});
	
	});
	
	
	//户籍地区选择
	$birthplaceId.lSelect({
		url: "${base}/mechanism/common/area.jhtml"
	});
	
	// 现住地区选择
	$areaId.lSelect({
		url: "${base}/mechanism/common/area.jhtml"
	});
	
	[@flash_message /]
	
	$.validator.addMethod("requiredTo", 
		function(value, element, param) {
			var parameterValue = $(param).val();
			if ($.trim(parameterValue) == "" || ($.trim(parameterValue) != "" && $.trim(value) != "")) {
				return true;
			} else {
				return false;
			}
		},
		"${message("admin.profile.requiredTo")}"
	);
	
	// 表单验证
	$inputForm.validate({
		rules: {
			currentPassword: {
				remote: {
					url: "${base}/mechanism/password/check_current_password.jhtml",
					cache: false
				}
			},
			password: {
				required: true,
				pattern: /^[^\s&\"<>]+$/,
				minlength: ${setting.passwordMinLength},
				maxlength: ${setting.passwordMaxLength}
			},
			rePassword: {
				equalTo: "#password"
			}
		},
		messages: {
			password: {
				pattern: "${message("admin.validate.illegal")}"
			}
		}
	});
	
	
		$('#number').tipso({
			useTitle: false,
			position:'left',
			background: "#279fff"
		});
	
});
</script>
<script>

	//上传预览
	function update_logo_img(upfile,upimg,pid,flag)
	{
		$("#" + upfile + "_" + pid).click();
		$("#" + upfile + "_" + pid).on("change",function(){
			var url=getObjectURL(this.files[0]);
			if (url) 
				{
					if(upimg!="")
					{
					if(flag=="1")
						{
							$("#" + upimg).attr("src", url) ; 
						}
					}
				}
		});
	}

</script>



</head>
<body>
  <table cellpadding="0" cellspacing="0" border="0" width="100%" style="position: absolute;" class="bgffffff">
   	<tr>
   		<td height="38">
   			<img src="${base}/resources/newmechanism/images/logo.png">
   		</td>
   		<td align="right" class="right-nav">
   			<ul>
   				<li class="pr"><div id = "number" data-tipso="${mechanism.name}" onClick="$('#EditInfo').toggle(500);"><i class="iconfont z20">&#xe60a;</i><strong id = "username"></strong>,欢迎您回来!</div> 
	   				<form id="EditInfoFrom" action="${base}/mechanism/doctor/update.jhtml" method="post" enctype="multipart/form-data">
	   				<table cellpadding="0" cellspacing="0" border="0" width="500" class="pa bgffffff k_1 ml200" id="EditInfo">
	   					<tr>
	   						<td colspan="3" class="bg279fff k_2" align="center">	
	   							修改资料
	   						</td>
	   					</tr>
	   					<tr>
	   						<td width="120" height="40" class="z143a3a3a" align="right">您的姓名：</td>
	   						<td><input type="text" id="name" name="name" class="input_1 w170" value="${doctor.name}"></td>
	   						<td width="150" rowspan="3">
	   							<img onerror = "htis.src='${base}/resources/mechanism/images/j_i_1.png'" id = "logo_img" src="${doctor.logo}" class="h105 w105" onclick="update_logo_img('logo','logo_img',0,'1');">
	   							<input id="logo_0" name="logo_img" type="file" style="display: none"  />
	   						</td>
	   					</tr>
	   					<tr>
	   						<td height="40" class="z143a3a3a" align="right">性　　别：</td>
	   						<td>
	   						[#list genders as gender]
	   							<input type="radio" name="gender" [#if gender == doctor.gender] checked [/#if] value="${gender}"> ${message("Member.Gender."+gender)}
								&nbsp;　&nbsp;
	   						[/#list]
	  						</td>
	   					</tr>
	   					<tr>
	   						<td height="40" class="z143a3a3a" align="right">民　　族：</td>
	   						<td>
								<input type="text" id="nation" name="nation" class="input_1 w170" value="${doctor.nation}">
	  						</td>
	   					</tr>
	   					<tr>
	   						<td height="40" align="right">出生日期：</td>
	   						<td colspan="2"><input type="text" id="birth" name="birth" class="input_1 w335" [#if doctor.birth??] value="${doctor.birth?string("yyyy-MM-dd")}" [/#if] onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '%y-%M-%d'});"></td>
	   					</tr>
	   					<tr>
	   						<td height="40" align="right">户籍地址：</td>
	   						<td colspan="2">
	   							<span class="fieldSet">
										<input type="hidden" id="birthplaceId" name="birthplaceId"  value="${(doctor.birthplace.id)!}" treePath="${(doctor.birthplace.treePath)!}"  />
								</span>
	   						</td>
	   					</tr>
	   					<tr>
	   						<td height="40" align="right">详细地址：</td>
	   						<td colspan="2"><input type="text" id="birthplaceAddress" class="input_1 w335" name="birthplaceAddress" value="${doctor.birthplaceAddress}"></td>
	   					</tr>
	   					<tr>
	   						<td height="40" align="right">现住地址：</td>
	   						<td colspan="2">
	   							<span class="fieldSet">
										<input type="hidden" id="areaId" name="areaId"  value="${(doctor.area.id)!}" treePath="${(doctor.area.treePath)!}"  />
								</span>
	  						</td>
	   					</tr>
	   					<tr>
	   						<td height="40" align="right">详细地址：</td>
	   						<td colspan="2"><input type="text" id="address" name="address" class="input_1 w335" value="${doctor.address}"></td>
	   					</tr>
	   					<tr>
	   						<td height="40" align="right">个人简介：</td>
	   						<td colspan="2">
	   							<textarea class="input_1 w335 h60" id="introduce" name="introduce">${doctor.introduce}</textarea>
	   						</td>
	   					</tr>
	   					<tr>
	   						<td height="50"></td>
	   						<td>
	   							<input type="button" id="updateDoctor" value="确认" class="button_1">
	   							<input type="button" value="取消" class="button_2 z14fefefe" onClick="$('#EditInfoFrom')[0].reset();$('#EditInfo').toggle(500);">
	   						</td>
	   						<td></td>
	   					</tr>
	   				</table>
					</form>
   				</li>
   				<li class="pr"><a href="javascript:;" target="main_ifr"><i class="iconfont">&#xe60e;</i><span class="pa iconfont">&#xe605;</span> 消息</a></li>
   				<li class="pr"><a href="javascript:;" onClick="$('#EditPass').toggle(500);"><i class="iconfont">&#xe638;</i> 修改密码</a>
   				<form id="inputForm" action="${base}/mechanism/password/update.jhtml" method="post">
   				<table cellpadding="0" cellspacing="0" border="0" width="380" class="pa bgffffff k_1" id="EditPass">
   					<tr>
   						<td colspan="2" class="bg279fff k_2" align="center">
   							修改密码
   						</td>
   					</tr>
   					<tr>
   						<td height="40" class="z143a3a3a" align="right">原始密码：</td>
   						<td><input type="password" class="input_1" name = "currentPassword"></td>
   					</tr>
   					<tr>
   						<td height="40" class="z143a3a3a" align="right">新密码：</td>
   						<td><input type="password" class="input_1" id="password" name="password"></td>
   					</tr>
   					<tr>
   						<td height="40" class="z143a3a3a" align="right">确认新密码：</td>
   						<td><input type="password" class="input_1" id="rePassword" name = "rePassword"></td>
   					</tr>
   					<tr>
   						<td height="50"></td>
   						<td>
   							<input type="submit" value="确认" class="button_1">
   							<input type="button"  value="取消" class="button_2 z14fefefe" onClick="$('#inputForm')[0].reset();$('#EditPass').toggle(500);">
   						</td>
   					</tr>
   				</table>
				</form>
   				</li>
   				<li><a href="${base}/mechanismLogin/logout.jhtml" target="main_ifr"><i class="iconfont">&#xe6e2;</i> 安全退出</a></li>
   				<li class="mr35"><a href="javascript:;" target="main_ifr"><i class="iconfont">&#xe60d;</i> 帮助反馈</a></li>
   			</ul>
   		</td>
   	</tr>
   </table>
   <div id="content" class="sticky-header" style="top: 38px;">
    <section>
    <!-- left side start-->
		<div class="left-side sticky-left-side">
			<span class="toggle-btn  menu-collapsed" style="float: right; display: block;margin-right: 17px;font-size: 35px; color: #fff; font-weight: bold;margin-top: -7px; cursor: pointer;"><i class="fa iconfont fa-bars"></i></span>
			<div class="left-side-inner" style="margin-top: 48px;">
				<ul class="nav nav-pills nav-stacked custom-nav">
				<li class="bt333743 active"><a href="index.jhtml" target="main_ifr"><i class="lnr iconfont lnr-main" style="font-size:17px;"></i> <span>首　　页</span></a></li>
					[#list ["authentication","mechanism","mechanismrolelist","serviceTime","projectlist","achievements"] as permission]
						[#if valid(permission)]
							<li class="bt333743"><a href="javascript:;" target="main_ifr"><i class="lnr iconfont lnr-mechanism" style="font-size:12px;"></i> <span>机构管理</span></a>
								<table cellpadding="0" cellspacing="0" border="0" class="k_1 mtb30" style="display:none;">
		                            <tr>
			                            [#if valid('authentication')] <td class="z14ffffff pa1624 b2292f7"><a href="../certificates/view.jhtml">企业认证</a></td> [/#if]
			                            [#if valid('mechanism')]<td class="z14717171 pa1624"><a href="../mechanism/view.jhtml">企业资料</a></td>[/#if]
			                            [#if valid('mechanismrolelist')]<td class="z14717171 pa1624"><a href="../mechanismrole/list.jhtml">角色设置</a></td>[/#if]
			                            [#if valid('serviceTime')]<td class="z14717171 pa1624"><a href="../mechanismServerTime/list.jhtml">服务时间</a></td>[/#if]
			                            [#if valid('serverProjectCotegorylist')]<td class="z14717171 pa1624"><a href="../serverProjectCategory/list.jhtml">服务项目</a></td>[/#if]
			                            [#if valid('achievements')]<td class="z14717171 pa1624"><a href="../mechanismSetup/achievements.jhtml">绩效管理</a></td>[/#if]
		                            </tr>
	                        	</table>
							</li>
							[#break /]
						[/#if]			
					[/#list]
					[#list ["doctorlist","doctorCategoryList"] as permission]
						[#if valid(permission)]
							<li class="bt333743"><a href="javascript:;" target="main_ifr"><i class="lnr iconfont lnr-staff"></i> <span>员工管理</span></a>
								<table cellpadding="0" cellspacing="0" border="0" class="k_1 mtb30" style="display:none;">
		                            <tr>
			                            [#if valid('doctorlist')] <td class="z14ffffff pa1624 b2292f7"><a href="../doctor/list.jhtml">员工管理</a></td> [/#if]
			                            [#if valid('doctorCategoryList')]<td class="z14717171 pa1624"><a href="../doctorCategory/list.jhtml">职级设置</a></td>[/#if]
		                            </tr>
	                        	</table>
							</li>
							[#break /]
						[/#if]			
					[/#list]
					[#list ["projectlist","workDaylist","orderlist"] as permission]
						[#if valid(permission)]
							<li class="bt333743"><a href="javascript:;" target="main_ifr"><i class="lnr iconfont lnr-appo"></i> <!--<span>预约管理</span>--><span>图书管理</span></a>
								<table cellpadding="0" cellspacing="0" border="0" class="k_1 mtb30" style="display:none;">
		                            <tr>
			                            [#if valid('projectlist')] <td class="z14ffffff pa1624 b2292f7"><a href="../project/list.jhtml">项目审核</a></td> [/#if]
			                            [#if valid('workDaylist')]<td class="z14717171 pa1624"><a href="../workDay/list.jhtml">排班管理</a></td>[/#if]
			                            [#if valid('orderlist')]<td class="z14717171 pa1624"><a href="../book/list.jhtml">图书管理</a></td>[/#if]
		                            </tr>
		                    	</table>
							</li>
							[#break /]
						[/#if]			
					[/#list]
					[#list ["memberlist","patientlist"] as permission]
						[#if valid(permission)]
							<li class="bt333743"><a href="javascript:;" target="main_ifr"><i class="lnr iconfont lnr-user"></i> <span>用户管理</span></a>
								<table cellpadding="0" cellspacing="0" border="0" class="k_1 mtb30" style="display:none;">
		                            <tr>
			                            [#if valid('memberlist')] <td class="z14ffffff pa1624 b2292f7"><a href="../member/member_list.jhtml">用户信息</a></td> [/#if]
			                            [#if valid('patientlist')]<td class="z14717171 pa1624"><a href="../member/patient_list.jhtml">患者信息</a></td>[/#if]
		                            </tr>
		                    	</table>
							</li>
							[#break /]
						[/#if]			
					[/#list]
					[#list ["couponlist"] as permission]
						[#if valid(permission)]
							<li class="bt333743"><a href="javascript:;" target="main_ifr"><i class="lnr iconfont lnr-operate"></i> <span>运营管理</span></a>
								<table cellpadding="0" cellspacing="0" border="0" class="k_1 mtb30" style="display:none;">
		                            <tr>
			                            [#if valid('couponlist')] <td class="z14ffffff pa1624 b2292f7"><a href="../coupon/list.jhtml">优惠券管理</a></td> [/#if]
		                            </tr>
		                    	</table>
							</li>
							[#break /]
						[/#if]			
					[/#list]
					[#list ["memberrecharge","refundslist","rechargeLoglist","depositlist","statisticscharge","statisticslist"] as permission]
						[#if valid(permission)]
							<li class="bt333743"><a href="javascript:;" target="main_ifr"><i class="lnr iconfont lnr-finance"></i> <span>财务管理</span></a>
								<table cellpadding="0" cellspacing="0" border="0" class="k_1 mtb30" style="display:none;">
		                            <tr>
			                            [#if valid('memberrecharge')] <td class="z14ffffff pa1624 b2292f7"><a href="../member/recharge.jhtml">账户充值</a></td> [/#if]
			                            [#if valid('refundslist')] <td class="z14717171 pa1624"><a href="../refunds/list.jhtml">退款管理</a></td> [/#if]
			                            [#if valid('rechargeLoglist')] <td class="z14717171 pa1624"><a href="../rechargeLog/list.jhtml">充值统计</a></td> [/#if]
			                           	[#if valid('depositlist')] <td class="z14717171 pa1624"><a href="../deposit/list.jhtml">余额变动</a></td> [/#if]
			                            [#if valid('statisticscharge')] <td class="z14717171 pa1624"><a href="../statistics/charge.jhtml">收费统计</a></td> [/#if]
			                            [#if valid('statisticslist')] <td class="z14717171 pa1624"><a href="../statistics/list.jhtml">收费月报</a></td> [/#if]
		                            </tr>
		                    	</table>
							</li>
						[#break /]
						[/#if]			
					[/#list]
					[#list ["ordercharge","projectcharge","memberpatienthealthType","evaluatelist","worklist"] as permission]
						[#if valid(permission)]
							<li class="bt333743 bb333743"><a href="javascript:;" target="main_ifr"><i class="lnr iconfont lnr-statistics" style="font-size:15px;"></i> <span>统计报表</span></a>
								<table cellpadding="0" cellspacing="0" border="0" class="k_1 mtb30" style="display:none;">
		                            <tr>
			                            [#if valid('ordercharge')] <td class="z14ffffff pa1624 b2292f7"><a href="../order/charge.jhtml">预约统计</a></td> [/#if]
			                            [#if valid('projectcharge')] <td class="z14717171 pa1624"><a href="../project/charge.jhtml">项目统计</a></td> [/#if]
			                            [#if valid('memberpatienthealthType')] <td class="z14717171 pa1624"><a href="../member/patient_healthType.jhtml">患者状态</a></td> [/#if]
			                            [#if valid('evaluatelist')] <td class="z14717171 pa1624"><a href="../evaluate/list.jhtml">评价统计</a></td> [/#if]
		                            	[#if valid('worklist')] <td class="z14717171 pa1624"><a href="../work/list.jhtml">工作统计</a></td> [/#if]
		                            </tr>
		                    	</table>
							</li>
						[#break /]
						[/#if]			
					[/#list]
					
					
				</ul>
			</div>
		</div>
		<div class="main-content main-content4" style="background: #f2f2f2;">
			<iframe src="index.jhtml" id="main_ifr" name="main_ifr" width="100%" frameborder="0" scrolling="no" marginheight="0" marginwidth="0"></iframe>
		</div>
	</section>
	 </div>

<script type="text/javascript" src="${base}/resources/newmechanism/js/jquery.nicescroll.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/scripts.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/bootstrap.min.js"></script>
</body>
</html>