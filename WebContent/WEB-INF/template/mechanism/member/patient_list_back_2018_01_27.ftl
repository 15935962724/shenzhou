<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("Common.Main.title")}</title>
<link href="${base}/resources/mechanism/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/js.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/lhgcalendar.min.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>

<script type="text/javascript">
$().ready(function() {

var $inputForm = $("#inputForm");
	
	[@flash_message /]
	
	// 表单验证
	$inputForm.validate({
	
		rules: {
			diseaseName: "required",
			nowExplain: "required",
			assessResult: "required",
			proposal: "required",
			shortTarget: "required",
			longTarget: "required",
			startTime: "required",
			endTime: "required",
			summary: "required"
			
		},
		
		submitHandler: function(form) {
		var i = $('#project option').size();
		console.log($('#project option').text());
		console.log(i);
		if(i<1){
		$.message("warn", "至少添加一个康护项目");
		return false;
		}
		
		var tmp_str="";
		for(var j = 0; j < i; j++){
				tmp_str = tmp_str  + $('#project option:eq('+j+')').val() + "&";
		}
		
		console.log(tmp_str);
		$('#drillContent').val(tmp_str);
		
		 		form.submit();
		}
	});






});
</script>	


<script type="text/javascript" >
$(function(){

	//添加服务项目
	$("#remove").click(function(){
		if(!$("#project_s option").is(":selected"))
			{			
				alert("请选择康复项目");
				return false;
			}
		if(isNaN($("#n").val()) || $("#n").val()<1)
			{
				alert("服务次数应为大于0的数字");
				return false;
			}
		var idx = $("#project_s option:selected").val() + "," + $("#n").val();
		var txt = $("#n").val() + "次 " + $("#project_s option:selected").text()
		$("#project").append("<option value='" + idx + "'>" + txt + "</option>")
		$('#project_s option:selected').remove();
	});
	
	//取消服务项目
	$("#project").dblclick(function(){
		var idx = $("#project option:selected").val().split(",");
		var txt = $("#project option:selected").text().split("次 ");
		$("#project_s").append("<option value='" + idx[0] + "'>" + txt[1] + "</option>")
		$('#project option:selected').remove();
	})
	
			var $listForm = $("#listForm");
			var $download = $("#download");
			
			[@flash_message /]
			
			//导出
			$download.click(function() {
			$listForm.attr('action','downloadPatientList.jhtml');
			$listForm.submit();
			$listForm.attr('action','patient_list.jhtml');		
			});
	
	
	
})
  
  //隐藏框显示
function disp_hidden_div(d_id,d_width,d_height,id)
	{
		if((typeof(d_width) != "undefined") && (typeof(d_height) !="undefined"))
			{
				var w=d_width / 2;
				var h=d_height / 2;
				$("."+d_id).css({"width":  d_width + "px","height": d_height + "px","left":"50%","top":($(parent.document).scrollTop()+50) + "px","margin-left": "-" + w + "px"});
				$(".d_l_1").css({"width":  d_width + "px","height": d_height + "px"});
				if($(parent.document).height()<(d_height+$(parent.document).scrollTop()))
					$("#main_ifr",parent.document).height((parseInt(d_height)+$(parent.document).scrollTop()+150)+"px");
			}
		//lock_Scroll();
		$("#patientMemberId").val(id);
		$("#"+d_id).toggle();	
	}
  
  
  
  //上传预览
function upload_assessReport_img(upfile,upimg,pid,flag)
{
	$("#" + upfile + "_" + pid).click();
	$("#" + upfile + "_" + pid).on("change",function(){
		url=getObjectURL(this.files[0]);

		if (url) 
			{
				if(upimg!="")
				{
				if(flag=="1")
					{
						$("#" + upimg).attr("src", url) ; 
					}
				if(flag=="2")
					{
						tmpstr="<div class=\"isImg\"><img src=\"" + url + "\" /><button id=\"" + upimg + "_b_" + pid + "\" class=\"removeBtn\" onclick=\"javascript:removeImg('"+ upimg +"','"+ upfile +"',"+ pid +")\">x</button></div>"
						$("." + upimg).append(tmpstr);
						pid=pid+1;
						$("#" + upimg +"_file").append("<input id=\"" + upfile + "_" + pid + "\" name=\"" + upfile + "[" + pid + "].file" +"\" type=\"file\" style=\"display: none\"  />");
						$("#up_" + upimg).attr('onclick','upload_assessReport_img("' + upfile + '","' + upimg + '",' + pid +',"' + flag +'")');
						upimg="";
					}
				}
			}
	});
}

//删除预览图片
function removeImg(upimg,upfile,pid)
{
	var count=$("#" + upimg +"_file").children("input").size()-1;
	$("#" + upimg + "_b_" + pid).parent().remove();
	
		$("#" + upfile + "_" + pid).remove();
		$("#up_" + upimg).attr('onclick','upload_assessReport_img("' + upfile +'","' + upimg + '",'+ (count-1) +',"2")');
		for(var i=(pid+1);i<=count;i++)
			{
				var tmp_pid=i-1;
				$("#" + upfile + "_" + i).attr({'name':upfile + '_' + tmp_pid,'id':upfile + '_' + tmp_pid});
				$("#" + upimg + "_b_" + i).attr({'onclick':'removeImg("' + upimg + '","' + upfile +'",'+ tmp_pid + ')','id':upimg + '_b_' + tmp_pid});
			}
}
  
</script>

</head>
<body>
<div class="nav">管理导航</div>
<div class="seat">
	<div class="left_z">患者管理</div>
	<div class="export">
		<a href="javascript:;"><img src="${base}/resources/mechanism/images/print.png" border="0" class="top_img_b_1" /> 打印</a>
		[#if valid('export')]<a href="javascript:;" id="download" ><img src="${base}/resources/mechanism/images/export.png" border="0" class="top_img_b_1" /> 导出</a>[/#if]
	</div>
</div>
<form id="listForm" action="patient_list.jhtml" method="get">
	<div class="detail">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" id="listTable">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td height="40" align="left">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
					<td width="400" height="40" style="position: relative;">
					<input id="nameOrmobile" name="nameOrmobile" type="text" [#if nameOrmobile??] value="${nameOrmobile}" [/#if] placeholder="患者姓名/电话" class="scr_k m_t_15_1" />
					<input type="submit" name="button" id="button" value="搜索" class="scr_b m_t_15_1" style="background:#dddddd;" /></td>
					<td align="right">
					<select id="flag" name="healthType" class="cate_o" onchange="$('#listForm').submit();">
						<option value="">全部</option>
						[#list healthTypes as healthType]
							<option [#if healthType==healthtype ]selected="selected"[/#if] value="${healthType}">${message("Member.HealthType."+healthType)}</option>
						[/#list]
					</select>
					</td>
					</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="20" align="left" valign="middle"></td>
			</tr>
			<tr>
				<td height="20" align="left" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" id="cont_list">
						<tr class="bg_f5f5f5_1">
							<td width="30" height="30" align="center">序号</td>
							<td width="90" class="p_l_r_5" align="center">姓名</td>
							<td width="50" align="center">状态</td>
							<td width="110" align="center">入院日期</td>
							<td width="100" align="center">联系电话</td>
							<td width="95" class="p_l_r_5" align="center">联系人</td>
							<td align="center">户籍地址</td>                
							<td align="center">管理</td>
						</tr>
						[#list  page.content as patient]
						<tr [#if patient_index%2==0]class="fff"[/#if]>
							<td align="center">${patient_index+1}</td>
							<td align="left">${patient.name}<br /><font class="z_12_999999_1"> ${message("Member.Gender."+patient.gender)} ${age(patient.birth)}周岁 ${patient.nation}<br>${patient.birth?string("yyyy年MM月dd日")}</font></td>
							<td align="center">${message("Member.HealthType."+patient.healthType)}</td>
							<td align="center">${firstOrderDate(patient.id)}</td>
							<td align="center">${patient.mobile}</td>
							<td align="left">${patient.parent.name}<br><font class="z_12_999999_1"> ${message("Member.Gender."+patient.parent.gender)} ${patient.parent.mobile}</font></td>                
							<td align="left">${patient.area.fullName}${patient.address}</td>                
							<td align="center">
								<input type="button" name="button2" id="button2" value="添加评估报告"onClick="disp_hidden_div('report',550,500,'${patient.id}');" class="button_1 b_0ee4d2_1" />&nbsp;&nbsp;
								<a href="patient_view.jhtml?patientMemberId=${patient.id}"><input type="button" name="button2" id="button2" value="明细" class="button_1 b_ee6a71_1" /></a>
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
				</td>
			</tr>
			<tr>
				<td align="left" valign="top">&nbsp;</td>
			</tr>
		</table>
	</div> 
</form>     

<div class="clear"></div>

	
<div id="report" >
	<div class="report">
    	<div class="d_l_1">
    	<form id="inputForm" action="${base}/mechanism/assessReport/save.jhtml" method="post" enctype="multipart/form-data">
			<input type="hidden" id = "patientMemberId" name="patientMemberId" value="" />
			<input type="hidden" id = "orderId" name="orderId" value="" />
			<input type="hidden" id = "drillContent" name="drill" value="" />
			<table cellpadding="0" cellspacing="0" border="0" width="95%" align="left">
				<tr>
					<td class="b_b_dcdcdc_1">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td width="10"></td>
								<td width="100" height="35" class="pos_re_1">
									<div id="b_t_1" class="k_3 b_4c98f6_1"><a href="javascript:;" onClick="Toggle_tag('b',1,2)">诊评报告</a></div>
								</td>
								<td width="10"></td>
								<td width="100" class="pos_re_1">
									<div id="b_t_2" class="k_3 b_dcdcdc_1"><a href="javascript:;" onClick="Toggle_tag('b',2,2)">康复计划</a></div>
								</td>
								<td align="center" class="z_12_707070_1">
									<font color="#ff0000">* 诊评报告、康复计划均须填写</font>
								</td>
								<td align="right">
									<input type="submit" class="button_1 b_4fc1e9_1" value="提交">
									<input type="button" class="button_1" value="取消" onclick="disp_hidden_d('report');">
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td valign="top">
						<table id="b_i_1" cellpadding="0" cellspacing="0" border="0" width="100%" class="z_14_666666_1" style="display: block;">
							
							<tr>
								<td height="10" colspan="2"></td>
							</tr>
							<tr>
								<td width="70" height="35">评估医师：</td>
								<td>
									<select id="doctor" name = "doctorId" class="inp_1 w_180_1">
									[#list mechanism.doctors as doctor]
										<option value="${doctor.id}">${doctor.name}</option>
									[/#list]
									</select>
								</td>
							</tr>
							<tr>
								<td width="70" height="35">病患名称：</td>
								<td><input type="text" name="diseaseName"  id="diseaseName" class="inp_1 w_315_1"></td>
							</tr>
							<tr>
								<td height="35" valign="top">病情描述:</td>
								<td><textarea  name="nowExplain" id="nowExplain" rows="5" class="inp_2 h_80_1 w_315_1"></textarea></td>
							</tr>
							<tr>
								<td height="3" colspan="2"></td>
							</tr>
							<tr>
								<td height="35" valign="top">诊评结果:</td>
								<td><textarea name="assessResult" id="assessResult"  rows="5" class="inp_2 h_80_1 w_315_1"></textarea></td>
							</tr>
							<tr>
								<td height="3" colspan="2"></td>
							</tr>
							<tr>
								<td height="35" valign="top">康复建议:</td>
								<td><textarea  name="proposal" id="proposal"  rows="5" class="inp_2 h_80_1 w_315_1"></textarea></td>
							</tr>
							<tr>
								<td height="3" colspan="2"></td>
							</tr>
							<tr>
								<td height="35" valign="top">图片上传：</td>
								<td>
									<div class="img_div"></div>
									<img id="up_img_div" src="${base}/resources/mechanism/images/j_i_1.png" alt="" width="80" height="80" class="u_img_1" onClick="upload_assessReport_img('assessReportImages','img_div',0,'2');" />
									<div id="img_div_file">
										<input id="assessReportImages_0"  name="assessReportImages[0].file" type="file" style="display: none"  />
									</div>
								</td>
							</tr>
							<tr>
								<td></td>
								<td>
									<input type="hidden" id="p_id" value="">	
            					</td>
							</tr>
						</table>
						<table id="b_i_2" cellpadding="0" cellspacing="0" border="0" width="100%" class="z_14_666666_1" style="display: none;">
							<tr>
								<td height="10" colspan="2"></td>
							</tr>
							<tr>
								<td width="70" height="35">康复医师：</td>
								<td>
									<select id="doctor"  name = "redoctorId" class="inp_1 w_180_1">
										[#list mechanism.doctors as doctor]
											<option value="${doctor.id}">${doctor.name}</option>
										[/#list]
									</select>
								</td>
							</tr>
							<tr>
								<td width="70" height="35">审核医师：</td>
								<td>
									<select id="doctor" name = "auditDoctorId" class="inp_1 w_180_1">
										[#list mechanism.doctors as doctor]
											<option value="${doctor.id}">${doctor.name}</option>
										[/#list]
									</select>
								</td>
							</tr>
							<tr>
								<td height="35" valign="top">短期目标：</td>
								<td><textarea name="shortTarget" id="shortTarget"  rows="5" class="inp_2 h_80_1 w_315_1"></textarea></td>
							</tr>
							<tr>
								<td height="3" colspan="2"></td>
							</tr>
							
							<tr>
								<td height="35" valign="top">长期目标：</td>
								<td><textarea  name="longTarget" id="longTarget"  rows="5" class="inp_2 h_80_1 w_315_1"></textarea></td>
							</tr>
							<tr>
								<td width="70" height="35">康复周期：</td>
								<td>
									<input type="text"  id="startTime" name="startTime"  class="inp_1 " onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '#F{$dp.$D(\'endTime\')}'});" >-<input type="text"  id="endTime" name="endTime"  class="inp_1 " onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', minDate: '#F{$dp.$D(\'startTime\')}'});" >
								</td>
							</tr>
							<tr>
								<td height="35" valign="top">康复项目：</td>
								<td valign="top">
									<table cellpadding="0" cellspacing="0" border="0" width="100%">
										<tr>
											<td width="160" valign="top"><select name="project" size="6" class="w_150_1 h_110_1 b_a_dcdcdc_2" id="project"></select><br><font color="#FF0000">* 双击取消</font></td>
											<td width="30">&lt;--</td>
											<td width="160" valign="top">
											<select name="project_s" size="6" class="w_150_1 h_110_1 b_a_dcdcdc_2" id="project_s">
													[#list serverProjectCategorys as serverProjectCategory]
														<option value="${serverProjectCategory.id}">
															${serverProjectCategory.name}
														</option>
													[/#list]
											</select>
											</td>
											<td align="center">
											<input name="n" style="width:20px" type="text" id="n" value="1" size="4" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
											节
										  <input type="button" name="remove" id="remove" class="button_1 b_d17441_1" value="添加">
										  <br>
										  
											</td>
										</tr>
									</table>
								  </td>
							</tr>
							<!--
							<tr>
								<td height="35" valign="top">康复总结：</td>
								<td><textarea  name="summary" id="summary"  rows="5" class="inp_2 h_80_1 w_315_1"></textarea></td>
							</tr>
							-->
							<tr>
								<td></td>
								<td>
									<input type="hidden" id="p_id_2" value="">
            					</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			</form>
        </div>
        <!--div class="d_cl_1"><a href="javascript:;" onclick="disp_hidden_d('report');"><img src="${base}/resources/mechanism/images/x.png" width="15" border="0" /></a></div-->
	</div>
</div>


</body>
</html>