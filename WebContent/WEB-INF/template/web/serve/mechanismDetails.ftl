<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<title>好康护 - 让幸福简单起来</title>
<link rel="stylesheet" href="${base}/resources/web/light7/css/light7.min.css">
<link rel="stylesheet" href="${base}/resources/web/light7/css/light7-swiper.min.css">
<link href="${base}/resources/web/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/web/font/iconfont.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/web/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/web/light7/js/light7.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${base}/resources/web/light7/js/light7-swiper.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${base}/resources/web/js/js.js"></script>
<style>
	.page{background: #ffffff;}
	.bar{height:3.125rem; background: #fff;}
	.bar .icon {padding: 1.0625rem .1rem;line-height: 1rem;}
	.title{line-height: 3.125rem;}
	.bar-nav~.content{top: 3.125rem;}
	.content-block {margin: 0.825rem 0;}
</style>
<script>
$(function () {		
	
});
	

</script>

</head>

<body>
<!-- page 容器 -->
<div class="page">
	<!-- 标题栏 -->
	<header class="bar bar-nav">
		<a href="javascript:;" onclick="javascript:history.back(-1);" class="icon icon-left pull-left"></a>
		<h1 class="title lh3125">机构详情</h1>
	</header>

	<!-- 这里是页面内容区 -->
	<div class="content">
		<table cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
				<td style="height: auto;"><img src="${mechanism.introduceImg}" class="w2666666666 hauto"></td>
			</tr>
			<tr>
				<td class="z_1_323232_1 lh170 paa05">
				<span class="z_1138_323232_1">${mechanism.name}</span>
				<br>
				${mechanism.introduce}<span class="z_1_4a4a4a_1">
				</span></td>
			</tr>
			
			[#list doctorList as doctor]
			
			<tr>
				<td class="h008333 bgf0f1f0"></td>
			</tr>
			<tr>
				<td class="patb05">
					<a href="${base}/web/doctor/doctorDetails.jhtml?doctorId=${doctor.id}">
					<table cellpadding="0" cellspacing="0" border="0" width="95%" class="m0auto">
						<tr>
							<td valign="top" class="w6 pr"><img src="${doctor.logo}" class="imgw120 br5"><img src="${base}/resources/web/images/sm.png" class="imgw19"></td>
							<td valign="top">
								<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z_0782_666666_1 tlf">
									<tr>
										<td class="z_1_323232_1 tdwb">
											${doctor.name} <span class="z_0782_666666_1">女</span> <span class="tag1">${doctor.doctorCategory.name}</span>
										</td>
									</tr>
									<tr>
										<td>
											<table cellpadding="0" cellspacing="0" border="0" width="100%">
												<tr>
													<td valign="top">
														<table cellpadding="0" cellspacing="0" border="0" width="95%" class="tla">
															<tr>
																<td class="lh085">${doctor.introduce}</td>
															</tr>
														</table>
													</td>
													<td class="w6" align="right">
														<table cellpadding="0" cellspacing="0" border="0" width="100%" class="lh085 tla">
															<tr>
																<td class="w3">评分：</td>
																<td class="w3"><span class="zdf0000">${doctor.scoreSort}</span>分</td>
															</tr>
															<tr>
																<td>诊次：</td>
																<td>${doctor.second}次</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					</a>
				</td>
			</tr>
			[/#list]
			<tr>
				<td class="h03333"></td>
			</tr>			
		</table>
	</div>
</div>

</body>
</html>
