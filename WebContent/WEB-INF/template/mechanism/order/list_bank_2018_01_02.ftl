[#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
[#include "/mechanism/include/meta.ftl" /] 
<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/js.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $listForm = $("#listForm");
	var $filterSelect = $("#filterSelect");
	var $print = $("select[name='print']");

	var $order_flag = $("#order_flag");//订单状态下拉
	var $submitButton = $("#submitButton");//搜索
	var $deleteOrder = $("#deleteOrder");//删除
	var $download = $("#download");//导出

	[@flash_message /]
	
	//导出
	$download.click(function() {
		$listForm.attr('action','downloadList.jhtml');
		$listForm.submit();
		$listForm.attr('action','list.jhtml');		
	});
	
	//订单状态
	$order_flag.change(function(){
		var selectValue = $(this).val(); 
		var selectClass = $(":selected",'#order_flag').attr("class")
			$('#paymentStatus').val('');
			$('#serveState').val('');
			$('#evaluate').val('');
			$('#orderStatus').val('');
			
				$('#'+selectClass).val(selectValue);
				$listForm.submit();
	}); 
	
	
	
	// 搜索
	$submitButton.click(function() {
		$listForm.submit();
		return false;
	});

	//删除
	$deleteOrder.click(function() {
		var $this = $(this);
		var $checkedIds = $("#listTable input[name='ids']:enabled:checked");
		
		if(confirm('确定要删除选中的信息吗？')){
			$.ajax({
					url: "delete.jhtml",
					type: "POST",
					data: $checkedIds.serialize(),
					dataType: "json",
					cache: false,
					success: function(message) {
						$.message(message);
						location.reload(true);
					}
				});
		}
		
		
	});


});
</script>
</head>
<body>
	<div class="nav">管理导航：<a href="javascript:;">管理首页</a>　<a href="javascript:;">新增医师</a>　<a href="javascript:;">排班管理</a></div>
            <div class="seat">
            	   <div class="left_z">订单管理</div>
                   <div class="export">
                     <a href="javascript:;"><img src="${base}/resources/mechanism/images/print.png" border="0" class="top_img_b_1" /> 打印</a>
                     [#if valid('export')]<a href="javascript:;" id = "download"><img src="${base}/resources/mechanism/images/export.png" border="0" class="top_img_b_1" /> 导出</a>[/#if]
                   </div>
            </div>
            <form id="listForm" action="list.jhtml" method="get">
            <input type="hidden" id="paymentStatus" name="paymentStatus" value="${paymentStatus}" />
            <input type="hidden" id="serveState" name="serveState" value="${serveState}" />
			<input type="hidden" id="evaluate" name="evaluate" value="${evaluate}" />
			<input type="hidden" id="orderStatus" name="orderStatus" value="${orderStatus}" />
	          <div class="detail">
	          <table id="listTable" width="100%" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <td>
	                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                    <tr>
	                      <td width="95%" valign="top">
	                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                          <tr>
	                            <td width="25%" height="50">订单编号：<input type="text" name="sn" id="sn" value="${sn}" class="input_1 w_100_1" /></td>
	                            <td width="25%">服务项目：<input type="text" name="projectName" id="projectName" value="${projectName}" class="input_1 w_100_1" /></td>
	                            <td width="25%">&nbsp;</td>
	                            <td width="25%">&nbsp;</td>
	                          </tr>
	                          <tr>
	                            <td height="50">服务医师：<input type="text" name="doctorName" id="doctorName" value="${doctorName}" class="input_1 w_100_1" /></td>
	                            <td>下单用户：<input type="text" name="memberName" id="memberName" value="${memberName}" class="input_1 w_100_1" /></td>
	                            <td>患者姓名：
	                            <input type="text" name="patientName" id="patientName" value="${patientName}" class="input_1 w_100_1" />
	                            </td>
	                            <td>联系电话：<input type="text" name="phone" id="phone" value="${phone}" class="input_1 w_100_1" /></td>
	                          </tr>
	                          <tr>
	                            <td height="50" colspan="2">订单周期：
	                            <input type="text"  id="startDate" name="startDate"  [#if startDate??] value="${startDate?string("yyyy-MM-dd")}" [/#if]   class="Wdate input_1 w_150_1" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '#F{$dp.$D(\'endDate\')}'});" class="input_1 w_100_1" /> - 
	                            <input type="text"  id="endDate" name="endDate" [#if endDate??] value="${endDate?string("yyyy-MM-dd")}" [/#if]   class="Wdate input_1 w_150_1" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', minDate: '#F{$dp.$D(\'startDate\')}'});" class="input_1 w_100_1" /></td>
	                            <td>&nbsp;</td>
	                            <td>&nbsp;</td>
	                          </tr>
	                          <tr>
	                            <td height="50" colspan="4">订单状态：
	                            <select name="order_flag" id="order_flag" class="cate_o">
	                            	<option class = "" value="" >全部</option>
	                                <option class = "paymentStatus" value="unpaid" [#if paymentStatus??]selected = "selected"[/#if]>待付款</option>
	                                <option class ="serveState" value="await" [#if serveState??]selected = "selected"[/#if]>待康复</option>
	                                <!--<option class = "evaluate" value="not" [#if evaluate??]selected = "selected"[/#if]>待评价</option>-->
	                                <option class = "orderStatus" value="completed" [#if orderStatus??]selected = "selected"[/#if]>已完成</option>
	                              </select>
	                            </td>
	                          </tr>
	                        </table>
	                      </td>
	                  <td width="5%" align="right" valign="bottom"><input type="submit" name="button" id="submitButton" value="搜索" class="button_2 b_4c98f6_1" /><br />&nbsp;
	                  </td>
	                </tr>
	              </table></td>
	            </tr>
	            <tr>
	              <td><table width="100%" border="0" cellspacing="0" cellpadding="0" id="cont_list">
	                <tr class="bg_f5f5f5_1">
	                  <td width="30" height="35" align="center">选择</td>
	                  <td width="30" align="center">序号</td>
	                  <td width="100" align="center">订单编号</td>
	                  <td align="center">服务项目</td>
	                  <td width="70" align="center">患者姓名</td>
	                  <td width="70" align="center">下单用户</td>
	                  <td width="90" align="center">下单时间</td>
	                  <td width="60" align="center">康复状态</td>
	                  <!--<td width="60" align="center">支付状态</td>-->
	                  <td width="100" align="center">订单状态</td>
	                  <td width="100" align="center">备注</td>
	                  <td width="180" align="center">服务时间</td>
	                  <td width="180" align="center">管理</td>
	                </tr>
	                
	                [#list page.content as order]
	                <tr class="fff">
	                  <td height="35" align="center">
	                  <input type="checkbox" name="ids" value="${order.id}">
	                  </td>
	                  <td align="center">${order_index+1}</td>
	                  <td align="center">${order.sn}</td>
	                  <td>${order.project.name}<br /><font>${order.doctor.name} ${order.doctor.mobile}</font></td>
	                  <td align="center">${order.patientMember.name}</td>
	                  <td>${order.consignee}<br /><font>${order.phone}</font></td>
	                  <td align="center">${order.createDate?string("yyyy-MM-dd")}<br />
	                    <font>${order.createDate?string("HH:mm:ss")}</font></td>
                	  <td align="center">${message("Order.ServeState." + order.serveState)}</td>
                	  <!--<td align="center">${message("Order.PaymentStatus." + order.paymentStatus)}</td>-->
                	  <td align="center">${message("Order.OrderStatus." + order.orderStatus)}[#if order.expired](已过期)[/#if]</td>
                      <td align="center">${order.memo}</td>
                      <td align="center">[#if order.workDayItem??] ${order.workDayItem.workDay.workDayDate?string("yyyy-MM-dd")} ${order.workDayItem.startTime}-${order.workDayItem.endTime}[#else] - [/#if]</td>
	                  <td align="center">
	                  	<a href="view.jhtml?id=${order.id}" ><input type="button" name="button5" id="button5" value="明细" class="button_3 b_d1d141_1" /></a>
	                  	<input type="button" name="button8" id="button8" value="${message("Project.ServeType." + order.project.serveType)}" class="button_3 b_e94fdc_1" />
	                      <input type="button" name="button7" id="button7" value="取消" class="button_3 b_4188d1_1" />
	                  </td>
	                </tr>
	                [/#list]
	                
	              </table>
	                <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                  	<tr>
	                     <td class="b_f_4c98f6_1 pos_r_1">
		                    <table width="200" border="0" cellspacing="0" cellpadding="0" class="pos_re_2 f_z_1">
		                      <tr>
		                        <td width="42" height="30" align="center">
		                        <input type="checkbox" id="selectAll">
		                        </td>
		                        <td>全选　<a id="deleteOrder" href="javascript:;">删除选中项目</a></td>
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
	          </table>
	          </div>
          </form>      
<div class="clear"></div>
</body>
</html>