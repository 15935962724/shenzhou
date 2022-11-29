<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>

<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>


<script type="text/javascript">
$().ready(function() {

	var $listForm = $("#listForm");
	var $nameOrphone = $("#nameOrphone");
	
	var $doctorCategorieId = $("#doctorCategorieId");
	var $gender = $("#gender");
	var $audit = $("#audit");
	var $serverProjectCategorieId = $("#serverProjectCategorieId");
	var $deleteDoctor = $("#deleteDoctor");
	var $download = $("#download");
	
	[@flash_message /]

	$doctorCategorieId.change(function(){
		$listForm.submit();
	}); 
	$audit.change(function(){
		$listForm.submit();
	}); 
	$gender.change(function(){
		$listForm.submit();
	});
	$serverProjectCategorieId.change(function(){
		$listForm.submit();
	});
				
	//导出
	$download.click(function() {
		$listForm.attr('action','downloadList.jhtml');
		$listForm.submit();
		$listForm.attr('action','list.jhtml');		
	});
	
});
</script>

<script type="text/javascript">

	//隐藏框显示
	function disp_hidden_d(d_id,d_width,d_height,p_id,id)
	{
		if((typeof(d_width) != "undefined") && (typeof(d_height) !="undefined"))
			{
				var w=d_width / 2;
				var h=d_height / 2;
				$("."+d_id).css({"width":  d_width + "px","height": d_height + "px","left":"50%","top":"-" + h + "px","margin-left": "-" + w + "px","margin-bottom":"50px"});
				if (self.frameElement && self.frameElement.tagName == "IFRAME")
					{
						$("."+d_id).css({"top":$(parent.document).scrollTop()+50})
						if($(parent.document).height()<(d_height+$(parent.document).scrollTop()))
							$("#main_ifr",parent.document).height((parseInt(d_height)+$(parent.document).scrollTop()+150)+"px");
					}
				
			}else{
				ifr_height("main_ifr");
			}
		$("#"+p_id).val(id);
		$("#"+d_id).toggle();	
	}	

$(function(){
	$("#through").change(function(){
		if($(this).val()=="succeed")
			{
				$("#Pass").css({"display":"block"});
				$("#NotPass").css({"display":"none"});
			}
		else
			{
				$("#Pass").css({"display":"none"});
				$("#NotPass").css({"display":"block"});
			}
	})
	
	$("#isAbout").click(function(){
		if($(this).is(':checked'))
			{
				$("#target").css({"display":"block"});
			}
		else
			{
				$("#target").css({"display":"none"});
			}
	})
})


	



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
						<td class="z20616161 bb1dd4d4d4" height="50">员工管理</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							<a href="add.jhtml">新增</a>
							[#if valid('export')]<a href="javascript:;" id = "download">导出</a>[/#if]
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td class="z14444444" colspan="2" align="right">
							级别：<select id="doctorCategorieId" name="doctorCategorieId" class="h30 w100 bae1e1e1">
											<option value="">全部</option>
											[#list doctorCategorys as category]
											<option [#if doctorCategory == category] selected = "selected"[/#if]  value="${category.id}">${category.title}</option>
											[/#list]
								 </select>
							状态：<select id="audit" name="audit" class="h30 w100 bae1e1e1">
											<option value="">全部</option>
											[#list doctorMechanismRelationAudits as audit]
												<option value="${audit}">
													${message("DoctorMechanismRelation.Audit." + audit)}
												</option>
											[/#list]
										</select>
							<input type="text" placeholder="姓名/电话" id="nameOrphone" name="nameOrphone" value="${nameOrphone}" class="k_3 h30 bae1e1e1 plr10 w125">
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
									<td class="btle3e3e3" align="center" width="100">姓名</td>
									<td class="btle3e3e3" align="center" width="80">性别</td>
									<td class="btle3e3e3" align="center" width="150">员工职级</td>
									<td class="btle3e3e3" align="center" width="90">员工角色</td>
									[#if valid('lookphonenum')]<td class="btle3e3e3" align="center" width="110">联系电话</td>[/#if]
									<td class="btle3e3e3" align="center" width="70">状态</td>
									<td class="btle3e3e3 bre3e3e3" align="center" width="150">操作</td>
								</tr>
								[#list page.content as doctorMechanismRelation]
								<tr [#if doctorMechanismRelation_index%2==0]class="bge7f4ff"[/#if]>
									<td class="btle3e3e3" align="center" height="50">${doctorMechanismRelation_index+1}</td>
									<td class="btle3e3e3" align="center">${doctorMechanismRelation.doctor.name}</td>
									<td class="btle3e3e3" align="center">${message("Member.Gender." + doctorMechanismRelation.doctor.gender)}</td>
									<td class="btle3e3e3" align="center">[#if doctorMechanismRelation.doctorCategory??]${doctorMechanismRelation.doctorCategory.title}[#else]暂未设置[/#if]</td>
									<td class="btle3e3e3" align="center">[#if doctorMechanismRelation.mechanismroles.size()>0] 
									[#list doctorMechanismRelation.mechanismroles as mechanismrole]${mechanismrole.name}[#break][/#list]
									[#else] 暂无分配角色 [/#if]</td>
									[#if valid('lookphonenum')]<td class="btle3e3e3" align="center">${doctorMechanismRelation.doctor.mobile}</td>[/#if]
									<td class="btle3e3e3" align="center">${message("DoctorMechanismRelation.Audit." + doctorMechanismRelation.audit)}</td>
									<td class="btle3e3e3 bre3e3e3" align="center">
										<a href = "view.jhtml?id=${doctorMechanismRelation.id}"><input type="button" value="明细" class="bae1e1e1 w40 z12ffffff bgff8001"></a>
										[#if doctorMechanismRelation.audit != "succeed"]<input type="button" value="审核" class="bae1e1e1 w40 z12ffffff bg279fff" onClick="disp_hidden_d('assess',370,250,'p_id',${doctorMechanismRelation.id})">[/#if]<!--审核、停用、启用按钮三选一-->
										[#if doctorMechanismRelation.audit == "succeed"]<input type="button" value="[#if doctorMechanismRelation.isEnabled]停用 [#else]启用[/#if]" class="bae1e1e1 w40 z12ffffff bg32d3ea" onClick="disp_hidden_d('visit',370,200,'id',${doctorMechanismRelation.id});">[/#if]
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
<div id="assess">
	<div class="assess">
  		<form id="myform" action="${base}/mechanism/doctorMechanismRelation/update_audit.jhtml" method="post" >
  		<input type="hidden" id="p_id" name="doctorMechanismRelationId" value="">
  		<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
  		<input type="hidden" id="pageNumber" name="pageNumber" value="${page.pageNumber}">
   		<table cellpadding="0" cellspacing="0" border="0" width="100%">
   			<tr>
   				<td align="center" class="k_4" colspan="2">
   					员工审核
  				</td>
   			</tr>
			<tr>
				<td height="30" width="90" class="z14323232" align="right">审核状态</td>
				<td align="left" class="plr20 ptb10">
					<select name="audit" class="inputkd9d9d9bgf6f6f6 h30 w125 z14717171" id="through">
						[#list doctorMechanismRelationAudits as audit]
							<option value="${audit}">
								${message("DoctorMechanismRelation.Audit." + audit)}
							</option>
						[/#list]
					</select>
  				</td>
   			</tr>
   			<tr>
   				<td colspan="2" height="130" valign="top">
   					<table cellpadding="0" cellspacing="0" border="0" width="100%" id="Pass">
   						<tr>
   							<td height="30" width="90" align="right" class="z14323232">员工职级</td>
   							<td  class="plr20 ptb10">
   								<select name = "doctorCategoryId" class="inputkd9d9d9bgf6f6f6 h30 w125 z14717171">
   								[#list mechanism.doctorCategorys as doctorCategory] 
   									<option value="${doctorCategory.id}">${doctorCategory.name}</option>
   								[/#list]
   								</select>
   							</td>
   						</tr>
   						<tr>
   							<td height="30" width="90" align="right" class="z14323232">角色设置</td>
   							<td  class="plr20 ptb10">
   								<select name = "mechanismRoleId" class="inputkd9d9d9bgf6f6f6 h30 w125 z14717171">
   								[#list mechanism.mechanismRoles as mechanismRole] 
   									<option value="${mechanismRole.id}">${mechanismRole.name}</option>
   								[/#list]
   								</select>
   							</td>
   						</tr>
   						<tr>
   							<td height="40" align="right" class="z14323232">开通预约</td>
   							<td class="plr20 ptb10 z14717171">
   								<input type="checkbox" id="isAbout" name="isAbout"> 设置该员工患者端为可预约
   							</td>
   						</tr>
   						[#if mechanism.mechanismSetup.achievementsType == "ladderProportion"]
   						<tr>
   							<td colspan="2">
   								<table cellpadding="0" cellspacing="0" border="0" width="100%"  id="target" class="dpn">
   									<tr>
										<td width="90" height="30" align="right" class="z14323232">日工作目标</td>
										<td class="plr20 z12939393">
											<input type="text" name = "dayWorkTarget" class="z14717171 inputkd9d9d9bgf6f6f6 h20 w125 plr10">　元/日
										</td>
   									</tr>
   									<tr>
										<td width="90" height="30" align="right" class="z14323232">提成占比</td>
										<td class="plr20 z12939393">
											<input type="text" name = "percentage" class="z14717171 inputkd9d9d9bgf6f6f6 h20 w125 plr10" value="${mechanism.mechanismSetup.doctorProportion}">　%
										</td>
   									</tr>
   								</table>
   							</td>
   						</tr>
   						[/#if]
   					</table>
   					<table cellpadding="0" cellspacing="0" border="0" width="100%" class="dpn" id="NotPass">
   						<tr>
   							<td height="100" width="90" align="right" class="z14323232">原因说明</td>
   							<td>
   								<textarea name="statusExplain" class="z14717171 inputkd9d9d9bgf6f6f6 w213 h80 m1020"></textarea>
   							</td>
   						</tr>
   					</table>
   				</td>
   			</tr>
   			<tr>
   				<td align="center" colspan="2" height="40" valign="middle">
   					<input type="submit" value="确认" class="button_3">
   					&nbsp;　&nbsp;
					<input type="button" value="取消" class="button_4 z14fefefe" onClick="$('#myform')[0].reset();disp_hidden_d('assess','','','p_id');">
  					
   				</td>
   			</tr>
   		</table>
   		</form>
	</div>
</div>

<div id="visit">
	<div class="visit">
  		<form id="visitform" action="${base}/mechanism/doctorMechanismRelation/update_isEnabled.jhtml" method="post">
  		<input type="hidden" id="id" name="id" value="">
  		<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
  		<input type="hidden" id="pageNumber" name="pageNumber" value="${page.pageNumber}">
   		<table cellpadding="0" cellspacing="0" border="0" width="100%">
   			<tr>
   				<td align="center" class="k_4" colspan="2">
   					员工状态设置
  				</td>
   			</tr>
			<tr>
				<td height="30" width="90" class="z14323232" align="right">状态选择</td>
				<td align="left" class="plr20 ptb10">
					<select name="isEnabled" class="inputkd9d9d9bgf6f6f6 h30 w125 z14717171" id="through">
						<option value = "false">停用</option>
						<option value = "true">启用</option>
					</select>
  				</td>
   			</tr>
			<tr>
				<td height="100" width="90" align="right" class="z14323232">原因说明</td>
				<td>
					<textarea  name="isEnabledExplain" class="z14717171 inputkd9d9d9bgf6f6f6 w213 h80 mlr20"></textarea>
				</td>
			</tr>
   			<tr>
   				<td align="center" colspan="2" height="40" valign="middle">
   					<input type="submit" value="确认" class="button_3">
   					&nbsp;　&nbsp;
					<input type="button" value="取消" class="button_4 z14fefefe" onClick="$('#visitform')[0].reset();disp_hidden_d('visit','','','v_id');">
   				</td>
   			</tr>
   		</table>
   		</form>
    
	</div>
</div>



</body>
</html>