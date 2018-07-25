<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tx" uri="/WEB-INF/tld/tx.tld"%>


<section class="content-header">
  <h1>
    사용자관리
    <small>등록/수정/권한부여</small>  
  </h1>
  <ol class="breadcrumb">
    <li><a href="#"> Level</a></li>
    <li class="active">Here</li>
  </ol>
</section>

<section class="content container-fluid">
<table style="width:100%;margin-bottom:10px">
<tr>
	<td style="width:80%;">
		<form name="frm" id="frm" action="" method="POST" class="form-inline">
		  <div class="form-group">
		    <label for="exampleInputName2">사용여부</label>
			   <select name="useYn" id="useYn" class="form-control" onChange="search()">
				<option value="">전체</option>
				<option value="Y" ${user.useYn eq "Y" ? "selected" : "" }>사용</option>
				<option value="N" ${user.useYn eq "N" ? "selected" : "" }>미사용</option>
			</select>
		  </div>
		  <button type="button" onClick="search()" class="btn btn-default">검색</button>
		  
		  <input type="hidden" name="page" id="page" value="${user.page }">
		  <input type="hidden" name="userSrl" id="userSrl" value="">
		</form>

	</td>
	<td style="width:20%;text-align:right">Total : ${user.totalRow} [ ${user.page} / ${user.totalPage} ]</td>
</tr>
</table>

<table class="table table-hover table-list">
  <colgroup>
    <col width="10%">
    <col width="25%">
    <col width="25%">
    <col width="20%">
    <col width="20%">
  </colgroup>
<thead>
<tr style="border-bottom:1px solid blue"> 
	<th>번호</th>
	<th>아이디</th>
	<th>사용자</th>
	<th>권한그룹</th>
	<th>사용여부</th>
</tr>
</thead>
<tbody>

<c:forEach var="lst" items="${list }" varStatus="status">
<tr style="text-align: center">
	<td>${user.totalRow - ((user.page-1) * user.rowSize + status.index) }</td>
	<td><a href="javascript:update(${lst.userSrl })">${lst.userId }</a></td>
	<td>${lst.userNm }</td>
	<td>${lst.roleNm }</td>
	<td>${lst.useYn }</td>
</tr>
</c:forEach>
<c:if test="${empty list }">
<tr>
	<td colspan="5" style="height:100px;text-align:center">자료가 없습니다.</td>
</tr>
</c:if>

</tbody>
</table> 

<div>
	<button type="button" class="btn btn-default" onClick="save()">등록</button>
</div>

<c:if test="${user.totalRow > 0}">
<div style="text-align:center">
<nav aria-label="Page navigation">
  <ul class="pagination">
 	 <tx:nav totalPage="${user.totalPage }" page="${user.page }"/>
  </ul>
</nav>
</div>
</c:if>

</section>

<script>

function search(){
	$("#page").val(1)
	document.frm.action = "/adm/user/list";
	document.frm.submit();
}

function goPage(p){
	$("#page").val(p)
	document.frm.action = "/adm/user/list";
	document.frm.submit();
}

function save(){
	document.location.href = "/adm/user/form";
}

function update(srl){
	document.frm.action = "/adm/user/form"
	$("#userSrl").val(srl);
	document.frm.submit();
}

</script>