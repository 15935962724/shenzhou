<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/newmechanism/css/iconfont.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<style>
	body {background: #646464;padding: 20px;font-size: 36px; color: #fff;padding-top: 30px;} 
	ul{ padding:0px; margin:0px;letter-spacing: -5px; margin: 0px auto;}
	li{ display:inline-block;letter-spacing: normal;margin: 0px 50px; width:500px; height: 250px;}
	.z20fff{font-size: 20px; color: #fff;text-decoration: none;}
	.z20fff a{font-size: 20px;color: #fff;text-decoration: none;}
	.mtl{margin-top: 53px; margin-left: 55px;}
	img{border: 10px solid rgba(255,255,255,0.4); width: 100px; height: 100px;}
	.bg_1{background: url(${base}/resources/newmechanism/images/k_1.png) no-repeat;}
	.bg_2{background: url(${base}/resources/newmechanism/images/k_2.png) no-repeat;}
</style>
</head>
<body>

<table cellpadding="0" cellspacing="0" border="0" align="center" width="1210">
	<tr>
		<td align="center" height="50">请选择机构</td>
	</tr>
	<tr>
		<td class="z20fff">
			<ul style="margin-top: 50px;">
			[#list doctorMechanismRelations as doctorMechanismRelation]
				<li class="bg_${doctorMechanismRelation_index%2+1}">
					<a href="main.jhtml?mechanismId=${doctorMechanismRelation.mechanism.id}">
					<table cellpadding="0" cellspacing="0" border="0" width="370" class="mtl">
						<tr>
							<td width="130">
								<img src="${doctorMechanismRelation.mechanism.logo}" border="0">
							</td>
							<td>${doctorMechanismRelation.mechanism.name}</td>
						</tr>
					</table>
					</a>
				</li>
			[/#list]	
			</ul>
		</td>
	</tr>
</table>

</body>
</html>