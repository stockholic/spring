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
		
		<tr v-for="lst in vData.dataList" v-cloak>
			<td></td>
			<td>
				<a href="#">{{ lst.siteNm | addComma }}</a>
			</td>
			<td>{{ lst.siteNm }}</td>
			<td>{{   }}</td>
		</tr>
		
		</tbody>
		</table> 
		
	</div>
	
	<div id="paging"></div>
	
	<div class="box-footer">
		<button type="button" class="btn btn-primary btn-xm" onClick="save()">등록</button>
	</div>
		
	
</div>	

</section>

<script>

var vObj = null;
$(document).ready(function() {

	vObj = com.initVue("#dataWrap");
	
});


// Ajax 데이터 추출,  Vue  함수명 고정 getVdata
function getVdata(params){	
	
 	var obj = com.getAjaxData({
		type: "POST",
		async : false, 
		url : "/adm/batch/siteListJson",
		params : params,
	});
	
	console.log(obj)
	
	if( vObj == null && obj.dataInfo.totalRow > 0 ){
		com.initPaging({
			selector : "#paging",
			items : obj.dataInfo.totalRow
		});
	}
	return obj;
}
 	

//페이지 이동, 함수명 고정
function goPage(pageNumber){
	
	var params = {
		page : pageNumber,
		rowSize : 15
	}
	
	//데이터 업데이트
	vObj.setVdata(params);
	
	//로우 개수 변화, 페이징 업데이트
	com.updatePageItems("#paging", vObj.dataInfo.totalRow)
}


</script>