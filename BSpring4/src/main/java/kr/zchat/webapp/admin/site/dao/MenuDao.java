package kr.zchat.webapp.admin.site.dao;


import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import kr.zchat.core.support.dao.MultiSqlSessionDaoSupport;
import kr.zchat.webapp.admin.site.model.Menu;
import kr.zchat.webapp.admin.site.model.Role;

@Repository
public class MenuDao extends MultiSqlSessionDaoSupport{	
	
	@Override
	@Autowired
	@Qualifier(value = "sqliteSqlSessionFactory")
	public void setSessionFactory(SqlSessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}


	public List<Menu> selectMenuList1(Menu menu){
		return  selectList("selectMenuList1",menu);
	}

	public List<Menu> selectMenuList2(Menu menu){
		return  selectList("selectMenuList2",menu);
	}

	public List<Menu> selectMenuList3(Menu menu){
		return  selectList("selectMenuList3",menu);
	}
	
	public Menu selectMenu(Menu menu){
		return selectOne("selectMenu",menu);
	}
	
	public int updateMenuStatus(Menu menu){
		return  update("updateMenuStatus",menu);
	}
	
	public int insertMenu(Menu menu){
		return  insert("insertMenu",menu);
	}
	
	public int updateMenu(Menu menu){
		return  update("updateMenu",menu);
	}

	public int deleteMenu(String menuSrl){
		return  update("deleteMenu",menuSrl);
	}
	
	public int deleteMenu2(Menu menu){
		return  update("deleteMenu2",menu);
	}
	
	public int deleteMenu3(Menu menu){
		return  update("deleteMenu3",menu);
	}
	
	public List<Menu> selectAllMenuList(Role role){
		return  selectList("selectAllMenuList",role);
	}

	public List<Menu> selectAuthMenuList(Role role){
		return  selectList("selectAuthMenuList",role);
	}

	public int deleteMenuRole(String roleCd){
		return  delete("deleteMenuRole",roleCd);
	}

	public int insertMenuRole(Role role){
		return  insert("insertMenuRole",role);
	}

}
