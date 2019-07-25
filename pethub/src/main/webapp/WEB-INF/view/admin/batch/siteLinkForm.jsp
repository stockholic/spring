<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<form id="regFrm" id="regFrm">

<input type="hidden"name="linkSrl" id="linkSrl" value="${siteLink.linkSrl}">

<table class="table">
  <colgroup>
    <col width="20%">
    <col width="80%">
  </colgroup>
<tbody>
<tr>
	<th>구분</th>
	<td>
	<select name="linkCd" id="linkCd" class="form-control" style="width:150px">
		<option value="Y" ${siteLink.linkCd eq "dog" ? "selected" : "" }>강아지</option>
		<option value="N" ${siteLink.linkCd eq "cat" ? "selected" : "" }>고양이</option>
	</select>
	</td>
</tr>
<tr>
	<th class="required">사이트</th>
	<td>
	<select name="siteSrl" id="siteSrl" class="form-control" style="width:300px">
		<option value="">선택</option>
		<c:forEach var="lst" items="${siteInfoLIst }" varStatus="status">
		<option value="${lst.siteSrl }" ${lst.siteSrl eq siteLink.siteSrl ? "selected" : "" }>${lst.siteNm }</option>
		</c:forEach>
	</select>
	</td>
</tr>
<tr>
	<th class="required">링크</th>
	<td><input type="text" class="form-control" name="linkUrl" id="linkUrl" value="${siteLink.linkUrl}"></td>
</tr>
<tr>
	<th class="required">클래스</th>
	<td><input type="text" class="form-control" name="linkCls" id="linkCls" value="${siteLink.linkCls}"></td>
</tr>
<tr>
	<th class="required">제목메소드</th>
	<td><input type="text" class="form-control" name="linkMtdLst" id="linkMtdLst" value="${siteLink.linkMtdLst}" style="width:150px"></td>
</tr>
<tr>
	<th>내용메소드</th>
	<td><input type="text" class="form-control" name="linkMtdCts" id="linkMtdCts" value="${siteLink.linkMtdCts}" style="width:150px"></td>
</tr>
<tr>
	<th class="required">배치간격</th>
	<td><input type="text" class="form-control" name="batchItv" id="batchItv" value="${siteLink.batchItv}" style="width:150px" onKeydown="com.numberInput(event)" ></td>
</tr>
<tr>
	<th>사용여부</th>
	<td>
	<select name="useYn" id="useYn" class="form-control" style="width:150px">
		<option value="Y" ${siteLink.useYn eq "Y" ? "selected" : "" }>사용</option>
		<option value="N" ${siteLink.useYn eq "N" ? "selected" : "" }>미사용</option>
	</select>
	</td>
</tr>
</tbody>
</table>
</form> 

<div style="text-align:center;">
	<c:if test="${ empty siteLink.linkSrl  }">
		<button type="button" class="btn btn-default" onClick="save()">저장</button>&nbsp;
		<button type="button" class="btn btn-default" onClick="com.popupClose()">닫기</button>
	</c:if>
	<c:if test="${ !empty siteLink.linkSrl  }">
		<button type="button" class="btn btn-default" onClick="update()">수정</button>&nbsp;
		<button type="button" class="btn btn-default" onClick="remove()">삭제</button>&nbsp;
		<button type="button" class="btn btn-default" onClick="com.popupClose()">닫기</button>
	</c:if>
</div> 


<script>

// 등록
function save(){
	
	if( com.validation("#regFrm") == false ) return;
	
	var obj = com.requestAjax({
		type: "POST",
		async : false, 
		url : "/adm/batch/insertSiteLink",
		params : $("#regFrm").serializeObject(),
	});
	
	if (obj.result > 0){
		com.notice("저장 되었습니다.")
		com.popupClose();
		goPage(1);
	}

}

// 수정
function update(){
	
	if( com.validation("#regFrm") == false ) return;
	
	var obj = com.requestAjax({
		type: "POST",
		async : false, 
		url : "/adm/batch/updateSiteLink",
		params : $("#regFrm").serializeObject(),
	});
	
	if (obj.result > 0){
		com.notice("수정 되었습니다.")
		com.popupClose();
		goPage();
	}
}


// 삭제
function remove(){
	
	com.confirm({
		content : "삭제 하겠습니까 ?",
		confirm : function(){
			var obj = com.requestAjax({
				type: "POST",
				async : false, 
				url : "/adm/batch/deleteSiteLink",
				params : { linkSrl : $("#linkSrl").val() },
			});
			
			if (obj.result > 0){
				com.notice("삭제 되었습니다.")
				com.popupClose();
				goPage();
			}
		},
		cancel : function(){
		}
	});
	
}

</script>

