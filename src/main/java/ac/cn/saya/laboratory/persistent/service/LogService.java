package ac.cn.saya.laboratory.persistent.service;

import ac.cn.saya.laboratory.entity.LogEntity;
import ac.cn.saya.laboratory.entity.LogTypeEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.dao.LogDAO;
import ac.cn.saya.laboratory.tools.CurrentLineInfo;
import ac.cn.saya.laboratory.tools.Log4jUtils;
import ac.cn.saya.laboratory.tools.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
public class LogService {

    private static Logger logger = LoggerFactory.getLogger(LogService.class);

    @Resource
    @Qualifier("logDAO")
    private LogDAO logDAO;

    /**
     * @描述 插入日志
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2018/11/11
     * @修改人和其它信息
     */
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
            logger.error("插入日志时发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
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
        List<LogTypeEntity> list = new ArrayList<>();
        try {
            list = logDAO.selectType();
            if (list.size() <= 0) {
                list = null;
            }
            return list;
        } catch (Exception e) {
            logger.error("查询日志类别时发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
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
        List<LogEntity> list = new ArrayList<>();
        try {
            list = logDAO.selectPage(entity);
            if (list.size() <= 0) {
                list = null;
            }
            return list;
        } catch (Exception e) {
            logger.error("查询日志时发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
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
            logger.error("统计日志总数时发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

}
