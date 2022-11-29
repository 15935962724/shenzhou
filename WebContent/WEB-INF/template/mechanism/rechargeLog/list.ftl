<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>充值记录</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
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
	
	[@flash_message /]
	
	//导出
	$download.click(function() {
		$listForm.attr('action','downloadList.jhtml');
		$listForm.submit();
		$listForm.attr('action','list.jhtml');		
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
						<td class="z20616161 bb1dd4d4d4" height="50">充值统计</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							[#if valid('export')]<a href="javascript:;"  id="download">导出</a>[/#if]
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td class="z14444444" colspan="2" align="right">
							变动周期：
							<input type="text" placeholder="开始时间" id="startDate" name="startDate"  [#if startDate??] value="${startDate?string("yyyy-MM-dd")}" [/#if] onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '#F{$dp.$D(\'endDate\')}'});" class="k_3 h30 bae1e1e1 w100 tac"> 
							- 
							<input type="text" placeholder="结束时间" id="endDate" name="endDate" [#if endDate??] value="${endDate?string("yyyy-MM-dd")}" [/#if] onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', minDate: '#F{$dp.$D(\'startDate\')}'});" class="k_3 h30 bae1e1e1 w100 tac"> 
							<input type="text" placeholder="用户姓名/电话" name="nameOrmobile" id="nameOrmobile" value="${nameOrmobile}" class="k_3 h30 bae1e1e1 tac w150">
							<input type="submit" value="查询" class="bae1e1e1 z16ffffff bg279fff h30 w65">
						</td>
					</tr>
					<tr>
						<td colspan="2" height="20"></td>
					</tr>
					<tr>
						<td colspan="2">
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="">
								<tr class="bg279fff z14ffffff">
									<td class="btle3e3e3" align="center" width="40" height="50">序号</td>
									<td class="btle3e3e3" align="center" width="90">充值时间</td>
									<td class="btle3e3e3" align="center" width="90">用户姓名</td>
									<td class="btle3e3e3" align="center" width="200">名下患者</td>
									<td class="btle3e3e3" align="center" width="90">充值方式</td>
									<td class="btle3e3e3" align="center" width="80">充值金额</td>
									<td class="btle3e3e3" align="center">备注</td>
									<td class="btle3e3e3 bre3e3e3" align="center" width="100">操作人</td>
								</tr>
								[#list page.content as rechargeLog]
								<tr [#if rechargeLog_index%2==0] class="bge7f4ff" [/#if]>
									<td class="btle3e3e3" align="center" height="50">${rechargeLog_index+1}</td>
									<td class="btle3e3e3" align="center">
										${rechargeLog.createDate?string("yyyy-MM-dd")}
										<span class="z12999999">
											${rechargeLog.createDate?string("HH:mm:ss")}
										</span>	
									</td>
									<td class="btle3e3e3 plr10" align="left"> ${rechargeLog.member.name}<br><span class="z12939393"> ${rechargeLog.member.mobile}</span></td>
									<td class="btle3e3e3 plr10" align="left">
									  [#list rechargeLog.member.children as patient]
				                         [#if !patient.isDeleted]
				                         ${patient.name}、
				                         [/#if]
				                      [/#list]
									</td>
									<td class="btle3e3e3" align="center">${message("Deposit.Type." + rechargeLog.type)}</td>
									<td class="btle3e3e3" align="center">${rechargeLog.money}</td>
									<td class="btle3e3e3 plr10" align="left">${rechargeLog.remarks}</td>
									<td class="btle3e3e3 bre3e3e3" align="center">
										${rechargeLog.operator}<br/>${(rechargeLog.mobile)!"-"}
									</td>
								</tr>
								[/#list]
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10" class="bte3e3e3"></td>
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