package ac.cn.saya.laboratory.service.impl;

import ac.cn.saya.laboratory.persistent.dao.LogDAO;
import ac.cn.saya.laboratory.entity.LogEntity;
import ac.cn.saya.laboratory.persistent.service.LogService;
import ac.cn.saya.laboratory.tools.City;
import ac.cn.saya.laboratory.tools.Log4jUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Title: RecordService
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/9/22 22:40
 * @Description:
 * 日志记录专用类
 */

@Service("recordService")
public class RecordService {

    private static Logger logger = LoggerFactory.getLogger(RecordService.class);

    @Resource
    @Qualifier("logService")
    private LogService logService;


    public RecordService() {
    }

    /**
     * 日志记录
     * @param type
     * @param httpRequest
     */
    public void record(String type, HttpServletRequest httpRequest)
    {
        try{
            //在session中取出管理员的名字
            String user = (String) httpRequest.getSession().getAttribute("user");
            String ip = httpRequest.getRemoteAddr();
            City cityUtil =new City();
            String city = cityUtil.getCity(ip, "utf-8");
            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String datetime = formatter.format(currentTime);
            LogEntity entity = new LogEntity(user, type, ip, city, datetime);
            logService.insert(entity);
        }catch (Exception e)
        {
            e.printStackTrace();
            logger.warn("记录日志异常"+ Log4jUtils.getTrace(e));
        }
    }

}
