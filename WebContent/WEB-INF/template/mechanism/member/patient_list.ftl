<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("Common.Main.title")}</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />
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

<script type="text/javascript">
$().ready(function() {
	
			var $listForm = $("#listForm");
			var $download = $("#download");
			
			[@flash_message /]
			
			//导出
			$download.click(function() {
			$listForm.attr('action','downloadPatientList.jhtml');
			$listForm.submit();
			$listForm.attr('action','patient_list.jhtml');		
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
				<form id="listForm" action="patient_list.jhtml" method="get">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">患者信息</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							[#if valid('export')]<a href="javascript:;" id = "download">导出</a>[/#if]
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td>
							<select id="flag" name="healthType" onchange="$('#listForm').submit();" class="h30 w100 bae1e1e1">
								<option value="">全部状态</option>
								[#list healthTypes as healthType]
									<option [#if healthType==healthtype ]selected="selected"[/#if] value="${healthType}">${message("PatientMechanism.HealthType."+healthType)}</option>
								[/#list]
							</select>
						</td>
						<td class="z14444444" colspan="2" align="right">
						
							<select name = "isDeleted"  onchange="$('#listForm').submit();"  class="h30 w100 bae1e1e1">
										<option [#if !isDeleted??]selected="selected" [/#if] value="">全部</option>
										<option [#if isDeleted??] [#if isDeleted] selected="selected" [/#if] [/#if] value="true">删除</option>
										<option [#if isDeleted??] [#if !isDeleted] selected="selected" [/#if] [/#if] value="false">正常</option>
							</select>
						
							<input type="text" id="nameOrmobile" name="nameOrmobile"  [#if nameOrmobile??] value="${nameOrmobile}" [/#if] placeholder="患者姓名/电话" class="k_3 h30 bae1e1e1 plr10 w125">
							<input type="submit" value="查询" class="button_1_1 plr20 z16ffffff bg279fff h30">
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td colspan="2">
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="">
								<tr class="bg279fff z14ffffff">
									<td class="btle3e3e3" align="center" width="40" height="50">序号</td>
									<td class="btle3e3e3" align="center" width="120">姓名</td>
									<td class="btle3e3e3" align="center" width="60">状态</td>
									<td class="btle3e3e3" align="center" width="60">删除状态</td>
									<td class="btle3e3e3" align="center" width="110">入院日期</td>
									<td class="btle3e3e3" align="center" width="120">联系人</td>
									<td class="btle3e3e3" align="center">现住地址</td>
									<td class="btle3e3e3 bre3e3e3" align="center" width="100">操作</td>
								</tr>
								[#list  page.content as patientMechanism]
								<tr [#if patientMechanism_index%2==0]class="bge7f4ff"[/#if]>
									<td class="btle3e3e3" align="center" height="50">${patientMechanism_index+1}</td>
									<td class="btle3e3e3 plr10" align="left">
										${patientMechanism.patient.name}<br>
										<span class="z12939393">
											${message("Member.Gender."+patientMechanism.patient.gender)} ${age(patientMechanism.patient.birth)}周岁 ${patientMechanism.patient.nation}<br>
											${patientMechanism.patient.birth?string("yyyy年MM月dd日")}
										</span>
									</td>
									<td class="btle3e3e3" align="center">${message("PatientMechanism.HealthType."+patientMechanism.healthType)}</td>
									<td class="btle3e3e3" align="center">${patientMechanism.isDeleted?string("已删除","正常")}</td>
									<td class="btle3e3e3" align="center">${firstOrderDate(patientMechanism.patient.id)}</td>
									<td class="btle3e3e3 plr10" align="left">
										${patientMechanism.patient.parent.name}<br>
										<span class="z12939393">${message("Member.Gender."+patientMechanism.patient.parent.gender)} ${patientMechanism.patient.parent.mobile}</span>
									</td>
									<td class="btle3e3e3 plr10" align="left">${patientMechanism.patient.area.fullName}${patientMechanism.patient.address}</td>
									<td class="btle3e3e3 bre3e3e3" align="center">
										<a href="patient_view.jhtml?patientMemberId=${patientMechanism.patient.id}"><input type="button" value="明细" class="bae1e1e1 w40 z12ffffff bgff8001"></a>
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