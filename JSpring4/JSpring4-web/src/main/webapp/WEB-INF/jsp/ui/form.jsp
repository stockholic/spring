 <%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
 
 <style>
 table td{padding:10px}
 </style>
 
 <script>

$(document).ready(function () {
	
	com.calendar("startDt");
	com.calendar("endDt");
	
	 $("#dateCheck").mask("0000-00-00", {placeholder: "____-__-__"});
	 $("#number").mask("0000000000");
	 $('#money').mask("#,##0,000", {reverse: true});
	 
	 
	 //toolTip  top | bottom | left | right | auto
	 $('#btn1').popover({
		 content : '윈도우 팝업 가운데 뛰우삼',
		 trigger : 'hover',
		 placement : 'bottom'
	 });
	 $('#btn2').popover({
		 content : '윈도우 팝업 삑딱하게 뛰우삼',
		 trigger : 'hover',
		 placement : 'bottom'
	 });
	 $('#btn3').popover({
		 content : '인쇄화면 지정하여 인쇄',
		 trigger : 'hover',
		 placement : 'bottom'
	 });
	 $('#btn4').popover({
		 content : '엑셀다운로드 예제',
		 trigger : 'hover',
		 placement : 'bottom'
	 });
});


function openWin(){
	com.popup({
		url :"",
		title : "가운데",
		width : 600,
		height : 400
	})
}

function openWin2(){
	com.popup({
		url :"",
		title : "삑딱하게",
		width : 600,
		height : 400,
		left : 100,
		top : -100
	})
}

function showModal(){
	$('#myModal').modal({
		 keyboard: false
	})
}

 </script>
 
 <div class="x_title">
    <h2>UI Commponents</h2>
 	 <div class="clearfix"></div>
</div>
 

<div id="print">

     <h5 class="page-header"><span class="glyphicon glyphicon-th-large"></span> 캘린더</h5>
	<div class="input-group date" id="startDt" style="width:150px;float: left;margin-right:10px">
  		<input type="text" class="form-control"><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
	</div>
	<div class="input-group date" id="endDt" style="width:150px;">
  		<input type="text" class="form-control"><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
	</div>
	
	
	<h5 class="page-header"><span class="glyphicon glyphicon-th-large"></span> 윈도우 팝업</h5>
	<button type="button" id="btn1" class="btn btn-default" onClick="openWin()">가운데</button>&nbsp;
	<button type="button" id="btn2" class="btn btn-default" onClick="openWin2()" data-content="윈도우 팝업을 삑딱하게 뛰움">삑딱하게</button>
	
	<h5 class="page-header"><span class="glyphicon glyphicon-th-large"></span> 레이어 팝업</h5>
	<button type="button" class="btn btn-default" onClick="showModal()">일반</button>&nbsp;
	<button type="button" class="btn btn-default">Iframe</button>
	
	
	<h5 class="page-header"><span class="glyphicon glyphicon-th-large"></span> 화면프린트</h5>
	<button type="button" id="btn3" class="btn btn-default" onClick="com.print()">인쇄</button>&nbsp;
	<button type="button" id="btn4" class="btn btn-default" onClick="document.location.href='/admin/ui/excel.do'">Excel</button>
	
	<h5 class="page-header"><span class="glyphicon glyphicon-th-large"></span> 폼체크 (jquery.mask : https://igorescobar.github.io/jQuery-Mask-Plugin/)</h5>
	
	<table style="width:400px">
	<colgroup>
		<col style="width:50%" />
		<col style="width:50%" />
	</colgroup>
	<tr>
		<td>
		  <div class="form-group">
		    <label for="date">Date</label>
		    <input type="text" class="form-control" id="dateCheck" >
		  </div>
		 </td>
		<td>
		 <div class="form-group">
		    <label for="number">Number</label>
		    <input type="text" class="form-control" id="number" >
		  </div>
		</td>
	</tr>
	<tr>
		<td>
		  <div class="form-group">
		    <label for="date">Money</label>
		    <input type="text" class="form-control" id="money">
		  </div>
		 </td>
		<td>
		 
		</td>
	</tr>
	</table>
	
	
</div>	



<div class="modal fade" tabindex="-1" role="dialog" id="myModal">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Modal title</h4>
      </div>
      <div class="modal-body">
        <p>One fine body&hellip;</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->



