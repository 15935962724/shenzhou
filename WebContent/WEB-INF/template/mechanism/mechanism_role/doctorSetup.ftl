<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("Haokanghu.Main.tile")}</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>

</head>
<body>
	<table cellpadding="0" cellspacing="0" border="0" class="k_1 mtb30" id="page_nav">
		<tr>
			
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="k_2">
		<tr>
			<td class="pa20">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">用户管理</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							<a href="list.jhtml">返回</a>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td colspan="2">
							<form id="inputForm" action="updateDoctorSetup.jhtml" method="post" enctype="multipart/form-data" >
							<input type = "hidden" name = "mechanismRoleId" value = "${mechanismRole.id}"/>
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z14444444">
								<tr>
									<td height="50" width="120" align="right" valign="top">角色名称：</td>
									<td valign="top">
										${mechanismRole.name}
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right" valign="top">角色描述：</td>
									<td valign="top">
										${mechanismRole.description}
									</td>
								</tr>
								<tr>
									<td width="120" align="right" valign="top">用户设置：</td>
									<td id="pb" valign="top">
										<ul>
										[#list doctorMechanismRelations as doctorMechanismRelation]
											[#if !doctorMechanismRelation.isSystem]
											<li><input type="checkbox" [#if doctorMechanismRelation.mechanismroles?seq_contains(mechanismRole)]checked="checked"[/#if] name="doctorIds" value = "${doctorMechanismRelation.doctor.id}">${doctorMechanismRelation.doctor.name}</li>
											[/#if]
										[/#list]
											
										</ul>
									</td>
								</tr>
								<tr>
									<td colspan="2" height="20"></td>
								</tr>
								<tr>
									<td height="50" width="120" align="right"></td>
									<td>
										<input type="submit" value="保存" class="button_3 ml20">
										&nbsp;　&nbsp;
										<input type="button" value="重置" class="button_4 z14fefefe" onClick="$('#inputForm')[0].reset();">
									</td>
								</tr>
							</table>
							</form>
						</td>
					</tr>
					<tr>
						<td></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
</body>
</html>