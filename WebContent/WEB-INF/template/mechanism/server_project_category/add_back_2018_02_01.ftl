<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/js.js"></script>
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
	
	[@flash_message /]
    
    
     $('#parentId').click(function(){
     	var parentId = $('#parentId').val();
		     if(parentId!=""){
		       console.log(parentId);
		        $.ajax({
		             type: "POST",
		             url: "query.jhtml",
		             data: {
		             parentId:parentId
		             },
		             dataType: "json",
		             success: function(data){
		             var last=data.data; 
		             var dataObj=eval("("+last+")");
				             console.log(dataObj);
				             console.log(dataObj.name);
				             $("#name").val(dataObj.name);      
				             $("#price").val(dataObj.price);      
				             $("#time").val(dataObj.time);      
				             $("#seoTitle").val(dataObj.seoTitle);      
				             $("#seoKeywords").val(dataObj.seoKeywords);      
				             $("#seoDescription").val(dataObj.seoDescription);  
		               }
		         });
		     }
    });
    

	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
			parentId: "required",
			seoTitle: "required",
			price: {
				required: true,
				min: 0,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			},
			time: {
			required: true,
				min: 10,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			},
			order: "digits"
		}
		
		
	});
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="">${message("admin.path.index")}</a> &raquo; 添加项目分类
	</div>
	<form id="inputForm" action="save.jhtml" method="post">
		<table class="input">
			
			<tr>
				<th>
					${message("ProductCategory.parent")}:
				</th>
				<td>
					<select id = "parentId" name="parentId">
						<option value="">${message("admin.productCategory.root")}</option>
						[#list serverProjectCategory as category]
							<option value="${category.id}">
								[#if category.grade != 0]
									[#list 1..category.grade as i]
										&nbsp;&nbsp;
									[/#list]
								[/#if]
								${category.name}
							</option>
						[/#list]
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>项目类别:
				</th>
				<td>
					<input type="text" id="name" name="name" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>项目标题:
				</th>
				<td>
					<input type="text" id="seoTitle" name="seoTitle" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("ProductCategory.seoKeywords")}:
				</th>
				<td>
					<input type="text" id="seoKeywords" name="seoKeywords" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>项目价格:
				</th>
				<td>
					<input type="text" id="price" name="price" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>项目时间:
				</th>
				<td>
					<input type="text" id="time"  name="time" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					项目描述:
				</th>
				<td>
					<textarea id="seoDescription" name="seoDescription" class="text" style="width: 50%; height: 200px;"></textarea>
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.common.order")}:
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