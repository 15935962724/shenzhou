<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加角色</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
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
						<td class="z20616161 bb1dd4d4d4" height="50">修改角色</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							<a href="javascript:;">返回</a>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td colspan="2">
							<form id="inputForm" action="update.jhtml" method="post" enctype="multipart/form-data">
							<input type="hidden" name="id" value="${mechanismRole.id}" />
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z14444444">
								<tr>
									<td height="50" width="120" align="right">角色名称：<span class="z14ff0000">*</span></td>
									<td>
										<input type="text" class="z14717171 inputkd9d9d9bgf6f6f6 h30 w420 m1020" name="name" value="${mechanismRole.name}" >
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">角色描述：<span class="z14ff0000">*</span></td>
									<td>
										<textarea class="z14323232 inputkd9d9d9bgf6f6f6 w420 h80 m1020" name="description" >${mechanismRole.description}</textarea>
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
														<li><input type="checkbox" name="authorities" value="authentication" [#if mechanismRole.authorities?seq_contains("authentication")] checked="checked"[/#if]>企业认证</li>
														<li><input type="checkbox" name="authorities" value="mechanism" [#if mechanismRole.authorities?seq_contains("mechanism")] checked="checked"[/#if]>企业资料</li>
														<li><input type="checkbox" name="authorities" value="mechanismrolelist" [#if mechanismRole.authorities?seq_contains("mechanismrolelist")] checked="checked"[/#if]>角色设置</li>
														<li><input type="checkbox" name="authorities" value="serviceTime" [#if mechanismRole.authorities?seq_contains("serviceTime")] checked="checked"[/#if]>服务时间</li>
														<li><input type="checkbox" name="authorities" value="serverProjectCotegorylist" [#if mechanismRole.authorities?seq_contains("serverProjectCotegorylist")] checked="checked"[/#if]>服务项目</li>
														<li><input type="checkbox" name="authorities" value="achievements" [#if mechanismRole.authorities?seq_contains("achievements")] checked="checked"[/#if]>绩效管理</li>
													</ul>
												</td>
											</tr>
											<tr>
												<td height="30" valign="top"><b>员工管理</b></td>
											</tr>
											<tr>
												<td>
													<ul>
														<li><input type="checkbox" name="authorities" value="doctorlist" [#if mechanismRole.authorities?seq_contains("doctorlist")] checked="checked"[/#if]>员工管理</li>
														<li><input type="checkbox" name="authorities" value="doctorCategoryList" [#if mechanismRole.authorities?seq_contains("doctorCategoryList")] checked="checked"[/#if]>职级管理</li>
													</ul>
												</td>
											</tr>
											<tr>
												<td height="30" valign="top"><b>预约管理</b></td>
											</tr>
											<tr>
												<td>
													<ul>
													
														<li><input type="checkbox" name="authorities" value="projectlist" [#if mechanismRole.authorities?seq_contains("projectlist")] checked="checked"[/#if]>项目审核</li>
														<li><input type="checkbox" name="authorities" value="workDaylist" [#if mechanismRole.authorities?seq_contains("workDaylist")] checked="checked"[/#if]>排班管理</li>
														<li><input type="checkbox" name="authorities" value="orderlist" [#if mechanismRole.authorities?seq_contains("orderlist")] checked="checked"[/#if]>订单管理</li>
													</ul>
												</td>
											</tr>
											<tr>
												<td height="30" valign="top"><b>用户管理</b></td>
											</tr>
											<tr>
												<td>
													<ul>
														<li><input type="checkbox" name="authorities" value="memberlist" [#if mechanismRole.authorities?seq_contains("memberlist")] checked="checked"[/#if]>用户信息</li>
														<li><input type="checkbox" name="authorities" value="patientlist" [#if mechanismRole.authorities?seq_contains("patientlist")] checked="checked"[/#if]>患者信息</li>
													</ul>
												</td>
											</tr>
											<tr>
												<td height="30" valign="top"><b>运营管理</b></td>
											</tr>
											<tr>
												<td>
													<ul>
														<li><input type="checkbox" name="authorities" value="couponlist" [#if mechanismRole.authorities?seq_contains("couponlist")] checked="checked"[/#if]>优惠券管理</li>
													</ul>
												</td>
											</tr>
											<tr>
												<td height="30" valign="top"><b>财务管理</b></td>
											</tr>
											<tr>
												<td>
													<ul>
														<li><input type="checkbox" name="authorities" value="memberrecharge" [#if mechanismRole.authorities?seq_contains("memberrecharge")] checked="checked"[/#if]>账户充值</li>
														<li><input type="checkbox" name="authorities" value="refundslist" [#if mechanismRole.authorities?seq_contains("refundslist")] checked="checked"[/#if]>退款管理</li>
														<li><input type="checkbox" name="authorities" value="rechargeLoglist" [#if mechanismRole.authorities?seq_contains("rechargeLoglist")] checked="checked"[/#if]>充值统计</li>
														<li><input type="checkbox" name="authorities" value="depositlist" [#if mechanismRole.authorities?seq_contains("depositlist")] checked="checked"[/#if]>余额变动</li>
														<li><input type="checkbox" name="authorities" value="statisticscharge" [#if mechanismRole.authorities?seq_contains("statisticscharge")] checked="checked"[/#if]>收费统计</li>
														<li><input type="checkbox" name="authorities" value="statisticslist" [#if mechanismRole.authorities?seq_contains("statisticslist")] checked="checked"[/#if]>收费月报</li>
													</ul>
												</td>
											</tr>
											<tr>
												<td height="30" valign="top"><b>统计报表</b></td>
											</tr>
											<tr>
												<td>
													<ul>
														<li><input type="checkbox" name="authorities" value="ordercharge" [#if mechanismRole.authorities?seq_contains("ordercharge")] checked="checked"[/#if]>预约统计</li>
														<li><input type="checkbox" name="authorities" value="projectcharge" [#if mechanismRole.authorities?seq_contains("projectcharge")] checked="checked"[/#if]>项目统计</li>
														<li><input type="checkbox" name="authorities" value="memberpatienthealthType" [#if mechanismRole.authorities?seq_contains("memberpatienthealthType")] checked="checked"[/#if]>患者状态</li>
														<li><input type="checkbox" name="authorities" value="evaluatelist" [#if mechanismRole.authorities?seq_contains("evaluatelist")] checked="checked"[/#if]>评价统计</li>
														<li><input type="checkbox" name="authorities" value="worklist" [#if mechanismRole.authorities?seq_contains("worklist")] checked="checked"[/#if]>评价统计</li>
													</ul>
												</td>
											</tr>
											
											<tr>
												<td height="30" valign="top"><b>其他管理</b></td>
											</tr>
											<tr>
												<td>
													<ul>
														<li><input type="checkbox" name="authorities" value="export" [#if mechanismRole.authorities?seq_contains("export")] checked="checked"[/#if]>导出</li>
														<li><input type="checkbox" name="authorities" value="lookphonenum" [#if mechanismRole.authorities?seq_contains("lookphonenum")] checked="checked"[/#if]>查看电话号码</li>
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