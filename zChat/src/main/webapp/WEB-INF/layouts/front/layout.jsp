<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
    <head>
    	<meta name="viewport" content="width=device-width, initial-scale=1">
    	<meta name="keywords" content="zChat,채팅,웹채팅,iframe 채팅,무료채팅,심플,간단,비회원가입,무료채팅,채팅사이트,만남채팅,대화방,chat,chatting" /> 
		<meta name='description' content='채팅방 생성이 자유로운 웹기반 채팅서비스입니다' />
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
