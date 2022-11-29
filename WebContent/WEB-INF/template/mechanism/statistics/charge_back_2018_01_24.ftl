
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>基本信息</title>
<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/js.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $listForm = $("#listForm");
	var $download = $("#download");
	
	[@flash_message /]
	
	//导出
	$download.click(function() {
	$listForm.attr('action','downloadCharge.jhtml');
	$listForm.submit();
	$listForm.attr('action','charge.jhtml');		
	});
	
});
</script>



</head>
<body>
    	<div class="nav">管理导航</div>
        <div class="seat">
        	<div class="left_z">收费日报</div>
            <div class="export">
            <a href="javascript:;"><img src="${base}/resources/mechanism/images/print.png" border="0" class="top_img_b_1" /> 打印</a>
            [#if valid('export')]<a href="javascript:;" id = "download" ><img src="${base}/resources/mechanism/images/export.png" border="0" class="top_img_b_1" /> 导出</a>[/#if]
          </div>
        
        </div>
        <form id="listForm" action="charge.jhtml" method="get">
        <div class="detail">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" id="listTable">
        <tr>
	              <td>
	                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                    <tr>
	                      <td width="95%" valign="top">
	                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                          <tr>
	                            <td width="25%" height="50">服务项目：<input type="text" name="projectName" placeholder="请输入项目名称" id="projectName" [#if projectName??] value="${projectName}" [/#if]  class="input_1 w_100_1" /></td>
	                            <td width="75%" colspan="3">
	                            	服务时间：
	                            	<input type="text"  id="startDate" name="startDate" placeholder="请选择日期"  [#if startDate??] value="${startDate?string("yyyy-MM-dd")}" [/#if]   class="input_1 w_150_1" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '#F{$dp.$D(\'endDate\')}'});"  /> - 
	                            	<input type="text"  id="endDate" name="endDate"  placeholder="请选择日期" [#if endDate??] value="${endDate?string("yyyy-MM-dd")}" [/#if]   class="input_1 w_150_1" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', minDate: '#F{$dp.$D(\'startDate\')}'});"  />
                            	</td>
	                          </tr>
	                          <tr>
	                            <td height="50">服务医师：<input type="text" name="doctorName" id="doctorName" placeholder="请输入医师姓名"  [#if doctorName??] value="${doctorName}" [/#if] class="input_1 w_100_1" /></td>
	                            <td>下单用户：<input type="text" name="memberName" id="memberName"  placeholder="请输入用户姓名"  [#if memberName??] value="${memberName}" [/#if] class="input_1 w_100_1" /></td>
	                            <td>患者姓名：
	                            <input type="text" name="patientName" id="patientName"  placeholder="请输入患者姓名"  [#if patientName??] value="${patientName}" [/#if] class="input_1 w_100_1" />
	                            </td>
	                            <td>联系人电话：<input type="text" name="phone" id="phone" placeholder="请输入联系电话"  [#if phone??] value="${phone}" [/#if] class="input_1 w_100_1" /></td>
	                          </tr>
	                        </table>
	                      </td>
		                  <td width="5%" align="right" valign="bottom">
		                  	<input type="submit" name="button" id="submitButton" value="搜索" class="button_2 b_4c98f6_1 m_b_10_1" />
		                  </td>
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
                <td width="120" class="p_l_r_5" align="center">患者</td>
                <td align="center">项目名称</td>
                <td width="170" align="center">服务时间</td>
                <td width="70" align="center">单节费用<br><font class="z_12_999999_1">(元)</font></td>
                <td width="50" align="center">课节数<br><font class="z_12_999999_1">(节)</font></td>
                <td width="70" align="center">应收金额<br><font class="z_12_999999_1">(元)</font></td>
                <td width="70" align="center">减免金额<br><font class="z_12_999999_1">(元)</font></td>
                <td width="70" align="center">实收金额<br><font class="z_12_999999_1">(元)</font></td>
                <td width="120" align="center">缴费时间</td>
              </tr>
              [#assign countQuantity = 0] 
              [#assign countPrice = 0] 
              [#assign countCouponDiscount = 0] 
              [#assign countAmountPaid = 0] 
              [#assign count = 0] 
              [#list page.content as order]
	              <tr [#if order_index%2==0]class="fff"[/#if]>
	                <td height="35" align="center">${order_index+1}</td>
	                <td align="left">${order.patientMember.name}<br><font class="z_12_999999_1">${order.member.name} ${order.phone}</font></td>
	                <td align="left">${order.project.name}<br><font class="z_12_999999_1">${order.project.doctor.name} ${order.project.doctor.mobile}</font></td>
	                <td align="center">${order.workDayItem.workDay.workDayDate?string("yyyy-MM-dd")} ${order.workDayItem.startTime}-${order.workDayItem.endTime}</td>
	                <td align="center">${order.workDayItem.price}</td>
	                <td align="center">${order.quantity}</td>
	                <td align="center">${order.price}</td>
	                <td align="center">${order.couponDiscount}</td>
	                <td align="center">${order.amountPaid}</td>
	                <td align="center">${order.paidDate?string("yyyy-MM-dd HH:mm:ss")}</td>
	              </tr>
	              [#assign countQuantity = order.quantity+countQuantity]
	              [#assign countPrice = order.price+countPrice]
	              [#assign countCouponDiscount = order.couponDiscount+countCouponDiscount]
	              [#assign countAmountPaid = order.amountPaid+countAmountPaid]
	              [#assign count = count+1] 
              [/#list]
              <tr>
                <td height="35" align="center"> 合计</td>
                <td align="center">${count} </td>
                <td align="left">&nbsp;</td>
                <td align="center">&nbsp;</td>
                <td align="center">&nbsp;</td>
                <td align="center">${countQuantity}</td>
                <td align="center">${countPrice}</td>
                <td align="center">${countCouponDiscount}</td>
                <td align="center">${countAmountPaid}</td>
                <td align="center">&nbsp;</td>
              </tr>            
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
        </table>
        </div>
        </form>  
<div class="clear"></div>

</body>
</html>