package kr.zchat.webapp.admin.site.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import kr.zchat.core.module.dao.MultiSqlSessionDaoSupport;
import kr.zchat.webapp.admin.site.model.Role;


@Repository
public class RoleDao extends MultiSqlSessionDaoSupport{	
	
	@Override
	@Autowired
	@Qualifier(value = "sqliteSqlSessionFactory")
	public void setSessionFactory(SqlSessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	

	public int selectRoleCount(Role role){
		return  getInt("selectRoleCount",role);
	}

	public List<Role> selectRoleList(Role role){
		return  selectList("selectRoleList",role);
	}

	public Role selectRole(String roleSrl){
		return  selectOne("selectRole",roleSrl);
	}

	public int insertRole(Role role){
		return  insert("insertRole",role);
	}

	public int updateRole(Role role){
		return  update("updateRole",role);
	}
	public int updateStatus(Map<String,String> map){
		return  update("updateStatus",map);
	}
	
	public int deleteRole(String roleSrl){
		return  delete("deleteRole",roleSrl);
	}

}
