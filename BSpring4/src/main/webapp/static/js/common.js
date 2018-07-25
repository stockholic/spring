var com = {

	/**
	 * 숫자 체크
	 */
	isNumber : function(str){
		//	var reg = /^[-0-9]/g ;
		var reg =  /^[0-9]+$/ ;
		return reg.test(str);
	},
	
	/**
	 * 메일 체크
	 */
	isEmail : function(str){
		var reg = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/ ;
		return reg.test(str);
	},
	
	/**
	 * Ajax 데이터 바인딩
	 */
	bindByUrl : function(obj){
		
		var options = {
				type : "GET", 
				async : true, 
				params : {},
				data : "dataList"
		};
		$.extend(options, obj);
		
		$.ajax({      
			type : options.type,  
	        url : options.url,
	        async : options.async,
	        data : options.params,      
	        success:function(response){   
	        	 jPut[obj.target].data = response[options.data];
	        },   
	        error : function(xhr, status, error) {
                alert("에러 : " + xhr);
	        }
		});  

	},
	
	/**
	 * Json 데이터 바인딩
	 */
	bindByJson : function(obj){
		 jPut[obj.target].data = obj.data;
	},
	
	/**
	 * 팝업창
	 */
	popup : function(params){
		
		var options = {
			title : "QA 관리도구",
			width : 600,
			height : 400,
			scrollbars : "no"
		};
		
		$.extend( options, params );
		
		var left = (screen.width/2)-(options.width /2);
		var top = (screen.height/2)-( options.height /2);
		if( options.left != undefined ) left = left +  options.left;
		if( options.top != undefined ) top = top +  options.top; 
		
		var winObj = window.open(options.url, options.title.replace(/ /g,'_'), 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=' + options.scroll + ', width='+options.width+', height='+options.height+', top='+top+', left='+left);
		winObj.document.title = options.title
	},
	
	fileSize :  function (size) {
        var sizeStr = "";
        var sizeKB = size / 1024;
        if(parseInt(sizeKB) > 1024) {
            var sizeMB = sizeKB / 1024;
            sizeStr = sizeMB.toFixed(2) + " MB";
        } else {
            sizeStr = sizeKB.toFixed(2) + " KB";
        }
        return sizeStr;
    },
	
	/**
	 * 파일업로드
	 */
	fileUpload : function(params, onSuccessCallback, afterUploadAllCallback){
	
		//http://hayageek.com/docs/jquery-upload-file.php#local
		
		var options = {
			url:"",
			fileName:"file",
			maxFileCount : 5,
			maxFileSize:1024 * 1024 * 10,	//10M
			allowedTypes : "*",
			//allowedTypes : "jpg,png,gif",
			autoSubmit : false,
			allowDuplicates : false,
			showFileCounter:false,
			showAbort : false,
			//showProgress : true,
			dragdropWidth : 450,
			cancelStr : "삭제",
			dragDropStr : "",
			uploadStr : "클릭 또는 드래그 <span class='ajax-file-upload-fileSize'>최대 : <span id='maxFileCount'>" + params.maxFileCount + "</span>개 , : " + this.fileSize(params.maxFileSize) + "이하</span>",
			sizeErrorStr : " 파일은 제외 되었습니다. 파일 최대 용량 : ",
			maxFileCountErrorStr:" 파일은 제외 되었습니다. 파일 최대  허용 개수 :  ",
			duplicateErrorStr : "이미 존재하는 파일입니다.",
			extErrorStr : " 파일은 제외 되었습니다. - 허용되는 확장자 : ",
			uploadQueueOrder:'bottom',
			
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
				onSuccessCallback(data)
			},afterUploadAll:function(obj)	{
				afterUploadAllCallback();
			}
		};
		
		$.extend( options, params );
		
		return $("#" + params.id).uploadFile(options);
	},
	
	/**
	 * 에디터
	 */
	editor : function(id){
		//http://summernote.org/
		$('#' + id).summernote({
	 		toolbar: [
	 		    ['style', ['bold', 'italic', 'underline', 'clear']], 
	 		    ['fontname', ['fontname']], 
	 		    ['fontsize', ['fontsize']],
	 		    ['color', ['color']],
	 		    ['table', ['table']],
	 		    ['para', ['ul', 'ol', 'paragraph']],
	 		    ['height', ['height']],
	 		    ['codeview',['codeview']], 
	 		  ],
			height: 400,                
			lang: 'ko-KR',
			fontNames: ['맑은 고딕', '굴림체','돋움체','바탕체','궁서체','Arial Black', 'Comic Sans MS', 'Courier New', 'Merriweather']
		});
	 
	 	$('#' + id).summernote('fontName', '맑은 고딕');
	}


};

	$.fn.clickToggle = function(func1, func2) {
    var funcs = [func1, func2];
    this.data('toggleclicked', 0);
    this.click(function() {
        var data = $(this).data();
        var tc = data.toggleclicked;
        $.proxy(funcs[tc], this)();
        data.toggleclicked = (tc + 1) % 2;
    });
    return this;
};


