<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("Common.Main.title")}</title>
<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/js.js"></script>

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
				             $("#logoImg").attr("src",dataObj.logo); 
				             $("#logo").val(dataObj.logo);  
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
		},
		messages: {
			sn: {
				pattern: "${message("admin.validate.illegal")}",
				remote: "${message("admin.validate.exist")}"
			}
		},
		submitHandler: function(form) {
			form.submit();
		}
		
		
	});
	
	// 表单验证
	$updateForm.validate({
		rules: {
			upName: "required",
			upParentId: "required",
			upSeoTitle: "required",
			upPrice: {
				required: true,
				min: 0,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			},
			upTime: {
			required: true,
				min: 10,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			}
		},
		submitHandler: function(form) {
			form.submit();
		}
		
		
	});
	
	
});
</script>
<script type="text/javascript">


	//上传预览
	function upload_logo_img(upfile,upimg)
	{
		$("#" + upfile ).click();
		$("#" + upfile ).on("change",function(){
			url=getObjectURL(this.files[0]);
			if (url) 
				{
					if(upimg!="")
					{
						$("#" + upimg).attr("src", url) ; 
					}
				}
		});
	}


</script>
</head>
<body>
<div>
     	<div class="nav">管理导航：
        <a href="javascript:;">管理首页</a>　
        <a href="企业信息.html">企业信息</a>　
        <a href="企业认证.html">企业认证</a>　
        <a href="服务时间.html">服务时间</a>　
        <a href="服务项目.html">服务项目</a>　
        <a href="我的账号.html">我的账号</a>　
        <a href="评价信息.html">评价信息</a>
      </div>
        <div class="seat">
        	  <div class="left_z">服务项目</div>
            <div class="export"><a href="javascript:;" onClick="window.back();"><img src="${base}/resources/mechanism/images/back.png" border="0" class="top_img_b_1" /> 返回</a></div>
        </div>
        <div class="detail">
        <form id="inputForm" action="update.jhtml" method="post" enctype="multipart/form-data">
    	<input type="hidden" name="id" value="${serverProjectCategory.id}" />
        	<table width="630" cellpadding="0" cellspacing="0" border="0">
            	<tr>
            	  <td height="45" valign="top">&nbsp;</td>
            	  <td valign="top">&nbsp;</td>
          	  </tr>
            	<tr>
                	<td width="80" height="45" align="right" valign="top">项目类别<font class="z_14_ff0000_1">*</font></td>
                    <td width="260" valign="top" class="p_l_r_20_1">
                     <select id = "parentId" name="parentId" class="cate_o">
                      <option value="">全部</option>
	                      [#list adminAerverProjectCategory as category]
									<option  [#if serverProjectCategory.parent==category] selected="true"[/#if] value="${category.id}">
										${category.name}
									</option>
						  [/#list]
                      </select>
                    </td>
            	  <td width="90" rowspan="4" align="left" valign="top">
	            	  <img id="logoImg" src="${base}/resources/mechanism/images/j_i_1.png" alt="" width="80" height="80" class="u_img_1" onclick="upload_logo_img('upfile','logoImg');" />
	                  <input id="upfile" name="upfile_logo" type="file" style="display: none"  />
            	  </td>
                </tr>
                <tr>
                  <td width="80" height="45" align="right" valign="top">项目名称<font class="z_14_ff0000_1">*</font></td>
                  <td width="260" valign="top" class="p_l_r_20_1"><input type="text"  name="name" id="name" value="${serverProjectCategory.name}" class="inp_1 w_315_1" /></td>
                </tr>
            	<tr>
                  <td width="80" height="45" align="right" valign="top">项目标题<font class="z_14_ff0000_1">*</font></td>
                  <td width="260" valign="top" class="p_l_r_20_1"><input type="text" name="seoTitle" id="seoTitle" value="${serverProjectCategory.seoTitle}"  class="inp_1 w_315_1" /></td>
                </tr>
            	<tr>
            	  <td width="80" height="45" align="right" valign="top">项目价格<font class="z_14_ff0000_1">*</font></td>
            	  <td width="260" valign="top" class="p_l_r_20_1"><input type="text" name="price" id="price" value="${serverProjectCategory.price}" class="inp_1 w_315_1" />
           	      <font class="z_12_999999_1">元</font></td>
           	  </tr>
            	<tr>
            	  <td width="80" height="45" align="right" valign="top">治疗时间<font class="z_14_ff0000_1">*</font></td>
            	  <td width="260" valign="top" class="p_l_r_20_1"><input type="text" name="time" id="time" value="${serverProjectCategory.time}" class="inp_1 w_315_1" />
           	      <font class="z_12_999999_1">分钟</font></td>
           	  </tr>
            	<tr>
            	  <td width="80" height="25" align="right" valign="top">详情描述<font class="z_14_ff0000_1">*</font></td>
            	  <td colspan="2" valign="top" class="p_l_r_20_1"><textarea name="seoDescription" id="seoDescription" rows="5" class="inp_2 h_80_1 w_450_1">${serverProjectCategory.seoDescription}</textarea></td>
          	  </tr>
            	<tr>
            	  <td colspan="3">&nbsp;</td>
           	  </tr>
            	<tr>
            	  <td height="45" align="center">&nbsp;</td>
            	  <td height="45" colspan="2" align="left" class="p_l_r_20_1">
            	<input type="submit" name="button7" id="button7" value=" 提 交 " class="button_1 b_4188d1_1" />　<input type="reset" name="button7" id="button7" value=" 重 置 " class="button_1 b_ececec_1 z_12_707070_1" />
                  </td>
           	  </tr>
            </table>
            </form>     
</div>

<div class="clear"></div>
</body>
</html>