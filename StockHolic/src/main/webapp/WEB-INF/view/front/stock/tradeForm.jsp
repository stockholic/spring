<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tx" uri="http://www.stockholic.com/tags"%>

<link rel="stylesheet" href="/static/assets/fileUpload/css/uploadfile.css">
<link rel="stylesheet" href="/static/assets/summernote/summernote.css">

<script src="/static/assets/summernote/summernote.min.js"></script>
<script src="/static/assets/summernote/lang/summernote-ko-KR.js"></script>

<script src="/static/assets/fileUpload/js/jquery.form.js"></script>
<script src="/static/assets/fileUpload/js/jquery.uploadfile.min.js"></script>


<section class="content-header">
  <h1>
    주식거래
    <small>등록폼</small>  
  </h1>
  <ol class="breadcrumb">
    <li> 주식관리</li>
    <li> 주식매매</li>
    <li class="active">등록폼</li>
  </ol>
</section>


<section class="content container-fluid">

<div class="box box-default">
    <div class="box-body">
    
    
    <table class="table">
	  <colgroup>
	    <col width="15%">
	    <col width="35%">
	    <col width="15%">
	    <col width="35%">
	  </colgroup>
	<tbody>
	<form id="frm" name="frm">
	<input type="hidden" name="tradeSrl" value="${trade.tradeSrl }">
	<input type="hidden" name="fileInfo" id="fileInfo" value="">
	<tr>
		<th>종목</th>
		<td>
			<input type="text" class="form-control" name="stockNm" id="stockNm" value="${tradeInfo.stockNm}" onClick="select()" style="width:100%">
			<input type="hidden" class="form-control" name="stockCd" id="stockCd" value="${tradeInfo.stockCd}">
		</td>
		<th>구분</th>
		<td>
			<select name="tradeTp" id="tradeTp" class="form-control" style="width:50%" onChange="showTp(this)">
				<option value="B" ${tradeInfo.tradeTp eq 'B' ? "selected" : "" }>매수</option>
				<option value="S" ${tradeInfo.tradeTp eq 'S' ? "selected" : "" }>매도</option>
			</select>
		</td>
	</tr>
	<tr>
		<th>매입가</th>
		<td><input type="text" class="form-control" name="tradeBuyPrc" id="tradeBuyPrc" value="${tradeInfo.tradeBuyPrc}" onClick="select()" style="width:50%"></td>
		<th><span style="${tradeInfo.tradeTp ne 'S' ? 'display:none' : ''}" class="sType">매도가</span></th>
		<td><span style="${tradeInfo.tradeTp ne 'S' ? 'display:none' : ''}" class="sType"><input type="text" class="form-control" name="tradeSellPrc" id="tradeSellPrc" value="${tradeInfo.tradeSellPrc}" onClick="select()" style="width:50%"></span></td>
	</tr>
	<tr>
		<th>수량</th>
		<td><input type="text" class="form-control" name="tradeEa" id="tradeEa" value="${tradeInfo.tradeEa}" onClick="select()" style="width:50%"></td>
		<th><span style="${tradeInfo.tradeTp ne 'S' ? 'display:none' : ''}" class="sType">수수료</span></th>
		<td><span style="${tradeInfo.tradeTp ne 'S' ? 'display:none' : ''}" class="sType"><input type="text" class="form-control" name="fee" id="fee" value="${tradeInfo.fee}" onClick="select()" style="width:50%"></span></td>
	</tr>
	<tr>
		<th>날짜</th>
		<td>
			<div class="input-group" style="width:50%">
                 <div class="input-group-addon">
                   <i class="fa fa-calendar"></i>
                 </div>
                 <input type="text" class="form-control" name="tradeDt" id="tradeDt" value="${tradeInfo.tradeDt }">
               </div>
		</td>
		<th><span style="${tradeInfo.tradeTp ne 'S' ? 'display:none' : ''}" class="sType">세금</span></th>
		<td><span style="${tradeInfo.tradeTp ne 'S' ? 'display:none' : ''}" class="sType"><input type="text" class="form-control" name="tax" id="tax" value="${tradeInfo.tax}" onClick="select()" style="width:50%"></span></td>
	</tr>
	<tr>
		<td colspan="4">
		<textarea id="memo" name="memo" style="display: none;" class="form-control">${tradeInfo.memo}</textarea>
		</td>
	</tr>
	</form>
	<tr>
		<td colspan="4">
		<c:forEach var="lst" items="${fileList }">
			<p style="font-size: 13px">
				<a href="javascript:" onClick="fileDownLoad(${lst.fileSrl})"><i class="fa fa-fw fa-floppy-o"></i>  ${lst.fileRealNm }  (<tx:fileSize fileSize="${lst.fileSize }" />)</a>
				&nbsp;&nbsp;<a href="javascript:" onClick="removeFile(this,${lst.fileSrl })"><span style="color:#000;text-decoration: underline;">삭제</span></a>
			</p>
		</c:forEach>
		</td>
	</tr>
	<tr>
		<td colspan="4">
			<div id="fileuploader"></div> 
		</td>
	</tr>
	</tbody>
	</table> 
    </div>
	
	<div class="box-footer">
		<button type="button" class="btn btn-primary btn-xm" onClick="list()">목록</button>
		<button type="button" class="btn btn-primary btn-xm" id="save">저장</button>
	 </div>
	    
</div>
 

</section>

<form name="urlFrm" method="POST">
	<input type="hidden" name="page" id="page" value="${trade.page }">
	<input type="hidden" name="tradeSrl" id="tradeSrl" value="${trade.tradeSrl }">
	<input type="hidden" name="searchTradeTp" value="${trade.searchTradeTp }">
</form>

<script>

//--------------------------------- file Upload 
var uploadObj = null;
var fileInfo = {
	insertFile : [],
	deleteFile : [] 
}
var maxFileCount = 10;
var fileCount = ${empty tradeInfo.fileCnt ? 0 : tradeInfo.fileCnt};
var allowFileCount = maxFileCount - fileCount;


$(document).ready(function() {
	
	com.calendar("tradeDt",{orientation:"bottom"});
	
	//--------------------------------------------------------- AutoComplete
	
	$.ajax({      
		type : "GET",  
        url : "/stock/code",
        success : function(response){
        	  $('#stockNm').autocomplete({
                  lookup: response,
                  onSelect: function (suggestion) {
                      $("#stockCd").val(suggestion.data);
                  }
              });
        },   
        error : function(xhr) { }
	});  

	
	//--------------------------------------------------------- Upload
 	uploadObj = com.fileUpload({
 		id : "fileuploader",
 		url:"/fileUpload/stock",
		maxFileCount : allowFileCount,
		maxFileSize:1024 * 1024 * 2,		//2M
		allowedTypes : "jpg,png,gif,bmp"
		
	//업로드 파일정보 처리
 	},function(data){
 		fileInfo.insertFile.push(data)
 		
 	//모든 파일 업로드 후 처리
 	},function(){
 		send();
 	}) ;
 	
 	$("#save").click(function(){
 		save();
 	});
/*
 	$("#allCancel").click(function(){
 		uploadObj.cancelAll();
 	});
 */	
 	com.editor("memo");
 
 
//--------------------------------------------------------- Mask
 $('#tradeBuyPrc').mask("#,##0,000", {reverse: true});	//매입가
 $('#tradeSellPrc').mask("#,##0,000", {reverse: true});	//매도가
 $('#tradeEa').mask("#,##0,000", {reverse: true});		//수량
 $('#fee').mask("#,##0,000", {reverse: true});				//수수료
 $('#tax').mask("#,##0,000", {reverse: true});				//세금
 
});
 	
//--------------------------------- file Upload end 	

function list(){
	document.urlFrm.action = "/stock/trade/list";
	document.urlFrm.submit();
}
function view(){
	document.urlFrm.action = "/stock/trade/view"
	document.urlFrm.submit();
}

function save(){
	
	if( $("#stockCd").val().trim() == "" ){
		alert("종목을 선택해주세요");
		$("#stockNm").focus();
		return;
	}
	if( $("#tradeBuyPrc").val().trim() == "" ){
		alert("매입가를 입력해주세요");
		$("#tradeBuyPrc").focus();
		return;
	}
	if( $("#tradeEa").val().trim() == "" ){
		alert("수량을 입력해주세요");
		$("#tradeEa").focus();
		return;
	}
	if( uploadObj.getFileCount() > 0)
		uploadObj.startUpload();
	else
		send();		
}

function send(){
	$("#fileInfo").val(JSON.stringify(fileInfo));
	
	$("#tradeBuyPrc").val($("#tradeBuyPrc").val().replace(/,/g,""));
	$("#tradeEa").val($("#tradeEa").val().replace(/,/g,""));
	if($("#tradeTp").val() == "B"){
		$("#tradeSellPrc").val(0);
		$("#tax").val(0);
		$("#fee").val(0);
	}else{
		$("#tradeSellPrc").val($("#tradeSellPrc").val().replace(/,/g,""));
		$("#tax").val($("#tax").val().replace(/,/g,""));
		$("#fee").val($("#fee").val().replace(/,/g,""));
	}
	
	$.ajax({      
		type : "POST",  
        url : "/stock/trade/${action}",
        data :  $("#frm").serialize(),
        beforeSend : function(xhr){
			xhr.setRequestHeader("AJAX", "true");
	    },
        success : function(response){
        	console.log(response)
        	$("#tradeSrl").val(response.tradeSrl)
        	view();
        },   
        error : function(xhr) {
        	if(xhr.status == "403"){
        		document.location.href = "/login";
        	}else{
    	        alert("에러 : " + xhr.status);
	       	}
        }
	});
}

function removeFile(obj,srl){
	allowFileCount++;
	$("#maxFileCount").text(allowFileCount)
	uploadObj.update({maxFileCount : allowFileCount});
	$(obj).closest("p").remove();
	
	fileInfo.deleteFile.push(srl);
}

function showTp(obj){
	if( $(obj).val() == "B" ) $(".sType").hide() 
	else if( $(obj).val() == "S" ) $(".sType").show() 
}

</script>
