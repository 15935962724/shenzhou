<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/js.js"></script>

<script type="text/javascript" >
function scheduling(doctorId,workDayDate){
//alert(workDayDate);

 if(confirm('确定要给该医生排班么?')){
				 $.ajax({
					url: "scheduling.jhtml",
					type: "POST",
					data: {
					 doctorId : doctorId,
					 workDayDate : workDayDate
					},
					dataType: "json",
					cache: false,
					async:false,
					success: function(data) {
					  if(data.status=="200"){
					  	$.message(data.message);
					  	location.href = "edit.jhtml?doctorId="+doctorId+"&workDayId="+data.data;
					  }else{
					    $.message(data.message);
					  }
					}
				});
		}
}
</script>
</head>
<body>
    	<div class="nav">管理导航</div>
        <div class="seat">
        	<div class="left_z">排班管理</div>
            <div class="export">
            <a href="javascript:;"><img src="${base}/resources/mechanism/images/print.png" border="0" class="top_img_b_1" /> 打印</a>
            <a href="javascript:;"><img src="${base}/resources/mechanism/images/export.png" border="0" class="top_img_b_1" /> 导出</a>
            </div>
        </div>
        <form id="listForm" action="list.jhtml" method="get">
        <div class="detail">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" id="listTable">
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td height="40" align="left"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="400" height="40" style="position: relative;">
                <input id="nameOrphone" name="nameOrphone" type="text" [#if name??] value="${name}" [#else] value="${nameOrphone}" [/#if] placeholder="医师姓名/电话" class="scr_k m_t_15_1" />
                <input type="submit" name="button" id="button" value="搜索" class="scr_b m_t_15_1 b_4c98f6_1" /></td>
                <td align="right">&nbsp;</td>
              </tr>
            </table></td>
            </tr>
          <tr>
            <td height="20" align="left" valign="middle"></td>
          </tr>
          <tr>
            <td height="20" align="left" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" id="cont_list">
              <tr class="bg_f5f5f5_1">
                <td height="50" width="30" align="center">序号</td>
                <td width="100" class="p_l_r_5" align="center">姓名</td>
                [#list dateDays as dateDay]
                <td align="center">${dateDay.week}<br /><font class="z_12_707070_1">${dateDay.dateDay?string("yyyy-MM-dd")}</font></td>
                [/#list]
              </tr>
              [#list page.content as doctorMechanismRelation]
	              <tr class="fff">
	                <td height="55" align="center">${doctorMechanismRelation_index+1}</td>
	                <td align="left">${doctorMechanismRelation.doctor.name}<br />
						<font class="z_12_999999_1">${message("Member.Gender."+doctorMechanismRelation.doctor.gender)} ${doctorMechanismRelation.doctor.doctorCategory.title}<br>${doctorMechanismRelation.doctor.mobile}</font>
	               </td>
	               [#list dateDays as dateDay]
	               	<td align="center">
	               	[#assign count = 0]
	               	[#list doctorMechanismRelation.doctor.workDays as workDay]
		               	   [#if  (workDay.workDayDate)?string("yyyyMMdd") == (dateDay.dateDay)?string("yyyyMMdd") && (workDay.isArrange) ]
			               	     	<a href="edit.jhtml?doctorId=${doctorMechanismRelation.doctor.id}&workDayId=${workDay.id}"><input type="button" name="button3" id="button3" value="已排" class="button_3 b_4c98f6_1" /></a>
			               	     	[#assign count = count+1 ]
		               	   [/#if]
	               	[/#list]
	               	[#if count==0]<a href="javascript:;" onClick="scheduling(${doctorMechanismRelation.doctor.id},${dateDay.dateDay?string("yyyyMMdd")});"><input type="button" name="button3" id="button3" value="未排" class="button_3" /></a>[/#if]
	               	</td>
	               [/#list]
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
            </td>
          </tr>
          <tr>
            <td align="left" valign="top">&nbsp;</td>
          </tr>
        </table>
        </div> 
		</form>     
    </div>
<div class="clear"></div>



</body>
</html>