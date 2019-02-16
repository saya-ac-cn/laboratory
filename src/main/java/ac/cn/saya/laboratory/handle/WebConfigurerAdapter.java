package ac.cn.saya.laboratory.handle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Title: WebConfigurerAdapter
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/9/24 23:21
 * @Description:
 * 使用WebMvcConfigurationSupport替代WebMvcConfigurerAdapter;
 */
@Configuration
public class WebConfigurerAdapter extends WebMvcConfigurationSupport {

    /**
     * 允许put
     * @return
     */
    @Bean
    public HttpPutFormContentFilter httpPutFormContentFilter() {
        return new HttpPutFormContentFilter();
    }


    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(new SystemInterceptor()).addPathPatterns("/backend/**").excludePathPatterns("/backend/login", "/backend/download/*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/files/** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/files/**").addResourceLocations("classpath:/files/");
    }

}
