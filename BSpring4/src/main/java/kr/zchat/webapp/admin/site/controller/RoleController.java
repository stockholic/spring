package kr.zchat.webapp.admin.site.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.zchat.webapp.admin.site.model.Role;
import kr.zchat.webapp.admin.site.service.RoleService;


@Controller
@RequestMapping("/adm")
public class RoleController{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RoleService roleService;

	/**
	 * 권한 목록
	 * @param role
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/role/list")
	public String roleList(@ModelAttribute Role role, Model model) {
		
		List<Role> list = roleService.selectRoleList(role);
		model.addAttribute("list", list);
		
		 return "admin:site/role/roleList";
	} 
	
	/**
	 * 권한 등록/수정 폼
	 * @param role
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/role/form")
	public String roleForm(@ModelAttribute Role role, Model model) {
		
		if(role.getRoleSrl() != null){
			model.addAttribute("role", roleService.selectRole(role.getRoleSrl()));
		}
		
		 return "none:admin/site/role/roleForm";
		 
	} 

	
	/**
	 * 권한 등록/수정	
	 * @param role
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/role/insert")
	public void insertRole(Role role) {
		
		if(StringUtils.isNotEmpty(role.getRoleSrl()))
			roleService.updateRole(role);
		else 
			roleService.insertRole(role);
	} 

	/**
	 * 권한 사용여부 수정
	 * @param role
	 */
	@ResponseBody
	@RequestMapping(value="/role/updateStatus")
	public void updateStatus(Role role) {
		roleService.updateStatus(role);
	} 

	/**
	 * 권한 삭제
	 * @param role
	 */
	@ResponseBody
	@RequestMapping(value="/role/delete")
	public void deleteRole(Role role) {
		roleService.deleteRole(role);
	} 
	
	
}