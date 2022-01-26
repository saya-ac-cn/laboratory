package ac.cn.saya.laboratory.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Title: BusinessDataSourceConfig
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2020-09-22 22:41
 * @Description: 业务数据源配置
 */
@Configuration
@MapperScan(basePackages = "ac.cn.saya.laboratory.persistent.business.dao", sqlSessionTemplateRef = "businessSqlSessionTemplate")
public class BusinessDataSourceConfig {

    public static BusinessDataSourceConfig create() {
        return new BusinessDataSourceConfig();
    }

    /**
     * 创建业务数据库数据源
     */
    public HikariDataSource build() {
        return new BusinessDataSourceWrapper();
    }

    @Bean(name = "businessDataSource")
    public DataSource businessDateSource() {
        return BusinessDataSourceConfig.create().build();
    }

    @Bean(name = "businessSqlSessionFactory")
    public SqlSessionFactory businessSqlSessionFactory(@Qualifier("businessDataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        //设置mybatis配置文件路径
        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml"));
        //设置对应的xml文件位置
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/business/*.xml"));
        return bean.getObject();
    }

    @Bean
    public DataSourceTransactionManager businessTransactionManager(@Qualifier("businessDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    @Bean("businessSqlSessionTemplate")
    public SqlSessionTemplate businessSqlSessionTemplate(
            @Qualifier("businessSqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }

}
