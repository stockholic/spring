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
		    <col style="width:60px">
		     <col style="width:160px">
		     <col style="width:80px">
		     <col />
		     <col style="width:80px">
		     <col style="width:80px">
		     <col style="width:80px">
		     <col style="width:100px">
		     <col style="width:100px">
		     <col style="width:80px">
		  </colgroup>
		<thead>
		<tr>
			<th class="text-center">번호</th>
			<th>사이트 명</th>
			<th>구분</th>
			<th>링크</th>
			<th class="text-center">링크수</th>
			<th class="text-center">배치간격</th>
			<th class="text-center">사용여부</th>
			<th class="text-center">실행일</th>
			<th class="text-center">등록일</th>
			<th class="text-center"></th>
		</tr>
		</thead>
		<tbody>
		
		<tr v-for="lst in vData.dataList" v-cloak>
			<td class="text-center">{{ lst.num | addComma }}</td>
			<td>{{ lst.siteNm }}</td>
			<td>{{ lst.linkCd  =='dog' ? '강아지' : '고양이' }}</td>
			<td class="truncate-ellipsis"><a href="javascript:;" v-on:click="openUptForm(lst.linkSrl)">{{ lst.linkUrl }}</a></td>
			<td class="text-center">{{ lst.linkCnt}}</td>
			<td class="text-center">{{ lst.batchItv}}</td>
			<td class="text-center" v-bind:style="{'color': ( lst.useYn == 'Y' ? 'blue' : 'orange' )}">{{ lst.useYn == 'Y' ? '사용' : '미사용' }}</td>
			<td class="text-center">{{ lst.regDt | timestampToDate }}</td>
			<td class="text-center">{{ lst.excDt | timestampToDate }}</td>
			<td class="text-center"><a href="javascript:;" v-on:click="openLinkTest()">테스트</a></td>
		</tr>
		
		<tr v-if="vData.dataInfo.totalPage == 0" v-cloak>
			<td class="text-center" colspan="5" style="height:150px;vertical-align: middle;">자료가 없습니다.</td>
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


var vObj = null;			//Vue 객제
var rowSize = 15;		//페이지당 보여줄 로우 수

$(document).ready(function() {

	//Vue 초기화
	vObj = com.initVue("#dataWrap");
	
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
		url : "/adm/batch/siteLinkJson", 
		params : params,
		
	//call back	
	},function(data){
		obj = data;
		console.log(obj);
		
		//페이징 표시
		if( $("#paging > ul").length == 0  && data.dataInfo.totalRow > 0 ){
			com.initPaging({
				selector : "#paging",
				items : obj.dataInfo.totalRow,
				itemsOnPage : rowSize
			});
		}else if($("#paging > ul").length > 0 ){
			com.updatePageItems("#paging", obj.dataInfo.totalRow)
		}
	});
	
	return obj;
}

// 검색
function search(){
	
	//페이징 새로 그리기 위해 제거
	com.pageDestroy("#paging");
	
	vObj.setVdata({
		rowSize : rowSize,
		searchString : $("#searchString").val().trim().length  > 1 ? $("#searchString").val() : ""
	});
	
}

//페이지 이동, 함수명 고정
function goPage(pageNumber){
	
	var page = pageNumber;
	
	if(page == undefined ? 1 : page);
	
	//데이터 업데이트
	vObj.setVdata({
		page : page,
		rowSize : rowSize,
		searchString : $("#searchString").val().trim().length  > 1 ? $("#searchString").val() : ""
	});
	
}

//등록폼 호출
function openRegForm(){
	com.popup({
		title : "링크 등록",
		width : 700,
		height : 480,
		type : "POST",
		async : false,
		url : "/adm/batch/siteLinkForm"
	})
}

//수정폼 호출
function openUptForm(linkSrl){
	com.popup({
		title : "링크 수정",
		width : 700,
		height : 480,
		type : "POST",
		async : false,
		url : "/adm/batch/siteLinkForm",
		params : {linkSrl : linkSrl}
	})
}

//테스트 호출
function openLinkTest(){
	com.popup({
		title : "링크 작업중 ...",
		width : 400,
		height : 250,
		content : com.loading(30)
	});
	
	setTimeout(function() {
		com.popupClose();
	}, 5000);
}



</script>