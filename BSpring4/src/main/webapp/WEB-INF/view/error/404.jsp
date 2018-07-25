 <%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>::: 404 :::</title>
	
<style type="text/css"> 

body {
	padding : 0;
	margin : 0;
	font-family: "맑은 고딕"
}

table{text-align:center}

#wrapper { 
	width: 400px; 
	height: 200px; 
	position: absolute; 
	top: 40%; 
	left: 55%; 
	margin: -100px 0 0 -200px;
} 

.code {
	font-size:28px;
	color:#5980D6;
	font-weight:bold;
	text-align:center;
}
.codeNm {
	font-size:14px;
	color:#5980D6;
	font-weight:bold;
}

.comment {
	text-align:center;
	line-height:20px
}
</style> 


</head>


<body>
	
<div id="wrapper">

    <table>
    <tr>
    	<td width="80 "height="40" class="code">404</td>
    	<td width=220 height=40 class="codeNm" > 
    		<spring:message code="page.error.404"/>
    	</td>
    </tr>
    <tr>
    	<td height='4' bgcolor='#5980D6'></td>
    	<td height='4' bgcolor='#AFAFAF'></td>
    </tr>
    <tr>
    	<td height='100' colspan='2'  class="comment"></td>
    </tr>
    </table>
	
</div>

</body>
</html>