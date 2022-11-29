[#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
[#include "/mechanism/include/meta.ftl" /] 
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>

<script type="text/javascript">
$().ready(function() {

	var $listForm = $("#listForm");
	var $filterSelect = $("#filterSelect");
	var $print = $("select[name='print']");


	var $order_isAbnormal = $("#order_isAbnormal");//订单种类(判断是否异常)
	var $order_serveType = $("#order_serveType");//服务类别
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
	
	//服务类别下拉
	$order_serveType.change(function(){
		var selectValue = $(this).val(); 
				$('#serveType').val(selectValue);
				$listForm.submit();
	}); 
	
	//订单种类(是否异常)
	$order_isAbnormal.change(function(){
		var selectValue = $(this).val(); 
				$('#isAbnormal').val(selectValue);
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

//隐藏框显示
function disp_hidden_d(d_id,d_width,d_height,p_id,id)
	{
		if((typeof(d_width) != "undefined") && (typeof(d_height) !="undefined"))
			{
				var w=d_width / 2;
				var h=d_height / 2;
				$("."+d_id).css({"width":  d_width + "px","height": d_height + "px","left":"50%","top":($(document).scrollTop()+50) + "px","margin-left": "-" + w + "px","margin-bottom":"50px"});
				if (self.frameElement && self.frameElement.tagName == "IFRAME")
					{
						$("."+d_id).css({"top":$(parent.document).scrollTop()+50})
						if($(parent.document).height()<(d_height+$(parent.document).scrollTop()))
							$("#main_ifr",parent.document).height((parseInt(d_height)+$(parent.document).scrollTop()+150)+"px");
					}
				
			}else{
				ifr_height("main_ifr");
			}
		$("#"+p_id).val(id);
		$("#"+d_id).toggle();	
	}	

</script>
</head>
<body>
<div class="m020">
	<table cellpadding="0" cellspacing="0" border="0" class="k_1 mtb30" id = "page_nav">
		<tr>

		</tr>
	</table>
	<form id="listForm" action="list.jhtml" method="get">
    <input type="hidden" id="paymentStatus" name="paymentStatus" value="${paymentStatus}" />
    <input type="hidden" id="serveState" name="serveState" value="${serveState}" />
	<input type="hidden" id="evaluate" name="evaluate" value="${evaluate}" />
	<input type="hidden" id="orderStatus" name="orderStatus" value="${orderStatus}" />
	<input type="hidden" id="serveType" name="serveType" value="${serveType}" />
	<input type="hidden" id="isAbnormal" name="isAbnormal" value="${isAbnormal}" />
	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="k_2">
		<tr>
			<td class="pa20">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">预约查询</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							[#if valid('export')]<a href="javascript:;" id = "download">导出</a>[/#if]
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td class="z14444444" colspan="2">
							<table cellpadding="0" cellspacing="0" border="0" width="100%">
								<tr>
									<td width="25%" height="40">订单编号：<input type="text"  name="sn" id="sn" value="${sn}" class="k_3 h30 bae1e1e1 w100 tac"></td>
									<td width="25%">服务项目：<input type="text" name="projectName" id="projectName" value="${projectName}" class="k_3 h30 bae1e1e1 w100 tac"></td>
									<td width="25%"></td>
									<td width="25%"></td>
								</tr>
								<tr>
									<td width="25%" height="40">服务医师：<input type="text" name="doctorName" id="doctorName" value="${doctorName}" placeholder="姓名" class="k_3 h30 bae1e1e1 w100 tac"></td>
									<td width="25%">下单用户：<input type="text"  name="memberName" id="memberName" value="${memberName}" placeholder="姓名" class="k_3 h30 bae1e1e1 w100 tac"></td>
									<td width="25%">患者姓名：<input type="text"  name="patientName" id="patientName" value="${patientName}" class="k_3 h30 bae1e1e1 w100 tac"></td>
									<td width="25%"></td>
								</tr>
								<tr>
									<td colspan="4" height="40">订单周期：
									<input type="text" placeholder="开始时间" id="startDate" name="startDate"[#if startDate??] value="${startDate?string("yyyy-MM-dd")}" [/#if] onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '#F{$dp.$D(\'endDate\')}'});"  class="k_3 h30 bae1e1e1 w100 tac"> 
									- 
									<input type="text" placeholder="结束时间" id="endDate" name="endDate" [#if endDate??] value="${endDate?string("yyyy-MM-dd")}" [/#if] onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', minDate: '#F{$dp.$D(\'startDate\')}'});" class="k_3 h30 bae1e1e1 w100 tac"></td>
								</tr>
								<tr>
									<td height="40">订单状态：<select id = "order_flag" name="" class="h30 w100 bae1e1e1">
											<option class = "" value="" >全部</option>
											<option class = "paymentStatus" value="unpaid" [#if paymentStatus=="unpaid"]selected = "selected"[/#if]>待付款</option>
											<option class="orderStatus" value="unconfirmed" [#if orderStatus == "unconfirmed"]selected = "selected"[/#if] >待确认</option>
											<option class ="serveState" value="await" [#if serveState == "await"]selected = "selected"[/#if]>待服务</option>
											<option class="orderStatus" value="record" [#if orderStatus == "record"]selected = "selected"[/#if]>待归档</option>
											<option class = "orderStatus" value="completed" [#if orderStatus == "completed"]selected = "selected"[/#if]>已完成</option>
										</select>
									</td>
									<td>服务类别：<select id="order_serveType" name="order_serveType" class="h30 w100 bae1e1e1">
											<option>全部</option>
											[#list serveTypes as serve]
											<option class = "serveType"[#if serveType == serve]selected='selected'[/#if]  value="${serve}">${message("Project.ServeType."+serve)}服务</option>
											[/#list]
										</select>
									</td>
									<td>订单种类：<select id="order_isAbnormal" name = "order_isAbnormal" class="h30 w100 bae1e1e1">
											<option [#if !isAbnormal??]selected="selected" [/#if]  value = "">全部订单</option>
											<option [#if isAbnormal??] [#if !isAbnormal] selected="selected" [/#if] [/#if]  value = "false">正常订单</option>
											<option [#if isAbnormal??] [#if isAbnormal] selected="selected" [/#if] [/#if] value = "true">异常订单</option>
										</select>
									</td>
									<td height="40" align="right">
										<input type="submit" value="查询" class="button_1_1 plr20 z16ffffff bg279fff h30">
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="20"></td>
					</tr>
					<tr>
						<td colspan="2">
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="">
								<tr class="bg279fff z14ffffff">
									<td class="btle3e3e3" align="center" width="40" height="50">序号</td>
									<td class="btle3e3e3" align="center" width="80">患者姓名</td>
									<td class="btle3e3e3" align="center" width="80">下单用户</td>
									<td class="btle3e3e3" align="center" width="200">服务项目</td>
									<td class="btle3e3e3" align="center" width="90">预约时间</td>
									<td class="btle3e3e3" align="center" width="70">订单状态</td>
									<td class="btle3e3e3" align="center" width="70">支付状态</td>
									<td class="btle3e3e3" align="center" width="130">订单编号</td>
									<td class="btle3e3e3" align="center">备注</td>
									<td class="btle3e3e3 bre3e3e3" align="center" width="150">操作</td>
								</tr>
								[#list page.content as order]
								<tr [#if order_index%2==0]class="bge7f4ff"[/#if]>
									<td class="btle3e3e3" align="center" height="50">${order_index+1}</td>
									<td class="btle3e3e3" align="center">${order.patientMember.name}</td>
									<td class="btle3e3e3" align="left"> ${order.consignee}<br><span class="z12939393"> ${order.phone}</span></td>
									<td class="btle3e3e3" align="left">${order.project.name}
										<br><span class="z12939393">${order.doctor.name} ${order.doctor.mobile}</span>
									</td>
									<td class="btle3e3e3" align="center">
									[#if order.workDayItem??] ${order.workDayItem.workDay.workDayDate?string("yyyy-MM-dd")}
									<br><span class="z12939393"> ${order.workDayItem.startTime}-${order.workDayItem.endTime}</span>[#else] - [/#if]
									</td>
									<td class="btle3e3e3" align="center">${message("Order.OrderStatus."+order.orderStatus)}</td>
									<td class="btle3e3e3" align="center">${message("Order.PaymentStatus."+order.paymentStatus)}</td>
									<td class="btle3e3e3" align="center">${order.sn}</td>
									<td class="btle3e3e3" align="left">${order.memo}</td>
									<td class="btle3e3e3 bre3e3e3" align="center">
										<a href="view.jhtml?id=${order.id}" ><input type="button" value="明细" class="bae1e1e1 w40 z12ffffff bgff8001"></a>
										 [#if order.orderStatus == "record"]
										    [#if order.serveType == "examine"]
										    	<input type="button" value="诊治" class="bae1e1e1 w40 z12ffffff bg279fff" onClick="disp_hidden_d('record',600,580,1)">
										    [#else]
										    	<input type="button" value="评估" class="bae1e1e1 w40 z12ffffff bg279fff" onClick="disp_hidden_d('assess',600,580,1)"><!--或为诊治按钮-->
										    [/#if]
										[/#if]
										<input type="button" value="回访" class="bae1e1e1 w40 z12ffffff bg32d3ea" onClick="disp_hidden_d('visit',600,450,'patientMemberId',${order.patientMember.id});">
									</td>
								</tr>
								[/#list]
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="bte3e3e3" height="10"></td>
					</tr>
					<tr>
						[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
							[#include "/mechanism/include/pagination.ftl"]
					  	[/@pagination] 
					</tr>
					<tr>
						<td></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	</form>      
</div>
<style>
	.assess,.record,.visit{z-index:99;display:block;background-color:#FFF;-moz-box-shadow:0px 0px 20px #979797; -webkit-box-shadow:0px 0px 20px #979797; box-shadow:0px 0px 20px #979797;-moz-border-radius:10px; -webkit-border-radius:10px; border-radius:10px;position:relative;padding:0 0px 30px;}	
</style>
<div id="assess">
	<div class="assess">
  		<form id="myform">
   		<table cellpadding="0" cellspacing="0" border="0" width="100%">
   			<tr>
   				<td align="center" class="k_4" colspan="2">
   					添加评估报告
  				</td>
   			</tr>
			<tr>
				<td height="50" class="bbe3e3e3 z14323232" align="right">评估时间</td>
				<td align="right" class="bbe3e3e3 pr65">2017-11-18</td><!--点击打开日历组件，可选择日期，默认为康复日期-->
   			</tr>
  			<tr>
   				<td height="50" width="90" align="right" class="z14323232">
   					病患名称
   				</td>
   				<td>
   					<input type="text" class="z14323232 inputkd9d9d9bgf6f6f6 h30 w420 m1020">
   				</td>
   			</tr>
   			<tr>
   				<td height="50" width="90" align="right" class="z14323232">
   					现况说明
   				</td>
   				<td>
   					<textarea class="z14323232 inputkd9d9d9bgf6f6f6 w420 h80 m1020"></textarea>
   				</td>
   			</tr>
   			<tr>
   				<td height="50" width="90" align="right" class="z14323232">
   					评估结果
   				</td>
   				<td>
   					<textarea class="z14323232 inputkd9d9d9bgf6f6f6 w420 h80 m1020"></textarea>
   				</td>
   			</tr>
   			<tr>
   				<td height="50" width="90" align="right" class="z14323232">
   					康复建议
   				</td>
   				<td>
   					<textarea class="z14323232 inputkd9d9d9bgf6f6f6 w420 h80 m1020"></textarea>
   				</td>
   			</tr>
   			<tr>
   				<td height="50" width="90" align="right" class="z14323232">
   					图片上传
   				</td>
   				<td>
   					<img src="${base}/resources/newmechanism/images/k.png" class="m1020">
   				</td>
   			</tr>
   			<tr>
   				<td align="center" colspan="2" height="60">
   					<input type="submit" value="确认" class="button_3">
   					&nbsp;　&nbsp;
					<input type="button" value="取消" class="button_4 z14fefefe" onClick="$('#myform')[0].reset();disp_hidden_d('assess','','','p_id');">
  					<input type="hidden" id="p_id" name="p_id" value="">
   				</td>
   			</tr>
   		</table>
   		</form>
    
	</div>
</div>
<div id="record">
	<div class="record">
  		<form id="recordform">
   		<table cellpadding="0" cellspacing="0" border="0" width="100%">
   			<tr>
   				<td align="center" class="k_4" colspan="2">
   					添加康复记录
  				</td>
   			</tr>
   			<tr>
				<td height="50" class="bbe3e3e3 z14323232" align="right">康复时间</td>
				<td align="right" class="bbe3e3e3 pr65">2017-11-18</td><!--点击打开日历组件，可选择日期，默认为康复日期-->
   			</tr>
   			<tr>
   				<td height="50" width="90" align="right" class="z14323232">
   					康复内容
   				</td>
   				<td>
   					<textarea class="z14323232 inputkd9d9d9bgf6f6f6 w420 h80 m1020"></textarea>
   				</td>
   			</tr>
   			<tr>
   				<td height="50" width="90" align="right" class="z14323232">
   					疗效总结
   				</td>
   				<td>
   					<textarea class="z14323232 inputkd9d9d9bgf6f6f6 w420 h80 m1020"></textarea>
   				</td>
   			</tr>
   			<tr>
   				<td height="50" width="90" align="right" class="z14323232">
   					图片上传
   				</td>
   				<td>
   					<img src="${base}/resources/newmechanism/images/k.png" class="m1020">
   				</td>
   			</tr>
   			<tr>
   				<td align="center" colspan="2" height="60">
   					<input type="submit" value="确认" class="button_3">
   					&nbsp;　&nbsp;
					<input type="button" value="取消" class="button_4 z14fefefe" onClick="$('#recordform')[0].reset();disp_hidden_d('record','','','r_id');">
  					<input type="hidden" id="r_id" name="r_id" value="">
   				</td>
   			</tr>
   		</table>
   		</form>
    
	</div>
</div>
<div id="visit">
	<div class="visit">
  		<form id="visitform" action="${base}/mechanism/visitMessage/save.jhtml" method="post">
  		<input type="hidden" id="patientMemberId" name="patientMemberId"  />
  		<input type="hidden" name="type" value="patient" />
  		
   		<table cellpadding="0" cellspacing="0" border="0" width="100%">
   			<tr>
   				<td align="center" class="k_4">
   					添加回访信息
  				</td>
   			</tr>
   			<tr>
   				<td>
   					<table cellpadding="0" cellspacing="0" border="0" width="90%" align="center" class="z14323232">
   						<tr>
   							<td height="50" class="bbe3e3e3">回访日期</td>
   							<td align="right" class="bbe3e3e3"><input type="text" name="visitDate" id="visitDate" value="${.now?string("yyyy-MM-dd")}" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd'});" class="baffffff tar z14444444 w100" readonly></td><!--点击打开日历组件，可选择日期-->
   						</tr>
   						<tr>
   							<td height="50" class="bbe3e3e3">回访员工</td>
   							<td align="right" class="bbe3e3e3">
   								<select name = "visitDoctorId" class="h30 w100 bg279fff z14ffffff baffffff"><!--默认为只有登录人自己，根据权限可以显示所有的人-->
   									[#list doctorMechanismRelations as doctorMechanismRelation]
   										<option value = "${doctorMechanismRelation.doctor.id}">${doctorMechanismRelation.doctor.name}</option>
   									[/#list]
   								</select>
   							</td>
   						</tr>
   						<tr>
   							<td height="50" class="bbe3e3e3">回访方式</td>
   							<td align="right" class="bbe3e3e3">
   								<select name = "visitType" class="h30 w100 bg279fff z14ffffff baffffff">
	   								[#list visitTypes as visitType]
	   									<option value = "${visitType}">${message("VisitMessage.VisitType."+visitType)}</option>
	   								[/#list]
   								</select>
   							</td>
   						</tr>
   						<tr>
   							<td class="bbe3e3e3">回访内容</td>
   							<td align="right" class="bbe3e3e3">
   								<textarea name = "message" class="z14323232 inputkd9d9d9bgf6f6f6 w450 h80 mtb10"></textarea>
   							</td>
   						</tr>
   						<tr>
   							<td class="bbe3e3e3">回访结果</td>
   							<td align="right" class="bbe3e3e3">
   								<textarea name = "resultMessage" class="z14323232 inputkd9d9d9bgf6f6f6 w450 h80 mtb10"></textarea></td>
   						</tr>
						<tr>
							<td align="center" colspan="2" height="60">
								<input type="submit" value="确认" class="button_3">
								&nbsp;　&nbsp;
								<input type="button" value="取消" class="button_4 z14fefefe" onClick="$('#visitform')[0].reset();disp_hidden_d('visit','','','v_id');">
								
							</td>
						</tr>
   					</table>
   				</td>
   			</tr>
   		</table>
   		</form>
    
	</div>
</div>
  		
</body>
</html>