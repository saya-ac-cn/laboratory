package ac.cn.saya.laboratory.persistent.service;

import ac.cn.saya.laboratory.entity.GuestBookEntity;
import ac.cn.saya.laboratory.persistent.dao.GuestBookDAO;
import ac.cn.saya.laboratory.tools.CurrentLineInfo;
import ac.cn.saya.laboratory.tools.Log4jUtils;
import ac.cn.saya.laboratory.tools.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: NewsService
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/10 23:03
 * @Description:
 * 留言dubbo
 */

@Service("guestBookService")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor=Exception.class)
public class GuestBookService {


    private static Logger logger = LoggerFactory.getLogger(GuestBookService.class);

    @Resource
    @Qualifier("guestBookDAO")
    private GuestBookDAO guestBookDAO;

    /**
     * @描述 留言
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    @Transactional(readOnly = false)
    public Integer insertGuestBook(GuestBookEntity entity) {
        Integer flog = null;
        try
        {
            flog = guestBookDAO.insertGuestBook(entity);
        }catch (Exception e) {
            flog = ResultEnum.UNKONW_ERROR.getCode();
            logger.error("用户留言异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return flog;
    }

    /**
     * @描述 审核修改
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    @Transactional(readOnly = false)
    public Integer updateGuestBook(GuestBookEntity entity) {
        Integer flog = null;
        try
        {
            flog = guestBookDAO.updateGuestBook(entity);
        }catch (Exception e) {
            flog = ResultEnum.UNKONW_ERROR.getCode();
            logger.error("审核修改留言异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return flog;
    }

    /**
     * @描述 查询一条留言
     * @参数  [entity]
     * @返回值  ac.cn.saya.datacenter.entity.NewsEntity
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/12
     * @修改人和其它信息
     */
    public GuestBookEntity queryOneGuestBook(GuestBookEntity entity){
        GuestBookEntity result = null;
        try
        {
            result = guestBookDAO.getOneGuestBook(entity);
        }catch (Exception e) {
            result = null;
            logger.error("查询动态异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return result;
    }

    /**
     * @描述 获取分页后的留言
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public List<GuestBookEntity> getGuestBookPage(GuestBookEntity entity) {
        List<GuestBookEntity> list = new ArrayList<>();
        try
        {
            list = guestBookDAO.getGuestBookPage(entity);
            if(list.size() <= 0) {
                list = null;
            }
        }catch (Exception e) {
            list = null;
            logger.error("获取分页后的留言发生异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return list;
    }

    /**
     * @描述 获取留言总数
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Long getGuestBookCount(GuestBookEntity entity){
        Long total = null;
        try
        {
            total = guestBookDAO.getGuestBookCount(entity);
        }catch (Exception e) {
            total = Long.valueOf(ResultEnum.ERROP.getCode());
            logger.error("获取留言总数异常："+Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return total;
    }

}
