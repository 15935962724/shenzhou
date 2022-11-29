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

<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $memberId = $("#memberId");
	var $patientMemberId = $("#patientMemberId");
	var $weekNum = $("#weekNum");
	var $upperWeek = $("#upperWeek");
	var $lowerWeek = $("#lowerWeek");
	var $today = $("#today");
	
	[@flash_message /]
	
	// 上一周
	$upperWeek.click(function() {
		var weekNum = $weekNum.val();
		weekNum--;
		$weekNum.val(weekNum)
		$inputForm.submit();
	});
	//下一周
	$lowerWeek.click(function() {
		var weekNum = $weekNum.val();
		weekNum++;
		$weekNum.val(weekNum)
		$inputForm.submit();
	});
	//今天
	$today.click(function() {
		$weekNum.val("")
		$inputForm.submit();
	});

});
</script>


<script type="text/javascript" >

function disp_hidden_div(d_id,d_width,d_height,id)
	{
	append_div(d_id,id);
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
	}

function append_div(d_id,id){
       console.log(id);
       var dlv = $("."+d_id);
       var ids = id.split(",")
       dlv.empty();
      
       
       
       //将数据转换成对象  
        var html = '';
        
            html += '<div class="d_l_1">';
			html += '<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z_14_707070_1">';
			html += '<tr>';
			html += '<td colspan="7" height="50" class="z_14_4c98f6_1 ">预约信息</td>';
			html += '</tr>';
			html += '<tr>';
			html += '<td width="200" height="40" align="center" class="b_l_t_dcdcdc_1 b_4c98f6_z_14_fefeff_1">患者信息</td>';
			html += '<td width="100" align="center" class="b_l_t_dcdcdc_1 b_4c98f6_z_14_fefeff_1">预约医师</td>';
			html += '<td align="center" class="b_l_t_dcdcdc_1 b_4c98f6_z_14_fefeff_1">预约项目</td>';
			html += '<td width="100" align="center" class="b_l_t_dcdcdc_1 b_4c98f6_z_14_fefeff_1">预约时间</td>';
			html += '<td width="80" align="center" class="b_l_t_dcdcdc_1 b_4c98f6_z_14_fefeff_1">服务方式</td>';
			html += '<td width="70" align="center" class="b_l_t_dcdcdc_1 b_r_dcdcdc_1 b_4c98f6_z_14_fefeff_1">备注</td>';
			html += '</tr>';
			
			 $.ajax( {     
                "type": "POST",  
                "url": 'order_view.jhtml',  
                "contentType": "application/x-www-form-urlencoded; charset=utf-8",   
                "traditional": true,  
                "async": false,
                "data": {  
                    ids:ids  
                },  
                success: function(data) {   
                	
                    //将数据转换成对象  
                    var dataObj = eval('('+data+')');  
                    console.log(dataObj);
                    for(var i = 0; i<dataObj.length; i++){
	                    html += '<tr>';
						html += '<td height="90" valign="middle" class="b_l_t_dcdcdc_1 b_b_dcdcdc_1">';
						html += '<table cellpadding="0" cellspacing="0" border="0" width="100%" class="m_a_10_1">';
						html += '<tr>';
						html += '<td width="70" valign="top"><img src="'+dataObj[i].patienMemberLogo+'" width="60" height="60" border="0" class="u_img_2" /></td>';
						html += '<td valign="middle">';
						html += '<table cellpadding="0" cellspacing="0" border="0" width="100%">';
						html += '<tr>';
						html += '<td height="14">'+dataObj[i].patienMemberName+'<font class="z_12_999999_1"> '+(dataObj[i].patienMemberGender=="mole"?"男":"女")+' '+getAge(getMyDate(dataObj[i].patienMemberBirth))+'周岁</font></td>';
						html += '</tr>';
						html += '<tr>';
						html += '<td class="z_12_999999_1">'+dataObj[i].patienMemberParentName +' '+dataObj[i].patienMemberParentMoble +'</td>';
						html += '</tr>';
						html += '</table>';
						html += '</td>';
						html += ' </tr>';
						html += '</table>';
						html += '</td>';
						html += '<td valign="middle" class="b_l_t_dcdcdc_1 b_b_dcdcdc_1">';
						html += '<table cellpadding="0" cellspacing="0" border="0" width="100%" class="m_a_10_1">';
						html += '<tr>';
						html += '<td height="25">'+dataObj[i].doctorName+'</td>';
						html += '</tr>';
						html += '<tr>';
						html += '<td class="z_12_999999_1">'+dataObj[i].doctorMobile+'</td>';
						html += '</tr>';
						html += '</table>';
						html += '</td>';
						html += '<td valign="middle" align="left" class="p_l_r_10_1 b_l_t_dcdcdc_1 b_b_dcdcdc_1">'+dataObj[i].porjectName+'</td>';
						html += '<td valign="middle" align="center" class="p_l_r_10_1 b_l_t_dcdcdc_1 z_12_707070_1 b_b_dcdcdc_1">'+dataObj[i].startTime+'—'+dataObj[i].endTime+'</td>';
						html += '<td valign="middle" align="center" class="p_l_r_10_1 b_l_t_dcdcdc_1 b_b_dcdcdc_1">到店服务</td>';
						html += '<td valign="middle" align="center" class="p_l_r_10_1 b_l_t_dcdcdc_1 b_r_dcdcdc_1 b_b_dcdcdc_1"><input type="text" value="'+dataObj[i].memo+'" class="inp_1 w_70_1" /></td>';
						html += '</tr>';
                    }
                }     
        }); 
			
			html += '</table>';
			html += '</div>';
			html += '<div class="d_cl_1"><a href="javascript:;" onclick="disp_hidden_d(\'layer\');"><img src="${base}/resources/mechanism/images/x.png" width="15" border="0" /></a></div>';
        dlv.append(html);  
      
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
        	<div class="left_z">[#if member??]用户信息[/#if][#if patientMember??]患者信息[/#if]</div>
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
            			[#if memberId??][#include "/mechanism/include/member_view.ftl" /][/#if]
            			[#if patientMemberId??][#include "/mechanism/include/patient_view.ftl" /][/#if]
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
		                    <li style="width:17%;"><div class="sel_div_1"><a href="${base}/mechanism/member/reserve.jhtml?memberId=${member.id}">预约信息</a></div><div class="div_x_1"></div></li>
		                    <li style="width:17%;"><div><a href="${base}/mechanism/visitMessage/member_visitMessage_list.jhtml?memberId=${member.id}&type=member">回访信息</a></div><div class="div_x_1"></div></li>
		                    <li style="width:16%;"><div><a href="${base}/mechanism/balance/recharge.jhtml?memberId=${member.id}">账户充值</a></div><div class="div_x_1"></div></li>
		                    <li style="width:16%;"><div class="border_radius_r_1"><a href="${base}/mechanism/member/member_doctor.jhtml?memberId=${member.id}">医师信息</a></div><div class="div_x_1"></div></li>
						</ul>												
					</div>
               	  </td>
                </tr>
                <tr>
                  <td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="z_14_323232_1">
					 <form id="inputForm" action="doctor_reserve.jhtml" method="get">
					 <input type="hidden" id="memberId" name="memberId" value="${memberId}" />
					 <input type="hidden" id="patientMemberId" name="patientMemberId" value="${patientMemberId}" />
					 <input type="hidden" id="weekNum" name="weekNum" value="${weekNum}" />
					  <tr>
						<td height="40" align="right" valign="center" class="p_t_5_1">
							<input type="button" name="upperWeek" id="upperWeek" value=" 上一周 " class="button_5" /><input type="button" name="lowerWeek" id="lowerWeek" value=" 下一周 " class="button_6" /><input type="button" name="today" id="today" value=" 今　天 " class="button_7" />
						</td>
					  </tr>
					 </form>
					  <tr>
						<td height="40" align="left" valign="top" class="p_t_5_1 z_14_707070_2">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
							  <tr>
								<td width="16%" valign="top" class=" b_t_r_b_dcdcdc_1 b_l_dcdcdc_1 ">
									<table width="100%" border="0" cellspacing="0" cellpadding="0" class="">
									  <tr>
										<td height="50" class="f8f8f8">&nbsp;</td>
									  </tr>
									  [#list workDates as workDate]
										   <tr>
											<td height="50" align="center" [#if workDate_index%2==0]class="b_t_dcdcdc_1"[#else]class="f8f8f8 b_t_dcdcdc_1"[/#if] >${workDate.workDateTime}:00</td>
										   </tr>
									  [/#list]
									</table>
								</td>
								[#list dateLists as date]
								<td width="12%" valign="top" [#if (date.workDate)?string("yyyyMMdd") == .now?string("yyyyMMdd") ]class="b_a_f00_1"[#else]class="b_t_r_b_dcdcdc_1"[/#if] >
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
									  <tr>
										<td height="50" align="center" valign="middle" class="f8f8f8">${date.week}<br />
										  <font class="z_12_999999_1">${date.workDate?string("yyyy-MM-dd")}</font></td>
									  </tr>
									   [#list workDates as workDate]
									       <tr>
												<td height="50" align="center" [#if workDate_index%2==0]class="b_t_dcdcdc_1"[#else]class="f8f8f8 b_t_dcdcdc_1"[/#if]>
													[#assign  count = 0]
													[#assign  str = ""]
													[#list orders as order]
												     [#if (date.workDate)?string("yyyyMMdd") == (order.orderWorkDay)?string("yyyyMMdd") && (order.orderStartTime == workDate.workDateTime) ]
												            [#assign  count = count + 1]
												            [#assign  str = str + "${order.orderId}"+","]
												     [/#if]
												    [/#list]
												    [#if count > 0] <input type="button" name="button3" id="button3" value="已约" class="button_3 b_4c98f6_1" data="${str}" onclick="disp_hidden_div('layer',($(document).width()-100),(90*${count}+150) ,'${str}')"> [/#if]
												</td>
										   </tr>
									  [/#list]
									</table>
								</td>
								[/#list]
							  </tr>
							</table>
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

<div id="layer" style="display:none;">
	<div class="layer">
    	
	</div>
</div>

</body>
</html>