package ac.cn.saya.laboratory.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: GuestBookEntity
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/12 19:34
 * @Description:
 * 留言簿
 */
@NoArgsConstructor
@Getter
@Setter
public class GuestBookEntity extends BaseEntity{

    private static final long serialVersionUID = 1574519837258335692L;
    /**
     * 留言编号
     */
    private Integer id;

    /**
     * 留言者姓名
     */
    private String name;

    /**
     * 留言者phone
     */
    private String phone;

    /**
     * 留言者邮箱
     */
    private String email;

    /**
     * 留言正文
     */
    private String content;

    /**
     * 回复者
     */
    private String source;

    /**
     * 回复内容
     */
    private String reply;

    /**
     * 状态，用户提交后的状态2，已回复状态1，已屏蔽4
     */
    private Integer status;

    /**
     * 留言时间
     */
    private String createtime;

    /**
     * 回复时间
     */
    private String updatetime;
}
