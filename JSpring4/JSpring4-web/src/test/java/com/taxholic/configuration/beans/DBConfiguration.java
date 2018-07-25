package com.taxholic.configuration.beans;


import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.taxholic.core.web.dao.RefreshableSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

//@Configuration
//@EnableTransactionManagement(mode = AdviceMode.PROXY, order = 0)
public class DBConfiguration {
private @Value("${deploy}") String deploy;
	
	private @Value("${stock.minimumIdle}") int minimumIdle;
	private @Value("${stock.maximumPoolSize}") int maximumPoolSize;
	private @Value("${stock.validationQuery}") String validationQuery;
	private @Value("${stock.connectionTimeout}") int connectionTimeout;
	private @Value("${stock.autocommit}") boolean isAutoCommit;
	 
	private @Value("${stock.driverClassName}") String driverClassName;
	private @Value("${stock.cachePrepStmts}") boolean cachePrepStmts;
	private @Value("${stock.prepStmtCacheSize}") int prepStmtCacheSize;
	private @Value("${stock.prepStmtCacheSqlLimit}") int prepStmtCacheSqlLimit;
	private @Value("${stock.useServerPrepStmts}") boolean useServerPrepStmts;
	 
	private @Value("${stock.url}") String url;
	private @Value("${stock.user}") String user;
	private @Value("${stock.password}") String password;

    @Bean(destroyMethod = "shutdown")
    public DataSource dataSource() {
    	
        HikariConfig config = new HikariConfig();
        
        config.setMinimumIdle(minimumIdle);
        config.setMaximumPoolSize(maximumPoolSize);
//        config.setConnectionTestQuery(validationQuery);
        config.setConnectionTimeout(connectionTimeout);
        config.setAutoCommit(isAutoCommit);
        
        config.addDataSourceProperty("cachePrepStmts", cachePrepStmts);
        config.addDataSourceProperty("prepStmtCacheSize", prepStmtCacheSize);
        config.addDataSourceProperty("useServerPrepStmts", useServerPrepStmts);
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);

        HikariDataSource dataSource = new HikariDataSource(config);

        return dataSource;
    }
  
   
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
    	
    	SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
    	sqlSessionFactory.setDataSource(dataSource());
		PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
		sqlSessionFactory.setConfigLocation(defaultResourceLoader.getResource("classpath:config/mybatis-config.xml"));
		sqlSessionFactory.setMapperLocations(resourcePatternResolver.getResources("classpath:mapper/**/*.xml"));

		return sqlSessionFactory.getObject();
    }
    
    
    @Bean
    public SqlSessionFactory refeshSqlSessionFactory() throws Exception {
    	
    	RefreshableSqlSessionFactoryBean sqlSessionFactory = new RefreshableSqlSessionFactoryBean();
    	
		DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
		sqlSessionFactory.setConfigLocation(defaultResourceLoader.getResource("classpath:config/mybatis-config.xml"));
		PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactory.setMapperLocations(resourcePatternResolver.getResources("classpath:mapper/**/*.xml"));
		sqlSessionFactory.setDataSource(dataSource());
		sqlSessionFactory.setCheckInterval(1000);
		sqlSessionFactory.setProxy();

		return sqlSessionFactory.getObject();
    }
    
    @Bean(destroyMethod = "clearCache")
	public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
		SqlSessionTemplate sqlSession = new SqlSessionTemplate(sqlSessionFactory);
		return sqlSession;
	}
    
    @Bean
    public DataSourceTransactionManager txManager(@Qualifier("dataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
    
    
}