<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>综合统计 - Powered By HaoKangHu</title>
<meta name="author" content="HaoKangHu Team" />
<meta name="copyright" content="HaoKangHu" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {
	
	
	var $inputForm = $("#inputForm");
	var $mechanismId = $("#mechanismId");
	
	[@flash_message /]
	
	$mechanismId.bind("change",function(){
            $inputForm.submit();
   });
	
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo;统计分析
	</div>
	<form id="inputForm" action="view.jhtml" method="get">		
	<table class="input">
		<tr>
			<th>
				门店名称:
			</th>
			<td colspan="5">
				<select id = "mechanismId" name="mechanismId" >
							<option >全部</option>
						[#list mechanisms as mechanism]
							<option value="${mechanism.id}">${mechanism.name}</option>
						[/#list]
					</select>
			</td>
		</tr>
		<tr>
			<th>
				现金充值总额:
			</th>
			<td>
				${currency(sumRecharge)!"0"}
			</td>
			<th>
				会员总数:
			</th>
			<td>
				${sumMember}
			</td>
			<th>
				康复总课时:
			</th>
			<td>
				${sumQuantity!"0"}
			</td>
		</tr>
		<tr>
			<th>
				现金消费总额:
			</th>
			<td>
				0
			</td>
			<th>
				在康复会员数:
			</th>
			<td>
				${countHealth} 
			</td>
			<th>
				康复师总数:
			</th>
			<td>
				${sumDoctor}
			</td>
		</tr>
		<tr>
			<th>
				爱心账户余额:
			</th>
			<td>
				0
			</td>
			<th>
				已康复会员数:
			</th>
			<td>
				${countAlreadyRecovery} 
			</td>
			<th>
				会员总积分:
			</th>
			<td>
				0
			</td>
		</tr>
		<tr>
			<th>
				
			</th>
			<td>
				
			</td>
			<th>
				休疗程会员数:
			</th>
			<td>
				${countHughCourse} 
			</td>
			<th>
				
			</th>
			<td>
				
			</td>
		</tr>
		<tr>
			<th>
				
			</th>
			<td>
				
			</td>
			<th>
				挂起会员数:
			</th>
			<td>
				${countHang} 
			</td>
			<th>
				
			</th>
			<td>
				
			</td>
		</tr>
	</table>
	</form>
</body>
</html>