<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>好康护 - 让幸福简单起来</title>
<!--<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />-->
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>

<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>


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
			<form id="listForm" action="past.jhtml" method="get">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">排班管理</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							<a href="javascript:;">导出</a>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td>
						</td>
						<td align="right">
							<input type="text" id = "startDate" name="startDate" [#if startDate??] value="${startDate?string("yyyy-MM-dd")}" [/#if] placeholder="开始时间" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '#F{$dp.$D(\'endDate\')}'});" class="k_3 h30 bae1e1e1 w80 tac">
							 -
							<input type="text" id = "endDate" name="endDate" [#if endDate??] value="${endDate?string("yyyy-MM-dd")}" [/#if] placeholder="结束时间" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', minDate: '#F{$dp.$D(\'startDate\')}'});" class="k_3 h30 bae1e1e1 w80 tac">
							<input type="text" name = "nameOrphone" value="${nameOrphone}" placeholder="员工姓名/电话" class="input_1 plr10 h30 bae1e1e1">
							<input type="submit" value="搜索" class="button_1 plr20 z16ffffff bg279fff h34">
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td colspan="2">
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="">
								<tr class="bg279fff z14ffffff">
									<td class="btle3e3e3" align="center" width="5%" height="50" rowspan="2">序号</td>
									<td class="btle3e3e3" align="center" rowspan="2" width="10%">工作日</td>
									<td class="btle3e3e3" align="center" rowspan="2" width="20%">姓名</td>
									<td class="btle3e3e3" align="center" rowspan="2" width="12%">联系电话</td>
									<td class="btle3e3e3" align="center" rowspan="2" width="13%">工作时间</td>
									<td class="btle3e3e3" align="center" colspan="2" width="20%" height="25">当日预约</td>
									<td class="btle3e3e3 bre3e3e3" align="center" width="20%" colspan="2">当日服务</td>
								</tr>
								<tr class="bg279fff z14ffffff">
									<td class="btle3e3e3" align="center" height="25" width="10%">人次</td>
									<td class="btle3e3e3" align="center" width="10%">课节</td>
									<td class="btle3e3e3" align="center" width="10%">人次</td>
									<td class="btle3e3e3 bre3e3e3" align="center" width="10%">课节</td>
								</tr>
								[#list page.content as workDay]
								<tr [#if workDay_index%2==0] class="bge7f4ff" [/#if]>
									<td class="btle3e3e3" align="center" height="50">${workDay_index+1}</td>
									<td class="btle3e3e3" align="center">${workDay.workDayDate?string("yyyy-MM-dd")}</td>
									<td class="btle3e3e3" align="center">${workDay.doctor.name}</td>
									<td class="btle3e3e3" align="center">${workDay.doctor.mobile}</td>
									<td class="btle3e3e3 ptb10" align="center">
									[#list workDay.workDayItems as workDayItem]
									    [#if workDayItem.mechanism == mechanism &&  workDayItem.workDayType=="mechanism" ]
									    ${workDayItem.startTime} - ${workDayItem.endTime}<br>
									    [/#if]
									[/#list]
									</td>
									<td class="btle3e3e3" align="center">${sameDayOrderCount(workDay.doctor.id,workDay.workDayDate?string("yyyy-MM-dd"))}</td>
									<td class="btle3e3e3" align="center">${sameDayQuantityCount(workDay.doctor.id,workDay.workDayDate?string("yyyy-MM-dd"))}</td>
									<td class="btle3e3e3" align="center">${sameDayServerCount(workDay.doctor.id,workDay.workDayDate?string("yyyy-MM-dd"))}</td>
									<td class="btle3e3e3 bre3e3e3" align="center">${sameDayServerQuantity(workDay.doctor.id,workDay.workDayDate?string("yyyy-MM-dd"))}</td>
								</tr>
								[/#list]
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