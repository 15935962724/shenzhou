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
	var $nameOrphone = $("#nameOrphone");
	
	var $doctorCategorieId = $("#doctorCategorieId");
	var $gender = $("#gender");
	var $serverProjectCategorieId = $("#serverProjectCategorieId");
	var $deleteDoctor = $("#deleteDoctor");
	var $download = $("#download");
	
	

	[@flash_message /]

	$doctorCategorieId.change(function(){
		$listForm.submit();
	}); 
	$gender.change(function(){
		$listForm.submit();
	});
	$serverProjectCategorieId.change(function(){
		$listForm.submit();
	});
				
	//导出
	$download.click(function() {
		$listForm.attr('action','downloadList.jhtml');
		$listForm.submit();
		$listForm.attr('action','list.jhtml');		
	});
	
	
	
	
	$deleteDoctor.click(function() {
		var $this = $(this);
		var $checkedIds = $("#listTable input[name='ids']:enabled:checked");
		
		if(confirm('确定要删除选中的信息吗？')){
			$.ajax({
					url: "delete.jhtml",
					type: "POST",
					data: $checkedIds.serialize(),
					dataType: "json",
					cache: false,
					success: function(message) {
						$.message(message);
						location.reload(true);
					}
				});
		}
		
		
	});
	


});
</script>

<script type="text/javascript">

		function disp_hidden(d_id,d_width,d_height,id)
			{
				if((typeof(d_width) != "undefined") && (typeof(d_height) !="undefined"))
					{
					$("."+d_id).css({"width":  d_width + "px","height": d_height + "px","left":"50%","top":"50%","margin-top":"-" + (d_height / 2) + "px","margin-left": "-" + (d_width / 2) + "px"});
					$(".d_l_1").css({"width":  (d_width-30) + "px","height": (d_height-30) + "px"});
					}
				$("#doctorId").val(id);
				$("#"+d_id).toggle(1000);	
			}

	function status(){
	    var id = $('#doctorId').val();
	    var status = $('#status').val();
	    var statusExplain = $('#statusExplain').val();
	   	if(status!=""){
		       console.log(status);
		        $.ajax({
		             type: "POST",
		             url: "updateStatus.jhtml",
		             data: {
		             id:id,
		             status:status,
		             statusExplain:statusExplain
		             },
		             dataType: "json",
		             success: function(message){
		             	$.message(message);
		             	disp_hidden('layer');
		             	location.reload();
		               }
		         });
		     }
	    
	}





</script>

</head>
<body>
<div class="nav">管理导航</div>
        <div class="seat">
        	<div class="left_z">医生技师</div>
            <div class="export">
            <a href="javascript:;"><img src="${base}/resources/mechanism/images/print.png" border="0" class="top_img_b_1" /> 打印</a>
            [#if valid('export')]<a href="javascript:;" id="download"><img src="${base}/resources/mechanism/images/export.png" border="0" class="top_img_b_1" /> 导出</a>[/#if]
          </div>
        
        </div>
        <form id="listForm" action="list.jhtml" method="get">
        <div class="detail">
        <table id="listTable" width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td height="40" align="left" valign="middle">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="40">
                <input id="nameOrphone" name="nameOrphone" type="text" value="${nameOrphone}"  placeholder="姓名/电话" class="scr_k" style="width:169px;" />
                <input type="button" name="button" id="button" value="搜索" class="scr_b" />
                </td>
                <td align="right">
                <ul>
                    <li><span>级别：</span>
                        <select id="doctorCategorieId" name="doctorCategorieId" class="cate_o" >
                            <option value="">全部</option>
                            [#list doctorCategorys as category]
                            <option [#if doctorCategory == category] selected = "selected"[/#if]  value="${category.id}">${category.title}</option>
                            [/#list]
                        </select>
                    </li>
                    <li><span>性别：</span>
                        <select id="gender" name="gender" class="cate_o">
                            <option value="">全部</option>
                            [#list genders as doctorGender]
                            <option [#if gender == doctorGender] selected = "selected" [/#if] value="${doctorGender}">${message("Member.Gender."+doctorGender)}</option>
                            [/#list]
                        </select>
                    </li>
                    <!--
                    <li><span>服务项目：</span>
                        <select id="serverProjectCategorieId" name="serverProjectCategorieId" class="cate_o">
                            <option value="">全部</option>
                            [#list serverProjectCategories as projectCategorie]
                            <option [#if serverProjectCategorie == projectCategorie]selected = "selected" [/#if]serverProjectCategorie value="${projectCategorie.id}">${projectCategorie.name}</option>
                            [/#list]
                        </select>
                    </li>
                    -->
                </ul>
                
                </td>
              </tr>
            </table></td>
            </tr>
          <tr>
            <td height="20" align="left" valign="middle"></td>
          </tr>
          <tr>
            <td height="20" align="left" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" id="cont_list">
              <tr class="bg_f5f5f5_1">
                <td height="35" width="30" align="center">选择</td>
                <td width="30" align="center">序号</td>
                <td width="70" class="p_l_r_5" align="center">医师</td>
                <td width="100" align="center">联系电话</td>
                <td width="100" align="center">总服务次数</td>
                <td width="100" align="center">状态</td>
                <td width="80" align="center">总患者人数</td>
                <td align="center">管理</td>
              </tr>
              [#list page.content as doctorMechanismRelation]
              <tr [#if doctorMechanismRelation_index%2==0]class="fff"[/#if]>
                <td height="35" align="center"><input name="ids" type="checkbox" value="${doctorMechanismRelation.id}" onclick="unselectall('chkAll');" /></td>
                <td align="center">${doctorMechanismRelation_index+1}</td>
                <td align="left">${doctorMechanismRelation.doctor.name}
                	<font style="z_12_ccc_1">
                		<br />${doctorMechanismRelation.doctor.doctorCategory.title} ${message("Member.Gender." + doctorMechanismRelation.doctor.gender)}
                	</font>
                </td>
                <td align="center">${doctorMechanismRelation.doctor.mobile}</td>
                <td align="center">${doctorMechanismRelation.doctor.second}次</td>
                <td align="center">${message("DoctorMechanismRelation.Audit." + doctorMechanismRelation.audit)}</td>
                <td align="center">${doctorMechanismRelation.doctor.parents.size()}人</td>
                <td align="center">
                      <input type="button" name="button2" id="button2" onclick="disp_hidden('layer','470','370','${doctorMechanismRelation.id}')"  value="审核" class="button_1 b_ee6a71_1" />
                      <a href="${base}/mechanism/doctor/view.jhtml?id=${doctorMechanismRelation.doctor.id}"><input type="button" name="button3" id="button3" value="详情" class="button_1 b_40d0a7_1" /></a>
                      <!--<input type="button" name="button4" id="button4" value="资料修改" class="button_1 b_4fc1e9_1" />-->
                      <a href="${base}/mechanism/doctor/project.jhtml?doctorId=${doctorMechanismRelation.doctor.id}"><input type="button" name="button5" id="button5" value="项目" class="button_1 b_d1d141_1" /></a>
                      <a href="${base}/mechanism/workDay/list.jhtml?nameOrphone=${doctorMechanismRelation.doctor.mobile}&name=${doctorMechanismRelation.doctor.name}"><input type="button" name="button6" id="button6" value="排班" class="button_1 b_d17441_1" /></a>
                      <a href="${base}/mechanism/doctor/doctor_visit_message_list.jhtml?doctorId=${doctorMechanismRelation.doctor.id}"><input type="button" name="button7" id="button7" value="回访" class="button_1 b_4188d1_1" /></a>
                      <a href="${base}/mechanism/order/list.jhtml?doctorName=${doctorMechanismRelation.doctor.name}"><input type="button" name="button8" id="button8" value="订单查阅" class="button_1 b_e94fdc_1" /></a>
                 </td>
              </tr>
              [/#list]
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td class="b_f_4c98f6_1 pos_r_1">
	                  <table width="200" border="0" cellspacing="0" cellpadding="0" class="pos_re_2 f_z_1">
	                    <tr>
	                      <td width="42" height="30" align="center"><input name="chkAll" id="chkAll" type="checkbox" value="10" onclick="CheckAll('ids','chkAll');" /></td>
	                      <td>全选　<a href="javascript:;"  id="deleteDoctor">删除选中项目</a></td>
	                    </tr>
	                  </table>
	                   [@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
								[#include "/mechanism/include/pagination.ftl"]
					   [/@pagination]                 
                  </td>
                </tr>
            </table>
            </td>
          </tr>
          <tr>
            <td align="left" valign="top">&nbsp;</td>
          </tr>
        </table>
        </div>      
    </div>
    </form>    
<div class="clear"></div>

<div id="layer">
	<div class="layer">
    	<div class="d_l_1">
	    	
	        	<div class="d_1_2">
		        	<input name="doctorId" id="doctorId" type="hidden" value="" />
		        	<span>状态：</span>
		        		<select id="status" name="status" class="cate_o">
			        		[#list DoctorMechanismRelationAudits as audit]
								<option value="${audit}">
									${message("DoctorMechanismRelation.Audit." + audit)}
								</option>
							[/#list]
		                </select>
	            </div>
	            <div class="d_1_3"><span>备注：</span><textarea id = "statusExplain" name="statusExplain" class="d_cont_1"></textarea></div>
	            <div class="clear"></div>
	            <div class="d_1_4">
	            	<input type="button" name="button4" id="button4" value="　提　交　" class="button_1 b_4fc1e9_1" onclick="status();" />　
	            	<input type="button" name="button5" id="button5" value="　取　消　" class="button_1 b_d1d141_1" onclick="disp_hidden('layer');"/>
	            </div>
	        
        </div>
        <div class="d_cl_1"><a href="javascript:;" onclick="disp_hidden('layer');"><img src="${base}/resources/mechanism/images/x.png" width="15" border="0" /></a></div>
    
	</div>
</div>


</body>
</html>