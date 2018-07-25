package kr.zchat.webapp.admin.site.dao;


import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import kr.zchat.core.support.dao.MultiSqlSessionDaoSupport;
import kr.zchat.webapp.admin.site.model.User;

@Repository
public class UserDao extends MultiSqlSessionDaoSupport{	
	
	@Override
	@Autowired
	@Qualifier(value = "sqliteSqlSessionFactory")
	public void setSessionFactory(SqlSessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	public int selectUserCount(User user){
		return  getInt("selectUserCount",user);
	}

	public List<User> selectUserList(User user){
		return  selectList("selectUserList",user);
	}

	public User selectUser(Integer usrSrl){
		return  selectOne("selectUser",usrSrl);
	}

	public int insertUser(User user){
		return  insert("insertUser",user);
	}

	public int insertUserRole(User user){
		return  insert("insertUserRole",user);
	}

	public int updateUser(User user){
		return  update("updateUser",user);
	}

	public int updateUseRole(User user){
		return  update("updateUseRole",user);
	}
}
