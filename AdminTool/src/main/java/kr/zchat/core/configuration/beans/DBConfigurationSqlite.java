package kr.zchat.core.configuration.beans;


import java.io.IOException;


import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import kr.zchat.core.module.dao.RefreshableSqlSessionFactoryBean;


@Configuration
@EnableTransactionManagement(mode = AdviceMode.PROXY, order = 0)
public class DBConfigurationSqlite{
    
    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private @Value("${deploy}") String deploy;
    private @Value("${sqlite.driverClassName}") String driverClassName;
    private @Value("${sqlite.url}") String url;
    

    @Bean(destroyMethod = "close")
    public BasicDataSource  dataSourceSqlite() {
    	
    	BasicDataSource dataSource = new BasicDataSource();
    	dataSource.setDriverClassName(driverClassName);
    	dataSource.setUrl(url);
    	
        return dataSource;
        
    }
    
    @Bean
    public SqlSessionFactory sqliteSqlSessionFactory() throws Exception {
    	 return "local".equals(deploy) ? refreshableSqlSessionFactoryBean() : sqlSessionFactoryBean();
    }
    
    private SqlSessionFactory refreshableSqlSessionFactoryBean() throws IOException{
        RefreshableSqlSessionFactoryBean sqlSessionFactory = new RefreshableSqlSessionFactoryBean();
        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        
        sqlSessionFactory.setConfigLocation(defaultResourceLoader.getResource("classpath:config/mybatis-config.xml"));
        sqlSessionFactory.setMapperLocations(resourcePatternResolver.getResources("classpath:mapper/**/*.xml"));
        sqlSessionFactory.setDataSource(dataSourceSqlite());
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
        sqlSessionFactory.setDataSource(dataSourceSqlite());
        
        return sqlSessionFactory.getObject();
    }
    
    @Bean
    public DataSourceTransactionManager txManager(@Qualifier("dataSourceSqlite") DataSource dataSourceSqlite) {
        return new DataSourceTransactionManager(dataSourceSqlite);
    }
}