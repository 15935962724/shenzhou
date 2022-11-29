<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("Common.Main.title")}</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/newmechanism/css/iconfont.css" rel="stylesheet" type="text/css">
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
<script>
	function switch_tag(id,select_tag,flag)
		{
			if(flag=="over")
				{
					if (id != select_tag)
						{
							$("#tag_" + id).removeClass("z12575757");
							$("#tag_" + id).removeClass("bgffffff");
							$("#tag_" + id).addClass("z12ffffff"); 
							$("#tag_" + id).addClass("bg279fff"); 
						}
				}
			else
				{
					if(id != select_tag)
						{
							$("#tag_" + id).addClass("z12575757");
							$("#tag_" + id).addClass("bgffffff");
							$("#tag_" + id).removeClass("z12ffffff"); 
							$("#tag_" + id).removeClass("bg279fff"); 
						}
				}
		}
	$(function(){
		$("#flag_1 :radio").click(function(){
			$("#rehstate").html($(this).val());
			$('#flag_1').css('display','none');
		})
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
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">患者明细</td>
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
									<td class="btle3e3e3 bre3e3e3" colspan="2" height="50" align="center">患者信息</td>
								</tr>
								<tr>
									<td class="btle3e3e3 plr20" align="left" height="50" width="50%">
										姓　　名：${patientMember.name} ${patientMember.relationship.title} ${message("PatientMechanism.HealthType."+patientMechanism.healthType)}
									</td>
									<td class="btle3e3e3 bre3e3e3 plr20" align="left" width="50%">
										性　　别：${message("Member.Gender."+patientMember.gender)}
									</td>
								</tr>
								<tr class="bge7f4ff">
									<td class="btle3e3e3 plr20" align="left" height="50" width="50%">
										出生日期：${patientMember.birth?string("yyyy-MM-dd")}
									</td>
									<td class="btle3e3e3 bre3e3e3 plr20" align="left" width="50%">
										建档时间：${patientMember.createDate?string("yyyy-MM-dd HH:mm:ss")}
									</td>
								</tr>
								<tr>
									<td class="btle3e3e3 plr20" align="left" height="50" width="50%">
										民　　族：${patientMember.nation}
									</td>
									<td class="btle3e3e3 bre3e3e3 plr20" align="left" width="50%">
										联系电话：${patientMember.mobile}
									</td>
								</tr>
								<tr class="bge7f4ff">
									<td class="btle3e3e3 bbe3e3e3 plr20" align="left" height="50" width="50%">
										身份证号：${patientMember.cardId}
									</td>
									<td class="btle3e3e3 bre3e3e3 bbe3e3e3 plr20" align="left" width="50%">
										医保卡号：${patientMember.medicalInsuranceId}
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
									<td class="btle3e3e3 bre3e3e3" colspan="2" height="50" align="center">联系人信息</td>
								</tr>
								<tr>
									<td class="btle3e3e3 plr20" align="left" height="50" width="50%">
										姓　　名：${patientMember.parent.name}
									</td>
									<td class="btle3e3e3 bre3e3e3 plr20" align="left" width="50%">
										性　　别：${message("Member.Gender."+patientMember.parent.gender)}
									</td>
								</tr>
								<tr class="bge7f4ff">
									<td class="btle3e3e3 plr20" align="left" height="50" width="50%">
										出生日期：${patientMember.parent.birth?string("yyyy-MM-dd")}
									</td>
									<td class="btle3e3e3 bre3e3e3 plr20" align="left" width="50%">
										身份证号：${patientMember.parent.cardId}
									</td>
								</tr>
								<tr>
									<td class="btle3e3e3 plr20" align="left" height="50" width="50%">
										民　　族：${patientMember.parent.nation}
									</td>
									<td class="btle3e3e3 bre3e3e3 plr20" align="left" width="50%">
										联系电话：${patientMember.parent.mobile}
									</td>
								</tr>
								<tr class="bge7f4ff">
									<td class="btle3e3e3 plr20" align="left" height="50" width="50%">
										Ｑ&nbsp;&nbsp;Ｑ：${patientMember.parent.cardQQ}
									</td>
									<td class="btle3e3e3 bre3e3e3 plr20" align="left" width="50%">
										微　　信：${patientMember.parent.cardWX}
									</td>
								</tr>
								<tr>
									<td class="btle3e3e3 plr20" align="left" height="50" width="50%">
										地　　址：${patientMember.parent.area.fullName}${patientMember.parent.address}
									</td>
									<td class="btle3e3e3 bre3e3e3 plr20" align="left" width="50%">
										账户余额：${currency(balance(mechanism.id,patientMember.parent.id))}元
									</td>
								</tr>
								<tr class="bge7f4ff">
									<td class="btle3e3e3 bbe3e3e3 plr20" align="left" height="50" width="50%">
										注册时间：${patientMember.parent.createDate?string("yyyy-MM-dd HH:mm:ss")}
									</td>
									<td class="btle3e3e3 bre3e3e3 bbe3e3e3 plr20" align="left" width="50%">
										
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>

</body>
</html>