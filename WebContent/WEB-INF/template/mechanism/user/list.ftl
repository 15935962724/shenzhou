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

	[@flash_message /]

});
</script>
</head>
<body>
	<div class="nav">管理导航</div>
        <div class="seat">
        	<div class="left_z">管理员列表</div>
            <div class="export">
              <a href="javascript:;"><img src="${base}/resources/mechanism/images/print.png" border="0" class="top_img_b_1" /> 打印</a>
              <a href="javascript:;"><img src="${base}/resources/mechanism/images/export.png" border="0" class="top_img_b_1" /> 导出</a>
            </div>
        </div>
        <form id="listForm" action="list.jhtml" method="get">
          <div class="detail">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="listTable">
              <tr>
                <td>&nbsp;</td>
              </tr>
              
              <tr>
                <td height="40" align="left" valign="middle"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                  	<td align="left"><a href = "add.jhtml"><input type="button" name="button" id="button" value="添加管理员" class="button_1 b_4188d1_1" /></a></td>
                    <td height="40" align="right">
	                    <input id="name" name="name" type="text" placeholder="姓名" value="" class="w_250_1" style="height: 28px;color: rgb(144, 144, 144);border-width: 1px;border-style: solid;border-color: rgb(220, 220, 220);border-image: initial;padding: 0px 30px;" />
	                    <input type="submit" name="button" id="button" value="搜索"  style="height: 30px;width:50px;color:rgb(255, 255, 255);left: 262px;border-radius: 0px 5px 5px 0px;border-width: 0px;border-style: initial;border-color: initial;border-image: initial;" />
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
                    <td width="70" class="p_l_r_5" align="center">用户名</td>
                    <td width="70" align="center">姓名</td>
                    <td width="120" align="center">部门</td>
                    <td width="160" align="center">最后登录日期</td>
                    <td width="160" align="center">最后登录ip</td>
                    <td width="70" align="center">状态</td>
                    <td width="160" align="center">创建日期</td>
                    <td width="200" align="center">操作</td>
                  </tr>
                  [#list  page.content as user]
                  <tr [#if user_index%2==0]class="fff"[/#if]>
                    <td height="35" align="center">${user_index+1}</td>
                    <td align="center">${user.username}</td>
                    <td align="center">${user.name}</td>
                    <td align="center">${user.department}</td>
                    <td align="center">${(user.loginDate?string("yyyy-MM-dd HH:mm:ss"))!"-"}</td>
                    <td align="center">${user.loginIp!"-"}</td>
                    <td align="center">
                    	[#if !user.isEnabled]
							<span class="red">禁用</span>
						[#elseif user.isLocked]
							<span class="red"> 锁定 </span>
						[#else]
							<span class="green">正常</span>
						[/#if]
					</td>
                     <td align="center">${user.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
                    <td align="center">
                    	  <a href = "edit.jhtml?id=${user.id}"><input type="button" name="button2" id="button2" value="编辑" class="button_1 b_d1d141_1" /></a>
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