<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/static/css/mail-maker.css">
<!-- DatePicker -->
<link href="/static/assets/datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="/static/js/bootstrap.min.js"></script>
<script src="/static/js/mail-maker.js"></script>
<script src="/static/js/mail-maker-img.js"></script>

 <!-- DatePicker -->
 <script src="/static/assets/datepicker/js/bootstrap-datepicker.min.js"></script>
 <script src="/static/assets/datepicker/locales/bootstrap-datepicker.kr.min.js"></script>


<body> 

<form action="/tmall/stockList" name="searchFrm" id="searchFrm"method="GET" class="form-inline form">
	<table  style="width:100%;margin-bottom:10px">
	<colgroup>
	<col style="width:30%" />
	<col style="width:70%" />
	</colgroup> 
	<tr> 
		<th style="font-size: 15px"><i class="entypo-layout"></i>메일리스트 / 폼 생성</th>
		<th style="text-align:right">
		<button type="button" class="btn btn-primary btn-sm" ><i class="glyphicon glyphicon-plus"></i> 메일폼 생성</button>
			<select id="limit" name="limit" class="form-control" onchange="search()" style="width:100px">
				<option <c:if test="${stock.limit == 20}">selected</c:if> value="15">20개 정렬</option>
				<option <c:if test="${stock.limit == 40}">selected</c:if> value="30">40개 정렬</option>
				<option <c:if test="${stock.limit == 80}">selected</c:if> value="50">80개 정렬</option>
				<option <c:if test="${stock.limit == 100}">selected</c:if> value="100">100개 정렬</option>
			</select>
		</th>
	</tr>
	</table>
</form>

	
<table class="table table-bordered table-hover mail-list">
<colgroup>
<col style="width:4%" />
<col />
<col style="width:20%" />
<col style="width:10%" />
<col style="width:10%" />
<col style="width:10%" />
<col style="width:8%" />
</colgroup> 
<thead>
<tr style="background-color: #efefef">
	<th>번호</th>
	<th>메일명</th>
	<th>관리</th>
	<th>제작일</th>
	<th>생성일</th>
	<th>발송예정일</th>
	<th>생성자</th>
</tr>
</thead>
<tbody> 
<c:choose>
	<c:when test="${empty dataList}">
		<tr>
	<td colspan='7' class="no-data" >데이터가 존재하지 않습니다.</td>
</tr> 
	</c:when>
	<c:otherwise>
		<c:forEach var="list" items="${dataList}" varStatus="status">
		<tr>
			<td>1</td>	
			<td style="text-align: left">${list.mailTitle }</td>
			<td>
				<button type="button" class="btn btn-primary btn-xs" >미리보기</button>
				<button type="button" class="btn btn-primary btn-xs" >폼복제</button>&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-warning btn-xs" >삭제</button>
			</td>	
			<td><fmt:formatDate value="${list.createDt}" pattern="yyyy/MM/dd HH:mm" /></td>	
			<td><fmt:formatDate value="${list.updateDt}" pattern="yyyy/MM/dd HH:mm" /></td>	
			<td><fmt:formatDate value="${list.createDt}" pattern="yyyy/MM/dd HH:mm" /></td>	
			<td>스팸머</td>	
		</tr>
		</c:forEach> 
	</c:otherwise>
</c:choose>
</tbody>
</table>


<div style="text-align:center"> 

<nav aria-label="..."> 
	<ul class="pagination"> 
		<li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">«</span></a></li>
		<li class="active"><a href="#">1</a></li> 
		<li><a href="#">2</a></li> 
		<li><a href="#">3</a></li> <li><a href="#">4</a></li> 
		<li><a href="#">5</a></li> <li><a href="#" aria-label="Next"><span aria-hidden="true">»</span></a></li> 
	</ul> 
</nav>

</div>

<div class="searchForm" > 

<form class="form-inline">
  <div class="form-group" style="margin-right:20px;">
  	<strong>메일예정일</strong>
    <input type="text" class="form-control" id="sendDate" name="sendDate"  style="width:100px">
  </div>
 <strong>메일명</strong> 
  <div class="form-group">
    <input type="text" class="form-control" id="mail_title" name="mail_title" >
  </div>
  <button type="button" class="btn btn-primary btn-sm" ><i class="glyphicon glyphicon-search"></i> 검색</button> 
</form>

</div>

</body>

<script>
$(document).ready(function(){
	//캘린더
	mk.calendar("sendDate");
	$(this).click(function(){
		$(this).blur();
	});
	 
});

</script>

