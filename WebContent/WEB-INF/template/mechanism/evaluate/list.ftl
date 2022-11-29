<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/newmechanism/css/tipso.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/tipso.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script>
	$().ready(function() {
	    
	    //评论内容悬浮
		$('.tipso_style').tipso({
			width:350,
			background:'#d5e9fb',
			color:'#666666',
			position:'top'
		}); 
		var $listForm = $("#listForm");
		var $download = $("#download");//导出
		//导出
		$download.click(function() {
			$listForm.attr('action','downloadList.jhtml');
			$listForm.submit();
			$listForm.attr('action','list.jhtml');		
		});
		
	})

</script>

</head>
<body>
<div class="m020">
	<table cellpadding="0" cellspacing="0" border="0" class="k_1 mtb30" id="page_nav">
		<tr>
		</tr>
	</table>
	<form id="listForm" action="list.jhtml" method="get" >
	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="k_2">
		<tr>
			<td class="pa20">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">评分统计</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							[#if valid('export')]<a href="javascript:;" id = "download">导出</a>[/#if]
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td class="z14444444" colspan="2">
							<table cellpadding="0" cellspacing="0" border="0" width="100%">
								<tr>
									<td>
										<table cellpadding="0" cellspacing="0" border="0" width="100%">
											<tr>
												<td width="50%" height="40">
													统计周期：
													<input type="text" id="startDate" name="startDate" [#if startDate??] value="${startDate?string("yyyy-MM-dd")}" [/#if] placeholder="开始时间" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd',maxDate: '#F{$dp.$D(\'endDate\')}'});" class="k_3 h30 bae1e1e1 w100 tac">
													 - 
													<input type="text" id="endDate" name="endDate" [#if endDate??] value="${endDate?string("yyyy-MM-dd")}" [/#if] placeholder="结束时间" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd',minDate: '#F{$dp.$D(\'startDate\')}'});" class="k_3 h30 bae1e1e1 w100 tac">
												</td>
												<td width="50%">
													服务医师：<input type="text" name="doctorNameOrMobile" value="${doctorNameOrMobile}" placeholder="姓名/电话" class="k_3 h30 bae1e1e1 w215 tac">
												</td>
											</tr>
											<tr>
												<td width="50%" height="40">
													下单用户： <input type="text" name="nameOrmobile" value="${nameOrmobile}" placeholder="姓名/电话" class="k_3 h30 bae1e1e1 w215 tac">
												</td>
												<td width="50%">
													
												</td>
											</tr>
										</table>
									</td>
									<td width="150" valign="bottom" align="right">
										<input type="submit" value="查询" class="button_1_1 plr20 z16ffffff bg279fff h30">
									</td>
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
									<td class="btle3e3e3" align="center" width="40" height="50">序号</td>
									<td class="btle3e3e3" align="center" width="120">联系人</td>
									<td class="btle3e3e3" align="center" width="70">综合评分</td>
									<td class="btle3e3e3" align="center" width="70">技能评分</td>
									<td class="btle3e3e3" align="center" width="70">服务能力</td>
									<td class="btle3e3e3" align="center" width="70">沟通水平</td>
									<td class="btle3e3e3" align="center" width="120">诊疗医师</td>
									<td class="btle3e3e3 bre3e3e3" align="center">服务项目</td>
									<td class="btle3e3e3 bre3e3e3" align="center">评论内容</td>
								</tr>
								[#list page.content as evaluate]
								<tr [#if evaluate_index%2==0] class="bge7f4ff" [/#if]>
									<td class="btle3e3e3" align="center" height="50">${evaluate_index+1}</td>
									<td class="btle3e3e3 plr10" align="left">${evaluate.member.name}<br><span class="z12939393"> ${evaluate.member.mobile}</span></td>
									<td class="btle3e3e3" align="center">${evaluate.scoreSort}</td>
									<td class="btle3e3e3" align="center">${evaluate.skillSort}</td>
									<td class="btle3e3e3" align="center">${evaluate.serverSort}</td>
									<td class="btle3e3e3" align="center">${evaluate.communicateSort}</td>
									<td class="btle3e3e3 plr10" align="left">${evaluate.project.doctor.name}<br><span class="z12939393"> ${evaluate.project.doctor.mobile}</span></td>
									<td class="btle3e3e3 bre3e3e3 plr10" align="left">
										${evaluate.project.name}<br>
										<span class="z12999999">评价时间：${evaluate.createDate?string("yyyy-MM-dd HH:mm:ss")}</span>
									</td>
									<td class="btle3e3e3 bre3e3e3 plr10 top tipso_style" align="left" title="${evaluate.content}">
										 ${abbreviate(evaluate.content,30,"...")}
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