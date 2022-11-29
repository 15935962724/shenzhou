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
            				<td><img onerror = "this.src='${base}/resources/mechanism/images/r.png'" src="${member.logo}" alt="${member.name}" title="${member.name}" class="head_1" /></td>
            			</tr>
            			<tr>
            				<td height="50" class="b_a_dcdcdc_1"><span class="sp_m_1">${member.name}</span><span class="z_12_999999_1">　${message("Member.Gender."+member.gender)}　${member.nation}</span></td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">联系电话：${member.mobile}</td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">名下患者：
                				[#list member.children as patient]
        				   		    [#if !patient.isDeleted]
										${patient.name}、
									[/#if]
                				[/#list]
            				</td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">出生日期：${member.birth?string("yyyy-MM-dd")}</td>
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
        							<li style="width:17%;"><div class="border_radius_l_1"><a href="${base}/mechanism/member/member_view.jhtml?id=${member.id}">基本信息</a></div><div class="div_x_1"></div></li>
                                    <li style="width:17%;"><div><a href="${base}/mechanism/member/member_patient.jhtml?memberId=${member.id}">患者信息</a></div><div class="div_x_1"></div></li>
                                    <li style="width:17%;"><div><a href="${base}/mechanism/member/member_reserve.jhtml?memberId=${member.id}">预约信息</a></div><div class="div_x_1"></div></li>
                                    <li style="width:17%;"><div><a href="${base}/mechanism/visitMessage/member_visitMessage_list.jhtml?memberId=${member.id}&type=member">回访信息</a></div><div class="div_x_1"></div></li>
                                    <li style="width:16%;"><div><a href="${base}/mechanism/balance/recharge.jhtml?memberId=${member.id}">账户充值</a></div><div class="div_x_1"></div></li>
                                    <li style="width:16%;"><div class="sel_div_1 border_radius_r_1"><a href="${base}/mechanism/member/member_doctor.jhtml?memberId=${member.id}">医师信息</a></div><div class="div_x_1"></div></li>
        						</ul>												
          					</div>
               	  </td>
                </tr>
                <tr>
                  <td class=" r_list">
                      <ul>
                      [#list member.doctors as doctor]
                      	   <li>
							  <table width="255" border="0" cellspacing="0" cellpadding="0">
								<tbody>
	  							  <tr>
	    								<td height="90" valign="top">
	      								  <div class="z_12_707070_1" style="height: 90px;">
	        								   <table width="240" border="0" cellspacing="0" cellpadding="0" align="center" class="m_t_10_1">
	          									  <tbody>
	            										<tr>
	            										  <td width="80" rowspan="3" valign="top"><img onerror="this.src='${base}/resources/mechanism/images/r.png'" src="${doctor.logo}" /></td>
	            										  <td width="160" valign="bottom"><span class="z_20_4c98f6_1">${doctor.name}</span><span class="z_12_999999_1">　${message("Member.Gender."+doctor.gender)} ${doctor.doctorCategory.name}</span></td>
	            										</tr>
	            										<tr>
	            										  <td>联系电话：${doctor.mobile}</td>
	            										</tr>
	            										<tr>
	            										  <td>医师状态：${message("Doctor.Status."+doctor.status)}</td>
	            										</tr>
	          									  </tbody>
	        									 </table>
	      								  </div>
	    							   </td>
	  							  </tr>
								  <tr> 
	  								<td height="35">
	  									<ul>
	  										<li class="b_0aa3ef_1 f_l_1" style="width: 127px;"><a href="javascript:;">预约</a></li>
	  										<li class="b_0ee4d2_1 f_r_1" style="width: 128px;"><a href="${base}/mechanism/doctor/view.jhtml?id=${doctor.id}">医师明细</a></li>
	  									</ul>
	  								</td>
								  </tr>
								</tbody>
							  </table>				   
                     	  </li>
                     [/#list]
                      </ul>
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