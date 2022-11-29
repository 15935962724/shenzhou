<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/js.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>

<script type="text/javascript">

$().ready(function() {

	var $inputForm = $("#inputForm");
	var $updateForm = $("#updateForm");
	
	[@flash_message /]
    
         function deleteButton(id){
 			$.ajax({
				url: "delete.jhtml",
				type: "POST",
				data: {id: id},
				dataType: "json",
				cache: false,
				success: function(message) {
					$.message(message);
					if(message.type=='success'){
						location.reload();
					}
				}
			});
     }
    
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

     function deleteButton(id){
 			$.ajax({
				url: "delete.jhtml",
				type: "POST",
				data: {id: id},
				dataType: "json",
				cache: false,
				success: function(message) {
					$.message(message);
					if(message.type=='success'){
						location.reload();
					}
				}
			});
     }

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


	function updateServerProjectCategory(d_id,d_width,d_height,id)
	{
		if((typeof(d_width) != "undefined") && (typeof(d_height) !="undefined"))
			{
				var w=d_width / 2;
				var h=d_height / 2;
				$("."+d_id).css({"width":  d_width + "px","height": d_height + "px","left":"50%","top":($(parent.document).scrollTop()+50) + "px","margin-left": "-" + w + "px"});
				$(".d_l_1").css({"width":  (d_width-40) + "px","height": (d_height-40) + "px"});
				if($(parent.document).height()<(d_height+$(parent.document).scrollTop()))
					$("#main_ifr",parent.document).height((parseInt(d_height)+$(parent.document).scrollTop()+150)+"px");
			}
		lock_Scroll();
		$("#p_id").val(id);
	    var report = $("."+d_id);
	    var html = '';
	     $.ajax({
		             type: "POST",
		             url: "query.jhtml",
		             data: {
		             parentId:id
		             },
		             dataType: "json",
		             success: function(data){
		             var last=data.data; 
		             var dataObj=eval("("+last+")");
				             console.log(dataObj);
				             console.log(dataObj.name);
				            html += '    	<div class=\"d_l_1\">';
				            html += '    	<form id=\"updateForm\" action="${base}/mechanism/serverProjectCategory/update.jhtml" method=\"POST\" enctype=\"multipart/form-data\">';
					        html +=	'        	<table width=\"430\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">';
							html +=	'            	<tr>';
							html +=	'                	<td width=\"80\" height=\"35\" valign=\"top\">项目类别<font class=\"z_14_ff0000_1\">*</font></td>';
							html +=	'                    <td width=\"260\" valign=\"top\"><select id = \"parentServerProjectCategoryId\" name=\"parentServerProjectCategoryId\" class=\"cate_o\" onchange=\"change_parentServerProjectCategoryId()\">';
							html +=	'                      <option value=\"\">全部</option>';
															[#list serverProjectCategory as category]
							html +=	'					   <option value="${category.id}">${category.name}</option>';
															[/#list]
							html +=	'                    </select></td>';
							html +=	'                  <td width=\"90\" rowspan=\"4\" align=\"center\" valign=\"top\"><img id=\"upLogoImg\" src=\"'+dataObj.logo+'\" alt=\"\" width=\"80\" height=\"80\" class=\"u_img_1\" onclick=\"upload_logo_img(\"upfile\",\"upLogoImg\");\" /><input id=\"upfile\" name=\"upfile_logo\" type=\"file\" style=\"display: none\"  /></td>';
							html +=	'                </tr>';
							html +=	'            	<tr>';
							html +=	'                	<td width=\"80\" height=\"35\" valign=\"top\">项目id<font class=\"z_14_ff0000_1\">*</font></td>';
							html +=	'                  <td width=\"260\" valign=\"top\"><input type=\"text\" name=\"id\" value=\"'+dataObj.id+'\" id=\"upId\" class=\"inp_1 w_200_1\" /></td>';
							html +=	'                </tr>';
							html +=	'            	<tr>';
							html +=	'                	<td width=\"80\" height=\"35\" valign=\"top\">项目名称<font class=\"z_14_ff0000_1\">*</font></td>';
							html +=	'                  <td width=\"260\" valign=\"top\"><input type=\"text\" name=\"name\" value='+dataObj.name+' id=\"upName\" class=\"inp_1 w_200_1\" /></td>';
							html +=	'                </tr>';
							html += '            	<tr>';
							html +=	'                	<td width=\"80\" height=\"35\" valign=\"top\">项目标题<font class=\"z_14_ff0000_1\">*</font></td>';
							html +=	'                  <td width=\"260\" valign=\"top\"><input type=\"text\" name=\"seoTitle\"  value='+dataObj.seoTitle+' id=\"upSeoTitle\" class=\"inp_1 w_200_1\" /></td>';
							html +=	'                </tr>';
							html +=	'            	<tr>';
							html +=	'            	  <td width=\"80\" height=\"35\" valign=\"top\">项目价格<font class=\"z_14_ff0000_1\">*</font></td>';
							html +=	'            	  <td width=\"260\" valign=\"top\"><input type=\"text\" name=\"price\" id=\"upPrice\" value='+dataObj.price+' class=\"inp_1 w_200_1\" />';
							html +=	'           	      <font class=\"z_12_999999_1\">元</font></td>';
							html +=	'           	  </tr>';
							html +=	'            	<tr>';
							html +=	'            	  <td width=\"80\" height=\"35\" valign=\"top\">治疗时间<font class=\"z_14_ff0000_1\">*</font></td>';
							html +=	'            	  <td width=\"260\" valign=\"top\"><input type=\"text\" name=\"time\" id=\"upTime\" value='+dataObj.time+' class=\"inp_1 w_200_1\" />';
							html +=	'           	      <font class=\"z_12_999999_1\">分钟</font></td>';
							html +=	'           	  </tr>';
							html +=	'            	<tr>';
							html +=	'            	  <td width=\"80\" height=\"25\" valign=\"top\">详情描述<font class=\"z_14_ff0000_1\">*</font></td>';
							html +=	'            	  <td colspan=\"2\" valign=\"top\"><textarea name=\"seoDescription\" id=\"upSeoDescription\" rows=\"5\" class=\"inp_2 h_80_1 w_315_1\">'+dataObj.seoDescription+'</textarea></td>';
							html +=	'          	  </tr>';
							html +=	'            	<tr>';
							html +=	'            	  <td colspan=\"3\">&nbsp;</td>';
							html +=	'           	  </tr>';
							html +=	'            	<tr>';
							html +=	'            	  <td height=\"45\" colspan=\"3\" align=\"center\">';
							html +=	'            	<input type=\"submit\" name=\"button4\" id=\"button4\" value=\"　提　交　\" class=\"button_1 b_4fc1e9_1\"  />';　
							html +=	'            	<input type=\"button\" name=\"button5\" id=\"button5\" value=\"　取　消　\" class=\"button_1 b_d1d141_1\" onclick=\"disp_hidden_d("report");\"/>';
							html +=	'                  </td>';
							html +=	'           	  </tr>';
							html +=	'            </table>';
							html +=	'            </form>';
							html +=	'        </div>';
							html +=	'        <div class=\"d_cl_1\"><a href=\"javascript:;\" onclick=\"disp_hidden_d("report");\"><img src=\"${base}/resources/mechanism/images/x.png\" width=\"15\" border=\"0\" /></a></div>';
							report.append(html);
		               }
		         });
		$("#"+d_id).toggle();	
	}


	    function change_parentServerProjectCategoryId(){
//	    $('#parentServerProjectCategoryId').onchange(function(){
    
     	var parentId = $('#parentServerProjectCategoryId').val();
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
				             $("#upName").val(dataObj.name);      
				             $("#upPrice").val(dataObj.price);      
				             $("#upTime").val(dataObj.time);      
				             $("#upSeoTitle").val(dataObj.seoTitle);      
				             $("#upSeoKeywords").val(dataObj.seoKeywords);      
				             $("#upSeoDescription").val(dataObj.seoDescription);  
				             $("#upLogoImg").attr("src",dataObj.logo); 
				             $("#upLogo").val(dataObj.logo);  
		               }
		         });
		     }
    }
	

</script>


</head>
<body>
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
            <div class="export"><a href="javascript:;" onclick="disp_hidden_d('layer','470','370','');">新增服务项目</a></div>
        </div>
        <form id="listForm" action="list.jhtml" method="get">
        <div class="detail">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" id="cont_list" class="m_t_20_1">
            <tr class="bg_f5f5f5_1">
              <td width="30" height="35" align="center">序号</td>
              <td align="center">分类名称</td>
              <td width="100" align="center">价格<font>(元)</font></td>
              <td width="100" align="center">治疗时间<font>(分钟)</font></td>
              <td width="160" align="center">管理</td>
            </tr>
            [#list page.content as serverProjectCategory]
            <tr [#if serverProjectCategory_index%2==0]class="fff"[/#if]>
              <td height="35" align="center">${serverProjectCategory_index+1}</td>
              <td align="left">${serverProjectCategory.name}</td>
              <td align="center">${currency(serverProjectCategory.price)}<font>&nbsp;</font></td>
              <td align="center">${serverProjectCategory.time}<font>&nbsp;</font></td>
              <td align="center">
              	<a href="edit.jhtml?id=${serverProjectCategory.id}"><input type="button" name="button5" id="button5" value="编辑" class="button_3 b_4c98f6_1" /></a>
                <input type="button" name="button5" id="button5" value="明细" class="button_3 b_d1d141_1" onclick="show_info(${serverProjectCategory.id},1);" />
                <input type="button" name="button7" id="button7" value="刪除" onClick="deleteButton('${serverProjectCategory.id}')" class="button_3 b_e60010_1" />
              </td>
            </tr>
            <tr [#if serverProjectCategory_index%2==0]class="fff"[/#if] style="display:none" id="info_${serverProjectCategory.id}">
              <td height="35" colspan="5" align="left" style="padding:10px 20px;line-height:170%;" >
			              项目类别：${serverProjectCategory.name}<br />
			              项目标题：[#if serverProjectCategory.seoTitle??]${serverProjectCategory.seoTitle}[#else]无[/#if]<br />
			              项目价格：${currency(serverProjectCategory.price)}元/${serverProjectCategory.time}分钟<br />
			              详情描述：[#if serverProjectCategory.seoDescription??]${serverProjectCategory.seoDescription}[#else]无[/#if]
              </td>
            </tr>
            [/#list]
          </table>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td class="b_f_4c98f6_1 pos_r_1">
                  [@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
						[#include "/mechanism/include/pagination.ftl"]
				  [/@pagination]                 
                  </td>
                </tr>
            </table>          
        </div>
        </form>      
 

<div class="clear"></div>

<div id="layer">
	<div class="layer">
    	<div class="d_l_1">
    	<form id="inputForm" action="save.jhtml" method="post" enctype="multipart/form-data">
        	<table width="430" cellpadding="0" cellspacing="0" border="0">
            	<tr>
                	<td width="80" height="35" valign="top">项目类别<font class="z_14_ff0000_1">*</font></td>
                    <td width="260" valign="top">
	                    <select id = "parentId" name="parentId" class="cate_o">
	                      <option value="">全部</option>
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
                  <td width="90" rowspan="4" align="center" valign="top">
	                  <img id="logoImg" src="${base}/resources/mechanism/images/j_i_1.png" alt="" width="80" height="80" class="u_img_1" onclick="upload_logo_img('upfile','logoImg');" />
	                  <input id="upfile" name="upfile_logo" type="file" style="display: none"  />
                  </td>
                </tr>
                <tr>
                  <td width="80" height="35" valign="top">项目名称<font class="z_14_ff0000_1">*</font></td>
                  <td width="260" valign="top"><input type="text" name="name" id="name" class="inp_1 w_200_1" /></td>
                </tr>
            	<tr>
                  <td width="80" height="35" valign="top">项目标题<font class="z_14_ff0000_1">*</font></td>
                  <td width="260" valign="top"><input type="text" name="seoTitle" id="seoTitle" class="inp_1 w_200_1" /></td>
                </tr>
            	<tr>
            	  <td width="80" height="35" valign="top">项目价格<font class="z_14_ff0000_1">*</font></td>
            	  <td width="260" valign="top"><input type="text" name="price" id="price" class="inp_1 w_200_1" />
           	      <font class="z_12_999999_1">元</font></td>
           	  </tr>
            	<tr>
            	  <td width="80" height="35" valign="top">治疗时间<font class="z_14_ff0000_1">*</font></td>
            	  <td width="260" valign="top"><input type="text" name="time" id="time" class="inp_1 w_200_1" />
           	      <font class="z_12_999999_1">分钟</font></td>
           	  </tr>
            	<tr>
            	  <td width="80" height="25" valign="top">详情描述<font class="z_14_ff0000_1">*</font></td>
            	  <td colspan="2" valign="top"><textarea name="seoDescription" id="seoDescription" rows="5" class="inp_2 h_80_1 w_315_1"></textarea></td>
          	  </tr>
            	<tr>
            	  <td colspan="3">&nbsp;</td>
           	  </tr>
            	<tr>
            	  <td height="45" colspan="3" align="center">
            	<input type="submit" name="button4" id="button4" value="　提　交　" class="button_1 b_4fc1e9_1" />　
            	<input type="button" name="button5" id="button5" value="　取　消　" class="button_1 b_d1d141_1" onclick="disp_hidden_d('layer');"/>
                  </td>
           	  </tr>
            </table>
            </form>
        </div>
        <div class="d_cl_1"><a href="javascript:;" onclick="disp_hidden_d('layer');"><img src="${base}/resources/mechanism/images/x.png" width="15" border="0" /></a></div>
	</div>
</div>	
	
	
<div id="report">
	<div class="report">
    	
	</div>
</div>		
	
	
</body>
</html>