<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>退款管理</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/js.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>

<script type="text/javascript">
$().ready(function() {

	var $listForm = $("#listForm");
	var $download = $("#download");
	var $inputForm = $("#inputForm");

	[@flash_message /]
   
   	//导出
	$download.click(function() {
		$listForm.attr('action','downloadList.jhtml');
		$listForm.submit();
		$listForm.attr('action','list.jhtml');		
	});
   
   	$(".detailed").click(function(){
		$(".detailed").parents("tr").next("tr").css({"display":"none"});
		$(this).parents("tr").next("tr").css({"display":""});
		ifr_height("main_ifr");
	})
	
	
	$('#confirm').off().on('click', function(){
	 			$.ajax({
					url: 'update.jhtml',
					type: "POST",
					data: $inputForm.serialize(),
					dataType: "json",
					cache: false,
					success: function(message) {
						if (message.type == "success") {
							$.message(message);
							location.reload(true);
							return ;
						} else {
							$.message(message);
							$("#cancel").click();
						}
					}
				});
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
			<form id="listForm" action="list.jhtml" method="get">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">退款管理</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							[#if valid('export')]<a href="javascript:;" id="download">导出</a>[/#if]
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td class="z14444444" colspan="2" align="right">
							<select class="h30 w100 bae1e1e1"  name="status" onchange="$('#listForm').submit();">
								<option value="" >全部</option>
									[#list statuss as stu]
		                            <option [#if stu == status] selected="selected" [/#if] value="${stu}">${message("Refunds.Status."+stu)}</option>
		                            [/#list]
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td colspan="2">
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="">
								<tr class="bg279fff z14ffffff">
									<td class="btle3e3e3" align="center" width="120" height="50">订单编号</td>
									<td class="btle3e3e3" align="center">项目名称</td>
									<td class="btle3e3e3" align="center" width="90">服务时间</td>
									<td class="btle3e3e3" align="center" width="150">下单人</td>
									<td class="btle3e3e3" align="center" width="80">订单金额</td>
									<td class="btle3e3e3" align="center" width="80">实付金额</td>
									<td class="btle3e3e3" align="center" width="80">退款金额</td>
									<td class="btle3e3e3" align="center" width="60">状态</td>
									<td class="btle3e3e3 bre3e3e3" align="center" width="100">操作</td>
								</tr>
								[#list page.content as refunds]
								<tr [#if refunds_index%2==0]class="bge7f4ff"[/#if]>
									<td class="btle3e3e3" align="center" height="50">${refunds.order.sn}</td>
									<td class="btle3e3e3 plr10" align="left">
										${refunds.order.project.name}<br>
										<span class="z12999999">
											${refunds.order.doctor.name} ${refunds.order.doctor.mobile}
										</span>
									</td>
									<td class="btle3e3e3" align="center">
									[#if refunds.order.workDayItem??]
										${refunds.order.workDayItem.workDay.workDayDate?string("yyyy-MM-dd")}<br>
										<span class="z12999999">
											${refunds.order.workDayItem.startTime}-${refunds.order.workDayItem.endTime}
										</span>
									[#else]
									-
									[/#if]
										
									</td>
									<td class="btle3e3e3 plr10" align="left">
										${refunds.order.consignee}<br>
										<span class="z12999999">
											${refunds.order.patientMember.name} ${message("Member.Gender."+refunds.order.patientMember.gender)} ${age(refunds.order.patientMember.birth)}周岁<br>
											${refunds.order.patientMember.mobile}
										</span>
									</td>
									<td class="btle3e3e3" align="center">${currency(refunds.order.amount,true)}</td>
									<td class="btle3e3e3" align="center">${currency(refunds.order.amountPaid,true)}</td>
									<td class="btle3e3e3" align="center">${currency(refunds.amount,true)}</td>
									<td class="btle3e3e3" align="center">${message("Refunds.Status."+refunds.status)}</td>
									<td class="btle3e3e3 bre3e3e3" align="center">
										<input type="button" value="明细" class="bae1e1e1 w40 z12ffffff bgff8001 detailed">
										<input type="button" value="审核" class="bae1e1e1 w40 z12ffffff bg279fff" onClick="disp_hidden_d('visit',370,250,'${refunds.id}',${refunds.id})">
									</td>
								</tr>
								<tr [#if refunds_index%2==0]class="bge7f4ff"[/#if] style="display:none;">
									<td colspan="9" class="btle3e3e3 bre3e3e3 pa20">
										服务项目：${refunds.order.project.name}<br>
										服务时间：[#if refunds.order.workDayItem??]${refunds.order.workDayItem.workDay.workDayDate?string("yyyy年MM月dd日")} ${refunds.order.workDayItem.startTime}-${refunds.order.workDayItem.endTime}[#else]已取消[/#if]<br>
										订单金额：${currency(refunds.order.amount,true)}<br>
										下 单 人：${refunds.order.consignee} <span class="z12999999">${refunds.order.phone}</span><br>
										患者姓名：${refunds.order.patientMember.name}　<span class="z12999999">${message("Member.Gender."+refunds.order.patientMember.gender)} ${age(refunds.order.patientMember.birth)}周岁</span><br>
										<hr class="banone bb1dd4d4d4">
										退款金额：${currency(refunds.amount,true)}<br>
										备　　注：${refunds.memo}<br>
										退款状态：${message("Refunds.Status."+refunds.status)}<br>
										申请医师：${refunds.operator}<br>
										申请时间：${refunds.createDate?string("yyyy年MM月dd日 HH:mm")}
									</td>
								</tr>
								 [/#list]
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="bte3e3e3"></td>
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

<div id="visit">
	<div class="visit">
  		<form id="inputForm" >
   		<table cellpadding="0" cellspacing="0" border="0" width="100%">
   			<tr>
   				<td align="center" class="k_4" colspan="2">
   					退款申请审核
  				</td>
   			</tr>
			<tr>
				<td height="30" width="90" class="z14323232" align="right">状态选择</td>
				<td align="left" class="plr20 ptb10">
					<select class="inputkd9d9d9bgf6f6f6 h30 w125 z14717171" name="status" id="through">
						[#list statuss as status]
		                <option value="${status}">${message("Refunds.Status."+status)}</option>
		                [/#list]
					</select>
					<input type="hidden" id="p_id" name="refundsId" value="">
  				</td>
   			</tr>
			<tr>
				<td height="100" width="90" align="right" class="z14323232">原因说明</td>
				<td>
					<textarea name="memo"  class="z14717171 inputkd9d9d9bgf6f6f6 w213 h80 mlr20"></textarea>
				</td>
			</tr>
   			<tr>
   				<td align="center" colspan="2" height="40" valign="middle">
   					<input type="button" value="确认" id = "confirm"  class="button_3" >
   					&nbsp;　&nbsp;
					<input type="button" value="取消" id="cancel" class="button_4 z14fefefe" onClick="$('#inputForm')[0].reset();disp_hidden_d('visit','','','v_id');">
   				</td>
   			</tr>
   		</table>
   		</form>
    
	</div>
</div>

</body>
</html>