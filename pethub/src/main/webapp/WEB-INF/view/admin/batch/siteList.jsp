<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<style>
</style>

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
	             <input type="text" name="searchValue" id="searchValue" value="" class="form-control pull-right" placeholder="Search">
	             <div class="input-group-btn">
	               <button type="button" onClick="search()" class="btn btn-default"><i class="fa fa-search"></i></button>
	             </div>
           	</div>
			
		  </div>
		  
		</form>
	  	</div>
	  	
		<div class="pull-right" v-cloak>Total : {{ vData.dataInfo.totalRow | addComma }} [ {{ vData.dataInfo.page }} / {{ vData.dataInfo.totalPage }} ]</div> 
    
	    <table class="table table-hover table-top">
		  <colgroup>
		    <col style="width:80px">
		     <col style="width:250px">
		     <col />
		     <col style="width:80px">
		  </colgroup>
		<thead>
		<tr>
			<th class="text-center">번호</th>
			<th>사이트 명</th>
			<th>사이트 URL</th>
			<th class="text-center">등록일</th>
		</tr>
		</thead>
		<tbody>
		
		<tr v-for="lst in vData.dataList" v-cloak>
			<td class="text-center">{{ lst.num | addComma }}</td>
			<td>{{ lst.siteNm }}</td>
			<td><a v-bind:href="lst.siteUrl" target="_blank">{{ lst.siteUrl }}</a></td>
			<td class="text-center">{{ lst.regDt | timestampToDate }}</td>
		</tr>
		
		</tbody>
		</table> 
		
	</div>
	
	<div id="paging"></div>
	
	<div class="box-footer">
		<button type="button" onclick="openRegForm()" class="btn btn-primary btn-xm">등록</button>
	</div>
	
</div>	

</section>

<script>

var vObj = null;		//Vue 객제
var rowSize = 15;	//페이지당 보여줄 로우 수

var regPopup;			//등록팝엽

$(document).ready(function() {

	//Vue 초기화
	vObj = com.initVue("#dataWrap");
	
	//동록 폼 초기화
	regPopup = com.initPopup({
		title : '사이트 등록',
		width : 600,
		height : 220
	});
	
	//수정 폼 초기화
	uptPopup = com.initPopup({
		title : '사이트 수정',
		width : 600,
		height : 220
	});
	
});


// Ajax 데이터 추출,  Vue  함수명 고정 getVdata
function getVdata(params){	
	
 	var obj = com.requestAjax({
		type: "POST",
		async : false, 
		url : "/adm/batch/siteListJson",
		params : params,
	});
	
	console.log(obj)
	
	//페이징 표시
	if( vObj == null && obj.dataInfo.totalRow > 0 ){
		com.initPaging({
			selector : "#paging",
			items : obj.dataInfo.totalRow,
			itemsOnPage : rowSize
		});
	}
	return obj;
}
 	

//페이지 이동, 함수명 고정
function goPage(pageNumber){
	
	var params = {
		page : pageNumber,
		rowSize : rowSize
	}
	
	//데이터 업데이트
	vObj.setVdata(params);
	
	//로우 개수 변화, 페이징 업데이트
	com.updatePageItems("#paging", vObj.dataInfo.totalRow)
}


//등록폼 호출
function openRegForm(){
	
	var obj = com.requestAjax({
		type: "POST",
		async : false,
		url : "/adm/batch/siteForm",
	});

	regPopup.setContent( obj);
	regPopup.open();
}

//수정폼 호출
function openUptForm(){
	
}



</script>