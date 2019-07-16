<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tx" uri="http://www.pethub.kr/tags"%>


<div class="search_wrap">
  
  <!-- Actual search box -->
  <div class="form-group has-search">
    <span class="fa fa-search form-control-feedback"></span>
    <input type="text" class="form-control" placeholder="Search">
  </div>
  
  <!-- Another variation with a button -->
 <!--  
  <div class="input-group">
    <input type="text" class="form-control" placeholder="Search this blog">
    <div class="input-group-append">
      <button class="btn btn-secondary" type="button">
        <i class="fa fa-search"></i>
      </button>
    </div>
  </div>
   -->
   
</div>


<div  id="list_wrap">
	<ul class="list-unstyled">
	  
	  <c:forEach var="lst" items="${list }" varStatus="status">
	  <li class="media my-4" v-for="lst in dataList">
	  	<c:if test="${!empty lst.dataImg}">
	    <img class="mr-3" src="${lst.dataImg }" style="max-width: 90px; min-width: 90px">
	    </c:if>
	    
	    <div class="media-body">
	      <h5 class="mt-0 mb-1">
	      	<a href="${lst.dataLink}" class="text-info" target="_blank"><span>[${lst.siteNm}]</span>${lst.dataTitle}</a>
		    <span class='reg-date'><fmt:formatDate value="${lst.regDt}" pattern="yyyy.MM.dd" /></span>
	      </h5>
	      ${lst.dataContent}  
	    </div>
	  </li>
	  <hr/>
	  </c:forEach>

	</ul>
</div>


==> count ${dataCount }

<ul class="pagination justify-content-center">
<tx:nav totalPage="${dataCount }" page="1"/>
</ul>

<script>

/*
var view;

$(document).ready(function() {
	
	view = new Vue({
		el: '#list_wrap',
		data: model
	});
	
});

var model = {
	dataList : [ 
		 { siteName : "도그짱 1", dataTitle : "가나다라 마마사 가나다라 마마사 가나다라 마마사 가나다라 마마사 가나다라 마마사 1", dataContent : "" }	
		,{ siteName : "도그짱 2", dataTitle : "가나다라 마마사 가나다라 마마사 가나다라 마마사 가나다라 마마사 가나다라 마마사 2", dataContent : "" }	
		,{ siteName : "도그짱 3", dataTitle : "가나다라 마마사 가나다라 마마사 가나다라 마마사 가나다라 마마사 가나다라 마마사 3", dataContent : "" }	
	]
}; 
*/

</script>

