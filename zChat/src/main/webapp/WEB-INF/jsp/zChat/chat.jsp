 <%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
 
<div id="chatHeader">

	 <div class="input-group" id="changeNickField">
    	  <input type="text" class="form-control" maxlength="10" id="inputNick" name="inputNick" placeholder="닉네임변경">
      	<span class="input-group-btn">
       	 <button class="btn btn-default btn-xs" type="button" id="change">변경</button>
       	 <button class="btn btn-default btn-xs" type="button" id="changeNickClose">닫기</button>
      	</span>
    </div>

	<div class="dropdown userDropDown" style="display: none">
		  <button class="btn btn-default dropdown-toggle btn-xs" type="button" id="userDropList" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
		    현재접속인원 <span id="userCount">0</span>명 <span class="caret"></span>
		  </button>
		  <ul class="dropdown-menu" aria-labelledby="userDropList" id="userList"></ul>
	</div>
	
	<div id="nickIcon" style="display: none">
 		<span class="glyphicon glyphicon-user text-primary ponter" id="openNickField"></span>
 	</div>
	
	<div id="chatTitle">
		<h5 id="channelInfo">${channelNm}</h5>
	</div>
	
	<div id="chatMenu" style="display: none">
		<!-- <span id="lock" class="glyphicon glyphicon-lock text-primary ponter" style="display: none;"></span> -->
		<span id="trash" class="glyphicon glyphicon-trash text-primary ponter"></span>
		<span id="chatLine" class="glyphicon glyphicon-off text-primary ponter"></span>
		<span id="home" class="glyphicon glyphicon-home text-primary ponter"></span>
	</div>
	
</div>
	 
<div id="chatBody">
	<div class="form-control" id="showMsg"></div>
</div>
	
<div id="chatFooter" style="display: none">
	<input type="text" class="form-control" id="msg" maxlength="200">
</div>
