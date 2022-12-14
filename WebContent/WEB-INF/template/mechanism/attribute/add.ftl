<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
[#include "/mechanism/include/meta.ftl" /] 
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $attributeTable = $("#attributeTable");
	var $addOption = $("#addOption");
	var $deleteOption = $("a.deleteOption");
	
	[@flash_message /]
	
	// 增加可选项
	$addOption.live("click", function() {
		var $this = $(this);
		[@compress single_line = true]
			var trHtml = 
			'<tr class="optionTr">
				<td>
					&nbsp;
				<\/td>
				<td>
					<input type="text" name="options" class="text" maxlength="200" \/>
				<\/td>
				<td>
					<a href="javascript:;" class="deleteOption">[${message("admin.common.delete")}]<\/a>
				<\/td>
			<\/tr>';
		[/@compress]
		$attributeTable.append(trHtml);
	});
	
	// 删除可选项
	$deleteOption.live("click", function() {
		var $this = $(this);
		if ($attributeTable.find("tr.optionTr").size() <= 1) {
			$.message("warn", "${message("admin.attribute.deleteAllOptionNotAllowed")}");
		} else {
			$this.closest("tr").remove();
		}
	});
	
	// 表单验证
	$inputForm.validate({
		rules: {
			productCategoryId: "required",
			name: "required",
			order: "digits",
			options: "required"
		}
	});

});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.attribute.add")}
	</div>
	<form id="inputForm" action="save.jhtml" method="post">
		<table id="attributeTable" class="input">
			<tr>
				<th>
					${message("Attribute.productCategory")}:
				</th>
				<td colspan="2">
					<select name="productCategoryId">
						[#list productCategoryTree as productCategory]
							<option value="${productCategory.id}">
								[#if productCategory.grade != 0]
									[#list 1..productCategory.grade as i]
										&nbsp;&nbsp;
									[/#list]
								[/#if]
								${productCategory.name}
							</option>
						[/#list]
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Attribute.name")}:
				</th>
				<td colspan="2">
					<input type="text" name="name" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.common.order")}:
				</th>
				<td colspan="2">
					<input type="text" name="order" class="text" maxlength="9" />
				</td>
			</tr>
			<tr>
				<td>&nbsp;
					
				</td>
				<td colspan="2">
					<a href="javascript:;" id="addOption" class="button">${message("admin.attribute.addOption")}</a>
				</td>
			</tr>
			<tr class="title">
				<td>&nbsp;
					
				</td>
				<td>
					${message("Attribute.options")}
				</td>
				<td>
					${message("admin.common.delete")}
				</td>
			</tr>
			<tr class="optionTr">
				<td>&nbsp;
					
				</td>
				<td>
					<input type="text" name="options" class="text" maxlength="200" />
				</td>
				<td>
					<a href="javascript:;" class="deleteOption">[${message("admin.common.delete")}]</a>
				</td>
			</tr>
		</table>
		<table class="input">
			<tr>
				<th>&nbsp;
					
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