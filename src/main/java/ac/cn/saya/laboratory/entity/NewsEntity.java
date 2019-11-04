package ac.cn.saya.laboratory.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: NewsEntity
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/11 21:14
 * @Description:
 * 动态实体类
 */
@NoArgsConstructor
@Getter
@Setter
public class NewsEntity extends BaseEntity{

    private static final long serialVersionUID = 8194446901823514395L;
    /**
     * 编号
     */
    private Integer id;

    /**
     * 主题
     */
    private String topic;

    /**
     * 标签
     */
    private String label;

    /**
     * 正文
     */
    private String content;

    /**
     * 作者
     */
    private String source;

    /**
     * 创建时间
     */
    private String createtime;

    /**
     * 修改时间
     */
    private String updatetime;
}
