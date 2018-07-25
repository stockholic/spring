<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cTag" uri="/WEB-INF/tld/tx.tld" %>

<div class="row">
	<div class="col-md-12">
	
	<div class="mail-env">
	
	<!-- Mail Body -->
	<div class="mail-body">
		
		<div class="mail-header">
			<!-- title -->
			<div class="mail-title">
			${boxName }
			</div>
			
			<!-- links -->
			<div class="mail-links">
			
				<a href="#" class="btn btn-default">
					<i class="entypo-print"></i>
				</a>
			
				<a href="#" class="btn btn-default">
					<i class="entypo-trash"></i>
				</a>
				
				<a class="btn btn-primary btn-icon">
					Reply
					<i class="entypo-reply"></i>
				</a>
				
			</div>
			
		</div>
		
		
		<div class="mail-info">
			
			<div class="mail-sender dropdown">
				From :  
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">
					<span><c:out value="${message.sender}" escapeXml="true"/></span>
				</a>
				
				<ul class="dropdown-menu"> 
					<li>
						<a href="#">
							<i class="entypo-user"></i>
							주소록저장
						</a>
					</li>
					<li class="divider"></li>
					<li>
						<a href="#">
							<i class="entypo-star"></i>
							중요표시
						</a>
					</li>
					<li>
						<a href="#">
							<i class="entypo-reply"></i>
							답장
						</a>
					</li>
					<li>
						<a href="#">
							<i class="entypo-right"></i>
							전달
						</a>
					</li>
				</ul>
				
			</div>
			
			<div class="mail-date">
				<fmt:formatDate value="${message.sentDate}" pattern="yyyy/MM/dd HH:mm" />
			</div>
			
			
			
		</div>
		
		<div class="mail-subject">
		${message.subject}
		</div>
		
		<div class="mail-text">
		${message.content.contentString}
		</div>
		
		<c:if  test="${!empty message.content.attachment}">
		<div class="mail-attachments">
			<h4>
				<i class="entypo-attach"></i> 첨부파일 <span>(${fn:length(message.content.attachment)})</span>
			</h4>
			<ul>
			<c:forEach var="list" items="${message.content.attachment}">
				<li>
					<a href="attachment?box=${email.box}&messageId=${email.messageId}&fileName=${list.fileName}" class="name">
						${list.fileName}&nbsp;
						<span><cTag:fileSize size="${list.fileSize}"/></span>
					</a>
					<!-- 
					<div class="links">
						<a href="#">View</a> - 
						<a href="#">Download</a>
					</div>
					 -->
				</li>
			</c:forEach>
			</ul>
		</div>
		</c:if>
		
		<div class="mail-reply">
		<!-- 
			<div class="fake-form">
				<div>
					<a href="mailbox-compose.html">Reply</a> or <a href="mailbox-compose.html">Forward</a> this message...
				</div>
			</div>
		 -->
		</div>
		
	</div>
		
		
	
	</div>
	
	</div>
</div>


