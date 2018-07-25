package com.taxholic.core.web.dao;


import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class StockDao extends MultiSqlSessionDaoSupport{	
	
	
	@Override
	@Autowired
	@Qualifier(value = "stockSqlSessionFactory")
	public void setSessionFactory(SqlSessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	protected String getSqlMapperPrefix() {
		return "com.taxholic.core.authority.";
	}
	
}
