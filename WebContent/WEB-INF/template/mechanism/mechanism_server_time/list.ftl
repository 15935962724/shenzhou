<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>基本信息</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>

<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>

<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>


<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	
	[@flash_message /]
	
	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
			startTime: "required",
			endTime: "required",
		},
		submitHandler: function(form) {
				form.submit();
		}
	});
	
		//删除
	$('.delete').click(function() {
		var id = $(this).attr('data_id');
		console.log(id);
		if(confirm('确定要删除选中的信息吗？')){
			$.ajax({
					url: "delete.jhtml",
					type: "POST",
					data: {id:id},
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
<script>
//隐藏框显示
function disp_hidden_d(d_id,d_width,d_height,p_id,id)
	{
		if((typeof(d_width) != "undefined") && (typeof(d_height) !="undefined"))
			{
				var w=d_width / 2;
				var h=d_height / 2;
				$("."+d_id).css({"width":  d_width + "px","height": d_height + "px","left":"50%","top":"50%","margin-left": "-" + w + "px","margin-top":"-"+ h + "px"});
				if (self.frameElement && self.frameElement.tagName == "IFRAME")
					{
						if($(parent.document).height()<(d_height+$(parent.document).scrollTop()))
							$("#main_ifr",parent.document).height((parseInt(d_height)+$(parent.document).scrollTop()+150)+"px");
					}
				
			}else{
				ifr_height("main_ifr");
			}
		//$("#"+p_id).val(id);
		show(id);
		$("#"+d_id).toggle();	
	}	
	
	//回显服务时间信息
	function show(id)
	{
		$.ajax({
					url: "show.jhtml",
					type: "POST",
					data: {id:id},
					dataType: "json",
					cache: false,
					success: function(data) {
					    $('#name').val(data.name);
					    $('#startTime').val(data.startTime);
					    $('#endTime').val(data.endTime);
						$('#inputForm').append('<input type = "hidden" name="id" value = "'+data.id+'">');
						$('#inputForm').attr('action','update.jhtml');
						
					}
				});
	}	
	
	//更改最大预约天数
	function updateMaxDay()
	{
	var maxDay = parseInt($('#maxDay').val());
	if(maxDay<2){
		$.message("warn", "${message("最大预约天数必须大于3")}");
	    return;
	}
		$.ajax({
					url: "../mechanismSetup/updateMaxDay.jhtml",
					type: "POST",
					data: {maxDay:maxDay},
					dataType: "json",
					cache: false,
					success: function(message) {
					   	$.message(message);
						location.reload(true);
						
					}
				});
	}	
	
	//更改接单类型
	function updateOrderType()
	{
	var orderType = $("*[name='orderType']").val();
	
		$.ajax({
					url: "../mechanismSetup/updateOrderType.jhtml",
					type: "POST",
					data: {orderType:orderType},
					dataType: "json",
					cache: false,
					success: function(message) {
					   	$.message(message);
						location.reload(true);
						
					}
				});
	}	
	
	
	//更改收费标准(类型)
	function updateChargeType()
	{
	var chargeType = $("*[name='chargeType']").val();
	var chargeTypeRemark = $("#chargeTypeRemark").val();
	var objS = document.getElementById("isminute");
	var index = objS.selectedIndex; // 选中索引
	var text = objS.options[index].text; // 选中文本
	var value = objS.options[index].value; // 选中值
	
		$.ajax({
					url: "../mechanismSetup/updateChargeType.jhtml",
					type: "POST",
					data: {chargeType:chargeType,chargeTypeRemark:chargeTypeRemark,minute:value},
					dataType: "json",
					cache: false,
					success: function(message) {
					   	$.message(message);
						location.reload(true);
						
					}
				});
	}
	
	function gradeChange(){
		var objS = document.getElementById("grade");
		var index = objS.selectedIndex; // 选中索引

		var text = objS.options[index].text; // 选中文本

		var value = objS.options[index].value; // 选中值
        
        if(value=="course"){
        	$("#minute").show();
        }else{
        	$("#minute").hide();
        }
	}
	
</script>

</head>
<body>
   
   
<div class="m020">
	<table cellpadding="0" cellspacing="0" border="0" class="k_1 mtb30" id = "page_nav">
		<tr>
			
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="k_2 mtb30">
		<tr>
			<td class="pa20">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">服务时间</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							<a href="javascript:;" onClick="disp_hidden_d('visit',580,250);">新增</a>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td colspan="2">
							<table cellpadding="0" cellspacing="0" border="0" width="100%">
								<tr class="bg279fff z14ffffff">
									<td class="btle3e3e3" align="center" width="40" height="50">序号</td>
									<td class="btle3e3e3 plr10" align="center">班次名称</td>
									<td class="btle3e3e3" align="center" width="200">起始时间</td>
									<td class="btle3e3e3 bre3e3e3" align="center" width="120">操作</td>
								</tr>
								[#list mechanismServerTimes?sort_by("startTime") as mechanismServerTime]
								<tr [#if mechanismServerTime_index%2==0]class="bge7f4ff"[/#if]>
									<td class="btle3e3e3" align="center" height="50">${mechanismServerTime_index+1}</td>
									<td class="btle3e3e3 plr10" align="left">${mechanismServerTime.name}</td>
									<td class="btle3e3e3" align="center">${mechanismServerTime.startTime} - ${mechanismServerTime.endTime}</td>
									<td class="btle3e3e3 bre3e3e3" align="center">
										<input type="button" value="修改" class="bae1e1e1 w40 z12ffffff bgff8001" onClick="disp_hidden_d('visit',580,250,'v_id',${mechanismServerTime.id});">
										<input type="button" value="删除" class="bae1e1e1 w40 z12ffffff bg279fff delete" data_id="${mechanismServerTime.id}">
									</td>
								</tr>
								[/#list]
								<tr>
									<td colspan="4" class="bte3e3e3"></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="k_2 mtb30">
		<tr>
			<td class="pa20">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161" height="50">参数设置</td>
					</tr>
					<tr>
						<td>
							<table cellpadding="0" cellspacing="0" border="0" width="100%">
								<tr class="bg279fff z14ffffff">
									<td class="btle3e3e3" align="center" width="40" height="50">序号</td>
									<td class="btle3e3e3 plr10" align="center" width="250">参数名称</td>
									<td class="btle3e3e3" align="center" width="200">参数值</td>
									<td class="btle3e3e3" align="center">参数说明</td>
									<td class="btle3e3e3 bre3e3e3" align="center" width="120">操作</td>
								</tr>
								<tr class="bge7f4ff">
									<td class="btle3e3e3" align="center" height="50">1</td>
									<td class="btle3e3e3 plr10" align="left">开放预约最大天数</td>
									<td class="btle3e3e3" align="center">${mechanismSetup.maxday}天</td>									
									<td class="btle3e3e3 plr10" align="left">${mechanismSetup.maxdayRemark}</td>
									<td class="btle3e3e3 bre3e3e3" align="center">
										<input type="button" value="修改" class="bae1e1e1 w40 z12ffffff bgff8001" onClick="disp_hidden_d('record',580,300,'r_id',1);">
									</td>
								</tr>
								<tr>
									<td class="btle3e3e3 bbe3e3e3" align="center" height="50">2</td>
									<td class="btle3e3e3 bbe3e3e3 plr10" align="left">预约确认模式</td>
									<td class="btle3e3e3 bbe3e3e3" align="center">${message("MechanismSetup.OrderType."+mechanismSetup.orderType)}</td>
									<td class="btle3e3e3 bbe3e3e3 plr10" align="left">自动：机构自动接单，无须医师接单；手动：医师须手动承接患者下单</td>
									<td class="btle3e3e3 bbe3e3e3 bre3e3e3" align="center">
										<input type="button" value="修改" class="bae1e1e1 w40 z12ffffff bgff8001" onClick="disp_hidden_d('orderType',580,300,'r_id',1);" >
									</td>
								</tr>
								<tr class="bge7f4ff">
									<td class="btle3e3e3" align="center" height="50">3</td>
									<td class="btle3e3e3 plr10" align="left">收费标准</td>
									<td class="btle3e3e3" align="center">${message("MechanismSetup.ChargeType."+mechanismSetup.chargeType)}</td>									
									<td class="btle3e3e3 plr10" align="left">${mechanismSetup.chargeTypeRemark}</td>
									<td class="btle3e3e3 bre3e3e3" align="center">
										<input type="button" value="修改" class="bae1e1e1 w40 z12ffffff bgff8001" onClick="disp_hidden_d('chargeType',580,330,'r_id',1);">
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<div id="visit">
	<div class="visit">
  		<form id="inputForm"  action="save.jhtml" method="post" enctype="multipart/form-data">
   		<table cellpadding="0" cellspacing="0" border="0" width="100%">
   			<tr>
   				<td align="center" class="k_4">
   					服务时间设置
  				</td>
   			</tr>
   			<tr>
   				<td>
   					<table cellpadding="0" cellspacing="0" border="0" width="90%" align="center" class="z14323232 m0auto">
   						<tr>
   							<td height="50">班次名称</td>
   							<td align="left">
   								<input type="text" id="name" name="name" class="z14717171 inputkd9d9d9bgf6f6f6 h30 w370 m1020">
   							</td>
   						</tr>
   						<tr>
   							<td height="50">开始时间</td>
   							<td align="left">
   								<input type="text" id="startTime" name="startTime" class="z14717171 inputkd9d9d9bgf6f6f6 h30 w370 m1020" onfocus="WdatePicker({dateFmt: 'HH:mm'});">
   							</td>
   						</tr>
   						<tr>
   							<td height="50">结束时间</td>
   							<td align="left">
   								<input type="text" id="endTime" name="endTime" class="z14717171 inputkd9d9d9bgf6f6f6 h30 w370 m1020" onfocus="WdatePicker({dateFmt: 'HH:mm'});">
   							</td>
   						</tr>
						<tr>
							<td align="center" colspan="2" height="60">
								<input type="submit" value="确认" class="button_3">
								&nbsp;　&nbsp;
								<input type="button" value="取消" class="button_4 z14fefefe" onClick="$('#inputForm')[0].reset();disp_hidden_d('visit','','','v_id');">
								<input type="hidden" id="v_id" name="v_id" value="">
							</td>
						</tr>
   					</table>
   				</td>
   			</tr>
   		</table>
   		</form>
	</div>
</div>

<div id="record">
	<div class="record">
  		<!--<form id="recordform">-->
   		<table cellpadding="0" cellspacing="0" border="0" width="100%">
   			<tr>
   				<td align="center" class="k_4">
   					参数设置修改
  				</td>
   			</tr>
   			<tr>
   				<td>
   					<table cellpadding="0" cellspacing="0" border="0" width="90%" align="center" class="z14323232 m0auto">
   						<tr>
   							<td height="50">参数名称</td>
   							<td align="left">
   								<input type="text" class="z14717171 inputkd9d9d9bgf6f6f6 h30 w420 m1020" value="开放预约最大天数" readonly>
   							</td>
   						</tr>
   						<tr>
   							<td height="50">参数值</td>
   							<td align="left">
   								<input type="text" id="maxDay" class="z14717171 inputkd9d9d9bgf6f6f6 h30 w370 m1020" value="${mechanismSetup.maxday}">天
   							</td>
   						</tr>
   						<tr>
   							<td height="50">参数说明</td>
   							<td align="left">
   								<textarea class="z14323232 inputkd9d9d9bgf6f6f6 w420 h80 m1020" readonly>${mechanismSetup.maxdayRemark}</textarea>
   							</td>
   						</tr>
						<tr>
							<td align="center" colspan="2" height="60">
								<input type="button" onClick="updateMaxDay();" value="确认" class="button_3">
								&nbsp;　&nbsp;
								<input type="button" value="取消" class="button_4 z14fefefe" onClick="disp_hidden_d('record','','','r_id');">
								<input type="hidden" id="r_id" name="r_id" value="">
							</td>
						</tr>
   					</table>
   				</td>
   			</tr>
   		</table>
   		<!--</form>-->
	</div>
</div>


<div id="orderType">
	<div class="orderType">
  		<!--<form id="recordform">-->
   		<table cellpadding="0" cellspacing="0" border="0" width="100%">
   			<tr>
   				<td align="center" class="k_4">
   					参数设置修改
  				</td>
   			</tr>
   			<tr>
   				<td>
   					<table cellpadding="0" cellspacing="0" border="0" width="90%" align="center" class="z14323232 m0auto">
   						<tr>
   							<td height="50">参数名称</td>
   							<td align="left">
   								<input type="text" class="z14717171 inputkd9d9d9bgf6f6f6 h30 w420 m1020" value="预约确认模式" readonly>
   							</td>
   						</tr>
   						<tr>
   							<td height="50">参数值</td>
   							<td align="left">
   								<select name = "orderType" class="z14717171 inputkd9d9d9bgf6f6f6 h30 w80 m1020">
	   								[#list orderTypes as orderType]
	   									<option value="${orderType}">${message("MechanismSetup.OrderType."+orderType)}</option>
	   								[/#list]
   								</select>
   							</td>
   						</tr>
   						<tr>
   							<td height="50">参数说明</td>
   							<td align="left">
   								<textarea class="z14717171 inputkd9d9d9bgf6f6f6 w420 h80 m1020" readonly>${mechanismSetup.orderTypeRemark}</textarea>
   							</td>
   						</tr>
   						
						<tr>
							<td align="center" colspan="2" height="60">
								<input type="button" onClick="updateOrderType();" value="确认" class="button_3">
								&nbsp;　&nbsp;
								<input type="button" value="取消" class="button_4 z14fefefe" onClick="disp_hidden_d('orderType','','','r_id');">
							</td>
						</tr>
   					</table>
   				</td>
   			</tr>
   		</table>
   		<!--</form>-->
	</div>
</div>



<div id="chargeType">
	<div class="chargeType">
  		<!--<form id="recordform">-->
   		<table cellpadding="0" cellspacing="0" border="0" width="100%">
   			<tr>
   				<td align="center" class="k_4">
   					参数设置修改
  				</td>
   			</tr>
   			<tr>
   				<td>
   					<table cellpadding="0" cellspacing="0" border="0" width="90%" align="center" class="z14323232 m0auto">
   						<tr>
   							<td height="50">参数名称</td>
   							<td align="left">
   								<input type="text" class="z14717171 inputkd9d9d9bgf6f6f6 h30 w420 m1020" value="收费标准(类型)" readonly>
   							</td>
   						</tr>
   						<tr>
   							<td height="50">参数值</td>
   							<td align="left">
   								<select name = "chargeType" class="z14717171 inputkd9d9d9bgf6f6f6 h30 w80 m1020" id="grade" onchange="gradeChange()">
	   								[#list chargeTypes as chargeType]
	   									<option value="${chargeType}">${message("MechanismSetup.ChargeType."+chargeType)}</option>
	   								[/#list]
   								</select>
   							</td>
   						</tr>
   						<tr>
   							<td height="50">参数说明</td>
   							<td align="left">
   								<textarea class="z14717171 inputkd9d9d9bgf6f6f6 w420 h80 m1020" id = "chargeTypeRemark" name="">${mechanismSetup.chargeTypeRemark}</textarea>
   							</td>
   						</tr>
   						<tr id="minute">
   							<td height="50">标准课节</td>
   							<td align="left">
   								<select name = "isminute" id="isminute" class="z14717171 inputkd9d9d9bgf6f6f6 h30 w80 m1020">
	   									
	   									[#list mechanismSetup as setup]
											<option value="0.5" [#if setup.isMinute == 0.5] selected="selected"[/#if]>0.5</option>
											<option value="1.0" [#if setup.isMinute == 1.0] selected="selected"[/#if]>1</option>
										[/#list]
										
   								</select>
   							</td>
   						</tr>
						<tr>
							<td align="center" colspan="2" height="60">
								<input type="button" onClick="updateChargeType();" value="确认" class="button_3">
								&nbsp;　&nbsp;
								<input type="button" value="取消" class="button_4 z14fefefe" onClick="disp_hidden_d('chargeType','','','r_id');">
							</td>
						</tr>
   					</table>
   				</td>
   			</tr>
   		</table>
   		<!--</form>-->
	</div>
</div>

</body>
</html>