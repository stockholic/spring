<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- uncomment code for absolute positioning tweek see top comment in css -->
<!-- <div class="absolute-wrapper"> </div> -->
<div class="side-menu">
    
    <nav class="navbar navbar-default" role="navigation">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
        <div class="brand-wrapper">
            <!-- Hamburger -->
            <button type="button" class="navbar-toggle">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

            <!-- Brand -->
            <div class="brand-name-wrapper">
            	<div style="float: left"><a class="navbar-brand" href="/">Work Group</a></div>
            	<div style="float: right"><a class="navbar-logout" href="/logout"><i class="entypo-logout right"></i> 로그아웃</a></div>
            </div>
    
        </div>

    </div>

    <!-- Main Menu -->
	<div class="side-menu-container">
        <ul class="nav navbar-nav">
        
             <li class="panel panel-default" id="dropdown" class="dropdown">
                <a data-toggle="collapse" href="#dropdown-bbs">
                    <i class="fa fa-edit fa-fw"></i> 게시판<span class="caret"></span>
                </a>

                <div id="dropdown-bbs" class="panel-collapse collapse">
                    <div class="panel-body">
                        <ul class="nav navbar-nav">
                            <li class="${menu0101}" ><a href="#" >공지사항</a></li>
                            <li class="${menu0102}"><a href="#" >자료실</a></li>
                            
                        </ul>
                    </div>
                </div>
            </li>

            <li class="panel panel-default" id="dropdown" class="dropdown">
                <a data-toggle="collapse" href="#dropdown-mail" >
                    <i class="entypo-mail"></i> 편지함<span class="caret"></span>
                </a>

                <div id="dropdown-mail" class="panel-collapse collapse ${menu0200}">
                    <div class="panel-body">
                        <ul class="nav navbar-nav">
                            <li class="${menu0201}" ><a href="/mail/inbox?box=INBOX" ><i class="entypo-inbox"></i> 받은편지함</a></li>
                            <li class="${menu0202}"><a href="/mail/inbox?box=INBOX.Sent" ><i class="entypo-upload"></i> 보낸편지함</a></li>
                            <li class="${menu0203}"><a href="/mail/inbox?box=INBOX.Drafts" ><i class="entypo-box"></i> 임시편지함</a></li>
                            <li class="${menu0204}"><a href="/mail/inbox?box=INBOX.Trash" ><i class="entypo-trash"></i> 지운편지함</a></li>
                            <li class="${menu0205}"><a href="/mail/inbox?box=INBOX.Spam" ><i class="entypo-mute"></i> 스팸편지함</a></li>
                            
                            <li class="${menu0206}"><a href="/mail/compose"><i class="entypo-pencil"></i> 메일작성</a></li>
                            <li class="${menu0207}"><a href="#"><i class="entypo-cog"></i> 환경설정</a></li>
                            
                        </ul>
                    </div>
                </div>
            </li>


            <li><a href="http://www.google.com"><span class="glyphicon glyphicon-cloud"></span> Link</a></li>
            <li><a href="#"><span class="glyphicon glyphicon-signal"></span> Link</a></li>

        </ul>
    </div>
    
	</nav>
    
</div>


    