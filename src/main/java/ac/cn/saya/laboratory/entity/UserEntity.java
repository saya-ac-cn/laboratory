package ac.cn.saya.laboratory.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: UserEntity
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/9/19 22:54
 * @Description:
 * 用户实体信息
 */
@NoArgsConstructor
@Getter
@Setter
public class UserEntity extends BaseEntity {

    private static final long serialVersionUID = 5355055389991833405L;
    /**
     * 用户名
     */
    private String user;
    /**
     * 密码
     */
    private String password;
    /**
     * 性别
     */
    private String sex;
    /**
     * qq
     */
    private String qq;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话
     */
    private String phone;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 故乡
     */
    private String hometown;
    /**
     * 个性签名
     */
    private String autograph;
    /**
     * 头像地址
     */
    private String logo;

    /**
     * 用户设置的背景图片编号
     */
    private Integer backgroundId;

    /**
     * 用户设置的背景
     */
    private String background;
    /**
     * 修改时间
     */
    private String updateTime;
}
