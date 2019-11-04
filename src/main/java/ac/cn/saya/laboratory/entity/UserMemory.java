package ac.cn.saya.laboratory.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户的内存记录信息
 * @Title: UserMemory
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-10-02 23:32
 * @Description:
 */
@NoArgsConstructor
@Getter
@Setter
public class UserMemory {

    /**
     * 用户名
     */
    private String user;

    /**
     * ip
     */
    private String ip;

    /**
     * ip所在城市
     */
    private String city;
}
