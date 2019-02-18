package ac.cn.saya.laboratory.persistent.service;

import ac.cn.saya.laboratory.entity.UserEntity;
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

}
