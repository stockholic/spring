<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="/static/css/mail.css">


<script src="/static/js/mail.js"></script>

<div class="content-title"> ${boxName }</div>
<input type="hidden" value="${email.box }" id="inbox">        
<div ng-app="appModule"  ng-controller="ngMailSubject">

     <!--  ---------------------------------------------- 메일목록 ----------------------------------------  -->
    <div id="inboxSubject" >

      <div id="inbox_list_wrap">

        <div class="inbox_header">
            <div class="pull-right message-menu3"> 
                Total : <span id="total">0</span> [<span id="page">0</span> / <span id="totalPage">0</span>]
                <button type="button" id="angle-left" class="btn btn-default btn-sm" ng-click="mailList( box , prePage)" ><i  class="fa fa-angle-left"></i></button>
				<button type="button" id="angle-right" class="btn btn-default btn-sm" ng-click="mailList( box , nextPage)"><i class="fa fa-angle-right"></i></button>
            </div>
            
            <div class="dropdown message-menu1">
            	<i class="glyphicon glyphicon-ok unchecked" ng-click="checkEmail($event)"></i>&nbsp;&nbsp;
		        <a href="#" data-toggle="dropdown" class="dropdown-toggle">메세지작업 <b class="caret"></b></a>
		        <ul class="dropdown-menu">
		            <li style="display:${email.box eq 'INBOX' ? 'none' : ''}"><a href="#" onClick='moveFolder("INBOX")'><i class="entypo-inbox"></i> 받은편지함 이동</a></li>
					<li style="display:${email.box eq 'INBOX.Sent' ? 'none' : ''}"><a href="#" onClick='moveFolder("INBOX.Sent")'><i class="entypo-upload"></i> 보낸편지함 이동</a></li>
					<li style="display:${email.box eq 'INBOX.Drafts' ? 'none' : ''}"><a href="#" onClick='moveFolder("INBOX.Drafts")'><i class="entypo-box"></i> 임시저장함 이동</a></li>
					<li style="display:${email.box eq 'INBOX.Trash' ? 'none' : ''}"><a href="#" onClick='moveFolder("INBOX.Trash")'><i class="entypo-trash"></i> 지운편지함 이동</a></li>
					<li style="display:${email.box eq 'INBOX.Spam' ? 'none' : ''}"><a href="#" onClick='moveFolder("INBOX.Spam")'><i class="entypo-mute"></i> 스팸편지함 이동</a></li>
	                <li role="separator" class="divider"></li>
	                <li><a href="#" >읽음</a></li>
	                <li><a href="#" >읽지않음</a></li>
		        </ul>
		    </div>
		    
		    <div class="message-menu2">
	            <a href="#" ng-click="mailList(box, '1')"><i class="fa fa-refresh"></i></a>&nbsp;
	            <a href="#"><i class="glyphicon glyphicon-trash"></i></a>
            </div>
        </div>
 

    	<div  id="inbox_list_subject">
    		<div ng-if="total ==0 ">
    		메세지가 없습니다.
    		</div>

	        <div class="list-group-item" ng-repeat="lst in list">
	        
	        <div class="inbox_list {{lst.unread ? 'unread' : ''}}" data-uid="{{lst.uid}}">
	            <div class="inbox_list_name" >
	            	<i class="glyphicon glyphicon-ok unchecked" ng-click="checkEmail($event)"></i><a href="#"  ng-click="mailView(box ,lst.messageId)">{{lst.senderName}}</a> <i ng-if="lst.multipart == true" class="entypo-attach"></i>
	            </div>
	            <div class="inbox_list_date">{{lst.sentDateString}}</div>
	            <div class="inbox_list_title">
		            <i class="fa {{lst.flaged ? 'fa-star stared' : 'fa-star-o'}}" ng-click="updateStarStatus($event,box)"></i> <a href="#"  ng-click="mailView(box ,lst.messageId)">{{lst.subject}}</a> 
	         	</div>
	         </div>
         
       	 	</div>
        
      	</div>



        </div>  
        <div id="inboxDragbar"></div>
    </div>


    <!--  ---------------------------------------------- 메일내용 ----------------------------------------  -->
   <div class="inbox_content_header" ng-if="view.uid">
        <div class="pull-right">
           
             <div class="btn-group">
                <button data-toggle="dropdown" class="btn btn-default btn-sm dropdown-toggle" type="button" aria-expanded="false">이동 <span class="caret"></span></button>
                <ul role="menu" class="dropdown-menu">
                   	<li style="display:${email.box eq 'INBOX' ? 'none' : ''}"><a href="#" onClick='moveFolder("INBOX")'><i class="entypo-inbox"></i> 받은편지함</a></li>
					<li style="display:${email.box eq 'INBOX.Sent' ? 'none' : ''}"><a href="#" onClick='moveFolder("INBOX.Sent")'><i class="entypo-upload"></i> 보낸편지함</a></li>
					<li style="display:${email.box eq 'INBOX.Drafts' ? 'none' : ''}"><a href="#" onClick='moveFolder("INBOX.Drafts")'><i class="entypo-box"></i> 임시저장함</a></li>
					<li style="display:${email.box eq 'INBOX.Trash' ? 'none' : ''}"><a href="#" onClick='moveFolder("INBOX.Trash")'><i class="entypo-trash"></i> 지운편지함</a></li>
					<li style="display:${email.box eq 'INBOX.Spam' ? 'none' : ''}"><a href="#" onClick='moveFolder("INBOX.Spam")'><i class="entypo-mute"></i> 스팸편지함</a></li>
                </ul>
            </div>

            <button type="button" class="btn btn-default btn-sm"><i class="entypo-reply"></i> 답장</button>
            <button type="button" class="btn btn-default btn-sm"><i class="glyphicon glyphicon-trash"></i> 삭제</button>

        </div>

    </div>

    <div id="inboxContent">
    
	    <div ng-if="view.uid">	
	    	<!-- 
	    	<div>messageNumber: {{view.messageNumber}}</div>
	       	<div>uid: {{view.uid}}</div>
	       	<div>sender: {{view.sender}}</div>
	       	<div>subject: {{view.subject}}</div>
	       	<div>sentDate: {{view.sentDate}}</div>
	       	<div>unread: {{view.unread}}</div>
	       	<div>answered: {{view.answered}}</div>
	       	<div>deleted: {{view.deleted}}</div>
	       	<div>flaged: {{view.flaged}}</div>
	       	<div>multipart: {{view.multipart}}</div>
	       	<div>contentType: {{view.contentType}}</div>
	       	<div>content.attachment : {{view.content.attachment}}</div>
	       	<div>content.html : {{view.content.html}}</div>
	       	<div>toRecipients: {{view.toRecipients}}</div>
	       	<div>ccRecipients: {{view.ccRecipients}}</div>
	       	<div>bccRecipients: {{view.bccRecipients}}</div>
	       	<div>senderName: {{view.senderName}} </div>
	       	<div>messageId: {{view.messageId}}</div>
	       	<div>sentDateString: {{view.sentDateString}}</div>
	       
	    	<p>------------------------------------------------------------------------------------------------</p>
	     	-->
	    	<div>
	    		<div class="inbox_content_sender">{{view.sender}}</div>
	    		<div class="inbox_content_date">{{view.sentDateString}}</div>
	    	</div>
	    	<div class="inbox_content_title">{{view.subject}}</div>
	    	
	    	<div id="inbox_content"></div>
	    	
	    	<div class="inbox_attachment" ng-if="view.content.attachment.length > 0">
				<i class="entypo-attach"></i> 첨부파일 <span>({{view.content.attachment.length}})</span>
			</div>
				
			 <div class="inbox_attachment_list" ng-repeat="attachment in view.content.attachment track by $index">
			 	<a href="#" ng-click="fileDownload(box, view.messageId, $index+1)">{{attachment.fileName}} ({{fileSize(attachment.fileSize)}})</a>
			 </div>
	
	    </div>
    </div>
    
 </div>        

 
 