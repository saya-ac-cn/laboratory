package ac.cn.saya.laboratory.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @Title: PrimaryDataSourceConfig
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2020-04-20 21:36
 * @Description: 主数据源配置
 */
https://www.cnblogs.com/ziyue7575/p/549bc1f2e0996ed979bd09c25a6a26c0.html
@Configuration
//basePackages:接口文件的包路径
@MapperScan(basePackages = "ac.cn.saya.laboratory.persistent.primary.dao", sqlSessionFactoryRef = "primarySqlSessionFactory")
public class PrimaryDataSourceConfig {

    @Bean(name = "primaryDataSource")
    // 表示这个数据源是默认数据源
    @Primary//这个一定要加，如果两个数据源都没有@Primary会报错
    @ConfigurationProperties(prefix = "spring.datasource.primary")//我们配置文件中的前缀
    public DataSource getPrimaryDateSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "primarySqlSessionFactory")
    @Primary
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/one/*.xml"));
        return bean.getObject();// 设置mybatis的xml所在位置
    }

    // 表示这个数据源是默认数据源
    @Primary
    @Bean("PrimarySqlSessionTemplate")
    public SqlSessionTemplate primarySqlSessionTemplate(
            @Qualifier("primarySqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }

}
