package ac.cn.saya.laboratory.persistent.primary.service;

import ac.cn.saya.laboratory.entity.LogEntity;
import ac.cn.saya.laboratory.entity.LogTypeEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.primary.dao.LogDAO;
import ac.cn.saya.laboratory.tools.CurrentLineInfo;
import ac.cn.saya.laboratory.tools.ResultEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @描述 用户业务层实现类
 * @参数
 * @返回值
 * @创建人 saya.ac.cn-刘能凯
 * @创建时间 2018/11/11
 * @修改人和其它信息 增加备注：
 * 注意MySQL表中的数据库引擎必须是InnoDB，否则不会生效
 * propagation=Propagation.REQUIRED  传播行为：支持当前事务，如果当前没有事务，就新建一个事务
 * isolation = Isolation.READ_COMMITTED 隔离级别：读写提交
 * rollbackFor=Exception.class 发送异常时回滚：回滚全部
 */

@Service("logService")
@Transactional(value = "primaryTransactionManager",readOnly = true,propagation= Propagation.REQUIRED, isolation= Isolation.REPEATABLE_READ, rollbackFor=MyException.class)
public class LogService {

    @Resource
    @Qualifier("logDAO")
    private LogDAO logDAO;

    /**
     * @描述 查询用户当日的计划安排，最近的一次操作
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-09-19
     * @修改人和其它信息
     */
    public LogEntity queryRecentlyLog(String user) {
        try {
            // 查询用户最近的操作
            return logDAO.queryRecentlyLog(user);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("查询用户最近的操作失败",e, LogService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 插入日志
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2018/11/11
     * @修改人和其它信息
     */
    @Transactional(readOnly = false)
    public Integer insert(LogEntity entity) {
        Integer flog = null;
        try {
            flog = logDAO.insert(entity);
            if (flog > 0) {
                // 返回自增后的主键
                flog = entity.getId();
            } else {
                //插入失败
                flog = ResultEnum.ERROP.getCode();
            }
            return flog;
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("插入日志时发生异常",e, LogService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 获取所有的日志类别
     * @参数 []
     * @返回值 java.util.List<ac.cn.saya.datacenter.entity.LogTypeEntity>
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2018/11/11
     * @修改人和其它信息
     */
    public List<LogTypeEntity> selectLogType() {
        try {
            return logDAO.selectType();
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("查询日志类别时发生异常",e, LogService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询日志 按用户、类别、日期
     * @参数 [entity]
     * @返回值 java.util.List<ac.cn.saya.datacenter.entity.LogEntity>
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2018/11/11
     * @修改人和其它信息
     */
    public List<LogEntity> selectPage(LogEntity entity) {
        try {
            return logDAO.selectPage(entity);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("查询日志时发生异常",e, LogService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询日志-计数 按用户、类别、日期
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2018/11/11
     * @修改人和其它信息
     */
    public Long selectCount(LogEntity entity) {
        try {
            return logDAO.selectCount(entity);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("统计日志总数时发生异常",e, LogService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

}
