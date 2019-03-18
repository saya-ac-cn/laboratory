package ac.cn.saya.laboratory.service;

import ac.cn.saya.laboratory.entity.NewsEntity;
import ac.cn.saya.laboratory.tools.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * @Title: IFrontendService
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-03-18 22:40
 * @Description:
 * 对外提供访问的接口，无需认证
 * 留言编号：20190318fb8ca4ca , 查询码：44a90448
 */

public interface IFrontendService {

    /**
     * @描述 查询一条动态
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/12
     * @修改人和其它信息
     */
    public Result<Object> getOneNews(NewsEntity entity) throws Exception;

    /**
     * @描述 获取分页的动态
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Result<Object> getNewsList(NewsEntity entity) throws Exception;

}
