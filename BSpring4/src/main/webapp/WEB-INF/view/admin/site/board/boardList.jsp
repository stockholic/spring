<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tx" uri="/WEB-INF/tld/tx.tld"%>


<section class="content-header">
  <h1>
    공지사항
    <small>목록</small>  
  </h1>
  <ol class="breadcrumb">
    <li><a href="#"> Level</a></li>
    <li class="active">Here</li>
  </ol>
</section>


<section class="content container-fluid">

<table style="width:100%;margin:10px 0px 10px 0px">
<tr>
	<td style="width:60%;">
		<form name="frm" id="frm" action="" method="POST" class="form-inline">
		  <div class="form-group">
			   <select name="searchKey" id="searchKey" class="form-control">
				<option value="title" ${board.searchKey eq 'title' ? "selected" : "" }>제목</option>
				<option value="content" ${board.searchKey eq 'content' ? "selected" : "" }>내용</option>
			</select>
			<input type="text" class="form-control" name="searchValue" id="searchValue" value="${board.searchValue }">
		  </div>
		  <button type="button" onClick="search()" class="btn btn-default">검색</button>
		  
		  <input type="hidden" name="page" id="page" value="${board.page }">
		  <input type="hidden" name="boardSrl" id="boardSrl" value="">
		</form>

	</td>
	<td style="width:40%;text-align:right">Total : ${board.totalRow} [ ${board.page} / ${board.totalPage} ]</td>
</tr>
</table>

<table class="table table-hover table-list">
  <colgroup>
    <col style="width:50px">
    <col />
     <col style="width:50px">
     <col style="width:80px">
     <col style="width:80px">
  </colgroup>
<thead>
<tr>
	<th>번호</th>
	<th>제목</th>
	<th>파일</th>
	<th>등록자</th>
	<th>등록일</th>
</tr>
</thead>
<tbody>

<c:forEach var="lst" items="${list }" varStatus="status">
<tr style="text-align: center">
	<td>${board.totalRow - ((board.page-1) * board.rowSize + status.index) }</td>
	<td style="text-align:left;padding-left:${lst.lev*12}px">
		<c:if test="${lst.lev > 0}">└</c:if>
		<a href="javascript:view(${lst.boardSrl })">${lst.title }</a>
	</td>
	<td>${lst.fileCnt }</td>
	<td>${lst.userNm } </td>
	<td><%-- <fmt:formatDate value="${lst.regdate}" pattern="yyyy/MM/dd HH:mm:ss" /> --%></td>
</tr>
</c:forEach>
<c:if test="${empty list }">
<tr>
	<td colspan="5" style="height:100px;text-align:center;vertical-align: middle">자료가 없습니다.</td>
</tr>
</c:if>

</tbody>
</table> 


<c:if test="${board.totalRow > 0}">
<div style="text-align:center">
<nav aria-label="Page navigation">
  <ul class="pagination">
 	 <tx:nav totalPage="${board.totalPage }" page="${board.page }"/>
  </ul>
</nav>
</div>
</c:if>


<div>
	<button type="button" class="btn btn-default" onClick="save()">등록</button>
</div>



</section>

<script>

function search(){
	$("#page").val(1)
	document.frm.action = "/adm/board/${flag}/list";
	document.frm.submit();
}

function goPage(p){
	$("#page").val(p)
	document.frm.action = "/adm/board/${flag}/list";
	document.frm.submit();
}

function save(){
	document.location.href = "/adm/board/${flag}/insert/form";
}


function view(srl){
	document.frm.action = "/adm/board/${flag}/view"
	$("#boardSrl").val(srl);
	document.frm.submit();
}

</script>