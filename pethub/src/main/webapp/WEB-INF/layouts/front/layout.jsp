<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
  	<title><tiles:getAsString name="title"/></title>
  
  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
  	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  	<link rel="shortcut icon" href="/static/favicon.ico" type="image/x-icon" />
  	
	<tiles:insertDefinition name="frontCss" />
	
	<script src="/static/assets/jquery/jquery.min.js"></script>
	<tiles:insertDefinition name="frontScript"/>
	
</head>

<body>

<div class="container ">

    
    
   <div >
     <tiles:insertAttribute name="body" />
   </div>
    
    
  
</div>


</body>
</html>


