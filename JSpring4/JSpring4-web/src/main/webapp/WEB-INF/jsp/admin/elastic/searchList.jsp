<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
#result .title {color:#337AB7;font-weight: bold;}
#result .title em{color:blue;font-style:normal;}
#result .description em{color:blue;font-style:normal;}
#result .date{font-size: 9pt}

.pagination>li>a, .pagination>li>span { border-radius: 50% !important;margin: 0 5px;}
</style>


 <script>
$(document).ready(function () {
	com.calendar("startDt");
	com.calendar("endDt");
});	


function search(){
	if(!$("#searchValue").val().trim()) return;
	document.searchFrm.submit();
}
 </script>
 
  <div class="x_title">
    <h2>Searc</h2>
 	 <div class="clearfix"></div>
</div>
 

	  <h5 class="page-header"><span class="glyphicon glyphicon-th-large"></span> 검색조건</h5>
	  
	  <form id="searchFrm" name="searchFrm" action="searchList" method="post" onSubmit="search();return false;" class="form-inline" >
	  
	  	<div>
			<input type="text" class="form-control" style="width:420px" name="searchValue" id="searchValue" value="${elasticSearch.searchValue}">
	 		<button type="submit" class="btn btn-default" id="searchBtn">검색</button>
	 	 </div>
	 	 
	 	<div style="margin:10px 0px 10px 0px">
		    <div class="input-group date" id="startDt" style="width:150px;float: left;margin-right:10px">
		  		<input type="text" name="startDt" value="${elasticSearch.startDt}" class="form-control" placeholder="시작일"><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
			</div>
			<div class="input-group date" id="endDt" style="width:150px;">
		  		<input type="text" name="endDt" value="${elasticSearch.endDt}" class="form-control" placeholder="종료일"><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
			</div>
			&nbsp;&nbsp;&nbsp;
			 <label>Title <input type="checkbox" value="title" name="fields" ${!empty elasticSearch.fields[0] ? "checked" : ""}></label>
		    <label>Description <input type="checkbox" value="description" name="fields" ${!empty elasticSearch.fields[1] ? "checked" : ""}></label>
		 </div>  
	 	 
		<div style="height:40px;line-height:40px">
		    
		    <label>AND <input type="radio" value="AND" name="defaultOperator" ${elasticSearch.defaultOperator eq "AND" ? "checked" : ""}></label>
		    <label>OR <input type="radio" value="OR" name="defaultOperator" ${elasticSearch.defaultOperator eq "OR" ? "checked" : ""}></label>
		    &nbsp;&nbsp;&nbsp;
		    <label>최신순 <input type="radio" value="regdate" name="sortKey" ${elasticSearch.sortKey eq "regdate" ? "checked" : ""}></label>
		    <label>정확도순 <input type="radio" value="_score" name="sortKey" ${elasticSearch.sortKey eq "_score" ? "checked" : ""}></label>
		</div>
		
	  	 
 	 </form>
 	 <br>
 	 
 	 <h5 class="page-header">
 	 	<span class="glyphicon glyphicon-th-large"></span> 검색결과 <fmt:formatNumber value="${elasticSearch.total }" pattern="#,###" />
	</h5>
 	 
	 <div id="result">
		 <c:forEach var="list" items="${dataList}">
		 	<div><span class="title">${list.title}</span> - <span class="date"><fmt:formatDate value="${list.regdate}" pattern="yyyy/MM/dd HH:mm" /></span></div>
		 	<div class="description">${list.description}</div>
		 	<hr>
		 </c:forEach>	
	 </div>
	 
	 <div class="col-md-offset-4">
		<ul class="pagination">
			<li><a href="#">«</a></li>
			<li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
			<li><a href="#">2</a></li>
			<li><a href="#">3</a></li>
			<li><a href="#">4</a></li>
			<li><a href="#">5</a></li>
			<li><a href="#">»</a></li>
		</ul>
	</div>	
	 
