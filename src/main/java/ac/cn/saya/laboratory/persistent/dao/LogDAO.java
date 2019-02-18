package ac.cn.saya.laboratory.persistent.dao;

import ac.cn.saya.laboratory.entity.LogEntity;
import ac.cn.saya.laboratory.entity.LogTypeEntity;
import org.apache.ibatis.annotations.Mapper;
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
@Repository("logDAO")
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

}
