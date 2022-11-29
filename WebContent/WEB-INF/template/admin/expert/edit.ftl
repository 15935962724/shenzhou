<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.article.edit")} - Powered By HaoKangHu</title>
<meta name="author" content="HaoKangHu Team" />
<meta name="copyright" content="HaoKangHu" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $browserButton = $("#browserButton");
	[@flash_message /]
	
	$browserButton.browser();
	
	// 表单验证
	$inputForm.validate({
		rules: {
			title: "required",
			image: "required",
			articleCategoryId: "required"
		}
	});

});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 编辑专家
	</div>
	<form id="inputForm" action="update.jhtml" method="post">
		<input type="hidden" name="id" value="${expert.id}" />
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>姓名:
				</th>
				<td>
					<input type="text" name="name" class="text" value="${expert.name}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>性别:
				</th>
				<td>
					<span class="fieldSet">
						[#list genders as gender]
							<label>
								<input type="radio" name="gender" [#if gender == expert.gender] checked="checked"[/#if] value="${gender}" />${message("Member.Gender." + gender)}
							</label>
						[/#list]
					</span>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>职位:
				</th>
				<td>
					<select name="doctorCategoryId" >
						[#list doctorCategorys as doctorCategory]
							<option [#if expert.doctorCategory == doctorCategory] selected="selected"  [/#if] value="${doctorCategory.id}">
								[#if doctorCategory.grade != 0]
									[#list 1..doctorCategory.grade as i]
										&nbsp;&nbsp;
									[/#list]
								[/#if]
								${doctorCategory.name}
							</option>
						[/#list]
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>级别:
				</th>
				<td>
					<select name="doctorRankId" >
						[#list doctorRanks as doctorRank]
							<option [#if expert.doctorRank == doctorRank] selected="selected"  [/#if] value="${doctorRank.id}">
								[#if doctorRank.grade != 0]
									[#list 1..doctorRank.grade as i]
										&nbsp;&nbsp;
									[/#list]
								[/#if]
								${doctorRank.name}
							</option>
						[/#list]
					</select>
				</td>
			</tr>
			<tr>
				<th>
					Logo:
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" name="logo" class="text" value="${expert.logo}" maxlength="200" />
						<input type="button" id="browserButton" class="button" value="选择文件" />
					</span>
				</td>
			</tr>
			
			<tr>
				<th>
					擅长领域:
				</th>
				<td>
					<textarea name="advantage" class="" style="width: 46.5%; height: 200px;">${expert.advantage}</textarea>
				</td>
			</tr>
			<tr>
				<th>
					简介:
				</th>
				<td>
					<textarea id="editor" name="introduce" class="editor">${expert.introduce}</textarea>
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