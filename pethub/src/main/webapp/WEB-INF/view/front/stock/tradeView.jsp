<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tx" uri="http://www.stockholic.com/tags"%>

<section class="content-header">
  <h1>
    주식거래
    <small>조회</small>  
  </h1>
  <ol class="breadcrumb">
    <li> 주식관리</li>
    <li> 주식매매</li>
    <li class="active">조회</li>
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
	<tr>
		<th>종목</th>
		<td>${tradeInfo.stockNm}
			<input type="hidden" class="form-control" name="stockCd" id="stockCd" value="${tradeInfo.stockCd}">
		</td>
		<th>구분</th>
		<td> ${tradeInfo.tradeTp eq 'B' ? "매수" : "<span style='color:blue'>매도</span>" }</td>
	</tr>
	<tr>
		<th>매입가</th>
		<td><fmt:formatNumber value="${tradeInfo.tradeBuyPrc}" pattern="#,###.##"/></td>
		<th><span style="${tradeInfo.tradeTp eq 'B' ? 'display:none' : '' }">매도가</span></th>
		<td><span style="${tradeInfo.tradeTp eq 'B' ? 'display:none' : '' }"><fmt:formatNumber value="${tradeInfo.tradeSellPrc}" pattern="#,###.##"/></span></td>
	</tr>
	<tr>
		<th>수량</th>
		<td><fmt:formatNumber value="${tradeInfo.tradeEa}" pattern="#,###.##"/></td>
		<th><span style="${tradeInfo.tradeTp eq 'B' ? 'display:none' : '' }">수수료</span></th>
		<td><span style="${tradeInfo.tradeTp eq 'B' ? 'display:none' : '' }"><fmt:formatNumber value="${tradeInfo.fee}" pattern="#,###.##"/></span></td>
	</tr>
	<tr>
		<th>매입금액</th>
		<td><fmt:formatNumber value="${tradeInfo.tradeBuyPrc * tradeInfo.tradeEa}" pattern="#,###.##"/></td>
		<th><span style="${tradeInfo.tradeTp eq 'B' ? 'display:none' : '' }">세금</span></th>
		<td><span style="${tradeInfo.tradeTp eq 'B' ? 'display:none' : '' }"><fmt:formatNumber value="${tradeInfo.tax}" pattern="#,###.##"/></span></td>
	</tr>
	
	<c:if test="${tradeInfo.tradeTp ne 'B' }">
	<tr>
		<th>매도금액</th> 
		<td><fmt:formatNumber value="${tradeInfo.tradeSellPrc * tradeInfo.tradeEa}" pattern="#,###.##"/></td>
		<th>손익</th>
		<td>
			<fmt:formatNumber value="${((tradeInfo.tradeSellPrc- tradeInfo.tradeBuyPrc) * tradeInfo.tradeEa) - tradeInfo.fee - tradeInfo.tax}" pattern="#,###.##"/>
			(<fmt:formatNumber value="${((((tradeInfo.tradeSellPrc*tradeInfo.tradeEa) - tradeInfo.fee - tradeInfo.tax) / (tradeInfo.tradeBuyPrc * tradeInfo.tradeEa )) -1) * 100}" pattern="#,###.##"/>%)
		</td>
	</tr>
	</c:if>
	<tr>
		<th></th> 
		<td></td>
		<th>평점</th>
		<td>
			<c:set var="key" value="3,2,1,0,-1,-2,-3" />
			<c:set var="value" value="3,2,1,0,-1,-2,-3" />
			<tx:selectArray key="${fn:split(key, ',')}" value="${fn:split(value, ',')}" id="point" selectKey="${tradeInfo.point}" style="width:50px" onChange="updatePoint(this)"/>
		</td>
	</tr>
	<tr>
			<td colspan="4" style="height:200px;vertical-align: top;">
			<p>${tradeInfo.memo}</p>
			<c:forEach var="lst" items="${fileList }">
			<p><img src="/fileDownLoad?srl=${lst.fileSrl}"></p>
			</c:forEach> 
		</td>
	</tr>
	<tr>
		<td colspan="4" >
		<c:forEach var="lst" items="${fileList }">
			<p style="font-size: 13px"><a href="javascript:" onClick="fileDownLoad(${lst.fileSrl})"><i class="fa fa-fw fa-floppy-o"></i>  ${lst.fileRealNm }  (<tx:fileSize fileSize="${lst.fileSize }" />)</a></p>
		</c:forEach> 
		</td>
	</tr>
	</tbody>
	</table> 
    </div>
	
	<div class="box-footer">
		<button type="button" class="btn btn-primary btn-xm" onClick="list()">목록</button>
		<button type="button" class="btn btn-primary btn-xm" onClick="update()">수정</button>
		<button type="button" class="btn btn-primary btn-xm" onClick="remove()">삭제</button>
	 </div>
	    
</div>
 

</section>
<form name="frm" method="POST">
	<input type="hidden" name="page" value="${trade.page }">
	<input type="hidden" name="tradeSrl" id="tradeSrl" value="${trade.tradeSrl }">
	<input type="hidden" name="searchTradeTp" value="${trade.searchTradeTp }">
</form>

<script>

function list(){
	document.frm.action = "/stock/trade/list";
	document.frm.submit();
}

function update(){ 
	document.frm.action = "/stock/trade/update/form"
	document.frm.submit();
}

function remove(){
	
	if( confirm("삭제하겠습니까?") == false ) return;
	
	$.ajax({      
    	type : "POST",  
        url : "/stock/trade/delete",
        data : {tradeSrl : $("#tradeSrl").val()},
	       beforeSend : function(xhr){
			xhr.setRequestHeader("AJAX", "true");
	    },
       success : function(response){
       	document.frm.action = "/stock/trade/list"
       	document.frm.submit();
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

function fileDownLoad(srl){ 
	document.location.href = "/fileDownLoad?srl=" + srl;
}	

function updatePoint(obj){

	$.ajax({      
    	type : "POST",  
        url : "/stock/trade/updatePoint",
        data : {tradeSrl : $("#tradeSrl").val(),point : $(obj).val()},
	       beforeSend : function(xhr){
			xhr.setRequestHeader("AJAX", "true");
	    },
       success : function(response){
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


</script>
