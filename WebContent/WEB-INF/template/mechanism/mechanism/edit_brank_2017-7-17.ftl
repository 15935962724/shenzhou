<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.product.add")} - Powered By HaoKangHu</title>
<meta name="author" content="HaoKangHu Team" />
<meta name="copyright" content="HaoKangHu" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>

<script type="text/javascript" src="${base}/resources/admin/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<style type="text/css">
	.specificationSelect {
		height: 100px;
		padding: 5px;
		overflow-y: scroll;
		border: 1px solid #cccccc;
	}
	
	.specificationSelect li {
		float: left;
		min-width: 150px;
		_width: 200px;
	}
</style>
<script type="text/javascript">
$().ready(function() {


	var $inputForm = $("#inputForm");
	
	var $areaId = $("#areaId");
	var $name = $("#name");
	var $address = $("#address");
	var $phone = $("#phone");
	var $mechanismCategoryId = $("#mechanismCategoryId");
	var $mechanismRankId = $("#mechanismRankId");
	var $logoBrowserButton = $("#logoBrowserButton");
	var $introduceImgBrowserButton = $("#introduceImgBrowserButton");
	
	
	
	
	
	// 地区选择
	$areaId.lSelect({
		url: "${base}/mechanism/common/area.jhtml"
	});
	
	// 机构类型
	$mechanismCategoryId.lSelect({
		url: "${base}/mechanism/common/mechanismCategory.jhtml"
	});
	
	// 机构等级
	$mechanismRankId.lSelect({
		url: "${base}/mechanism/common/mechanismRank.jhtml"
	});
	
	[@flash_message /]
	
	var mechanismLogpath = "/upload/mechanismLogo/";
	var mechanismIntroduceImgpath = "/upload/mechanismIntroduceImg/";
	
	$logoBrowserButton.uploadImg(mechanismLogpath);
	$introduceImgBrowserButton.uploadImg(mechanismIntroduceImgpath);
	

	$.validator.addClassRules({
		logo: {
			required: true,
			extension: "${setting.uploadImageExtension}"
		},
		introduceImg: {
			required: true,
			extension: "${setting.uploadImageExtension}"
		},
	});
	
	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
			mechanismCategoryId: "required",
			mechanismRankId: "required",
			areaId: "required",
		    address: "required",
		    phone: {
				pattern: /^0\d{2,3}-?\d{7,8}$/,
			},
		},
		messages: {
			phone: {
				pattern: "电话号码有误"
			}
		},
		submitHandler: function(form) {
			if ($specificationIds.filter(":checked").size() > 0 && $specificationProductTable.find("tr:gt(1)").size() == 0) {
				$.message("warn", "${message("admin.product.specificationProductRequired")}");
				return false;
			} else {
				var isRepeats = false;
				var parameters = new Array();
				$specificationProductTable.find("tr:gt(1)").each(function() {
					var parameter = $(this).find("select").serialize();
					if ($.inArray(parameter, parameters) >= 0) {
						$.message("warn", "${message("admin.product.specificationValueRepeat")}");
						isRepeats = true;
						return false;
					} else {
						parameters.push(parameter);
					}
				});
				if (!isRepeats) {
					form.submit();
				}
			}
		}
	});
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="">${message("admin.path.index")}</a> &raquo; 修改机构信息
	</div>
	<form id="inputForm" action="update.jhtml" method="post" enctype="multipart/form-data">
	<input type="hidden" name="id" value="${mechanism.id}" />
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="${message("admin.product.base")}" />
			</li>
		</ul>
		<table class="input tabContent">
			<tr>
				<th>
					<span class="requiredField">*</span>机构名称:
				</th>
				<td>
					<input type="text" name="name" class="text" maxlength="200" value="${mechanism.name}" />
				</td>
			</tr>
			<tr>
				<th>
					企业logo:
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" name="logo" class="text" maxlength="200" value="${mechanism.logo}" title="${message("admin.product.imageTitle")}" />
						<input type="button" id="logoBrowserButton" class="button" value="${message("admin.browser.select")}" />
					</span>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>机构类型:
				</th>
				<td>
					<span class="fieldSet">
						<input type="hidden" id="mechanismCategoryId" name="mechanismCategoryId" value="${(mechanism.mechanismCategory.id)!}" treePath="${(mechanism.mechanismCategory.treePath)!}"  />
					</span>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>机构级别:
				</th>
				<td>
					<span class="fieldSet">
						<input type="hidden" id="mechanismRankId" name="mechanismRankId" value="${(mechanism.mechanismRank.id)!}" treePath="${(mechanism.mechanismRank.treePath)!}" />
					</span>
				</td>
			</tr>
			<tr>
				<th>
					经营模式:
				</th>
				<td>
					<label>
						<input type="checkbox" name="isMarketable" value="true" />到店康护
						<input type="hidden" name="_isMarketable" value="false" />
					</label>
					<label>
						<input type="checkbox" name="isList" value="true" />上门康护
						<input type="hidden" name="_isList" value="false" />
					</label>
					<label>
						<input type="checkbox" name="isTop" value="true" />电话咨询
						<input type="hidden" name="_isTop" value="false" />
					</label>
					<label>
						<input type="checkbox" name="isGift" value="true" />线上咨询
						<input type="hidden" name="_isGift" value="false" />
					</label>
				</td>
			</tr>
			<tr>
				<th>
					主要服务项目:
				</th>
				<td>
				[#list serverProjectCategorys as serverProjectCategory]
						<label>
							<input type="checkbox" name="isMarketable" value="true" />${serverProjectCategory.name}
							<input type="hidden" name="_isMarketable" value="false" />
						</label>
				[/#list]
				</td>
			</tr>
			<tr>
			<th>
				工作时间:
			</th>
		      <td>
		          <input type="text" id="startTime" name="startTime" value="${mechanism.workDate.startTime}" class="Wdate" onfocus="WdatePicker({dateFmt: 'HH:mm', maxDate: '#F{$dp.$D(\'endTime\')}'});" />
			       - 
			       <input type="text" id="endTime" name="endTime" value="${mechanism.workDate.endTime}"  class="Wdate" onfocus="WdatePicker({dateFmt: 'HH:mm', minDate: '#F{$dp.$D(\'startTime\')}'});" />
		       </td>
			</tr>	
			<tr>
				<th>
					<span class="requiredField">*</span>机构地址:
				</th>
				<td>
					<span class="fieldSet">
						<input type="hidden" id="areaId" name="areaId"  value="${(mechanism.area.id)!}" treePath="${(mechanism.area.treePath)!}"  />
					</span>
				</td>
			</tr>		
			<tr>
				<th>
					<span class="requiredField">*</span>详细地址:
				</th>
				<td>
					<input type="text" name="address" class="text" maxlength="100" value="${mechanism.address}" title="" />
				</td>
			</tr>
			<tr>
				<th>
					企业介绍:
				</th>
				<td>
					<textarea name="introduce" class="text" style="width: 50%; height: 200px;">${mechanism.introduce}</textarea>
				</td>
			</tr>
			<tr>
				<th>
					图片介绍:
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" name="introduceImg" class="text" maxlength="200" value="${mechanism.introduceImg}" title="${message("admin.product.imageTitle")}" />
						<input type="button" id="introduceImgBrowserButton" class="button" value="${message("admin.browser.select")}" />
					</span>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>联系电话:
				</th>
				<td>
					<input type="text" name="phone" class="text" value="${mechanism.phone}" maxlength="100" title="" />
				</td>
			</tr>
		</table>
		
		<table class="input">
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