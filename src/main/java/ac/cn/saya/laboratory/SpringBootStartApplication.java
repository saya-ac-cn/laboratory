package ac.cn.saya.laboratory;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @Title: SpringBootStartApplication
 * @ProjectName laboratory-one
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-02-12 22:34
 * @Description:
 * tomcat 启动
 *
 */

public class SpringBootStartApplication extends SpringBootServletInitializer{

    /**
     * Configure the application. Normally all you would need to do is to add sources
     * (e.g. config classes) because other settings have sensible defaults. You might
     * choose (for instance) to add default command line arguments, or set an active
     * Spring profile.
     *
     * @param builder a builder for the application context
     * @return the application builder
     * @see SpringApplicationBuilder
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(LaboratoryApplication.class);
    }

}
