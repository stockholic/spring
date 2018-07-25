package com.taxholic.core.configuration.beans;


import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
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

@Configuration
@EnableTransactionManagement(mode = AdviceMode.PROXY, order = 0)
public class DBConfiguration implements InitializingBean{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
        config.addDataSourceProperty("prepStmtCacheSqlLimit", prepStmtCacheSqlLimit);
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);

        HikariDataSource dataSource = new HikariDataSource(config);

        return dataSource;
    }
    
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
   		return "local".equals(deploy) ? refreshableSqlSessionFactoryBean() : sqlSessionFactoryBean();
    }
    
    private SqlSessionFactory refreshableSqlSessionFactoryBean() throws IOException{
    	
    	RefreshableSqlSessionFactoryBean sqlSessionFactory = new RefreshableSqlSessionFactoryBean();
    	DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
    	PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    	
    	sqlSessionFactory.setConfigLocation(defaultResourceLoader.getResource("classpath:config/mybatis-config.xml"));
		sqlSessionFactory.setMapperLocations(resourcePatternResolver.getResources("classpath:mapper/**/*.xml"));
		sqlSessionFactory.setDataSource(dataSource());
		sqlSessionFactory.setCheckInterval(1000);
		sqlSessionFactory.setProxy();
		
		return sqlSessionFactory.getObject();
    }
    
    private SqlSessionFactory sqlSessionFactoryBean() throws Exception{
    	
    	SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
    	DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
    	PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    	
    	sqlSessionFactory.setConfigLocation(defaultResourceLoader.getResource("classpath:config/mybatis-config.xml"));
    	sqlSessionFactory.setMapperLocations(resourcePatternResolver.getResources("classpath:mapper/**/*.xml"));
    	sqlSessionFactory.setDataSource(dataSource());
    	
    	return sqlSessionFactory.getObject();
    }
    
    
//    @Bean(destroyMethod = "clearCache")
//		public SqlSessionTemplate sqlSession(SqlSessionFactory refeshSqlSessionFactory) {
//		SqlSessionTemplate sqlSession = new SqlSessionTemplate(refeshSqlSessionFactory);
//		return sqlSession;
//	}
    
    @Bean
    public DataSourceTransactionManager txManager(@Qualifier("dataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("--------------------------- " + deploy +" : DataSource");
		logger.info("SqlSessionFactory : dataSource");
		logger.info("URL : " + url);
	}
    
    
}