package ac.cn.saya.laboratory.persistent.business.service;


import ac.cn.saya.laboratory.entity.NewsEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.business.dao.BusinessBatchDAO;
import ac.cn.saya.laboratory.persistent.business.dao.NewsDAO;
import ac.cn.saya.laboratory.tools.CurrentLineInfo;
import ac.cn.saya.laboratory.tools.HtmlUtils;
import ac.cn.saya.laboratory.tools.ResultEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Title: NewsService
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/10 23:03
 * @Description:
 */

@Service("newsService")
@Transactional(value = "primaryTransactionManager", readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = MyException.class)
public class NewsService {

    @Resource
    private NewsDAO newsDAO;

    @Resource
    @Qualifier("businessBatchDAO")
    private BusinessBatchDAO batchDAO;

    /**
     * @描述 发布动态
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public Integer publishNews(NewsEntity entity) {
        try {
            return newsDAO.insertNews(entity);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("发布动态异常", e, NewsService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 编辑修改动态
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public Integer editNews(NewsEntity entity) {
        try {
            return newsDAO.updateNews(entity);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("编辑修改动态异常", e, NewsService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 删除动态
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public Integer deleteNews(NewsEntity entity) {
        try {
            return newsDAO.deleteNews(entity);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("删除动态异常", e, NewsService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询一条动态
     * @参数 [entity]
     * @返回值 ac.cn.saya.datacenter.entity.NewsEntity
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public NewsEntity getOneNews(NewsEntity entity) {
        try {
            return newsDAO.getOneNews(entity);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("查询动态异常", e, NewsService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 获取分页后的动态
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public List<NewsEntity> getNewsPage(NewsEntity entity) {
        try {
            List<NewsEntity> list = newsDAO.getNewsPage(entity);
            if (CollectionUtils.isEmpty(list)) {
                return list;
            }
            // 过滤文本
            list = list.stream().map(item -> {
                item.setContent(HtmlUtils.SplitHtmlText(item.getContent(), 150));
                return item;
            }).collect(Collectors.toList());
            return list;
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("获取分页后的动态发生异常", e, NewsService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 获取动态总数
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public Long getNewsCount(NewsEntity entity) {
        try {
            return newsDAO.getNewsCount(entity);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("获取动态总数时发生异常", e, NewsService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 获取上一条和下一条动态
     * @参数 [newsId]
     * @返回值 java.util.Map<java.lang.String, java.lang.String>
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-18
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public Map<String, String> getNewsPreAndNext(Integer newsId) {
        try {
            return batchDAO.getNewsNotesPreAndNext(1, newsId);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("获取上一条和下一条动态时发生异常", e, NewsService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询近半年的动态发布情况
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-03
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public Map<String, Object> countPre6MonthNews(String user,String endDate) {
        try {
            return batchDAO.countPre6MonthNews(user,endDate);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("查询近半年的动态发布情况失败", e, NewsService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

}
