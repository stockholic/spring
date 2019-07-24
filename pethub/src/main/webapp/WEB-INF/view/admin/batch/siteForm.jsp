<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<form id="regFrm" id="regFrm">

<input type="hidden"name="siteSrl" id="siteSrl" value="${siteInfo.siteSrl}">

<table class="table">
  <colgroup>
    <col width="25%">
    <col width="75%">
  </colgroup>
<tbody>
<tr>
	<th>사이트명</th>
	<td><input type="text" class="form-control" name="siteNm" id="siteNm" value="${siteInfo.siteNm }" style="width:200px"></td>
</tr>
<tr>
	<th>사이트 URL</th>
	<td><input type="text" class="form-control" name="siteUrl" id="siteUrl" value="${siteInfo.siteUrl}"></td>
</tr>
<tr>
	<th>기타</th>
	<td><input type="text" class="form-control" name="siteEtc" id="siteEtc" value="${siteInfo.siteEtc}"></td>
</tr>
</tbody>
</table>
</form> 

<div style="text-align:center;">

	
<c:if test="${ empty siteInfo.siteSrl  }">
	<button type="button" class="btn btn-default" onClick="save()">저장</button>&nbsp;
	<button type="button" class="btn btn-default" onClick="com.popup.close()">닫기</button>
</c:if>
<c:if test="${ !empty siteInfo.siteSrl  }">
	<button type="button" class="btn btn-default" onClick="update()">수정</button>&nbsp;
	<button type="button" class="btn btn-default" onClick="remove()">삭제</button>&nbsp;
	<button type="button" class="btn btn-default" onClick="com.popup.close()">닫기</button>
</c:if>
	
	
</div> 


<script>

// 등록
function save(){
	
	var obj = com.requestAjax({
		type: "POST",
		async : false, 
		url : "/adm/batch/insertSite",
		params : $("#regFrm").serializeObject(),
	});
	
	if (obj.result > 0){
		com.notice("저장 되었습니다.")
		com.popup.close();
		goPage(1);
	}

}

// 수정
function update(){
	var obj = com.requestAjax({
		type: "POST",
		async : false, 
		url : "/adm/batch/updateSite",
		params : $("#regFrm").serializeObject(),
	});
	
	if (obj.result > 0){
		com.notice("수정 되었습니다.")
		com.popup.close();
		goPage();
	}
}

// 삭제
function remove(){
	
	com.confirm.setContent("삭제 하시겠습니까 ?")
	com.confirm.open({
		
		confirm : function(){
			
			var obj = com.requestAjax({
				type: "POST",
				async : false, 
				url : "/adm/batch/deleteSite",
				params : { siteSrl : $("#siteSrl").val() },
			});
			
			if (obj.result > 0){
				com.notice("삭제 되었습니다.")
				com.popup.close();
				goPage();
			}
		},
		cancel : function(){
		}
	});
	
}

</script>

