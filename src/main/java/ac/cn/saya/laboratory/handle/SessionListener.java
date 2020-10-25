package ac.cn.saya.laboratory.handle;/**
 * Created by Administrator on 2018/9/26 0026.
 */

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @Title: SessionListener
 * @ProjectName spring-boot-dubbo-pro
 * @Description: TODO
 * @Author Administrator
 * @Date: 2018/9/26 0026 12:57
 * @Description:
 */

public class SessionListener implements HttpSessionListener {

    /**
     * 该HashMap以用户名-HttpSession对象存储一个账号只能被一个人登陆的信息。
     */
    ///public static HashMap<String,HttpSession> sessionMap = new HashMap<String,HttpSession>();


    // 创建session
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
    }

    // 销毁session
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        RepeatLogin.delSession(session);
    }

}
