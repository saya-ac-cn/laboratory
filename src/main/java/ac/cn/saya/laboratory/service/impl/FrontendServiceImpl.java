package ac.cn.saya.laboratory.service.impl;

import ac.cn.saya.laboratory.entity.NewsEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.service.NewsService;
import ac.cn.saya.laboratory.service.IFrontendService;
import ac.cn.saya.laboratory.tools.Paging;
import ac.cn.saya.laboratory.tools.Result;
import ac.cn.saya.laboratory.tools.ResultEnum;
import ac.cn.saya.laboratory.tools.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: FrontendServiceImpl
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-03-18 22:51
 * @Description:
 */
@Service("frontendServiceImpl")
public class FrontendServiceImpl implements IFrontendService {

    private static Logger logger = LoggerFactory.getLogger(FrontendServiceImpl.class);

    @Resource
    @Qualifier("newsService")
    private NewsService newsService;

    /**
     * @param entity
     * @描述 查询一条动态
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @Override
    public Result<Object> getOneNews(NewsEntity entity) throws Exception {
        if(entity == null || entity.getId() == null){
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        NewsEntity result = newsService.getOneNews(entity);
        if(result == null){
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        }else{
            // 寻找上一条和下一条
            Map<String,String> preAndNext = newsService.getNewsPreAndNext(entity.getId());
            Map<String,NewsEntity> out = new HashMap();
            out.put("now",result);
            if(preAndNext != null){
                for (Map.Entry<String,String> item : preAndNext.entrySet()) {
                    entity.setId(Integer.valueOf(item.getValue()));
                    result = newsService.getOneNews(entity);
                    out.put(item.getKey(),result);
                }
            }
            return ResultUtil.success(out);
        }
    }

    /**
     * @param entity
     * @描述 获取分页的动态
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Override
    public Result<Object> getNewsList(NewsEntity entity) throws Exception {
        Paging paging =new Paging();
        if(entity.getNowPage() == null){
            entity.setNowPage(1);
        }
        if(entity.getPageSize() == null){
            entity.setPageSize(20);
        }
        //每页显示记录的数量
        paging.setPageSize(entity.getPageSize());
        //获取满足条件的总记录（不分页）
        Long pageSize = newsService.getNewsCount(entity);
        if(pageSize > 0) {
            //总记录数
            paging.setDateSum(pageSize);
            //计算总页数
            paging.setTotalPage();
            //设置当前的页码-并校验是否超出页码范围
            paging.setPageNow(entity.getNowPage());
            //设置行索引
            entity.setPage((paging.getPageNow()-1)*paging.getPageSize(),paging.getPageSize());
            //获取满足条件的记录集合
            List<NewsEntity> list = newsService.getNewsPage(entity);
            paging.setGrid(list);
            return ResultUtil.success(paging);
        }else{
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        }
    }
}
