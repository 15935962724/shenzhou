<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("Common.Main.title")}</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/chart.bundle.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>
<script>
$(function(){

	var $listForm = $('#listForm');
	var $couponType = $('#couponType');
	var $isType = $('#isType');
	
	//优惠券状态
	$isType.bind("change",function(){
    		var typeValue = $(this).val(); 
    		if(typeValue == 'isEnabled'){  //已停止
    			$('#'+typeValue).val('false');
    			$listForm.submit();
    		}
    		if(typeValue == 'isConduct'){  //进行中
    			$('#isConduct').val('true');
    			$('#isEnabled').val('true');
    			$listForm.submit();
    		}
    		
    		if(typeValue == 'isEnd'){  //已结束
    			$('#isEnd').val('true');
    			$listForm.submit();
    		}
            
   });
    
	//优惠券类型
	$couponType.bind("change",function(){
            $listForm.submit();
   });
     
	//明细展开
	$(".detailed").click(function(){
		$(".detailed").parents("tr").next("tr").hide();		
		$(this).parents("tr").next("tr").show();
		ifr_height("main_ifr");
	});
	
	//启用or停用
	$(".stop").click(function(){
		var id = $(this).attr("data_id");
		
		$.ajax({ 
				url: "update.jhtml",
				type: "POST",
				data: {
					"id":id
				},
				datatype:"text",
				cache: false,
				success: function(message){
				window.location.reload();
		    	},
		    	error:function(message){
		    	alert(message);
		    	}
		    });

		
	});
	
	
	
	
})
</script>
</head>
<body>
<div class="m020">
	<table cellpadding="0" cellspacing="0" border="0" class="k_1 mtb30" id="page_nav">
		<tr>
		</tr>
	</table>
	<form id = "listForm" action="list.jhtml" method = "get">
	<input type = "hidden" id="isConduct" name="isConduct" value="false" />
	<input type = "hidden" id="isEnd" name="isEnd" value="false" />
	<input type = "hidden" id="isEnabled" name="isEnabled" value="true" />
	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="k_2">
		<tr>
			<td class="pa20">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">优惠卷管理</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							<a href="add.jhtml">新增优惠卷</a>
							[#if valid('export')]<a href="javascript:;">导出</a>[/#if]
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td class="z14444444" colspan="2" align="right">
						<select id ="couponType" name="couponType" class="h30 w100 bae1e1e1">
							<option value="">全部优惠卷</option>
							[#list couponTypes as type]
							<option [#if couponType == type] selected [/#if] value="${type}">${message("Coupon.CouponType."+type)}</option>
							[/#list]
						</select>
						<select id = "isType" class="h30 w100 bae1e1e1">
							<option value="">全部状态</option>
							<option value="isConduct" >进行中</option>
							<option value="isEnd" >已结束</option>
							<option value="isEnabled" >已停止</option>
						</select>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td colspan="2">
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="">
								<tr class="bg279fff z14ffffff">
									<td class="btle3e3e3" align="center" width="40" height="50">序号</td>
									<td class="btle3e3e3" align="center" width="80">优惠卷类</td>
									<td class="btle3e3e3" align="center">活动名称</td>
									<td class="btle3e3e3" align="center" width="100">发放时间</td>
									<td class="btle3e3e3" align="center" width="190">有效期限</td>
									<td class="btle3e3e3" align="center" width="80">是否启用</td>
									<td class="btle3e3e3 bre3e3e3" align="center" width="220">操作</td>
								</tr>
								[#list page.content as coupon]
								<tr [#if coupon_index%2==0] class="bge7f4ff" [/#if]>
									<td class="btle3e3e3" align="center" height="50">${coupon_index+1}</td>
									<td class="btle3e3e3" align="center">${message("Coupon.CouponType."+coupon.couponType)}</td>
									<td class="btle3e3e3 plr10">${coupon.name}</td>
									<td class="btle3e3e3 plr10" align="center">
										${coupon.createDate?string("yyyy-MM-dd")} 
										<br>
										<span class="z12999999">
											${coupon.createDate?string("HH:mm:ss")} 
										</span>
									</td>
									<td class="btle3e3e3 plr10">
									[#if coupon.couponType == "collarcoupon" ]自领券之日起,有效期为${coupon.effectiveDay}天[#else]${coupon.beginDate?string("yyyy-MM-dd")} - ${coupon.endDate?string("yyyy-MM-dd")}[/#if]
									</td>
									<td align="center" class="btle3e3e3 plr10">
										${coupon.isEnabled?string("是", "否")}
									</td>
									<td class="btle3e3e3 bre3e3e3" align="center">
										<input type="button" value="明细" class="bae1e1e1 w40 z12ffffff bgff8001 detailed">
										<!--<a href="edit.jhtml?couponId=${coupon.id}"><input type="button" value="编辑" class="bae1e1e1 w40 z12ffffff bg279fff"></a>-->
										<a href="view.jhtml?couponId=${coupon.id}"><input type="button" value="领用明细" class="bae1e1e1 w60 z12ffffff bg32d3ea"></a>
										<input type="button"  value="${message(coupon.isEnabled?string("停用","启用"))} " data_id="${coupon.id}" class="bae1e1e1 w40 z12ffffff bg32d3ea stop" >
									</td>
								</tr>
								<tr [#if coupon_index%2==0] class="bge7f4ff" [/#if] style="display:none;" >
									<td colspan="7" class="btle3e3e3 bre3e3e3 pa20">
										活动名称：${coupon.name}<br>
										创建时间：${coupon.createDate?string("yyyy-MM-dd HH:mm:ss")}<br>
										参与项目：<br>
										[#list coupon.serverProjectCategorys as serverProjectCategory]
											&nbsp;&nbsp;${serverProjectCategory.name}<br>
										[/#list]
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
				</form>
			</td>
		</tr>
	</table>
</div>
</body>
</html>