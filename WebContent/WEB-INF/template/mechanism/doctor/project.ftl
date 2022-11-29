<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/js.js"></script>

<script type="text/javascript">
 function udpateAudit()  {
     	var audit = $('#flag').val();
     	var id = $('#p_id').val();
     	var remarks = $('#remarks').val();
     	console.log(audit);
		     if(audit!=""){
		       console.log(audit);
		        $.ajax({
		             type: "POST",
		             url: "${base}/mechanism/project/updateAudit.jhtml",
		             data: {
		             id:id,
		             audit:audit,
		             remarks:remarks
		             },
		             dataType: "json",
		             success: function(message){
		             	$.message(message);
		             	disp_hidden_d('layer');
		             	 location.reload();
		               }
		         });
		     }
    }

</script>


</head>
<body>
    	<div class="nav">管理导航：
        <a href="http://www.sina.com.cn">管理首页</a>　
        <a href="javascript:;">新增医师</a>　
        <a href="javascript:;">排班管理</a>
      </div>
        <div class="seat">
        	<div class="left_z">医师信息</div>
            <div class="export">
      				[#include "/mechanism/include/export.ftl" /]    
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
            				<td><img onerror="this.src='${base}/resources/mechanism/images/add_img.png'" src="${doctor.logo}" alt="${doctor.name} ${doctor.doctorCategory.name}　${message("Member.Gender."+doctor.gender)}" title="${doctor.name} ${doctor.doctorCategory.name}　${message("Member.Gender."+doctor.gender)}" class="head_1" /></td>
            			</tr>
            			<tr>
            				<td height="50" class="b_a_dcdcdc_1"><span class="sp_m_1">${doctor.name}</span><span class="z_12_999999_1">　${doctor.doctorCategory.name}　${message("Member.Gender."+doctor.gender)}</span></td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">联系电话：${doctor.mobile}</td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">岗位级别：${doctor.doctorCategory.name}</td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">出生日期：${doctor.birth?string("yyyy-MM-dd")}</td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">用户积分：1000分</td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">用户评分：
            					<table width="200" cellpadding="0" cellspacing="0" border="0" class="pif_1">
            						<tr>
            							<td align="center">
											<span>综合评分</span>
            								<span class="pf_span_1">
												<div class="pf_bg_1" style="width:${doctor.scoreSort*10}%;"></div>
												<img src="${base}/resources/mechanism/images/x_1.png" border="0" class="pos_re_2" />
           									</span>
           									<span>${doctor.scoreSort}分</span>
										</td>
            						</tr>
            						<tr>
            							<td align="center">
											<span>技能评价</span>
            								<span class="pf_span_1">
												<div class="pf_bg_1" style="width:${doctor.skillSort*10}%;"></div>
												<img src="${base}/resources/mechanism/images/x_1.png" border="0" class="pos_re_2" />
           									</span>
           									<span>${doctor.skillSort}分</span>
										</td>
            						</tr>
            						<tr>
            							<td align="center">
											<span>服务能力</span>
            								<span class="pf_span_1">
												<div class="pf_bg_1" style="width:${doctor.serverSort*10}%;"></div>
												<img src="${base}/resources/mechanism/images/x_1.png" border="0" class="pos_re_2" />
           									</span>
           									<span>${doctor.serverSort}分</span>
										</td>
            						</tr>
            						<tr>
            							<td align="center">
											<span>沟通能力</span>
            								<span class="pf_span_1">
												<div class="pf_bg_1" style="width:${doctor.communicateSort*10}%;"></div>
												<img src="${base}/resources/mechanism/images/x_1.png" border="0" class="pos_re_2" />
           									</span>
           									<span>${doctor.communicateSort}分</span>
										</td>
            						</tr>
            					</table>
            				</td>
            			</tr>
            			<tr>
            				<td>
            				</td>
            			</tr>
            		</table>
            	</div>
            </td>
            <td valign="top"><table width="99%" align="right" border="0" cellspacing="0" cellpadding="0">
              <tbody>
                <tr>
                  <td height="40">
					<div class="peo_tag_1">
						<ul>
							<li style="width:17%;"><div class="border_radius_l_1"><a href="${base}/mechanism/doctor/view.jhtml?id=${doctor.id}">基本信息</a></div><div class="div_x_1"></div></li>
							<li style="width:16%;"><div class="sel_div_1"><a href="${base}/mechanism/doctor/project.jhtml?doctorId=${doctor.id}">服务项目</a></div><div class="div_x_1"></div></li>
							<li style="width:17%;"><div><a href="${base}/mechanism/workDay/list.jhtml?nameOrphone=${doctor.mobile}&name=${doctor.name}">排班信息</a></div><div class="div_x_1"></div></li>							
							<li style="width:16%;"><div><a href="${base}/mechanism/doctor/doctor_reserve.jhtml?doctorId=${doctor.id}">预约管理</a></div><div class="div_x_1"></div></li>
							<li style="width:17%;"><div><a href="${base}/mechanism/doctor/doctor_visit_message_list.jhtml?doctorId=${doctor.id}">回访信息</a></div><div class="div_x_1"></div></li>
							<li style="width:17%;"><div class="border_radius_r_1"><a href="${base}/mechanism/doctor/doctor_evaluate_list.jhtml?doctorId=${doctor.id}">评价信息</a></div><div class="div_x_1"></div></li>
						</ul>
					</div>
               	  </td>
                </tr>
                <tr>
                  <td>
                  		    <form id="listForm" action="list.jhtml" method="get">
                  		    <input type = "hidden" id = "doctorId" name = "doctorId" value = "${doctor.id}"/>
				  	        <div class="detail">
					          <table width="100%" border="0" cellspacing="0" cellpadding="0" id="cont_list" class="m_t_20_1">
					            <tr class="bg_f5f5f5_1">
					              <td height="35" align="center">项目名称</td>
					              <td width="200" align="center">项目分类</td>
					              <td align="center" width="100">价格<font>(元/分钟)</font></td>
					              <td width="100" align="center">服务医师</td>
					              <td width="70" align="center">审核状态</td>
					              <td width="90" align="center">创建时间</td>
					              <td width="120" align="center">审核</td>
					            </tr>
					            [#list page.content as project]
					            <tr [#if project_index%2==0]class="fff"[/#if]>
					              <td height="35" align="left">${project.name}</td>
					              <td align="left">${project.serverProjectCategory.name}</td>
					              <td align="center">${currency(project.price)}/${project.time}</td>
					              <td align="center">${project.doctor.name}<font>&nbsp;</font></td>
					              <td align="center">${message("Project.Audit." + project.audit)}</td>
					              <td align="center">${project.createDate?string("yyyy-MM-dd")}<font>&nbsp;</font></td>
					              <td align="center"><input type="button" name="button" id="button" value="明细" class="button_3 b_d1d141_1" onclick="show_info(${project.id},1);" />
					              <input type="button" name="button7" id="button7" value="审核" class="button_3 b_4188d1_1" onclick="disp_hidden_d('layer','470','320','${project.id}')" /></td>
					            </tr>
					            <tr [#if project_index%2==0]class="fff"[/#if] style="display:none" id="info_${project.id}">
					              <td height="35" colspan="7" align="left" class="p_10_20_1">
								              项目类别：${project.serverProjectCategory.name}<br />
								              项目名称：${project.name}<br/>
								              服务医师：${project.doctor.name}<br/>
								              项目价格：${currency(project.price)}元/${project.time}分钟<br/>
								              创建时间：${project.createDate?string("yyyy-MM-dd HH:mm:ss")}<br/>
								              详情描述：${project.introduce}<br/>
								              审核状态：${message("Project.Audit." + project.audit)}<br/>
								              审核说明：${project.remarks}
					              </td>
					            </tr>
					            [/#list]
					          </table>
					              <table width="100%" border="0" cellspacing="0" cellpadding="0">
					                <tr>
					                  <td class="b_f_4c98f6_1 pos_r_1">
						                  [@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
												[#include "/mechanism/include/pagination.ftl"]
										  [/@pagination]                 
					                  </td>
					                </tr>
					            </table>          
					        </div> 
					        </form> 
				  </td>
            </tr>
        </table>
        </div> 

<div class="clear"></div>


<div id="layer">
	<div class="layer">
    	<div class="d_l_1">
        	<div class="d_1_2"><span>状态：</span>
        				<select id="flag" name="flag" class="cate_o">
        				<option value="">请选择</option>
        				[#list audits as audit]
        				 <option value="${audit}">${message("Project.Audit." + audit)}</option>
        				[/#list]
                        </select>
                        <input name="p_id" id="p_id" type="hidden" value="" /></div>
            <div class="d_1_3"><span>备注：</span><textarea id="remarks" name="remarks" class="d_cont_1"></textarea></div>
            <div class="clear"></div>
            <div class="d_1_4">
            	<input type="button" name="button4" id="button4" value="　提　交　" class="button_1 b_4fc1e9_1" onclick="udpateAudit()" />　
            	<input type="button" name="button5" id="button5" value="　取　消　" class="button_1 b_d1d141_1" onclick="disp_hidden_d('layer');"/></div>
        </div>
        <div class="d_cl_1"><a href="javascript:;" onclick="disp_hidden_d('layer');"><img src="${base}/resources/mechanism/images/x.png" width="15" border="0" /></a></div>
    
	</div>
</div>

	
</body>
</html>