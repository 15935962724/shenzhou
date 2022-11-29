// JavaScript Document
$(function(){	
	$(window).scroll(function()
		{  
			var scroll_top = $(window).scrollTop();
			var hidden_height = $(document.body).height()-$(window).height()-100;
			
			if(scroll_top > 0)
				{
					$("#header").css("border-bottom" , "1px solid #d8d8d8"); 
				}
			else
				{
					$("#header").css("border-bottom" , "none"); 
				}
		
			if(scroll_top >= hidden_height)
				{ 
					$("#header").fadeOut();  
				}
			else
				{  
					$("#header").fadeIn();  
				}  
		});
})

//切换标签
function Toggle_tag(til,id,count)
{
	for(var i = 0; i < count; i++)
		{
			$("#" + til + "_t_" + i).removeClass("z_18_4b99f6_1");
			$("#" + til + "_t_" + i).removeClass("z_18_1f1f1f_1");
			if(id == i)
				{
					$("#" + til + "_t_" + i).addClass("z_18_4b99f6_1"); 
					$("#" + til + "_i_" + i).css("display","block");
				}
			else
				{
					$("#" + til + "_t_" + i).addClass("z_18_1f1f1f_1"); 
					$("#" + til + "_i_" + i).css("display","none");
				}
		}
}

//切换样式
function Toggle_css(id,sty_1,sty_2)
{
	$("#" + id).removeClass(sty_1);
	$("#" + id).addClass(sty_2); 
}

function Toggle_div(d_id,p_id,id)
{
	$("#" + p_id).val(id);
	$("#" + d_id).toggle();	
}