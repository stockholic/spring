<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
    <head>
    	<meta name="viewport" content="width=device-width, initial-scale=1">
        <title><tiles:getAsString name="title"/></title>
		<link rel="shortcut icon" href="/static/favicon.ico" type="image/x-icon" />
        <tiles:insertDefinition name="frontCss" />
        <tiles:insertDefinition name="frontScript"/>
    </head>
    <body>
		<tiles:insertAttribute name="header" />
		<tiles:insertAttribute name="body" />
		<tiles:insertAttribute name="footer"/>
    </body>

</html>
