$.fn.clickToggle = function(func1, func2) {
    var funcs = [func1, func2];
    this.data('toggleclicked', 0);
    this.click(function() {
        var data = $(this).data();
        var tc = data.toggleclicked;
        $.proxy(funcs[tc], this)();
        data.toggleclicked = (tc + 1) % 2;
    });
    return this;
}; 

var chat = {
	
	webSocket : null,
	isConnect : false,
	channelId : null,
	userNick : "",
	userList : [],
	
	/**
	 * 소켓연결 
	 * @param data
	 */
	connect : function(){
		
		var host = "http://"+window.location.hostname;
		var port = window.location.port
		
		chat.webSocket = new SockJS(host + ":" + port+ "/zzz");
		
		//웹 소켓이 연결되었을 때 호출되는 이벤트
		chat.webSocket.onopen = function(message){
		    console.log("Server connect");
		    chat.isConnect = true;
		};
		//웹 소켓이 닫혔을 때 호출되는 이벤트
		chat.webSocket.onclose = function(message){
			console.log("Server Disconnect");
			chat.isConnect = false;
			
			$("#showMsg").append("<div class='msg_left disconnect'>연결이 종료되었습니다.</div>");
			chat.setScrollbar("showMsg");
		};
		//웹 소켓이 에러가 났을 때 호출되는 이벤트
		chat.webSocket.onerror = function(message){
			console.log("Server Error");
		};
		//웹 소켓에서 메시지가 날라왔을 때 호출되는 이벤트
		chat.webSocket.onmessage = function(message){
			var obj = JSON.parse(message.data);
			console.log(obj);
			switch (obj.cmd) {
				case "CHANNEL_IN"					: chat.showChannelIn(obj);		break;	//채널 입장
				case "CHANNEL_INFO"					: chat.setChannelInfo(obj);		break;	//채널 정보
				case "CHANNEL_OUT"					: chat.showChannelOut(obj);	break;	//채널 퇴장
				case "USER_MSG"						: chat.showMessage(obj);		break;	//재널 메세지
				case "USER_LIST"							: chat.showUserList(obj);		break;	//유저 목록
				case "NICK_CHANGE_FINISH"		: chat.nickChangeFinish(obj);	break;	//유저 목록
				case "CHANNEL_CONNECT_FINISH"	: chat.changeConnectFinish();	break;	//채널 연결완효
				case "CREATE_PASSWORD_FINISH"	: chat.createPasswordFinish(obj);	break;	//PWD생성 완료 
				case "IS_PASSWORD"						: chat.isPassword(obj);	break;	//비번방 유무
				case "FALSE_PASSWORD"					: chat.falsePassword(obj);	break;	//틀린 비번
			}
		};
		
	/**
	 * 소켓해제
	 */
	},disconnect : function(){
		if(this.isConnect) this.webSocket.close();
		
	/**
	 * 닉 변경
	 */
	},changeNick : function(){
		if(this.isConnect) {
			if( $("#inputNick").val().trim()=="" ) return;
			var data = {
				cmd : "NICK_CHANGE",
				changeNick: $("#inputNick").val(),
			};
			this.webSocket.send( JSON.stringify(data)) ;
		}
		this.userNick = $("#inputNick").val();
		
		$("#changeNickField").animate({
			left : -245
		});
	
	/**
	 * 닉 변경 완료 메세지
	 */
	},nickChangeFinish : function(obj){
		$("#showMsg").append("<div class='msg_left join'>" +  obj.oldNick + " 님이 "+obj.changeNick+"(으)로 닉네임을 변경하였습니다.</div>");
		this.setScrollbar("showMsg");
		
	/**
	 * 메세지 전송
	 */	
	},sendMsg : function(){
		if(this.isConnect) {
			if(!$("#msg").val().trim()) return;
			var data = {
				cmd : "USER_MSG",
				msg : $("#msg").val(),
			};
			this.webSocket.send( JSON.stringify(data)) ;
			$("#msg").val("");
		}
	
	/**
	 * 채널생성 요청
	 */
	},createChannel : function(channelNm){
		var data = {
				cmd : "CHANNEL_CREATE",
				channelNm : channelNm,
		};
		this.webSocket.send( JSON.stringify(data)) ;
		
	/**
	 * 채널연결(대기실) 완료 / 채널 변경 요청
	 */
	},changeConnectFinish : function(){
		$("#showMsg div").remove();
		this.createChannel( $("#channelInfo").text() );
		
	/**
	 * 비공개방에 패스워드 전송
	 */
	},sendPwd : function(pwd,channelNm){
		var data = {
				cmd : "PASSWORD_CREATE",
				pwd : pwd,
				channelNm : channelNm,
		};
		this.webSocket.send( JSON.stringify(data)) ;
		
	/**
	 * 비공개방 생성 후 처리
	 */
	},createPasswordFinish : function(){
		$("#lock").hide();
	
	/**
	 * 비번방에 접근
	 */
	},isPassword : function(obj){
		this.popup();
	
	/**
	 * 틀린 비번
	 */
	},falsePassword : function(obj){
		this.popup();
		$(".modal-body .popMsg").text("비빌번호가 일치하지 않습니다.").css("color","#FF710F");
		
	/**
	 * 채널입장 메세지
	 */
	},showChannelIn : function(obj){
		$("#showMsg").append("<div class='msg_left join'>" +  obj.userNick + " 님이 입장하셨습니다.</div>");
		this.showMenu();
		this.setScrollbar("showMsg");
		
	/**
	 * 현재 채널정보
	 */
	},setChannelInfo : function(obj){
		this.userNick = obj.userNick;
		$("#channelInfo").text(obj.channelNm);
		$("#showMsg div").remove();
	
	/**
	* 채널 퇴장 메세지
	*/	
	},showChannelOut : function(obj){
		$("#showMsg").append("<div class='msg_left out'>" +  obj.userNick + " 님이 퇴장하셨습니다.</div>");
		this.setScrollbar("showMsg");
	
	/**
	* 채널 메세지 내용 
	*/
	},showMessage : function(obj){
		
		var templet = "<div class=\"" + (obj.userNick==this.userNick ? 'msg_right' :'msg_left') + "\">" +
								"<div class='user'>"+ obj.userNick +" <span class='time'>"+ this.getDate() +"</span></div><div class=\"" + (obj.userNick==this.userNick ? 'me_bubble me' :'you_bubble you') + "\">" + obj.msg  + "</div>" +
							"</div>";
		
		$("#showMsg").append(templet);
		this.setScrollbar("showMsg"); 
		
	/**
	* 채널 유저 목록 
	*/ 
	},showUserList : function(obj){
		this.userList = [];
		$("#userCount").text(obj.userList.length);
		for( user in obj.userList){
			this.userList.push(obj.userList[user]);
		}
		
	/**
	* 스크롤바 조정 
	*/
	},setScrollbar : function(id){
		$("#"+id).scrollTop($("#"+id)[0].scrollHeight);
		
	},getDate : function(){
		
		var currentTime = new Date()
		var hours = currentTime.getHours()
		var minutes = currentTime.getMinutes()
		var suffix = "";
		if (minutes < 10){
			minutes = "0" + minutes
		}
		if (hours >= 12) {
			suffix = "PM ";
			hours = hours - 12;
		}else{
			suffix = "AM ";
		}
		if (hours == 0) hours = 12;
		
		return suffix + hours + ":" + minutes;
		
	},popup:function(){
		 bootbox.dialog({
			 title : "비공개방 비밀번호",
			 size : 'small',
			 message : 	'<div><input id="pwd" name="pwd" type="text" class="form-control input-md" maxlength="8"></div>'+
			 				'<div class="popMsg">8자리이내로 입력해주세요</div>',
			 buttons : {
				 success : {
					 label : "전송",
					 className : "btn-default",
					 callback : function(){
						var pwd = $('#pwd').val();
                        var channelInfo = $("#channelInfo").text() 
                        chat.sendPwd(pwd, channelInfo);
					 }
				 }
			 }
		 });
	 },showMenu : function(){
		 $(".userDropDown").show();
		 $("#nickIcon").show();
		 $("#chatMenu").show();
		 $("#chatFooter").show();
	 }
 }
 
 $( document ).ready(function() {
	 chat.connect();
	 
	$('#msg').keydown(function(e) {
		if(e.keyCode == 13)	chat.sendMsg()
	}).focus();
	
	//연결아이콘
	$("#chatLine").popover({
		 content : '해제',
		 trigger : 'hover',
		 placement : 'bottom'
	});
	
	//채팅해제
	$("#chatLine").clickToggle(function() {
		 if(chat.isConnect) {
			 chat.disconnect();
			 $(this).removeClass("glyphicon-off").addClass("glyphicon-sort");
			 $('#chatLine').attr('data-content', '연결').setContent();
		 }
	//채팅연결
	}, function() {
		 if(!chat.isConnect) {
			 chat.connect();
			 $(this).removeClass("glyphicon-sort").addClass("glyphicon-off");
			 $('#chatLine').attr('data-content', '해제').setContent();
		 }
	});
	
	//내용비우기
	$("#trash").click(function(){
		$("#showMsg div").remove();
	}).popover({
		 content : '방청소',
		 trigger : 'hover',
		 placement : 'bottom'
	});

	//비공개방 생성
	$("#lock").click(function(){
		chat.popup();
	}).popover({
		 content : '비공개방',
		 trigger : 'hover',
		 placement : 'bottom'
	});
	
	//홈으로
	$("#home").click(function(){
		window.open('/ ', '_blank'); 
	}).popover({
		 content : '홈',
		 trigger : 'hover',
		 placement : 'bottom'
	});;
	
	//유저리스트
	$("#userDropList").click(function(){
		$("#userList li").remove();
		for( n in chat.userList ){
			$("#userList").append("<li>" + chat.userList[n] + "</li>");
		}
	});
	//닉 변경
	$("#change").click(function(){
		chat.changeNick();
	});
	
	//닉네임 변경 오픈
	$("#openNickField").click(function(){
		$("#changeNickField").animate({
			left : 245
		});
	}).popover({
		 content : '닉네임변경',
		 trigger : 'hover',
		 placement : 'bottom'
	});
	
	//닉네임 변경 닫기
	$("#changeNickClose").click(function(){
		$("#changeNickField").animate({
			left : -245
		});
	});
	
});
 
 