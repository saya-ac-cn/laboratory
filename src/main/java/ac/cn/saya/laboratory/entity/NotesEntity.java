package ac.cn.saya.laboratory.entity;

/**
 * @Title: NotesEntity
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/19 17:10
 * @Description:
 * 笔记
 */

public class NotesEntity extends BaseEntity{

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


    public NotesEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNotebookId() {
        return notebookId;
    }

    public void setNotebookId(Integer notebookId) {
        this.notebookId = notebookId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public NoteBookEntity getNotebook() {
        return notebook;
    }

    public void setNotebook(NoteBookEntity notebook) {
        this.notebook = notebook;
    }
}
