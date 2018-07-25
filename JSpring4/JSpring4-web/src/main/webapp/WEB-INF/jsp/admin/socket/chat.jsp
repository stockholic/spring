<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<script src="/static/js/sockjs.min.js"></script>
<script src="/static/js/chat.js"></script>

<style>
	.msg_left{
		width:80%;
		float: left
	}
	.msg_right {
		width:80%;
		float:right;
		text-align:right;
	}
</style>

 <script>

$( document ).ready(function() {

	chat.connect();
	
	$('#msg').keydown(function(e) {
		if(e.keyCode == 13)	chat.sendMsg()
	}).focus();
	
	//채팅해제
	$("#btn1").clickToggle(function() {
		 if(chat.isConnect) {
			 chat.disconnect();
			 $(this).html('채팅접속');
		 }
	
	//채팅연결
	}, function() {
		 if(!chat.isConnect) {
			 chat.connect({
				url : "chat"
			});
			$(this).html('채팅해제');
			$("#msg").focus();
		 }
	});
	
	//내용해제
	$("#btn2").click(function(){
		$("#showMsg div").remove();
	});
	
	//채널변경
	$(".channel").click(function(){
		chat.changeChannel( $(this).data("channelId") )
	});
});

</script>

 <div class="x_title">
     <h2>채팅<small>WEB SOCKET 을 이용한 채팅</small></h2>
    <div class="clearfix"></div>
 </div>
 

 <div style="height:40px">
 	<div style="float:left">
	 	<button type="button" id="btn1" class="btn btn-round btn-default" >채팅해제</button>
	 	<button type="button" id="btn2" class="btn btn-round btn-default" >내용삭제</button>
 	</div>
 	<div style="float:left;padding:7px 0px 0px 10px" id="channelInfo"></div>
 	<div style="float:right">
	 	<button type="button" class="btn btn-round btn-default channel" data-channel-id="100001">Channel 1</button>
	 	<button type="button" class="btn btn-round btn-default channel" data-channel-id="100002">Channel 2</button>
	 	<button type="button" class="btn btn-round btn-default channel" data-channel-id="100003">Channel 3</button>
 	</div>
</div>

<div style="height:400px;">
	<div class="form-control" id="showMsg"  style="line-height:22px;float:left;width:80%;height:390px;overflow: auto;"></div>
	<div style="float:left;width:1%"></div>
	<div class="form-control"  id="userList" style="line-height:22px;float:right;width:19%;height:390px;overflow: auto;"></div>
</div>

<input type="text" style="width:100%;"class="form-control"  id="msg" >
 
 
