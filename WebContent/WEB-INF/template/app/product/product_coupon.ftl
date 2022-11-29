<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<title>${message("Common.Main.title")}</title>
<title>保障说明</title>
<link href="${base}/resources/app/css/product.css" rel="stylesheet" type="text/css" />
<script src="http://app1.haokanghu.cn/resources/mechanism/js/jquery.js"></script>
<script src="${base}/resources/app/js/jquery.rotate.min.js"></script>
<script>
	$(function(){
		$(".limitInfo").click(function(){
			var num = $(".h1").height() * 1.4 + 1;
			console.log($(this).find("div").height());
			if($(this).find("div").height()<=num)
				{
					$(this).find("div").css({"height":"auto","max-height":"none"});
					$(this).find("img").rotate({animateTo: -180});
				}
			else
				{
					$(this).find("div").css({"height":"1.4rem","max-height" : num + "px"});
					$(this).find("img").rotate({animateTo: 0});
				}
		})
	})
</script>
</head>

<body>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td class="h1">
		</td>
	</tr>

	<tr>
		<td class="pr pb1">
			<img src="${base}/resources/app/images/jfdh_details_ggcs_2@1x.png" class="pa gimg">
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
					[#list coupon as coupon]
						<tr>
							<td>
								<table cellpadding="0" cellspacing="0" border="0" class="gtable pr ">
									<tr>
										<td class="h62917 pa05 w6 oh" align="center" valign="middle">
											<span class="z166674c98f6">￥</span><span class="z283334c98f6">${coupon.price}</span><br>
											<span class="z14c98f6 wsn w57 m0auto oh dib">${message("Coupon.CouponType."+coupon.couponType)}</span>
										</td>
										<td width="2" bgcolor="#4c98f6"></td>
										<td class="pa05" valign="middle">
											<table cellpadding="0" cellspacing="0" border="0" width="100%">
												<tr>
													<td colspan="2"><div class="h2 bg4c98f6 tac z13333ffffff oh w14917 lh2">${coupon.name}</div></td>
												</tr>
												<tr>
													<td class="z14c98f6 cdate pt08" align="left">${coupon.beginDate}-${coupon.endDate}</td>
													<td class="w5 oh pt08">
														<span class="cnum">数量:${coupon.number}</span>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td colspan="3" class="h18333"></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table cellpadding="0" cellspacing="0" border="0" class="w24374 pr ml108333333">
									<tr>
										<td class="limitinfotd">
											<table cellpadding="0" cellspacing="0" border="0" width="95%" class="limitInfo m0auto">
												<tr>
													<td valign="top" class="z1a9a9a9">
														
															<div class="limitinfodiv">限：
																[#list coupon.mechanism as mechanism]
																	${mechanism.name}、
																[/#list]	
															</div>
														
													</td>
													<td valign="top" align="right">
														<img src="${base}/resources/app/images/j.png" class="jimg">
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					[/#list]		
			</table>
		</td>
	</tr>
	
</table> 
</body>
</html>
