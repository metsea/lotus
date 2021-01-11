package cn.metsea.lotus.dao.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * Data Connection Config
 */
@PropertySource("classpath:datasource.properties")
@Configuration
@MapperScan("cn.metsea.lotus.*.mapper")
public class DataConnectionConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.validation-query}")
    private String validationQuery;

    @Value("${spring.datasource.pool-prepared-statements}")
    private boolean poolPreparedStatements;
    @Value("${spring.datasource.test-while-idle}")
    private boolean testWhileIdle;
    @Value("${spring.datasource.test-on-borrow}")
    private boolean testOnBorrow;
    @Value("${spring.datasource.test-on-return}")
    private boolean testOnReturn;
    @Value("${spring.datasource.keep-alive}")
    private boolean keepAlive;

    @Value("${spring.datasource.min-idle}")
    private int minIdle;
    @Value("${spring.datasource.max-active}")
    private int maxActive;
    @Value("${spring.datasource.max-wait}")
    private int maxWait;
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;
    @Value("${spring.datasource.initial-size}")
    private int initialSize;
    @Value("${spring.datasource.time-between-eviction-runs-millis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.time-between-connect-error-millis}")
    private int timeBetweenConnectErrorMillis;
    @Value("${spring.datasource.min-evictable-idle-time-millis}")
    private int minEvictableIdleTimeMillis;
    @Value("${spring.datasource.validation-query-timeout}")
    private int validationQueryTimeout;
    @Value("${spring.datasource.default-auto-commit}")
    private boolean defaultAutoCommit;


    /**
     * Mybatis Plus Page
     *
     * @return
     */
    @Bean
    @Primary
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean
    @Primary
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }

    /**
     * Druid DataSource
     */
    @Bean
    @Primary
    public DruidDataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setUrl(dbUrl);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setValidationQuery(validationQuery);

        druidDataSource.setPoolPreparedStatements(poolPreparedStatements);
        druidDataSource.setTestWhileIdle(testWhileIdle);
        druidDataSource.setTestOnBorrow(testOnBorrow);
        druidDataSource.setTestOnReturn(testOnReturn);
        druidDataSource.setKeepAlive(keepAlive);

        druidDataSource.setMinIdle(minIdle);
        druidDataSource.setMaxActive(maxActive);
        druidDataSource.setMaxWait(maxWait);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        druidDataSource.setTimeBetweenConnectErrorMillis(timeBetweenConnectErrorMillis);
        druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        druidDataSource.setValidationQueryTimeout(validationQueryTimeout);
        druidDataSource.setDefaultAutoCommit(defaultAutoCommit);
        return druidDataSource;
    }

    /**
     * DataSource Transaction Manager
     */
    @Bean
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * Database Id Provider
     */
    @Bean
    @Primary
    public DatabaseIdProvider databaseIdProvider() {
        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties properties = new Properties();
        properties.setProperty("MySQL", "mysql");
        databaseIdProvider.setProperties(properties);
        return databaseIdProvider;
    }

    /**
     * Sql Session Factory
     */
    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory(
        @Qualifier("dataSource") DataSource dataSource
        , @Qualifier("databaseIdProvider") DatabaseIdProvider databaseIdProvider
        , @Qualifier("mybatisPlusInterceptor") MybatisPlusInterceptor mybatisPlusInterceptor
    ) throws Exception {
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        configuration.setCallSettersOnNulls(true);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.addInterceptor(mybatisPlusInterceptor);
        configuration.setDefaultEnumTypeHandler(EnumOrdinalTypeHandler.class);
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setConfiguration(configuration);
        sqlSessionFactoryBean.setDataSource(dataSource);

        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setIdType(IdType.AUTO);
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setDbConfig(dbConfig);
        sqlSessionFactoryBean.setGlobalConfig(globalConfig);
        sqlSessionFactoryBean.setTypeAliasesPackage("cn.metsea.lotus.dao.entity");
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("cn/metsea/lotus/dao/mapper/*Mapper.xml"));
        sqlSessionFactoryBean.setTypeEnumsPackage("cn.metsea.lotus.*.enums");
        sqlSessionFactoryBean.setDatabaseIdProvider(databaseIdProvider);
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * Sql Session
     */
    @Bean
    @Primary
    public SqlSession sqlSession(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
