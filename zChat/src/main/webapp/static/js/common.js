var com = {

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
	
	currentBrowser: function() {
		var ag = window.navigator.userAgent.toLowerCase();
		var obj = {CHROME: false, SAFARI: false, FIREFOX: false, MSIE: false};
		if (ag.indexOf("chrome") > -1) obj.CHROME = true;
		if (ag.indexOf("safari") > -1 && ag.indexOf("chrome") < 0) obj.SAFARI = true;
		if (ag.indexOf("firefox") > -1) obj.FIREFOX = true;
		if (ag.indexOf("msie") > -1 || ag.indexOf("trident") > -1 || ag.indexOf("edge") > -1) obj.MSIE = true;
		return obj;
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


