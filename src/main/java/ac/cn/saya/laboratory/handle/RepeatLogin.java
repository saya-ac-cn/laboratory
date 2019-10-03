package ac.cn.saya.laboratory.handle;


import java.util.Enumeration;

import ac.cn.saya.laboratory.entity.UserMemory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @Title: RepeatLong
 * @ProjectName laboratory
 * @Description: TODO
 * @Author saya
 * @Date: 2018/9/30 0030 23:39
 * @Description:
 */
@Component
public class RepeatLogin {

    /**
     * 该HashMap以用户名-HttpSession对象存储一个账号只能被一个人登陆的信息。
     */
    public static HashMap<String,HttpSession> sessionMap = new HashMap<String,HttpSession>();

    /**
     * redis方案
     */
    ///private static RedisUtils redisUtils;
    ///@Autowired(required = true)
    ///public void setRedisUtils(RedisUtils redisUtils) {
    ///    RepeatLogin.redisUtils = redisUtils;
    ///}


    public static synchronized void delSession(HttpSession session){
        if(session != null){
            // 删除单一登录中记录的变量 
            if(session.getAttribute("user") != null)
            {
                /**
                 * redis方案
                 */
                ///String user = (String) session.getAttribute("user");
                ///redisUtils.hmDelete("DataCenter:SessionMap",user);
                UserMemory user = (UserMemory) session.getAttribute("user");
                RepeatLogin.sessionMap.remove(user.getUser());
            }
        }
    }

    /**
     * 当发现账号已经被人登陆了，就将这个已经登陆上的人的Session从SessionListener.java中的HashMap里给
     * 拿到，并且移除在此HashMap中的记录并将session  invalidate掉
     * @param username
     */
    public static void forceUserLogout(String username)
    {
        /**
         * redis方案
         */
        //if(redisUtils.hmExists("DataCenter:SessionMap",username))
        //{
        //    // 取出用户的SessionID
        //    String sessionID = (String) redisUtils.hmGet("DataCenter:SessionMap",username);
        //    // 删除HashMap中用户的的信息
        //    redisUtils.hmDelete("DataCenter:SessionMap",username);
        //   redisUtils.removePattern("DataCenter:Session:sessions:"+sessionID);// 采用硬编码删除Redis数据库中用户的session
        //}
        if(RepeatLogin.sessionMap.get( username ) != null){
            HttpSession session = RepeatLogin.sessionMap.get( username );
            RepeatLogin.sessionMap.remove( username );
            Enumeration e = session.getAttributeNames();
            while(e.hasMoreElements())
            {
                String sessionName = (String) e.nextElement();
                session.removeAttribute( sessionName );
            }
            session.invalidate();
        }
    }


}
