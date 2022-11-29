<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>

<script>
$().ready(function() {
	var $delete = $("#listTable input.delete");

	var $download = $("#download");
	//导出
	$download.click(function() {
		$listForm.attr('action','downloadList.jhtml');
		$listForm.submit();
		$listForm.attr('action','list.jhtml');		
	});


		$delete.click(function(){
		   var $this = $(this);
		   		$.ajax({
						url: "delete.jhtml",
						type: "POST",
						data: {id: $this.attr("data_id")},
						dataType: "json",
						cache: false,
						success: function(message) {
							$.message(message);
							if (message.type == "success") {
							//$this.parent().parent().remove();
								$this.closest("tr").remove();
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
				<table cellpadding="0" id="listTable" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">职级设置</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							<a href="add.jhtml">新增设置</a>
							[#if valid('export')]<a href="javascript:;" id = "download">导出</a>[/#if]
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
									<td class="btle3e3e3" align="center" width="100">职级名称</td>
									<td class="btle3e3e3" align="center">上门费用</td>
									<td class="btle3e3e3 bre3e3e3" align="center" width="150">操作</td>
								</tr>
								[#list page.content as doctorCategory]
								<tr [#if doctorCategory_index%2==0] class="bge7f4ff" [/#if] >
									<td class="btle3e3e3" align="center" height="50">${doctorCategory_index+1}</td>
									<td class="btle3e3e3 plr10" align="left">${doctorCategory.name}</td>
									<td class="btle3e3e3 plr10" align="left">${message("DoctorCategory.ChargeType."+doctorCategory.chargeType)}</td>
									<td class="btle3e3e3 bre3e3e3" align="center">
										<a href = "edit.jhtml?id=${doctorCategory.id}"><input type="button" value="编辑" class="bae1e1e1 w40 z12ffffff bg279fff"></a>
										<input type="button" class= "delete bae1e1e1 w40 z12ffffff bg32d3ea" data_id="${doctorCategory.id}" value="删除" class="bae1e1e1 w40 z12ffffff bg32d3ea">
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