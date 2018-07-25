package kr.zchat.web.admin.mail.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import kr.zchat.core.authority.Auth;
import kr.zchat.core.web.dao.MultiSqlSessionDaoSupport;

@Repository
public class UserDao extends MultiSqlSessionDaoSupport{	
	
//	@Override
//	@Autowired
//	@Qualifier(value = "groupWareSqlSessionFactory")
//	public void setSessionFactory(SqlSessionFactory sessionFactory) {
//		super.setSessionFactory(sessionFactory);
//	}

	@Override
	@Autowired
	@Qualifier(value = "sqliteSqlSessionFactory")
	public void setSessionFactory(SqlSessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	

	public Auth selectUserInfo(Map<String,String> user){
		return  selectOne("selectUserInfo",user);
	}

	public List<Auth> selectUserAuthList(String userId){
		return  selectList("selectUserAuthList",userId);
	}

}
