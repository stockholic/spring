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
		  <div class="form-group-sm">
			
			<div class="input-group input-group-sm" style="width: 200px;">
	             <input type="text" name="searchString" id="searchString" value="" class="form-control pull-right" placeholder="Search">
	             <div class="input-group-btn">
	               <button type="button" onClick="search()" class="btn btn-default"><i class="fa fa-search"></i></button>
	             </div>
           	</div>
			
		  </div>
	  	</div>
	  	
		<div class="pull-right" v-cloak>Total : {{ vData.dataInfo.totalRow | addComma }} [ {{ vData.dataInfo.page }} / {{ vData.dataInfo.totalPage }} ]</div> 
    
	    <table class="table table-hover table-top">
		  <colgroup>
		    <col style="width:80px">
		     <col style="width:250px">
		     <col style="width:250px">
		     <col />
		     <col style="width:80px">
		  </colgroup>
		<thead>
		<tr>
			<th class="text-center">번호</th>
			<th>사이트 명</th>
			<th>사이트 URL</th>
			<th>기타</th>
			<th class="text-center">등록일</th>
		</tr>
		</thead>
		<tbody>
		
		<tr v-for="lst in vData.dataList" v-cloak>
			<td class="text-center">{{ lst.num | addComma }}</td>
			<td><a href="javascript:;" v-on:click="openUptForm(lst.siteSrl)">{{ lst.siteNm }}</a></td>
			<td><a v-bind:href="lst.siteUrl" target="_blank">{{ lst.siteUrl }}</a></td>
			<td>{{ lst.siteEtc }}</a></td>
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
var paging = null;	//페이징 객체
var rowSize = 15;	//페이지당 보여줄 로우 수
var page = 1;			//현재페이지

$(document).ready(function() {

	//Vue 초기화
	vObj = com.initVue("#dataWrap");
	
	//폼 초기화
	com.initPopup({
		title : '사이트 등록',
		width : 600,
		height : 250
	});
	
	//확인 창 초기화
	com.initConfirm();
	
	//검색
	$("#searchString").keyup(function(event) {
		if( event.keyCode == 13){
			search();
    	}
	});
	
});


// Ajax 데이터 추출,  Vue 에 정의된 함수명, setVdata 에서 호출, 고정 
function getVdata(params){	
	
	var obj = {};
 	com.requestAjax({
		type: "POST",
		async : false, 
		url : "/adm/batch/siteInfoJson",
		params : params,
		
	//call back	
	},function(data){
		obj = data;
		console.log(obj);
		
		//페이징 표시
		if( paging == null && data.dataInfo.totalRow > 0 ){
			paging = com.initPaging({
				selector : "#paging",
				items : obj.dataInfo.totalRow,
				itemsOnPage : rowSize
			});
		}else if( paging != null ){
			com.updatePageItems("#paging", obj.dataInfo.totalRow)
			com.selectPage("#paging", obj.dataInfo.page);
		}
	});
	
	return obj;
}

// 검색
function search(){
	
	vObj.setVdata({
		page : 1,
		rowSize : rowSize,
		searchString : $("#searchString").val().trim().length  > 1 ? $("#searchString").val() : ""
	});
	
}

//페이지 이동, 함수명 고정
function goPage(pageNumber){
	
	page = pageNumber;
	
	if(page == undefined ? 1 : page);
	
	//데이터 업데이트
	vObj.setVdata({
		page : page,
		rowSize : rowSize
	});
	
}

//등록폼 호출
function openRegForm(){
	
	var obj = com.requestAjax({
		type: "POST",
		async : false,
		url : "/adm/batch/siteInfoForm"
	});

	com.popup.setContent(obj);
	com.popup.open();
}

//수정폼 호출
function openUptForm(siteSrl){
	var obj = com.requestAjax({
		type: "POST",
		async : false,
		url : "/adm/batch/siteInfoForm",
		params : {siteSrl : siteSrl}
	});
	
	com.popup.setContent(obj);
	com.popup.open();
}



</script>