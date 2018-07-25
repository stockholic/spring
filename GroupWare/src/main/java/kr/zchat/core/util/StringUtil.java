package kr.zchat.core.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.StringCharacterIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/***
	 * null && 공백 체크 <p>
	 * @param str
	 * @return
	 */
	public static String chkNull(String str){
		if(str == null){
			return "";
		}
		return str.trim();
	}

	/**
	 * <pre>
	 * String null or empty("") check
	 * </pre>
	 * @param str
	 * @return boolean 널이거나 공백일경우 true 아니면 false
	 */
	public static boolean isNullOrEmpty(String str) {
		if (str == null || str.equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * <pre>
	 * Object null or empty("") check
	 * </pre>
	 * @param obj
	 * @return boolean 널이거나 공백일경우 true 아니면 false
	 */
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null || obj.toString().equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 크로스사이트 스크립트 막기
	 * @param str
	 * @return String
	 */
	public static String setScriptChange(String str){
		return (str == null) ? "" : str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	/***
	 * 숫자체크 <p>
	 * @param num
	 * @return
	 */
	public static boolean chkNum(String num){

		boolean chk = true;

		if(num == null) num = "";

		Pattern patt = Pattern.compile("^[0-9]+$");
		Matcher match = patt.matcher(num);

		if(num == null || !match.find()){
			chk = false;
		}else{
			chk = true;
		}

		return chk;
	}

	/***
	 * 정규식 문자열 체크<p>
	 * @param pt : 패턴 <br>
	 * @param str : 문자열
	 * @return
	 */
	public static boolean chkString(String pt, String str){

		boolean chk = true;

		Pattern patt = Pattern.compile(pt);
		Matcher match = patt.matcher(str);

		if(str == null || !match.find()){
			chk = false;
		}else{
			chk = true;
		}

		return chk;
	}


	/**
	 * <br> 처리 <p>
	 * @param src
	 * @return
	 */
	public static String lineBreak(String src){

		if(src== null ) return "";

		int len = src.length();

		int linenum = 0, i = 0;

		for(i = 0; i < len; i++){
			if(src.charAt(i) == '\n') linenum ++;
		}
		StringBuffer dest = new StringBuffer(len + linenum * 3);

		for(i = 0; i < len; i++){
			if(src.charAt(i) == '\n'){
				dest.append("<br>");
			}else{
				dest.append(src.charAt(i));
			}
		}

		return dest.toString();
	}


	 /***
	  * 문자열 반복 <p>
	  * @param str <br>
	  * @param num :  반복 수
	  * @return
	  */
	public static String strRepeat(String str, int num){
		String result = "";
		for(int i = 0; i < num; i++){
			result += str;
		}
		return result;
	}


	/***
	 * 태그제거 <p>
	 * @param str <br>
	 * @return String
	 */
	public static String removeTag(String str){

		if(str == null) return "";

		int lt = str.indexOf("<");

		if ( lt != -1 ) {
			int gt = str.indexOf(">", lt);
			if ( (gt != -1) ) {
				str = str.substring( 0, lt ) + str.substring( gt + 1 );
				str = removeTag(str);		// 재귀 호출
			}
		}
		return str.replaceAll("&nbsp;", "");
	}

 /**
  * * <pre>
  * 문자열 자르기
  * </pre>
  * @param value
  * @param numBytes
  * @param charset UTF-8
  * @return
  */
	public static String strlen(String value, int numBytes, String charset) {
        do {
            byte[] valueInBytes = null;
            try {
                valueInBytes = value.getBytes(charset);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
            if (valueInBytes.length > numBytes) {
                value = value.substring(0, value.length() - 1);
            } else {
                return value;
            }
        } while (value.length() > 0);
        return "";
    }

	
	/**
	 * <pre>
	 * 문자열 자르기(UTF-8 글자수로 자르기)
	 * </pre>
	 * @param str
	 * @param len
	 * @param tail
	 * @return
	 */
	public static String strlenUTF(String str, int len,String tail){
		if( str.length() <= len){
			return str;
		}
		StringCharacterIterator sci = new StringCharacterIterator(str);
		StringBuffer buffer = new StringBuffer();
		buffer.append(sci.first());
		for(int i=1; i<len ; i++){
			if( i < len-1){
				buffer.append(sci.next());
			}else{
				char c=sci.next();
				if(c != 32){ //마지막 charater가 공백이 아닐경우
					buffer.append(c);
				}
			}
		}	
		buffer.append(tail);
		return buffer.toString();
	}
	
	/**
    * 제로필 숫자 처리 ex)zeroFill("0000","5") ==> 0005
    * @param format
    * @param input - String
    * @return String
    */
	public static String zeroFill(String format, String input){
		DecimalFormat DF = new DecimalFormat(format);
		String outPut = DF.format(Integer.parseInt(input));
		return outPut;
	}
	
	/**
	* 제로필 숫자 처리 ex)zeroFill("0000","5") ==> 0005
	* @param format
	* @param input - int
	* @return String
	*/
	public static String zeroFill(String format, int input){
		DecimalFormat DF = new DecimalFormat(format);
		String outPut = DF.format(input);
		return outPut;
	}

	/**
	 * 통화 표시
	 * @param str
	 * @return
	 */
	public static String getPrice(String str) {
	  DecimalFormat df = new DecimalFormat("###,###");

	  return df.format(Long.parseLong(str));
	}


	public static int getNumber(String str) {
	  return Integer.parseInt(str);
	}

	public static double getRound(double val1, double val2) {
		 return  100 * Math.round( val1 / val2 * 10000) / 10000.0;
	}

	public static int getRoundInt(double val1, double val2) {
		 return (int)( Math.round( val1/val2 ) );
	}


	/***
	 * 파일 사이즈 <p>
	 * @param file
	 * @return String
	 */
	public static String getFileSize(double file){

		String fileValue = "";

		if(file < 1024){
			fileValue = Double.toString(file) + " byte";
		}else if(file >= 1024 && file < 1024 * 1024){
			file = file / (1024);
			file = (double)(int)(file * 100 + 0.5)/100.0; //소수 2 재자리 반올림
			fileValue = Double.toString(file) + " KB";
		}else{
			file = file / (1024 * 1024);
			file = (double)(int)(file * 100 + 0.5)/100.0;
			fileValue = Double.toString(file) + " MB";
		}

		return fileValue;
	}

	/**
	 * 파일 존재 유무
	 * @param fileName
	 * @return boolean
	 */
	public static boolean getFileExist(String fileName){

		boolean isCheck = false;

		File file = new File(fileName);
		if(file.exists()) isCheck = true;

		return isCheck;
	}
}
