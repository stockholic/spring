<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags"	prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta name="author" content="" />
	<title><tiles:getAsString name="title"/></title>
    <tiles:insertDefinition name="adminCss" />
    <tiles:insertDefinition name="adminScript"/>
    
</head>
<body>

<!-- Menu -->
<tiles:insertAttribute name="menu" />

<!-- Main Content -->
<div class="container-fluid">
	<div class="side-body">
	
	
	
<tiles:insertAttribute name="body" />	
	
	

	</div>
</div>

</body>


</html>	




