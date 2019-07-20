var com = {

	/**
	 * 숫자 체크
	 */
	isNumber : function(str){
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
	
	//달력
	calendar : function(id,obj){
		
		var options = {
			    format: "yyyy-mm-dd",
			    todayBtn: "linked",
			    language: "kr",
			    keyboardNavigation: false,
			    forceParse: false,
			    todayHighlight: true,
			    autoclose : true
		};
		$.extend(options, obj);
		
		 $('#' + id).datepicker(options);
	},
	
	/**
	 * Ajax 데이터 바인딩
	 */
	getAjaxData : function(obj){
		var data = {};
		var options = {
				type : "GET", 
				async : true, 
				params : {}
			};
		$.extend(options, obj);
		
		 $.ajax({      
		    	type : options.type,  
		    	async : options.async,
		        url : options.url,
		        data : options.params,
		        beforeSend : function(xhr){
					xhr.setRequestHeader("AJAX", "true");
			    },
		        success : function(response){   
		        	data = response;
		        },   
		        error : function(xhr) {
		        	if(xhr.status == "403"){
		        		document.location.href = "/adm/login";
		        	}else{
		    	        alert("에러 : " + xhr.status);
			       	}
		        }
		    });  
		 
		 return  data;
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
			title : "관리도구",
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
	
	 formatBytes : function(a,b){
		if(0==a)return"0 Bytes";
		var c=1024,
		d=b||2,e=["Bytes","KB","MB","GB","TB","PB","EB","ZB","YB"],
		f=Math.floor(Math.log(a)/Math.log(c));
		return parseFloat((a/Math.pow(c,f)).toFixed(d))+" "+e[f]
	},
	
	formatNumber : function(x){
	    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
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
			uploadStr : "클릭 또는 드래그 <span class='ajax-file-upload-fileSize'>최대 : <span id='maxFileCount'>" + params.maxFileCount + "</span>개 , : " + this.formatBytes(params.maxFileSize,2) + "이하</span>",
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
	},
	
	/**
		Vue 초기화
		beforeCreate 	Called synchronously after the instance has just been initialized, before data observation and event/watcher setup.
		created 			Called synchronously after the instance is created. At this stage, the instance has finished processing the options which means the following have been set up: 
							data observation, computed properties, methods, watch/event callbacks. However, the mounting phase has not been started, and the $el property will not be available yet.
		beforeMount 	Called right before the mounting begins: the render function is about to be called for the first time.
		mounted 		Called after the instance has just been mounted where el is replaced by the newly created vm.$el.
		beforeUpdate 	Called when the data changes, before the virtual DOM is re-rendered and patched.
		updated 		Called after a data change causes the virtual DOM to be re-rendered and patched.
	*/
	initVue : function( selector ){
		var vObj = new Vue({
				el: selector,
				data : {
					vData : {}
				},
				methods : {
					setVdata : function(params) {
						this.vData = getVdata(params);
						//console.log(this.objData.list)
					}
				},
				created: function () {
					var _rowSize = (rowSize == undefined) ? 15 : rowSize;
					this.setVdata({rowSize : rowSize})
				}
			});
		
		return vObj;
	},
	
	
	/**
	 * 페이징 초기화
	 *  http://flaviusmatis.github.io/simplePagination.js/
	 */
	initPaging : function( obj ){
		
		var options = {
				selector : "#paging",
				items : 0,						// 전체 레코드 수
				itemsOnPage : 15,			// 페이지당 목록 수
				currentPage : 1				// 현재페이지
			};
	
		$.extend( options, obj );
		
		$(options.selector).pagination({
			items: options.items,
		 	itemsOnPage: options.itemsOnPage,
			currentPage : options.currentPage,
			prevText : "〈 이전",
			nextText : "다음 〉",
			onPageClick : function(pageNumber,event){
				goPage(pageNumber)
			}
		});
	},
	/**
	 * 페이지 선택
	 */
	selectPage  : function( selector, pageNumber ){
		$(selector).pagination('selectPage', pageNumber);
	},
	/**
	 * 전체 페이지 수
	 */
	getPagesCount  : function( selector ){
		return $(selector).pagination('getPagesCount');
	},
	/**
	 * 현재 페이지
	 */
	getCurrentPage   : function( selector ){
		return $(selector).pagination('getCurrentPage');
	},
	/**
	 * 전체 레코드 수 변경
	 */
	updatePageItems  : function( selector, items ){
		$(selector).pagination('updateItems', items);
	},
	/**
	 * 목록에 보여줄 페이지 수 변경
	 */
	updatePageItemsOnPage   : function( selector, itemsOnPage ){
		$(selector).pagination('updateItemsOnPage', itemsOnPage);
	},
	
	/* msg
	 * https://www.jqueryscript.net/lightbox/Draggable-Skinnable-jQuery-Popup-Windows-Plugin-Msgbox.html
	 */
	
	initAlertMsg : function(fnc){
		$(".msgbox.alert").msgbox({
			type: 'alert',
			content: ' ',
			title: '알림',
			resize: false,
			width: 300,
			height: 200,
			initialWidth: 300,
			initialHeight: 200,
			buttons: ['닫기'],
			buttonEvents: {
				'닫기': function() {
					this.close()
				}
			},
			onClose: fnc == undefined ? false : fnc
		});
	},
	initInfoMsg : function(fnc){
		$(".msgbox.info").msgbox({
			type: 'info',
			content: ' ',
			title: '정보',
			resize: false,
			width: 300,
			height: 200,
			initialWidth: 300,
			initialHeight: 200,
			buttons: ['닫기'],
			buttonEvents: {
				'닫기': function() {
					this.close()
				}
			},
			onClose: fnc == undefined ? false : fnc
		});
	},
	
	alertMsg : function(msg){
		$(".msgbox.alert").msgbox().content(msg);
		$(".msgbox.alert").trigger("click");
	},
	infoMsg : function(msg){
		$(".msgbox.info").msgbox().content(msg);
		$(".msgbox.info").trigger("click");
	},

};

//--------------------------------------------------------------------  Jquery plugin
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


//--------------------------------------------------------------------  Vue Filter
// 천단위 콤마
Vue.filter('addComma', function (num) {
	return com.formatNumber(num)
});

Vue.filter('timestampToDate', function (timestamp) {
	var d = new Date(timestamp);
    year = d.getFullYear(),
    month = '' + (d.getMonth() + 1),
    day = '' + d.getDate();

	if (month.length < 2) month = '0' + month;
	if (day.length < 2) day = '0' + day;
	
	return [year, month, day].join('.');
});


