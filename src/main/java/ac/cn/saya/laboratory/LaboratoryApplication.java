package ac.cn.saya.laboratory;

import ac.cn.saya.laboratory.config.BusinessDataSourceWrapper;
import ac.cn.saya.laboratory.config.FinancialDataSourceWrapper;
import ac.cn.saya.laboratory.config.PrimaryDataSourceWrapper;
import ac.cn.saya.laboratory.tools.CurrentLineInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @描述
 * @参数
 * @返回值
 * @创建人 saya.ac.cn-刘能凯
 * @创建时间 2019-02-12
 * @修改人和其它信息 项目启动入口
 */

//// 移除 @SpringBootApplication and @ComponentScan, 用 @EnableAutoConfiguration 来替代
//@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"ac.cn.saya.laboratory"})
@EnableTransactionManagement //开启声明式事务
@EnableScheduling
@EnableConfigurationProperties({PrimaryDataSourceWrapper.class,FinancialDataSourceWrapper.class, BusinessDataSourceWrapper.class})
public class LaboratoryApplication {

    private static Logger logger = LoggerFactory.getLogger(LaboratoryApplication.class);


    public static void main(String[] args) {
        try {
            ///SpringApplication.run(LaboratoryApplication.class, args);
            SpringApplication springApplication = new SpringApplication(LaboratoryApplication.class);
            // 禁止命令行设置参数
            springApplication.setAddCommandLineProperties(false);
            springApplication.run(args);
            //项目启动完成打印项目名
            logger.warn("实验室中心已经启动 ... ");
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("实验室中心已经启动失败",e, LaboratoryApplication.class);
        }
    }

}

