package com.board.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/* Configuration = 자바기반의 설정파일로 인식 */
/* PropoertySource = 해당 위치에서 참조할 properties파일의 위치 설정 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:/application.properties")
public class DBConfiguration {

	/*
	 * ApplicationContext = Spring IoC Container중 하나 
	 * Spring Container는 Bean의 생성과 사용, 관계, 생명 주기를 관리
	 */
	@Autowired
	private ApplicationContext applicationContext;

	/*
	 * prefix에 해당하는 모든 설정을 불러들여 매핑(바인딩)함. 
	 * Configuration Annotation은 method뿐만아니라 class레벨에서도 지정 가능.
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		/* hikariConfig = Connection Pool library중 하나 */
		return new HikariConfig();
	}

	/*
	 * DataSource = Connection pool을 지원하기 위한 Interface 
	 * Connection Pool = Connection Object를 생성 
	 * DB에 접근하는 사용자에게 미리 생성해둔 Connection을 제공했다가 다시 돌려받음
	 */
	@Bean
	public DataSource dataSource() {
		return new HikariDataSource(hikariConfig());
	}

	/* 
	 * SqlSessionFactory = DB Connection과 SQL실행에 대한 모든 것을 갖는 중요한 친구.
	 * SqlSessionFactoryBean = Mybatis와 Spring의 연동 Module로 사용
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource());
		/* getResources method의 argument로 지정된 패턴이 포함되는 XML Mapper를 인식하도록 하는 역할 */
		factoryBean.setMapperLocations(applicationContext.getResources("classpath:/mappers/**/*Mapper.xml"));
		/* argument로 지정된 패턴을 이용해 풀 패키지 경로를 생략 할 수 있음. */
		factoryBean.setTypeAliasesPackage("com.board.*");
		/* 밑에 mybatisConfig설정과 관련된 Bean을 설정파일로 지정 */
		factoryBean.setConfiguration(mybatisConfg());
		return factoryBean.getObject();
	}

	/* 
	 * SqlSessionTemplate = SqlSessionFactory를 통해 생성
	 * DB의 commit, rollback등 SQL의 실행에 필요한 모든 method를 갖는 Object
	 */
	@Bean
	public SqlSessionTemplate sqlSession() throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory());
	}
	
	/* 
	 * application.properties에서 
	 * mybatis.configuration으로 시작하는 모든 설정을 읽어 Bean으로 등록 
	 */
	@Bean
	@ConfigurationProperties(prefix = "mybatis.configuration")
	public org.apache.ibatis.session.Configuration mybatisConfg() {
		return new org.apache.ibatis.session.Configuration();
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		return new DataSourceTransactionManager(dataSource());
	}
	
}
