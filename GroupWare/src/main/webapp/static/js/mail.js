$(document).ready(function(){
	
    $('#inboxDragbar').mousedown(function(e){
        e.preventDefault();
	        $(document).mousemove(function(e){
	        $('#inboxSubject').css("width",e.pageX - $(".side-menu").width() - 43 );
	
	
	        $('#inboxContent').css("left",e.pageX);
	        $('#inboxContent').css("width", $('body').width() - e.pageX - 35);
	
	        //console.log(  $('#inboxSubject').width()  +":"+ $('#inboxContent').width() )

        });
    });

	$(document).mouseup(function(e){
	    $(document).unbind('mousemove');
	})

	
	 //height fix  모바일 아닐때
	if($('#inboxContent').is(':visible')){
		$("#inbox_list_subject").css('height' , $("#inboxDragbar").height() - 60);              
		$("#inboxContent").css('height' , $("#inboxDragbar").height() - 60);
	}
	
	 //content width fix
	$('#inboxContent').css("width", $('body').width() - $(".side-menu").width() - $('#inboxSubject').width() - 80);     
	
	$(window).resize(function(){
		if($('#inboxContent').is(':visible')){
		    $("#inbox_list_subject").css('height' , $("#inboxDragbar").height() - 60);
		    $("#inboxContent").css('height' , $("#inboxDragbar").height() - 60); 
		}
	    $('#inboxContent').css("width", $('body').width() - $(".side-menu").width() - $('#inboxSubject').width() - 80);     
	}) ;  
	
})



    
var app = angular.module("appModule", []);
app.config(function ($httpProvider) {
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded; charset=UTF-8';
});

app.controller('ngMailSubject', function($scope, $http){
	
	/**
	 * 메일리스트
	 */
	 $scope.mailList = function(inbox, page) {
	   $http({
            method: 'POST',
            url: "/mail/" + inbox + "/" + page,
            headers: { "AJAX": "true"}
        })
        .then(function successCallback(response) { 
        	
        	var total = response.data.total;
        	var totalPage = response.data.totalPage;
        	var page = response.data.page;
        	
          	$("#total").text(total);
		 	$("#page").text( page );
		 	$("#totalPage").text( totalPage );
      
		 	$scope.total = total;
		 	$scope.list = response.data.inboxList;
		 	$scope.page(totalPage, page);
		 	
		 	$scope.box = response.data.box;
		 }, function errorCallback(response) {
			 if(response.status == 403 || response.status == 401){
				document.location.href = "/login";
			}
		 }); 
    }
	 
	 /**
	 * 메일조회
	 */
	 $scope.mailView = function(inbox, messageId) {
		 $http({
			 method: 'POST',
	         url: "/mail/" + inbox + "/view/" + messageId,
	         headers: { "AJAX": "true"}
	      })
		 .then(function successCallback(response) {
        	$scope.view = response.data;
        	$("#inbox_content").html($scope.view.content.contentString);
		 }, function errorCallback(response) {
			if(response.status == 403 || response.status == 401){
				document.location.href = "/login";
			}
		 });
	 }
	 
	 /**
	 * 파일사이즈
	 */
	$scope.fileSize = function(bytes) {
		var sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
		if (bytes == 0) return '0 Byte';
		var i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)));
		return Math.round(bytes / Math.pow(1024, i), 2) + ' ' + sizes[i];
	}
	
	/**
	 * 파일다운로드
	 */
	$scope.fileDownload = function(box, messageId, index) {
		document.location.href = "/mail/attachment/"+ box + "/" + messageId + "/" + index;
	}
	
	 /**
	  * 메일선택
	  */
	 $scope.checkEmail = function(obj) {
		 
		 var obj = $(obj.target);
		 var uid = $(obj).parent().parent().data('uid');

		 if( obj.hasClass("unchecked")  ) {
			 obj.removeClass("unchecked").addClass("checked");
		 }else{
			 obj.removeClass("checked").addClass("unchecked");
		 }
		 
	 }
	
	 /**
	  * 중요상태 변경
	  */
	 $scope.updateStarStatus = function(obj,inbox) {
		 
		 var obj = $(obj.target);
		 var uid = $(obj).parent().parent().data('uid');
		 var flag = "";

		 if( obj.hasClass("stared")  ) {
			 flag = "UNFLAGGED"
			 obj.removeClass("fa-star stared").addClass("fa-star-o");
		 }else{
			 flag = "FLAGGED";
			 obj.removeClass("fa-star-o").addClass("fa-star stared");
		 }
		 
		 var data = {
			inbox : inbox,
			uuids : uid,
			flag : flag
		 }
		 
		 $http({
			 method: 'POST',
	         url: "/mail/updateMailStatus",
	         data: $.param(data)
	      })
		 .then(function successCallback(response) {
		 }, function errorCallback(response) {
		 });
	 }
	 
	 /**
	 * 메일삭제
	 */
	 $scope.mailDelete = function(inbox, messageId) {
		 $http({
			 method: 'POST',
	         url: "/mail/" + inbox + "/view/" + messageId,
	         headers: { "AJAX": "true"}
	      })
		 .then(function successCallback(response) {
        	$scope.view = response.data;
        	$("#inbox_content").html($scope.view.content.contentString);
		 }, function errorCallback(response) {
			if(response.status == 403 || response.status == 401){
				document.location.href = "/login";
			}
		 });
	 }

	 /**
	  * 메일이동
	  */
	 $scope.mailMove = function(inbox, messageId) {
		 $http({
			 method: 'POST',
	         url: "/mail/" + inbox + "/view/" + messageId,
	         headers: { "AJAX": "true"}
	      })
		 .then(function successCallback(response) {
        	$scope.view = response.data;
        	$("#inbox_content").html($scope.view.content.contentString);
		 }, function errorCallback(response) {
			if(response.status == 403 || response.status == 401){
				document.location.href = "/login";
			}
		 });
	 }
	 
	 /**
	  * 페이징
	  */
	 $scope.page = function(totalPage, page){
 		$("#angle-right").attr( "disabled", (totalPage == page) ? true : false );
 		$("#angle-left").attr( "disabled", (1 == page) ? true : false);
	 	$scope.nextPage = totalPage == page ?  totalPage : page+1;
	 	$scope.prePage = 1 == page ?  1 : page-1;
	 };
	 
	 
	 
	 $scope.mailList($("#inbox").val(),"1");
});








