<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>退款管理</title>
<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
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
		$listForm.attr('action','downloadList.jhtml');
		$listForm.submit();
		$listForm.attr('action','list.jhtml');		
	});
   
   
});


  function saveRefunds(){
  			var $inputForm = $("#inputForm");
     			 $.ajax({
					url: $inputForm.attr("action"),
					type: "POST",
					data: $inputForm.serialize(),
					dataType: "json",
					cache: false,
					success: function(message) {
						if (message.type == "success") {
							$.message(message);
							location=location;
						} else {
							$.message(message);
						}
					}
				});
     }

</script>
</head>
<body>

    	<div class="nav">管理导航</div>
        <div class="seat">
        	<div class="left_z">退款审核</div>
            <div class="export">
            <a href="javascript:;"><img src="${base}/resources/mechanism/images/print.png" border="0" class="top_img_b_1" /> 打印</a>
            [#if valid('export')]<a href="javascript:;" id="download"><img src="${base}/resources/mechanism/images/export.png" border="0" class="top_img_b_1" /> 导出</a>[/#if]
            </div>
        </div>
        <form id="listForm" action="list.jhtml" method="get">
        <div class="detail">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td height="40" align="left" valign="middle"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="40"></td>
                <td align="right">
               		<span>状态：</span>
                        <select id="level" name="status" class="cate_o" onchange="$('#listForm').submit();">
                            <option value="">全部</option>
                            [#list statuss as stu]
                            <option [#if stu == status] selected="selected" [/#if] value="${stu}">${message("Refunds.Status."+stu)}</option>
                            [/#list]
                        </select>
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
                <td height="35" align="center">项目名称</td>
                <td width="150" align="center">服务时间</td>
                <td width="150" align="center">下单人</td>
                <td width="100" align="center">康复医师</td>
                <td width="100" align="center">订单金额</td>
                <td width="100" align="center">已付金额</td>
                <td width="100" align="center">退款金额</td>
                <td width="100" align="center">状态</td>
                <td width="120" align="center">管理</td>
              </tr>
              [#list page.content as refunds]
              <tr [#if refunds_index%2==0]class="fff"[/#if]>
                <td height="60" align="left">${refunds.order.project.name}<font class="z_12_707070_1"><br>订单编号：${refunds.order.sn}</font></td>
                <td align="center">${refunds.order.workDayItem.workDay.workDayDate?string("yyyy年MM月dd日")}<font class="z_12_707070_1"><br />${refunds.order.workDayItem.startTime}-${refunds.order.workDayItem.endTime}</font></td>
                <td align="left">${refunds.order.consignee}<br /><font class="z_12_707070_1">${refunds.order.patientMember.name} ${message("Member.Gender."+refunds.order.patientMember.gender)} ${age(refunds.order.patientMember.birth)}周岁<br />${refunds.order.patientMember.mobile}</font></td>
                <td align="left">${refunds.order.doctor.name}<br />
			    <font style="z_12_707070_1">${refunds.order.doctor.mobile}</font></td>
                <td align="center">${currency(refunds.order.workDayItem.countPrice,true)}</td>
                <td align="center">${currency(refunds.order.amountPaid,true)}</td>
                <td align="center">${currency(refunds.amount,true)}</td>
                <td align="center">${message("Refunds.Status."+refunds.status)}</td>
                <td align="center">
                      <input type="button" name="button5" id="button5" value="明细" class="button_3 b_d1d141_1" onclick="show_info(${refunds.id},1);" />
                      <input type="button" name="button2" id="button2" value="审核" class="button_1 [#if refunds.status == "applying" ] b_ee6a71_1 [/#if] " [#if refunds.status == "applying" ] onclick="disp_hidden_d('layer','470','370','${refunds.id}');" [/#if]  />
                </td>
              </tr>
				<tr [#if refunds_index%2==0]class="fff"[/#if] style="display:none" id="info_${refunds.id}">
				  <td colspan="9" align="left" class="p_10_20_1">
					  服务项目：物理治疗(PT)<font class="z_12_707070_1">订单号:${refunds.order.sn}</font><br />
					  服务时间：${refunds.order.workDayItem.workDay.workDayDate?string("yyyy年MM月dd日")} ${refunds.order.workDayItem.startTime}-${refunds.order.workDayItem.endTime}<br />
					  订单金额：${currency(refunds.order.workDayItem.countPrice,true)}<br />
					  下 单 人：${refunds.order.member.name} <font class="z_12_707070_1">13212341234</font><br />
					  患者姓名：${refunds.order.patientMember.name}　<font class="z_12_707070_1">${message("Member.Gender."+refunds.order.patientMember.gender)} ${age(refunds.order.patientMember.birth)}周岁</font>
					  <hr class="b_b_dcdcdc_3">
					  退款金额：${currency(refunds.amount,true)}<br />
					  备       注：${refunds.memo}<br />			  
					  退款状态：${message("Refunds.Status."+refunds.status)}<br />
					  操作人：${refunds.operator}<br />
				  </td>
				</tr>
              [/#list]
              
              
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td class="b_f_4c98f6_1 pos_r_1">
                  <table width="200" border="0" cellspacing="0" cellpadding="0" class="pos_re_2 f_z_1">
                    <tr>
                      <td width="42" height="30" align="center">&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  </table>
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
    </div>
    </form>
<div class="clear"></div>

<div id="layer">
	<div class="layer">
	<form id="inputForm" action="update.jhtml" method="post">
    	<div class="d_l_1">
        	<div class="d_1_2">
        		<span>状态：</span>
		    	<select id="flag" name="status" class="cate_o">
                    [#list statuss as status]
                    <option value="${status}">${message("Refunds.Status."+status)}</option>
                    [/#list]
		        </select>
		        <input name="refundsId" id="p_id" type="hidden" value="" />
		    </div>
            <div class="d_1_3"><span>备注：</span><textarea name="memo" class="d_cont_1"></textarea></div>
            <div class="clear"></div>
            <div class="d_1_4">
            	<input type="button" name="button4" id="button4" value="　提　交　" class="button_1 b_4fc1e9_1" onclick="saveRefunds();" />　
            	<input type="button" name="button5" id="button5" value="　取　消　" class="button_1 b_d1d141_1" onclick="disp_hidden_d('layer');"/>
            </div>
        </div>
        </form>
        <div class="d_cl_1"><a href="javascript:;" onclick="disp_hidden_d('layer');"><img src="${base}/resources/mechanism/images/x.png" width="15" border="0" /></a></div>
    
	</div>
</div>

</body>
</html>