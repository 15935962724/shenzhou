<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>患者详情 - Powered By HaoKangHu</title>
<meta name="author" content="HaoKangHu Team" />
<meta name="copyright" content="HaoKangHu" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/js.js"></script>
<script type="text/javascript">
$().ready(function() {
	
	var $inputForm = $("#inputForm");
	
	[@flash_message /]
	
	$('.visitMessageDelete').click(function(){
		var id = $(this).attr("data_id");
		
			if(confirm("确定要删除吗？")){
				$.ajax({ 
				url: "delete_visit_message.jhtml",
				type: "POST",
				data: {
					"id":id
				},
				datatype:"text",
				cache: false,
				success: function(data){
				 $.message("删除成功!");
				window.location.reload();
		    	},
		    	error:function(message){
		    	alert(message);
		    	}
		    });
		}
		
	});
	
});

</script>
</head>
<body>
	
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 查看患者
	</div>
	<ul id="tab" class="tab">
		<li>
			<input type="button" value="基本信息" />
		</li>
		<li>
			<input type="button" value="健康档案" />
		</li>
		<li>
			<input type="button" value="预约信息" />
		</li>
		<li>
			<input type="button" value="回访信息" />
		</li>
		<li>
			<input type="button" value="医师信息" />
		</li>
	</ul>
	<!--基本信息start-->
	<table class="input tabContent">
		<tr>
			<td>
				&nbsp;
			</td>
			<td>
				患者信息
			</td>
			<td>
				&nbsp;
			</td>
			<td>
				&nbsp;
			</td>
		</tr>
		<tr>
			<th>
				姓名:
			</th>
			<td width="360">
				${patient.name}
			</td>
			<th>
				出生日期:
			</th>
			<td>
				${patient.birth?string("yyyy-MM-dd")}
			</td>
		</tr>
		<tr>
			<th>
				性别:
			</th>
			<td>
				${message("Member.Gender."+patient.gender)}
			</td>
			<th>
				建档日期:
			</th>
			<td>
				${patient.createDate?string("yyyy-MM-dd HH:mm:ss")}
			</td>
		</tr>
		<tr>
			<th>
				民族:
			</th>
			<td>
				${patient.nation}
			</td>
			<th>
				联系电话:
			</th>
			<td>
				${patient.mobile}
			</td>
		</tr>
		<tr>
			<th>
				与监护人关系:
			</th>
			<td>
				${patient.relationship.title}
			</td>
			<th>
				身份证:
			</th>
			<td>
				${patient.cardId}
			</td>
		</tr>
		<tr>
			<th>
				状态:
			</th>
			<td>
				${message("Member.HealthType."+patient.healthType)}
			</td>
			<th>
				医保卡号:
			</th>
			<td>
				${(patient.medicalInsuranceId)!"-"}
			</td>
		</tr>
		<tr>
			<td>
				&nbsp;
			</td>
			<td>
				监护人信息
			</td>
			<td>
				&nbsp;
			</td>
			<td>
				&nbsp;
			</td>
		</tr>
		<tr>
			<th>
				姓名：
			</th>
			<td>
				${patient.parent.name}
			</td>
			<th>
				出生日期:
			</th>
			<td>
				${patient.parent.birth?string("yyyy-MM-dd")}
			</td>
		</tr>
		<tr>
			<th>
				性别:
			</th>
			<td>
				${message("Member.Gender."+patient.parent.gender)}
			</td>
			<th>
				身份证:
			</th>
			<td>
				${patient.parent.cardId}
			</td>
		</tr>
		<tr>
			<th>
			         民族:
			</th>
			<td>
				${patient.parent.nation}
			</td>
			<th>
				联系电话:
			</th>
			<td>
				${patient.parent.mobile}
			</td>
		</tr>
		<tr>
			<th>
				QQ:
			</th>
			<td>
				${patient.parent.cardQQ}
			</td>
			<th>
				微信:
			</th>
			<td>
				${patient.parent.cardWX}
			</td>
		</tr>
		<tr>
			<th>
				户籍地址:
			</th>
			<td>
				${patient.parent.area.fullName}${patient.parent.areaAddress}
			</td>
			<th>
				余额:
			</th>
			<td>
				${currency(patient.parent.balance,true)}
			</td>
		</tr>
		<tr>
			<th>
				注册日期:
			</th>
			<td>
				${patient.parent.createDate?string("yyyy-MM-dd HH:mm:ss")}
			</td>
			<th>
				
			</th>
			<td>
				
			</td>
		</tr>
	</table>
	<!--基本信息end-->
	<!--健康档案start-->
	<table class="input tabContent" width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
		    <tr>
		      <td height="40" align="right" class="z_14_4c98f6_1">
  			      <a href="javascript:;">更多病例</a>
		      </td>
	        </tr>
	        [#list patient.assessReports as assessReport]
		    <tr onclick="show_info('${assessReport.id}','1')">
		      <td style="border-bottom: 0px;">
  			      <table width="100%" border="0" cellspacing="0" cellpadding="0">
  			        <tbody>
  			          <tr>
  			            <td height="60"><span class="z_20_4c98f6_1">${assessReport.diseaseName}</span><br>${assessReport.mechanism.name}</td>
  			            <td align="right">诊评人：${assessReport.doctor.name}<br>${assessReport.createDate?string("yyyy-MM-dd")}</td>
		              </tr>
		            </tbody>
		          </table>
	          </td>
	        </tr>
	        
		    <tr class="d_n_1" id="info_${assessReport.id}" style="display: none;">
		      <td class="b_l_r_b_cccccc_1 back_f6faff_1">
		      	<table cellpadding="0" cellspacing="0" border="0" width="98%" class="m_a_10_1">
		      		<tr>
		      			<td valign="top" class="z_14_4c98f6_1" width="70">病情描述：</td>
		      			<td>${assessReport.nowExplain}</td>
		      		</tr>
		      		<tr>
		      		  <td valign="top" class="z_14_4c98f6_1">诊评结果：</td>
		      		  <td>${assessReport.assessResult}</td>
	      		  </tr>
		      	  <tr>
		      		  <td valign="top" class="z_14_4c98f6_1">康复建议：</td>
		      		  <td>${assessReport.proposal}</td>
	      		  </tr>
	      		  <tr>
		      		  <td valign="top">康复图片：</td>
		      		  <td>
  			      		  [#list assessReport.assessReportImages as assessReportImage]
  			      		  	<a href="${assessReportImage.source}" target="_blank"><img onerror="this.src='${base}/resources/mechanism/images/tmp_3.jpg'" src="${assessReportImage.source}" class="img_b_ccc_m_r_20_1"></a>
  			      		  [/#list]
		      		  </td>
	      		  </tr>
		      	  <tr>
		      		  <td valign="top" class="z_14_4c98f6_1">查阅权限：</td>
		      		  <td>
			      		  [#list assessReport.doctorAssessReport as doctorAssessReport]
		  			      		   [#if doctorAssessReport.whetherAllow == "allow"]
		  			      		   		${doctorAssessReport.doctor.name}
		  			      		   [/#if]
			      		  [/#list] 
		      		  </td>
	      		  </tr>
		      	  <tr>
		      		  <td valign="top" class="z_14_4c98f6_1">其他计划：</td>
		      		  <td class="z_14_e60010_1"><a href="javascript:;" onclick="disp_hidden_div('layer',470,370,${assessReport.id});">点击查阅</a></td>
	      		  </tr>
		      	</table>
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="b_t_cccccc_1" style="border-bottom: 0px;">
							<table cellpadding="0" cellspacing="0" border="0" width="96%" align="right">
							[#list assessReport.recoveryPlans as recoveryPlan]
								<tr class="c_1" onclick="show_info('${assessReport.id}_${recoveryPlan.id}','1')">
									<td valign="bottom" height="45" class="p_b_10_1 b_b_cccccc_1">康护计划<br><font class="z_12_999999_1">[#if recoveryPlan.recoveryDoctor??]${recoveryPlan.recoveryDoctor.name}[/#if]：${recoveryPlan.createDate?string("yyyy-MM-dd")}</font></td>
									<td align="right" valign="bottom" class="z_12_999999_1 p_b_10_1 b_b_cccccc_1">2017年5月22日——2017年9月26日　&gt;　　　&nbsp;</td>
								</tr>
								<tr id="info_${assessReport.id}_${recoveryPlan.id}" class="d_n_1" style="display: none;">
									<td colspan="2" style="border-bottom: 0px;">
										<table cellpadding="0" cellspacing="0" border="0" width="95%" align="right" class="m_t_10_1">
											<tr>
												<td valign="top" class="z_14_4c98f6_1" align="right" width="70">计 划 人：</td>
												<td valign="top">${recoveryPlan.recoveryDoctor.name}</td>
											</tr>
											<tr>
												<td valign="top" class="z_14_4c98f6_1" align="right">计划周期：</td>
												<td valign="top">（${recoveryPlan.startTime}——${recoveryPlan.endTime}）</td>
											</tr>
											<tr>
												<td valign="top" class="z_14_4c98f6_1" align="right">康复目标：</td>
												<td valign="top">${recoveryPlan.summary}</td>
											</tr>
											<tr>
												<td valign="top" class="z_14_4c98f6_1" align="right">康复计划：</td>
												<td valign="top">${recoveryPlan.recoveryProject}</td>
											</tr>
											<tr>
												<td valign="top" class="z_14_4c98f6_1" align="right">短期目标：</td>
												<td valign="top">${recoveryPlan.shortTarget}</td>
											</tr>
											<tr>
												<td valign="top" class="z_14_4c98f6_1" align="right">长期目标：</td>
												<td valign="top">${recoveryPlan.longTarget}</td>
											</tr>
											<tr>
												<td valign="top" class="z_14_4c98f6_1" align="right">审核状态：</td>
												<td valign="top">${message("RecoveryPlan.CheckState."+recoveryPlan.checkState)}</td>
											</tr>
											<tr>
												<td valign="top" class="z_14_4c98f6_1" align="right">审  核  人：</td>
												<td valign="top">${assessReport.auditDoctor.name} (${assessReport.createDate?string("yyyy-MM-dd HH:mm:ss")})</td>
											</tr>
											<tr>
												<td valign="top" class="z_14_4c98f6_1" align="right">原因说明：</td>
												<td valign="top">无</td>
											</tr>
										</table>
										<table cellpadding="0" cellspacing="0" border="0" width="100%">
											<tr>
												<td height="45" class="p_l_r_20_1 b_t_b_cccccc_1">康护记录</td>
											</tr>
											<tr>
												<td style="border-bottom: 0px;">
													<table cellpadding="0" cellspacing="0" border="0" width="95%" align="right" class="m_t_20_1">
														[#list recoveryPlan.recoveryRecordList as recoveryRecord]
															<tr>
																<td valign="top" width="16" class="back_d_1"><img src="${base}/resources/mechanism/images/q_2.png"></td>
																<td valign="top" class="p_l_r_20_1 p_b_10_1">${recoveryRecord.createDate?string("yyyy-MM-dd HH:mm:ss")}<br><font class="z_14_999999_1">${recoveryRecord.recoveryContent}</font></td>
															</tr>
														[/#list]
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								[/#list]
							</table>
						</td>
					</tr>
				</table>
		      </td>
	        </tr>
		[/#list]	  			   
	</table>
	<!--健康档案end-->
	<!--预约信息start-->
	<table class="input tabContent">
		<tr class="title">
			<th>
				&nbsp;
			</th>
			[#list dateLists as dateDay]
			<th>
				${dateDay.week} <br/> ${dateDay.workDate?string("yyyy-MM-dd")}
			</th>
            [/#list]
		</tr>
		[#list workDates as workDate]
		<tr>
			<td>
				${workDate.workDateTime}:00
			</td>
			[#list dateLists as dateDay]
			<td>
				[#assign  count = 0]
				[#list orders as order]
			     [#if (dateDay.workDate?string("yyyyMMdd")) == (order.orderWorkDay?string("yyyyMMdd")) && (order.orderStartTime == workDate.workDateTime) ]
			            [#assign  count = count + 1]
			     [/#if]
			    [/#list]
			    [#if count > 0] <input type="button" name="button3" id="button3" value="已约" class="button_3 b_4c98f6_1" > [/#if]
			</td>
            [/#list]
			
			
		</tr>
		[/#list]
	</table>
	<!--预约信息end-->
	<!--回访信息start-->
	<table class="input tabContent">
		<tr class="title">
			<th>
				回访人
			</th>
			<th>
				被访人
			</th>
			<th>
				回访时间
			</th>
			<th>
				建立时间
			</th>
			<th>
				回访反馈
			</th>
			<th>
				操作
			</th>
		</tr>
		[#list visitMessagePage.content as visitMessage ]
			<tr>
				<td>
				${visitMessage.doctor.name}
				</td>
				<td>
				${visitMessage.member.name}
				</td>
				<td>
				${visitMessage.visitDate?string("yyyy-MM-dd HH:mm:ss")}
				</td>
				<td>
				${visitMessage.createDate?string("yyyy-MM-dd HH:mm:ss")}
				</td>
				<td>
				${visitMessage.message}
				</td>
				<td>
				<a href="javascript:;" data_id="${visitMessage.id}" class="visitMessageDelete" >[删除]</a>
				</td>
			</tr>
		[/#list]
	</table>
	<!--回访信息end-->
	<!--医生信息start-->
	<table class="input tabContent">
		<tr class="title">
			<th>
				医生姓名
			</th>
			<th>
				性别
			</th>
			<th>
				级别
			</th>
			<th>
				身份证
			</th>
			<th>
				加入时间
			</th>
			<th>
				操作
			</th>
		</tr>
		 [#list patient.patientDoctor as doctor]
			<tr>
				<td>
				${doctor.name}
				</td>
				<td>
				${message("Member.Gender."+doctor.gender)}
				</td>
				<td>
				${doctor.doctorCategory.name}
				</td>
				<td>
				${doctor.entityCode}
				</td>
				<td>
				${doctor.createDate?string("yyyy-MM-dd HH:mm:ss")}
				</td>
				<td>
				<a href="javascript:;" data_id="${doctor.id}">[暂无]</a>
				</td>
			</tr>
		[/#list]
	</table>
	<!--医生信息end-->
</body>
</html>