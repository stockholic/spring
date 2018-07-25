<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tx" uri="http://www.stockholic.com/tags"%>

<section class="content-header">
  <h1>
    년도별수익
  </h1>
  <ol class="breadcrumb">
    <li> 주식관리</li>
    <li> 수익현황</li>
    <li class="active">년도별수익</li>
  </ol>
</section>


<section class="content container-fluid">

<div class="box box-default">
    <div class="box-body">
    
    <table class="table table-hover table-top">
		  <colgroup>
		    <col width="20%">
		    <col width="20%">
		    <col width="20%">
		    <col width="20%">
		    <col width="20%">
		    <col width="20%">
		  </colgroup>
		<thead>
		<tr>
			<th style="text-align: right;">년도</th>
			<th style="text-align: right;">종목</th>
			<th style="text-align: right;">총손익</th>
			<th style="text-align: right;">수수료+세금</th>
			<th style="text-align: right;">총순손익</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="lst" items="${profit }" varStatus="status">
		<c:set value="${lst.profit - lst.tax - lst.fee}" var="netProfit"/>
		<tr style="text-align: right">
			<td>${lst.tradeYear }</td>
			<td>${lst.stockNm }</td>
			<td style="${lst.profit < 0 ? 'color:red':''}"><fmt:formatNumber value="${lst.profit}" pattern="#,###.##"/></td>
			<td><fmt:formatNumber value="${lst.tax + lst.fee}" pattern="#,###.##"/></td>
			<td style="${netProfit < 0 ? 'color:red':''}"><fmt:formatNumber value="${netProfit}" pattern="#,###.##"/></td>
		</tr>
		</c:forEach>
		</tbody>
		</table>
    
    </div>
	
	<div class="box-footer">
	 </div>
	    
</div>
 

</section>

