<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("Common.Main.title")}</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/newmechanism/css/iconfont.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/chart.bundle.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>

<script>
	
	$(function(){
		
		//明细展开
		$(".detailed").click(function(){
			$(".detailed").losest("tr").next("tr").hide();		
			$(this).losest("tr").next("tr").show();
			ifr_height("main_ifr");
		});
		
	})
</script>

<style>
	#flag_1{padding: 0px;left: 130px;top: 0px; position: absolute; display: none;}
</style>
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
			<form id="listForm" action="patient_visitMessage_list.jhtml" method="get">
            <input type="hidden" name="type" value="patient" />
            <input type="hidden" name="patientMemberId" value="${patientMember.id}" />
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">回访记录</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							<!--<a href="javascript:;">导出</a>-->
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
						[#include "/mechanism/include/patient_view.ftl"]		
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td colspan="2">
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="">
								<tr class="bg279fff z14ffffff">
									<td class="btle3e3e3" align="center" width="40" height="50">序号</td>
									<td class="btle3e3e3" align="center" width="120">回访医师</td>
									<td class="btle3e3e3" align="center" width="120">回访日期</td>
									<td class="btle3e3e3" align="center" width="80">回访方式</td>
									<td class="btle3e3e3 bre3e3e3" align="center" width="60">操作</td>
								</tr>
								[#list page.content as visitMessage]
								<tr [#if visitMessage_index%2==0] class="bge7f4ff" [/#if]>
									<td class="btle3e3e3" align="center" height="50">${visitMessage_index+1}</td>
									<td class="btle3e3e3 plr10" align="left">
										${visitMessage.doctor.name}<br>
										<span class="z12939393">
											${visitMessage.doctor.mobile}
										</span>
									</td>
									<td class="btle3e3e3" align="center">${visitMessage.visitDate?string("yyyy年MM月dd日")}</td>
									<td class="btle3e3e3" align="center">${message("VisitMessage.VisitType."+visitMessage.visitType)}</td>
									<td class="btle3e3e3 bre3e3e3" align="center">
										<input type="button" value="明细" class="bae1e1e1 w40 z12ffffff bgff8001 detailed">
									</td>
								</tr>
								<tr class="bge7f4ff" style="display:none;">
									<td colspan="8" class="btle3e3e3 bre3e3e3 pa10">
										<b>回访结果：</b><br>
										${visitMessage.message}
										<br><br>
										<b>回访时间：</b><br>
										${visitMessage.visitDate?string("yyyy-MM-dd HH:mm:ss")}
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
</body>
</html>