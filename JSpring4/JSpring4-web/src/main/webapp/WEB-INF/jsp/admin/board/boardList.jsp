 <%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
 <script src="/static/js/ngGrid-core.js"></script>
<script>
	grid.init({
		selectUrl : "/admin/board/listJson.do",
		deleteUrl : "/admin/board/delete.do", 
		method : "POST"
	});
</script>

 <div class="x_title">
    <h2>게시판</h2>
    <div class="clearfix"></div>
 </div>

 <div class="panel panel-default">
     <div class="panel-body">
         <div class="table-responsive">
         
		<div ng-app="appModule">
         	<div ng-controller="ngCtr">
         	   
	         	<h5>Total : {{dataList.length}}</h5>
	         	
	         	<div style="height:500px">
	             <table class="table table-striped table-hover" >
	             	<colgroup>
					<col width="5%" />
					<col width="10%" />
					<col />
					<col width="10%" />
					<col width="20%" />
				  </colgroup>
	                 <thead>
	                 	<tr class="bg-header">
	                      <th class="text-center"><input type="checkbox"  id="checkAll"></th>
	                      <th>번호</th>
	                      <th>제목</th>
	                      <th>이름</th>
	                      <th class="text-center">날자</th> 
	                    </tr>
	                 </thead>
	                 <tbody>
	                      <tr ng-repeat="list in dataList">
	                         <td class="text-center"><input type="checkbox"></td>
	                         <td>{{ list.no }}</td>
	                         <td>{{ list.title }}</td>
	                         <td>{{ list.userNm }}</td>
	                         <td class="text-center">{{ list.regDate | date:'y/MM/dd h:mm:ss a' }}</td>
	                     </tr>
	                  </tbody>
	              </table>
				</div>
			
				<button type="button" class="btn btn-round btn-primary" ng-click="getData()"><i class="fa fa-edit fa-fw"></i> 글쓰기</button>
				<button type="button" class="btn btn-round btn-primary" ng-click="deleteRow()"><i class="fa fa-edit fa-fw"></i> 삭제</button>

			</div>
		</div>
             
		</div>
      </div>
  </div>
	


