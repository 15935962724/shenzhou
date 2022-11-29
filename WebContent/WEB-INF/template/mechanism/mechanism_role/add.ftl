<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加角色</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $name = $("#name");

	[@flash_message /]

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
<div class="m020">
	<table cellpadding="0" cellspacing="0" border="0" class="k_1 mtb30" id = "page_nav">
		<tr>
			
		</tr>
	</table>

	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="k_2">
		<tr>
			<td class="pa20">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">新增角色</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							<a href="list.jhtml">返回</a>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td colspan="2">
							<form id="inputForm" action="save.jhtml" method="post" enctype="multipart/form-data">
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z14444444">
								<tr>
									<td height="50" width="120" align="right">角色名称：<span class="z14ff0000">*</span></td>
									<td>
										<input type="text" class="z14717171 inputkd9d9d9bgf6f6f6 h30 w420 m1020" name="name"  placeholder="角色名称">
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">角色描述：<span class="z14ff0000">*</span></td>
									<td>
										<textarea class="z14323232 inputkd9d9d9bgf6f6f6 w420 h80 m1020" name="description" placeholder="角色描述"></textarea>
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right" valign="top">角色权限设置：<span class="z14ff0000">*</span></td>
									<td id="pb" valign="top">
										<table cellpadding="0" cellspacing="0" border="0" width="100%" class="ml20">
											<tr>
												<td height="30" valign="top"><b>机构管理</b></td>
											</tr>
											<tr>
												<td>
													<ul>
														<li><input type="checkbox" name="authorities" value="authentication">企业认证</li>
														<li><input type="checkbox" name="authorities" value="mechanism">企业资料</li>
														<li><input type="checkbox" name="authorities" value="mechanismrolelist">角色设置</li>
														<li><input type="checkbox" name="authorities" value="serviceTime">服务时间</li>
														<li><input type="checkbox" name="authorities" value="serverProjectCotegorylist">服务项目</li>
														<li><input type="checkbox" name="authorities" value="achievements">绩效管理</li>
													</ul>
												</td>
											</tr>
											<tr>
												<td height="30" valign="top"><b>员工管理</b></td>
											</tr>
											<tr>
												<td>
													<ul>
														<li><input type="checkbox" name="authorities" value="doctorlist">员工管理</li>
														<li><input type="checkbox" name="authorities" value="doctorCategoryList">职级管理</li>
													</ul>
												</td>
											</tr>
											<tr>
												<td height="30" valign="top"><b>预约管理</b></td>
											</tr>
											<tr>
												<td>
													<ul>
													
														<li><input type="checkbox" name="authorities" value="projectlist">项目审核</li>
														<li><input type="checkbox" name="authorities" value="workDaylist">排班管理</li>
														<li><input type="checkbox" name="authorities" value="orderlist">订单管理</li>
													</ul>
												</td>
											</tr>
											<tr>
												<td height="30" valign="top"><b>用户管理</b></td>
											</tr>
											<tr>
												<td>
													<ul>
														<li><input type="checkbox" name="authorities" value="memberlist">用户信息</li>
														<li><input type="checkbox" name="authorities" value="patientlist">患者信息</li>
													</ul>
												</td>
											</tr>
											<tr>
												<td height="30" valign="top"><b>运营管理</b></td>
											</tr>
											<tr>
												<td>
													<ul>
														<li><input type="checkbox" name="authorities" value="couponlist">优惠券管理</li>
													</ul>
												</td>
											</tr>
											<tr>
												<td height="30" valign="top"><b>财务管理</b></td>
											</tr>
											<tr>
												<td>
													<ul>
														<li><input type="checkbox" name="authorities" value="memberrecharge">账户充值</li>
														<li><input type="checkbox" name="authorities" value="refundslist">退款管理</li>
														<li><input type="checkbox" name="authorities" value="rechargeLoglist">充值统计</li>
														<li><input type="checkbox" name="authorities" value="depositlist">余额变动</li>
														<li><input type="checkbox" name="authorities" value="statisticscharge">收费统计</li>
														<li><input type="checkbox" name="authorities" value="statisticslist">收费月报</li>
													</ul>
												</td>
											</tr>
											<tr>
												<td height="30" valign="top"><b>统计报表</b></td>
											</tr>
											<tr>
												<td>
													<ul>
														<li><input type="checkbox" name="authorities" value="ordercharge">预约统计</li>
														<li><input type="checkbox" name="authorities" value="projectcharge">项目统计</li>
														<li><input type="checkbox" name="authorities" value="memberpatienthealthType">患者状态</li>
														<li><input type="checkbox" name="authorities" value="evaluatelist">评价统计</li>
														<li><input type="checkbox" name="authorities" value="worklist">工作统计</li>
													</ul>
												</td>
											</tr>
											<tr>
												<td height="30" valign="top"><b>其他管理</b></td>
											</tr>
											<tr>
												<td>
													<ul>
														<li><input type="checkbox" name="authorities" value="export">导出</li>
														<li><input type="checkbox" name="authorities" value="lookphonenum">查看电话号码</li>
													</ul>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								
								
								<tr>
									<td height="50" width="120" align="right"></td>
									<td>
										<input type="submit" value="保存" class="button_3 ml20">
										&nbsp;　&nbsp;
										<input type="button" value="重置" class="button_4 z14fefefe" onClick="$('#inputForm')[0].reset();">
									</td>
								</tr>
							</table>
							</form>
						</td>
					</tr>
					<tr>
						<td></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
</body>
</html>