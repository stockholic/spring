$(function () {
	
	//모바일용 슬라이드 표시
    $('.navbar-toggle').click(function () {
        $('.navbar-nav').toggleClass('slide-in');
        $('.side-body').toggleClass('body-slide-in');
        
    });
   
   //열린 메뉴 닫기
   $(".panel > a").click(function(){
	   var clickId = $(this).parent().find(".panel-collapse").attr("id");
	   
	   $(".panel-collapse").each(function(){
		   if( clickId != $(this).attr("id") ){
			   $(this).removeClass("in");
		   }
	   });
   });
   
});