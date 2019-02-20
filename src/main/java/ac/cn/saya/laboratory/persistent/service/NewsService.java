package ac.cn.saya.laboratory.persistent.service;


import ac.cn.saya.laboratory.entity.NewsEntity;
import ac.cn.saya.laboratory.persistent.dao.NewsDAO;
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
 */

@Service("newsService")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = false, rollbackFor=Exception.class)
public class NewsService {


    private static Logger logger = LoggerFactory.getLogger(NewsService.class);

    @Resource
    @Qualifier("newsDAO")
    private NewsDAO newsDAO;

    /**
     * @param entity
     * @描述 发布动态
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public Integer publishNews(NewsEntity entity) {
        Integer flog = null;
        try
        {
            flog = newsDAO.insertNews(entity);
        }catch (Exception e) {
            flog = ResultEnum.UNKONW_ERROR.getCode();
            logger.error("发布动态异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return flog;
    }

    /**
     * @param entity
     * @描述 编辑修改动态
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public Integer editNews(NewsEntity entity) {
        Integer flog = null;
        try
        {
            flog = newsDAO.updateNews(entity);
        }catch (Exception e) {
            flog = ResultEnum.UNKONW_ERROR.getCode();
            logger.error("编辑修改动态异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return flog;
    }

    /**
     * @param entity
     * @描述 删除动态
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public Integer deleteNews(NewsEntity entity) {
        Integer flog = null;
        try
        {
            flog = newsDAO.deleteNews(entity);
        }catch (Exception e) {
            flog = ResultEnum.UNKONW_ERROR.getCode();
            logger.error("删除动态异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return flog;
    }

    /**
     * @描述 查询一条动态
     * @参数  [entity]
     * @返回值  ac.cn.saya.datacenter.entity.NewsEntity
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/12
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public NewsEntity getOneNews(NewsEntity entity){
        NewsEntity result = null;
        try
        {
            result = newsDAO.getOneNews(entity);
        }catch (Exception e) {
            result = null;
            logger.error("查询动态异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return result;
    }

    /**
     * @param entity
     * @描述 获取分页后的动态
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public List<NewsEntity> getNewsPage(NewsEntity entity) {
        List<NewsEntity> list = new ArrayList<>();
        try
        {
            list = newsDAO.getNewsPage(entity);
            if(list.size() <= 0) {
                list = null;
            }
        }catch (Exception e) {
            list = null;
            logger.error("获取分页后的动态发生异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return list;
    }

    /**
     * @param entity
     * @描述 获取动态总数
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public Long getNewsCount(NewsEntity entity) {
        Long total = null;
        try
        {
            total = newsDAO.getNewsCount(entity);
        }catch (Exception e) {
            total = Long.valueOf(ResultEnum.ERROP.getCode());
            logger.error("获取动态总数时发生异常："+Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return total;
    }

}
