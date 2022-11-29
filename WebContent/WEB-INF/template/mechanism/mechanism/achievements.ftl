<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>绩效管理</title>
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

<script>

	function toggle_ladder()
		{
			//if($('input:radio[name="flag"]:checked').val() == "阶梯分配"){
			//		$("#ladder").css({"display":"block"});
			//	}
			//	else{
			//		$("#ladder").css({"display":"none"});
			//	}
			
				var node=$('#ladder');
				if(node.is(':hidden')){　　//如果node是隐藏的则显示node元素，否则隐藏
				　　node.show();　
				}else{
				　　node.hide();
				}
				ifr_height("main_ifr");
		}
		
		//机构、医师劳务分配占比设置
		function updateProportions(){
			var achievementsType = $('input:radio[name="achievementsType"]:checked').val();
			var mechanismProportion = parseFloat($('#mechanismProportion').val());
			var doctorProportion = parseFloat($('#doctorProportion').val());
			
			
			
			$.ajax({
					url: "updateProportion.jhtml",
					type: "POST",
					data: {
					achievementsType : achievementsType,
					mechanismProportion : mechanismProportion,
					doctorProportion : doctorProportion,
					},
					dataType: "json",
					cache: false,
					success: function(message) {
						$.message(message);
						location.reload(true);
					}
				});	
		}
		
		//机构、医师劳务分配占比设置
		function updateMoneyAndProportion(){
				var reduceMoney=$('#reduceMoney').val();
				var reduceProportion=$('#reduceProportion').val();
				$.ajax({
					url: "updateMoneyAndProportion.jhtml",
					type: "POST",
					data: {
					reduceMoney:reduceMoney,
					reduceProportion:reduceProportion
					},
					dataType: "json",
					cache: false,
					success: function(message) {
						$.message(message);
						location.reload(true);
					}
				});	
		}
		
</script>

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
						<td class="z20616161 bb1dd4d4d4" height="50">绩效管理</td>
						<td class="z16279fff bb1dd4d4d4" align="right"></td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td colspan="2" height="60">
						[#list achievementsTypes as achievementsType]
						<input type="radio" name="achievementsType" value="${achievementsType}" [#if mechanism.mechanismSetup.achievementsType == achievementsType] checked [/#if] onClick="toggle_ladder();"> ${message("MechanismSetup.AchievementsType." +achievementsType)}
						&nbsp;　&nbsp;
						[/#list]
						</td>
					</tr>
					<tr>
						<td colspan="2" height="30">
							机构、医师劳务分配占比设置
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="">
								<tr class="bg279fff z14ffffff">
									<td class="btle3e3e3" align="center" width="33%" height="50">机构占有比率</td>
									<td class="btle3e3e3" align="center" width="33%">医师占用比率</td>
									<td class="btle3e3e3 bre3e3e3" align="center" width="33%">操作</td>
								</tr>
								<tr class="bge7f4ff">
									<td class="btle3e3e3 bbe3e3e3" align="center" height="50">
										<input type="text" class="k_3 h30 bae1e1e1 w100 tac" id="mechanismProportion" name="mechanismProportion" value="${mechanism.mechanismSetup.mechanismProportion}">&nbsp;%
									</td>
									<td class="btle3e3e3 bbe3e3e3" align="center">
										<input type="text" class="k_3 h30 bae1e1e1 w100 tac" id="doctorProportion" name="doctorProportion" value="${mechanism.mechanismSetup.doctorProportion}">&nbsp;%
									</td>
									<td class="btle3e3e3 bbe3e3e3 bre3e3e3" align="center">
										<input type="button" value="设置" onclick="updateProportions()" class="bae1e1e1 w40 z12ffffff bg279fff">
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td height="20" colspan="2"></td>
					</tr>
					<tr>
						<td colspan="2">
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z14444444 bgfafafa" id="ladder" style="display: none;">
								<tr>
									<td class="z14717171 ptb10 lh170 plr20" valign="top" height="60">
										<span class="z16279fff"><b>参数设置说明：</b></span><br>
										月度目标：日工作目标 × 月工作天数
									</td>
								</tr>
								<tr>
									<td class="z14717171 ptb10 lh170 plr20" valign="top">
										<span class="z16646464"><b>阶梯考核设置：</b></span><br>
										实际完成工作收入与月度目标每降低
										<input type="text" class="k_3 h30 bae1e1e1 w40 tac" id="reduceMoney" name="reduceMoney" value="${mechanism.mechanismSetup.reduceMoney}">
										元，员工占比降低
										<input type="text" class="k_3 h30 bae1e1e1 w40 tac" id="reduceProportion" name="reduceProportion" value="${mechanism.mechanismSetup.reduceProportion}">
										%，直至占比降至0%截至。
										<input type="button" value="确认修改" onClick="updateMoneyAndProportion();" class="bae1e1e1 w65 z12ffffff bg279fff">
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
</body>
</html>