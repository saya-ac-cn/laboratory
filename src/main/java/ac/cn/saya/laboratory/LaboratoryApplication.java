package ac.cn.saya.laboratory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @描述
 * @参数
 * @返回值
 * @创建人  saya.ac.cn-刘能凯
 * @创建时间  2019-02-12
 * @修改人和其它信息
 * 项目启动入口
 */

@SpringBootApplication
// 开启定时任务
@EnableScheduling
public class LaboratoryApplication {

    private static Logger logger = LoggerFactory.getLogger(LaboratoryApplication.class);

    public static void main(String[] args) {
        ///SpringApplication.run(LaboratoryApplication.class, args);
        SpringApplication springApplication = new SpringApplication(LaboratoryApplication.class);
        // 禁止命令行设置参数
        springApplication.setAddCommandLineProperties(false);
        springApplication.run(args);
        //项目启动完成打印项目名
        logger.warn("实验室中心已经启动 ... ");
    }

}

