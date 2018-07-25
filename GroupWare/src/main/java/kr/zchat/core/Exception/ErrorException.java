package kr.zchat.core.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.zchat.core.constants.SystemConstants;

@SuppressWarnings("serial")
public class ErrorException extends Exception{
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	public ErrorException(){}
	
	public ErrorException(Exception e){
		logger.error("Cause: {}",e.toString());

		for(StackTraceElement element : e.getStackTrace()){
			if(element.getClassName() .contains(SystemConstants.EXCEPTION_PREFIX)){
				logger.error("Method : {}",element.toString());
			}
		}
	}
	
	public ErrorException(String msg, Exception e){
		logger.error("{}",msg);
		logger.error("Cause: {}",e.toString());

		for(StackTraceElement element : e.getStackTrace()){
			if(element.getClassName() .contains(SystemConstants.EXCEPTION_PREFIX)){
				logger.error("Method : {}",element.toString());
			}
		}
	}
} 