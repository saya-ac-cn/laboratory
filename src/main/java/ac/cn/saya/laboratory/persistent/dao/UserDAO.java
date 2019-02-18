package ac.cn.saya.laboratory.persistent.dao;
import ac.cn.saya.laboratory.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Title: UserDAO
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/9/19 23:20
 * @Description:
 * 用户Dao层
 */
@Mapper
@Repository("userDAO")
public interface UserDAO {

    /**
     * 查询用户信息
     * @param user
     * @return
     */
    public UserEntity queryUser(@Param("user") String user);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public Integer updateUser(UserEntity user);

}
