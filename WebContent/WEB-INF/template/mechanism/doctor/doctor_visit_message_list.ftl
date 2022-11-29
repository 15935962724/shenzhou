<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>好康护 - 让幸福简单起来</title>
<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/message.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/js.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>

<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	
	var $doctorId = $("#doctorId");
	var $patientMemberId = $("#patientMemberId");
	var $visitDate = $("#visitDate");
	var $message = $("#message");
	
	[@flash_message /]
	
	// 表单验证
	$inputForm.validate({
		rules: {
			doctorId: "required",
			patientMemberId: "required",
			visitDate: "required",
			message: "required"
		},
		submitHandler: function(form) {
			form.submit();
		}
	});
	
	$('.delete').click(function(){
		var id = $(this).attr("data_id");
		
			if(confirm("确定要删除吗？")){
				$.ajax({ 
				url: "delete_visit_message.jhtml",
				type: "POST",
				data: {
					"id":id
				},
				datatype:"text",
				cache: false,
				success: function(data){
				 $.message("删除成功!");
				window.location.reload();
		    	},
		    	error:function(message){
		    	alert(message);
		    	}
		    });
		}
		
	   
	
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
		             url: "${base}/mechanism/project/updateAudit.jhtml",
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
        <a href="http://www.sina.com.cn">管理首页</a>　
        <a href="javascript:;">新增医师</a>　
        <a href="javascript:;">排班管理</a>
      </div>
        <div class="seat">
        	<div class="left_z">医师信息</div>
            <div class="export">
      				[#include "/mechanism/include/export.ftl" /]    
            </div>
        
        </div>
        <div class="detail">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td colspan="2">&nbsp;</td>
          </tr>
          <tr>
            <td width="280" valign="top">
            	<div class="peo_2">
            		<table cellpadding="0" cellspacing="0" border="0">
            			<tr>
            				<td><img onerror="this.src='${base}/resources/mechanism/images/add_img.png'" src="${doctor.logo}" alt="${doctor.name} ${doctor.doctorCategory.name}　${message("Member.Gender."+doctor.gender)}" title="${doctor.name} ${doctor.doctorCategory.name}　${message("Member.Gender."+doctor.gender)}" class="head_1" /></td>
            			</tr>
            			<tr>
            				<td height="50" class="b_a_dcdcdc_1"><span class="sp_m_1">${doctor.name}</span><span class="z_12_999999_1">　${doctor.doctorCategory.name}　${message("Member.Gender."+doctor.gender)}</span></td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">联系电话：${doctor.mobile}</td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">岗位级别：${doctor.doctorCategory.name}</td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">出生日期：${doctor.birth?string("yyyy-MM-dd")}</td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">用户积分：1000分</td>
            			</tr>
            			<tr>
            				<td height="35" class="b_a_dcdcdc_1">用户评分：
            					<table width="200" cellpadding="0" cellspacing="0" border="0" class="pif_1">
            						<tr>
            							<td align="center">
											<span>综合评分</span>
            								<span class="pf_span_1">
												<div class="pf_bg_1" style="width:${doctor.scoreSort*10}%;"></div>
												<img src="${base}/resources/mechanism/images/x_1.png" border="0" class="pos_re_2" />
           									</span>
           									<span>${doctor.scoreSort}分</span>
										</td>
            						</tr>
            						<tr>
            							<td align="center">
											<span>技能评价</span>
            								<span class="pf_span_1">
												<div class="pf_bg_1" style="width:${doctor.skillSort*10}%;"></div>
												<img src="${base}/resources/mechanism/images/x_1.png" border="0" class="pos_re_2" />
           									</span>
           									<span>${doctor.skillSort}分</span>
										</td>
            						</tr>
            						<tr>
            							<td align="center">
											<span>服务能力</span>
            								<span class="pf_span_1">
												<div class="pf_bg_1" style="width:${doctor.serverSort*10}%;"></div>
												<img src="${base}/resources/mechanism/images/x_1.png" border="0" class="pos_re_2" />
           									</span>
           									<span>${doctor.serverSort}分</span>
										</td>
            						</tr>
            						<tr>
            							<td align="center">
											<span>沟通能力</span>
            								<span class="pf_span_1">
												<div class="pf_bg_1" style="width:${doctor.communicateSort*10}%;"></div>
												<img src="${base}/resources/mechanism/images/x_1.png" border="0" class="pos_re_2" />
           									</span>
           									<span>${doctor.communicateSort}分</span>
										</td>
            						</tr>
            					</table>
            				</td>
            			</tr>
            			<tr>
            				<td>
            				</td>
            			</tr>
            		</table>
            	</div>
            </td>
            <td valign="top"><table width="99%" align="right" border="0" cellspacing="0" cellpadding="0">
              <tbody>
                <tr>
                  <td height="40">
					<div class="peo_tag_1">
						<ul>
							<li style="width:17%;"><div class="border_radius_l_1"><a href="${base}/mechanism/doctor/view.jhtml?id=${doctor.id}">基本信息</a></div><div class="div_x_1"></div></li>
							<li style="width:16%;"><div><a href="${base}/mechanism/doctor/project.jhtml?doctorId=${doctor.id}">服务项目</a></div><div class="div_x_1"></div></li>
							<li style="width:17%;"><div><a href="${base}/mechanism/workDay/list.jhtml?nameOrphone=${doctor.mobile}&name=${doctor.name}">排班信息</a></div><div class="div_x_1"></div></li>							
							<li style="width:16%;"><div><a href="${base}/mechanism/doctor/doctor_reserve.jhtml?doctorId=${doctor.id}">预管理项目</a></div><div class="div_x_1"></div></li>
							<li style="width:17%;"><div class="sel_div_1"><a href="${base}/mechanism/doctor/doctor_visit_message_list.jhtml?doctorId=${doctor.id}">回访信息</a></div><div class="div_x_1"></div></li>
							<li style="width:17%;"><div class="border_radius_r_1"><a href="${base}/mechanism/doctor/doctor_evaluate_list.jhtml?doctorId=${doctor.id}">评价信息</a></div><div class="div_x_1"></div></li>
						</ul>
					</div>
               	  </td>
                </tr>
                <tr>
                  <td>
                  	   <form id="listForm" action="doctor_visit_message_list.jhtml" method="get">
                  	   <input type="hidden" name="doctorId" value="${doctor.id}" />
					  	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="z_14_707070_1" >
					  		<tr class="fff">
					  			<td height="45" class="b_l_r_b_dcdcdc_1"><table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
					  			  <tbody>
					  			    <tr>
					  			      <td height="40" align="right" class="z_14_4c98f6_1 pos_r_1"><a href="javascript:;" onClick="disp_hidden_d('layer',515,330,1);">添加回访信息</a> <a href="javascript:;" onClick="$('.lay_flt_1').toggle(1000);">筛选</a>
					  			      	<div class="lay_flt_1">
											<table cellpadding="0" cellspacing="0" border="0" width="95%" align="center" class="m_t_10_1">
												<tr>
													<td height="45" align="left">建立时间：<input id="startCreateDate" type="text" name="startCreateDate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '#F{$dp.$D(\'endCreateDate\')}'});" class="inp_1 w_70_1"> - <input id="endCreateDate" name="endCreateDate" type="text"  onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', minDate: '#F{$dp.$D(\'startCreateDate\')}'});" class="inp_1 w_70_1"> </td>
													<td rowspan="4" valign="top" width="30" align="left"><a href="javascript:;" onClick="$('.lay_flt_1').toggle(1000);"><img src="${base}/resources/mechanism/images/x.png" width="15" border="0" class="m_t_10_1" /></a></td>
												</tr>
												<tr>
													<td align="left">回访时间：<input id="startVisitDate" type="text" name="startVisitDate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '#F{$dp.$D(\'endVisitDate\')}'});" class="inp_1 w_70_1"> - <input id="endVisitDate" name="endVisitDate" type="text" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', minDate: '#F{$dp.$D(\'startVisitDate\')}'});" class="inp_1 w_70_1"></td>
												</tr>
												<!--
												<tr>
													<td height="50"><input type="text" placeholder="用户姓名/用户电话" name="nameOrMobile" class="inp_1 w_255_1"></td>
												</tr>
												-->
												<tr>
													<td height="60" align="right"><input type="button" name="button4" id="button4" value="信息获取" class="button_1 b_4fc1e9_1"  onClick="$('.lay_flt_1').toggle(1000);$('#listForm').submit();" />　</td>
												</tr>
											</table>
					  			      		
					  			      	</div>
					  			      
					  			      </td>
				  			        </tr>
					  			    <tr>
					  			      <td>
										  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="z_14_666666_1">
											<tr>
											  <td align="center" valign="top" height="5"><hr class="b_b_dcdcdc_2" /></td>
											</tr>
											[#list page.content as visitMessage]
												<tr>
												  <td align="left" valign="top" class="p_10_20_1">
													  <table width="100%" border="0" cellspacing="0" cellpadding="0">
														<tr>
														  <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
															<tr>
															  <td height="20">回访人：${visitMessage.doctor.name}</td>
															</tr>
															<tr>
															  <td height="20">被访人：${visitMessage.member.name}</td>
															</tr>
															<tr>
															  <td height="20">回访时间：${visitMessage.visitDate?string("yyyy-MM-dd HH:mm:ss")}</td>
															  <td rowspan="3" width="70" align="right" valign="top">
																  <a href="doctor_visit_message_edit.jhtml?visitMessageId=${visitMessage.id}"><input type="button" name="button7" id="button7" value=" 修改 " class="button_1 b_4c98f6_1" /></a>
																  <input type="button" name="button7" data_id="${visitMessage.id}" value=" 删除 " class="button_1 b_ee6a71_1 delete" />
															  </td>
															</tr>
															<tr>
															  <td height="20">建立时间：${visitMessage.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
															</tr>
															<tr>
															  <td height="20">回访反馈：${visitMessage.message}</td>
															  </tr>
														  </table></td>
														</tr>
													  </table>
												  </td>
												</tr>
												<tr>
												  <td align="center" valign="top" height="5"><hr class="b_b_dcdcdc_3" /></td>
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
					  			      </td>
				  			        </tr>
					  			    <tr>
					  			      <td>&nbsp;</td>
				  			        </tr>
					  			    <tr>
					  			      <td>&nbsp;</td>
				  			        </tr>
				  			      </tbody>
				  			    </table></td>
				  			</tr>
					  	</table>
					  </form>
				  </td>
            </tr>
        </table>
        </div> 

<div class="clear"></div>

<div id="layer" style="display: none;">
	<div class="layer" >
    	<div class="d_l_1">
    	   <form id="inputForm" action="save_visit_message.jhtml" method="post">
    	   <input type="hidden" name="doctorId" value="${doctor.id}" />
			<table cellpadding="0" cellspacing="0" border="0" width="95%" align="right">
				<tr>
					<td valign="top" class="z_20_4c98f6_1" align="left" width="70" colspan="2">回访信息</td>
				</tr>
				<tr>
					<td valign="top" align="left" width="70" colspan="2"><hr class="b_b_dcdcdc_3"></td>
				</tr>
				<tr>
					<td colspan="2">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td class="z_14_4c98f6_1" align="right" width="70" height="40">被访人：</td>
							<td>
								<select id="patientMemberId" name="patientMemberId" class="inp_1 w_250_1">
									<option value="">请选择</option>
									[#list mechanism.patientMember as patient]
	                				   <option value="${patient.id}">${patient.name}</option>
	                				[/#list]
								</select>
							</td>
						</tr>
						<tr>
							<td class="z_14_4c98f6_1" align="right" height="40">回访时间：</td>
							<td><input id="visitDate" type="text" class="inp_1 w_315_1" name="visitDate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});"></td>
						</tr>
						<tr>
							<td class="z_14_4c98f6_1" align="right" valign="top">回访反馈：</td>
							<td><textarea id="message" name="message" class="inp_1 w_315_1" style="height: 110px;"></textarea></td>
						</tr>
						<tr>
							<td colspan="2" height="10"></td>
						</tr>
						<tr>
							<td class="z_14_4c98f6_1" align="right">&nbsp;</td>
							<td>
								<input type="submit" name="button9" id="button9" value="确定" class="button_1 b_4fc1e9_1" />
								<input type="button" name="button8" id="button8" value="取消" class="button_1" onclick="disp_hidden_d('layer');" />
								<input type="hidden" name="p_id" id="p_id" value="">
							</td>
						</tr>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
						
					</table>
					</td>
				</tr>
			</table>
			</form>
        </div>
        <div class="d_cl_1"><a href="javascript:;" onclick="disp_hidden_d('layer');"><img src="${base}/resources/mechanism/images/x.png" width="15" border="0" /></a></div>
    
	</div>
</div>
	
</body>
</html>