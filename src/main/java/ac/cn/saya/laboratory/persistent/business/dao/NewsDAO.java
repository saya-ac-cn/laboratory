package ac.cn.saya.laboratory.persistent.business.dao;

import ac.cn.saya.laboratory.entity.NewsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title: NewsDAO
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/10 23:06
 * @Description:
 * 动态DAO
 */

@Mapper
public interface NewsDAO {

    /**
     * @描述 发布动态
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Integer insertNews(NewsEntity entity);

    /**
     * @描述 编辑修改动态
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Integer updateNews(NewsEntity entity);

    /**
     * @描述 删除动态
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Integer deleteNews(NewsEntity entity);

    /**
     * @描述 查询一条动态
     * @参数  
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/12
     * @修改人和其它信息
     */
    public NewsEntity getOneNews(NewsEntity entity);

    /**
     * @描述 获取分页后的动态
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public List<NewsEntity> getNewsPage(NewsEntity entity);

    /**
     * @描述 获取动态总数
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Long getNewsCount(NewsEntity entity);

}
