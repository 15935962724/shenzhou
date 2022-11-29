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
		<h1 class="title lh3125">项目详情</h1>
	</header>

	<!-- 工具栏 -->
	<nav class="bar bar-tab">
		<table cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
				<td width="50%" class="bgffffff h354" valign="top">
					<a href="javascript:;">
					<table cellpadding="0" cellspacing="0" border="0" width="auto" class="m0auto ">
						<tr>
							<td align="center" valign="bottom" class="iconfont z_0782_4c98f6_1 lh085"><font class="zfs167 lh208">&#xe6f2;</font></td>
						</tr>
						<tr>
							<td align="center" class="lh085 z_0782_4c98f6_1">我要咨询</td>
						</tr>
					</table>
					</a>
				</td>
				<td width="50%" class="bg4c98f6" valign="top">
					<a href="${base}/web/order/toConfirmOrder.jhtml?projectId=${project.id}">
					<table cellpadding="0" cellspacing="0" border="0" width="auto" class="m0auto">
						<tr>
							<td align="center" class="iconfont z_0782_ffffff_1"><font class="zfs138">&#xe601;</font></td>
						</tr>
						<tr>
							<td align="center" class="z_0782_ffffff_1 lh085">我要预约</td>
						</tr>
					</table>
					</a>
				</td>
			</tr>
		</table>
	</nav>

	<!-- 这里是页面内容区 -->
	<div class="content">
		<table cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
				<td style="height: auto;"><img src="${project.introduceImg}" class="w2666666666 hauto"></td>
			</tr>
			<tr>
				<td>
					<table cellpadding="0" cellspacing="0" border="0" width="95%" class="m0auto tlf">
						<tr>
							<td align="left" class="tdwb z_1138_323232_1">${project.name}</td>
							<td align="right" style="" class="z_0924_333333_1 w8">康复师：${doctor.name}</td>
						</tr>
						<tr>
							<td align="left" class="z_0782_909090_1"><span style="color: #dc0000"><font class="z_0782_dc00000_1">￥</font><font class="z_1138_dc00000_1">${project.price}</font></span>/${project.time}分钟</td>
							<td align="right" class="z_0782_909090_1">康复次数：${project.second}次</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="h03333 bgf0f1f0"></td>
			</tr>
			<tr>
				<td>
					<table cellpadding="0" cellspacing="0" border="0" width="95%" class="iconfont m0auto">
						<tr>
							<td width="33%" align="left"><span class="z_106_4c98f6_1 lh208">&#xe63b;</span> <span class="z_0924_333333_1">五星好评</span></td>
							<td width="33%" align="center"><span class="z_106_4c98f6_1">&#xe620;</span> <span class="z_0924_333333_1">医疗保障</span></td>
							<td width="33%" align="right"><span class="z_106_4c98f6_1">&#xe604;</span> <span class="z_0924_333333_1">快速接单</span></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="h03333 bgf0f1f0"></td>
			</tr>
			<tr>
				<td class="z_1_323232_1 lh170 paa05">项目详情：${project.introduce}</td>
			</tr>
			<tr>
				<td class="h008333 bgf0f1f0"></td>
			</tr>
			<tr>
				<td class="patb05">
					<table cellpadding="0" cellspacing="0" border="0" width="95%" class="m0auto">
						<tr>
							<td colspan="3" class="z_1138_4c98f6_1 lh229 h316" valign="top">服务医师</td>
						</tr>
						<tr>
							<td align="left" class="w35">
								<img src="${doctor.logo}" class="br5 imgw291 hauto">
							</td>
							<td align="left">
								<span class="z_1_323232_1">${doctor.name}</span><br>
								<span class="z_085_909090_1">${doctor.doctorCategory.name}</span>
							</td>
							<td align="right" class="w5">
								<a href="${base}/web/doctor/doctorDetails.jhtml?doctorId=${doctor.id}" class="kl8725">了解医师</a>
							</td>
						</tr>
						<tr>
							<td colspan="3" class="paa05">
								<table cellpadding="0" cellspacing="0" border="0" width="100%">
									<tr>
										<td>
											<table cellpadding="0" cellspacing="0" border="0">
												<tr>
													<td align="center" class="z_128_4c98f6_1">${projectSize}</td>
												</tr>
												<tr>
													<td align="center">服务项目</td>
												</tr>
											</table>
										</td>
										<td align="center">
											<table cellpadding="0" cellspacing="0" border="0" align="center">
												<tr>
													<td align="center" class="z_128_4c98f6_1">${project.second}</td>
												</tr>
												<tr>
													<td align="center">诊疗次数</td>
												</tr>
											</table>
										</td>
										<td>
											<table cellpadding="0" cellspacing="0" border="0" align="right">
												<tr>
													<td align="center" class="z_128_e10101_1">${doctor.scoreSort}</td>
												</tr>
												<tr>
													<td align="center">综合评分</td>
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
			<tr>
				<td class="h008333 bgf0f1f0"></td>
			</tr>
			<tr>
				<td class="patb05">
					<table cellpadding="0" cellspacing="0" border="0" width="95%" class="m0auto">
						<tr>
							<td colspan="3" class="z_1138_4c98f6_1 lh229 h316" valign="top">服务机构</td>
						</tr>
						<tr>
							<td align="left" class="w35">
								<img src="${mechanism.logo}" class="br5 imgw291 hauto">
							</td>
							<td align="left">
								<table cellpadding="0" cellspacing="0" align="0" width="100%" class="tlf">
									<tr>
										<td class="tdwb">
											<span class="z_1_323232_1">${mechanism.name}</span>
										</td>
									</tr>
									<tr>
										<td>
											<span class="z_085_909090_1">${mechanism.mechanismRank.name}</span>
										</td>
									</tr>
								</table>
							</td>
							<td align="right" class="w5">
								<a href="${base}/web/mechanism/mechanismDetails.jhtml?mechanismId=${mechanism.id}" class="kl8725">了解机构</a>
							</td>
						</tr>
						<tr>
							<td colspan="3" class="paa05">
								<table cellpadding="0" cellspacing="0" border="0" width="100%">
									<tr>
										<td>
											<table cellpadding="0" cellspacing="0" border="0">
												<tr>
													<td align="center" class="z_128_4c98f6_1">${doctorSize}</td>
												</tr>
												<tr>
													<td align="center">医生数量</td>
												</tr>
											</table>
										</td>
										<td align="center">
											<table cellpadding="0" cellspacing="0" border="0" align="center">
												<tr>
													<td align="center" class="z_128_4c98f6_1">${mechanism.second}</td>
												</tr>
												<tr>
													<td align="center">诊疗次数</td>
												</tr>
											</table>
										</td>
										<td>
											<table cellpadding="0" cellspacing="0" border="0" align="right">
												<tr>
													<td align="center" class="z_128_e10101_1">${mechanism.scoreSort}</td>
												</tr>
												<tr>
													<td align="center">综合评分</td>
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
			<tr>
				<td class="h03333"></td>
			</tr>
		</table>
	</div>
</div>

</body>
</html>
