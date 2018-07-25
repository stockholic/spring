<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>

<style>
.center {
    margin: auto;
    width: 60%;
    border: 3px solid #73AD21;
    padding: 10px;
}
</style>

<body>


<div class="container">

<div class="row">
    <div class="col-xs-12 col-sm-6 col-md-4 col-sm-offset-3 col-md-offset-4">
		
	<div style="margin-top:50px;height:50px">
	<c:if test="${!empty param.err}">
		<p> 아이디와 비밀번호를 확인해 주세요</p>
	</c:if> 
	</div>
	<form method="post" role="form" id="form_login" action='/loginProc' method='POST'>
		
		<div class="form-group">
			
			<div class="input-group">
				<div class="input-group-addon">
					<i class="entypo-user"></i>
				</div>
				
				<input type="text" class="form-control" name="userNm" id="userNm" value="jspark" placeholder="아이디"  autofocus autocomplete="off" />
			</div>
			
		</div>
		
		<div class="form-group">
			<div class="input-group">
				<div class="input-group-addon">
					<i class="entypo-key"></i>
				</div>
				
				<input type="password" class="form-control" name="password" id="password" value="qkrtjqkddhkTsi"  placeholder="패스워드" autocomplete="off" />
			</div>
		</div>
		
		<div class="form-group">
			<button type="submit" class="btn btn-primary btn-block btn-login">
				<i class="entypo-login"></i>
				Login In
			</button>
		</div>
		
		<!-- 
		<div class="form-group" >
			<div>  
				<label class="checkbox" style="text-align:left;">
					<input type="checkbox"  name="remember-me"> 로그인유지
				</label>
			</div>
		</div>
		 -->
	
	</form>
	
	</div>
</div>
</div>	
			


</body>