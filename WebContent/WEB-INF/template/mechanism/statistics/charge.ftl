
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>基本信息</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $listForm = $("#listForm");
	var $download = $("#download");
	var $accountType = $("#accountType");
	[@flash_message /]
	
	//导出
	$download.click(function() {
	$listForm.attr('action','downloadCharge.jhtml');
	$listForm.submit();
	$listForm.attr('action','charge.jhtml');		
	});
	
	
	$accountType.bind("change",function(){
            $listForm.submit();
   });
});
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
			<form id="listForm" action="charge.jhtml" method="get">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">收费统计</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							[#if valid('export')]<a href="javascript:;"  id = "download" >导出</a>[/#if]
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td class="z14444444" colspan="2">
							<table cellpadding="0" cellspacing="0" border="0" width="100%">
								<tr>
									<td width="25%">服务项目：<input type="text" name="projectName" id="projectName" class="k_3 h30 bae1e1e1 w100 tac" [#if projectName??] value="${projectName}" [/#if] placeholder="请输入项目名称"></td>
									<td width="25%" height="40">服务医师：<input type="text" placeholder="请输入医师姓名" name="doctorName" id="doctorName" [#if doctorName??] value="${doctorName}" [/#if] class="k_3 h30 bae1e1e1 w100 tac"></td>
									<td width="50%" colspan="2" height="40">服务周期：
									<input type="text" id="startDate" name="startDate" placeholder="开始时间" [#if startDate??] value="${startDate?string("yyyy-MM-dd")}" [/#if] onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '#F{$dp.$D(\'endDate\')}'});" class="k_3 h30 bae1e1e1 w100 tac">
									 - 
									<input type="text" id="endDate" name="endDate" placeholder="结束时间" [#if endDate??] value="${endDate?string("yyyy-MM-dd")}" [/#if] onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', minDate: '#F{$dp.$D(\'startDate\')}'});" class="k_3 h30 bae1e1e1 w100 tac">
									</td>
									<td>
										<select id = "accountType" name="accountType" class="h30 w100 bae1e1e1">
											<option value="">全部账户</option>
											
											<option [#if "platform"==accountType ]selected="selected"[/#if] value="platform">平台账户</option>
											<option [#if "mechanism"==accountType ]selected="selected"[/#if] value="mechanism">机构账户</option>
										</select>
									</td>
								</tr>
								<tr>
									<td width="25%">下单用户：<input type="text" name="memberName" id="memberName" placeholder="请输入用户姓名" [#if memberName??] value="${memberName}" [/#if] class="k_3 h30 bae1e1e1 w100 tac"></td>
									<td width="25%">患者姓名：<input type="text" name="patientName" id="patientName" class="k_3 h30 bae1e1e1 w100 tac" placeholder="请输入患者姓名" [#if patientName??] value="${patientName}" [/#if]></td>
									<td width="25%">用户电话：<input type="text" name="phone" id="phone" class="k_3 h30 bae1e1e1 w215 tac" [#if phone??] value="${phone}" [/#if] placeholder="请输入联系人电话"></td>
									<td width="25%" height="40" align="right"><input type="submit" value="查询" class="bae1e1e1 z16ffffff bg279fff h30 w65"></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="20"></td>
					</tr>
					<tr>
						<td colspan="2">
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="">
								<tr class="bg279fff z14ffffff">
									<td class="btle3e3e3" align="center" width="40" height="50">
										序号
									</td>
									<td class="btle3e3e3" align="center" width="150">
										患者
									</td>
									<td class="btle3e3e3" align="center">
										项目名称
									</td>
									<td class="btle3e3e3" align="center" width="90">
										服务时间
									</td>
									<td class="btle3e3e3" align="center" width="70">
										单节费用<br>
										<span class="z12ffffff">
											(元)
										</span>
									</td>
									<td class="btle3e3e3" align="center" width="70">
										课节数<br>
										<span class="z12ffffff">
											(节)
										</span>
									</td>
									<td class="btle3e3e3" align="center" width="70">
										应收金额<br>
										<span class="z12ffffff">
											(元)
										</span>
									</td>
									<td class="btle3e3e3" align="center" width="70">
										减免金额<br>
										<span class="z12ffffff">
											(元)
										</span>
									</td>
									<td class="btle3e3e3" align="center" width="70">
										实收金额<br>
										<span class="z12ffffff">
											(元)
										</span>
									</td>
									<td class="btle3e3e3 bre3e3e3" align="center" width="90">
										缴费账户
									</td>
									<td class="btle3e3e3 bre3e3e3" align="center" width="90">
										缴费时间
									</td>
									
								</tr>
								  [#assign countQuantity = 0] 
					              [#assign countPrice = 0] 
					              [#assign countCouponDiscount = 0] 
					              [#assign countAmountPaid = 0] 
					              [#assign count = 0] 
								[#list page.content as order]
								<tr [#if order_index%2==0]class="bge7f4ff"[/#if]>
									<td class="btle3e3e3" align="center" height="50">${order_index+1}</td>
									<td class="btle3e3e3 plr10" align="left">
										${order.patientMember.name}<br>
										<span class="z12999999">
											${order.member.name} ${order.phone}
										</span>
									</td>
									<td class="btle3e3e3 plr10" align="left">
										${order.project.name}<br>
										<span class="z12939393">${order.project.doctor.name} ${order.project.doctor.mobile}</span>
									</td>
									<td class="btle3e3e3" align="center">
										${order.workDayItem.workDay.workDayDate?string("yyyy-MM-dd")}<br>
										<span class="z12939393">
											${order.workDayItem.startTime}-${order.workDayItem.endTime}
										</span>
									</td>
									<td class="btle3e3e3" align="center">
										${order.orderItems[0].price}
									</td>
									<td class="btle3e3e3" align="center">${order.quantity}</td>
									<td class="btle3e3e3" align="center">${order.price}</td>
									<td class="btle3e3e3" align="center">${order.couponDiscount}</td>
									<td class="btle3e3e3" align="center">${order.amountPaid}</td>
									<td class="btle3e3e3" align="center">
										${message("Order.AccountType."+order.accountType)}
									</td>
									<td class="btle3e3e3 bre3e3e3" align="center">
										${order.paidDate?string("yyyy-MM-dd")}<br>
										<span class="z12939393">
											${order.paidDate?string("HH:mm:ss")}
										</span>
									</td>
									
								</tr>
								[#assign countQuantity = order.quantity+countQuantity]
	              				[#assign countPrice = order.price+countPrice]
	              				[#assign countCouponDiscount = order.couponDiscount+countCouponDiscount]
				                [#assign countAmountPaid = order.amountPaid+countAmountPaid]
					            [#assign count = count+1] 
								[/#list]
								<tr>
									<td class="btle3e3e3" align="center" height="50">合计</td>
									<td class="btle3e3e3 plr10" align="left">${count}</td>
									<td class="btle3e3e3 plr10" align="left"></td>
									<td class="btle3e3e3" align="center"></td>
									<td class="btle3e3e3" align="center"></td>
									<td class="btle3e3e3" align="center">${countQuantity}</td>
									<td class="btle3e3e3" align="center">${countPrice}</td>
									<td class="btle3e3e3" align="center">${countCouponDiscount}</td>
									<td class="btle3e3e3" align="center">${countAmountPaid}</td>
									<td class="btle3e3e3 bre3e3e3" align="center"></td>
									<td class="btle3e3e3 bre3e3e3" align="center"></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="bte3e3e3" height="10"></td>
					</tr>
					<tr>
						[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
								[#include "/mechanism/include/pagination.ftl"]
					   	[/@pagination]
					</tr>
					<tr>
						<td></td>
					</tr>
				</table>
				</form>  
			</td>
		</tr>
	</table>
</div>
</body>
</html>