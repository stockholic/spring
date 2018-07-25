package kr.zchat.core.tld;

import java.io.IOException;




import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import kr.zchat.core.util.SysUtil;

@SuppressWarnings("serial")
public class FileSize extends TagSupport{

	private String size;

	public int doStartTag(){
		
		JspWriter out = pageContext.getOut();
		
		try {
			out.println(SysUtil.getFileSize(Long.parseLong(size)));
		} catch(IOException ioe){          
			ioe.printStackTrace(); 
		}
		return SKIP_BODY; 
	}

	public void setSize(String size) {
		this.size = size;
	}

}
