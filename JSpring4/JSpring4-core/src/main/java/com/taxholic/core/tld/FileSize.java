package com.taxholic.core.tld;

import java.io.IOException;



import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import com.taxholic.core.util.SysUtil;

@SuppressWarnings("serial")
public class FileSize extends TagSupport{

	private String fileSize;

	public int doStartTag(){
		
		JspWriter out = pageContext.getOut();
		
		try {
			out.println(SysUtil.getFileSize(Long.parseLong(fileSize)));
		} catch(IOException ioe){          
			ioe.printStackTrace(); 
		}
		return SKIP_BODY; 
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

}
