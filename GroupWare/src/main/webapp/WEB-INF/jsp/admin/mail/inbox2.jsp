<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="/static/assets/js/jquery-1.11.0.min.js"></script>
<div class="row">
	<div class="col-md-12">
	
	<div class="mail-env">
	
		<!-- Mail Body -->
		<div class="mail-body">
			
			<div class="mail-header">
				<!-- title -->
				<h3 class="mail-title">
					${boxName }
					<span class="count">${folderInfo.unreadMessageCount } / ${folderInfo.messageCount }</span>
				</h3>
				
				<!-- search -->
				<form method="get" role="form" class="mail-search">
					<input type="hidden" id="box"  name="box" value=${email.box }>
					<div class="input-group" style="width:250px;float:right">
						<input type="text" class="form-control" name="s"  placeholder="Search for mail..." />
						
						<div class="input-group-addon">
							<i class="entypo-search"></i>
						</div>
					</div>
				</form>
			</div>
			
			<!-- mail table -->
			<table class="table mail-table">
				<!-- mail table header -->
				<thead>
					<tr>
						<th width="5%">
							<div class="checkbox checkbox-replace">
								<input type="checkbox"  class = "allCheck"/>
							</div>
						</th>
						<th colspan="4">
							
							<div class="dropdown" style="float:left">
								<button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">메세지이동&nbsp;
								<span class="caret"></span></button>
								<ul class="dropdown-menu">
									<li style="display:${email.box eq 'INBOX' ? 'none' : ''}"><a href="#" onClick='moveFolder("INBOX")'><i class="entypo-inbox"></i> 받은편지함</a></li>
									<li style="display:${email.box eq 'INBOX.Sent' ? 'none' : ''}"><a href="#" onClick='moveFolder("INBOX.Sent")'><i class="entypo-upload"></i> 보낸편지함</a></li>
									<li style="display:${email.box eq 'INBOX.Drafts' ? 'none' : ''}"><a href="#" onClick='moveFolder("INBOX.Drafts")'><i class="entypo-box"></i> 임시저장함</a></li>
									<li style="display:${email.box eq 'INBOX.Trash' ? 'none' : ''}"><a href="#" onClick='moveFolder("INBOX.Trash")'><i class="entypo-trash"></i> 지운편지함</a></li>
									<li style="display:${email.box eq 'INBOX.Spam' ? 'none' : ''}"><a href="#" onClick='moveFolder("INBOX.Spam")'><i class="entypo-mute"></i> 스팸편지함</a></li>
								</ul>
							</div>
							&nbsp;&nbsp;<button type="button" class="btn btn-default" onClick='moveFolder("INBOX.Trash")'>삭제</button>
							<div class="mail-pagination" >
								<strong>${email.page }</strong> <span>of ${email.totalPage }</span>
								
								<div class="btn-group">
								<c:choose>
									<c:when test="${email.page  > 1}"><a href="inbox?box=${email.box }&page=${email.page -1 }" class="btn btn-sm btn-white"><i class="entypo-left-open"></i></a></c:when>
									<c:otherwise><span class="btn btn-sm btn-white"><i class="entypo-left-open"></i></span></c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${email.page < email.totalPage}"><a href="inbox?box=${email.box }&page=${email.page +1 }" class="btn btn-sm btn-white"><i class="entypo-right-open"></i></a></c:when>
									<c:otherwise><span class="btn btn-sm btn-white"><i class="entypo-right-open"></i></span></c:otherwise>
								</c:choose>
								</div>
							</div>
						</th>
					</tr>
				</thead>
				
				<!-- email list -->
				<tbody id="box_body">
				
					<c:if test="${empty messages}">
					<tr>
						<td colspan='5' style="height:100px;text-align: center;">메일이 없습니다.</td>
					</tr>
					</c:if>
				
					<c:forEach var="list" items="${messages}" varStatus="status">
					<tr class="${list.unread == true ? 'unread' : ''}"><!-- new email class: unread -->
						<td>
							<div class="checkbox checkbox-replace">
								<input type="checkbox"  value="${list.messageId}"/>
							</div>
						</td>
						<td class="col-name">
							<a href="#"  onClick="startMark('${list.messageId}',this)" class="star ${list.flaged ? 'stared' : '' }">
								<i class="entypo-star"></i>
							</a>
							<a href="message?box=${email.box}&messageId=${list.messageId}" class="col-name">${list.senderName }</a>
						</td>
						<td class="col-subject">
							<a href="message?box=${email.box}&messageId=${list.messageId}">${list.subject }</a>
						</td>
						<td class="col-options">
							<c:if test="${list.multipart }"><i class="entypo-attach"></i></c:if>
						</td>
						<td class="col-time"><fmt:formatDate value="${list.sentDate}" pattern="yyyy/MM/dd HH:mm" /></td>
					</tr>
					</c:forEach>
					
				</tbody>
				
				<!-- mail table footer -->
				<tfoot>
					<tr>
						<th width="5%">
							<div class="checkbox checkbox-replace">
								<input type="checkbox" class="allCheck"/>
							</div>
						</th>
						<th colspan="4">
							
							<div class="mail-pagination" >
								<strong>${email.page }</strong> <span>of ${email.totalPage }</span>
								
								<c:choose>
									<c:when test="${email.page  > 1}"><a href="inbox?box=${email.box }&page=${email.page -1 }" class="btn btn-sm btn-white"><i class="entypo-left-open"></i></a></c:when>
									<c:otherwise><span class="btn btn-sm btn-white"><i class="entypo-left-open"></i></span></c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${email.page < email.totalPage}"><a href="inbox?box=${email.box }&page=${email.page +1 }" class="btn btn-sm btn-white"><i class="entypo-right-open"></i></a></c:when>
									<c:otherwise><span class="btn btn-sm btn-white"><i class="entypo-right-open"></i></span></c:otherwise>
								</c:choose>
							</div>
						</th>
					</tr>
				</tfoot>
			</table>
		</div>
	
	</div>
	
</div>
</div>
<script>
$(".allCheck").click(function(){
	$(".mail-body").find("input:checkbox").prop("checked",$(this).is(":checked"));
	if($(this).is(":checked")){
		$(".mail-body").find(".checkbox").addClass("checked")
	}else{
		$(".mail-body").find(".checkbox").removeClass("checked")
	}
});

$("#box_body").find("input:checkbox").click(function(){
	var totalBox = $("#box_body").find("input:checkbox").length;
	var checkedBox = $("#box_body").find("input:checkbox:checked").length;
	if(totalBox==checkedBox){
		$(".allCheck").prop("checked","checked");
		$(".allCheck").parent().parent().addClass("checked");
	}else{
		$(".allCheck").prop("checked","");
		$(".allCheck").parent().parent().removeClass("checked");
	}
});

function moveFolder(toBox){
	var uuids=[];
	$("#box_body").find("input:checkbox:checked").each(function(){
		uuids.push($(this).val());
	});
	
	if(uuids.length == 0) return;
	
	var box = $("#box").val();
	
	var params ={
		fromBox: box,
		toBox : toBox,
		uuids : uuids
	}
	
	if(toBox==box && box=="INBOX.Trash"){
		$.ajax({
			url: '/mail/deleteMailMessages',
			method : 'POST',
			data : $.param(params,true),
			success: function () {
				alert("메세지가 삭제 되었습니다.");
				document.location.reload();
			},
			error:function(request,status,error){}
		});
	}else{
		$.ajax({
			url: '/mail/moveMailMessages',
			method : 'POST',
			data : $.param(params,true),
			success: function () {
				alert("메세지가 이동 되었습니다.");
				document.location.reload();
			},
			error:function(request,status,error){}
		});
	}
	
}

function startMark(uuid,obj){
	
	var flag = "FLAGGED";
	if( $(obj).hasClass("stared") == true){
		flag = "UNFLAGGED";
		$(obj).removeClass("stared");
	}else{
		$(obj).addClass("stared");
	}
	
	var uuids=[];
	uuids.push(uuid);
	var params ={
		inbox : $("#box").val(),
		flag : flag,
		uuids : uuids
	}
	
	$.ajax({
		url: '/mail/changeMailStatus',
		method : 'POST',
		data : $.param(params,true),
		success: function () {},
		error:function(request,status,error){}
	});
	
}

$( document ).ready(function() {
});
</script>
