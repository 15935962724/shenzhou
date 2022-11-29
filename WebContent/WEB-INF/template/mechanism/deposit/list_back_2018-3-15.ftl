<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>充值记录</title>
<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/js.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/editor/kindeditor.js"></script>
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
</script>
</head>
<body>
<div class="nav">管理导航</div>
  <div class="seat">
  	<div class="left_z">充值日报</div>
      <div class="export">
      <a href="javascript:;"><img src="${base}/resources/mechanism/images/print.png" border="0" class="top_img_b_1" /> 打印</a>
      [#if valid('export')]<a href="javascript:;" id = "download"><img src="${base}/resources/mechanism/images/export.png" border="0" class="top_img_b_1" /> 导出</a>[/#if]
    </div>
  </div>
  <form id="listForm" action="list.jhtml" method="get">
  <div class="detail">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" id="listTable">
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
	        <td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				    <tr>
				      <td width="95%" valign="top">
				        <table width="100%" border="0" cellspacing="0" cellpadding="0">
				          <tr>
				            <td width="25%" height="50">联系人电话：<input type="text" name="nameOrmobile" id="nameOrmobile" placeholder="姓名/电话" value="${nameOrmobile}" class="input_1 w_150_1" /></td>
				            <td width="75%" colspan="3">
				            	变动周期：
				            	<input type="text"  id="startDate" name="startDate"  [#if startDate??] value="${startDate?string("yyyy-MM-dd")}" [/#if]   class="input_1 w_150_1" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '#F{$dp.$D(\'endDate\')}'});"/> - 
				            	<input type="text"  id="endDate" name="endDate" [#if endDate??] value="${endDate?string("yyyy-MM-dd")}" [/#if]   class="input_1 w_150_1" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', minDate: '#F{$dp.$D(\'startDate\')}'});" class="input_1 w_100_1" />
				        	</td>
				          </tr>
				        </table>
				      </td>
				      <td width="5%" align="right" valign="bottom">
				      	<input type="submit" name="button" id="submitButton" value="搜索" class="button_2 b_4c98f6_1 m_b_10_1" />
				      </td>
					</tr>
				</table>
	         </td>
	    </tr>
        <tr>
          <td height="20" align="left" valign="middle"></td>
        </tr>
        <tr>
          <td height="20" align="left" valign="top">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" id="cont_list">
                    <tr class="bg_f5f5f5_1">
                      <td height="50" width="30" align="center">序号</td>
                      <td width="80" align="center">用户姓名</td>
                      <td width="80" align="center">类型</td>
                      <td width="80" align="center">收入金额<br><font class="z_12_999999_1">(元)</font></td>
                      <td width="80" align="center">支出金额<br><font class="z_12_999999_1">(元)</font></td>                      
                      <td width="80" align="center">当前余额<br><font class="z_12_999999_1">(元)</font></td>                      
                      <td width="120" align="center">订单</td>
                      <td align="center">备注</td>
                      <td width="90" align="center">操作时间</td>
                      <td width="80" align="center">操作人</td>
                    </tr>
                    [#list page.content as deposit]
                    <tr [#if deposit_index%2==0] class="fff" [/#if]>
                      <td height="35" align="center">${deposit_index+1}</td>
                      <td align="left">${deposit.member.name}<br><font style="z_12_999999_1">${deposit.member.mobile}</font></td>
                      <td align="center">${message("Deposit.Type." + deposit.type)}</td>
                      <td align="center">${currency(deposit.credit)}</td>
                      <td align="center">${currency(deposit.debit)}</td>
                      <td align="center">
                      		${currency(deposit.balance)}
					  </td>
                      <td align="center">
                        [#if deposit.order??]
							${deposit.order.sn}
						[#else]
							-
						[/#if]
                      </td>
                      <td align="left">
	                      [#if deposit.memo??]
								<span title="${deposit.memo}">${abbreviate(deposit.memo, 50, "...")}</span>
						  [/#if]
                      </td>
                      <td align="center">${deposit.createDate?string("yyyy-MM-dd")}<br><font class="z_12_999999_1">${deposit.createDate?string("HH:mm:ss")}</font></td>
                      <td align="center">${deposit.operator}</td>
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
<div class="clear"></div>
</body>
</html>