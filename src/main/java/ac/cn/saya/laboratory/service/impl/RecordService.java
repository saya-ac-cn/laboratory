package ac.cn.saya.laboratory.service.impl;

import ac.cn.saya.laboratory.entity.LogEntity;
import ac.cn.saya.laboratory.entity.UserMemory;
import ac.cn.saya.laboratory.persistent.primary.service.LogService;
import ac.cn.saya.laboratory.tools.DateUtils;
import ac.cn.saya.laboratory.tools.Log4jUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Title: RecordService
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/9/22 22:40
 * @Description: 日志记录专用类
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
     *
     * @param type
     * @param httpRequest
     */
    public void record(String type, HttpServletRequest httpRequest) {
        try {
            //在session中取出管理员的名字
            UserMemory user = (UserMemory) httpRequest.getSession().getAttribute("user");
            String datetime = DateUtils.getCurrentDateTime(DateUtils.dateTimeFormat);
            LogEntity entity = new LogEntity(user.getUser(), type, user.getIp(), user.getCity(), datetime);
            logService.insert(entity);
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("记录日志异常" + Log4jUtils.getTrace(e));
        }
    }

}
