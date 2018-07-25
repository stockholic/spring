<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
  	<title><tiles:getAsString name="title"/></title>
  	<!-- Tell the browser to be responsive to screen width -->
  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
  	
	<tiles:insertDefinition name="adminCss" />
	<script src="/static/assets/jquery/jquery.min.js"></script>
</head>

<body>

  

<tiles:insertAttribute name="body" />


<tiles:insertDefinition name="adminScript"/>


</body>
</html>


