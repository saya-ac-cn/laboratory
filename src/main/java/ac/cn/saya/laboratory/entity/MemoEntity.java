package ac.cn.saya.laboratory.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 便笺、便利贴
 * @Title: MemoEntity
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-09-18 23:03
 * @Description:
 */
@NoArgsConstructor
@Getter
@Setter
public class MemoEntity extends BaseEntity{

    private static final long serialVersionUID = -8697347000612852109L;
    /**
     * 便笺编号
     */
    private Integer id;

    /**
     * 创建者
     */
    private String source;

    /**
     * 标题
     */
    private String title;

    /**
     * 便笺内容
     */
    private String content;

    /**
     * 创建时间
     */
    private String createtime;

    /**
     * 修改时间
     */
    private String updatetime;
}
