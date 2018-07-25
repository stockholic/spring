 <%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
 <script src="/static/assets/fileUpload/js/jquery.uploadfile.min.js"></script>
<link href="/static/assets/fileUpload/css/uploadfile.css" rel="stylesheet">

<div class="x_title">
     <h2>FileUpload<small>Ajax & Progress</small></h2>
    <div class="clearfix"></div>
</div>
<div class="x_content">

	<div id="fileuploader">Upload</div>
	
	<div style="clear:both">
		<button type="button" id="btn2" class="btn btn-default">모두취소</button>
		<button type="button" id="btn1" class="btn btn-default">전송</button>
	</div>
	
	<div style="margin-top:20px" id="result"></div>
 
</div>

<script>

var uploadObj = null;

$(document).ready(function() {

	//http://hayageek.com/docs/jquery-upload-file.php#local
	
 	uploadObj = $("#fileuploader").uploadFile({
		url:"/admin/ui/saveFile",
		fileName:"files",
		maxFileCount : 5,
		maxFileSize:1024 * 1024 * 600,
		allowedTypes : "*",
		//allowedTypes : "jpg,png,gif",
		autoSubmit : false,
		allowDuplicates : false,
		showFileCounter:false,
		showAbort : false,
		//showProgress : true,
		dragdropWidth : 400,
		cancelStr : "취소",
		dragDropStr : "",
		uploadStr : "파일을 드래그 OR 클릭",
		sizeErrorStr : "파일은 제외 되었습니다. 파일 허용 용량 : ",
		maxFileCountErrorStr:" 파일은 제외 되었습니다. 최대 허용 개수 :  ",
		duplicateErrorStr : "이미 존재하는 파일입니다.",
		extErrorStr : "허용되는 확장자 : ",
		
		customProgressBar: function(obj,s){
			this.statusbar = $("<div class='ajax-file-upload-statusbar'></div>");
		//	this.preview = $("<img class='ajax-file-upload-preview' />").width(s.previewWidth).height(s.previewHeight).appendTo(this.statusbar).hide();
			this.filename = $("<div class='ajax-file-upload-filename'></div>").appendTo(this.statusbar);
			this.progressDiv = $("<div class='ajax-file-upload-progress'>").appendTo(this.statusbar);
			this.progressbar = $("<div class='ajax-file-upload-bar'></div>").appendTo(this.progressDiv);
			this.abort = $("<div>" + s.abortStr + "</div>").appendTo(this.statusbar).hide();
			this.cancel = $("<div>" + s.cancelStr + "</div>").appendTo(this.statusbar).hide();
			this.done = $("<div>" + s.doneStr + "</div>").appendTo(this.statusbar).hide();
			this.download = $("<div>" + s.downloadStr + "</div>").appendTo(this.statusbar).hide();
			this.del = $("<div>" + s.deletelStr + "</div>").appendTo(this.statusbar).hide();
			
			this.cancel.addClass("ajax-file-upload-red");

		},onSuccess:function(files,data,xhr,pd){
			$("#result").append("<div>" + JSON.stringify(data) + "</div>");
		},afterUploadAll:function(obj)	{
			$("#result").append("<div>complete</div>");
		}
		
	});
	
});

$("#btn1").click(function(){
	uploadObj.startUpload();
//	uploadObj.stopUpload();
});
$("#btn2").click(function(){
	uploadObj.cancelAll();
});
	
</script>