package ac.cn.saya.laboratory.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: NotesEntity
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/19 17:10
 * @Description:
 * 笔记
 */
@NoArgsConstructor
@Getter
@Setter
public class NotesEntity extends BaseEntity{

    private static final long serialVersionUID = -7973110959944488014L;
    /**
     * 笔记编号
     */
    private Integer id;

    /**
     * 笔记簿编号
     */
    private Integer notebookId;

    /**
     * 标签
     */
    private String label;

    /**
     * 主题
     */
    private String topic;

    /**
     * 正文
     */
    private String content;

    /**
     * 发布日期
     */
    private String createtime;

    /**
     * 修改日期
     */
    private String updatetime;

    /**
     * 所属的笔记簿
     */
    private NoteBookEntity notebook;
}
