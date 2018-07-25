<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" import="java.io.*"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>::: 500 :::</title>


<style type="text/css"> 
/* default */
* {
    font-family: "NanumGothic";
    font-style: normal;
    font-weight: 400;
    src: url("/fonts/NanumGothic-Regular.eot?#iefix") format("embedded-opentype"), url("/fonts/NanumGothic-Regular.woff2") format("woff2"), url("/fonts/NanumGothic-Regular.woff") format("woff"), url("/fonts/NanumGothic-Regular.ttf") format("truetype");
}

#wrapper { 
	font-size:9pt;
	width: 400px; 
	height: 200px; 
	position: absolute; 
	top: 40%; 
	left: 50%; 
	margin: -100px 0 0 -200px;
}

.code {
	font-size:20pt;
	color:#5980D6;
	font-weight:bold;
	text-align:center;
}
.codeNm {
	font-size:11pt;
	color:#5980D6;
	font-weight:bold;
}

.comment {
	text-align:center;
	line-height:20px;
	font-size:10pt;
}
</style> 


</head>


<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	
<div id="wrapper">

    <table  border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
    	<td width="80 "height="40" class="code">500</td>
    	<td width=220 height=40 class="codeNm" > 
    		시스템 에러
    	</td>
    </tr>
    <tr>
    	<td height='4' bgcolor='#5980D6'></td>
    	<td height='4' bgcolor='#AFAFAF'></td>
    </tr>
    
    <tr>
    	<td height='100' colspan='2'  class="comment">
    		시스템 에러가 발생하였습니다.<br>
    		불편을 드려 죄송합니다.
    	</td>
    </tr>
    <tr><td height=2 colspan=2 bgcolor=#D0D0D0></td></tr>
    </table>
	
	 <br><br>
	
</div>

<div style="font-size: 9pt;display: none;">
<% 
if (exception != null) {
	exception.printStackTrace(new PrintWriter(out));
}
%>
</div>

</body>
</html>