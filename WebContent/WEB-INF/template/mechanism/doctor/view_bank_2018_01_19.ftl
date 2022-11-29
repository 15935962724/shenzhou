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
							<li style="width:17%;"><div class="sel_div_1 border_radius_l_1"><a href="${base}/mechanism/doctor/view.jhtml?id=${doctor.id}">基本信息</a></div><div class="div_x_1"></div></li>
							<li style="width:16%;"><div><a href="${base}/mechanism/doctor/project.jhtml?doctorId=${doctor.id}">服务项目</a></div><div class="div_x_1"></div></li>
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
				  	<table cellpadding="0" cellspacing="0" border="0" width="100%" id="cont_list" class="z_14_707070_1" >
				  		<tr class="fff">
				  			<td width="50%" height="45">姓名：${doctor.name}</td>
				  			<td width="50%">性别：${message("Member.Gender."+doctor.gender)}</td>
				  		</tr>
				  		<tr class="fff">
				  			<td height="45">出生日期：${doctor.birth?string("yyyy-MM-dd")}</td>
				  			<td>身份证号：${doctor.entityCode}</td>
				  		</tr>
				  		<tr class="fff">
				  			<td height="45">联系电话：${doctor.mobile}</td>
				  			<td>联系地址：${doctor.area.fullName}${doctor.addrss}</td>
				  		</tr>
				  		<tr class="fff">
				  			<td height="45">从业年限：${doctor.year}年</td>
				  			<td>所处岗位：${doctor.doctorCategory.name}</td>
				  		</tr>
				  		<tr class="fff">
				  			<td height="45">服务机构：
				  			[#list doctor.doctorMechanismRelations as doctorMechanism]
					  			 [#if doctorMechanism.audit == "succeed"]
					  			      ${doctorMechanism.mechanism.name}、
					  			 [/#if]
				  			[/#list]
				  			</td>
				  			<td>加入时间：${doctor.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
				  		</tr>
				  		<tr class="fff">
				  			<td height="45" colspan="2">服务项目：	                    	
								[#if doctor.projects.size()>0]
									[#list doctor.projects as project]
										${project.name}、
									[/#list]
								[#else]
									暂无项目   
								[/#if]
                            </td>
				  		</tr>
				  		<tr class="fff">
				  			<td height="45" colspan="2">个人简介：${doctor.introduce}</td>
				  		</tr>
				  		<tr class="fff">
				  			<td height="45" colspan="2">审核状态：${message("Doctor.Status."+doctor.status)}</td>
				  		</tr>
				  		<tr class="fff">
				  			<td height="45" colspan="2">审核说明：[#if doctor.statusExplain??]${doctor.statusExplain}[#else]暂无说明[/#if]</td>
				  		</tr>
				  		<tr class="fff">
				  			<td height="45" colspan="2">资格证书：
								[#if doctor.doctorImages.size()>0]
									[#list doctor.doctorImages as doctorImage]
										<a href="javascript:;"><img src="${doctorImage.source}" /></a>
									[/#list]
								[#else]
									暂无    
								[/#if]
							</td>
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


	
</body>
</html>