<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("Common.Main.title")}</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/chart.bundle.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>

<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	
	[@flash_message /]
	
	$("#flag").change(function(){
		if($("#flag").val()=="collarcoupon")
			{
				$("#special").hide();
				$("#expiry").show();
			}
		else
			{
				$("#special").show();
				$("#expiry").hide();
			}
	})
	
	
	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
			minConsumptionPrice: {
				required: true,
				positive : true,
				min:1
			},
			reductionPrice: {
				required: true,
				positive : true,
				min:1
			},
			effectiveDay:"positive",
		},
		
		submitHandler: function(form) {
		
		var reductionPrice = $('#reductionPrice').val();
		var minConsumptionPrice = $('#minConsumptionPrice').val();
		var flag = $('#flag').val();
		
		if(flag!="collarcoupon"){
		var beginDate = $('#beginDate').val();
		var endDate = $('#endDate').val();
		//console.log(beginDate);
		   if(endDate==""||beginDate==""){
			   	$.message("warn", "${message("优惠时间必填")}");
				return false;
		   }
		}else{
			var effectiveDay = $('#effectiveDay').val();
			 if(effectiveDay==""){
			   	$.message("warn", "${message("有效期限必填")}");
				return false;
		   }
		}
		
		if(parseInt(reductionPrice)>parseInt(minConsumptionPrice)){
		$.message("warn", "${message("立减金额不得大于最低消费金额")}");
			return false;
		}
		
		if ($("input[name='serverProjectCategoryIds']:checked").length == 0) {
			$.message("warn", "${message("至少选择一种有效项目")}");
			return false;
		}
			form.submit();
		}
	});
	
});
</script>

<style>
	#pb ul{width: 450px;}
	#pb li{margin-left: 0px; margin-right: 10px; width: 210px;}	
</style>


</head>
<body>
<div class="m020">
	<table cellpadding="0" cellspacing="0" border="0" class="k_1 mtb30" id = "page_nav">
		<tr>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="k_2">
		<tr>
			<td class="pa20">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">优惠卷管理</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							<a href="list.jhtml">返回</a>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td colspan="2" class="bgf5fafe plr10 ptb10">
						<form id="inputForm" action="save.jhtml" method="post" >
							<table cellpadding="0" cellspacing="0" border="0" width="100%" id="pb">
								<tr>
									<td height="50" width="120" align="right">
										所属分类：
										<span class="z14ff0000">*</span>
									</td>
									<td align="left">
										<select name="couponType" class="h30 w100 inputkd9d9d9bgf6f6f6 m1020" id="flag">
											[#list couponTypes as couponType]
											<option value = "${couponType}" >${message("Coupon.CouponType."+couponType)}</option>
											[/#list]
										</select>
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">
										优惠卷名称：
										<span class="z14ff0000">*</span>
									</td>
									<td align="left">
										<input type="text" name="name" class="z14323232 inputkd9d9d9bgf6f6f6 h30 w370 m1020 plr10">
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right" valign="top">
										有效项目：
										<span class="z14ff0000">*</span>
									</td>
									<td align="left" class="plr20" valign="top">
										<ul>
										[#list serverProjectCategorys as serverProjectCategory]
											<li>
												<input type="checkbox" name="serverProjectCategoryIds" value="${serverProjectCategory.id}"> ${serverProjectCategory.name}
											</li>
										[/#list]
										</ul>
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">
										最低消费：
										<span class="z14ff0000">*</span>
									</td>
									<td align="left">
										<input type="number" id="minConsumptionPrice" name="minConsumptionPrice" placeholder="单位(元)" class="z14323232 inputkd9d9d9bgf6f6f6 h30 m1020 plr10 w370">
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">
										立减金额：
										<span class="z14ff0000">*</span>
									</td>
									<td align="left">
										<input type="number" id="reductionPrice" name="reductionPrice" placeholder="单位(元)" class="z14323232 inputkd9d9d9bgf6f6f6 h30 m1020 plr10 w370">
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<table cellpadding="0" cellspacing="0" border="0" width="100%" id="special">
											<tr>
												<td height="50" width="120" align="right">
													优惠时间：
													<span class="z14ff0000">*</span><!--格式：年-月-日-->
												</td>
												<td align="left">
													<input type="text" id="beginDate" name="beginDate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '#F{$dp.$D(\'endDate\')}'});" class="z14323232 inputkd9d9d9bgf6f6f6 h30 m1020 plr10 w150">
													-
													<input type="text" id="endDate" name="endDate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', minDate: '#F{$dp.$D(\'beginDate\')}'});" class="z14323232 inputkd9d9d9bgf6f6f6 h30 m1020 plr10 w150">
												</td>
											</tr>
										</table>
										<table cellpadding="0" cellspacing="0" border="0" width="100%" id="expiry" class="dpn">
											<tr>
												<td height="50" width="120" align="right">
													有效期限：
													<span class="z14ff0000">*</span>
												</td>
												<td align="left">
													<input type="number" id="effectiveDay" name="effectiveDay" placeholder="单位(天)" class="z14323232 inputkd9d9d9bgf6f6f6 h30 m1020 plr10 w370">
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right"></td>
									<td align="left">
										<input type="submit" value="提交" class="button_3 ml20">
										&nbsp;　&nbsp;
										<input type="button" value="取消" class="button_4 z14fefefe">

									</td>
								</tr>
							</table>
							</form>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
</body>
</html>