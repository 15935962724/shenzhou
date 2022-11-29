<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>新闻详情</title>
<meta name="author" content="HaoKangHu Team" />
<meta name="copyright" content="HaoKangHu" />
<link href="${base}/resources/guanwang/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/guanwang/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/guanwang/js/jquery.cycle.all.js"></script>
<script type="text/javascript" src="${base}/resources/guanwang/js/js.js"></script>
<script type="text/javascript" src="${base}/resources/guanwang/js/index.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $hits = $("#hits");

	// 查看点击数
	$.ajax({
		url: "${base}/journalism/hits/${journalism.id}.jhtml",
		type: "GET",
		success: function(data) {
			$hits.text(data);
		}
	});

});
</script>
</head>
<body>
[#include "guanwang/shenzhou/header.ftl"]

<div id = "content">		   
	<div class="bik h400 mt85" style="background-image:url(${base}/resources/guanwang/images/banner.png);">
	</div>
	
	<table cellpadding="0" cellspacing="0" border="0" width="1200" align="center">
		<tr>
			<td>
				<table cellpadding="0" cellspacing="0" border="0" width="1200">
					<tr>
						<td height="90" class="z_20_7e7e7e_1">
							当前位置：<a href="${base}/guanwang/index/shenzhou.jhtml" title="神州儿女********">首页</a> &gt; <a href="javascript:;" title="新闻资讯">新闻资讯</a> &gt; <a href="javascript:;" title="神州动态">${journalism.journalismCategory.seoTitle}</a>
						</td>
						<td width="500" class="pr">
							<input id="keys" name="keys" type="text" placeholder="请输入关键词" class="scr_k" /><input type="button" name="button" id="button" value="搜索" class="scr_b" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table cellpadding="0" cellspacing="0" border="0" width="1020" align="center">
					<tr>
						<td height="80"></td>
					</tr>
					<tr>
						<td align="center" class="z_22_323232_1">${journalism.title}</td>
					</tr>
					<tr>
						<td align="center" class="z_16_929292_1">来源：<a href="" target="_blank">${journalism.source}</a>　编辑：${journalism.author}　</td>
					</tr>
					<tr>
						<td height="40"></td>
					</tr>
					<tr>
						<td class="z_16_323232_1" valign="top" height="500">
							${journalism.content}
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table cellpadding="0" cellspacing="0" border="0" width="1200" align="center">
					<tr>
						<td height="50" colspan="2"></td>
					</tr>
					<tr>
						<td class="z_16_323232_14_4b99f6_1" align="left">上一篇：<a href="javascript:;" title="我院专家抵达迪拜参加“第12届国际脑创伤及临床">我院专家抵达迪拜参加“第12届国际脑创伤及临床</a></td>
						<td class="z_16_323232_14_4b99f6_1" align="right">下一篇：<a href="javascript:;" title="我院专家抵达迪拜参加“第12届国际脑创伤及临床">我院专家抵达迪拜参加“第12届国际脑创伤及临床</a></td>
					</tr>
					<tr>
						<td height="20" colspan="2"></td>
					</tr>
					<tr>
						<td colspan="2">
							[#include "guanwang/shenzhou/share.ftl"]
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
[#include "guanwang/shenzhou/footer.ftl"]
</body>
</html>