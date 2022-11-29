<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>查看机构</title>
<meta name="author" content="HaoKangHu Team" />
<meta name="copyright" content="HaoKangHu" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>

<script type="text/javascript">
$().ready(function() {
	
	var $areaId = $("#areaId");
	var $mechanismCategoryId = $("#mechanismCategoryId");
	var $mechanismRankId = $("#mechanismRankId");
	var $certificatesAreaId = $("#certificatesAreaId");
	var $doctorBirthplaceId = $("#doctorBirthplaceId");
	var $doctorAreaId = $("#doctorAreaId");
	
	
	// 联系人籍贯地区
	$doctorBirthplaceId.lSelect({
		url: "${base}/admin/common/area.jhtml"
	});
	
	// 联系人现住地区
	$doctorAreaId.lSelect({
		url: "${base}/admin/common/area.jhtml"
	});
	
	// 机构地区
	$areaId.lSelect({
		url: "${base}/admin/common/area.jhtml"
	});
	
	// 企业注册地区
	$certificatesAreaId.lSelect({
		url: "${base}/admin/common/area.jhtml"
	});
	
	// 机构类型
	$mechanismCategoryId.lSelect({
		url: "${base}/admin/common/mechanismCategory.jhtml"
	});
	
	// 机构等级
	$mechanismRankId.lSelect({
		url: "${base}/admin/common/mechanismRank.jhtml"
	});
	
	[@flash_message /]

});
</script>
</head>
<body>
	
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 查看机构
	</div>
	<ul id="tab" class="tab">
		<li>
			<input type="button" value="企业基本资料" />
		</li>
		<li>
			<input type="button" value="企业认证信息" />
		</li>
		<li>
			<input type="button" value="联系人信息" />
		</li>
		<li>
			<input type="button" value="认证状态" />
		</li>
		<li>
			<input type="button" value="服务状态" />
		</li>
		<li>
			<input type="button" value="操作记录" />
		</li>
	</ul>
	<!--企业基本资料start-->
	<table class="input tabContent">
		<tr>
			<th>
			企业名称:
			</th>
			<td>
				${mechanism.name}
			</td>
		</tr>
		<tr>
			<th>
				企业LOGO:
			</th>
			<td>
				<img  style="height: 80px;width: 80px;" src = "${mechanism.logo}"/>
			</td>
		</tr>
		<tr>
			<th>
				机构类型:
			</th>
			<td>
				<span class="fieldSet">
					<input type="hidden" id="mechanismCategoryId" name="mechanismCategoryId"  value="${(mechanism.mechanismCategory.id)!}" treePath="${(mechanism.mechanismCategory.treePath)!}" />
				</span>
			</td>
		</tr>
		<tr>
			<th>
				机构等级:
			</th>
			<td>
				<span class="fieldSet">
					<input type="hidden" id="mechanismRankId" name="mechanismRankId" value="${(mechanism.mechanismRank.id)!}" treePath="${(mechanism.mechanismRank.treePath)!}" />
				</span>
			</td>
		</tr>
		<tr>
			<th>
				机构地址:
			</th>
			<td>
				<span class="fieldSet">
					<input type="hidden" id="areaId" name="areaId"  value="${(mechanism.area.id)!}" treePath="${(mechanism.area.treePath)!}"  />
				</span>
			</td>
		</tr>
		<tr>
			<th>
				详细地址:
			</th>
			<td>
				${mechanism.address}
			</td>
		</tr>
		<tr>
			<th>
				企业介绍:
			</th>
			<td>
				${mechanism.introduce}
			</td>
		</tr>
		<tr>
			<th>
				机构图片:
			</th>
			<td>
			    [#list mechanism.mechanismImages as mechanismImage]
			    	<img  src = "${mechanismImage.thumbnail}" />
			    [/#list]
			</td>
		</tr>
		<tr>
			<th>
				联系电话:
			</th>
			<td>
				${mechanism.phone}
			</td>
		</tr>
	</table>
	<!--企业基本资料end-->
	<!--企业认证信息start-->
	<table class="input tabContent">
	[#if certificates??]
		<tr>
			<th>
				企业名称:
			</th>
			<td>
				${certificates.title}
			</td>
		</tr>
		<tr>
			<th>
				企业注册地:
			</th>
			<td>
				<span class="fieldSet">
					<input type="hidden" id="certificatesAreaId" name="certificatesAreaId"  value="${(certificates.area.id)!}" treePath="${(certificates.area.treePath)!}"  />
				</span>
			</td>
		</tr>
		<tr>
			<th>
				详细地址:
			</th>
			<td>
				${certificates.address}
			</td>
		</tr>
		<tr>
			<th>
				证件图片:
			</th>
			<td>
				${certificates.certificatesImg}
			</td>
		</tr>
		<tr>
			<th>
				法定代表人:
			</th>
			<td>
			    ${certificates.name}
			</td>
		</tr>
		<tr>
			<th>
				开户银行:
			</th>
			<td>
				${certificates.brank}
			</td>
		</tr>
		<tr>
			<th>
				账号:
			</th>
			<td>
				${certificates.brank}
			</td>
		</tr>
	[#else]
		<tr class="title">
			<th style= "text-align: center; color: red;">
				该机构未填写认证资料
			</th>
		</tr>
	[/#if]
	</table>
	<!--企业认证信息end-->
	<!--联系人信息start-->
	<table class="input tabContent">
	[#if doctor??]
		<tr>
			<th>
				联系人LOGO:
			</th>
			<td>
				<img style="height: 80px;width: 80px;" src = "${doctor.logo}" />
			</td>
		</tr>
		<tr>
			<th>
				姓名:
			</th>
			<td>
				${doctor.name}
			</td>
		</tr>
		<tr>
			<th>
				性别:
			</th>
			<td>
				${message("Member.Gender."+doctor.gender)}
			</td>
		</tr>
		<tr>
			<th>
				民族:
			</th>
			<td>
			    ${doctor.nation}
			</td>
		</tr>
		<tr>
			<th>
				出生日期:
			</th>
			<td>
				[#if doctor.birth??]${doctor.birth?string("yyyy-MM-dd")}[/#if]
			</td>
		</tr>
		<tr>
			<th>
				户籍地址:
			</th>
			<td>
				<span class="fieldSet">
					<input type="hidden" id="doctorBirthplaceId" name="doctorBirthplaceId"  value="${(doctor.birthplace.id)!}" treePath="${(doctor.birthplace.treePath)!}"  />
				</span>
				<span >
					${doctor.birthplaceAddress}
				</span>
			</td>
		</tr>
		<tr>
			<th>
				现住地址:
			</th>
			<td>
				<span class="fieldSet">
					<input type="hidden" id="doctorAreaId" name="doctorAreaId"  value="${(doctor.area.id)!}" treePath="${(doctor.area.treePath)!}"  />
				</span>
				<span >
					${doctor.birthplaceAddress}
				</span>
			</td>
		</tr>
	[#else]
		<tr class="title">
			<th style= "text-align: center; color: red;">
				联系人信息读取失败
			</th>
		</tr>
	[/#if]
	</table>
	<!--联系人信息end-->
	<!--认证状态start-->
	<form action="updateIsAuthentication.jhtml" method="post">
	<input type="hidden" name="id" value="${mechanism.id}" />
	<table class="input tabContent">
		<tr>
			<th>
				认证状态:
			</th>
			<td>
				<select name="isAuthentication">
						<option value="">未认证</option>
						<option value="true" >认证通过</option>
						<option value="false" >认证失败</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>
				原因说明:
			</th>
			<td>
				<textarea name="remark" class="" style="width: 46.5%; height: 200px;"></textarea>
			</td>
		</tr>
		<tr>
			<th>
				&nbsp;
			</th>
			<td>
				<input type="submit" class="button" value="确&nbsp;&nbsp;定">
				<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="location.href='list.jhtml'">
			</td>
		</tr>
		
	</table>
	</form>
	<!--认证状态end-->
	<!--服务状态start-->
	<form action="updateServerStatus.jhtml" method="post">
	<input type="hidden" name="id" value="${mechanism.id}" />
	<table class="input tabContent">
		<tr>
			<th>
				服务状态:
			</th>
			<td>
				<select name="serverStatus">
				[#list serverStatus as serverStatu]
						<option value="${serverStatu}">${message("Mechanism.ServerStatus."+serverStatu)}</option>
				[/#list]
				</select>
			</td>
		</tr>
		<tr>
			<th>
				结束设置:
			</th>
			<td>
				<input type="text" id="endDate" name="endDate" class="text Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate: '%y-%M-%d'});">
			</td>
		</tr>
		<tr>
			<th>
				原因说明:
			</th>
			<td>
				<textarea name="remark" class="" style="width: 46.5%; height: 200px;"></textarea>
			</td>
		</tr>
		<tr>
			<th>
				&nbsp;
			</th>
			<td>
				<input type="submit" class="button" value="确&nbsp;&nbsp;定">
				<input type="button" class="button" value="返&nbsp;&nbsp;回" onclick="location.href='list.jhtml'">
			</td>
		</tr>
		
	</table>
	</form>
	<!--服务状态end-->
	<!--操作记录start-->
	<table class="input tabContent">
		<tr class="title">
			<th>
				操作人
			</th>
			<th>
				动作
			</th>
			<th>
				备注
			</th>
			<th>
				操作时间
			</th>
		</tr>
		
		[#list mechanismLogs as mechanismLog]
			<tr>
				<td>
					[#if mechanismLog.operator??] ${mechanismLog.operator.name} [#else] - [/#if]
				</td>
				<td>
					${mechanismLog.action}
				</td>
				<td>
					${mechanismLog.remark}
				</td>
				<td>
					${mechanismLog.createDate?string("yyyy-MM-dd HH:mm:ss")}
				</td>
			</tr>
		[/#list]
		[#if mechanismLogs.size()==0]
		<tr>
			<td colspan = "4" style= "text-align: center; color: red;">
				暂无操作记录
			</td>
		</tr>
		[/#if]
		
	</table>
	<!--操作记录end-->
</body>
</html>