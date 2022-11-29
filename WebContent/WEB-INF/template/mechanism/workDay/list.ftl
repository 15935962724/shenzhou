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
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">排班管理</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							<!--<a href="javascript:;">导出</a>-->
							&nbsp;　&nbsp;
							<a href="past.jhtml">以往排班</a>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td colspan="2" class="z14ffffff">
							<table cellpadding="0" cellspacing="0" border="0" align="left" width="100%" >
								<tr>
								    [#list dateDays as dateDay]
								    [#if dateDay_index%10==0&&dateDay_index!=0]</tr><tr height="10"></tr><tr>[/#if]
								    <td height="40" width="10%" [#if (date)?string("yyyyMMdd") == (dateDay.dateDay)?string("yyyyMMdd")]class="bg279fff"[#else]class="bgd5d5d5"[/#if]  align="center"><a data_date="${dateDay.dateDay?string("yyyy-MM-dd")}" href="list.jhtml?date=${dateDay.dateDay?string("yyyy-MM-dd")}">${dateDay.week}</a></td>
									<td width="1" bgcolor="#e1e1e1"></td>
								    [/#list]
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="20"></td>
					</tr>
					<tr>
						<td colspan="2">
							<table cellpadding="0" cellspacing="0" border="0" width="100%" id="pb">
								<tr class="bg279fff z14ffffff">
									<td class="btle3e3e3" align="center" width="200" height="50"></td>
									<td class="btle3e3e3 bre3e3e3" align="center">${date?string("yyyy年MM月dd日")}</td>
								</tr>
								[#list server_time_doctor_list as mechanismServerTime]
									<tr [#if mechanismServerTime_index%2!=0]class="bge7f4ff"[/#if]>
										<td class="btle3e3e3 z14444444" align="center" height="50" valign="middle">
											上午<br>
											${mechanismServerTime.startTime} - ${mechanismServerTime.endTime}
										</td>
										<td class="btle3e3e3 bre3e3e3">
											<ul>
												[#list mechanismServerTime.doctors as doctor]
													<li>${doctor.name}</li>
												[/#list]
											</ul>
										</td>
									</tr>
								[/#list]
							</table>
						</td>
					</tr>
					<tr >
						<td colspan="2" class = "bte3e3e3"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
 
</body>
</html>