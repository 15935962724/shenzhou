<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>好康护 - 让幸福简单起来</title>
<!--<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />-->
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>


<script type="text/javascript">
$().ready(function() {

	var $listForm = $("#listForm");
	var $filterSelect = $("#filterSelect");
	var $download = $("#download");
	[@flash_message /]
	
	// 筛选选项
	$filterSelect.bind("change",function() {
		var $this = $(this);
		
		$listForm.submit();
		return false;
	});

	//导出
	$download.click(function() {
		$listForm.attr('action','downloadList.jhtml');
		$listForm.submit();
		$listForm.attr('action','list.jhtml');		
	});

	


});
</script>
<script>
	
	function set_info()
		{
			var check = $("#audit").val();
			var html = "";
			if(check == "succeed")
				{
					html = '<input type="text" class="bad9d9d9 h30 w368" placeholder="请输入提成占比">'
				}
			if(check == "fail")
				{
					html = '<textarea class="bad9d9d9 h50 w368" placeholder="请输入不通过原因"></textarea>';
				}
			$("#memotd").html(html);   					
		}
	function toggle_div(id)
		{
			$("#projectId").val(id);
			$("#layer").toggle();	
		}
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
			<form id="listForm" action="list.jhtml" method="get">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">项目审核</td>
						[#if valid('export')]<td class="z16279fff bb1dd4d4d4" align="right"><a href="javascript:;" id="download">导出</a></td>[/#if]
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td colspan="2" align="right">
							状态：
							<select name="audit" id="filterSelect" class="h34 bae1e1e1">
								<option value="">全部</option>
								[#list audits as au]
				             	<option [#if audit==au ] selected = "selected" [/#if]  value="${au}">${message("Project.Audit." + au)}</option>
				             	[/#list]
							</select>
							<input type="text" placeholder="员工姓名" name="doctorName" value="${doctorName}" class="input_1 plr10 h30 bae1e1e1">
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
									<td class="btle3e3e3" align="center" width="40" height="50">序号</td>
									<td class="btle3e3e3" align="center">项目名称</td>
									<td class="btle3e3e3" align="center" width="120">所属项目</td>
									<td class="btle3e3e3" align="center" width="80">服务医师</td>
									<td class="btle3e3e3" align="center" width="60">状态</td>
									<td class="btle3e3e3" align="center" width="100">创建时间</td>
									<td class="btle3e3e3 bre3e3e3" align="center" width="100">操作</td>
								</tr>
								[#list page.content as project]
								<tr [#if project_index%2==0] class="bge7f4ff" [/#if]>
									<td class="btle3e3e3" align="center" height="50">${project_index+1}</td>
									<td class="btle3e3e3 plr10">${project.name}</td>
									<td class="btle3e3e3" align="center">${project.serverProjectCategory.name}</td>
									<td class="btle3e3e3" align="center">${project.doctor.name}</td>
									<td class="btle3e3e3" align="center">${message("Project.Audit." + project.audit)}</td>
									<td class="btle3e3e3" align="center">${project.createDate?string("yyyy-MM-dd")}</td>
									<td class="btle3e3e3 bre3e3e3" align="center">
										<input type="button" value="明细" onClick="show_info(${project.id},1);" class="bae1e1e1 w40 z12ffffff bgff8001">
										<input type="button" value="审核" class="bae1e1e1 w40 z12ffffff bg279fff" onClick="toggle_div(${project.id})">
									</td>
								</tr>
								<tr class="bge7f4ff" style="display:none;" id="info_${project.id}">
									<td colspan="8" class="btle3e3e3 bre3e3e3 pa20">
										[#list project.projectItems as projectItem]
										${message("Project.ServiceGroup."+projectItem.serviceGroup)} ${message("Project.Mode."+projectItem.mode)} ${projectItem.price}元/${projectItem.time}分钟<br>
										[/#list]
										创建时间：${project.createDate?string("yyyy-MM-dd HH:mm:ss")}<br>
										项目描述:${project.introduce}
									</td>
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
<div id="layer">
	<div class="layer">
  		<form id="myform" action = "updateAudit.jhtml" method = "POST">
  		<input type="hidden" id="projectId" name="id" value="">
  		<input type="hidden" id="pageNumber" name="pageNumber" value="${page.pageNumber}">
  		<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
   		<table cellpadding="0" cellspacing="0" border="0" width="100%">
   			<tr>
   				<td>
					<select id="check" name="audit" class="h30 w370 bg279fff z14ffffff" <!--onChange="set_info();"-->>
						[#list audits as aud]
						<option value = "${aud}">${message("Project.Audit." + aud)}</option>
						[/#list]
					</select>
  				</td>
   			</tr>
   			<tr>
   				<td height="15"></td>
   			</tr>
   			<tr>
   				<td height="60" id="memotd" valign="top">
   					<textarea class="bad9d9d9 h50 w368" name = "remarks" placeholder="备注"></textarea>
   				</td>
   			</tr>
   			<tr>
   				<td height="15"></td>
   			</tr>
   			<tr>
   				<td align="center">
   					<input type="submit" value="确认" class="button_3">
   					&nbsp;　&nbsp;
					<input type="button" value="取消" class="button_4 z14fefefe" onClick="$('#myform')[0].reset();$('#memotd').html();toggle_div();">
  					
   				</td>
   			</tr>
   		</table>
   		</form>
    
	</div>
</div>

</body>
</html>