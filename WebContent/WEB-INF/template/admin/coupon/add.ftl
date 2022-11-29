<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.coupon.add")} - Powered By HaoKangHu</title>
<meta name="author" content="HaoKangHu Team" />
<meta name="copyright" content="HaoKangHu" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	
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
	});
	
	[@flash_message /]
	
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
			   	$.message("warn", "优惠时间必填");
				return false;
		   }
		}else{
			var effectiveDay = $('#effectiveDay').val();
			 if(effectiveDay==""){
			   	$.message("warn", "有效期限必填");
				return false;
		   }
		}
		
		if(parseInt(reductionPrice)>parseInt(minConsumptionPrice)){
		$.message("warn", "立减金额不得大于最低消费金额");
			return false;
		}
		
		if ($("input[name='serverProjectCategoryIds']:checked").length == 0) {
			$.message("warn", "至少选择一种有效项目");
			return false;
		}
			form.submit();
		}
		
	});
	
});
</script>

</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.coupon.add")}
	</div>
	<form id="inputForm" action="save.jhtml" method="post">
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="${message("admin.coupon.base")}" />
			</li>
		</ul>
		<div class="tabContent">
			<table class="input">
			<tr>
				<th>
					所属分类:
				</th>
				<td>
					<select name="couponType" id="flag">
						<option value="">请选择...</option>
						[#list couponTypes as couponType]
						<option value="${couponType}">${message("Coupon.CouponType."+couponType)}</option>
						[/#list]
					</select>
				</td>
			</tr>
				<tr>
					<th>
						<span class="requiredField">*</span>优惠券名称:
					</th>
					<td>
						<input type="text" name="name" class="text" maxlength="200" />
					</td>
				</tr>
				<tr>
					<th>
						<span class="requiredField">*</span>有效项目:
					</th>
					<td>
					   [#list serverProjectCategories as serverProjectCategorie]
					   		<input type="checkbox" name="serverProjectCategoryIds" value="${serverProjectCategorie.id}">${serverProjectCategorie.name}
					   [/#list]
					</td>
				</tr>
				<tr>
					<th>
						<span class="requiredField">*</span>最低消费:
					</th>
					<td>
					   <input type="text" id = "minConsumptionPrice" name="minConsumptionPrice" placeholder="单位(元)" class="text" maxlength="200" />单位(元)
					</td>
				</tr>
				<tr>
					<th>
						<span class="requiredField">*</span>立减金额:
					</th>
					<td>
					   <input type="text" id = "reductionPrice" name="reductionPrice" placeholder="单位(元)" class="text" maxlength="200" />单位(元)
					</td>
				</tr>
				<tr id = "special">
					<th>
						优惠时间:
					</th>
					<td>
						<input type="text" id="beginDate" name="beginDate" class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', maxDate: '#F{$dp.$D(\'endDate\')}'});" />
						-
						<input type="text" id="endDate" name="endDate" class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', minDate: '#F{$dp.$D(\'beginDate\')}'});" />
					</td>
				</tr>
				<tr id="expiry" style="display: none;">
					<th>
						有效期限:
					</th>
					<td>
						<input type="text" id="effectiveDay" name="effectiveDay" placeholder="单位(天)" class="text" maxlength="9" />单位(天)
					</td>
				</tr>
			</table>
		</div>
		
		<table class="input">
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="${message("admin.common.submit")}" />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>