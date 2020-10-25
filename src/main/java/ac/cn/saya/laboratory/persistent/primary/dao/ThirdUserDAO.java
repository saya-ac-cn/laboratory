package ac.cn.saya.laboratory.persistent.primary.dao;

import ac.cn.saya.laboratory.entity.ThirdUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Title: ThirdUserDAO
 * @ProjectName laboratory
 * @Description: TODO
 * @Author saya
 * @Date: 2020/10/25 20:44
 * @Description: 三方用户DAO层
 */

@Mapper
public interface ThirdUserDAO {

    /**
     * 根据三方用户id查询用户信息
     * @param entity 三方信息
     * @return 用户的完整信息
     */
    public ThirdUserEntity queryUserByOpenId(ThirdUserEntity entity);

    /**
     * 更新第三方用户信息
     * @param entity 更新的内容
     * @return 更新结果
     */
    public int updateThirdUser(ThirdUserEntity entity);

}
