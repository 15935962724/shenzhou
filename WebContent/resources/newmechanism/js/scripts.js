(function() {
    "use strict";

    // custom scrollbar

    //$("html").niceScroll({styler:"fb",cursorcolor:"#b1c7df", cursorwidth: '10', cursorborderradius: '10px', background: '#424f63', spacebarenabled:false, cursorborder: '0',  zindex: '1000' ,top:'0px'});

    $(".left-side").niceScroll({styler:"fb",cursorcolor:"#b1c7df", cursorwidth: '10', cursorborderradius: '10px', background: '#424f63', spacebarenabled:false, cursorborder: '0', top:'0px'});


    $(".left-side").getNiceScroll();
    if ($('body').hasClass('left-side-collapsed')) {
        $(".left-side").getNiceScroll().hide();
    }




   function mainContentHeightAdjust() {
      // Adjust main content height
      var docHeight = jQuery(document).height();
      if(docHeight > jQuery('.main-content').height())
         jQuery('.main-content').height(docHeight);
   }

   //  class add mouse hover
   jQuery('.custom-nav > li').hover(function(){
      jQuery(this).addClass('nav-hover');
   }, function(){
      jQuery(this).removeClass('nav-hover');
   });

   //  class add mouse click
   jQuery('.custom-nav > li').click(function(){
	   
	   $(this).find("td").eq(0).removeClass("z14717171");
	   $(this).find("td").eq(0).addClass("z14ffffff");
	   $(this).find("td").eq(0).addClass("b2292f7");
	   
	   var trs=$(this).find("tr").eq(0).html();
	   var tds=$(this).find("td").eq(0).html();
	   var url=tds.substring(tds.indexOf('href=\"')+6,tds.indexOf('\">'));
	   $.cookie('iframe_nav', trs, {path:'/'});
	   $('#main_ifr').attr('src',url);
	   for(var j=0;j<jQuery('.custom-nav > li').length;j++)
		   {
			   if($('.custom-nav > li').eq(j).attr("class").indexOf("active")>0)
				   {
					   $('.custom-nav > li').eq(j).removeClass("active");
				   }
		   }
      jQuery(this).addClass('active');
   });

	
   // Menu Toggle
   jQuery('.toggle-btn').click(function(){
       $(".left-side").getNiceScroll().hide();
       
       if ($('body').hasClass('left-side-collapsed')) {
           $(".left-side").getNiceScroll().hide();
       }
      var body = jQuery('body');
      var bodyposition = body.css('position');

      if(bodyposition != 'relative') {

         if(!body.hasClass('left-side-collapsed')) {
            body.addClass('left-side-collapsed');
            jQuery('.custom-nav ul').attr('style','');

            jQuery(this).addClass('menu-collapsed');

         } else {
            body.removeClass('left-side-collapsed chat-view');
            jQuery('.custom-nav li.active ul').css({display: 'block'});

            jQuery(this).removeClass('menu-collapsed');

         }
      } else {

         if(body.hasClass('left-side-show'))
            body.removeClass('left-side-show');
         else
            body.addClass('left-side-show');

         mainContentHeightAdjust();
      }

   });
   

//   searchform_reposition();

   jQuery(window).resize(function(){

      if(jQuery('body').css('position') == 'relative') {

         jQuery('body').removeClass('left-side-collapsed');

      } else {

         jQuery('body').css({left: '', marginRight: ''});
      }

//      searchform_reposition();

   });

//   function searchform_reposition() {
//      if(jQuery('.searchform').css('position') == 'relative') {
//         jQuery('.searchform').insertBefore('.left-side-inner .logged-user');
//      } else {
//         jQuery('.searchform').insertBefore('.menu-right');
//      }
//   }
	

})(jQuery);

                      // Dropdowns Script
						$(document).ready(function() {
						  $(document).on('click', function(ev) {
						    ev.stopImmediatePropagation();
						    $(".dropdown-toggle").dropdown("active");
						  });
						});
						
