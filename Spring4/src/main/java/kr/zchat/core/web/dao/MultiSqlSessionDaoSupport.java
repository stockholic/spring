package kr.zchat.core.web.dao;


import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

public abstract class MultiSqlSessionDaoSupport {	
	
	private SqlSession sqlSession;
	
	/**
	 * MapperPrefix
	 * @return String
	 */
	protected abstract String getSqlMapperPrefix();
	
	/**
	 * SqlSessionFactory
	 * @param sessionFactory
	 */
	public void setSessionFactory(SqlSessionFactory sessionFactory) {
		this.sqlSession = new SqlSessionTemplate(sessionFactory);
	}
	
	/**
	 * SelectOne
	 * @param sqlId
	 * @return int
	 */
	public int getInt(String sqlId) {
		return (Integer) sqlSession.selectOne(getSqlMapperPrefix() + sqlId);
	}

	/**
	 * Select int
	 * @param sqlId
	 * @param object
	 * @return int
	 */
	public int getInt(String sqlId,Object object) {
		return (Integer) sqlSession.selectOne(getSqlMapperPrefix() + sqlId,object);
	}
	
	/**
	 * Select String
	 * @param sqlId
	 * @return String
	 */
	public String getString(String sqlId) {
		return (String) sqlSession.selectOne(getSqlMapperPrefix() + sqlId);
	}

	/**
	 * Select String
	 * @param sqlId
	 * @param object
	 * @return String
	 */
	public String getString(String sqlId,Object object) {
		return (String) sqlSession.selectOne(getSqlMapperPrefix() + sqlId,object);
	}
	
	public <T> T getObject(String sqlId){
		return sqlSession.selectOne(getSqlMapperPrefix() + sqlId);
	}

	/**
	 * Select Object
	 * @param sqlId
	 * @param object
	 * @return <T> the generic type
	 */
	public <T> T getObject(String sqlId, Object object){
		return sqlSession.selectOne(getSqlMapperPrefix() + sqlId,object);
	}
	
	/**
	 * Select List
	 * @param sqlId
	 * @return <E> the element type
	 */
	public <E> List<E> selectList(String sqlId) {		
		return sqlSession.selectList(getSqlMapperPrefix() + sqlId);		
	}

	/**
	 * Select List 
	 * @param sqlId
	 * @param object
	 * @return <E> the element type
	 */
	public <E> List<E> selectList(String sqlId, Object object) {		
		return sqlSession.selectList(getSqlMapperPrefix() + sqlId,object);		
	}
	
	/**
	 *  Insert 
	 * @param sqlId
	 * @return int
	 */
	public int insert(String sqlId){
		return sqlSession.insert(getSqlMapperPrefix() + sqlId);	
	}
	
	/**
	 *  Insert 
	 * @param sqlId
	 * @param object
	 * @return int
	 */
	public int insert(String sqlId, Object object){
		return sqlSession.insert(getSqlMapperPrefix() + sqlId,object);	
	}

	/**
	 * Update
	 * @param sqlId
	 * @return int
	 */
	public int update(String sqlId){
		return  sqlSession.update(getSqlMapperPrefix() + sqlId);
	}

	/**
	 * Update
	 * @param sqlId
	 * @param object
	 * @return int
	 */
	public int update(String sqlId, Object object){
		return  sqlSession.update(getSqlMapperPrefix() + sqlId, object);
	}
	
	/**
	 * Delete 
	 * @param sqlId
	 * @return int
	 */
	public int delete(String sqlId){
		return sqlSession.delete(getSqlMapperPrefix() + sqlId);
	}

	/**
	 * Delete 
	 * @param sqlId
	 * @param object
	 * @return int
	 */
	public int delete(String sqlId, Object object){
		return sqlSession.delete(getSqlMapperPrefix() + sqlId, object);
	}
	
}
