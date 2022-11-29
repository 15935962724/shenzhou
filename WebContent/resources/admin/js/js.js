// JavaScript Document


var shenzhou = {
	base: "/shenzhou",
	locale: "zh_CN"
};


//框架左右全屏显示
//window.onload = function(){
//	if(document.body.scrollHeight > document.body.clientHeight)
//		{
//			
//			document.getElementById("nav").style.height=document.body.scrollHeight + "px";
//			document.getElementById("cont").style.height=(document.body.scrollHeight-80) + "px";	
//		}
//	else
//		{
//			if((document.getElementById("nav").clientHeight > document.body.clientHeight) || (document.getElementById("cont").clientHeight > document.body.clientHeight))
//				{
//					if(document.getElementById("nav").clientHeight > document.getElementById("cont").clientHeight)
//						{
//							document.getElementById("cont").style.height = document.getElementById("nav").clientHeight;
//						}
//					else
//						{
//							document.getElementById("nav").style.height = document.getElementById("cont").clientHeight;
//						}	
//				}
//			else
//				{
//					document.getElementById("nav").style.height=document.body.clientHeight + "px";
//					document.getElementById("cont").style.height=document.body.clientHeight + "px";	
//				}
//		}
//				//alert(document.getElementById("right").clientHeight);
//}




function ifr_height(ifr)
{
    var main = $(window.parent.document).find("#" + ifr);
    var thisheight = $(document.body).height() + 100;
    if(thisheight<=500)
	{
		thisheight = $(window).height;
	}
	main.height(thisheight);
}
//自动调整iframe高度
$(window).load(function () {
	ifr_height("main_ifr");
});

//左侧菜单

//左侧菜单
$(function(){
	$("#nav dt").click(function(){
		if($(this).parent().find("dd").css("display")=="block")
			{
				$(this).css({"background-color":"#f6f9fa","color": "#666666"});
				$(this).parent().find("img").attr("src",  shenzhou.base + "/resources/mechanism/images/off.png");
				$(this).parent().find('dd').slideToggle();
			}
		else
			{
				$("#nav dd").hide();
				$("#nav dt").css({"background-color":"#f6f9fa","color": "#666666"})
				$(this).css({"background-color": "#4c98f6","color": "#fff"});
				$("#nav dt img").attr("src", shenzhou.base + "/resources/mechanism/images/off.png");
				$(this).parent().find('img').attr("src", shenzhou.base + "/resources/mechanism/images/on.png");
				$(this).parent().find('dd').slideToggle();
			}
	});
	$("#nav dd").click(function(){
		$("#nav dd").removeClass("x_1");
		$(this).addClass("x_1");
	});
})

//搜索框默认内容切换
function keyfocus(key,ele)
	{
		if($("#" + ele).val()==key)
		{
			$("#" + ele).val("");
		}
		else
			if($("#" + ele).val()=="")
			{
				$("#" + ele).val(key);
			}
	}



function lock_Scroll()
	{
		if($(parent.document.body).css("overflow-x")=="hidden")
			{
				 $(parent.document.body).css({"overflow-x":"auto","overflow-y":"auto"});
			}
		else
			{
				 $(parent.document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
			}
	}

//隐藏框显示
function disp_hidden_d(d_id,d_width,d_height,id)
	{
		if((typeof(d_width) != "undefined") && (typeof(d_height) !="undefined"))
			{
				var w=d_width / 2;
				var h=d_height / 2;
				$("."+d_id).css({"width":  d_width + "px","height": d_height + "px","left":"50%","top":($(parent.document).scrollTop()+50) + "px","margin-left": "-" + w + "px"});
				$(".d_l_1").css({"width":  (d_width-40) + "px","height": (d_height-40) + "px"});
				if($(parent.document).height()<(d_height+$(parent.document).scrollTop()))
					$("#main_ifr",parent.document).height((parseInt(d_height)+$(parent.document).scrollTop()+150)+"px");
			}
		//lock_Scroll();
		$("#p_id").val(id);
		$("#"+d_id).toggle();	
	}



//取消全选	
function unselectall(chkall)
{
	if($("#"+chkall).get(0).checked)
		$("input[name='" + chkall + "']").removeAttr("checked"); 
}

//全选
function CheckAll(id,chkall)
{
	if($("#"+chkall).get(0).checked)
		{
			$("input[name='" + id + "']").attr("checked","true"); 
		}
	else
		{
			$("input[name='" + id + "']").removeAttr("checked"); 
		}
	}
	
	
function InitAjax() 
	{ 
		var ajax=false; 
		try 
			{ 
				ajax = new ActiveXObject("Msxml2.XMLHTTP"); 
			} 
		catch (e) 
			{ 
			try 
				{ 
					ajax = new ActiveXObject("Microsoft.XMLHTTP"); 
				} 
			catch (E) 
				{ 
					ajax = false; 
				} 
			} 
		if (!ajax && typeof XMLHttpRequest!='undefined') 
			{ 
				ajax = new XMLHttpRequest(); 
			} 
		return ajax; 
	} 
//ajax取内容	
function getcontent(files,vars) 
	{ 
		if (typeof(files) == 'undefined' || typeof(vars) == 'undefined') 
			{ 
				return false; 
			} 
		var url = files + "&t=" + new Date().getTime();	
		var show = document.getElementById(""+vars); 
		var ajax = InitAjax(); 
		
		ajax.open("GET", url, true); 
		
		ajax.onreadystatechange = function() { 
			if (ajax.readyState == 4 && ajax.status == 200) 
				{ 
					if(ajax.responseText=="errors:100")
						{
							show.innerHTML = "信息错误，请联系管理员";
						}
					else
						{
							show.innerHTML = ajax.responseText; 
						}
				} 
			} 
		ajax.send(null); 
	}
	
//select中内容移到另一select种
function left_to_right(source,target)
	{ 
		//alert(1);
		$("#" + source + " option:selected").appendTo("#" + target);
	}

//本地图片未上传预览
//function preview_img(upfile,upimg,pid,flag)
//{
//	$("#" + upfile).click();
//	$("#" + upfile).on("change",function(){
//		url=getObjectURL(this.files[0]);
//
//		if (url) 
//			{
//				if(upimg!="")
//				{
//				if(flag=="1")
//					{
//						$("#" + upimg).attr("src", url) ; 
//					}
//				if(flag=="2")
//					{
//						pid=pid+1;
//						$("." + upimg).append("<div class='isImg'><img src='" + url + "' /><button class='removeBtn' onclick='javascript:removeImg(this)'>x</button></div>");
//						$("#" + upimg +"_file").append("<input id=\"upfile_" + pid + "\" name=\"upfile_" + pid + "\" type=\"file\" style=\"display: none\"  />");
//						$("#up_" + upimg).attr('onclick','preview_img("upfile_' + pid + '","' + upimg + '",' + pid +',"' + flag +'")');
//						upimg="";
//					}
//				}
//			}
//	});
//}
//删除预览图片
//function removeImg(r)
//{
//	$(r).parent().remove();
//}


////获取图片地址
function getObjectURL(file) {
	var url = null;
	if(window.createObjectURL != undefined) { // basic
		url = window.createObjectURL(file);
	} else if(window.URL != undefined) { // mozilla(firefox)
		url = window.URL.createObjectURL(file);
	} else if(window.webkitURL != undefined) { // webkit or chrome
		url = window.webkitURL.createObjectURL(file);
	}
	//console.log(url);
	return url;
}


function show_info(sid,flag)
	{
		$("#info_"+sid).toggle();
		
		if(flag=="1")
			{
				ifr_height("main_ifr");
			}
		else
			{
			lock_Scroll();
			}
	}


//切换标签
function Toggle_tag(til,id,count)
{
	for(var i = 1; i <= count; i++)
		{
			if($("#" + til + "_t_" + i).attr("class").indexOf("b_4c98f6_1")>0)
				{
					$("#" + til + "_t_" + i).removeClass("b_4c98f6_1");
				}
			if($("#" + til + "_t_" + i).attr("class").indexOf("b_dcdcdc_1")>0)
				{
					$("#" + til + "_t_" + i).removeClass("b_dcdcdc_1");
				}
			if(id == i)
				{
					$("#" + til + "_t_" + i).addClass("b_4c98f6_1"); 
					$("#" + til + "_i_" + i).css("display","block");
					if(i != 1)
						{
							$("#p_id_" + i).val($("#p_id").val());
						}
				}
			else
				{
					$("#" + til + "_t_" + i).addClass("b_dcdcdc_1"); 
					$("#" + til + "_i_" + i).css("display","none");
				}
		}
}

//计算年龄
function getAge(birth)
{
	 // 获得今天的时间
    var date = new Date();
//    var birthday = '${userDetail.birthday}';
//    alert(birthday);
    var startDate = new Date(birth);
    var newDate = date.getTime() - startDate.getTime();
    // 向下取整  例如 10岁 20天 会计算成 10岁
    // 如果要向上取整 计算成11岁，把floor替换成 ceil
    var age = Math.ceil(newDate / 1000 / 60 / 60 / 24 /365);
    if (isNaN(age)){
        age = "";
    }
    return age;
}



//手机号码验证
function checkPhone(str){
	var reg=/^1[34578]\d{9}$/
	if (reg.test(str)){
		return true;
	} else {
		return false;
	}
}

//纯数字验证
function checkNum(str){
	var reg=/^[0-9]{1,20}$/;
	if (reg.test(str)){
		return true;
	} else {
		return false;
	}
}

//获得月日得到日期oTime  
function getMoth(str){  
    var oDate = new Date(str),  
    oMonth = oDate.getMonth()+1,  
    oDay = oDate.getDate(),  
    oTime = getzf(oMonth) +'-'+ getzf(oDay);//最后拼接时间  
    return oTime;  
};  
//获得年月日      得到日期oTime  
function getMyDate(str){  
    var oDate = new Date(str),  
    oYear = oDate.getFullYear(),  
    oMonth = oDate.getMonth()+1,  
    oDay = oDate.getDate(),  
    oHour = oDate.getHours(),  
    oMin = oDate.getMinutes(),  
    oSen = oDate.getSeconds(),  
    oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay) +' '+ getzf(oHour) +':'+ getzf(oMin) +':'+getzf(oSen);//最后拼接时间  
    return oTime;  
};  
//补0操作  
function getzf(num){  
    if(parseInt(num) < 10){  
        num = '0'+num;  
    }  
    return num;  
}  

