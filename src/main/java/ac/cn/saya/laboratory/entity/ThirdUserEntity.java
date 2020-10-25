package ac.cn.saya.laboratory.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: ThirdUserEntity
 * @ProjectName laboratory
 * @Description: TODO
 * @Author saya
 * @Date: 2020/10/25 20:35
 * @Description: 三方用户信息（微信，支付宝，QQ）
 */
@NoArgsConstructor
@Getter
@Setter
public class ThirdUserEntity extends BaseEntity {

    private static final long serialVersionUID = 8048980609769328353L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 绑定的用户
     */
    private String  user;

    /**
     * 三方用户唯一id
     */
    private String  openId;

    /**
     * 三方用户类型('wx','qq')
     */
    private String  type;

    /**
     * 昵称
     */
    private String  nickName;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 语言
     */
    private String  language;

    /**
     * 所在市
     */
    private String  city;

    /**
     * 所在省
     */
    private String  province;

    /**
     * 国家
     */
    private String  country;

    /**
     * 头像
     */
    private String  avatarUrl;

    /**
     * 创建时间
     */
    private String  createTime;

    /**
     * 更新时间
     */
    private String  updateTime;

    /**
     * 关联的用户信息（非数据库字段）
     */
    private UserEntity localUser;

}
