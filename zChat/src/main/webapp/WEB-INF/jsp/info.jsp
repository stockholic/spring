 <%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>


 <div class="col-lg-12" id="info">
     <h3 class="page-header"> zChat</h3>
	 	
	<div id="showMsg" style="height:550px">
		<div class="msg_left"><div class="user">손님</div><div class="you_bubble you">여기 뭐하는 곳인가요 ?</div></div>
		<div class="msg_right"><div class="user">주인장</div><div class="me_bubble me">채팅하는 곳이예요</div></div>
		<div class="msg_left"><div class="user">손님</div><div class="you_bubble you">어떻게 사용하나요 ?</div></div>
		<div class="msg_right"><div class="user">주인장</div>
			<div class="me_bubble me">
				브라우저창에 URL을 입력하세요. 예을 들어 <b>http://www.zchat.kr/chat/</b><span style="color:blue;font-weight: bold">demo</span><br><br>
				<span style="color:blue;font-weight: bold">demo</span> 부분에 원하는 방이름을 입력하시면 됩니다.(4자이상 30자 미만)<br>
				생성 후 친구들한테 주소 알려 주면 되겠죠
			</div>
		</div>
		<div class="msg_left"><div class="user">손님</div><div class="you_bubble you">그렇군요!! 그런데 제 홈페이지에 채팅방을 만들고 싶은데 어떻게 하면 되죠 ?</div></div>
		<div class="msg_right"><div class="user">주인장</div>
			<div class="me_bubble me">
				<span style="color:blue;font-weight: bold">iframe</span> 태그를 사용하시면 됩니다. <br><br>
				<b>&lt;iframe src="http://www.zchat.kr/chat/demo" style="border-style: none;width: 600px; height: 400px;"&gt;&lt;/iframe&gt;</b><br><br>
				width, height 로 크기 조절하시면 되겠죠 ? 스타일로 조정하세요
			</div>
		</div>
	</div>
	
	<div id="demo" style="padding-top:20px;clear: both;"> 
	 <h5 class="page-header"><span class="glyphicon glyphicon-th-large"></span> DEMO (iframe 사용)</h5>
		<iframe src="http://www.zchat.kr/chat/demo" style="border-style: none;width: 100%; height: 400px;">NO</iframe>
	</div>
	
 </div>
 
 