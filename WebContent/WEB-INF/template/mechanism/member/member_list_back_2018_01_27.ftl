<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("Common.Main.title")}</title>
<link href="${base}/resources/mechanism/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/js.js"></script>
<script type="text/javascript">
$().ready(function() {

		var $listForm = $("#listForm");
		var $download = $("#download");
		
		[@flash_message /]
		
		//导出
		$download.click(function() {
		$listForm.attr('action','downloadMemberList.jhtml');
		$listForm.submit();
		$listForm.attr('action','member_list.jhtml');		
		});
});
</script>
</head>
<body>
	<div class="nav">管理导航</div>
        <div class="seat">
        	<div class="left_z">注册用户管理</div>
            <div class="export">
              <a href="javascript:;"><img src="${base}/resources/mechanism/images/print.png" border="0" class="top_img_b_1" /> 打印</a>
              [#if valid('export')]<a href="javascript:;" id = "download"><img src="${base}/resources/mechanism/images/export.png" border="0" class="top_img_b_1" /> 导出</a>[/#if]
            </div>
        </div>
        <form id="listForm" action="member_list.jhtml" method="get">
          <div class="detail">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="listTable">
              <tr>
                <td>&nbsp;</td>
              </tr>
              <tr>
                <td height="40" align="left" valign="middle"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="40" align="right">
                    	<select id="type" name="type" class="cate_o">
								<option [#if type == "member"] selected = 'selected' [/#if] value="member">用户</option>
								<option [#if type == "patient"] selected = 'selected' [/#if]value="patient">患者</option>
		                </select>
	                    <input id="nameOrmobile" name="nameOrmobile" type="text" placeholder="联系人姓名/联系人电话" value="${nameOrmobile}" class="w_250_1" style="height: 28px;color: rgb(144, 144, 144);border-width: 1px;border-style: solid;border-color: rgb(220, 220, 220);border-image: initial;padding: 0px 30px;" /><input type="submit" name="button" id="button" value="搜索"  style="height: 30px;width:50px;color:rgb(255, 255, 255);left: 262px;border-radius: 0px 5px 5px 0px;border-width: 0px;border-style: initial;border-color: initial;border-image: initial;background:#dddddd;" />
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
                    <td height="25" width="30" align="center">序号</td>
                    <td width="70" class="p_l_r_5" align="center">姓名</td>
                    <td width="30" align="center">性别</td>
                    <td width="120" align="center">出生日期</td>
                    <td width="100" align="center">联系电话</td>
                    <td width="center" align="center">名下患者</td>
                    <td width="120" align="center">余额</td>
                    <td width="200" align="center">管理</td>
                  </tr>
                  [#list  page.content as member]
                  <tr [#if member_index%2==0]class="fff"[/#if]>
                    <td height="35" align="center">${member_index+1}</td>
                    <td align="left">${member.name}</td>
                    <td align="center">${message("Member.Gender."+member.gender)}</td>
                    <td align="center">${member.birth?string("yyyy年MM月dd日")}</td>
                    <td align="center">${member.mobile}</td>
                    <td align="center">[#list member.children as patient][#if !patient.isDeleted]${patient.name}&nbsp;&nbsp;[/#if][/#list]</td>
                    <td align="center">${currency(member.balance)}</td>
                    <td align="center">
                    	  <a href = "${base}/mechanism/balance/recharge.jhtml?memberId=${member.id}"><input type="button" name="button2" id="button2" value="充值" class="button_1 b_d1d141_1" /></a>
                          <a href = "${base}/mechanism/member/member_view.jhtml?id=${member.id}"><input type="button" name="button2" id="button2" value="明细" class="button_1 b_ee6a71_1" /></a>
                          <a href = "../order/list.jhtml?memberName=${member.name}"><input type="button" name="button3" id="button3" value="关联订单" class="button_1 b_40d0a7_1" /></a>
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
    </div>
<div class="clear"></div>
</body>
</html>