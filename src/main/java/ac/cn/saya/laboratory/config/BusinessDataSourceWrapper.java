package ac.cn.saya.laboratory.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Title: BusinessDataSourceWrapper
 * @ProjectName lab
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2020-09-22 22:40
 * @Description:业务数据库数据源配置
 */
@ConfigurationProperties(prefix = "spring.datasource.business")
public class BusinessDataSourceWrapper extends DruidDataSource implements InitializingBean {

    @Autowired
    private DataSourceProperties basicProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 如果未找到前缀“spring.datasource.business”JDBC属性，将使用“Spring.DataSource”前缀JDBC属性。
        if (super.getUsername() == null) {
            super.setUsername(basicProperties.determineUsername());
        }
        if (super.getPassword() == null) {
            super.setPassword(basicProperties.determinePassword());
        }
        if (super.getUrl() == null) {
            super.setUrl(basicProperties.determineUrl());
        }
        if (super.getDriverClassName() == null) {
            super.setDriverClassName(basicProperties.getDriverClassName());
        }
    }

}
