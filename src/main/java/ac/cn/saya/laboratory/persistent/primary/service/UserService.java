package ac.cn.saya.laboratory.persistent.primary.service;

import ac.cn.saya.laboratory.entity.ThirdUserEntity;
import ac.cn.saya.laboratory.entity.UserEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.primary.dao.PrimaryBatchDAO;
import ac.cn.saya.laboratory.persistent.primary.dao.ThirdUserDAO;
import ac.cn.saya.laboratory.persistent.primary.dao.UserDAO;
import ac.cn.saya.laboratory.tools.CurrentLineInfo;
import ac.cn.saya.laboratory.tools.ResultEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
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
    
    @Resource
    private UserDAO userDAO;

    @Resource
    private ThirdUserDAO thirdUserDAO;

    @Resource
    @Qualifier("primaryBatchDAO")
    private PrimaryBatchDAO batchDAO;

    /**
     * 获取第三方用户信息
     * @param openId 用户在三方中唯一的标识
     * @return 三方用户信息
     */
    public ThirdUserEntity getThirdUser(String openId,String type){
        try {
            ThirdUserEntity query = new ThirdUserEntity();
            query.setOpenId(openId);
            query.setType(type);
            return thirdUserDAO.queryUserByOpenId(query);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("获取第三方用户信息失败",e, UserService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * 更新第三方用户信息
     * @param param 更新的内容
     * @return 更新结果
     */
    public int updateThirdUser(ThirdUserEntity param){
        int result = 0;
        try {
            result = thirdUserDAO.updateThirdUser(param);
            if (result <= 0) {
                // 更新失败
                result = ResultEnum.ERROP.getCode();
            }
            return result;
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("更新第三方用户信息失败",e, UserService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

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
            CurrentLineInfo.printCurrentLineInfo("获取用户信息失败",e, UserService.class);
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
    public int setUser(UserEntity user) {
        int result = 0;
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
            CurrentLineInfo.printCurrentLineInfo("修改用户信息失败",e, UserService.class);
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
            CurrentLineInfo.printCurrentLineInfo("查询近半年活跃情况失败",e, UserService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

}
