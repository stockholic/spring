var com = {
	CURRENT_BROWSER: (function() {
		var ag = window.navigator.userAgent.toLowerCase();
		var obj = {CHROME: false, SAFARI: false, FIREFOX: false, MSIE: false};
		if (ag.indexOf("chrome") > -1) obj.CHROME = true;
		if (ag.indexOf("safari") > -1 && ag.indexOf("chrome") < 0) obj.SAFARI = true;
		if (ag.indexOf("firefox") > -1) obj.FIREFOX = true;
		if (ag.indexOf("msie") > -1 || ag.indexOf("trident") > -1 || ag.indexOf("edge") > -1) obj.MSIE = true;
		return obj;
	})(),

	//달력
	calendar : function(id){
		 $('#' + id).datepicker({
		    format: "yyyy-mm-dd",
		    todayBtn: "linked",
		    language: "kr",
		    keyboardNavigation: false,
		    forceParse: false,
		    todayHighlight: true,
		    autoclose : true
		});
		
	},
	//팝업 창
	popup : function(params){
		
		var options = {
			width : 300,
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

	// 화면프린트
	print : function(){
		$("#print").print({
			globalStyles: true,
			mediaPrint: false,
			stylesheet: null,
			noPrintSelector: ".no-print",
			iframe: true,
			append: null,
			prepend: null,
			manuallyCopyFormValues: true,
			deferred: $.Deferred(),
			timeout: 250
		});
	},

	/**
	 * checkMsg : 공백체크, checkFile : 파일필수, checkEmail : 메일체크, checkNum : 숫자체크
	 * @param id
	 * @returns {Boolean}
	 */
	formCheck : function(id){

		var exit = false;

		 $("#"+id+" input[type=text], #" +id+" textarea").each(function() {

			  if( $(this).attr("checkMsg") != undefined && !com.trim($(this).val()) ){
				  alert($(this).attr("checkMsg") + " 입력해주세요")
				  $(this).focus();
				  exit = false;
				  return false;
			  }else{
				  exit = true;
			  }

			  if( $(this).attr("checkEmail") != undefined && !com.emailCheck($(this).val()) ){
				  alert("메일형식이 옳바르지 않습니다.")
				  $(this).focus();
				  exit = false;
				  return false;
			  }else{
				  exit = true;
			  }

			  if( $(this).attr("checkNum") != undefined && !com.numberCheck($(this).val()) ){
				  alert("숫자만 입력해주세요.")
				  $(this).focus();
				  exit = false;
				  return false;
			  }else{
				  exit = true;
			  }
        });

		 if (exit) {
			$("#"+id+" select").each(function() {
//				  if( $(this).attr("checkMsg") != undefined  && ($(this).val() == ""|| $(this).val() == "00" ) ){
				  if( $(this).attr("checkMsg") != undefined  && ($(this).val() == "") ){
					  alert($(this).attr("checkMsg") + " 선택해주세요");
					  $(this).focus();
					  exit = false;
					  return false;
				  }else{
					  exit = true;
				  }
			});
		}

		if (exit) {
			$("#"+id+" input[type=checkbox]").each(function() {
				  if( $(this).attr("checkMsg") != undefined  && $(this).prop("checked" ) == false ){
					  alert($(this).attr("checkMsg") + " 체크해주세요");
					  $(this).focus();
					  exit = false;
					  return false;
				  }else{
					  exit = true;
				  }
			});
		}

		return exit;

	},

	//공백제거
	trim : function(str){
		re = /^\s+|\s+$/g;
		return str.replace(re, '');
	},

	//숫자만
	numberCheck : function(str){
	//	var reg = /^[-0-9]/g ;
		var reg =  /^[0-9]+$/ ;
		return reg.test(str);
	},

	//메일체크
	emailCheck : function(str){
		var reg = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/ ;
		return reg.test(str);
	},

	accountCheck: function(str) {
		var reg = /^([0-9_-]+)$/;
		return reg.test(str);
	},

	//테이블 로스팬 id: 테이블아이디, colIdx 컬럼
	rowspan : function(id,colIdx, isStats) {
		return $("#" + id).each(function(){
			var that;
			$('tr', this).each(function(row) {
				$('td:eq('+colIdx+')', this).filter(':visible').each(function(col) {

					if ($(this).html() == $(that).html()
						&& (!isStats
								|| isStats && $(this).prev().html() == $(that).prev().html()
								)
						) {
						rowspan = $(that).attr("rowspan") || 1;
						rowspan = Number(rowspan)+1;

						$(that).attr("rowspan",rowspan);

						// do your action for the colspan cell here
						$(this).hide();

						//$(this).remove();
						// do your action for the old cell here

					} else {
						that = this;
					}

					// set the that if not already set
					that = (that == null) ? this : that;
				});
			});
		});

	},

	toolTip : function(params){
		$("#" + params.id).bt(params.content,{
			width : params.width,
			fill: params.fill,
			strokeStyle: '#B7B7B7',
			spikeLength: 10,
			spikeGirth: 10,
			padding: 8,
			cornerRadius: 5,
			cssStyles: {  fontSize: '9pt' },
			positions: params.position
		});
	},

	ieCheck : function(){

		var ua = window.navigator.userAgent;
	    var msie = ua.indexOf('MSIE ');
	    if (msie > 0) {
	        // IE 10 or older => return version number
	        return parseInt(ua.substring(msie + 5, ua.indexOf('.', msie)), 10);
	    }
	    var trident = ua.indexOf('Trident/');
	    if (trident > 0) {
	        // IE 11 => return version number
	        var rv = ua.indexOf('rv:');
	        return parseInt(ua.substring(rv + 3, ua.indexOf('.', rv)), 10);
	    }
	    var edge = ua.indexOf('Edge/');
	    if (edge > 0) {
	       // IE 12 => return version number
	       return parseInt(ua.substring(edge + 5, ua.indexOf('.', edge)), 10);
	    }
	},

	reset : function(id){
		$("#"+id+" input[type=text]").each(function() {
				$(this).val("");
		});
	},
	
	bytesToSize : function(bytes) {
		   var sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
		   if (bytes == 0) return '0 Byte';
		   var i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)));
		   return Math.round(bytes / Math.pow(1024, i), 2) + ' ' + sizes[i];
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


