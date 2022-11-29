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


	var $delete_evaluate = $("#delete_evaluate");//删除

	[@flash_message /]
	
	
	
	
	$delete_evaluate.click(function() {
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


		function deleteEvaluate(id){
			if(confirm('确定要删除选中的信息吗？')){
				$.ajax({
						url: "delete.jhtml",
						type: "POST",
						data: {
						ids:id
						},
						dataType: "json",
						cache: false,
						traditional:true,
						success: function(message) {
							$.message(message);
							location.reload(true);
						}
					});
			}
		}


</script>
</head>
<body>

    	<div class="nav">管理导航：
        <a href="javascript:;">用户管理首页</a>　
        <a href="javascript:;">患者信息</a>　
        <a href="javascript:;">预约信息</a>　
        <a href="javascript:;">回访信息</a>　
        <a href="javascript:;">账户信息</a>　
        <a href="javascript:;">医师信息</a>
      </div>
        <div class="seat">
        	<div class="left_z">医师信息</div>
            <div class="export">
      				<a href="javascript:;"><img src="${base}/resources/mechanism/images/print.png" border="0" class="top_img_b_1" /> 打印</a>
      				<a href="javascript:history.back(-1);"><img src="${base}/resources/mechanism/images/back.png" border="0" class="top_img_b_1" /> 返回</a>
            </div>
        
        </div>
        <form id="listForm" action="list.jhtml" method="get">
        <div class="detail">
        <table id="listTable" width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td colspan="2">&nbsp;</td>
          </tr>
          <tr>
            <td valign="top"><table width="99%" align="right" border="0" cellspacing="0" cellpadding="0">
              <tbody>
                <tr>
                  <td>
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="m_t_20_1">
            <tr>
              <td width="270" class="z_12_999999_1">评价时间：
              <input name="startDate" type="text" class="inp_3 h_28_1 w_80_1" id="startDate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '#F{$dp.$D(\'endDate\')}'});" />            
              -
              <input name="endDate" type="text" class="inp_3 h_28_1 w_80_1" id="endDate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd', minDate: '#F{$dp.$D(\'startDate\')}'});" />            
              </td>
              <td align="right">
                    <select name="projectId" id="type" class="cate_o">
                      <option value="">全部</option>
                      [#list projects as project]
                      	<option [#if projectId == project.id]selected = "true"[/#if]value="${project.id}">${project.name}</option>
                      [/#list]
                    </select>    
                <input type="text" name="nameOrmobile" id="nameOrmobile" [#if nameOrmobile??] value="${nameOrmobile}" [/#if] placeholder="姓名/电话" onfocus="keyfocus('姓名/电话','keys');" onblur="keyfocus('姓名/电话','keys');" class="inp_1 w_315_1" /><input type="submit" name="button4" id="button4" value=" 获取 " class="button_4 b_4c98f6_1" />          
              </td>
            </tr>
          </table>
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="z_14_666666_1">
           <tr>
              <td colspan="2" align="center" valign="top" height="5"><hr class="b_b_dcdcdc_2" /></td>
            </tr>
          [#list page.content as evaluate]
          <tr>
              <td width="30" align="center" valign="top" class="p_t_10_1"><input id="id" name="ids" type="checkbox" value="${evaluate.id}" onclick="unselectall('chkAll');" /></td>
              <td align="left" valign="top" class="p_10_20_1"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="100" align="left" valign="top"><img name="" onerror = "this.src='${base}/resources/mechanism/images/tmp_2.jpg'" src="${evaluate.order.patientMember.logo}" width="90" height="90" alt="" class="img_radius_70_1" /></td>
                  <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="25"><span class="z_20_4c98f6_1">${evaluate.project.name}</span><span class="z_12_999999_1">(订单号：${evaluate.order.sn})</span></td>
                      <td rowspan="5" width="100" align="right" valign="top"><input type="button" name="button7" id="button7" value=" 删除 " onClick="deleteEvaluate(${evaluate.id});" class="button_1 b_ececec_1 z_12_707070_1" /></td>
                    </tr>
                    <tr>
                      <td height="20">患者姓名：${evaluate.order.patientMember.name} <span class="z_12_999999_1">${evaluate.order.patientMember.nation} ${age(evaluate.order.patientMember.birth)}周岁 ${message("Member.Gender."+evaluate.order.patientMember.gender)} ${evaluate.order.patientMember.mobile}</span></td>
                    </tr>
                    <tr>
                      <td height="20">联 系 人：${evaluate.order.member.name} <span class="z_12_999999_1">${evaluate.order.member.nation} ${evaluate.order.member.mobile}</span></td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td align="right" valign="top">评价内容：</td>
                  <td valign="top">${evaluate.content}</td>
                </tr>
                <tr>
                  <td align="right" valign="top">项目评分：</td>
                  <td valign="top"><table width="600" border="0" cellspacing="0" cellpadding="0" class="pif">
                    <tr>
                      <td height="25" colspan="3"><span>综合得分</span><span class="pf_span">
                        <div class="pf_bg" style="width:${evaluate.scoreSort*10}%;"></div>
                        <img src="${base}/resources/mechanism/images/x_1.png" border="0" class="pos_re_2" /></span><span>${evaluate.scoreSort}.0分</span></td>
                    </tr>
                    <tr>
                      <td height="25"><span>技能评价</span><span class="pf_span">
                        <div class="pf_bg" style="width:${evaluate.skillSort*10}%;"></div>
                        <img src="${base}/resources/mechanism/images/x_1.png" border="0" class="pos_re_2" /></span><span>${evaluate.skillSort}.0分</span></td>
                      <td><span>服务能力</span><span class="pf_span">
                        <div class="pf_bg" style="width:${evaluate.serverSort*10}%;"></div>
                        <img src="${base}/resources/mechanism/images/x_1.png" border="0" class="pos_re_2" /></span><span>${evaluate.serverSort}.0分</span></td>
                      <td><span>沟通水平</span><span class="pf_span">
                        <div class="pf_bg" style="width:${evaluate.communicateSort*10}%;"></div>
                        <img src="${base}/resources/mechanism/images/x_1.png" border="0" class="pos_re_2" /></span><span>${evaluate.communicateSort}.0分</span></td>
                    </tr>
                  </table>
                  </td>
                </tr>
                <tr>
                  <td height="20" align="right" valign="top">评价时间：</td>
                  <td valign="top">${evaluate.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
                </tr>
              </table>
              </td>
            </tr>
            <tr>
              <td colspan="2" align="center" valign="top" height="5"><hr class="b_b_dcdcdc_3" /></td>
            </tr>
          [/#list]
            
          </table>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td class="b_f_4c98f6_1 pos_r_1" height="50">
	                  <table width="200" border="0" cellspacing="0" cellpadding="0" class="pos_re_2 f_z_1">
	                    <tr>
	                      <td width="42" height="30" align="center"><input name="chkAll" id="chkAll" type="checkbox" value="10" onclick="CheckAll('ids','chkAll');" /></td>
	                      <td>全选　<a id="delete_evaluate" href="javascript:;">删除选中项目</a></td>
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
                	<td height="50"></td>
                </tr>
              </tbody>
            </table></td>
            </tr>
        </table>
        </div> 
		</form>    
<div class="clear"></div>

</body>
</html>