<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.member.list")} - Powered By HaoKangHu</title>
<meta name="author" content="HaoKangHu Team" />
<meta name="copyright" content="HaoKangHu" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>

<script type="text/javascript">
$().ready(function() {

	[@flash_message /]

 $('.status').click(function(){
     	var status = $(this).val();
     	console.log(status);
     	var id = $(this).attr('id');;
     	console.log(status);
		     if(status!=""){
		       console.log(status);
		        $.ajax({
		             type: "POST",
		             url: "updateStatus.jhtml",
		             data: {
		             id:id,
		             status:status
		             },
		             dataType: "json",
		             success: function(message){
		             	$.message(message);
		             	$('#refreshButton').click();
		               }
		         });
		     }
    });


});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.member.list")} <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="list.jhtml" method="get">
		<div class="bar">
			<a href="add.jhtml" class="iconButton">
				<span class="addIcon">&nbsp;</span>${message("admin.common.add")}
			</a>
			<div class="buttonWrap">
				<a href="javascript:;" id="deleteButton" class="iconButton disabled">
					<span class="deleteIcon">&nbsp;</span>${message("admin.common.delete")}
				</a>
				<a href="javascript:;" id="refreshButton" class="iconButton">
					<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
				</a>
				<div class="menuWrap">
					<a href="javascript:;" id="pageSizeSelect" class="button">
						${message("admin.page.pageSize")}<span class="arrow">&nbsp;</span>
					</a>
					<div class="popupMenu">
						<ul id="pageSizeOption">
							<li>
								<a href="javascript:;"[#if page.pageSize == 10] class="current"[/#if] val="10">10</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.pageSize == 20] class="current"[/#if] val="20">20</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.pageSize == 50] class="current"[/#if] val="50">50</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.pageSize == 100] class="current"[/#if] val="100">100</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<div class="menuWrap">
				<div class="search">
					<span id="searchPropertySelect" class="arrow">&nbsp;</span>
					<input type="text" id="searchValue" name="searchValue" value="${page.searchValue}" maxlength="200" />
					<button type="submit">&nbsp;</button>
				</div>
				<div class="popupMenu">
					<ul id="searchPropertyOption">
						<li>
							<a href="javascript:;"[#if page.searchProperty == "username"] class="current"[/#if] val="username">${message("Member.username")}</a>
						</li>
						<li>
							<a href="javascript:;"[#if page.searchProperty == "email"] class="current"[/#if] val="email">${message("Member.email")}</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th class="check">
					<input type="checkbox" id="selectAll" />
				</th>
				<th>
					<a href="javascript:;" class="sort" name="name">姓名</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="gender">性别</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="memberRank">级别</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="email">出生日期</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="phone">联系电话</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="address">联系地址</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="status">状态</a>
				</th>
				<th>
					<span>${message("admin.common.handle")}</span>
				</th>
			</tr>
			[#list page.content as doctor]
				<tr>
					<td>
						<input type="checkbox" name="ids" value="${doctor.id}" />
					</td>
					<td>
						${doctor.name}
					</td>
					<td>
						${message("Member.Gender." + doctor.gender)}
					</td>
					<td>
						${doctor.doctorCategory.name}
					</td>
					<td>
						<span title="${doctor.birth?string("yyyy-MM-dd HH:mm:ss")}">${doctor.birth}</span>
					</td>
					<td>
						${doctor.phone}
					</td>
					<td>
						${doctor.area.fullName} ${doctor.address}
					</td>
					<td>
						${message("Doctor.Status." + doctor.status)}
					</td>
					<td>
						<select class="status"  id = "${doctor.id}" name="status">
						<option value="">审核</option>
						[#list doctorStatus as status]
							<option value="${status}">
								${message("Doctor.Status." + status)}
							</option>
						[/#list]
					</select>
						<!--<a href="view.jhtml?id=${doctor.id}">[${message("admin.common.view")}]</a>
						<a href="view.jhtml?id=${doctor.id}">[添加评估报告]</a>
						<a href="edit.jhtml?id=${doctor.id}">[${message("admin.common.edit")}]</a>-->
					</td>
				</tr>
			[/#list]
		</table>
		[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
			[#include "/admin/include/pagination.ftl"]
		[/@pagination]
	</form>
</body>
</html>