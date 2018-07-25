<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<section class="content-header">
  <h1>
    <spring:message code="main.title"/>
    <small></small>  
  </h1>
  <ol class="breadcrumb">
    <li><a href="#"> Level</a></li>
    <li class="active">Here</li>
  </ol>
</section>


<section class="content container-fluid">

<spring:message code="main.content"/>


<p style="margin-top:30px"> 
	<a href="/?lang=ko_KR">[<spring:message code="main.lang.ko"/>]</a>
	<a href="/?lang=en_US">[<spring:message code="main.lang.en"/>]</a> 
</p>

</section>