<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<form id="regFrm" id="regFrm">
<table class="table">
  <colgroup>
    <col width="25%">
    <col width="75%">
  </colgroup>
<tbody>
<tr>
	<th>사이트명</th>
	<td><input type="text" class="form-control" name="siteNm" id="siteNm" value="" style="width:200px"></td>
</tr>
<tr>
	<th>사이트 URL</th>
	<td><input type="text" class="form-control" name="siteUrl" id="siteUrl" value=""></td>
</tr>
</tbody>
</table>
</form> 

<div style="text-align:center;padding:10px">
	<button type="button" class="btn btn-default" onClick="save()">저장</button>&nbsp;
	<button type="button" class="btn btn-default" onClick="regPopup.close()">닫기</button>
</div> 


<script>

function save(){
	
	var obj = com.requestAjax({
		type: "POST",
		async : false, 
		url : "/adm/batch/siteListJson",
		params : $("#regFrm").serializeObject(),
	});

	console.log( obj )
}
</script>

