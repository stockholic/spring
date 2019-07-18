<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tx" uri="http://www.pethub.kr/tags"%>


<div class="row search-wrap">
	<div class="col-3 logo">
		<a href= "/">PetHub.kr</a>
	</div>
	
  	<div class="col-6 text-center">
  	<div class="search-input">
		<div class="form-group has-search">
		<span class="fa fa-search form-control-feedback"></span>
    	<input type="text" id="searchString" value="${siteLinkData.dataTitle }" maxlength="20" class="form-control" placeholder="Search">
  		</div>
  	</div>
  	</div>
  	
	<div class="col-3 text-right last-update">
		<div>last updated</div>
		<div>${updatedTime }</div>
	</div>
</div>

<div id="list_wrap">
	<ul class="list-unstyled">
	  
	  <c:forEach var="lst" items="${list }" varStatus="status">
	  <li class="media my-4" v-for="lst in dataList">
	  	<c:if test="${!empty lst.dataImg}">
	    <img class="mr-3" src="${lst.dataImg }" style="max-width: 90px; min-width: 90px">
	    </c:if>
	    
	    <div class="media-body">
	      <h5 class="mt-0 mb-1">
	      	<a href="${lst.dataLink}" target="_blank"><span class="site-name">[${lst.siteNm}]</span> ${lst.dataTitle}</a>
		    <span class='reg-date'><fmt:formatDate value="${lst.regDt}" pattern="yyyy.M.dd" /></span>
	      </h5>
	      ${lst.dataContent}  
	    </div>
	  </li>
	  <hr/>
	  </c:forEach>

	</ul>
	
	<div class="row-count">${siteLinkData.totalRow } [ <fmt:formatNumber value="${siteLinkData.page }" pattern="#,###" /> / ${siteLinkData.totalPage } ]</div>
</div>




<c:if test="${siteLinkData.totalRow > 0}">
<ul class="pagination justify-content-center">
<tx:nav totalPage="${siteLinkData.totalPage }" page="${siteLinkData.page}" pageCount="5" searchString="${siteLinkData.dataTitle }"/>
</ul>
</c:if>


<script>

$(document).ready(function() {
	
	//검색
	$("#searchString").keyup(function(event) {
		if (event.keyCode == 13) {
	    	if( $(this).val().trim().length  == 0){
	    		document.location.href = "/search/list"
	    	}else if( $(this).val().trim().length  > 1){
	         document.location.href = "/search/list/1/" + $(this).val();
	    	}
	    }
	});

	
});


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

