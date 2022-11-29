<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("Common.Main.title")}</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/chart.bundle.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>

<script type="text/javascript">

$().ready(function() {

	var $inputForm = $("#inputForm");
	
	[@flash_message /]
    
    
    //明细展开
	$(".detailed").click(function(){
		$(".detailed").parents("tr").next("tr").hide();		
		$(this).parents("tr").next("tr").show();
		ifr_height("main_ifr");
	})
    
    
    //获取机构端内容赋值给现有输入框
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

</script>
</head>
<body>
<div class="m020">
	<table cellpadding="0" cellspacing="0" border="0" class="k_1 mtb30" id = "page_nav">
		<tr>
			
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="k_2">
		<tr>
			<td class="pa20">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">服务项目</td>
						<td class="z16279fff bb1dd4d4d4" align="right"><a href="add.jhtml" >新增服务项目</a></td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td colspan="2">
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="">
								<tr class="bg279fff z14ffffff">
									<td class="btle3e3e3" align="center" width="40" height="50">序号</td>
									<td class="btle3e3e3" align="center">项目名称</td>
									<td class="btle3e3e3 bre3e3e3" align="center" width="160">操作</td>
								</tr>
								[#list page.content as serverProjectCategory]
								<tr [#if serverProjectCategory_index%2==0]class="bge7f4ff"[/#if]>
									<td class="btle3e3e3" align="center" height="50">${serverProjectCategory_index+1}</td>
									<td class="btle3e3e3 plr10">${serverProjectCategory.name}</td>
									<td class="btle3e3e3 bre3e3e3" align="center">
										<input type="button" value="明细" class="bae1e1e1 w40 z12ffffff bgff8001 detailed">
										<a href = "edit.jhtml?id=${serverProjectCategory.id}"><input type="button" value="编辑" class="bae1e1e1 w40 z12ffffff bg279fff"></a>
										<!--<input type="button" value="删除" onClick="deleteButton('${serverProjectCategory.id}');" class="bae1e1e1 w40 z12575757 bgfafafa">-->
									</td>
								</tr>
								<tr [#if serverProjectCategory_index%2==0]class="bge7f4ff"[/#if] style="display:none;" >
									<td colspan="3" class="btle3e3e3 bre3e3e3 pa20">
										项目名称：${serverProjectCategory.name}<br>
										创建时间：${serverProjectCategory.createDate?string("yyyy-MM-dd HH:mm:ss")}<br>
										项目描述：${serverProjectCategory.seoDescription}<br>
										详情图片：<a href="${serverProjectCategory.introduceImg}" target="_blank"><img onerror="this.src='${base}/resources/mechanism/images/j_i_1.png'" src="${serverProjectCategory.introduceImg}" width="75" height="28"></a><br>
										列表图片：<a href="${serverProjectCategory.logo}" target="_blank"><img onerror="this.src='${base}/resources/mechanism/images/j_i_1.png'" src="${serverProjectCategory.logo}" width="28" height="28"></a><br>
									</td>
								</tr>
								[/#list]
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="bte3e3e3" height="10"></td>
					</tr>
					<tr>
						[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
								[#include "/mechanism/include/pagination.ftl"]
				  		[/@pagination] 
					</tr>
					<tr>
						<td></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<div id="visit">
	<div class="visit">
  		<form id="visitform">
   		<table cellpadding="0" cellspacing="0" border="0" width="100%">
   			<tr>
   				<td align="center" class="k_4">
   					服务项目设置
  				</td>
   			</tr>
   			<tr>
   				<td>
   					<table cellpadding="0" cellspacing="0" border="0" width="90%" align="center" class="z14323232">
   						<tr>
   							<td height="50" class="bbe3e3e3" align="right">项目类别</td>
   							<td align="left" class="bbe3e3e3" colspan="2">
   								<select class="h30 w100 bg279fff z14ffffff baffffff m1020">
   									<option>物理治疗</option>
   									<option>作业治疗</option>
   								</select>
   							</td>
   						</tr>
						<tr>
							<td height="50" width="90" align="right" class="z14323232">
								项目名称
							</td>
							<td colspan="2">
								<input type="text" class="z14323232 inputkd9d9d9bgf6f6f6 h30 w420 m1020">
							</td>
						</tr>
   						<tr>
   							<td class="bbe3e3e3" align="right">详情描述</td>
   							<td align="right" class="bbe3e3e3" colspan="2">
   								<textarea class="z14323232 inputkd9d9d9bgf6f6f6 w420 h80 m1020"></textarea>
   							</td>
   						</tr>
   						<tr>
   							<td class="bbe3e3e3">详情页图片</td>
   							<td align="left" class="bbe3e3e3" width="120">
   								<img src="${base}/resources/mechanism/images/k.png" class="m1020 w80 h80">
   							</td>
   							<td align="left" valign="bottom" class="ptb10 bbe3e3e3 z12939393">
   								长宽比为750：280
   							</td>
   						</tr>
   						<tr>
   							<td class="bbe3e3e3">列表页图片</td>
   							<td align="left" class="bbe3e3e3">
   								<img src="${base}/resources/mechanism/images/k.png" class="m1020 w80 h80"> 
   							</td>
   							<td align="left" valign="bottom" class="ptb10 bbe3e3e3 z12939393">
   								长宽比为140：140
   							</td>
   						</tr>
						<tr>
							<td align="center" colspan="3" height="60">
								<input type="submit" value="确认" class="button_3">
								&nbsp;　&nbsp;
								<input type="button" value="取消" class="button_4 z14fefefe" onClick="$('#visitform')[0].reset();disp_hidden_d('visit','','','v_id');">
								<input type="hidden" id="v_id" name="v_id" value="">
							</td>
						</tr>
   					</table>
   				</td>
   			</tr>
   		</table>
   		</form>
    
	</div>
</div>
	
</body>
</html>