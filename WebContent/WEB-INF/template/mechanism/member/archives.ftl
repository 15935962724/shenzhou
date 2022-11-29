<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("Common.Main.title")}</title>

<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/js.js"></script>
<script type="text/javascript" >

function disp_hidden_div(d_id,d_width,d_height,id)
	{
		if((typeof(d_width) != "undefined") && (typeof(d_height) !="undefined"))
			{
				var w=d_width / 2;
				var h=d_height / 2;
				$("."+d_id).css({'width':d_width + "px","height": d_height + "px","left":"50%","top":($(parent.document).scrollTop()+50) + "px","margin-left": "-" + w + "px"});
				$(".d_l_1").css({"width":(d_width-40) + "px","height": (d_height-40) + "px"});
				if($(parent.document).height()<(d_height+$(parent.document).scrollTop()))
					$("#main_ifr",parent.document).height((parseInt(d_height)+$(parent.document).scrollTop()+150)+"px");
			}
		lock_Scroll();
		$("#"+d_id).toggle();	
		append_div(d_id,id);
		
	}

function append_div(d_id,id){
       var dlv = $("."+d_id);
       dlv.empty();
        $.ajax( {     
                "type": "POST",  
                "url": 'view.jhtml',  
                "contentType": "application/x-www-form-urlencoded; charset=utf-8",   
                "data": {  
                    id:id  
                },  
                success: function(data) {   
                	
                    //将数据转换成对象  
                    var dataObj = eval('('+data+')');  
                    var html = '';
                    
						html += '<div class="d_l_1">'; 
							html += '<table cellpadding="0" cellspacing="0" border="0" width="95%" align="right">'; 
								for(var i = 0; i<dataObj.length; i++){
	                       			html += '<tr>'; 
										html += '<td valign="top" class="z_14_4c98f6_1" align="right" width="70">计 划 人：</td>'; 
										html += '<td valign="top">'+dataObj[i].recoveryDoctor.name+'</td>'; 
									html += '</tr>'; 
									html += '<tr>'; 
										html += '<td valign="top" class="z_14_4c98f6_1" align="right">计划周期：</td>'; 
										html += '<td valign="top">（'+dataObj[i].startTime+'——'+dataObj[i].endTime+'）</td>'; 
									html += '</tr>'; 
									html += '<tr>'; 
										html += '<td valign="top" class="z_14_4c98f6_1" align="right">康复目标：</td>'; 
										html += '<td valign="top">'+dataObj[i].summary+'</td>'; 
									html += '</tr>'; 
									html += '<tr>'; 
										html += '<td valign="top" class="z_14_4c98f6_1" align="right">康复计划：</td>'; 
										html += '<td valign="top">'+dataObj[i].recoveryProject+'</td>'; 
									html += '</tr>'; 
									html += '<tr>'; 
										html += '<td valign="top" class="z_14_4c98f6_1" align="right">短期目标：</td>'; 
										html += '<td valign="top">'+dataObj[i].shortTarget+'</td>'; 
									html += '</tr>'; 
									html += '<tr>'; 
										html += '<td valign="top" class="z_14_4c98f6_1" align="right">长期目标：</td>'; 
										html += '<td valign="top">'+dataObj[i].longTarget+'</td>'; 
									html += '</tr>'; 
									html += '<tr>'; 
										html += '<td valign="top" class="z_14_4c98f6_1" align="right">审核状态：</td>'; 
										html += '<td valign="top">'+ checkStates(dataObj[i].checkState)+'</td>'; 
									html += '</tr>'; 
									html += '<tr>'; 
										html += '<td valign="top" class="z_14_4c98f6_1" align="right">审 核 人：</td>'; 
										html += '<td valign="top">'+dataObj[i].assessReport.auditDoctor.name+'('+getMyDate(dataObj[i].createDate)+')</td>'; 
									html += '</tr>'; 
									html += '<tr>'; 
										html += '<td valign="top" class="z_14_4c98f6_1" align="right">原因说明：</td>'; 
										html += '<td valign="top" >无</td>'; 
									html += '</tr>'; 
									html += '<tr>';
										html += '<td colspan="2"><hr style="border:none;border-bottom:1px dashed #dcdcdc"></td>';
									html += '</tr>';
                    			}
							html += '</table>'; 
						html += '</div>'; 
						html += '<div class="d_cl_1" style="top:13px;position:absolute;right:13px">'; 
							html += '<a href="javascript:;" onclick="disp_hidden_d(\'layer\');">';
								html += '<img src="${base}/resources/mechanism/images/x.png" width="15" border="0" />';
							html += '</a>';
						html += '</div>'; 
                    
                    dlv.append(html);  
                }     
        });  
       
       

}


function checkStates(obj){
   
   var checkStare = '';
   if(obj == "waitCheck"){
   		checkStare = '待审核';
   }
   if(obj == "succeed"){
   		checkStare = '通过';
   }
   if(obj == "defeated"){
   		checkStare = '拒绝';
   }
   
   return checkStare;
}


</script>

</head>
<body>
 
    	<div class="nav">管理导航：
	    	<a href="javascript:;">用户管理首页</a>　
	    	<a href="javascript:;">患者信息</a>　
	    	<a href="javascript:;">预约信息</a>　
	    	<a href="javascript:;">回访信息</a>　
	    	<a href="javascript:;">账户信息</a>　
	    	<a href="javascript:;">医师信息</a>
    	</div>
        <div class="seat">
        	<div class="left_z">患者信息</div>
            <div class="export">
	            <a href="javascript:;"><img src="${base}/resources/mechanism/images/print.png" border="0" class="top_img_b_1" /> 打印</a>
	            <a href="javascript:;"><img src="${base}/resources/mechanism/images/export.png" border="0" class="top_img_b_1" /> 导出</a>
	            <a href="javascript:window.back();"><img src="${base}/resources/mechanism/images/back.png" border="0" class="top_img_b_1" /> 返回</a>
            </div>
        
        </div>
        <div class="detail">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td colspan="2">&nbsp;</td>
          </tr>
          <tr>
            <td width="280" valign="top">
            	<div class="peo_2">
            		<table cellpadding="0" cellspacing="0" border="0">
            			<tr>
            				<td><img onerror = "this.src='${base}/resources/mechanism/images/tmp_2.jpg'" src="${patientMember.logo}" alt="孙志远 主任医师 女" title="孙志远 主任医师 女" class="head_1" /></td>
            			</tr>
            			<tr>
            				<td height="50" class="b_a_dcdcdc_1"><span class="sp_m_1">${patientMember.name}</span><span class="z_12_999999_1">　${message("Member.Gender."+patientMember.gender)}　${age(patientMember.birth)}周岁　${patientMember.nation}</span></td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">联系电话：${patientMember.mobile}</td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">就诊次数：${patientMember.orders.size()}次<span class="z_12_999999_1">　</span>在康护</td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">出生日期：${patientMember.birth?string("yyyy-MM-dd")}</td>
            			</tr>
            		</table>
            	</div>
            </td>
            <td valign="top">
            <table width="99%" align="right" border="0" cellspacing="0" cellpadding="0">
              <tbody>
                <tr>
                  <td height="40">
					<div class="peo_tag_1">
						<ul>
						      <li style="width:20%;"><div class="border_radius_l_1"><a href="${base}/mechanism/member/patient_view.jhtml?patientMemberId=${patientMember.id}">基本信息</a></div><div class="div_x_1"></div></li>
                              <li style="width:20%;"><div class="sel_div_1"><a href="${base}/mechanism/assessReport/archives.jhtml?patientMemberId=${patientMember.id}">健康档案</a></div><div class="div_x_1"></div></li>
                              <li style="width:20%;"><div><a href="${base}/mechanism/member/patient_reserve.jhtml?patientMemberId=${patientMember.id}">预约信息</a></div><div class="div_x_1"></div></li>
                              <li style="width:20%;"><div><a href="${base}/mechanism/visitMessage/patient_visitMessage_list.jhtml?patientMemberId=${patientMember.id}&type=patient">回访信息</a></div><div class="div_x_1"></div></li>
                              <li style="width:20%;"><div class="border_radius_r_1"><a href="${base}/mechanism/member/patient_doctor.jhtml?patientMemberId=${patientMember.id}">医师信息</a></div><div class="div_x_1"></div></li>
						</ul>												
					</div>

               	  </td>
                </tr>
                <tr>
                  <td>
				  	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z_14_707070_1" >
				  		<tr class="fff">
				  			<td height="45" class="b_l_r_b_dcdcdc_1">
				  			<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
				  			  <tbody>
				  			    <tr>
				  			      <td height="40" align="right" class="z_14_4c98f6_1">
					  			      <!--<a href="javascript:;">添加诊治报告</a>--> 
					  			      <a href="javascript:;">更多病例</a>
				  			      </td>
			  			        </tr>
			  			        [#list assessReports as assessReport]
			  			        
				  			    <tr class="c_1" onClick="show_info('${assessReport.id}','1')">
				  			      <td class="b_a_cccccc_1 p_a_10_1">
					  			      <table width="100%" border="0" cellspacing="0" cellpadding="0">
					  			        <tbody>
					  			          <tr>
					  			            <td height="60"><span class="z_20_4c98f6_1">${assessReport.diseaseName}</span><br>${assessReport.doctor.mechanism.name}</td>
					  			            <td align="right">诊评人：${assessReport.doctor.name}<br />${assessReport.createDate?string("yyyy-MM-dd")}</td>
				  			              </tr>
				  			            </tbody>
				  			          </table>
			  			          </td>
			  			        </tr>
				  			    <tr class="d_n_1" id="info_${assessReport.id}">
				  			      <td class="b_l_r_b_cccccc_1 back_f6faff_1">
				  			      	<table cellpadding="0" cellspacing="0" border="0" width="98%" class="m_a_10_1">
				  			      		<tr>
				  			      			<td valign="top" class="z_14_4c98f6_1" width="70">病情描述：</td>
				  			      			<td>${assessReport.nowExplain}</td>
				  			      		</tr>
				  			      		<tr>
				  			      		  <td colspan="2" valign="top" height="10"></td>
			  			      		  </tr>
				  			      		<tr>
				  			      		  <td valign="top" class="z_14_4c98f6_1">诊评结果：</td>
				  			      		  <td>${assessReport.assessResult}</td>
			  			      		  </tr>
				  			      		<tr>
				  			      		  <td colspan="2" valign="top" height="10"></td>
			  			      		  </tr>
				  			      		<tr>
				  			      		  <td valign="top" class="z_14_4c98f6_1">康复建议：</td>
				  			      		  <td>${assessReport.proposal}</td>
			  			      		  </tr>
				  			      		<tr>
				  			      		  <td colspan="2" valign="top" height="10"></td>
			  			      		  </tr>
				  			      	  <tr>
				  			      		  <td valign="top">图片</td>
				  			      		  <td>
					  			      		  [#list assessReport.assessReportImages as assessReportImage]
					  			      		  	<a href="${assessReportImage.source}" target="_blank"><img onerror="this.src='${base}/resources/mechanism/images/tmp_3.jpg'" src="${assessReportImage.source}" class="img_b_ccc_m_r_20_1"></a>
					  			      		  [/#list]
				  			      		  </td>
			  			      		  </tr>
				  			      		<tr>
				  			      		  <td colspan="2" valign="top" height="10"></td>
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
				  			      		  <td colspan="2" valign="top" height="10"></td>
			  			      		  </tr>
				  			      		<tr>
				  			      		  <td valign="top" class="z_14_4c98f6_1">其他计划：</td>
				  			      		  <td class="z_14_e60010_1"><a href="javascript:;" onClick="disp_hidden_div('layer',470,370,${assessReport.id});">点击查阅</a></td>
			  			      		  </tr>
				  			      	</table>
									<table cellpadding="0" cellspacing="0" border="0" width="100%">
										<tr>
											<td class="b_t_cccccc_1">
												<table cellpadding="0" cellspacing="0" border="0" width="96%" align="right">
													[#list assessReport.recoveryPlans as recoveryPlan]
													<tr class="c_1" onClick="show_info('${assessReport.id}_${recoveryPlan.id}','1')">
														<td valign="bottom" height="45" class="p_b_10_1 b_b_cccccc_1">康护计划<br><font class="z_12_999999_1">${recoveryPlan.recoveryDoctor.name}：${recoveryPlan.createDate?string("yyyy-MM-dd")}</font></td>
														<td align="right" valign="bottom" class="z_12_999999_1 p_b_10_1 b_b_cccccc_1">2017年5月22日——2017年9月26日　&gt;　　　&nbsp;</td>
													</tr>
													<tr id="info_${assessReport.id}_${recoveryPlan.id}" class="d_n_1">
														<td colspan="2">
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
																	<td height="10"></td>
																</tr>
																<tr>
																	<td height="45" class="p_l_r_20_1 b_t_b_cccccc_1">康护记录</td>
																</tr>
																<tr>
																	<td>
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
				  			    <tr>
				  			      <td>&nbsp;</td>
			  			        </tr>
			  			      </tbody>
			  			    </table></td>
			  			</tr>
				  	</table>
                  </td>
                </tr>
              </tbody>
            </table></td>
            </tr>
        </table>
        </div> 

<div class="clear"></div>

<div id="layer">
	<div class="layer scroll_y_1">
		
	</div>
</div>

</body>
</html>