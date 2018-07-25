package kr.zchat.core.web.dao;


import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CommonDao extends MultiSqlSessionDaoSupport{	
	
	
	@Override
	@Autowired
	@Qualifier(value = "sqlSessionFactory")
	public void setSessionFactory(SqlSessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	protected String getSqlMapperPrefix() {
		return "kr.zchat.board.";
	}
	
}
