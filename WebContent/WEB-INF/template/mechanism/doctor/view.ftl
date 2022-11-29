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
<script>
$(function(){

    var audit = '${doctorMechanismRelation.audit}';
    var fals = '${doctorMechanismRelation.isAbout}';
    audits(audit);
	appo(fals);

	function appo(fals){
		if(fals)
			{
				$("#target").show();
			}
		else
			{
				$("#target").hide();
			}
	}
	
	$("#appo").click(function(){
			appo($(this).is(':checked'));
	})
	
	function audits(audit){
		if(audit=='succeed'){
			$("#isAbout").show();
		}else{
			$("#isAbout").hide();
		}
	}
	
	
	$("#audit").click(function(){
		audits($(this).val());
	})
})
</script>

<style>
	#pb li{width: 285px;margin-left: 0px;margin-right: 20px; line-height: 20px;}
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
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">员工明细</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							<a href="list.jhtml">返回</a>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td width="300" valign="top">
							<table cellpadding="0" cellspacing="0" border="0" width="260" class="k_1 mtb10 btlr10 bblr10">
								<tr>
									<td height="30" class="btlr10 bg279fff"></td>
								</tr>
								<tr>
									<td class="pa20">
										<table cellpadding="0" cellspacing="0" border="0" width="100%">
											<tr>
												<td align="center" height="170">
													<img onerron = "this.src='${base}/resources/mechanism/images/add_img.png'" src="${doctorMechanismRelation.doctor.logo}" class="w150 h150 bad9d9d9">
												</td>
											</tr>
											<tr>
												<td class="z20279fff" align="center">${doctorMechanismRelation.doctor.name} <span class="z14279fff">${message("Member.Gender."+doctorMechanismRelation.doctor.gender)}</span></td>
											</tr>
											<tr>
												<td height="10"></td>
											</tr>
											<tr>
												<td class="bge6f3ff ptb10 z12444444">
													<table cellpadding="0" cellspacing="0" border="0" width="220">
														<tr>
															<td align="center" width="80">员工角色</td>
															<td width="1" bgcolor="#d5d4d4"></td>
															<td align="center">
															[#if doctorMechanismRelation.mechanismroles.size()>0] 
																[#list doctorMechanismRelation.mechanismroles as mechanismrole]${mechanismrole.name}[#break][/#list]
															[#else] 
																暂无分配角色 
															[/#if]
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td height="10"></td>
											</tr>
											<tr>
												<td class="bge6f3ff ptb10 z12444444">
													<table cellpadding="0" cellspacing="0" border="0" width="220">
														<tr>
															<td align="center" width="80">员工状态</td>
															<td width="1" bgcolor="#d5d4d4"></td>
															<td align="center">${message("DoctorMechanismRelation.Audit."+doctorMechanismRelation.audit)}</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td height="10"></td>
											</tr>
											<tr>
												<td class="bge6f3ff ptb10 z12444444">
													<table cellpadding="0" cellspacing="0" border="0" width="220">
														<tr>
															<td align="center" width="80">联系电话</td>
															<td width="1" bgcolor="#d5d4d4"></td>
															<td align="center">${doctorMechanismRelation.doctor.mobile}</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td height="10"></td>
											</tr>
											<tr>
												<td class="bge6f3ff ptb10 z12444444">
													<table cellpadding="0" cellspacing="0" border="0" width="220">
														<tr>
															<td align="center" width="80">加入时间</td>
															<td width="1" bgcolor="#d5d4d4"></td>
															<td align="center">${doctorMechanismRelation.createDate?string("yyyy年MM月dd日")}</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td height="20" class="bblr10 bg279fff"></td>
								</tr>
							</table>
						</td>
						<td valign="top">
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="mtb10">
								<tr class="bg279fff z14ffffff">
									<td class="btle3e3e3 bre3e3e3" align="center" height="50" colspan="2">基本信息</td>
								</tr>
								<tr>
									<td class="btle3e3e3 plr20" height="50" width="50%">
										出生日期：　[#if doctorMechanismRelation.doctor.birth??] ${doctorMechanismRelation.doctor.birth?string("yyyy-MM-dd")} [/#if] 
									</td>
									<td class="btle3e3e3 bre3e3e3 plr20" width="50%">
										身份证号：　${doctorMechanismRelation.doctor.entityCode} 
									</td>
								</tr>
								<tr class="bge7f4ff">
									<td class="btle3e3e3 plr20" height="50" width="50%">
										户籍地址：[#if doctorMechanismRelation.doctor.birthplace??] ${doctorMechanismRelation.doctor.birthplace.fullName} [/#if]　${doctorMechanismRelation.doctor.birthplaceAddress}
									</td>
									<td class="btle3e3e3 bre3e3e3 plr20" width="50%">
										现住地址：[#if doctorMechanismRelation.doctor.area??] ${doctorMechanismRelation.doctor.area.fullName} [/#if]　${doctorMechanismRelation.doctor.addrss}
									</td>
								</tr>
								<tr>
									<td class="btle3e3e3 plr20" height="50" width="50%">
										员工职级：　${doctorMechanismRelation.doctor.doctorCategory.name} <span class="z12999999">(已认证)</span>
									</td>
									<td class="btle3e3e3 bre3e3e3 plr20" width="50%">
										从业年限：　${doctorMechanismRelation.doctor.year}年
									</td>
								</tr>
								<tr class="bge7f4ff">
									<td class="btle3e3e3 bre3e3e3 pa20" colspan="2">
										<table cellpadding="0" cellspacing="0" border="0" width="100%" id="pb">
											<tr>
												<td width="85" valign="top">服务项目：</td>
												<td>
													<ul>
													[#if doctorMechanismRelation.doctor.projects.size()>0]
														[#list doctorMechanismRelation.doctor.projects as project]
															<li>${project.name}<span class="z12999999">(提成：30%)</span></li>
														[/#list]
													[#else]
														<li>暂无项目</li>
													[/#if]
													</ul>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td class="btle3e3e3 bre3e3e3 plr20 ptb10" colspan="2">
										用户评分：<br>
										<table width="612" border="0" cellspacing="0" cellpadding="0" class="pif ml20">
										  <tr>
											<td width="204" height="30" colspan="3" align="left"><span style="margin: 0px">综合得分</span><span class="pf_span"><div class="pf_bg" style="width:${doctorMechanismRelation.doctor.scoreSort*10}%;"></div><img src="${base}/resources/mechanism/images/x_1.png" border="0" class="pa" /></span><span>${doctorMechanismRelation.doctor.scoreSort}分</span></td>
										  </tr>
										  <tr>
											<td width="204" height="30"><span style="margin: 0px;">技能评价</span><span class="pf_span"><div class="pf_bg" style="width:${doctorMechanismRelation.doctor.skillSort*10}%;"></div><img src="${base}/resources/mechanism/images/x_1.png" border="0" class="pa" /></span><span>${doctorMechanismRelation.doctor.skillSort}分</span></td>
											<td width="204"><span>服务能力</span><span class="pf_span"><div class="pf_bg" style="width:${doctorMechanismRelation.doctor.serverSort*10}%;"></div><img src="${base}/resources/mechanism/images/x_1.png" border="0" class="pa" /></span><span>${doctorMechanismRelation.doctor.serverSort}分</span></td>
											<td width="204"><span>沟通水平</span><span class="pf_span"><div class="pf_bg" style="width:${doctorMechanismRelation.doctor.communicateSort*10}%;"></div><img src="${base}/resources/mechanism/images/x_1.png" border="0" class="pa" /></span><span>${doctorMechanismRelation.doctor.communicateSort}分</span></td>
										  </tr>
										</table>									
									</td>
								</tr>
								<tr class="bge7f4ff">
									<td class="btle3e3e3 bre3e3e3 plr20 ptb10" colspan="2">
										个人简介：　${doctorMechanismRelation.doctor.introduce}
									</td>
								</tr>
							</table>
							<form action="${base}/mechanism/doctorMechanismRelation/update_audit.jhtml" method="post">
							<input type="hidden" id="p_id" name="doctorMechanismRelationId" value="${doctorMechanismRelation.id}">
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="mtb20">
								<tr class="bg279fff z14ffffff pr">
									<td class="btle3e3e3 bre3e3e3" align="center" height="50" colspan="2">状态管理<input type="submit" value="保存" class="bae1e1e1 w40 bgffffff z12444444" style="position: absolute;right: 50px;"></td>
								</tr>
								<tr>
									<td class="btle3e3e3 plr20" height="50" width="50%">
										<table cellpadding="0" cellspacing="0" border="0" width="100%" class="mtb10">
											<tr>
												<td width="85">审核状态：</td>
												<td>
													<select id="audit" name="audit" class="h30 w100 bae1e1e1">
														<option value="">全部</option>
														[#list doctorMechanismRelationAudits as audit]
															<option [#if doctorMechanismRelation.audit == audit]selected[/#if] value="${audit}">
																${message("DoctorMechanismRelation.Audit." + audit)}
															</option>
														[/#list]
													</select>
												</td>
											</tr>
											<tr>
												<td width="85">医生职级：</td>
												<td>
													<select id="doctorCategoryId" name="doctorCategoryId" class="h30 w100 bae1e1e1">
														<option value="">全部</option>
														[#list mechanism.doctorCategorys as doctorCategory] 
						   									<option [#if doctorMechanismRelation.doctorCategory == doctorCategory] selected [/#if] value="${doctorCategory.id}">${doctorCategory.name}</option>
						   								[/#list]
													</select>
												</td>
											</tr>
											<tr>
												<td width="85">原因说明：</td>
												<td>
													<textarea name="statusExplain" class="z14717171 inputkd9d9d9bgf6f6f6 w213 mtb10 h40"></textarea>
												</td>
											</tr>
										</table>
									</td>
									<td class="btle3e3e3 bre3e3e3 plr20" width="50%" valign="top">
										<table id = "isAbout" cellpadding="0" cellspacing="0" border="0" width="100%" class="mtb10">
											<tr>
												<td width="85">开通预约：</td>
												<td>
													<input type="checkbox" [#if doctorMechanismRelation.isAbout] checked [/#if] id="appo" name="isAbout"> 设置该员工患者端为可预约
												</td>
											</tr>
											<tr>
												<td width="85"></td>
												<td>
													<table cellpadding="0" cellspacing="0" border="0" width="100%" class="mtb10 dpn" id="target">
														<tr>
															<td height="25">
																　　日工作目标：<input type="text" value="${doctorMechanismRelation.workTarget.dayWorkTarget}" name="dayWorkTarget" class="z14717171 inputkd9d9d9bgf6f6f6 h20 w80" value="1000"> <span class="z12939393">元/日</span>
															</td>
														</tr>
														<tr>
															<td height="25">
																　　提成   占比：<input type="text" value="${doctorMechanismRelation.workTarget.percentage}" name="percentage" class="z14717171 inputkd9d9d9bgf6f6f6 h20 w80" value="30"> <span class="z12939393">%</span>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr class="bge7f4ff">
									<td class="btle3e3e3 plr20" height="50" width="50%">
										角色设置：　<select name = "mechanismRoleId" class="h30 w100 bae1e1e1">
										    [#list mechanism.mechanismRoles as mechanismrole]
										    	<option [#if doctorMechanismRelation.mechanismroles?seq_contains(mechanismrole)] selected [/#if] value = "${mechanismrole.id}">${mechanismrole.name}</option>
										    [/#list]
										</select>
									</td>
								</tr>
							</table>
							</form>
						</td>
					</tr>
					<tr>
						<td colspan="2"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
	
</body>
</html>