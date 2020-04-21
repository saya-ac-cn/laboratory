package ac.cn.saya.laboratory.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Title: FinancialDataSourceConfig
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2020-04-20 21:38
 * @Description: 财政金融数据源配置
 */
@Configuration
@MapperScan(basePackages = "ac.cn.saya.laboratory.persistent.financial.dao", sqlSessionTemplateRef = "financialSqlSessionTemplate")
public class FinancialDataSourceConfig {

    @Bean(name = "financialDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.financial")
    public DataSource financialDateSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "financialSqlSessionFactory")
    public SqlSessionFactory financialSqlSessionFactory(@Qualifier("financialDataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        //设置mybatis配置文件路径
        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml"));
        //设置对应的xml文件位置
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/financial/*.xml"));
        return bean.getObject();
    }

    @Bean
    public DataSourceTransactionManager financialTransactionManager(@Qualifier("financialDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    @Bean("financialSqlSessionTemplate")
    public SqlSessionTemplate financialSqlSessionTemplate(
            @Qualifier("financialSqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }

}
