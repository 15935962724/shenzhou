<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("Common.Main.title")}</title>
<link href="${base}/resources/newmechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>
<script type="text/javascript">
$().ready(function() {

	[@flash_message /]

		$('.delete').click(function(){
		var id = $(this).attr("data_id");
			if(confirm("确定要删除吗？")){
				$.ajax({ 
				url: "delete.jhtml",
				type: "POST",
				data: {
					"id":id
				},
				datatype:"text",
				cache: false,
				success: function(message){
				if (message.type == "success") {
					location.reload(); 
				}else{
					$.message(message);
				}
				
				
		    	},
		    	error:function(message){
		    	$.message(message);
		    	}
		    });
		}
		
	});
	

});
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
				<form id="listForm" action="list.jhtml" method="get">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">角色设置</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							<a href="add.jhtml">新增角色</a>
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td colspan="2">
							<table cellpadding="0" cellspacing="0" border="0" width="100%" class="">
								<tr class="bg279fff z14ffffff">
									<td class="btle3e3e3" align="center" width="40" height="50">序号</td>
									<td class="btle3e3e3" align="center" width="200">角色名称</td>
									<td class="btle3e3e3" align="center" width="80">是否内置</td>
									<td class="btle3e3e3" align="center">描述</td>
									<td class="btle3e3e3" align="center" width="150">创建时间</td>
									<td class="btle3e3e3 bre3e3e3" align="center" width="200">操作</td>
								</tr>
								[#list  page.content as mechanismRole]
								<tr [#if mechanismRole_index%2==0]class="bge7f4ff"[/#if]>
									<td class="btle3e3e3" align="center" height="50">${mechanismRole_index+1}</td>
									<td class="btle3e3e3" align="center">${mechanismRole.name}</td>
									<td class="btle3e3e3" align="center">${message(mechanismRole.isSystem?string('是', '否'))}</td>
									<td class="btle3e3e3" align="left">${abbreviate(mechanismRole.description, 50, "...")}</td>
									<td class="btle3e3e3" align="center">${mechanismRole.createDate?string("yyyy-MM-dd")}<span class="z12939393"> ${mechanismRole.createDate?string("HH:mm:ss")}</span></td>
									<td class="btle3e3e3 bre3e3e3" align="center">
										<a href = "edit.jhtml?id=${mechanismRole.id}"><input type="button" value="修改" class="bae1e1e1 w40 z12ffffff bgff8001"></a>
										<a href = "doctorSetup.jhtml?mechanismRoleId=${mechanismRole.id}"><input type="button" value="用户管理" class="bae1e1e1 w65 z12ffffff bg279fff" ></a>
										[#if !mechanismRole.isSystem]
										<input type="button" value="删除" data_id="${mechanismRole.id}" class="bae1e1e1 w40 z12ffffff bg32d3ea delete" >
										[/#if]
									</td>
									
								</tr>
								[/#list]
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="bte3e3e3"></td>
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
				</form>
			</td>
		</tr>
	</table>
</div>
</body>
</html>