<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/web/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.event.drag-1.5.min.js"></script>
<script type="text/javascript" src="${base}/resources/js/jquery.touchSlider.js"></script>
<script type="text/javascript" src="${base}/resources/js/js.js"></script>
<script type="text/javascript">
$(function(){
	var dw = 21 * $(".main_image").find("ul").find("img").length / 2
	$(".flicking_con").css("margin-left","-" + dw + "px")
	$(".main_visual").hover(function(){
		$("#btn_prev,#btn_next").fadeIn()
	},function(){
		$("#btn_prev,#btn_next").fadeOut()
	});
	
	$dragBln = false;
	
	$(".main_image").touchSlider({
		flexible : true,
		speed : 200,
		btn_prev : $("#btn_prev"),
		btn_next : $("#btn_next"),
		paging : $(".flicking_con a"),
		counter : function (e){
			$(".flicking_con a").removeClass("on").eq(e.current-1).addClass("on");
		}
	});
	
	$(".main_image").bind("mousedown", function() {
		$dragBln = false;
	});
	
	$(".main_image").bind("dragstart", function() {
		$dragBln = true;
	});
	
	$(".main_image a").click(function(){
		if($dragBln) {
			return false;
		}
	});
	
	timer = setInterval(function(){
		$("#btn_next").click();
	}, 5000);
	
	$(".main_visual").hover(function(){
		clearInterval(timer);
	},function(){
		timer = setInterval(function(){
			$("#btn_next").click();
		},5000);
	});
	
	$(".main_image").bind("touchstart",function(){
		clearInterval(timer);
	}).bind("touchend", function(){
		timer = setInterval(function(){
			$("#btn_next").click();
		}, 5000);
	});
	
});
	
</script>
</head>

<body>
<div id="header" class="pf h329">
	<table cellpadding="0" cellspacing="0" border="0" width="98%" align="center">
		<tr>
			<td align="center" class="wz w43">北京 <img src="${base}/resources/web/images/j_1.png" style="width: 1.05rem;height: auto; max-width: 24px;"></td>
			<td align="center">
				<input type="text" placeholder="搜索机构、医生、康复师、康复项目" class="sip">
			</td>
			<td style="width: 2rem" align="center"><img src="${base}/resources/web/images/x_1.png" style="width:1.53rem"></td>
		</tr>
	</table>
</div>
<div id="content" class="mt329 pb4376">
	<table cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td class="pr">
				<div class="main_visual" style="">
					<div class="flicking_con"> <!--margin-left=n*3/2-->
						<a href="#"></a>
						<a href="#"></a>
						<a href="#"></a>
						<a href="#"></a>
					</div>
					<div class="main_image">
						<ul>
							<li><img src="${base}/resources/web/images/img_ad_one.png"></li>
							<li><img src="${base}/resources/web/images/img_ad_two.png"></li>
							<li><img src="${base}/resources/web/images/img_ad_three.png"></li>
							<li><img src="${base}/resources/web/images/img_ad_three.png"></li>
						</ul>
						<a href="javascript:;" id="btn_prev"></a>
						<a href="javascript:;" id="btn_next"></a>
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td style="height: 1rem"></td>
		</tr>
		<tr>
			<td>
				<table cellpadding="0" cellspacing="0" border="0" width="100%" id="et">
					<tr>
						<td width="33%" align="right">
							<a href="${base}/web/mechanism/toMechanismList.jhtml"><img src="${base}/resources/web/images/zjg.png"></a>
						</td>
						<td width="33%" align="center">
							<a href="${base}/web/project/toProjectList.jhtml"><img src="${base}/resources/web/images/zxx.png"></a>
						</td>
						<td width="33%" align="center">
							<a href="${base}/web/doctor/toDoctorList.jhtml"><img src="${base}/resources/web/images/yzj.png"></a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td style="height: 1rem"></td>
		</tr>
		<tr>
			<td>
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z_128_4c98f6_1 pal10" style="height: 3rem;">热门项目</td>
						<td class="z_085_909090_1 par10" align="right"><a href="javascript:;">更多 &gt;&gt;</a></td>
					</tr>
					<tr>
						<td colspan="2" id="ht" align="center">
							<ul>
								<li>
									<a href="javascript:;">
										<img src="${base}/resources/web/images/rmxm_1.png">
										<h1>PT康复</h1>
										<h2 class="wsn">Physics Tainer</h2>
									</a>
								</li>
								<li>
									<a href="javascript:;">
										<img src="${base}/resources/web/images/rmxm_2.png">
										<h1>OT康复</h1>
										<h2 class="wsn">Physics Tainer</h2>
									</a>
								</li>
								<li>
									<a href="javascript:;">
										<img src="${base}/resources/web/images/rmxm_3.png">
										<h1>言语治疗</h1>
										<h2>Speech Therapy</h2>
									</a>
								</li>
								<li>
									<a href="javascript:;">
										<img src="${base}/resources/web/images/rmxm_4.png">
										<h1>中医按摩</h1>
										<h2>Chinese Massage</h2>
									</a>
								</li>

							</ul>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td style="height: 1rem"></td>
		</tr>
		<tr>
			<td>
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z_128_4c98f6_1 pal10" style="height: 3rem;">知名专家</td>
						<td class="z_085_909090_1 par10" align="right"><a href="javascript:;">更多 &gt;&gt;</a></td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<a href="javascript:;">
							<table cellpadding="0" cellspacing="0" border="0" width="95%" align="center" class="mb1">
								<tr>
									<td valign="top" class="pr" style="width: 6rem;"><img src="${base}/resources/web/images/tmp_1.png" class="imgw120"><img src="${base}/resources/web/images/ic_rz.png" class="imgw19"></td>
									<td valign="top">
										<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z_0782_666666_1">
											<tr>
												<td style="width: 1.5rem" class="z_128_323232_1">李胜利</td>
											</tr>
											<tr>
												<td style="width: 1rem">主任医师 教授</td>
											</tr>
											<tr>
												<td style="width: 1rem">神州儿女康复中心</td>
											</tr>
											<tr>
												<td style="width: 1rem">擅长：0-12岁儿童</td>
											</tr>
										</table>
									</td>
									<td align="right" valign="middle" class="z_0782_666666_1 zj_tb_w_1">
										<table cellpadding="0" cellspacing="0" border="0" width="95%" align="right">
											<tr>
												<td align="left" class="zj_td_w_1">评分：</td>
												<td align="right"><font class="z_e00101">9.9</font>分</td>
											</tr>
											<tr>
												<td align="left">诊次：</td>
												<td align="right">9999次</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
							</a>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<a href="javascript:;">
							<table cellpadding="0" cellspacing="0" border="0" width="95%" align="center" class="mb1">
								<tr>
									<td valign="top" class="pr" style="width: 6rem;"><img src="${base}/resources/web/images/tmp_1.png" class="imgw120"><img src="${base}/resources/web/images/ic_rz.png" class="imgw19"></td>
									<td valign="top">
										<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z_0782_666666_1">
											<tr>
												<td style="width: 1.5rem" class="z_128_323232_1">李胜利</td>
											</tr>
											<tr>
												<td style="width: 1rem">主任医师 教授</td>
											</tr>
											<tr>
												<td style="width: 1rem">神州儿女康复中心</td>
											</tr>
											<tr>
												<td style="width: 1rem">擅长：0-12岁儿童</td>
											</tr>
										</table>
									</td>
									<td align="right" valign="middle" class="z_0782_666666_1 zj_tb_w_1">
										<table cellpadding="0" cellspacing="0" border="0" width="95%" align="right">
											<tr>
												<td align="left" class="zj_td_w_1">评分：</td>
												<td align="right"><font class="z_e00101">9.9</font>分</td>
											</tr>
											<tr>
												<td align="left">诊次：</td>
												<td align="right">9999次</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
							</a>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<a href="javascript:;">
							<table cellpadding="0" cellspacing="0" border="0" width="95%" align="center" class="mb1">
								<tr>
									<td valign="top" class="pr" style="width: 6rem;"><img src="${base}/resources/web/images/tmp_1.png" class="imgw120"><img src="${base}/resources/web/images/ic_rz.png" class="imgw19"></td>
									<td valign="top">
										<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z_0782_666666_1">
											<tr>
												<td style="width: 1.5rem" class="z_128_323232_1">李胜利</td>
											</tr>
											<tr>
												<td style="width: 1rem">主任医师 教授</td>
											</tr>
											<tr>
												<td style="width: 1rem">神州儿女康复中心</td>
											</tr>
											<tr>
												<td style="width: 1rem">擅长：0-12岁儿童</td>
											</tr>
										</table>
									</td>
									<td align="right" valign="middle" class="z_0782_666666_1 zj_tb_w_1">
										<table cellpadding="0" cellspacing="0" border="0" width="95%" align="right">
											<tr>
												<td align="left" class="zj_td_w_1">评分：</td>
												<td align="right"><font class="z_e00101">9.9</font>分</td>
											</tr>
											<tr>
												<td align="left">诊次：</td>
												<td align="right">9999次</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
							</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td style="height: 1rem"></td>
		</tr>
		<tr>
			<td class="pal10 par10">
				<table cellpadding="0" cellspacing="0" border="0" width="98%">
					<tr>
						<td width="50%" valign="top" class="br_dcdcdc_1">
							<a href="javascript:;">
							<table cellpadding="0" cellspacing="0" border="0" width="100%">
								<tr>
									<td valign="top">
										<font class="z_128_4c98f6_1">康护百科</font><br>
										<font class="z_0924_909090_1">康护类百科全书</font>
									</td>
									<td style="width:4.79rem"><img src="${base}/resources/web/images/khbk.png" style="width: 4.79rem;max-width: 115px;"></td>
								</tr>
							</table>
							</a>
						</td>
						<td width="50%" valign="top">
							<a href="javascript:;">
							<table cellpadding="0" cellspacing="0" border="0" width="98%" align="right">
								<tr>
									<td valign="top">
										<font class="z_128_4c98f6_1">健康自诊</font><br>
										<font class="z_0924_909090_1">根据症状找医生</font>
									</td>
									<td style="width:4.79rem"><img src="${base}/resources/web/images/jkzz.png" style="width: 4.79rem;max-width: 115px;"></td>
								</tr>
							</table>
							</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<div id="food">
	<table cellpadding="0" cellspacing="0" border="0" width="100%" >
		<tr>
			<td width="20%" style="height: 4.376rem;" valign="top"><a href="javascript:;" class="as"><img src="${base}/resources/web/images/home_l_1.png"><span>首页</span></a></td>
			<td width="20%" valign="top"><a href="javascript:;"><img src="${base}/resources/web/images/im_h_1.png"><span>咨询</span></a></td>
			<td width="20%" valign="top"><a href="javascript:;" class="buy"><img src="${base}/resources/web/images/buy_1.png"><span>预约<br>康复</span></a></td>
			<td width="20%" valign="top"><a href="${base}/web/order/toMemberOrderList.jhtml"><img src="${base}/resources/web/images/order_h_1.png"><span>订单</span></a></td>
			<td width="20%" valign="top"><a href="${base}/web/member/toMyself.jhtml"><img src="${base}/resources/web/images/my_h_1.png"><span>我的</span></a></td>
		</tr>
	</table>
</div>
</body>
</html>
