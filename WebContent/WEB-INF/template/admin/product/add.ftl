<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.product.add")} - Powered By HaoKangHu</title>
<meta name="author" content="HaoKangHu Team" />
<meta name="copyright" content="HaoKangHu" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
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
	var $productCategoryId = $("#productCategoryId");
	var $isMemberPrice = $("#isMemberPrice");
	var $memberPriceTr = $("#memberPriceTr");
	var $memberPrice = $("#memberPriceTr input");
	var $browserButton = $("#browserButton");
	var $productImageTable = $("#productImageTable");
	var $addProductImage = $("#addProductImage");
	var $deleteProductImage = $("a.deleteProductImage");
	var $parameterTable = $("#parameterTable");
	var $attributeTable = $("#attributeTable");
	var $specificationIds = $("#specificationSelect :checkbox");
	var $couponIds = $("#couponSelect :checkbox");
	var $specificationProductTable = $("#specificationProductTable");
	var $couponProductTable = $("#couponProductTable");
	var $addSpecificationProduct = $("#addSpecificationProduct");
	var $addCouponProduct = $("#addCouponProduct");
	var $deleteSpecificationProduct = $("a.deleteSpecificationProduct");
	var $deleteCouponProduct = $("a.deleteCouponProduct");
	
	var productImageIndex = 0;
	
	[@flash_message /]
	
	var previousProductCategoryId = getCookie("previousProductCategoryId");
	if (previousProductCategoryId != null) {
		$productCategoryId.val(previousProductCategoryId);
	} else {
		previousProductCategoryId = $productCategoryId.val();
	}
	
	loadParameter();
	loadAttribute();
	
	$browserButton.browser();
	
	// 会员价
	$isMemberPrice.click(function() {
		if ($(this).prop("checked")) {
			$memberPriceTr.show();
			$memberPrice.prop("disabled", false);
		} else {
			$memberPriceTr.hide();
			$memberPrice.prop("disabled", true);
		}
	});
	
	// 增加商品图片
	$addProductImage.click(function() {
		[@compress single_line = true]
			var trHtml = 
			'<tr>
				<td>
					<input type="file" name="productImages[' + productImageIndex + '].file" class="productImageFile" \/>
				<\/td>
				<td>
					<input type="text" name="productImages[' + productImageIndex + '].title" class="text" maxlength="200" \/>
				<\/td>
				<td>
					<input type="text" name="productImages[' + productImageIndex + '].order" class="text productImageOrder" maxlength="9" style="width: 50px;" \/>
				<\/td>
				<td>
					<a href="javascript:;" class="deleteProductImage">[${message("admin.common.delete")}]<\/a>
				<\/td>
			<\/tr>';
		[/@compress]
		$productImageTable.append(trHtml);
		productImageIndex ++;
	});
	
	// 删除商品图片
	$deleteProductImage.live("click", function() {
		var $this = $(this);
		$.dialog({
			type: "warn",
			content: "${message("admin.dialog.deleteConfirm")}",
			onOk: function() {
				$this.closest("tr").remove();
			}
		});
	});
	
	// 修改商品分类
	$productCategoryId.change(function() {
		var hasValue = false;
		$parameterTable.find(":input").each(function() {
			if ($.trim($(this).val()) != "") {
				hasValue = true;
				return false;
			}
		});
		if (!hasValue) {
			$attributeTable.find(":input").each(function() {
				if ($.trim($(this).val()) != "") {
					hasValue = true;
					return false;
				}
			});
		}
		if (hasValue) {
			$.dialog({
				type: "warn",
				content: "${message("admin.product.productCategoryChangeConfirm")}",
				width: 450,
				onOk: function() {
					loadParameter();
					loadAttribute();
					previousProductCategoryId = $productCategoryId.val();
				},
				onCancel: function() {
					$productCategoryId.val(previousProductCategoryId);
				}
			});
		} else {
			loadParameter();
			loadAttribute();
			previousProductCategoryId = $productCategoryId.val();
		}
	});
	
	// 加载参数
	function loadParameter() {
		$.ajax({
			url: "parameter_groups.jhtml",
			type: "GET",
			data: {id: $productCategoryId.val()},
			dataType: "json",
			beforeSend: function() {
				$parameterTable.empty();
			},
			
			success: function(data) {
				var trHtml = "";
				$.each(data, function(i, parameterGroup) {
					trHtml += '<tr><td style="text-align: right;"><strong>' + parameterGroup.name + ':<\/strong><\/td><td>&nbsp;<\/td><\/tr>';
					$.each(parameterGroup.parameters, function(i, parameter) {
						[@compress single_line = true]
							trHtml += 
							'<tr>
								<th>' + parameter.name + ': <\/th>
								<td>
									<input type="text" name="parameter_' + parameter.id + '" class="text" maxlength="200" \/>
								<\/td>
							<\/tr>';
						[/@compress]
					});
				});
				$parameterTable.append(trHtml);
			}
		});
	}
	
	// 加载属性
	function loadAttribute() {
		$.ajax({
			url: "attributes.jhtml",
			type: "GET",
			data: {id: $productCategoryId.val()},
			dataType: "json",
			beforeSend: function() {
				$attributeTable.empty();
			},
			success: function(data) {
				var trHtml = "";
				$.each(data, function(i, attribute) {
					var optionHtml = '<option value="">${message("admin.common.choose")}<\/option>';
					$.each(attribute.options, function(j, option) {
						optionHtml += '<option value="' + option + '">' + option + '<\/option>';
					});
					[@compress single_line = true]
						trHtml += 
						'<tr>
							<th>' + attribute.name + ': <\/th>
							<td>
								<select name="attribute_' + attribute.id + '">
									' + optionHtml + '
								<\/select>
							<\/td>
						<\/tr>';
					[/@compress]
				});
				$attributeTable.append(trHtml);
			}
		});
	}
	
	// 修改商品规格
	$specificationIds.click(function() {
		if ($specificationIds.filter(":checked").size() == 0) {
			$specificationProductTable.find("tr:gt(1)").remove();
		}
		var $this = $(this);
		if ($this.prop("checked")) {
			$specificationProductTable.find("td.specification_" + $this.val()).show().find("select").prop("disabled", false);
		} else {
			$specificationProductTable.find("td.specification_" + $this.val()).hide().find("select").prop("disabled", true);
		}
	});
	
	// 增加规格商品
	$addSpecificationProduct.click(function() {
		if ($specificationIds.filter(":checked").size() == 0) {
			$.message("warn", "${message("admin.product.specificationRequired")}");
			return false;
		}
		if ($specificationProductTable.find("tr:gt(1)").size() == 0) {
			$tr = $specificationProductTable.find("tr:eq(1)").clone().show().appendTo($specificationProductTable);
			$tr.find("td:first").text("${message("admin.product.currentSpecification")}");
			$tr.find("td:last").text("-");
		} else {
			$specificationProductTable.find("tr:eq(1)").clone().show().appendTo($specificationProductTable);
		}
	});
	
	// 删除规格商品
	$deleteSpecificationProduct.live("click", function() {
		var $this = $(this);
		$.dialog({
			type: "warn",
			content: "${message("admin.dialog.deleteConfirm")}",
			onOk: function() {
				$this.closest("tr").remove();
			}
		});
	});
	
	// 修改优惠券规格
	$couponIds.click(function() {
		//if ($couponIds.filter(":checked").size() == 0) {
			//$couponProductTable.find("tr:gt(1)").remove();
		//}
		var $this = $(this);
		if ($this.prop("checked")) {
			$couponProductTable.find("tr.coupon_" + $this.val()).show().find("select").prop("disabled", "");
		} else {
			$couponProductTable.find("tr.coupon_" + $this.val()).hide().find("select").prop("disabled", "none");
		}
	});
	
	// 增加优惠券
	$addCouponProduct.click(function() {
		if ($couponIds.filter(":checked").size() == 0) {
			$.message("warn", "${message("admin.product.specificationRequired")}");
			return false;
		}
		if ($couponProductTable.find("tr:gt(1)").size() == 0) {
			$tr = $couponProductTable.find("tr:eq(1)").clone().show().appendTo($couponProductTable);
			$tr.find("td:first").text("${message("admin.product.currentSpecification")}");
			$tr.find("td:last").text("-");
		} else {
			$couponProductTable.find("tr:eq(1)").clone().show().appendTo($couponProductTable);
		}
	});
	
	// 删除优惠券
	$deleteCouponProduct.live("click", function() {
		var $this = $(this);
		$.dialog({
			type: "warn",
			content: "${message("admin.dialog.deleteConfirm")}",
			onOk: function() {
				$this.closest("tr").remove();
			}
		});
	});
	
	$.validator.addClassRules({
		memberPrice: {
			min: 0,
			decimal: {
				integer: 12,
				fraction: ${setting.priceScale}
			}
		},
		productImageFile: {
			required: true,
			extension: "${setting.uploadImageExtension}"
		},
		productImageOrder: {
			digits: true
		}
	});
	
	// 表单验证
	$inputForm.validate({
		rules: {
			productCategoryId: "required",
			name: "required",
			sn: {
				pattern: /^[0-9a-zA-Z_-]+$/,
				remote: {
					url: "check_sn.jhtml",
					cache: false
				}
			},
			price: {
				required: true,
				min: 0,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			},
			cost: {
				min: 0,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			},
			marketPrice: {
				min: 0,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			},
			weight: "digits",
			stock: "digits",
			point: "digits"
		},
		messages: {
			sn: {
				pattern: "${message("admin.validate.illegal")}",
				remote: "${message("admin.validate.exist")}"
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
					$specificationProductTable.find("tr:eq(1)").find("select").prop("disabled", true);
					addCookie("previousProductCategoryId", $productCategoryId.val(), {expires: 24 * 60 * 60});
					form.submit();
				}
			}
		}
	});
	
});

	//统计现价
   function chkvalue(){
	  	var presentPrice=0;//现价
	  	var costPrice = 0;//原价(成本价)
	   	var table = document.getElementById('couponProductTable');
		// 获取 table 内的全部 input
		var textinputs = table.getElementsByTagName('input');
		// 循环
		for(var i = 0; i < textinputs.length; i++) {
			if(textinputs[i].id == "moenyc" && textinputs[i].value != 0 && textinputs[i].value != null){
				presentPrice += parseInt(textinputs[i].value);
				costPrice += parseInt(textinputs[i-1].value);
			}
		}
		$("#cost").val(costPrice);
		$("#price").val(presentPrice);
   	}
	
	

KindEditor.ready(function(K) {
	var editor1 = K.create('textarea[name="introduction"]', {
	width : '99%',
    height : '500px',
	allowFileManager : true
	});
	var editor2 = K.create('textarea[name="safeguards"]', {
	width : '99%',
    height : '500px',
	allowFileManager : true
	});
});



</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.product.add")}
	</div>
	<form id="inputForm" action="save.jhtml" method="post" enctype="multipart/form-data">
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="${message("admin.product.base")}" />
			</li>
			<li>
				<input type="button" value="${message("admin.product.introduction")}" />
			</li>
			<li>
				<input type="button" value="${message("admin.product.productImage")}" />
			</li>
			<li>
				<input type="button" value="${message("admin.product.parameter")}" />
			</li>
			<li>
				<input type="button" value="${message("admin.product.attribute")}" />
			</li>
			<li>
				<input type="button" value="${message("admin.product.specification")}" />
			</li>
			<li>
				<input type="button" value="优惠券" />
			</li>
			<li>
				<input type="button" value="其他信息" />
			</li>
			<li>
				<input type="button" value="保证说明" />
			</li>
		</ul>
		<table class="input tabContent">
			<tr>
				<th>
					${message("Product.productCategory")}:
				</th>
				<td>
					<select id="productCategoryId" name="productCategoryId">
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
					<span class="requiredField">*</span>${message("Product.name")}:
				</th>
				<td>
					<input type="text" name="name" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.sn")}:
				</th>
				<td>
					<input type="text" name="sn" class="text" maxlength="100" title="${message("admin.product.snTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Product.price")}:
				</th>
				<td>
					<input type="text" name="price" id="price" class="text" maxlength="16" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.memberPrice")}:
				</th>
				<td>
					<label>
						<input type="checkbox" id="isMemberPrice" name="isMemberPrice" value="true" />${message("admin.product.isMemberPrice")}
					</label>
				</td>
			</tr>
			<tr id="memberPriceTr" class="hidden">
				<th>
					&nbsp;
				</th>
				<td>
					[#list memberRanks as memberRank]
						${memberRank.name}: <input type="text" name="memberPrice_${memberRank.id}" class="text memberPrice" maxlength="16" style="width: 60px; margin-right: 6px;" disabled="disabled" />
					[/#list]
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.cost")}:
				</th>
				<td>
					<input type="text" id="cost" name="cost" class="text" maxlength="16" title="${message("admin.product.costTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.marketPrice")}:
				</th>
				<td>
					<input type="text" name="marketPrice" class="text" maxlength="16" title="${message("admin.product.marketPriceTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.image")}:
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" name="image" class="text" maxlength="200" title="${message("admin.product.imageTitle")}" />
						<input type="button" id="browserButton" class="button" value="${message("admin.browser.select")}" />
					</span>
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.unit")}:
				</th>
				<td>
					<input type="text" name="unit" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.weight")}:
				</th>
				<td>
					<input type="text" name="weight" class="text" maxlength="9" title="${message("admin.product.weightTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.stock")}:
				</th>
				<td>
					<input type="text" name="stock" class="text" maxlength="9" title="${message("admin.product.stockTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.stockMemo")}:
				</th>
				<td>
					<input type="text" name="stockMemo" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.point")}:
				</th>
				<td>
					<input type="text" name="point" class="text" maxlength="9" title="${message("admin.product.pointTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.brand")}:
				</th>
				<td>
					<select name="brandId">
						<option value="">${message("admin.common.choose")}</option>
						[#list brands as brand]
							<option value="${brand.id}">
								${brand.name}
							</option>
						[/#list]
					</select>
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.tags")}:
				</th>
				<td>
					[#list tags as tag]
						<label>
							<input type="checkbox" name="tagIds" value="${tag.id}" />${tag.name}
						</label>
					[/#list]
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.common.setting")}:
				</th>
				<td>
					<label>
						<input type="checkbox" name="isMarketable" value="true" checked="checked" />${message("Product.isMarketable")}
						<input type="hidden" name="_isMarketable" value="false" />
					</label>
					<label>
						<input type="checkbox" name="isList" value="true" checked="checked" />${message("Product.isList")}
						<input type="hidden" name="_isList" value="false" />
					</label>
					<label>
						<input type="checkbox" name="isTop" value="true" />${message("Product.isTop")}
						<input type="hidden" name="_isTop" value="false" />
					</label>
					<label>
						<input type="checkbox" name="isGift" value="true" />${message("Product.isGift")}
						<input type="hidden" name="_isGift" value="false" />
					</label>
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.memo")}:
				</th>
				<td>
					<input type="text" name="memo" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.keyword")}:
				</th>
				<td>
					<input type="text" name="keyword" class="text" maxlength="200" title="${message("admin.product.keywordTitle")}" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.seoTitle")}:
				</th>
				<td>
					<input type="text" name="seoTitle" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.seoKeywords")}:
				</th>
				<td>
					<input type="text" name="seoKeywords" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Product.seoDescription")}:
				</th>
				<td>
					<input type="text" name="seoDescription" class="text" maxlength="200" />
				</td>
			</tr>
		</table>
		<table class="input tabContent">
			<tr>
				<td>
					<textarea id="editor" name="introduction" class="editor" style="width: 100%;"></textarea>
				</td>
			</tr>
		</table>
		<table id="productImageTable" class="input tabContent">
			<tr>
				<td colspan="4">
					<a href="javascript:;" id="addProductImage" class="button">${message("admin.product.addProductImage")}</a>
				</td>
			</tr>
			<tr class="title">
				<td>
					${message("ProductImage.file")}
				</td>
				<td>
					${message("ProductImage.title")}
				</td>
				<td>
					${message("admin.common.order")}
				</td>
				<td>
					${message("admin.common.delete")}
				</td>
			</tr>
		</table>
		<table id="parameterTable" class="input tabContent"></table>
		<table id="attributeTable" class="input tabContent"></table>
		<table class="input tabContent">
			<tr class="title">
				<th>
					${message("admin.product.selectSpecification")}:
				</th>
			</tr>
			<tr>
				<td>
					<div id="specificationSelect" class="specificationSelect">
						<ul>
							[#list specifications as specification]
								<li>
									<label>
										<input type="checkbox" name="specificationIds" value="${specification.id}" />${specification.name}
										[#if specification.memo??]
											<span class="gray">[${specification.memo}]</span>
										[/#if]
									</label>
								</li>
							[/#list]
						</ul>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<a href="javascript:;" id="addSpecificationProduct" class="button">${message("admin.product.addSpecificationProduct")}</a>
				</td>
			</tr>
			<tr>
				<td>
					<table id="specificationProductTable" class="input">
						<tr class="title">
							<td width="60">
								&nbsp;
							</td>
							[#list specifications as specification]
								<td class="specification_${specification.id} hidden">
									${specification.name}
									[#if specification.memo??]
										<span class="gray">[${specification.memo}]</span>
									[/#if]
								</td>
							[/#list]
							<td>
								${message("admin.common.handle")}
							</td>
						</tr>
						<tr class="hidden">
							<td>
								&nbsp;
							</td>
							[#list specifications as specification]
								<td class="specification_${specification.id} hidden">
									<select name="specification_${specification.id}" disabled="disabled">
										[#list specification.specificationValues as specificationValue]
											<option value="${specificationValue.id}">${specificationValue.name}</option>
										[/#list]
									</select>
								</td>
							[/#list]
							<td>
								<a href="javascript:;" class="deleteSpecificationProduct">[${message("admin.common.delete")}]</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table class="input tabContent">
			<tr class="title">
				<th>
					请选择优惠券:
				</th>
			</tr>
			<tr>
				<td>
					<div id="couponSelect" class="specificationSelect">
						<ul>
							[#list coupons as coupon]
								<li>
									<label>
										<input type="checkbox"  id="coupon_${coupon.id}" name="couponIds" value="${coupon.id}" />${coupon.name}
									</label>
								</li>
							[/#list]
						</ul>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<a href="javascript:;" id="addCouponProduct" class="button">${message("admin.product.addSpecificationProduct")}</a>
				</td>
			</tr>
			<tr>
				<td>
					<table id="couponProductTable" class="input">
						<tr class="title">
							<td width="60">
								&nbsp;
							</td>
							<td width="60">
									分类
							</td>
							<td width="100">
									优惠券名称
							</td>
							<td width="150">
									有效期限
							</td>
							<td width="180">
									有效机构
							</td>
							<td width="180">
									有效项目
							</td>
							<td width="100">
									原价
							</td>
							<td width="100">
									现价
							</td>
							<td width="100">
									数量
							</td>
							<td>
								${message("admin.common.handle")}
							</td>
						</tr>
						[#list coupons as coupon]
						<tr class="coupon_${coupon.id}" id="coupon_${coupon.id}" style="display:none">
							<td>
								&nbsp;
							</td>
							<td >
									${message("Coupon.CouponType."+coupon.couponType)}
							</td>
							<td >
									${coupon.name}
							</td>
							<td >
									<span title="${coupon.beginDate?string("yyyy-MM-dd HH:mm:ss")}">${coupon.beginDate}</span>
									- 
									<span title="${coupon.endDate?string("yyyy-MM-dd HH:mm:ss")}">${coupon.endDate}</span>
							</td>
							<td >
									[#list coupon.mechanisms as mechanism]
									   ${mechanism.name}、
									[/#list]
							</td>
							<td >
									[#list coupon.serverProjectCategorys as serverProjectCategory]
									   ${serverProjectCategory.name}、
									[/#list]
							</td>
							<td >
									<input type = "text" style="width:50px;" id="costs" name="costs" value="${coupon.reductionPrice}">
							</td>
							<td >
									<input type = "text" style="width:50px;" id="moenyc" name="prices" onblur="chkvalue(this)">
							</td>
							<td >
									<input type = "text" style="width:50px;" name="quantity">
							</td>
							<td>
								<a href="javascript:;" class="deleteCouponProduct">[${message("admin.common.delete")}]</a>
							</td>
						</tr>
						[/#list]
						<tr class="hidden">
							<td>
								&nbsp;
							</td>
							[#list specifications as specification]
								<td class="specification_${specification.id} hidden">
									<select name="specification_${specification.id}" disabled="disabled">
										[#list specification.specificationValues as specificationValue]
											<option value="${specificationValue.id}">${specificationValue.name}</option>
										[/#list]
									</select>
								</td>
							[/#list]
							<td>
								<a href="javascript:;" class="deleteCouponProduct">[${message("admin.common.delete")}]</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
		<table class="input tabContent">
			<tr class="title">
				<th style="text-align:right">库存数量</th>
				<td><input type="text" id="stocks" name="stocks"/></td>
				
			</tr>
			<tr class="title">
				<th style="text-align:right">上架</th>
				<td><input type="checkbox" checked="checked" id="isMarketable"/><span> 打✔️为上架，否则为下架状态</span></td>
			</tr>
			<tr class="title">
				<th style="text-align:right">销售方式</th>
				<td><input type="radio" checked="checked" id="points" name="points"/><span> 健康金</span></td>
			</tr>
			<tr class="title">
				<th style="text-align:right">停罄状态</th>
				<td><input type="checkbox" id="sellOut" name="sellOut"/><span> 设为售罄</span></td>
			</tr>
		</table>
		
		
		<table class="input tabContent">
			<tr>
				<td>
					<textarea id="safeguards" name="safeguards" class="editor" style="width: 100%;"></textarea>
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