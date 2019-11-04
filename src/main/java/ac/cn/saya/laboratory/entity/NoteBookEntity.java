package ac.cn.saya.laboratory.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: NoteBookEntity
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/19 17:08
 * @Description:
 * 笔记簿名
 */
@NoArgsConstructor
@Getter
@Setter
public class NoteBookEntity extends BaseEntity{

    private static final long serialVersionUID = -3627300703508384086L;
    /**
     * 笔记簿编号
     */
    private Integer id;

    /**
     * 笔记簿名
     */
    private String  name;

    /**
     * 发布者
     */
    private String source;

    /**
     * 是否公开：1、公开；2、屏蔽
     */
    private Integer status;

    /**
     * 笔记簿描述
     */
    private String descript;

    /**
     * 本笔记簿下笔记总数
     */
    private Integer notesCount;

    public NoteBookEntity(String name,String source,Integer status) {
        this.name = name;
        this.source = source;
        this.status = status;
    }
}
