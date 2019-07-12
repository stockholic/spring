<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tx" uri="http://www.pethub.kr/tags"%>

<section class="content-header">
  <h1>
    전체수익
  </h1>
  <ol class="breadcrumb">
    <li> 주식관리</li>
    <li> 수익현황</li>
    <li class="active">전체수익</li>
  </ol>
</section>


<section class="content container-fluid">

<div class="box box-default">
    <div class="box-body">
    
    	<table class="table table-hover table-top">
		  <colgroup>
		    <col width="33%">
		    <col width="33%">
		    <col width="33%">
		  </colgroup>
		<thead>
		<tr>
			<th style="text-align: right;">총손익</th>
			<th style="text-align: right;">수수료+세금</th>
			<th style="text-align: right;">총순손익</th>
		</tr>
		</thead>
		<tbody>
		<c:set value="${profit.profit - profit.tax - profit.fee}" var="netProfit"/>
		<tr style="font-size: 18px;font-weight: :bold;text-align: right">
			<td style="${profit.profit < 0 ? 'color:red':''}"><fmt:formatNumber value="${profit.profit}" pattern="#,###.##"/></td>
			<td><fmt:formatNumber value="${profit.tax + profit.fee}" pattern="#,###.##"/></td>
			<td style="${netProfit < 0 ? 'color:red':''}"><fmt:formatNumber value="${netProfit}" pattern="#,###.##"/></td>
		</tr>
		</tbody>
		</table>
		
    
    </div>
	
</div>
 

</section>

