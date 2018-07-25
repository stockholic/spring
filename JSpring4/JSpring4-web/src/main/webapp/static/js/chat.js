 var chat = {
	
	webSocket : null,
	isConnect : false,
	channelId : null,
	userNick : "",
	
	/**
	 * 소켓연결
	 * @param data
	 */
	connect : function(data){
		
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
			
			$("#showMsg").append("<div style='color:#990000' class='msg_right>연결종료</div>");
			chat.setScrollbar("showMsg");
		};
		//웹 소켓이 에러가 났을 때 호출되는 이벤트
		chat.webSocket.onerror = function(message){
			console.log("Server Error");
		};
		//웹 소켓에서 메시지가 날라왔을 때 호출되는 이벤트
		chat.webSocket.onmessage = function(message){
			var obj = JSON.parse(message.data);
			switch (obj.cmd) {
				case "CHANNEL_IN"					: chat.showChannelIn(obj);		break;	//채널 입장
				case "CHANNEL_INFO"					: chat.setChannelInfo(obj);		break;	//채널 정보
				case "CHANNEL_OUT"					: chat.showChannelOut(obj);	break;	//채널 퇴장
				case "USER_MSG"						: chat.showMessage(obj);		break;	//재널 메세지
				case "USER_LIST"							: chat.showUserList(obj);		break;	//유저 목록
				case "CHANNEL_CHANGE_FINISH"	: chat.changeChangeFinish();	break;	//채널 변경 완료
			}
		};
		
	/**
	 * 소켓해제
	 */
	},disconnect : function(){
		if(this.isConnect) this.webSocket.close();
	
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
	 * 채널변경 요청
	 */
	},changeChannel : function(channelId){
		var data = {
			cmd : "CHANNEL_CHANGE",
			channelId : channelId
		};
		this.webSocket.send( JSON.stringify(data)) ;
		
	/**
	 * 채널변경 완료
	 */
	},changeChangeFinish : function(){
		$("#showMsg div").remove();
	
	/**
	 * 채널입장 메세지
	 */
	},showChannelIn : function(obj){
		$("#showMsg").append("<div style='color:#3333ff' class='msg_left'>" +  obj.userNick + " 님 왔슈 !!</div>");
		this.setScrollbar("showMsg");
	
	/**
	 * 현재 채널정보
	 */
	},setChannelInfo : function(obj){
		this.userNick = obj.userNick;
		$("#channelInfo").html("<span style='color:#FF6600'>::: " +  obj.channelNm + " :::</span>");
		$("#showMsg div").remove();
		
		$(".channel").each(function(){
			if($(this).data("channelId") == obj.channelId){
				$(this).attr("disabled",true);
			}else{
				$(this).attr("disabled",false);
			}
		});
	
	/**
	* 채널 퇴장 메세지
	*/	
	},showChannelOut : function(obj){
		$("#showMsg").append("<div style='color:#990000' class='msg_left'>" +  obj.userNick + " 님 나갔슈 !!</div>");
		this.setScrollbar("showMsg");
	
	/**
	* 채널 메세지 내용 
	*/
	},showMessage : function(obj){
		$("#showMsg").append("<div class=\"" + (obj.userNick==this.userNick ? 'msg_right' :'msg_left') + "\">" + obj.userNick + " : " +  obj.msg + "</div>");
		this.setScrollbar("showMsg"); 
		
	/**
	* 채널 유저 목록 
	*/
	},showUserList : function(obj){
		$("#userList .user").remove();
		for( user in obj.userList){
			$("#userList").append("<div class='user' " + (obj.userList[user]==this.userNick?'style=\"color:#3333ff\"':'')  + ">" + obj.userList[user] +  "</div>");
		}
		this.setScrollbar("userList");
	/**
	* 스크롤바 조정 
	*/
	},setScrollbar : function(id){
		$("#"+id).scrollTop($("#"+id)[0].scrollHeight);
	}
	
 }