$( function() {
	
	//파일찾기
	$('#fileManager .browse').click(function(){
		$("#fileManager .imgFile").trigger('click');
	});
	$('#fileManager .imgFile').change(function(){
		 $('#fileManager .form-control').val($(this).val().replace(/C:\\fakepath\\/i, ''));
	});
	
	//전송
	$('#fileManager .send').click(function(){
		mk.file.send();
	});
	
	//닫기
	$('#fileManager .closeBtn').click(function(){
		$('#fileManager').modal('hide');
	});
	//삭제
	$('#fileManager .deleteBtn').click(function(){
		mk.file.delete();
	});
	
});


var img = {
		
	target : null,
	flag : null,

		
	list : function(obj, flag){
		
		angular.element(document.getElementById('fileManager')).scope().imageList(obj,flag);
		this.target = obj;
		this.flag = flag;
		//$('#fileManager').modal('show');
		
	},
	delete : function(){
		alert('삭제')
	},
	add :function(obj){
		var img = $(obj).closest("tr").find("td .imgBanner");
		
		if( this.flag=="l"){
			
			$(this.target).closest("div").css({
			   	width : img.width(), 
			   	height : img.height(),
			   	"background-image":"url(" + img.attr("src") + ")"
			   	,"position": "relative"
			 });
			$(this.target).closest("tr").find("img").attr("src",img.attr("src"));
			$(this.target).closest("tbody").find(".imgRemoveBtn").show();
			$(this.target).closest("tbody").find(".inputAddBtn").show();
			$(this.target).hide();
		}else{
			$(this.target).closest("div").css({
			   	width : img.width(), 
			   	height : img.height(),
			   	"background-image":"url(" + img.attr("src") + ")"
			 });
			$(this.target).closest("td").find("img").attr("src",img.attr("src"));
		}
		
		$('#fileManager').modal('hide');

	},
	send : function(){
		alert('전송');
	}
	
}

var app = angular.module("appModule", []);
app.config(function ($httpProvider) {
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded; charset=UTF-8';
});

app.controller('ngImgeDiv', function($scope, $http){
	var target  =  null;
	var flag = null;
	$scope.imageList = function(obj, imgTp){
		$('#fileManager').modal('show');
		target  =  obj;
		flag = imgTp;
		$http({
			method: 'POST',
	        url: "imageList",
	        data: $.param({ imgTp: imgTp })
        })
        .then(function successCallback(response) {
        	console.log(response);
        	$scope.list = response.data;
        	$('#fileManager').modal('show');
		 }, function errorCallback(response) {
			 alert("실패");
		 }); 
        
        
	 }
});



