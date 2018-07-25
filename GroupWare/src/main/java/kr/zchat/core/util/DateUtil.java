package kr.zchat.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
	
	/**
	 * 현재 년
	 * @return int
	 */
	public static int getYear()	 {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	 }
	
	/***
	 * 현재 월
	 * @return int
	 */
	public static int getMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH)+1;
	}
	
	/***
	 * 현재 일 <p>
	 * @return int
	 */
	public static int getDay() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DATE);
	}
	
	/**
	 * 현재 날짜
	 * @return String
	 */
	public static String getDate() {
		Date date = new Date();
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		 return df.format(date);
	}
	
	/**
	 * 현재 날짜 
	 * @param format - yyyy-MM-dd hh:mm:ss
	 * @return String
	 */
	public static String getDate( String format){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return  sdf.format(date);
	}
	
	/**
	 * 날짜 변환 
	 * @param date
	 * @param format - yyyy-MM-dd hh:mm:ss
	 * @return String
	 */
	public static String getDate(Date date, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return  sdf.format(date);
	}
	
	/**
	 * n일 전/후 날짜 변환
	 * @param ymd - 대상날짜(yyyy-MM-dd)
	 * @param n  - n일 전/후 날자
	 * @return String
	 * @throws ParseException
	 */
	public static String getDate (String ymd, int n) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();    
		cal.setTime(sdf.parse(ymd));
	    cal.add ( Calendar.DAY_OF_MONTH, n );
	    return getDate(cal.getTime(), "yyyy-MM-dd");
	}
	
	/***
	 * 현재 요일 <p>
	 * @return String
	 */
	public static String getWeek() {
		Calendar cal = Calendar.getInstance();
		String [] week = {"","일","월","화","수","목","금","토"};
		return week[cal.get(Calendar.DAY_OF_WEEK)]; 
	}	
	
	/***
	 * 현재달 주 (1주, 2주 ...)
	 * @return int
	 */
	public static int getCurrentWeek(){
		Calendar cal = Calendar.getInstance();
		int result = cal.get(Calendar.WEEK_OF_MONTH) ;
		return result;
	} 
	
	/***
	 * 해당년월의 마지막 날짜
	 * @param nYear : 년도
	 * @param nMonth : 월
	 * @return int
	 */
	public static int getLastDay(int nYear, int nMonth){
		GregorianCalendar cld = new GregorianCalendar (nYear, nMonth - 1, 1);
		int result = cld.getActualMaximum(Calendar.DAY_OF_MONTH);
		return result;
	}
	
	/***
	 * 해당년월의 첫번째 날짜의 요일(1:SUNDAY, 2:MONDAY...)
	 * @param nYear
	 * @param nMonth
	 * @return int
	 */
	public static int getFirstWeekday(int nYear, int nMonth){
		GregorianCalendar cld = new GregorianCalendar (nYear, nMonth - 1, 1);
		int result = cld.get(Calendar.DAY_OF_WEEK);
		return result;
	}
	
	/***
	 * 해당년월의 주의 개수
	 * @param nFristWeekday : 그 달의 첫째날의 요일
	 * @param nToDay : 그 달의 날짜 수
	 * @return int
	 */
	public static int getWeekCount(int nFristWeekday, int nToDay){
		int nCountDay = nFristWeekday + nToDay - 1;
		int result = (nCountDay / 7);
		if ((nCountDay % 7) > 0) {
			result++;
		}
		return result;
	}

	
	/**
	 * 셀렉트 년도 날짜 생성 
	 * @return String
	 */
	public static String getSelYear(int sel){
		StringBuffer dataList = new StringBuffer();
		int year = getYear();
		
		for(int i = 0; i <  7; i++){
			String selected = (year - i ==  sel) ? "selected" : "";
			dataList.append("<option value='" + (year -i) + "' " + selected + ">" + (year -i) + "</option>");
		}

		return dataList.toString();
	}

	/**
	 *  셀렉트 월  날짜 생성 ex) getMonth(3);
	 * @param m : 현재 월
	 * @return String
	 */
	public static String getSelMonth(int sM){

		StringBuffer dataList = new StringBuffer();
	
		for(int i = 1; i < 13; i++){
	
			if(i == sM){
				dataList.append("<option value='" + i + "' selected>" + i + "</option>");
			}else{
				dataList.append("<option value='" + i + "'>" + i + "</option>");
			}
		}

		return dataList.toString();
	}

	/***
	 * 셀렉트 일 날짜 생성 ex(getDay(25))
	 * @param d : 현재 일
	 * @return String
	 */
	public static String getSelDay(int sY, int sM, int sD){
		StringBuffer dataList = new StringBuffer();

		for(int i = 1; i <= DateUtil.getLastDay(sY,sM); i++){

			if(i == sD){
				dataList.append("<option value='" + i + "' selected>" + i + "</option>");
			}else{
				dataList.append("<option value='" + i + "'>" + i + "</option>");
			}
		}

		return dataList.toString();
	}

}
