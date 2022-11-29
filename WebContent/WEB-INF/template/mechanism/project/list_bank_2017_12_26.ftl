<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/js.js"></script>

<script type="text/javascript">
$().ready(function() {

	var $listForm = $("#listForm");
	var $filterSelect = $("#filterSelect");
	var $download = $("#download");
	[@flash_message /]
	
	
	// 筛选选项
	$filterSelect.bind("change",function() {
		var $this = $(this);
		
		$listForm.submit();
		return false;
	});

	//导出
	$download.click(function() {
		$listForm.attr('action','downloadList.jhtml');
		$listForm.submit();
		$listForm.attr('action','list.jhtml');		
	});



});
</script>


<script type="text/javascript">
 function udpateAudit()  {
     	var audit = $('#flag').val();
     	var id = $('#p_id').val();
     	var remarks = $('#remarks').val();
     	console.log(audit);
		     if(audit!=""){
		       console.log(audit);
		        $.ajax({
		             type: "POST",
		             url: "updateAudit.jhtml",
		             data: {
		             id:id,
		             audit:audit,
		             remarks:remarks
		             },
		             dataType: "json",
		             success: function(message){
		             	$.message(message);
		             	disp_hidden_d('layer');
		             	 location.reload();
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
        
        <form id="listForm" action="list.jhtml" method="get">
        <div class="seat">
        	<div class="left_z">服务项目</div>
            <div class="export" style="margin-top:20px">
	            <select name="audit" id="filterSelect" class="cate_o">
	             	<option value="">全部</option>${audit}
	             	[#list audits as au]
	             	<option [#if audit==au ] selected = "selected" [/#if]  value="${au}">${message("Project.Audit." + au)}</option>
	             	[/#list]
	            </select>
	            <a href="javascript:;" ><img src="${base}/resources/mechanism/images/print.png" border="0" class="top_img_b_1" /> 打印</a>
	            [#if valid('export')]<a href="javascript:;" id="download"><img src="${base}/resources/mechanism/images/export.png" border="0" class="top_img_b_1" /> 导出</a>[/#if]
            </div>
            
        </div>
        
        <div class="detail">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" id="cont_list" class="m_t_20_1">
            <tr class="bg_f5f5f5_1">
              <td height="35" align="center">项目名称</td>
              <td width="200" align="center">项目分类</td>
              <td align="center" width="100">价格<font>(元/分钟)</font></td>
              <td width="100" align="center">服务医师</td>
              <td width="70" align="center">审核状态</td>
              <td width="90" align="center">创建时间</td>
              <td width="120" align="center">审核</td>
            </tr>
            [#list page.content as project]
            <tr [#if project_index%2==0]class="fff"[/#if]>
              <td height="35" align="left">${project.name}</td>
              <td align="left">${project.serverProjectCategory.name}</td>
              <td align="center">${currency(project.price)}/${project.time}</td>
              <td align="center">${project.doctor.name}<font>&nbsp;</font></td>
              <td align="center">${message("Project.Audit." + project.audit)}</td>
              <td align="center">${project.createDate?string("yyyy-MM-dd")}<font>&nbsp;</font></td>
              <td align="center"><input type="button" name="button" id="button" value="明细" class="button_3 b_d1d141_1" onclick="show_info(${project.id},1);" />
              <input type="button" name="button7" id="button7" value="审核" class="button_3 b_4188d1_1" onclick="disp_hidden_d('layer','470','320','${project.id}')" /></td>
            </tr>
            <tr [#if project_index%2==0]class="fff"[/#if] style="display:none" id="info_${project.id}">
              <td height="35" colspan="7" align="left" class="p_10_20_1">
			              项目类别：${project.serverProjectCategory.name}<br />
			              项目名称：${project.name}<br/>
			              服务医师：${project.doctor.name}<br/>
			              项目价格：${currency(project.price)}元/${project.time}分钟<br/>
			              创建时间：${project.createDate?string("yyyy-MM-dd HH:mm:ss")}<br/>
			              详情描述：${project.introduce}<br/>
			              审核状态：${message("Project.Audit." + project.audit)}<br/>
			              审核说明：${project.remarks}
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
        	<div class="d_1_2"><span>状态：</span>
        				<select id="flag" name="flag" class="cate_o">
        				<option value="">请选择</option>
        				[#list audits as audit]
        				 <option value="${audit}">${message("Project.Audit." + audit)}</option>
        				[/#list]
                        </select>
                        <input name="p_id" id="p_id" type="hidden" value="" /></div>
            <div class="d_1_3"><span>备注：</span><textarea id="remarks" name="remarks" class="d_cont_1"></textarea></div>
            <div class="clear"></div>
            <div class="d_1_4">
            	<input type="button" name="button4" id="button4" value="　提　交　" class="button_1 b_4fc1e9_1" onclick="udpateAudit()" />　
            	<input type="button" name="button5" id="button5" value="　取　消　" class="button_1 b_d1d141_1" onclick="disp_hidden_d('layer');"/></div>
        </div>
        <div class="d_cl_1"><a href="javascript:;" onclick="disp_hidden_d('layer');"><img src="${base}/resources/mechanism/images/x.png" width="15" border="0" /></a></div>
    
	</div>
</div>

</body>
</html>