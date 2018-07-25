<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/static/css/mail-maker.css">
<!-- DatePicker -->
<link href="/static/assets/datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet">
<script src="/static/js/angular.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="/static/js/bootstrap.min.js"></script>
<script src="/static/js/mail-maker.js"></script>
<script src="/static/js/mail-maker-img.js"></script>


 <!-- DatePicker -->
 <script src="/static/assets/datepicker/js/bootstrap-datepicker.min.js"></script>
 <script src="/static/assets/datepicker/locales/bootstrap-datepicker.kr.min.js"></script>


<body> 

<div id="contentWrap">

	<div id="left-form">
		<!-- -------------- 메일폼 -------------- -->
		<table class="table">
		<colgroup>
		    <col width="120" />
		    <col />
	  	</colgroup>
	  	<thead>
	  	<tr>
	  		<th colspan="2"><i class="entypo-layout"></i>메일폼</th>
	  	</tr>
	  	</thead>
	  	<tbody>
		<tr>
			<th>메일명</th>
			<td><input type="text" class="form-control"name="mailTitle"  value=""></td> 
		</tr>
		<tr>
			<th>발송예정일</th>
			<td>
				 <div class="input-group">
					<div class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></div>
				    <input type="text" class="form-control"  id="sendDate" name="sendDate" value="" style="width:100px" placeholder="날짜입력">
			    </div> 
			</td>
		</tr>
		<tr>
			<th>자동업데이트 시간</th>
			<td class="form-inline">
				<div class="input-group">
					<div class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></div>
				    <input type="text" class="form-control"  id="sendDate" name="sendDate" value="" style="width:100px" placeholder="날짜입력">
			    </div> 
			    
		        <select class="form-control" > 
			    <option>01</option>
			    <option>02</option>
			    <option>03</option>
			    </select>
			</td>
		</tr>
		<tr>
			<th>링크열기 옵션</th>
			<td>
			 <select class="form-control"  style="width:120px"> 
			    <option></option>
			    </select>
			</td>
		</tr>
		<tr>
			<th>메일폼 상단 히든 텍스트</th>
			<td><input type="text" class="form-control" name="mailTitle"  value=""></td>
		</tr>
		</tbody>
		</table> 
		
		<!-- -------------- JP / LN 입력 -------------- -->
		<table class="table">
		<colgroup>
		    <col width="20%" />
		    <col width="40%" />
		    <col width="40%" />
	  	</colgroup>
	  	<thead>
	  	<tr>
	  		<th colspan="2"><i class="entypo-layout"></i>JP / LN 입력</th>
	  	</tr>
	  	</thead>
	  	<tbody>
		<tr>
			<th>전체</th>
			<td class="form-inline">
				<div class="input-group">
		      	<input type="text" class="form-control input-sm" >
		      	<span class="input-group-btn">
		        	<button class="send btn btn-default btn-sm" type="button">JP적용</button>
		      	</span>
		      	</div> 
			</td>
			<td>
				<div class="input-group">
		      	<input type="text" class="form-control input-sm" >
		      	<span class="input-group-btn">
		        	<button class="send btn btn-default btn-sm" type="button">LN적용</button>
		      	</span>
		    	</div>
			</td>
		</tr>
		<tr>
			<th>GNB</th>
			<td class="form-inline">
				<div class="input-group">
		      	<input type="text" class="form-control input-sm" >
		      	<span class="input-group-btn">
		        	<button class="send btn btn-default btn-sm" type="button">JP적용</button>
		      	</span>
		      	</div> 
			</td>
			<td>
				<div class="input-group">
		      	<input type="text" class="form-control input-sm" >
		      	<span class="input-group-btn">
		        	<button class="send btn btn-default btn-sm" type="button">LN적용</button>
		      	</span>
		    	</div>
			</td>
		</tr>
		<tr>
			<th>이미지</th>
			<td class="form-inline">
				<div class="input-group">
		      	<input type="text" class="form-control input-sm" >
		      	<span class="input-group-btn">
		        	<button class="send btn btn-default btn-sm" type="button">JP적용</button>
		      	</span>
		      	</div> 
			</td>
			<td>
				<div class="input-group">
		      	<input type="text" class="form-control input-sm" >
		      	<span class="input-group-btn">
		        	<button class="send btn btn-default btn-sm" type="button">LN적용</button>
		      	</span>
		    	</div>
			</td>
		</tr>
		<tr>
			<th>이미지 맵</th>
			<td class="form-inline">
				<div class="input-group">
		      	<input type="text" class="form-control input-sm" >
		      	<span class="input-group-btn">
		        	<button class="send btn btn-default btn-sm" type="button">JP적용</button>
		      	</span>
		      	</div> 
			</td>
			<td>
				<div class="input-group">
		      	<input type="text" class="form-control input-sm" >
		      	<span class="input-group-btn">
		        	<button class="send btn btn-default btn-sm" type="button">LN적용</button>
		      	</span>
		    	</div>
			</td>
		</tr>
		<tr> 
			<th>딜</th>
			<td class="form-inline">
				<div class="input-group">
		      	<input type="text" class="form-control input-sm" >
		      	<span class="input-group-btn">
		        	<button class="send btn btn-default btn-sm" type="button">JP적용</button>
		      	</span>
		      	</div> 
			</td>
			<td>
				<div class="input-group">
		      	<input type="text" class="form-control input-sm" >
		      	<span class="input-group-btn">
		        	<button class="send btn btn-default btn-sm" type="button">LN적용</button>
		      	</span>
		    	</div>
			</td>
		</tr>
		</tbody>
		</table>
		
		<!-- -------------- 로그정보 -------------- -->
		<table class="table">
	  	<thead>
	  	<tr>
	  		<th><i class="entypo-layout"></i>로그정보</th>
	  	</tr>
	  	</thead>
	  	<tbody>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td></td>
		</tr>
		</tbody>
		</table> 
		
	</div>
	
	
	<div id="right-form">
	
		<div class="top-menu">
			<nav class="navbar navbar-inverse"> 
				<div class="container-fluid"> 
					<div style="float:left">
						<a class="navbar-brand  nav-gnb">GNB</a>
						<a class="navbar-brand nav-title">타이틀</a>
						<a class="navbar-brand nav-deal-group">딜그룹</a>
						<a class="navbar-brand nav-image1">이미지1단</a>
						<a class="navbar-brand nav-image2">이미지2단</a>
						<a class="navbar-brand nav-image-map">이미지맵</a>
						<a class="navbar-brand nav-footer">풋터</a>
						<a class="navbar-brand nav-separation">간격</a>
					</div>
					
					<div style="float:right">
						<a class="navbar-brand nav-preview">미리보기</a>
						<a class="navbar-brand nav-save">저장</a>
					</div>
				</div>
			</nav> 
		</div>
		
		<div class="mail-form">	
			<ul id="sortable">
			<c:forEach var="list" items="${dataList}" varStatus="status">
			 	${list}
			</c:forEach>
			</ul>
		</div>
	
	</div>

</div>

<div id="formSource" style="display: none">
<c:forEach var="list" items="${formList}" varStatus="status">
 	${list}
</c:forEach>
</div>

 
<div class="modal fade" tabindex="-1" role="dialog" id="fileManager" ng-app="appModule"  ng-controller="ngImgeDiv" >
  <div class="modal-dialog" role="document"  >
    <div class="modal-content" style="width:600px;height:700px">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">이미지 선택</h4>
      </div>
      <div class="modal-body" > 
      
	    <div class="input-group">
	      <span class="input-group-addon"><i class="glyphicon glyphicon-picture"></i></span>
	      <input type="text" class="form-control input-sm" disabled placeholder="이미지 선택">
	      <span class="input-group-btn">
	        <button class="browse btn btn-primary btn-sm" type="button"><i class="glyphicon glyphicon-search"></i> 찾기</button>
	        <button class="send btn btn-primary btn-sm" type="button">등록</button>
	      </span>
	    </div>
    	<input type="file" name="imgFile" class="imgFile" >

      	<div class="fileList">
      
	        <table class="table table-hover">
	  		<colgroup>
		    <col width="30" />
		    <col />
		    <col width="40" />
		    <col width="40" />
	  		</colgroup>
	  		<tbody>
	  		
	  		<tr class=""  ng-repeat="lst in list" >
		        <td><input type="checkbox"></td>
	  			<td>
	  				<img ng-src="{{lst.imgUrl}}"  class="imgBanner" style="display: none">
	  				<img ng-src="{{lst.imgUrl}}" class="imgList">
	  			</td>
	  			<td><button type="button" class="btn btn-primary btn-xs"  onClick="img.add(this)">선택</button></td>
	  			<td><button type="button" class="btn btn-warning btn-xs" >삭제</button></td>
       	 	</tr>
       	 	
	  		</tbody>
			</table>

      </div>
      <div class="modal-footer">
      	<div class="deleteDiv">
      		<input type="checkbox">
      		<button type="button" class="btn btn-default btn-sm deleteBtn" >삭제</button>
      	</div>
      	<div class="closeDiv">
      	  <button type="button" class="btn btn-default btn-sm closeBtn" >닫기</button>
      	</div>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</div>

</body>

