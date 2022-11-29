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
<script type="text/javascript" src="${base}/resources/mechanism/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/mechanism/js/chart.bundle.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/cookieUtil.js"></script>
<script type="text/javascript" src="${base}/resources/newmechanism/js/js.js"></script>
<script type="text/javascript">
$().ready(function() {

		var $listForm = $("#listForm");
		var $download = $("#download");
		var $inputForm = $("#inputForm");
		
		[@flash_message /]
		
		//导出
		$download.click(function() {
			$listForm.attr('action','downloadMemberList.jhtml');
			$listForm.submit();
			$listForm.attr('action','member_list.jhtml');		
		});
		
		
		//明细展开
		$(".detailed").click(function(){
			$(".detailed").parents("tr").next("tr").hide();		
			$(this).parents("tr").next("tr").show();
			ifr_height("main_ifr");
		})
		
			// 表单验证
		$inputForm.validate({
			submitHandler: function(form) {
				var modifyBalance = $('input:radio[name="modifyBalance"]:checked').val();
				
				if(modifyBalance==null){
				  $.message("warn", "${message("请选择充值金额")}");
				  return false;
				}
				
				if(modifyBalance==0){
					var money = $('#money').val();
						if(money==""){
						$.message("warn", "${message("请输入充值金额")}");
							return false;
						}else{
						   $('input:radio[name="modifyBalance"]:checked').val(money);
						    modifyBalance = $('input:radio[name="modifyBalance"]:checked').val();
						}
				}
				form.submit();
			}
		});
		
});
</script>

<script>
	//隐藏框显示
	function disp_hidden_d(d_id,d_width,d_height,p_id,id)
		{
			if((typeof(d_width) != "undefined") && (typeof(d_height) !="undefined"))
				{
					var w=d_width / 2;
					var h=d_height / 2;
					$("."+d_id).css({"width":  d_width + "px","height": d_height + "px","left":"50%","top":($(document).scrollTop()+50) + "px","margin-left": "-" + w + "px","margin-bottom":"50px"});
					if (self.frameElement && self.frameElement.tagName == "IFRAME")
						{
							$("."+d_id).css({"top":$(parent.document).scrollTop()+50})
							if($(parent.document).height()<(d_height+$(parent.document).scrollTop()))
								$("#main_ifr",parent.document).height((parseInt(d_height)+$(parent.document).scrollTop()+150)+"px");
						}

				}else{
					ifr_height("main_ifr");
				}
			$("#"+p_id).val(id);
			
			$.ajax({
		             type: "POST",
		             url: "query.jhtml",
		             data: {
		             id:id
		             },
		             dataType: "json",
		             success: function(data){
		             var last=data.data; 
		             var dataObj=eval("("+last+")");
				             console.log(dataObj);
				             console.log(dataObj.name);
				             $("#name").val(dataObj.name);      
				             $("#mobile").val(dataObj.username);      
		               }
		         });
			
			$("#"+d_id).toggle();	
		}	

		
		function Multiple(id)
			{
				$("input[name='modifyBalance']:eq(6)").attr("checked","checked");
				var str = $("#money").val();
				var pattern = /^(-)?[1-9][0-9]*$/;
				if(pattern.test(str) == false)
					{
						$.message("warn", "${message("充值金额只能为正数或负数")}");
						return false;
					}
				if($("#" + id).val() % 10)
					{
						$.message("warn", "${message("只能充值10元或10的倍数")}");
						return false;
					}
			}



</script>
</head>
<body>
<div class="m020">
	<table cellpadding="0" cellspacing="0" border="0" class="k_1 mtb30" id="page_nav">
		<tr>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" border="0" width="100%" class="k_2">
		<tr>
			<td class="pa20">
			 <form id="listForm" action="member_list.jhtml" method="get">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td class="z20616161 bb1dd4d4d4" height="50">用户管理</td>
						<td class="z16279fff bb1dd4d4d4" align="right">
							[#if valid('export')]<a href="javascript:;" id="download">导出</a>[/#if]
						</td>
					</tr>
					<tr>
						<td colspan="2" height="10"></td>
					</tr>
					<tr>
						<td class="z14444444" colspan="2" align="right">
							<select id="type" name="type" class="h30 w100 bae1e1e1">
									<option [#if type == "member"] selected = 'selected' [/#if] value="member">用户</option>
									<option [#if type == "patient"] selected = 'selected' [/#if]value="patient">患者</option>
							 </select>
							<input type="text" id="nameOrmobile" name="nameOrmobile" value="${nameOrmobile}" placeholder="姓名/电话" class="k_3 h30 bae1e1e1 plr10 w125">
							<input type="submit" value="查询" class="button_1_1 plr20 z16ffffff bg279fff h30">
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
									<td class="btle3e3e3" align="center" width="120">姓名</td>
									<td class="btle3e3e3" align="center" width="40">性别</td>
									<td class="btle3e3e3" align="center" width="120">出生日期</td>
									<td class="btle3e3e3" align="center" width="40">民族</td>
									<td class="btle3e3e3" align="center" width="100">联系电话</td>
									<td class="btle3e3e3" align="center">名下患者</td>
									<td class="btle3e3e3" align="center" width="100">平台账户余额</td>
									<td class="btle3e3e3" align="center" width="100">账户余额</td>
									<td class="btle3e3e3 bre3e3e3" align="center" width="100">操作</td>
								</tr>
								[#list  page.content as member]
								<tr [#if member_index%2==0]class="bge7f4ff"[/#if]>
									<td class="btle3e3e3" align="center" height="50">${member_index+1}</td>
									<td class="btle3e3e3" align="center">${member.name}</td>
									<td class="btle3e3e3" align="center">${message("Member.Gender."+member.gender)}</td>
									<td class="btle3e3e3" align="center">${member.birth?string("yyyy年MM月dd日")}</td>
									<td class="btle3e3e3" align="center">${member.nation}</td>
									<td class="btle3e3e3" align="center">${member.mobile}</td>
									<td class="btle3e3e3" align="center">[#list member.children as patient][#if !patient.isDeleted&&patients?seq_contains(patient)]${patient.name}&nbsp;&nbsp;[/#if][/#list]</td>
									<td class="btle3e3e3" align="center">${currency(member.balance)}</td>
									<td class="btle3e3e3" align="center">${currency(balance(mechanism.id,member.id))}</td>
									<td class="btle3e3e3 bre3e3e3" align="center">
										<input type="button" value="明细" class="bae1e1e1 w40 z12ffffff bgff8001 detailed">
										<input type="button" value="充值" class="bae1e1e1 w40 z12ffffff bg279fff" onClick="disp_hidden_d('assess',470,340,'p_id',${member.id})">
									</td>
								</tr>
								<tr [#if member_index%2==0]class="bge7f4ff"[/#if] style="display:none;">
									<td colspan="10" class="btle3e3e3 bre3e3e3 pa10">
										<table cellpadding="0" cellspacing="0" border="0" width="100%">
											<tr>
												<td width="50%" height="25" class="plr20">姓　　名：${member.name}</td>
												<td width="50%" class="plr20">性　　别：${message("Member.Gender."+member.gender)}</td>
											</tr>
											<tr>
												<td width="50%" height="25" class="plr20">出生日期：${member.birth?string("yyyy-MM-dd")}</td>
												<td width="50%" class="plr20">身份证号：${member.cardId}</td>
											</tr>
											<tr>
												<td width="50%" height="25" class="plr20">民　　族：${member.nation}</td>
												<td width="50%" class="plr20">联系电话：${member.mobile}</td>
											</tr>
											<tr>
												<td width="50%" height="25" class="plr20">户籍地址：${member.area.fullName}${member.address}</td>
												<td width="50%" class="plr20">长居地址：${member.area.fullName}${member.address}</td>
											</tr>
											<tr>
												<td width="50%" height="25" class="plr20">注册时间：${member.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
												<td width="50%" class="plr20">账户余额：${currency(balance(mechanism.id,member.id))}元</td>
											</tr>
											<tr>
												<td class="plr20">名下患者：
												[#list member.children as patient]
            				   		    			[#if !patient.isDeleted&&patients?seq_contains(patient)]
														${patient.name}、
													[/#if]
				                				[/#list]
	                							</td>
											</tr>
										</table>
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
<div id="assess">
	<div class="assess">
  		<form id="inputForm" method="post" action="${base}/mechanism/balance/save.jhtml">
  		
  		<input type="hidden" id="pageNumber" name="pageNumber" value="${page.pageNumber}">
  		<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
  		<input type="hidden" id="p_id" name="memberId" value="">
   		<table cellpadding="0" cellspacing="0" border="0" width="100%">
   			<tr>
   				<td align="center" class="k_4" colspan="2">
   					账户充值
  				</td>
   			</tr>
			<tr>
				<td height="30" width="90" class="z14323232" align="right">充值账号</td>
				<td align="left" class="plr20 ptb10">
					<input type="text" class="z14717171 inputkd9d9d9bgf6f6f6 h20 w310 plr10" id="mobile" disabled>
  				</td>
   			</tr>
   			<tr>
				<td height="30" width="90" class="z14323232" align="right">用户姓名</td>
				<td align="left" class="plr20 ptb10">
					<input type="text" class="z14717171 inputkd9d9d9bgf6f6f6 h20 w310 plr10" id = "name" disabled>
  				</td>
   			</tr>
   			<tr>
				<td height="30" width="90" class="z14323232 ptb10" align="right" valign="top">充值金额</td>
				<td align="left" class="plr20 ptb10" valign="top">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							  <td width="33%" height="25">
			                  	<input type="radio" name="modifyBalance"  value="200" />200元
			                  </td>
			                  <td width="33%">
			                  	<input type="radio" name="modifyBalance"  value="500" />500元
			                  </td>
			                  <td width="33%">
			                  	<input type="radio" name="modifyBalance"  value="1000" />1000元
			                  </td>
						</tr>
						<tr>
							  <td height="25">
			                  	<input type="radio" name="modifyBalance"  value="3000"  />3000元
			                  </td>
			                  <td>
			                  	<input type="radio" name="modifyBalance"  value="5000"  />5000元
			                  </td>
			                  <td>
			                  	<input type="radio" name="modifyBalance"  value="10000"  />10000元
			                  </td>
						</tr>
						<tr>
							<td colspan="3" height="25">
								<input type="radio" id="modifyBalance" name="modifyBalance" value="0"> 其他
								<input type="text" class="w50 h15 banone bb1dd4d4d4 plr10" onblur="Multiple('money');" onpaste="return false;" id="money" name="money">元 <span class="z14ff0000">* 正数充值 负数扣除</span>
							</td>
						</tr>
					</table>
  				</td>
   			</tr>
   			<tr>
				<td height="30" width="90" class="z14323232" align="right">备注说明</td>
				<td align="left" class="plr20 ptb10">
					<textarea name="memo" class="z14323232 inputkd9d9d9bgf6f6f6 h40 w310 plr10 ptb10"></textarea>
  				</td>
   			</tr>
   			<tr>
   				<td align="center" colspan="2" height="40" valign="middle">
   					<input type="submit" value="确认" class="button_3">
   					&nbsp;　&nbsp;
					<input type="button" value="取消" class="button_4 z14fefefe" onClick="$('#inputForm')[0].reset();disp_hidden_d('assess','','','p_id');">
   				</td>
   			</tr>
   		</table>
   		</form>
	</div>
</div>
</body>
</html>