<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>
<script>
$().ready(function() {

		
   var chargeType = '${doctorCategory.chargeType}';
	toggle_ladder(chargeType);

	var $inputForm = $("#inputForm");

// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
		},
		submitHandler: function(form) {
		
		if ($('input:radio[name="chargeType"]:checked').val() == "kmCharge") {
			if($('#kilometre').val()==""||$('#price').val()==""||$('#increaseKilometre').val()==""||$('#increasePrice').val()==""){
			$.message("warn", "${message("请填写公里收费相应的参数")}");
			return false;
			}
		}
			form.submit();
		}
		
	});

		$('#doctorCategoryId').change(function(){
		     var nameValue =  $(this).val();
		         if(nameValue == '其他'){
		         	$('#name').show();
		         	$('#name').val('');
		         }else{
		         	$('#name').hide();
		            $('#name').val(nameValue);
		         }
			 });


});
		
</script>
<script>
	function toggle_ladder(chargeType)
		{
			if("kmCharge" == chargeType)
				{
					$("#ladder").show();
				}
			else
				{
					$("#ladder").hide();
				}
		}
		
</script>



</head>
<body>
<div class="m020">
	<table cellpadding="0" cellspacing="0" border="0" class="k_1 mtb30" id="page_nav">
		<tr>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="k_2">
		<tr>
			<td class="pa20">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">新增职级</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							<a href="list.jhtml">返回</a>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td colspan="2">
							<form id="inputForm" action="update.jhtml" method="post" >
							<input type = "hidden" name="id" value = "${doctorCategory.id}" />
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z14444444">
								<tr>
									<td height="50" width="120" align="right">职级名称：<span class="z14ff0000">*</span></td>
									<td width="140">
										<select class="z14777777 inputkd9d9d9bgf6f6f6 h30 w100 m1020" id="doctorCategoryId" >
											<option>请选择</option>
											[#list doctorCategorys as pdoctorCategory]
											<option [#if doctorCategory.name == pdoctorCategory.name] selected [/#if] value="${pdoctorCategory.name}">${pdoctorCategory.name}</option>
											[/#list]
											<option>其他</option>
										</select>
									</td>
									<td>
										<input type="text" id="name" name="name" class="z14717171 inputkd9d9d9bgf6f6f6 h30 w210 dpn" placeholder="请输入医师职级名称" value="${doctorCategory.name}">
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right">上门费用：<span class="z14ff0000">*</span></td>
									<td colspan="2" class="ptb10 plr20">
									[#list chargeTypes as chargeType]
									<input type="radio" name="chargeType" value="${chargeType}" [#if doctorCategory.chargeType==chargeType]checked[/#if]  onClick="toggle_ladder('${chargeType}');"> ${message("DoctorCategory.ChargeType."+chargeType)}
										&nbsp;　&nbsp;
									[/#list]
									</td>
								</tr>
								<tr>
									<td colspan="3" class="bgf5fafe">
										<table cellpadding="0" cellspacing="0" border="0" width="100%" id="ladder" class="dpn">
											<tr>
												<td width="120"></td>
												<td class="pa10">
													路程在 <input type="text" id="kilometre" name="kilometre" value="${doctorCategory.doorCost.kilometre}" class="z14717171 inputkd9d9d9bgf6f6f6 h30 w40 tac"> 公里内收费 <input type="text" id="price" name="price"  value="${doctorCategory.doorCost.price}" class="z14717171 inputkd9d9d9bgf6f6f6 h30 w40 tac"> 元
													<br><br>
													每超过 <input type="text" id="increaseKilometre" value="${doctorCategory.doorCost.increaseKilometre}" name="increaseKilometre" class="z14717171 inputkd9d9d9bgf6f6f6 h30 w40 tac"> 公里增加　 <input type="text" id="increasePrice" name="increasePrice" value="${doctorCategory.doorCost.increasePrice}" class="z14717171 inputkd9d9d9bgf6f6f6 h30 w40 tac"> 元
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td height="50" width="120" align="right"></td>
									<td colspan="2">
										<input type="submit" value="保存" class="button_3 ml20">
										&nbsp;　&nbsp;
										<input type="button" value="重置" class="button_4 z14fefefe" onClick="$('#inputForm')[0].reset();">
									</td>
								</tr>
							</table>
							</form>
						</td>
					</tr>
					<tr>
						<td></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>

</body>
</html>