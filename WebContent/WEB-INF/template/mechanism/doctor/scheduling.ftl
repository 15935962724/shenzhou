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
		               }
		         });
		     }
	    
	}





</script>

</head>
<body>
    	<div class="nav">管理导航</div>
        <div class="seat">
        	<div class="left_z">排班管理</div>
            <div class="export">
            <a href="javascript:;"><img src="images/print.png" border="0" class="top_img_b_1" /> 打印</a>
            <a href="javascript:;"><img src="images/export.png" border="0" class="top_img_b_1" /> 导出</a>
            </div>
        </div>
        <form id="listForm" action="list.jhtml" method="get">
        <div class="detail">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" id="listTable">
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td height="40" align="left"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="400" height="40" style="position: relative;">
                <input id="keys" name="keys" type="text" placeholder="医师姓名/电话" class="scr_k m_t_15_1" />
                <input type="button" name="button" id="button" value="搜索" class="scr_b m_t_15_1 b_4c98f6_1" /></td>
                <td align="right">&nbsp;</td>
              </tr>
            </table></td>
            </tr>
          <tr>
            <td height="20" align="left" valign="middle"></td>
          </tr>
          <tr>
            <td height="20" align="left" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" id="cont_list">
              <tr class="bg_f5f5f5_1">
                <td height="50" width="30" align="center">选择</td>
                <td width="30" align="center">序号</td>
                <td width="100" class="p_l_r_5" align="center">姓名</td>
                <td align="center">今天<br /><font class="z_12_707070_1">2017-08-22</font></td>
                <td align="center">明天<br /><font class="z_12_707070_1">2017-08-23</font></td>
                <td align="center">后天<br /><font class="z_12_707070_1">2017-08-24</font></td>
                <td align="center">周五<br /><font class="z_12_707070_1">2017-08-25</font></td>
                <td align="center">周六<br /><font class="z_12_707070_1">2017-08-26</font></td>
                <td align="center">周日<br /><font class="z_12_707070_1">2017-08-27</font></td>
                <td align="center">周一<br /><font class="z_12_707070_1">2017-08-28</font></td>
              </tr>
              <tr class="fff">
                <td height="55" align="center"><input name="id" type="checkbox" value="1" onclick="unselectall('chkAll');" /></td>
                <td align="center">2</td>
                <td align="left">孙庆升<br />
					<font class="z_12_999999_1">男 副主任医师<br>13212341234</font>
               </td>
                <td align="center"><input type="button" name="button3" id="button3" value="已排" class="button_3 b_4c98f6_1" /></td>
                <td align="center"><input type="button" name="button3" id="button3" value="已排" class="button_3 b_4c98f6_1" /></td>
                <td align="center"><input type="button" name="button3" id="button3" value="已排" class="button_3 b_4c98f6_1" /></td>
                <td align="center"><input type="button" name="button3" id="button3" value="未排" class="button_3" /></td>
                <td align="center"><input type="button" name="button3" id="button3" value="未排" class="button_3" /></td>
                <td align="center"><input type="button" name="button3" id="button3" value="未排" class="button_3" /></td>
                <td align="center"><input type="button" name="button3" id="button3" value="未排" class="button_3" /></td>
              </tr>
              <tr>
                <td height="55" align="center"><input name="id" type="checkbox" value="1" onclick="unselectall('chkAll');" /></td>
                <td align="center">3</td>
                <td align="left">孙庆升<br />
					<font class="z_12_999999_1">男 副主任医师<br>13212341234</font>
               </td>
                <td align="center"><input type="button" name="button3" id="button3" value="已排" class="button_3 b_4c98f6_1" /></td>
                <td align="center"><input type="button" name="button3" id="button3" value="已排" class="button_3 b_4c98f6_1" /></td>
                <td align="center"><input type="button" name="button3" id="button3" value="已排" class="button_3 b_4c98f6_1" /></td>
                <td align="center"><input type="button" name="button3" id="button3" value="未排" class="button_3" /></td>
                <td align="center"><input type="button" name="button3" id="button3" value="未排" class="button_3" /></td>
                <td align="center"><input type="button" name="button3" id="button3" value="未排" class="button_3" /></td>
                <td align="center"><input type="button" name="button3" id="button3" value="未排" class="button_3" /></td>
              </tr>
              <tr class="fff">
                <td height="55" align="center"><input name="id" type="checkbox" value="1" onclick="unselectall('chkAll');" /></td>
                <td align="center">1</td>
                <td align="left">孙庆升<br />
					<font class="z_12_999999_1">男 副主任医师<br>13212341234</font>
               </td>
                <td align="center"><input type="button" name="button3" id="button3" value="已排" class="button_3 b_4c98f6_1" /></td>
                <td align="center"><input type="button" name="button3" id="button3" value="已排" class="button_3 b_4c98f6_1" /></td>
                <td align="center"><input type="button" name="button3" id="button3" value="已排" class="button_3 b_4c98f6_1" /></td>
                <td align="center"><input type="button" name="button3" id="button3" value="未排" class="button_3" /></td>
                <td align="center"><input type="button" name="button3" id="button3" value="未排" class="button_3" /></td>
                <td align="center"><input type="button" name="button3" id="button3" value="未排" class="button_3" /></td>
                <td align="center"><input type="button" name="button3" id="button3" value="未排" class="button_3" /></td>
              </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td class="b_f_4c98f6_1 pos_r_1"><div class="page">                        
                  	<div class="page_r_1">
						<span><a href="javascript:;">首页</a>　<a href="javascript:;">上一页</a>　<a href="javascript:;">1</a>　<a href="javascript:;">2</a>　<a href="javascript:;">3</a>　<a href="javascript:;">4</a>　<a href="javascript:;">5</a>　…　<a href="javascript:;">98</a>　<a href="javascript:;">99</a>　<a href="javascript:;">100</a>　<a href="javascript:;">下一页</a>　<a href="javascript:;">尾页</a>　共100页　跳转至<input id="page" name="page" type="text" value="" />　　页</span><span class="box_1"></span>
					</div> 
                  </div>                 
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