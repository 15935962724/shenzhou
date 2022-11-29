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
<script>
$(function () {		
	$(".content").scroll(function(){
		if ($(".content").scrollTop()>($("#headPic").height()-$("header").height()))
			$(".bar").css({"background":"rgba(255,255,255,1)","border-bottom":"1px solid #e7e7e7"});
		else
			$(".bar").css({"background":"rgba(255,255,255,0.3)","border-bottom":"none"});
			
	});
	
	$("#follow").on("click",function(){


		if($(this).hasClass("inp_fff_07825_4c98f6"))
			{
				$(this).removeClass("inp_fff_07825_4c98f6");
				$(this).addClass("inp_4c98f6_07825_fff");
				//此处调用关注接口
			}
		else
			{
				$(this).removeClass("inp_4c98f6_07825_fff");
				$(this).addClass("inp_fff_07825_4c98f6");
				//此处调用取消关注接口
			}

			
	});
});
	

</script>

</head>

<body>
<!-- page 容器 -->
<div class="page">
	<!-- 标题栏 -->
	<header class="bar bar-nav" style="z-index: 999;">
		<a href="javascript:;" onclick="javascript:history.back(-1);" class="icon icon-left pull-left"></a>
		<h1 class="title lh3125">医师详情</h1>
	</header>

	<!-- 这里是页面内容区 -->
	<div class="content">
		<table cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
				<td id="headPic" valign="bottom" class="h10958 paa05">
					<table cellpadding="0" cellspacing="0" border="0" width="95%" class="m0auto">
						<tr>
							<td class="h625 w7 pr">
								<img src="${doctor.logo}" class="h625 wauto br5"><img src="${base}/resources/web/images/sm.png" class="imgw19">
							</td>
							<td valign="top">
								<span class="z_14225_ffffff_1 lh3125">${doctor.name}</span><br>
								<span class="z_106_ffffff_1">${doctor.doctorCategory.name}</span>
							</td>
							<td valign="bottom" align="right">
								<a href="javascript:;" id="follow" class="inp_fff_07825_4c98f6">+ 关注</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="z_0924_646464_1">
					<table cellpadding="0" cellspacing="0" border="0" width="95%" class="m0auto">
						<tr>
							<td wdith="50%" valign="middle" class="pa1 brf0f1f0 bbf0f1f0">
								总治疗次数：<span class="z_0924_ff7800_1">${doctor.second}</span>次
								<br>
								综合评分：<span class="z_0924_ff7800_1">${doctor.scoreSort}</span>分
							</td>
							<td width="50%" valign="middle" class="pa1 bbf0f1f0">
								服务：<span class="z_0924_ff7800_1">${doctor.serverSort}</span>次
								<br>
								技能：<span class="z_0924_ff7800_1">${doctor.skillSort}</span>分
								<br>
								沟通：<span class="z_0924_ff7800_1">${doctor.communicateSort}</span>分
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="z_1_4a4a4a_1 lh170 paa05">
					<b>所属机构：</b>[#list mechanismName_list as mechanismName]${mechanismName}	[/#list]
					<br><br>
					<b>简介：</b>${doctor.introduce}
				</td>
			</tr>
			<tr>
				<td class="h03333 bgf0f1f0"></td>
			</tr>
			
			[#list mechanismName_list as mechanismName]
			
			<tr>
				<td class="paa05 bbf0f1f0 z_106_4c98f6_1">
					${mechanismName}
				</td>
			</tr>
			[#list doctor_projects as project]
			[#if project.mechanism.name==mechanismName]
			<tr>
				<td class="paa05 bbf0f1f0 pr">
					<a href="${base}/web/project/projectDetails.jhtml?projectId=${project.id}">
					<table cellpadding="0" cellspacing="0" border="0" width="100%" class="m0auto">
						<tr>
							<td valign="top" class="w6"><img src="${project.logo}" class="imgw120"></td>
							<td valign="top">
								<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z_0782_666666_1 tlf">
									<tr>
										<td class="z_128_323232_1 tdwb lh13">
											${project.name}
										</td>
									</tr>
									<tr>
										<td>
											<table cellpadding="0" cellspacing="0" border="0" width="100%" class="tlf">
												<tr>
													<td valign="top" class="lh08" style="height: 0.8rem;">诊次：<span class="zdf0000">${project.second}</span>次</td>
													<td class="w3 lh08" align="right" valign="top">评分：</td>
													<td class="w3 lh08" align="right" valign="top"><span class="zdf0000">${project.doctor.scoreSort}</span>分</td>
												</tr>
												<tr>
													<td rowspan="2" valign="top" align="left">${project.introduce}</td>
													<td colspan="2" align="right" valign="top"><span class="z_106_df0000_1">￥${project.price}</span>/${project.time}分钟</td>
												</tr>
												<tr>
												  <td colspan="2" align="right">
												  	
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
					<a href="${base}/web/order/toConfirmOrder.jhtml?projectId=${project.id}" class="inp_4c98f6_07825_fff pa" style="right: 0.5rem;bottom: 0rem;"><font color="#ffffff">预约</font></a>
				</td>
			</tr>
			[/#if]
			[/#list]
			[/#list]
			
			<tr>
				<td class="h008333 bgf0f1f0"></td>
			</tr>
			<tr>
				<td class="paa05 bbf0f1f0 ">
					用户评价（${evaluateSize}条）
				</td>
			</tr>
			[#list evaluateList as evaluate]
			<tr>
				<td class="bbf0f1f0 paa05">
					<table cellpadding="0" cellspacing="0" border="0" width="100%" class="m0auto">
						<tr>
							<td rowspan="2" valign="top" align="left" class="w25"><img src="${evaluate.member.logo}" class="h208 wauto br5"></td>
							<td>${evaluate.member.name}</td>
						</tr>
						<tr>
							<td>${evaluate.content}</td>
						</tr>
					</table>
				</td>
			</tr>
			[/#list]
			<tr>
				<td class="h03333"></td>
			</tr>			
		</table>
	</div>
</div>
<style>
	.page{background: #ffffff;}
	.bar{height:3.125rem; background:rgba(255,255,255,0.2); border-bottom: none; }
	.bar .icon {padding: 1.0625rem .1rem;line-height: 1rem; }
	.title{line-height: 3.125rem;}
	.bar-nav~.content{top: 0rem;}
	.content-block {margin: 0.825rem 0;}
</style>
</body>
</html>
