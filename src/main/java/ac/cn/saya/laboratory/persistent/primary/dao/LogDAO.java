package ac.cn.saya.laboratory.persistent.primary.dao;

import ac.cn.saya.laboratory.entity.LogEntity;
import ac.cn.saya.laboratory.entity.LogTypeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title: LogDAO
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/9/22 22:23
 * @Description:
 * 日志Dao
 */

@Mapper
public interface LogDAO {

    /**
     * 插入一条日志
     * 返回插入后的主键
     * @param entity
     * @return
     */
    public Integer insert(LogEntity entity);

    /**
     * 获取所有的日志类别
     * @return
     */
    public List<LogTypeEntity> selectType();

    /**
     * 查询日志-分页
     * 按用户、类别、日期
     * @param entity
     * @return
     */
    public List<LogEntity> selectPage(LogEntity entity);

    /**
     * 查询日志-计数
     * 按用户、类别、日期
     * @param entity
     * @return
     */
    public Long selectCount(LogEntity entity);

    /**
     * @描述 查询该用户最近的一次操作
     * @param user 查询的用户
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-09-19
     * @修改人和其它信息
     */
    public LogEntity queryRecentlyLog(@Param("user") String user);

}
