<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<section class="content-header">
  <h1>
    사이트 정보
    <small>목록</small>  
  </h1>
  <ol class="breadcrumb">
    <li><a href="#"> 배치관리</a></li>
    <li class="active">사이트관리</li>
  </ol>
</section>


<section class="content container-fluid">

<div class="box box-default">

    <div class="box-body" id="dataWrap">
    
    	<div class="form-group-sm pull-left">
     	<form name="frm" id="frm" action="" method="POST" class="form-inline">
		  <div class="form-group-sm">
			
			<div class="input-group input-group-sm" style="width: 200px;">
	             <input type="text" name="searchValue" id="searchValue" value="${board.searchValue }" class="form-control pull-right" placeholder="Search">
	             <div class="input-group-btn">
	               <button type="button" onClick="search()" class="btn btn-default"><i class="fa fa-search"></i></button>
	             </div>
           	</div>
			
		  </div>
		  
		  <input type="hidden" name="page" id="page" value="${board.page }">
		  <input type="hidden" name="boardSrl" id="boardSrl" value="">
		</form>
	  	</div>
		<div class="pull-right">Total : ${board.totalRow} [ ${board.page} / ${board.totalPage} ]</div> 
    
	    <table class="table table-hover table-top">
		  <colgroup>
		    <col style="width:60px">
		     <col style="width:250px">
		     <col />
		     <col style="width:80px">
		  </colgroup>
		<thead>
		<tr>
			<th>번호</th>
			<th>사이트 명</th>
			<th>사이트 URL</th>
			<th>등록일</th>
		</tr>
		</thead>
		<tbody>
		
		<tr v-for="lst in objData.list">
			<td></td>
			<td>
				<a href="#">{{ lst.siteNm }}</a>
			</td>
			<td>{{ lst.siteNm }}</td>
			<td>{{   }}</td>
		</tr>
		
		</tbody>
		</table> 
		
	</div>
		
	<div class="box-footer">
		<button type="button" class="btn btn-primary btn-xm" onClick="save()">등록</button>
	</div>
		
	
</div>	

</section>

<script>

/**
 
beforeCreate 	Called synchronously after the instance has just been initialized, before data observation and event/watcher setup.
created 			Called synchronously after the instance is created. At this stage, the instance has finished processing the options which means the following have been set up: 
					data observation, computed properties, methods, watch/event callbacks. However, the mounting phase has not been started, and the $el property will not be available yet.
beforeMount 	Called right before the mounting begins: the render function is about to be called for the first time.
mounted 		Called after the instance has just been mounted where el is replaced by the newly created vm.$el.
beforeUpdate 	Called when the data changes, before the virtual DOM is re-rendered and patched.
updated 		Called after a data change causes the virtual DOM to be re-rendered and patched.
 
 */
 


var vObj = null;
$(document).ready(function() {
	
	vObj = new Vue({
		el: '#dataWrap',
		data : {
			objData : {}
		},
		methods : {
			setObjData : function() {
				this.objData = getObjData();
				console.log(this.objData.list)
			}
		},
		created: function () {
			this.setObjData()
		}
	});
	
});

// 목록 데이터
function getObjData(){	
	
	var obj = {};
	
	 $.ajax({      
	    	type : "POST",  
	    	async : false, 
	        url : "/adm/batch/siteListJson",
	        data : {},
	        beforeSend : function(xhr){
				xhr.setRequestHeader("AJAX", "true");
		    },
	        success : function(response){   
	        	console.log( response )
	        	obj = response;
	        	console.log( obj )
	        },   
	        error : function(xhr) {
	        	if(xhr.status == "403"){
	        		document.location.href = "/adm/login";
	        	}else{
	    	        alert("에러 : " + xhr.status);
		       	}
	        }
	    });  
	
	 return  obj;
	 
}
 	



</script>