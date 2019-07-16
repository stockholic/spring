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
	  
<<<<<<< HEAD
	  <c:forEach var="lst" items="${list }" varStatus="status">
	  <li class="media my-4" v-for="lst in dataList">
	  	<c:if test="${!empty lst.dataImg}">
	    <img class="mr-3" src="${lst.dataImg }" style="max-width: 90px; min-width: 90px">
	    </c:if>
	    
=======
	  <li class="media my-4">
	    <img class="mr-3" src="https://image-notepet.akamaized.net/resize/620x-/seimage/20190222%2F88df4645d7d2a4d2ed42628d30cd83d0.jpg" style="max-width: 80px; min-width: 80px">
>>>>>>> branch 'master' of https://github.com/stockholic/spring.git
	    <div class="media-body">
	      <h5 class="mt-0 mb-1">
	      	<a href="${lst.dataLink}" class="text-info" target="_blank"><span>[${lst.siteNm}]</span>${lst.dataTitle}</a>
		    <span class='reg-date'><fmt:formatDate value="${lst.regDt}" pattern="yyyy.MM.dd" /></span>
	      </h5>
	      ${lst.dataContent}  
	    </div>
	  </li>
<<<<<<< HEAD
	  <hr/>
	  </c:forEach>

=======
	  <hr />
	  
	  <li class="media my-4">
	    <div class="media-body">
	      <h5 class="mt-0 mb-1">List-based media object</h5>
	      Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.
	    </div>
	  </li> 
	  <hr />
	  
	    <li class="media my-4">
	    <img class="mr-3" src="https://image-notepet.akamaized.net/resize/620x-/seimage/20190222%2F88df4645d7d2a4d2ed42628d30cd83d0.jpg" style="max-width: 80px; min-width: 80px">
	    <div class="media-body">
	      <h5 class="mt-0 mb-1">
	      	<a href="" class="text-info"><span>[도그짱]</span> List-based media object</a>
	      </h5>
	      가나다라 마마사가나다라 마마사 가나다라 마마사 가나다라 마마사 가나다라 마마사 가나다라 마마사 가나다라 마마사 가나다라 마마사 가나다라 마마사  
	    </div>
	  </li>
	  <hr />
	  
>>>>>>> branch 'master' of https://github.com/stockholic/spring.git
	</ul>
</div>


<<<<<<< HEAD
==> count ${dataCount }

=======
>>>>>>> branch 'master' of https://github.com/stockholic/spring.git
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

<script>


$(document).ready(function() {
	

});

</script>

