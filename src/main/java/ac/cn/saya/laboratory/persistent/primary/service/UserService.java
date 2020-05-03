package ac.cn.saya.laboratory.persistent.primary.service;

import ac.cn.saya.laboratory.entity.LogEntity;
import ac.cn.saya.laboratory.entity.PlanEntity;
import ac.cn.saya.laboratory.entity.UserEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.primary.dao.PrimaryBatchDAO;
import ac.cn.saya.laboratory.persistent.primary.dao.LogDAO;
import ac.cn.saya.laboratory.persistent.primary.dao.PlanDAO;
import ac.cn.saya.laboratory.persistent.primary.dao.UserDAO;
import ac.cn.saya.laboratory.tools.CurrentLineInfo;
import ac.cn.saya.laboratory.tools.Log4jUtils;
import ac.cn.saya.laboratory.tools.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @描述 用户业务层实现类
 * @参数
 * @返回值
 * @创建人 saya.ac.cn-刘能凯
 * @创建时间 2018/11/11
 * @修改人和其它信息
 */
@Service("userService")
@Transactional(value = "primaryTransactionManager",readOnly = true,propagation= Propagation.REQUIRED, isolation= Isolation.REPEATABLE_READ, rollbackFor=MyException.class)
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Resource
    @Qualifier("userDAO")
    private UserDAO userDAO;

    @Resource
    @Qualifier("primaryBatchDAO")
    private PrimaryBatchDAO batchDAO;

    @Resource
    @Qualifier("logDAO")
    private LogDAO logDAO;

    @Resource
    @Qualifier("planDAO")
    private PlanDAO planDAO;

    /**
     * @描述 获取用户的信息
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2018/11/11
     * @修改人和其它信息
     */
    public UserEntity getUser(String user) {
        try {
            return userDAO.queryUser(user);
        } catch (Exception e) {
            logger.error("获取用户信息失败" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 修改用户信息
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2018/11/11
     * @修改人和其它信息
     */
    @Transactional(readOnly = false)
    public Integer setUser(UserEntity user) {
        Integer result = 0;
        if (user == null || StringUtils.isEmpty(user.getUser())) {
            // 缺少参数
            return ResultEnum.NOT_PARAMETER.getCode();
        }
        try {
            result = userDAO.updateUser(user);
            if (result <= 0) {
                // 修改失败
                result = ResultEnum.ERROP.getCode();
            }
            return result;
        } catch (Exception e) {
            logger.error("修改用户信息失败" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
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
    public Map<String, Object> countPre6MonthNews(String user) {
        try {
            return batchDAO.countPre6MonthNews(user);
        } catch (Exception e) {
            logger.error("查询近半年的动态发布情况失败" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询近半年活跃情况
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-03
     * @修改人和其它信息
     */
    public Map<String, Object> countPre6Logs(String user) {
        try {
            return batchDAO.countPre6Logs(user);
        } catch (Exception e) {
            logger.error("查询近半年活跃情况失败" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询近半年文件上传情况
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-03
     * @修改人和其它信息
     */
    public Map<String, Object> countPre6Files(String user) {
        try {
            return batchDAO.countPre6Files(user);
        } catch (Exception e) {
            logger.error("查询近半年文件上传情况失败" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询近半年便笺发布情况
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-03
     * @修改人和其它信息
     */
    public Map<String, Object> countPre6Memo() {
        try {
            return batchDAO.countPre6Memo();
        } catch (Exception e) {
            logger.error("查询近半年便笺发布情况失败" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询用户当日的计划安排，最近的一次操作
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-09-19
     * @修改人和其它信息
     */
    public Map<String, Object> queryUserRecentlyInfo(String user) {
        List<PlanEntity> plan = null;
        LogEntity log = null;
        Map<String, Object> result = new HashMap<>();
        try {
            // 查询用户当日安排
            plan = planDAO.getTodayPlanListByUser(user);
            result.put("plan",plan);
        } catch (Exception e) {
            logger.error("查询用户当日安排失败" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            result.put("plan",null);
        }
        try {
            // 查询用户最近的操作
            log = logDAO.queryRecentlyLog(user);
            result.put("log",log);
        } catch (Exception e) {
            logger.error("查询用户最近的操作失败" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            result.put("log",null);
        }
        return result;
    }

}
