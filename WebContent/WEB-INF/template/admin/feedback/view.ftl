<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.message.view")} - Powered By HaoKangHu</title>
<meta name="author" content="HaoKangHu Team" />
<meta name="copyright" content="HaoKangHu" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {

	[@flash_message /]
	
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 查看反馈
	</div>
		<table class="input">
			<tr>
				<th>
					姓名:
				</th>
				<td colspan="2">
					${feedback.name}
				</td>
			</tr>
			<tr>
				<th>
					电话:
				</th>
				<td colspan="2">
					${feedback.phone}
				</td>
			</tr>
			<tr>
				<th>
					ip:
				</th>
				<td colspan="2">
					${feedback.ip}
				</td>
			</tr>
			<tr>
				<th>
					创建时间:
				</th>
				<td colspan="2">
					${feedback.createDate?string("yyyy-MM-dd HH:mm:ss")}
				</td>
			</tr>
			<tr>
				<th>
					反馈内容:
				</th>
				<td colspan="2">
					<textarea name="content" class="text">${feedback.content}</textarea>
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td colspan="2">
					<input type="button" class="button" value="${message("admin.common.submit")}" onclick="location.href='list.jhtml'" />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
				</td>
			</tr>
		</table>
</body>
</html>