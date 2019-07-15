<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tx" uri="http://www.pethub.kr/tags"%>


<section class="content-header">
  <h1>
    주식거래
    <small>목록</small>  
  </h1>
  <ol class="breadcrumb">
    <li> 주식관리</li>
    <li> 주식매매</li>
    <li class="active">목록</li>
  </ol>
</section>


<section class="content container-fluid">

<div class="box box-default">
    <div class="box-body">
    
    <div class="search-box">
   	<div class="form-group-sm pull-left">
    	<form name="frm" id="frm" action="" method="POST" class="form-inline" onSubmit="search();return false">
	  	<div class="form-group-sm">
	  		<label>구분</label>
		   <select name="searchTradeTp" id="searchTradeTp" class="form-control" onChange="search()">
				<option value="">전체</option>
				<option value="B" ${trade.searchTradeTp eq 'B' ? "selected" : "" }>매수</option>
				<option value="S" ${trade.searchTradeTp eq 'S' ? "selected" : "" }>매도</option>
			</select>
		
			<label>평점</label>
			<c:set var="key" value="3,2,1,0,-1,-2,-3" />
			<c:set var="value" value="3,2,1,0,-1,-2,-3" />
			<tx:selectArray key="${fn:split(key, ',')}"  all="전체"  value="${fn:split(value, ',')}" id="searchPoint" clasz="form-control" selectKey="${trade.searchPoint}" onChange="search()"/>
		
           <label>날짜</label>
			<div class="input-group" style="width:130px">
            <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
            <input type="text" class="form-control" name="searchStartDt" id="searchStartDt" value="${trade.searchStartDt }" placeholder="시작일">
            </div>
        	<div class="input-group" style="width:130px">
            <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
            <input type="text" class="form-control" name="searchEndDt" id="searchEndDt" value="${trade.searchEndDt }" placeholder="종료일">
            </div>
               
			<label>종목</label>
			<div class="input-group input-group-sm" style="width: 250px;">
			<input type="text" name="stockNm" id="stockNm" value="${trade.stockNm }" class="form-control pull-right">
			<input type="hidden" name="stockCd" id="stockCd" value="${trade.stockCd }">
			<div class="input-group-btn">
				<button type="button" onClick="search()" class="btn btn-default"><i class="fa fa-search"></i></button>
			</div>
           	</div>
	  	</div>
	</div>
	  
	  <input type="hidden" name="page" id="page" value="${trade.page }">
	  <input type="hidden" name="tradeSrl" id="tradeSrl" value="">
	</form>
	<div class="pull-right">Total : ${trade.totalRow} [ ${trade.page} / ${trade.totalPage} ]</div> 
  	</div>
    
	  <table class="table table-hover table-top">
		  <colgroup>
		    <col width="8">
		    <col width="7%">
		    <col width="18%">
		    <col width="8%">
		    <col width="8%">
		    <col width="7%">
		    <col width="13%">
		    <col width="8%">
		    <col width="7%">
		    <col width="10%">
		    <col width="8%">
		  </colgroup>
		<thead>
		<tr>
			<th>번호</th>
			<th>구분</th>
			<th>종목</th>
			<th>매입가</th>
			<th>매도가</th>
			<th>수량</th>
			<th>금액</th>
			<th>손익</th>
			<th>수익률</th>
			<th>날짜</th>
			<th>평점</th>
		</tr>
		</thead>
		<tbody>
		
		<c:forEach var="lst" items="${list }" varStatus="status">
		<c:set value="0" var="profit"/>
		<c:if test="${lst.tradeTp eq 'S'}" ><c:set value="${((lst.tradeSellPrc - lst.tradeBuyPrc) * lst.tradeEa) - lst.fee - lst.tax}" var="profit"/></c:if>

		<tr>
			<td>${trade.totalRow - ((trade.page-1) * trade.rowSize + status.index) }</td>
			<td>${lst.tradeTp eq "B" ? "매수" : "<span style='color:blue'>매도</span>" }</td>
			<td><a href="javascript:view(${lst.tradeSrl })">${lst.stockNm }</a></td>
			<td><fmt:formatNumber value="${lst.tradeBuyPrc}" pattern="#,###.##"/></td>
			<td><fmt:formatNumber value="${lst.tradeSellPrc}" pattern="#,###.##"/></td>
			<td><fmt:formatNumber value="${lst.tradeEa}" pattern="#,###.##"/></td>
			<td><fmt:formatNumber value="${lst.tradeTp eq 'B' ? lst.tradeBuyPrc * lst.tradeEa : lst.tradeSellPrc * lst.tradeEa}" pattern="#,###.##"/></td>
			<td style="${profit < 0 ? 'color:red':''}"><fmt:formatNumber value="${profit}" pattern="#,###.##"/></td>
			<td style="${profit < 0 ? 'color:red':''}">
				<c:choose>
					<c:when test="${lst.tradeTp eq 'B'}">0</c:when>
					<c:otherwise><fmt:formatNumber value="${((((lst.tradeSellPrc*lst.tradeEa) - lst.fee - lst.tax) / (lst.tradeBuyPrc * lst.tradeEa )) -1) * 100}" pattern="#,###.##"/>%</c:otherwise>
				</c:choose>
			</td>
			<%-- <td><fmt:formatDate value="${lst.regDt}" pattern="yyyy/MM/dd HH:mm" /></td> --%>
			<td>${lst.tradeDt }</td>
			<td style="${lst.point < 0 ? 'color:red':''}">${lst.point }</td>
		</tr>
		</c:forEach>
		<c:if test="${empty list }">
		<tr>
			<td colspan="9" style="height:100px;text-align:center;vertical-align: middle">자료가 없습니다.</td>
		</tr>
		</c:if>
		
		</tbody>
		</table> 
		
	</div>
	
	<div class="box-footer">
		<button type="button" class="btn btn-primary btn-xm" onClick="save()">등록</button>
	</div>
   
    <c:if test="${trade.totalRow > 0}">
	<div style="text-align:center">
	<nav aria-label="Page navigation">
	  <ul class="pagination">
	 	 <tx:nav totalPage="${trade.totalPage }" page="${trade.page }"/>
	  </ul>
	</nav>
	</div>
	</c:if>
		
</div>	

</section>

<script>

$(document).ready(function() {
	

	com.calendar("searchStartDt");
	com.calendar("searchEndDt");
	
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
});

function search(){
	$("#page").val(1)
	if($("#stockNm").val() == "") $("#stockCd").val("");
	
	document.frm.action = "/stock/trade/list";
	document.frm.submit();
}

function goPage(p){
	$("#page").val(p)
	document.frm.action = "/stock/trade/list";
	document.frm.submit();
}

function save(){
	document.location.href = "/stock/trade/insert/form";
}

function view(srl){
	document.frm.action = "/stock/trade/view";
	$("#tradeSrl").val(srl);
	document.frm.submit();
}

</script>