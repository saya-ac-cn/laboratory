package ac.cn.saya.laboratory.persistent.service;

import ac.cn.saya.laboratory.entity.TransactionListEntity;
import ac.cn.saya.laboratory.entity.UserEntity;
import ac.cn.saya.laboratory.persistent.dao.BatchDAO;
import ac.cn.saya.laboratory.persistent.dao.UserDAO;
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
import java.util.List;
import java.util.Map;

/**
 * @描述 用户业务层实现类
 * @参数
 * @返回值  
 * @创建人  saya.ac.cn-刘能凯
 * @创建时间  2018/11/11
 * @修改人和其它信息
 * 增加备注：
 * 注意MySQL表中的数据库引擎必须是InnoDB，否则不会生效
 * propagation=Propagation.REQUIRED  传播行为：支持当前事务，如果当前没有事务，就新建一个事务
 * isolation = Isolation.READ_COMMITTED 隔离级别：读写提交
 * rollbackFor=Exception.class 发送异常时回滚：回滚全部
 */
@Service("userService")
@Transactional(propagation=Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED,rollbackFor=Exception.class,readOnly = true)
public class UserService{

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Resource
    @Qualifier("userDAO")
    private UserDAO userDAO;

    @Resource
    @Qualifier("batchDAO")
    private BatchDAO batchDAO;

    /**
     * @描述 获取用户的信息
     * @参数
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2018/11/11
     * @修改人和其它信息
     */
    public UserEntity getUser(String user){
        UserEntity entity = null;
        try {
            entity = userDAO.queryUser(user);
        }catch (Exception e) {
            logger.error("获取用户信息失败"+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return entity;
    }

    /**
     * @描述 修改用户信息
     * @参数  
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2018/11/11
     * @修改人和其它信息
     */
    @Transactional(readOnly = false)
    public Integer setUser(UserEntity user){
        Integer result = 0;
        if(user == null || StringUtils.isEmpty(user.getUser())) {
            // 缺少参数
            return ResultEnum.NOT_PARAMETER.getCode();
        }
        try
        {
            result = userDAO.updateUser(user);
            if( result <= 0) {
                // 修改失败
                result = ResultEnum.ERROP.getCode();
            }
        }catch (Exception e){
            result = 0;
            logger.error("修改用户信息失败"+Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return result;
    }

    /**
     * @描述 查询近半年的动态发布情况
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-03-03
     * @修改人和其它信息
     */
    public Map<String,Object> countPre6MonthNews(String user){
        Map<String,Object> result = null;
        try {
            result = batchDAO.countPre6MonthNews(user);
        }catch (Exception e) {
            logger.error("查询近半年的动态发布情况失败"+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return result;
    }

    /**
     * @描述 查询近半年活跃情况
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-03-03
     * @修改人和其它信息
     */
    public Map<String,Object> countPre6Logs(String user){
        Map<String,Object> result = null;
        try {
            result = batchDAO.countPre6Logs(user);
        }catch (Exception e) {
            logger.error("查询近半年活跃情况失败"+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return result;
    }

    /**
     * @描述 查询近半年文件上传情况
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-03-03
     * @修改人和其它信息
     */
    public Map<String,Object> countPre6Files(String user){
        Map<String,Object> result = null;
        try {
            result = batchDAO.countPre6Files(user);
        }catch (Exception e) {
            logger.error("查询近半年文件上传情况失败"+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return result;
    }

    /**
     * @描述 查询近半年留言情况
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-03-03
     * @修改人和其它信息
     */
    public Map<String,Object> countPre6Board(){
        Map<String,Object> result = null;
        try {
            result = batchDAO.countPre6Board();
        }catch (Exception e) {
            logger.error("查询近半年留言情况失败"+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return result;
    }

    /**
     * @描述 查询近半年财政收支情况
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-03-03
     * @修改人和其它信息
     */
    public List<TransactionListEntity> countPre6Financial(String user){
        List<TransactionListEntity> result = null;
        try {
            result = batchDAO.countPre6Financial(user);
        }catch (Exception e) {
            logger.error("查询近半年财政收支情况失败"+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return result;
    }

}
