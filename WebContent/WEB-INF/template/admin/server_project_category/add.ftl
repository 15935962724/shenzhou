<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.productCategory.add")} - Powered By HaoKangHu</title>
<meta name="author" content="HaoKangHu Team" />
<meta name="copyright" content="HaoKangHu" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<style type="text/css">
.brands label {
	width: 150px;
	display: block;
	float: left;
	padding-right: 6px;
}
</style>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $browserButton = $("#browserButton");
	var $introduceImgBrowserButton = $("#introduceImgBrowserButton");
	
	
	
	[@flash_message /]
	
	$browserButton.browser();
	$introduceImgBrowserButton.browser();

	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
			order: "digits"
		}
	});
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.productCategory.add")}
	</div>
	<form id="inputForm" action="save.jhtml" method="post">
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>名称:
				</th>
				<td>
					<input type="text" id="name" name="name" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					Logo图片:
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" id = "logo" name="logo" class="text" maxlength="200" />
						<input type="button" id="browserButton" class="button" value="选择文件" />
					</span>
				</td>
			</tr>
			<tr>
				<th>
					Logo图片:
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" id = "introduceImg" name="introduceImg" class="text" maxlength="200" />
						<input type="button" id="introduceImgBrowserButton" class="button" value="选择文件" />
					</span>
				</td>
			</tr>
			<tr>
				<th>
					服务类型:
				</th>
				<td>
					<span class="fieldSet">
						[#list serveTypes as serveType]
							<label>
								<input type="radio" name="serveType" value="${serveType}" />${message("ServerProjectCategory.ServeType." + serveType)}
							</label>
						[/#list]
					</span>
				</td>
			</tr>
			
			<tr>
				<th>
					详情描述:
				</th>
				<td>
					<input type="text" id = "seoDescription" name="seoDescription" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					排序:
				</th>
				<td>
					<input type="text" name="order" class="text" maxlength="9" />
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="${message("admin.common.submit")}" />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>