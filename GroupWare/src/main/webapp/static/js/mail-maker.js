$( function() {
	
	$( "#sortable" ).sortable({
		placeholder: "ui-state-highlight"
	}); 
	
	//캘린더
	mk.calendar("footDate");
	$("#sortable #footDate").click(function(){
		$(this).blur();
	});
	
	//--------------------------------------- form 생성 버튼
	//GNB
	$(".nav-gnb").click(function(){													
		$("#sortable").prepend($("#formSource .layout-gnb").clone()) ;
	});
	
	//타이틀
	$(".nav-title").click(function(){													
		$("#sortable").prepend($("#formSource .layout-title").clone()) ;
	});
	
	//딜 그룹
	$(".nav-deal-group").click(function(){											
		var deal = $("#formSource .layout-deal-group").clone();
		deal.find(".list-group-item").remove();
		$("#sortable").prepend(deal) ;
	});
	
	//이미지 1단
	$(".nav-image1").click(function(){												
		$("#sortable").prepend($("#formSource .layout-image1").clone()) ;
	});
	
	//이미지 2단
	$(".nav-image2").click(function(){												
		$("#sortable").prepend($("#formSource .layout-image2").clone()) ;
	});
	
	//이미지 맵
	$(".nav-image-map").click(function(){											
		var mapId = 2;
		$("#sortable").prepend($("#formSource .layout-image-map").clone()) ;
		var mapLength = $("#sortable .layout-image-map").length;
		$("#sortable .layout-image-map:eq(0)").find('input[name=mapId]').val(mapLength*mapId);
		mk.imageMapAreaDrag();
	});
	
	//footer
	$(".nav-footer").click(function(){		
		if( $("#sortable .layout-footer").length > 0 ) return;
		$("#sortable").prepend($("#formSource .layout-footer").clone()) ;
		mk.calendar("footDate");
	});
	
	//간격
	$(".nav-separation").click(function(){											
		$("#sortable").prepend($("#formSource .layout-separation").clone()) ;
	});
	
	//미리보기
	$(".nav-preview").click(function(){											
		mk.preView();
	});
	
	//저장
	$(".nav-save").click(function(){											
		mk.save();
	});
	
	//이미지맵 load시  div.image_map_area 생성
	$("#sortable .image_map_input").each(function(){		
		var coords = $(this).data("coords").split(",");
		mk.addBtnMap($(this), $(this).data("coords"));
	});
	
	
	//이미지맵 폼 백그라운드 삽입
	$('#sortable .imgMapSrc').each(function() {
		 $(this).closest("td").find(".imgMap").css({
	    	width : $(this).width(), 
	    	height : $(this).height(),
	    	"background-image":"url(" + $(this).attr("src") + ")"
	    	,"background-repeat": "no-repeat"
	    	,"position": "relative"
		 });
	 }) ;
	
	//이미지(1단, 2단)  폼 백그라운드 삽입
	 $('#sortable .imgBanner').each(function() {
		 $(this).closest("td").find("div").css({
	    	width : $(this).width(), 
	    	height : $(this).height(),
	    	"background-image":"url(" + $(this).attr("src") + ")"
		 });
	 }) ;
	
});

//이미지맵  div영역 마우스 클릭 하이라이트
$(document).on("click", ".image_map_area", function(e){
	var imageMapArea = $(this).parent().find(".image_map_area");
	var selectIndex = imageMapArea.index(this);
	$(this).closest("tbody").find(".image_map_input").each(function() {
		if( $(this).index()-2 == selectIndex){
			$(this).css({ 'background-color' : '#ddd' });
      }else{
         $(this).css({  'background-color' : '#fff'  });
      }
   });
   imageMapArea.each(function() {
      if( $(this).index()-2 == selectIndex){
         $(this).css({ 'background-color' : '#790000',  'z-index' : 1 });
      }else{
         $(this).css({ 'background-color' : '#fff',  'z-index' : 0 });
      }
   });
});
//이미지맵 input영역 마우스 클릭 하이라이트
$(document).on("click", ".image_map_input", function(e){
  
   var imageMapInput = $(this).parent().find(".image_map_input");
   var selectIndex = imageMapInput.index(this);
   
	imageMapInput.each(function() {
		if( $(this).index()-2== selectIndex){ $(this).css({ 'background-color' :  '#ddd' });
		}else{
			$(this).css({ 'background-color' : '#fff' });
		}
	});
	$(this).closest("tbody").find(".image_map_area").each(function() {
		if( $(this).index()-2 == selectIndex){
			$(this).css({ 'background-color' : '#790000', 'z-index' : 1 });
		}else{
			$(this).css({ 'background-color' : '#fff',  'z-index' : 0 });
		}
	});
   // return;
});


var mk = {
	
	//딜 그룹 생성
	createDealGroupForm : function(obj, type){
		$("#formSource .layout-deal-group ." + type).clone().appendTo($(obj).closest(".layout-deal-group").find(".list-group"))
	},
	
	//이미지맵 추가
	 addBtnMap : function(obj,coord){
		var coords = coord.split(",");
		var default_s_x = 0;
		var default_s_y = 0;
		var default_e_x = 90;
		var default_e_y = 90;
		
		// 영역추가
		var left = default_s_x;
		var top = default_s_y;
		var width = default_e_x;
		var height = default_e_y;
		
		//Data load시  area 위치 지정 및 버튼
		if(coords[1]){
			left = coords[0];
			top = coords[1];
			width = coords[2]-coords[0];
		    height = coords[3]-coords[1];
		    
		    console.log($(obj).closest("tbody").find(".inputAddBtn"));
		    
		    $(obj).closest("tbody").find(".inputAddBtn").show();
		    $(obj).closest("tbody").find(".imgRemoveBtn").show();
		    $(obj).closest("tbody").find(".imgAddBtn").hide();
		}else{
			//load 된 데이터가 없으면 input과 area 생성
			$(obj).closest("tbody").append($("#formSource ").find(".empty_input").clone());
			//empty_input 비어있는 tr 
			$(obj).closest("tbody").find(".empty_input:eq(0)").show().addClass("image_map_input").removeClass("empty_input");		
		}
		var addLayerArea = "";
		addLayerArea += "<div  style='top:"+top+"px;left:"+left+"px;width:"+width+"px;height:"+height+"px;border:1px solid #790000; background-color:#fff;filter:alpha(opacity=70); opacity:0.7; -moz-opacity:0.7;position:absolute;' class='image_map_area'><p></p></div>";
		
		$(obj).closest("tbody").find('.image_map_content > div').append(addLayerArea);
		
		mk.imageMapAreaDrag();
	},
	//이미지맵 제거
	 removeBtnMap : function(obj){

		var selectIndex = $(obj).closest("tbody").find( ".removeBtnMap " ).index(obj);
		var area =$(obj).closest("tbody").find(".image_map_area").length;
		 
		if(area > 1){
			$(obj).closest("tbody").find(".image_map_area").eq(selectIndex).remove();
			$(obj).closest("tr").remove();
		 }
	},
	imageMapAreaDrag : function() {
		$(".image_map_area" ).resizable({containment: "parent"});		//이거 없으면 두번째 clone은 resizable이 안 됨
		$(".image_map_area" )
		.draggable({ 
		    containment: "parent", 
		    cursor: "crosshair",
		    scroll: false,
		    stop :function(event,ui){
				var selectIndex = $(this).parent().find(".image_map_area").index(this);
		    	var selectInput = $(this).closest("tbody").find(".image_map_input:eq("+selectIndex+")");
		    	
		    	var sx = parseInt($(this).css('left'),10);
		        var sy = parseInt($(this).css('top'),10);
		        var ex = sx+ parseInt($(this).css('width'),10);
		        var ey = sy + parseInt($(this).css('height'),10);
		        var coords = sx+','+sy+','+ex+','+ey;
		        selectInput.data("coords" ,null);
		        selectInput.data("coords" ,coords);
		        
			      //$(this).find("p").text(ui.position.left  +":"+ ui.position.top +":"+ ($(this).width()+ui.position.left)+":"+ ($(this).height()+ui.position.top))
			    }
		})
		.resizable({
			containment : "parent",
		    stop :function(event,ui){
				var selectIndex = $(this).parent().find(".image_map_area").index(this);
		    	var selectInput = $(this).closest("tbody").find(".image_map_input:eq("+selectIndex+")");
		    	
		    	var sx = parseInt($(this).css('left'),10);
		        var sy = parseInt($(this).css('top'),10);
		        var ex = sx+ parseInt($(this).css('width'),10);
		        var ey = sy + parseInt($(this).css('height'),10);
		        var coords = sx+','+sy+','+ex+','+ey;
		        selectInput.data("coords" ,null);
		        selectInput.data("coords" ,coords);
		        
		      //(this).find("p").text(ui.position.left  +":"+ ui.position.top +":"+ ($(this).width()+ui.position.left)+":"+ ($(this).height()+ui.position.top))
		    },
		    handles: "all",
		   
		});
	},
	
	//GNB 문구 삽입
	 makeTitle : function(obj){
		 $(obj).closest("tbody").find("div > span").text( $(obj).val() );
	},
	
	//타이들 문구 삽입
	 makeTitle : function(obj){
		 $(obj).closest("tbody").find("div > span").text( $(obj).val() );
	},
	//달력
	calendar : function(id){
		 $('#' + id).datepicker({
		    format: "yyyy-mm-dd",
		    todayBtn: "linked",
		    language: "kr",
		    keyboardNavigation: false,
		    forceParse: false,
		    todayHighlight: true,
		    autoclose : true
		});
		
	},
	
	
	
	
	//미리보기
	preView : function(){
			
		 $.ajax({
			url: 'preView',
			type: 'POST',
			data : {jsonData :  mk.getJsonData() },
			success: function (html) {
				console.log(html);
				var w = window.open();
				w.document.write("<!DOCTYPE html>");
			    w.document.write("<html><body>" +html+ "<body></html>");
			    w.document.close();
				
			},
			error: function (xhr) {
				alert('에러 발생');
			}
		});
	},
	
	//저장
	save : function(){
		console.log( this.getJsonData() );
	},
	
	//폼 데이터 추출
	getJsonData : function(){
		
		var mailData = {dataList : []};
		
		 //------------------------------------ 메일 폼 JOSON 데이터 생성
		$("#sortable li").each(function(){
			
			//GNB JSON 데이터 생성
			if($(this).hasClass("layout-gnb")){
				mailData.dataList.push(mk.createGnbJson($(this)));
				
			//타이틀 JSON 데이터 생성
			}else if($(this).hasClass("layout-title")){
				mailData.dataList.push(mk.createTitleJson($(this)));
					
			//딜 그룹 JSON 데이터 생성
			}else if($(this).hasClass("layout-deal-group")){
				mailData.dataList.push(mk.createDealGroupJson($(this)));
			
			//이미지 1단 JSON 데이터 생성
			}else if($(this).hasClass("layout-image1")){
				mailData.dataList.push(mk.createImage1Json($(this)));	
			
			//이미지 2단 JSON 데이터 생성
			}else if($(this).hasClass("layout-image2")){
				mailData.dataList.push(mk.createImage2Json($(this)));		
				
			//간격 JSON 데이터 생성
			}else if($(this).hasClass("layout-separation")){
				mailData.dataList.push(mk.createSeparationJson($(this)));
				
			//이미지맵 JSON 데이터 생성
			}else if($(this).hasClass("layout-image-map")){
				mailData.dataList.push(mk.createImageMapJson($(this)));
			
			//footer JSON 데이터 생성
			}else if($(this).hasClass("layout-footer")){
				mailData.dataList.push(mk.createFooterJson($(this)));
			}
			
		});
		
		return JSON.stringify(mailData);
	},
	//GNB JSON 데이터 생성
	createGnbJson : function(obj){
		var name = {};
		var data = {};
		
		if( obj.find('input[name=textUseYn]').is(":checked") ){
			data["gnbText"] =  obj.find('input[name=gnbText]').val();
			data["textUseYn"] = "Y";
		}
		
		name["gnb"] = data;
		
		console.log(name);
		return name;
		
	},
	//타이틀 JSON 데이터 생성
	createTitleJson : function(obj){
		var name = {};
		var data = {};
		data["titleText"] =  obj.find('input[name=titleText]').val(); 
		name["title"] = data;
		
		console.log(name);
		return name;
	},
	//딜 그룹 JSON 데이터 생성
	createDealGroupJson : function(obj){
		var name = {};
		var groups = [];
		
		obj.find(".list-group-item").each(function(){
			
			if($(this).hasClass("deal1")) {			//1단
				groups.push( {type : "deal1", data : mk.createDealDataJson($(this), 1,"deal1")} );
			}else if($(this).hasClass("deal2")){ 	//2단
				groups.push( {type : "deal2", data : mk.createDealDataJson($(this), 2,"deal2")} );
			}else if($(this).hasClass("deal3")){ 	//3단
				groups.push( {type : "deal3", data : mk.createDealDataJson($(this), 3,"deal3")} );
			}else if($(this).hasClass("deal4")){ 	//4단
				groups.push( {type : "deal4", data : mk.createDealDataJson($(this), 4,"deal4")} );
			}else if($(this).hasClass("deal5")){ 	//5단
				groups.push( {type : "deal5", data : mk.createDealDataJson($(this), 5,"deal5")} );
			}else if($(this).hasClass("bob")){ 	//bob
				groups.push( {type : "bob", data : mk.createDealDataJson($(this), 1,"bob")} );
			}
		}); 
		
		name["dealGroup"] = groups;

		console.log(name);
		return name;
	},
	//딜 그룹 유형별 JSON 데이터 생성
	createDealDataJson : function(obj, n, type){
		var data = [];
		for(i = 0; i < n; i++){
			var deal = {};
			deal["srl"] =  obj.find('input[name=srl]').eq(i).val(); 
			deal["jp"] =  obj.find('input[name=jp]').eq(i).val(); 
			deal["ln"] =  obj.find('input[name=ln]').eq(i).val(); 
			deal["title"] =  obj.find('input[name=title]').eq(i).val(); 
			deal["content"] =  obj.find('input[name=content]').eq(i).val(); 
			deal["best"] =  obj.find('select[name=best]').eq(i).val(); 
			deal["price"] =  obj.find('select[name=price]').eq(i).val();
			
			data.push(deal);
		}
		return data;
	},
	//이미지 1단 JSON 데이터 생성
	createImage1Json : function(obj){
		var name = {};
		var data = {};
		
		data["url1"] =  obj.find("img").attr("src"); 
		name["image1"] = data;

		console.log(name);
		return name;
	},
	//이미지 2단 JSON 데이터 생성
	createImage2Json : function(obj){
		var name = {};
		var data = {};
		data["url1"] =  obj.find("img").eq(0).attr("src"); 
		data["url2"] =  obj.find("img").eq(1).attr("src"); 
		name["image2"] = data;
		
		console.log(name);
		return name;
	},
	//간격 JSON 데이터 생성
	createSeparationJson : function(obj){
		var name = {};
		var data = {};
		data["height"] = obj.find('input[name=height]').val(); 
		name["separation"] = data;
		
		console.log(name);
		return name;
	},
	//이미지맵 JSON 데이터 생성
	createImageMapJson : function(obj){
		var name = {};
		var imgMap = [];
		obj.find("table").each(function(i){
			imgMap.push({ imageUrl :   obj.find("img").attr("src"), alt : obj.find('input[name=alt]').val(), mapId : obj.find('input[name=mapId]').val(), data: mk.createImageMapDataJson( obj ) });
		}); 
		name["imageMap"] = imgMap;
		console.log(name);
		return name;
	},//이미지맵 input JSON 데이터 생성
	createImageMapDataJson : function(obj){
		var data = [];
		obj.find(".image_map_input").each(function(i){	
			var input = {};
			input["coords"] = $(this).data("coords");
			input["jp"] =  $(this).find('input[name=jp]').val(); 
			input["ln"] =  $(this).find('input[name=ln]').val(); 
			input["url"] =  $(this).find('input[name=url]').val(); 
			input["urlType"] =  $(this).find('select[name=urlType]').val(); 
			data.push(input);
		}); 
		return data;
	},
	//footer JSON 데이터 생성
	createFooterJson : function(obj){
		var name = {};
		var data = {};
		data["footDate"] = obj.find('input[name=footDate]').val(); 
		name["footer"] = data;
		
		console.log(name);
		return name;
	}

}




