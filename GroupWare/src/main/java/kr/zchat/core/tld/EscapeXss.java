package kr.zchat.core.tld;

import java.io.IOException;



import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import kr.zchat.core.util.StringUtil;


@SuppressWarnings("serial")
public class EscapeXss extends TagSupport{

	private String str;
	private String br;

	public int doStartTag(){
		
		JspWriter out = pageContext.getOut();
		
		if(this.str != null) {
			this.str  = str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
			if(this.br != null && this.br.equals("true")) {
				this.str = StringUtil.lineBreak(this.str);
			}
		}else{
			this.str  = "";
		}
		
		try {
			out.println(this.str);
		} catch(IOException ioe){          
			ioe.printStackTrace(); 
		}
		return SKIP_BODY; 
	}

	public void setStr(String str) {
		this.str = str;
	}
	public void setBr(String br) {
		this.br = br;
	}

}
