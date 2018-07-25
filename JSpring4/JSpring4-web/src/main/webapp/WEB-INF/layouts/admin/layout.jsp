<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags"	prefix="spring" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="info" property="principal" />
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><tiles:getAsString name="title"/></title>
     <tiles:insertDefinition name="css" />
     <tiles:insertDefinition name="script"/>
  </head>

  <body class="nav-md">
    <div class="container body">
      <div class="main_container">
        <div class="col-md-3 left_col menu_fixed">
          <div class="left_col scroll-view">
            <div class="navbar nav_title" style="border: 0;">
              <a href="index.html" class="site_title">  <span class="fa fa-cog "> JSpring4</span></a>
            </div>

            <div class="clearfix"></div>
            
              <!-- menu profile quick info -->
            <div class="profile"> 
              <div class="profile_info">
              
                <sec:authorize access="hasAnyRole('ROLE_ADM', 'ROLE_USR')">
                	<div><span>${info.user.userNm}(${info.user.userId})</span></div>
                	<div><span class="glyphicon glyphicon-off"></span> <a href="/logout"><span> 로그아웃</span></a></div>
                  </sec:authorize>
                  <sec:authorize access="!hasRole('ROLE_USR')">
                 	 <div><span class="fa fa-sign-out fa-fw"></span> <a href="#"><span> 로그인</span></a></div>
		    	 </sec:authorize>
		    	 
              </div>
            </div>
            <!-- /menu profile quick info -->
            
  			<div class="clearfix"></div>
  			
            <!-- sidebar menu -->
            <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
              <div class="menu_section"> 
              
                <ul class="nav side-menu">
                
                 <li><a href="/admin/board/list"><i class="fa fa-edit"></i> 게시판</a></li>
                 
                  <li><a><i class="fa fa-wrench"></i> 유틸 <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="/zTree/tree">zTree</a></li>
                      <li><a href="/static/zGrid/grid.html" target="_blank">zGrid</a></li>
                      <li> <a href="/admin/ui/form">UI Components</a></li>
                      <li> <a href="/admin/ui/fileUpload">File Upload</a></li>
                    </ul>
                  </li>
                  
                    <li><a><i class="fa fa-bolt"></i> 소켓 <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="/admin/socket/chat">채팅<span class="label label-primary pull-right">new</span></a></li>
                    </ul>
                  </li>
                  
                    <li><a><i class="fa fa-database"></i> ElasticSearch <span class="fa fa-chevron-down"></span><span class="label label-primary pull-right">new</span></a>
                    <ul class="nav child_menu">
                      <li><a href="/admin/elastic/info">Info</a></li>
                      <li><a href="/admin/elastic/createForm">Index/Type</a></li>
                      <li><a href="/admin/elastic/searchList">Search</a></li>
                    </ul>
                  </li>
                
                </ul>
                
              </div>
            
            </div>
            <!-- /sidebar menu -->

            <!-- /menu footer buttons -->
            <!-- 
            <div class="sidebar-footer hidden-small">
              <a data-toggle="tooltip" data-placement="top" title="Settings">
                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="FullScreen">
                <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="Lock">
                <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="Logout">
                <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
              </a>
            </div>
             -->
            <!-- /menu footer buttons -->
          </div>
        </div>

        <!-- top navigation -->
        <div class="top_nav">
          <div class="nav_menu">
            <nav class="" role="navigation">
              <div class="nav toggle">
                <a id="menu_toggle"><i class="fa fa-arrows-h"></i></a>
              </div>

              <ul class="nav navbar-nav navbar-right">
                <li><a href="javascript:;" > <i class="fa fa-envelope-o"></i>  <span> 메일</span></a></li>
                <li><a href="javascript:;" > <i class="fa fa-comments-o"></i>  <span> 채팅</span></a></li>
                <li><a href="javascript:;" > <i class="fa fa-gears"></i>  <span> 젠킨스</span></a></li>
                <li><a href="javascript:;" > <i class="fa fa-github"></i>  <span> 프로젝트</span></a></li>
              </ul>
            </nav>
          </div>
        </div>
        <!-- /top navigation --> 

      <!-- page content -->
        <div class="right_col" role="main">
<tiles:insertAttribute name="body" />
         </div>
        <!-- /page content -->
        
        
         <!-- footer content -->
         <!-- 
        <footer>
          <div class="text-center" style="color:#7F7F7F;"> 
            Copyright <i class="fa fa-copyright"></i> 2016 2run18.com All rights reserved.
          </div>
          <div class="clearfix"></div>
        </footer>
         -->
        <!-- /footer content -->
        

      </div>
    </div>
  
    <!-- Custom Theme Scripts -->
    <script src="/static/js/custom.min.js"></script>
    
  </body>
</html>