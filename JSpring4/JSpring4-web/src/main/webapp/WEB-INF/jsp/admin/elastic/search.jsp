<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <script>
$(document).ready(function () {
	
	com.calendar("startDt");
	com.calendar("endDt");
});	
 </script>
 <div class="row">

	 <div class="col-lg-12">
	     <h3 class="page-header">Seaarch</h3>
	 </div>
	 
	  <h5 class="page-header"><span class="glyphicon glyphicon-th-large"></span> 검색조건</h5>
	  
	  <form class="form-inline">
	  
	  	<div style="float: left">
			<input type="text" class="form-control" style="width:500px" name="searchValue" id="searchValue">
	 		<button type="button" class="btn btn-default" id="searchBtn">검색</button>
	 	 </div><br><br>
	  
		<div style="clear:both;float:left;margin-right:10px">
		    <label>Title <input type="checkbox" value="title" name="fields"></label>
		    <label>Description <input type="checkbox" value="description" name="fields"></label>
		</div>
		
		<div style="float: left;margin-right:10px">
		    <div class="input-group date" id="startDt" style="width:150px;float: left;margin-right:10px">
		  		<input type="text" class="form-control" placeholder="시작일"><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
			</div>
			<div class="input-group date" id="endDt" style="width:150px;">
		  		<input type="text" class="form-control" placeholder="종료일"><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
			</div>
		 </div>  
		    
		<div style="float: left">
		    <label>AND <input type="radio" value="AND" name="operator" checked></label>
		    <label>OR <input type="radio" value="OR" name="operator"></label>
	  	</div>
		
	  	
 	 </form>
 	 <br>
 	 
 	 <h5 class="page-header"><span class="glyphicon glyphicon-th-large"></span> 검색결과 Total : ${elasticSearch.total }</h5>
 	 
	 <div>
		 <c:forEach var="list" items="${dataList}">
		 	<div>${list.title} - <fmt:formatDate value="${list.regdate}" pattern="yyyy/MM/dd HH:mm:ss" /></div>
		 </c:forEach>	
	 </div>
</div>