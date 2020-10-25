package ac.cn.saya.laboratory.handle;


import ac.cn.saya.laboratory.entity.UserMemory;
import ac.cn.saya.laboratory.tools.HttpRequestUtil;
import ac.cn.saya.laboratory.tools.ResultUtil;
import com.alibaba.fastjson.JSON;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Title: SystemInterceptor
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/9/24 23:24
 * @Description: 系统拦截器
 */

public class SystemInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(SystemInterceptor.class);

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        UserMemory userMemory = HttpRequestUtil.getUserMemory(request);
        if (null != userMemory) {
            ///logger.warn("you can go to controller");
            return true;
        } else {
            //logger.warn("controller tell you Please login");
            ///request.getRequestDispatcher("/login.html").forward(request, response);
            // 设置将字符以"UTF-8"编码输出到客户端浏览器
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            // 401（未授权） 请求要求身份验证
            response.setStatus(401);
            //获取PrintWriter输出流
            PrintWriter out = response.getWriter();
            out.write(JSON.toJSONString(ResultUtil.error(-7, "请登录")));
            out.close();
            ///response.sendRedirect("/login.html");
            return false;
        }
    }

    @Override
    public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("处理请求完成后视图渲染之前的处理操作 ");
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) throws Exception {
        logger.warn("视图渲染之后的操作");
    }
}
