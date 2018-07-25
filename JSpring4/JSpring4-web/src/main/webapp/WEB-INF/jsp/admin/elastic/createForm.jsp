 <%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
 
 <script>
 
 function elasticProc(url, params, fnc){
		$.ajax({
			url: url,
			type: 'POST',
			data: params,
			success: function (data) {
				if(fnc != undefined) fnc(data);
			},
			error: function () {
				$("#result").append("<div>오류발생</div>");
			}
		});
 }
 
 function viewIndex(){
	elasticProc("/admin/elastic/indexList",{},function(data){
		$.each(data,function(key){
			$("#result").append("<div class='index'>" + key + "</div>");
		});
	});
 }
 function viewMapping(){
	elasticProc("/admin/elastic/mappingList",{},function(data){
		
		$.each(data,function(key){
			var mapping = data[key].mappings;
			$.each(mapping, function(mkey){
				var type = mapping[mkey].properties;
				$.each(type, function(field){
					$("#result").append( "<div class='type'>" + key +" : "+ mkey +" : "+ field + ":" + JSON.stringify(type[field]) + "</div>");
				})
			})
			
		});
		
	});
 }
 
 $( document ).ready(function() {
	 
	 //------------------------------------------------------------------------------ Index
	 //Index 조회
	 $("#btn11").click(function(){
		$("#result div").remove();
		 viewIndex();
	 });
	 
	 //Index 생성
	 $("#btn12").click(function(){
		if( !$("#index1").val().trim() ) return false;
		
		elasticProc("/admin/elastic/createIndex",{"indexNm" : $("#index1").val()}, function(data){
			$("#result div").remove();
			try{
				var result = JSON.parse(data);
				if(result.acknowledged == true){
					 viewIndex();
				}else{
					$("#result").append("<div>" + data + "</div>");
				}
			}catch(e){
				$("#result").append("<div>" + data + "</div>");
			}
		});
	 });
	 
	 //Index 삭제
	 $("#btn13").click(function(){
		if( !$("#index1").val().trim() ) return false;
		if(confirm("삭제 콜 ?") == false) return false;
		
		elasticProc("/admin/elastic/deleteIndex",{"indexNm" : $("#index1").val()}, function(data){
			$("#result div").remove();
			try{
				var result = JSON.parse(data);
				if(result.acknowledged == true){
					 viewIndex();
				}else{
					$("#result").append("<div>" + data + "</div>");
				}
			}catch(e){
				$("#result").append("<div>" + data + "</div>");
			}
		});
	 });
	 
	 
	 //------------------------------------------------------------------------------ Mapping
	 
	  //Mapping 조회
	 $("#btn21").click(function(){
		$("#result div").remove();
		viewMapping();
	 });
	 
	 //Mapping 생성
	 $("#btn22").click(function(){
		 
		if( !$("#index2").val().trim() || !$("#type2").val().trim() || !$("#properties").val().trim() ) return false;
		
		var params = {
			"indexNm" : $("#index2").val(),
			"typeNm" : $("#type2").val(),
			"propKey" : [],
			"propValue" : []
		}
		
		var properties =  $("#properties").val().split("\n");
		 
		for(var prop in properties){
			var js = JSON.parse("{" +  properties[prop] + "}");
			
			$.each(js,function(key){
				params.propKey.push(key)
				params.propValue.push(JSON.stringify(js[key]))
			});
		} 
		 
		elasticProc("/admin/elastic/createMapping",params, function(data){
			$("#result div").remove();
			try{
				var result = JSON.parse(data);
				if(result.acknowledged == true){
					viewMapping();
				}else{
					$("#result").append("<div>" + data + "</div>");
				}
			}catch(e){
				$("#result").append("<div>" + data + "</div>");
			}
		});
		
	 });
	 
	 //Mapping 삭제
	 $("#btn23").click(function(){
		if( !$("#index2").val().trim() || !$("#type2").val().trim() ) return false;
		if(confirm("삭제 콜 ?") == false) return false;
		
		var params = {
				"indexNm" : $("#index2").val(),
				"typeNm" : $("#type2").val()
			}
		
		elasticProc("/admin/elastic/deleteMapping",params, function(data){
			console.log(data)
			$("#result div").remove();
			try{
				var result = JSON.parse(data);
				if(result.acknowledged == true){
					viewMapping();
				}else{
					$("#result").append("<div>" + data + "</div>");
				}
			}catch(e){
				$("#result").append("<div>" + data + "</div>");
			}
		});
		
		
	 });
	 
	 
 });
 </script>
 

 <div class="x_title">
    <h2>Index/Type</h2>
 	 <div class="clearfix"></div>
</div>
 

<h5 class="page-header"><span class="glyphicon glyphicon-th-large"></span> Index 생성</h5>
 
 <form class="form-inline">
	<div class="form-group">
    	<input type="text" class="form-control" id="index1"placeholder="Index name">
 	</div>
	 <button type="button" class="btn btn-default" id="btn11">조회</button>
	 <button type="button" class="btn btn-default" id="btn12">생성</button>
	 <button type="button" class="btn btn-warning" id="btn13">삭제</button>
 </form>
 
 
 <h5 class="page-header"><span class="glyphicon glyphicon-th-large"></span> Mapping</h5>
<form class="form-inline">
	<div class="form-group">
	    <label>Index</label>
	    <input type="text" class="form-control" id="index2" placeholder="Index name">
	 </div>
	<div class="form-group">
	    <label>Type</label>
	    <input type="text" class="form-control" id="type2" placeholder="Type name">
	</div>
	 <button type="button" class="btn btn-default" id="btn21">조회</button>
	 <button type="button" class="btn btn-default" id="btn22">생성</button>
	 <!-- <button type="button" class="btn btn-warning" id="btn23">삭제</button> -->
</form><br>

<div> 
<label>Properties</label>
<textarea id="properties" class="form-control" style="height:100px;font-size: 9pt">
"seq": { "type": "integer", "index": "not_analyzed" }
"title": { "type": "string", "analyzer": "korean_analyzer"}
"description": { "type": "string", "analyzer": "korean_analyzer"}
"regdate": { "type": "date"}</textarea>
</div>
 	 
<h5 class="page-header"><span class="glyphicon glyphicon-th-large"></span> 결과</h5>
<div id="result" class="form-control" style="font-size: 9pt;height:200px;overflow: auto;"></div>


