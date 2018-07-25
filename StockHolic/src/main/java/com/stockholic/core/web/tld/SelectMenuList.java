package com.stockholic.core.web.tld;

import javax.servlet.jsp.tagext.TagSupport;



import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.stockholic.core.authority.AuthUtil;
import com.stockholic.webapp.admin.site.model.Role;
import com.stockholic.webapp.admin.site.service.MenuService;

@SuppressWarnings("serial")
public class SelectMenuList extends TagSupport{
	
	private String roleCd;

	public int doStartTag(){
		
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext( pageContext.getServletContext());
		MenuService service = context.getBean(MenuService.class);
		
		String roleCd = (this.roleCd != null) ? this.roleCd : AuthUtil.getUser().getRoleList().get(0) ;
		Role role = new Role();
		role.setRoleCd(roleCd);
		
		pageContext.setAttribute("menuList", service.selectAuthMenuList(role));
		
		return SKIP_BODY; 
	}

	public void setRoleCd(String roleCd) {
		this.roleCd = roleCd;
	}

}
